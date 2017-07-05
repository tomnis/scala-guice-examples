package org.mccandless.di.credentials

import com.google.inject.Inject
import com.google.inject.name.Named
import org.mccandless.di.annotations.{Login, Secure}

/**
  * An example using `@Named` fields for binding.
  *
  * The compiler can't check that the strings here match what is declared in our module configuration.
  * This approach should be used sparingly.
  *
  * Created by tomas.mccandless on 7/6/17.
  */
case class NamedCredentials @Inject() (@Named("login") user: String, @Named("secure") password: String)


/**
  * An example using custom annotations to denote bindings.
  */
case class TypesafeCredentials @Inject() (@Login user: String, @Secure password: String)
