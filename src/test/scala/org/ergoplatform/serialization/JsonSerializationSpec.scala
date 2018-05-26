package org.ergoplatform.serialization

import org.ergoplatform.modifiers.mempool.TransactionIdsForHeader
import org.ergoplatform.settings.Constants
import org.ergoplatform.utils.ErgoGenerators
import org.scalatest.{Matchers, PropSpec}
import scorex.crypto.encode.Base58
import io.circe.parser._
import io.circe.syntax._
import org.ergoplatform.ErgoBox
import org.scalacheck.Gen
import scorex.core.ModifierId
import sigmastate.Values.TrueLeaf

class JsonSerializationSpec extends PropSpec with ErgoGenerators with Matchers {

  import org.ergoplatform.utils.JsonSerialization._

  property("TransactionIdsForHeader should be converted into json correctly") {
    val modifierId = genBytesList(Constants.ModifierIdSize).sample.get
    val stringId = Base58.encode(modifierId)
    val Right(expected) = parse(s"""{ "ids" : ["$stringId"]}""")
    val data = TransactionIdsForHeader(ModifierId @@ Seq(modifierId))
    //todo: after testnet1 - fix
   // data.asJson shouldEqual expected
  }

  property("AnyoneCanSpendNoncedBox should be converted into json correctly") {
    val value: Long = Gen.chooseNum(1, Long.MaxValue).sample.get
    val box = ErgoBox(value, TrueLeaf)
    val stringId = Base58.encode(box.id)
    val Right(expected) = parse(s"""{"id": "$stringId", "value" : $value}""")
    //todo: after testnet1 - fix
    // box.asJson shouldEqual expected
  }

}
