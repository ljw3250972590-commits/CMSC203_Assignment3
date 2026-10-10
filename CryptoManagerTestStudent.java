/*
 * Class: CMSC203
 * Instructor: Huseyin Aygun
 * Description: This class contains JUnit tests for the CryptoManager class.
 * Due: 10/10/2026
 * Platform/compiler: Eclipse IDE / Java 25
 * I pledge that I have completed the programming assignment independently.
 * I have not copied the code from a student or any source.
 * I have not given my code to any student.
 * Print your Name here: Jiawei Lyu
 */

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CryptoManagerTestStudent {

    @Test
    public void testIsStringInBounds() {
        assertTrue(CryptoManager.isStringInBounds("JAVA 203"));
        assertFalse(CryptoManager.isStringInBounds("java"));
    }

    @Test
    public void testVigenereEncryptionDecryption() {
        String text = "HELLO CMSC203";
        String key = "CODE";

        String encrypted = CryptoManager.vigenereEncryption(text, key);
        String decrypted = CryptoManager.vigenereDecryption(encrypted, key);

        assertEquals(text, decrypted);
    }

    @Test
    public void testPlayfairEncryptionDecryption() {
        String text = "STUDENT";
        String key = "MATRIX";

        String encrypted = CryptoManager.playfairEncryption(text, key);
        String decrypted = CryptoManager.playfairDecryption(encrypted, key);

        assertEquals(text, decrypted);
    }

    @Test
    public void testCaesarEncryptionDecryption() {
        String text = "ASSIGNMENT 3";
        int key = 5;

        String encrypted = CryptoManager.caesarEncryption(text, key);
        String decrypted = CryptoManager.caesarDecryption(encrypted, key);

        assertEquals(text, decrypted);
    }

    @Test
    public void testCaesarWrapAround() {
        assertEquals(" ", CryptoManager.caesarEncryption("_", 1));
        assertEquals("_", CryptoManager.caesarDecryption(" ", 1));
    }

    @Test
    public void testOutOfBounds() {
        String expected = "The selected string is not in bounds, Try again.";

        assertEquals(expected,
                CryptoManager.vigenereEncryption("hello", "KEY"));

        assertEquals(expected,
                CryptoManager.playfairEncryption("hello", "KEY"));

        assertEquals(expected,
                CryptoManager.caesarEncryption("hello", 3));
    }
}