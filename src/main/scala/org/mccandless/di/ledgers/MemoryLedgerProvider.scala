package org.mccandless.di.ledgers

import com.google.inject.Provider

/**
  * An example of using a provider to obtain instances to inject.
  *
  * Created by tomas.mccandless on 7/5/17.
  */
class MemoryLedgerProvider extends Provider[MemoryLedger] {
  override def get(): MemoryLedger = new MemoryLedger
}
