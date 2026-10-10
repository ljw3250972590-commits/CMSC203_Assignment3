/*
 * Class: CMSC203
 * Instructor: Huseyin Aygun
 * Description: This class encrypts and decrypts text using
 * Vigenere, Playfair, and Caesar ciphers.
 * Due: 10/10/2026
 * Platform/compiler: Eclipse IDE / Java 25
 * I pledge that I have completed the programming assignment independently.
 * I have not copied the code from a student or any source.
 * I have not given my code to any student.
 * Print your Name here: Jiawei Lyu
 */


public class CryptoManager { 

    private static final char LOWER_RANGE = ' ';
    private static final char UPPER_RANGE = '_';
    private static final int RANGE = UPPER_RANGE - LOWER_RANGE + 1;
    // Use 64-character matrix (8X8) for Playfair cipher  
    private static final String ALPHABET64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 !\"#$%&'()*+,-./:;<=>?@[\\]^_";

    public static boolean isStringInBounds(String plainText) {
        for (int i = 0; i < plainText.length(); i++) {
            if (!(plainText.charAt(i) >= LOWER_RANGE && plainText.charAt(i) <= UPPER_RANGE)) {
                return false;
            }
        }
        return true;
    }

	/**
	 * Vigenere Cipher is a method of encrypting alphabetic text 
	 * based on the letters of a keyword. It works as below:
	 * 		Choose a keyword (e.g., KEY).
	 * 		Repeat the keyword to match the length of the plaintext.
	 * 		Each letter in the plaintext is shifted by the position of the 
	 * 		corresponding letter in the keyword (A = 0, B = 1, ..., Z = 25).
	 */   

    public static String vigenereEncryption(String plainText, String key) {
        if (!isStringInBounds(plainText) || !isStringInBounds(key)) {
            return "The selected string is not in bounds, Try again.";
        }

        String encryptedText = "";

        for (int i = 0; i < plainText.length(); i++) {
            int plainIndex = ALPHABET64.indexOf(plainText.charAt(i));
            int keyIndex = ALPHABET64.indexOf(key.charAt(i % key.length()));

            int encryptedIndex = (plainIndex + keyIndex) % RANGE;

            encryptedText += ALPHABET64.charAt(encryptedIndex);
        }

        return encryptedText;
    }

    // Vigenere Decryption
    public static String vigenereDecryption(String encryptedText, String key) {
        if (!isStringInBounds(encryptedText) || !isStringInBounds(key)) {
            return "The selected string is not in bounds, Try again.";
        }

        String plainText = "";

        for (int i = 0; i < encryptedText.length(); i++) {
            int encryptedIndex = ALPHABET64.indexOf(encryptedText.charAt(i));
            int keyIndex = ALPHABET64.indexOf(key.charAt(i % key.length()));

            int plainIndex = (encryptedIndex - keyIndex + RANGE) % RANGE;

            plainText += ALPHABET64.charAt(plainIndex);
        }

        return plainText;
    }

	/**
	 * Playfair Cipher encrypts two letters at a time instead of just one.
	 * It works as follows:
	 * A matrix (8X8 in our case) is built using a keyword
	 * Plaintext is split into letter pairs (e.g., ME ET YO UR).
	 * Encryption rules depend on the positions of the letters in the matrix:
	 *     Same row: replace each letter with the one to its right.
	 *     Same column: replace each with the one below.
	 *     Rectangle: replace each letter with the one in its own row but in the column of the other letter in the pair.
	 */    
    
    private static char[][] buildPlayfairMatrix(String key) {
        char[][] matrix = new char[8][8];
        String characters = "";

        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);

