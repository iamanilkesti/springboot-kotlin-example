package com.ak.employeeapp.controller

import collections.EncryptionUtil
import collections.mapper
import com.ak.employeeapp.tokentask.IdentityToken
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.web.bind.annotation.*
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit

@RestController
class RefreshIdExpiryController {

    @GetMapping("/refreshtoken")
    fun refreshExpiry(@RequestHeader("test-token")  idToken:String) : String
        {
            /*var idToken = IdentityToken("anilkesti", "guuid", 1, "stc", "b2c",
                12312312, null, null,
                null, false,  "jawwy-tv");*/
            //var encIdToken = collections.encrypt(idToken)
           // println("Encrypted ID token: $encIdToken")
            var testToken = "MmcosM7tWDiOr0QH4PGwwxIBPbJJc548xWb7daNeEAYrWZYyohedF5l9oiLo/pWG0jABfQDv68Qdez28+C5RI5rlVWKUKH5m3ARo7o8zqaB3xVyW8DfiPahKgGI6KjL/NUYy6xcbhnultNUm/MaH4cTxEzLyfwIszRznKI+u06nxZ1m4lBCWsRNb34i0eEBqsHE94ioAPFab8ptwRvZamTBYJe+QxrqEOHZATpaWK+5PZ3wG8Ls3DZdgb47UpMDhyvBM7hHp814A5oh6ue1Xn4LBzsXWW7lM7r84pmh/3mf6jCcjPTZ7HFfYP0gNG8Ar"
            println("Received idtoken: $idToken")
            var idTokenDec = EncryptionUtil.decrypt(idToken)
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

            var updatedIdTokenEnc = collections.encrypt(idTokenObject)
            println("Updated  ID token: $updatedIdTokenEnc")

            println("Are testToken and updated token both same? : " + idTokenDec.equals(updatedIdTokenEnc))
        println("Returning refreshed token: $updatedIdTokenEnc")
        return updatedIdTokenEnc
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
}