package re.forestier.edu.Exceptions;

/**
 * Exception levée lorsqu'un joueur n'a pas assez d'argent pour effectuer une opération.
 */
public class NotEnoughMoneyException extends IllegalArgumentException {
    public NotEnoughMoneyException(String message) {
        super(message);
    }
}

