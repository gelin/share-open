package ru.gelin.kotlin.util.hex

import java.security.MessageDigest
import kotlin.test.assertEquals

/**
 *  Extension functions to format bytes as Hex values.
 */

/**
 *  Set of chars for a half-byte.
 */
private val CHARS = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

/**
 *  Returns the string of two characters representing the HEX value of the byte.
 */
internal fun Byte.toHexString() : String {
    val i = this.toInt()
    val char2 = CHARS[i and 0x0f]
    val char1 = CHARS[i shr 4 and 0x0f]
    return "$char1$char2"
}

/**
 *  Returns the HEX representation of ByteArray data.
 */
internal fun ByteArray.toHexString() : String {
    val builder = StringBuilder()
    for (b in this) {
        builder.append(b.toHexString())
    }
    return builder.toString()
}

private fun testByteToHexString() {
    assertEquals("00", 0x0.toByte().toHexString())
    assertEquals("09", 0x9.toByte().toHexString())
    assertEquals("0a", 0xa.toByte().toHexString())
    assertEquals("0f", 0xf.toByte().toHexString())
    assertEquals("10", 0x10.toByte().toHexString())
    assertEquals("19", 0x19.toByte().toHexString())
    assertEquals("1a", 0x1a.toByte().toHexString())
    assertEquals("1f", 0x1f.toByte().toHexString())
    assertEquals("90", 0x90.toByte().toHexString())
    assertEquals("99", 0x99.toByte().toHexString())
    assertEquals("9a", 0x9a.toByte().toHexString())
    assertEquals("9f", 0x9f.toByte().toHexString())
    assertEquals("a0", 0xa0.toByte().toHexString())
    assertEquals("a9", 0xa9.toByte().toHexString())
    assertEquals("aa", 0xaa.toByte().toHexString())
    assertEquals("ff", 0xff.toByte().toHexString())
}

private fun testByteArrayToHexString() {
    assertEquals("900150983cd24fb0d6963f7d28e17f72", md5("abc").toHexString())
    assertEquals("65a8e27d8879283831b664bd8b7f0ad4", md5("Hello, World!").toHexString())
}

private fun md5(text: String) : ByteArray {
    val digester = MessageDigest.getInstance("MD5")
    return digester.digest(text.toByteArray())
}

/**
 *  Runs tests.
 */
public fun main(args: Array<String>) {
    testByteToHexString()
    testByteArrayToHexString()
}
