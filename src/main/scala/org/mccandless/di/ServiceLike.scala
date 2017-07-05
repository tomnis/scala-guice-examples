package org.mccandless.di

import com.google.inject.Inject
import org.mccandless.di.ledgers.Ledger

/**
  * Example of injecting into a singleton.
  *
  * We need to define a trait extended by our object, and then bind the trait class to that object.
  *
  * `bind(classOf[ServiceLike]).toInstance(SingletonService)`
  *
  * Created by tomas.mccandless on 7/5/17.
  */
trait ServiceLike {
  def getLedgerName: String
}

object SingletonService extends ServiceLike {
  // i don't like declaring this as null. guice will reassign during the injection process, which can be confusing
  @Inject val ledger: Ledger = null

  override def getLedgerName: String = this.ledger.getClass.getName
}