            if (characters.indexOf(c) == -1) {
                characters += c;
            }
        }

        for (int i = 0; i < ALPHABET64.length(); i++) {
            char c = ALPHABET64.charAt(i);

            if (characters.indexOf(c) == -1) {
                characters += c;
            }
        }

        int index = 0;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                matrix[row][col] = characters.charAt(index);
                index++;
            }
        }

        return matrix;
    }
    
    private static int[] findPosition(char[][] matrix, char target) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (matrix[row][col] == target) {
                    return new int[] {row, col};
                }
            }
        }

        return null;
    }
    
    public static String playfairEncryption(String plainText, String key) {
        if (!isStringInBounds(plainText) || !isStringInBounds(key)) {
            return "The selected string is not in bounds, Try again.";
        }

        char[][] matrix = buildPlayfairMatrix(key);

        if (plainText.length() % 2 != 0) {
            plainText += "X";
        }

        String encryptedText = "";

        for (int i = 0; i < plainText.length(); i += 2) {
            char first = plainText.charAt(i);
            char second = plainText.charAt(i + 1);

            int[] firstPosition = findPosition(matrix, first);
            int[] secondPosition = findPosition(matrix, second);

            int row1 = firstPosition[0];
            int col1 = firstPosition[1];
            int row2 = secondPosition[0];
            int col2 = secondPosition[1];

            if (row1 == row2) {
                encryptedText += matrix[row1][(col1 + 1) % 8];
                encryptedText += matrix[row2][(col2 + 1) % 8];
            }
            else if (col1 == col2) {
                encryptedText += matrix[(row1 + 1) % 8][col1];
                encryptedText += matrix[(row2 + 1) % 8][col2];
            }
            else {
                encryptedText += matrix[row1][col2];
                encryptedText += matrix[row2][col1];
            }
        }

        return encryptedText;
    }

    // Playfair Decryption
    public static String playfairDecryption(String encryptedText, String key) {
        if (!isStringInBounds(encryptedText) || !isStringInBounds(key)) {
            return "The selected string is not in bounds, Try again.";
        }

        char[][] matrix = buildPlayfairMatrix(key);
        String plainText = "";

        for (int i = 0; i < encryptedText.length(); i += 2) {
            char first = encryptedText.charAt(i);
            char second = encryptedText.charAt(i + 1);

            int[] firstPosition = findPosition(matrix, first);
            int[] secondPosition = findPosition(matrix, second);

            int row1 = firstPosition[0];
            int col1 = firstPosition[1];
            int row2 = secondPosition[0];
            int col2 = secondPosition[1];

            if (row1 == row2) {
                plainText += matrix[row1][(col1 + 7) % 8];
                plainText += matrix[row2][(col2 + 7) % 8];
            }
            else if (col1 == col2) {
                plainText += matrix[(row1 + 7) % 8][col1];
                plainText += matrix[(row2 + 7) % 8][col2];
            }
            else {
                plainText += matrix[row1][col2];
                plainText += matrix[row2][col1];
            }
        }

        if (plainText.endsWith("X")) {
            plainText = plainText.substring(0, plainText.length() - 1);
        }

        return plainText;
    }

    /**
     * Caesar Cipher is a simple substitution cipher that replaces each letter in a message 
     * with a letter some fixed number of positions down the alphabet. 
     * For example, with a shift of 3, 'A' would become 'D', 'B' would become 'E', and so on.
     */    
 
    public static String caesarEncryption(String plainText, int key) {
        if (!isStringInBounds(plainText)) {
            return "The selected string is not in bounds, Try again.";
        }

        String encryptedText = "";

        for (int i = 0; i < plainText.length(); i++) {
            int value = plainText.charAt(i) - LOWER_RANGE;

            int encryptedValue = ((value + key) % RANGE + RANGE) % RANGE;

            encryptedText += (char) (encryptedValue + LOWER_RANGE);
        }

        return encryptedText;
    }

    // Caesar Decryption
    public static String caesarDecryption(String encryptedText, int key) {
        if (!isStringInBounds(encryptedText)) {
            return "The selected string is not in bounds, Try again.";
        }

        String plainText = "";

        for (int i = 0; i < encryptedText.length(); i++) {
            int value = encryptedText.charAt(i) - LOWER_RANGE;

            int plainValue = ((value - key) % RANGE + RANGE) % RANGE;

            plainText += (char) (plainValue + LOWER_RANGE);
        }

        return plainText;
    }

}
