package re.forestier.edu.Exceptions;

/**
 * Exception levée lorsqu'une opération sur l'inventaire échoue.
 */
public class InventoryException extends RuntimeException {
    public InventoryException(String message) {
        super(message);
    }
}

