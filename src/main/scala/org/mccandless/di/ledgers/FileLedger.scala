package org.mccandless.di.ledgers

import java.io.{BufferedWriter, File, FileWriter}

import org.mccandless.di.transactions.Transaction

/**
  * Writes transaction logs to a file.
  *
  * Created by tomas.mccandless on 7/5/17.
  */
class FileLedger extends Ledger {

  override def log(transaction: Transaction): Unit = {
    FileLedger.writer.write(transaction.toString)
    FileLedger.writer.flush()
  }
}


/**
  * Holds a handle to a file where transactions will be logged.
  *
  * For simplicity, this file is kept open for the duration of the JVM.
  */
object FileLedger {

  private[this] val output: File = new File("build/transactions.log")

  val writer: BufferedWriter = new BufferedWriter(new FileWriter(this.output))
}