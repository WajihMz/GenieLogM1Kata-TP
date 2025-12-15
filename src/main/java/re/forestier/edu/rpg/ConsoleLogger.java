package re.forestier.edu.rpg;

/**
 * Implémentation de GameLogger qui utilise System.out pour l'affichage console.
 */
public class ConsoleLogger implements GameLogger {
    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
