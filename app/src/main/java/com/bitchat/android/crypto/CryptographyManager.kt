package com.bitchat.android.crypto

import android.util.Log
import com.goterl.lazysodium.LazySodiumAndroid
import com.goterl.lazysodium.SodiumAndroid
import com.goterl.lazysodium.interfaces.Box
import com.goterl.lazysodium.interfaces.Sign
import com.goterl.lazysodium.utils.Key
import com.goterl.lazysodium.utils.KeyPair
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptographyManager @Inject constructor() {
    
    companion object {
        private const val TAG = "CryptographyManager"
    }
    
    private val lazySodium = LazySodiumAndroid(SodiumAndroid())
    
    // Key pairs for encryption and signing
    private var encryptionKeyPair: KeyPair? = null
    private var signingKeyPair: KeyPair? = null
    
    fun initialize(): Boolean {
        return try {
            // Generate or load key pairs
            encryptionKeyPair = lazySodium.cryptoBoxKeypair()
            signingKeyPair = lazySodium.cryptoSignKeypair()
            
            Log.d(TAG, "Cryptography manager initialized successfully")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize cryptography manager", e)
            false
        }
    }
    
    /**
     * Get the public encryption key for this device
     */
    fun getPublicEncryptionKey(): ByteArray? {
        return encryptionKeyPair?.publicKey?.asBytes
    }
    
    /**
     * Get the public signing key for this device
     */
    fun getPublicSigningKey(): ByteArray? {
        return signingKeyPair?.publicKey?.asBytes
    }
    
    /**
     * Encrypt a message for a specific recipient
     */
    fun encryptMessage(message: String, recipientPublicKey: ByteArray): EncryptedMessage? {
        return try {
            val mySecretKey = encryptionKeyPair?.secretKey ?: return null
            val theirPublicKey = Key.fromBytes(recipientPublicKey)
            
            // Generate a random nonce
            val nonce = lazySodium.nonce(Box.NONCEBYTES)
            
            // Encrypt the message
            val ciphertext = ByteArray(message.toByteArray().size + Box.MACBYTES)
            val success = lazySodium.cryptoBoxEasy(
                ciphertext,
                message.toByteArray(),
                message.toByteArray().size.toLong(),
                nonce,
                theirPublicKey.asBytes,
                mySecretKey.asBytes
            )
            
            if (success) {
                EncryptedMessage(
                    ciphertext = ciphertext,
                    nonce = nonce,
                    senderPublicKey = getPublicEncryptionKey()!!
                )
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to encrypt message", e)
            null
        }
    }
    
    /**
     * Decrypt a message from a sender
     */
    fun decryptMessage(encryptedMessage: EncryptedMessage): String? {
        return try {
            val mySecretKey = encryptionKeyPair?.secretKey ?: return null
            val senderPublicKey = Key.fromBytes(encryptedMessage.senderPublicKey)
            
            // Decrypt the message
            val plaintext = ByteArray(encryptedMessage.ciphertext.size - Box.MACBYTES)
            val success = lazySodium.cryptoBoxOpenEasy(
                plaintext,
                encryptedMessage.ciphertext,
                encryptedMessage.ciphertext.size.toLong(),
                encryptedMessage.nonce,
                senderPublicKey.asBytes,
                mySecretKey.asBytes
            )
            
            if (success) {
                String(plaintext, Charsets.UTF_8)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to decrypt message", e)
            null
        }
    }
    
    /**
     * Sign a message to prove authenticity
     */
    fun signMessage(message: String): ByteArray? {
        return try {
            val secretKey = signingKeyPair?.secretKey ?: return null
            val signature = ByteArray(Sign.BYTES)
            
            val success = lazySodium.cryptoSignDetached(
                signature,
                message.toByteArray(),
                message.toByteArray().size.toLong(),
                secretKey.asBytes
            )
            
            if (success) signature else null
        } catch (e: Exception) {
            Log.e(TAG, "Failed to sign message", e)
            null
        }
    }
    
    /**
     * Verify a message signature
     */
    fun verifySignature(message: String, signature: ByteArray, senderPublicKey: ByteArray): Boolean {
        return try {
            val publicKey = Key.fromBytes(senderPublicKey)
            
            lazySodium.cryptoSignVerifyDetached(
                signature,
                message.toByteArray(),
                message.toByteArray().size,
                publicKey.asBytes
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failed to verify signature", e)
            false
        }
    }
    
    /**
     * Generate a shared secret for channel encryption
     */
    fun generateChannelKey(): ByteArray {
        return lazySodium.randomBytesBuf(32) // 256-bit key
    }
    
    /**
     * Encrypt message with a shared channel key
     */
    fun encryptWithChannelKey(message: String, channelKey: ByteArray): ChannelEncryptedMessage? {
        return try {
            val key = Key.fromBytes(channelKey)
            val nonce = lazySodium.nonce(Box.NONCEBYTES)
            
            val ciphertext = ByteArray(message.toByteArray().size + Box.MACBYTES)
            val success = lazySodium.cryptoSecretBoxEasy(
                ciphertext,
                message.toByteArray(),
                message.toByteArray().size.toLong(),
                nonce,
                key.asBytes
            )
            
            if (success) {
                ChannelEncryptedMessage(
                    ciphertext = ciphertext,
                    nonce = nonce
                )
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to encrypt with channel key", e)
            null
        }
    }
    
    /**
     * Decrypt message with a shared channel key
     */
    fun decryptWithChannelKey(encryptedMessage: ChannelEncryptedMessage, channelKey: ByteArray): String? {
        return try {
            val key = Key.fromBytes(channelKey)
            
            val plaintext = ByteArray(encryptedMessage.ciphertext.size - Box.MACBYTES)
            val success = lazySodium.cryptoSecretBoxOpenEasy(
                plaintext,
                encryptedMessage.ciphertext,
                encryptedMessage.ciphertext.size.toLong(),
                encryptedMessage.nonce,
                key.asBytes
            )
            
            if (success) {
                String(plaintext, Charsets.UTF_8)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to decrypt with channel key", e)
            null
        }
    }
    
    /**
     * Generate a secure hash of data
     */
    fun hash(data: String): ByteArray {
        val output = ByteArray(32) // SHA-256 hash size
        lazySodium.cryptoHashSha256(output, data.toByteArray(), data.toByteArray().size.toLong())
        return output
    }
    
    /**
     * Generate a random ID
     */
    fun generateRandomId(): String {
        val randomBytes = lazySodium.randomBytesBuf(16)
        return android.util.Base64.encodeToString(randomBytes, android.util.Base64.URL_SAFE or android.util.Base64.NO_WRAP)
    }
}

/**
 * Represents an encrypted message for peer-to-peer communication
 */
data class EncryptedMessage(
    val ciphertext: ByteArray,
    val nonce: ByteArray,
    val senderPublicKey: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EncryptedMessage

        if (!ciphertext.contentEquals(other.ciphertext)) return false
        if (!nonce.contentEquals(other.nonce)) return false
        if (!senderPublicKey.contentEquals(other.senderPublicKey)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ciphertext.contentHashCode()
        result = 31 * result + nonce.contentHashCode()
        result = 31 * result + senderPublicKey.contentHashCode()
        return result
    }
}

/**
 * Represents an encrypted message for channel communication
 */
data class ChannelEncryptedMessage(
    val ciphertext: ByteArray,
    val nonce: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChannelEncryptedMessage

        if (!ciphertext.contentEquals(other.ciphertext)) return false
        if (!nonce.contentEquals(other.nonce)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ciphertext.contentHashCode()
        result = 31 * result + nonce.contentHashCode()
        return result
    }
}
