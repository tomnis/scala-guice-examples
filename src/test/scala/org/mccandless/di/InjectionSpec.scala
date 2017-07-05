package org.mccandless.di

import com.google.inject.name.Names
import com.google.inject.{AbstractModule, Guice, Injector, Provides}
import org.junit.Test
import org.mccandless.di.annotations.{Login, Secure}
import org.mccandless.di.credentials.{NamedCredentials, TypesafeCredentials}
import org.mccandless.di.ledgers.{FileLedger, Ledger, MemoryLedger, MemoryLedgerProvider}
import org.scalatest.Matchers
import org.scalatest.junit.JUnitSuite

/**
  * Some examples of using guice with scala.
  *
  * Each test creates its own local module with dependency bindings.
  *
  * Created by tomas.mccandless on 7/5/17.
  */
class InjectionSpec extends JUnitSuite with Matchers {

  /** Checks basic binding defined in a configure method of a module. */
  @Test
  def binding(): Unit = {
    // use the file ledger for transaction logging
    class BankModule extends AbstractModule {
      override def configure(): Unit = bind(classOf[Ledger]).to(classOf[FileLedger])
    }

    val injector: Injector = Guice.createInjector(new BankModule)
    val bank: Bank = injector.getInstance(classOf[Bank])
    bank.send("billy", "tomas", 5000)
    bank.ledger.getClass.getName should be (classOf[FileLedger].getName)
  }


  /** Checks using a provider for a binding. Our module binds the given class to a provider in the configure method. */
  @Test
  def withProvider(): Unit = {
    // use a provider to instantiate ledgers
    class BankProviderModule extends AbstractModule {
      override def configure(): Unit = bind(classOf[Ledger]).toProvider(classOf[MemoryLedgerProvider])
    }

    val injector: Injector = Guice.createInjector(new BankProviderModule)
    val bank: Bank = injector.getInstance(classOf[Bank])
    bank.send("billy", "tomas", 5000)
    bank.ledger.getClass.getName should be (classOf[MemoryLedger].getName)
  }


  /** Checks using a Provides method for a binding. Note that our module has an empty configure method. */
  @Test
  def withProvidesMethod(): Unit = {
    // use a provides method for creating ledger instances
    class BankProviderModule extends AbstractModule {
      // note this method body is empty
      override def configure(): Unit = { }

      @Provides def provideLedger: Ledger = new FileLedger
    }

    val injector: Injector = Guice.createInjector(new BankProviderModule)
    val bank: Bank = injector.getInstance(classOf[Bank])
    bank.send("billy", "tomas", 5000)
    bank.ledger.getClass.getName should be (classOf[FileLedger].getName)
  }


  /** Checks injecting a field into a singleton object. Note the field is null before injection takes place. */
  @Test
  def injectIntoObject(): Unit = {
    SingletonService.ledger should be (null)

    // inject a memory ledger into our singleton object
    class ObjectInjectionModule extends AbstractModule {
      override def configure(): Unit = {
        val ledger: Ledger = new MemoryLedger
        bind(classOf[Ledger]).toInstance(ledger)
        bind(classOf[ServiceLike]).toInstance(SingletonService)
      }
    }

    val injector: Injector = Guice.createInjector(new ObjectInjectionModule)
    val component = injector.getInstance(classOf[ServiceLike])

    // evil, the val in the object has been reassigned. this means our SingletonService is not in a usable state until
    // injection has taken place.
    SingletonService.ledger should not be null

    component.getLedgerName should be (classOf[MemoryLedger].getName)
  }


  /** Checks injecting fields using @Named annotation. This approach should be avoided due to a lack of type safety. */
  @Test
  def namedInjection(): Unit = {
    val username: String = "bruce.wayne"
    val password: String = "iAmBatman123"

    class NamedInjectionModule extends AbstractModule {
      override def configure(): Unit = {
        // ensuring these names match up with what is declared on our class fields can be error-prone
        bind(classOf[String]).annotatedWith(Names.named("login")).toInstance(username)
        bind(classOf[String]).annotatedWith(Names.named("secure")).toInstance(password)
      }
    }

    val injector: Injector = Guice.createInjector(new NamedInjectionModule)
    val creds: NamedCredentials = injector.getInstance(classOf[NamedCredentials])
    creds.user should be (username)
    creds.password should be (password)
  }


  /** Checks injecting fields using custom annotations. This approach should be preferred. */
  @Test
  def annotatedInjection(): Unit = {
    val username: String = "bruce.wayne"
    val password: String = "iAmBatman123"

    class AnnotatedInjectionModule extends AbstractModule {
      override def configure(): Unit = {
        bind(classOf[String]).annotatedWith(classOf[Login]).toInstance(username)
        bind(classOf[String]).annotatedWith(classOf[Secure]).toInstance(password)
      }
    }

    val injector: Injector = Guice.createInjector(new AnnotatedInjectionModule)
    val creds: TypesafeCredentials = injector.getInstance(classOf[TypesafeCredentials])
    creds.user should be (username)
    creds.password should be (password)
  }
}
