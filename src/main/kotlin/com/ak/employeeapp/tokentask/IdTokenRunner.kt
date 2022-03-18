package collections

import com.ak.employeeapp.tokentask.IdentityToken
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.util.*


private val mapper2 = ObjectMapper()
val mapper = jacksonObjectMapper()
fun main(args:Array<String>){
var idToken = IdentityToken("anilkesti", "guuid", 1, "stc", "b2c",
    12312312, null, null,
    null, false,  "jawwy-tv");

    var encIdToken = encrypt(idToken)
    println("Encrypted ID token: $encIdToken")

    var idTokenDec = EncryptionUtil.decrypt(encIdToken)
    println("Decryptted ID token: $idTokenDec")

    var jsonId = mapper.writeValueAsString(idTokenDec)
   // println("jsonId ID token: $jsonId")

    val idTokenObject:IdentityToken = mapper.readValue(idTokenDec)


    val dateTime = LocalDateTime.now()
    println("Current Time: $dateTime")
    val added10Mins = dateTime.plus(Duration.of(30, ChronoUnit.MINUTES))
    println("Added 10 Mins to Current Time: $added10Mins ")

    val thirtyMinsFromNowInMillis: Long = (added10Mins.toEpochSecond(ZoneOffset.UTC) * 1000
            + added10Mins.get(ChronoField.MILLI_OF_SECOND))

    println("thirtyMinsFromNowInMillis long value: $thirtyMinsFromNowInMillis")
    idTokenObject.expiryMs =thirtyMinsFromNowInMillis
    println("Updated  ID token: $idTokenObject")

    var updatedIdTokenEnc = encrypt(idTokenObject)
    println("Updated  ID token: $updatedIdTokenEnc")

    println("Are both same? : " + encIdToken.equals(updatedIdTokenEnc))
}

fun encrypt(identityToken: IdentityToken): String {


    return try {
        val json = mapper.writeValueAsString(identityToken)

        EncryptionUtil.encrypt(json)
    } catch (e: Exception) {
        println("caught exception while encrypting identity token: " + e)
        "null"
    }
}