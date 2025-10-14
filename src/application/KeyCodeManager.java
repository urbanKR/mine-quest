package application;

import java.util.Random;

public class KeyCodeManager {
    private String[] keyCodes = new String[3];
    private boolean[] keyCollected = new boolean[3];
    private static final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random random = new Random();

    public KeyCodeManager() {
        generateAllKeyCodes();
    }

    private void generateAllKeyCodes() {
        for (int i = 0; i < 3; i++) {
            keyCodes[i] = generateRandomCode();
        }
    }

    private String generateRandomCode() {
        return String.valueOf(CHARSET.charAt(random.nextInt(CHARSET.length())));
    }

    public void collectKey(int keyIndex) {
        if (keyIndex >= 0 && keyIndex < 3) {
            keyCollected[keyIndex] = true;
        }
    }

    public String getKeyCode(int keyIndex) {
        if (keyIndex >= 0 && keyIndex < 3) {
            return keyCodes[keyIndex];
        }
        return "";
    }

    public String getAllKeyCodes() {
        return keyCodes[0] + keyCodes[1] + keyCodes[2];
    }

    public boolean isKeyCollected(int keyIndex) {
        if (keyIndex >= 0 && keyIndex < 3) {
            return keyCollected[keyIndex];
        }
        return false;
    }

    public int getCollectedKeysCount() {
        int count = 0;
        for (boolean collected : keyCollected) {
            if (collected) count++;
        }
        return count;
    }
}
