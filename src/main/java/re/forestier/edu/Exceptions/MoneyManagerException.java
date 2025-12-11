package re.forestier.edu.Exceptions;

/**
 * Exception levée lorsqu'une opération sur l'argent échoue.
 */
public class MoneyManagerException extends RuntimeException {
    public MoneyManagerException(String message) {
        super(message);
    }
}

