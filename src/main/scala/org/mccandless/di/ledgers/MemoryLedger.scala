package org.mccandless.di.ledgers

import org.mccandless.di.transactions.Transaction

import scala.collection.mutable

/**
  * Writes financial transactions to a mutable in-memory list.
  *
  * Created by tomas.mccandless on 7/5/17.
  */
class MemoryLedger extends Ledger {
  override def log(transaction: Transaction): Unit = {
    MemoryLedger.transactions += transaction
  }
}


object MemoryLedger {

  val transactions: mutable.ListBuffer[Transaction] = mutable.ListBuffer.empty
}
