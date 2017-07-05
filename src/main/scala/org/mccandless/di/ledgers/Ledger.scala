package org.mccandless.di.ledgers

import org.mccandless.di.transactions.Transaction

/**
  * A ledger is a simple log of financial transactions.
  *
  * Created by tomas.mccandless on 7/5/17.
  */
trait Ledger {

  def log(transaction: Transaction): Unit
}
