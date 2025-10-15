package application;

import java.util.Random;

/**
 * Manages the generation, storage, and tracking of key codes for game progression.
 * Each key has a unique single-character code that can be collected during gameplay.
 */
public class KeyCodeManager {
    private String[] keyCodes = new String[3];
    private boolean[] keyCollected = new boolean[3];
    private static final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random random = new Random();

    /**
     * Constructs a new KeyCodeManager and generates random key codes.
     */
    public KeyCodeManager() {
        generateAllKeyCodes();
    }

    /**
     * Generates unique random codes for all three keys.
     */
    private void generateAllKeyCodes() {
        for (int i = 0; i < 3; i++) {
            keyCodes[i] = generateRandomCode();
        }
    }

    /**
     * Generates a single random character code from the charset.
     * @return a random uppercase letter or digit
     */
    private String generateRandomCode() {
        return String.valueOf(CHARSET.charAt(random.nextInt(CHARSET.length())));
    }

    /**
     * Marks a key as collected.
     * @param keyIndex the index of the key to collect (0-2)
     */
    public void collectKey(int keyIndex) {
        if (keyIndex >= 0 && keyIndex < 3) {
            keyCollected[keyIndex] = true;
        }
    }

    /**
     * Gets the code for a specific key.
     * @param keyIndex the index of the key (0-2)
     * @return the key code, or empty string if index is invalid
     */
    public String getKeyCode(int keyIndex) {
        if (keyIndex >= 0 && keyIndex < 3) {
            return keyCodes[keyIndex];
        }
        return "";
    }

    /**
     * Gets all key codes concatenated into a single string.
     * @return a string containing all three key codes
     */
    public String getAllKeyCodes() {
        return keyCodes[0] + keyCodes[1] + keyCodes[2];
    }

    /**
     * Checks if a specific key has been collected.
     * @param keyIndex the index of the key to check (0-2)
     * @return true if the key has been collected, false otherwise or if index is invalid
     */
    public boolean isKeyCollected(int keyIndex) {
        if (keyIndex >= 0 && keyIndex < 3) {
            return keyCollected[keyIndex];
        }
        return false;
    }

    /**
     * Gets the number of keys that have been collected.
     * @return the count of collected keys (0-3)
     */
    public int getCollectedKeysCount() {
        int count = 0;
        for (boolean collected : keyCollected) {
            if (collected) count++;
        }
        return count;
    }
}