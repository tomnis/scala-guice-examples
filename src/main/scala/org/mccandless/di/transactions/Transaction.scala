package org.mccandless.di.transactions

/**
  * A simple case class that models a financial transaction.
  *
  * Created by tomas.mccandless on 7/5/17.
  */
case class Transaction(from: String, to: String, cents: Int, timestamp: Long = System.currentTimeMillis())
