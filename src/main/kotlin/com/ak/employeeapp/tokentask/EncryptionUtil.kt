package collections

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.apache.tomcat.util.codec.binary.Base64
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionUtil {
    private val key = "Int12345Pqr67890" // 128 bit key
    private val initVector = "CWDRqxozJyhllX1f" //RandomStringUtils.randomAlphanumeric(16);
    private val iv = IvParameterSpec(initVector.toByteArray(charset("UTF-8")))
    private val skeySpec = SecretKeySpec(key.toByteArray(charset("UTF-8")), "AES")

    fun encrypt(value: String): String {
        try {
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)

            val encrypted = cipher.doFinal(value.toByteArray())
            return Base64.encodeBase64String(encrypted)
        } catch (e: Exception) {
            println("caught exception while encrypting value: " + e)
        }
        return value
    }

    fun decrypt(value: String): String {
        try {
            var b = Base64.decodeBase64(value)
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)

            b = cipher.doFinal(b)
            return String(b)
        } catch (e: Exception) {
           println("caught exception while decrypting value: $e")
        }
        return value
    }

    /**
     * Sha256 encryption of String
     */
    fun sha256(base: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(base.toByteArray(charset("UTF-8")))
        val hexString = StringBuilder()
        for (i in hash.indices) {
            val hex = Integer.toHexString(0xff and hash[i].toInt())
            if (hex.length == 1) {
                hexString.append('0')
            }
            hexString.append(hex)
        }
        return hexString.toString()
    }

    /**
     * Generate Sha256 signature
     */
    fun generateSha256Signature(rscPath: String, key: String, method: String, logger: Logger?=null, body: String? = null, queryParams: String? = null): String {
        var signature = "$rscPath:$key:$method"

        if (!body.isNullOrBlank()) {
            signature = "$signature:$body"
        }

        if (!queryParams.isNullOrBlank()) {
            signature = "$signature:$queryParams"
        }
        return sha256(signature)
    }
}