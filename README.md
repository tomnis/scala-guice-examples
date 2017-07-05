# guice-examples

### A few examples of using guice with scala. In particular, we have examples for:
  - basic dependency bindings using a module `configure` method

    ```
    bind(classOf[Ledger]).to(classOf[FileLedger])
    ```
  - dependency bindings using a `Provider` in a `configure` method

    ```
    bind(classOf[Ledger]).toProvider(classOf[MemoryLedgerProvider])
    ```
  - using an `@Provides` method instead of a `Provider`

    ```
    @Provides def provideLedger: Ledger = new FileLedger
    ```
  - injecting a field into a scala object singleton

    ```
    object SingletonService extends ServiceLike {
      // guice will reassign this val during the injection process
      @Inject val ledger: Ledger = null
    }
    ```
  - injecting fields using `@Named` annotation (avoid this approach)

    ```
    case class NamedCredentials @Inject() (@Named("login") user: String, @Named("secure") password: String)
    
    bind(classOf[String]).annotatedWith(Names.named("login")).toInstance(username)
    bind(classOf[String]).annotatedWith(Names.named("secure")).toInstance(password)
    ```
  - injecting fields using custom annotations

    ```
    case class TypesafeCredentials @Inject() (@Login user: String, @Secure password: String)
     
    bind(classOf[String]).annotatedWith(classOf[Login]).toInstance(username)
    bind(classOf[String]).annotatedWith(classOf[Secure]).toInstance(password)
    ```
