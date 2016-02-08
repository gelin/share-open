package ru.gelin.kotlin.util.hex

import android.test.AndroidTestCase
import java.security.MessageDigest


class HexTest : AndroidTestCase() {

    fun testByteToHexString() {
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

    internal fun md5(text: String) : ByteArray {
        val digester = MessageDigest.getInstance("MD5")
        return digester.digest(text.toByteArray())
    }

    fun testByteArrayToHexString() {
        assertEquals("900150983cd24fb0d6963f7d28e17f72", md5("abc").toHexString())
        assertEquals("65a8e27d8879283831b664bd8b7f0ad4", md5("Hello, World!").toHexString())
    }

}
