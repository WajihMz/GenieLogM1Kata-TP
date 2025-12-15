package re.forestier.edu.rpg.interfaces;

import java.util.List;

/**
 * Interface pour la gestion de l'inventaire d'un joueur.
 * Définit les opérations liées à l'inventaire.
 */
public interface IInventoryManager {
    List<String> getInventory();
    void addToInventory(String item);
    boolean inventoryContains(String item);
    void clearInventory();
    boolean isInventoryEmpty();
    int getInventorySize();
    String getInventoryItem(int index);
    List<String> getInventoryInternal();
}
