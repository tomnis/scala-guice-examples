package org.mccandless.di

import com.google.inject.Inject
import org.mccandless.di.ledgers.Ledger
import org.mccandless.di.transactions.Transaction

/**
  * Created by tomas.mccandless on 7/5/17.
  */
class Bank @Inject() (val ledger: Ledger) {

  /**
    * Models transferring money from `from` to `to`.
    *
    * Just instantiates a transaction and logs it in our ledger.
    *
    * @param from
    * @param to
    * @param cents
    */
  def send(from: String, to: String, cents: Int): Unit = {
    val transaction: Transaction = Transaction(from, to, cents)
    this.ledger.log(transaction)
  }
}
