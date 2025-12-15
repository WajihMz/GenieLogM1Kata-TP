package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de GameLogger pour les tests.
 * Stocke les messages loggés pour permettre leur vérification.
 */
public class TestLogger implements GameLogger {
    private final List<String> messages = new ArrayList<>();

    @Override
    public void log(String message) {
        messages.add(message);
    }

    public String getLastMessage() {
        if (messages.isEmpty()) {
            return null;
        }
        return messages.get(messages.size() - 1);
    }

    public List<String> getAllMessages() {
        return new ArrayList<>(messages);
    }

    public void clear() {
        messages.clear();
    }
}
