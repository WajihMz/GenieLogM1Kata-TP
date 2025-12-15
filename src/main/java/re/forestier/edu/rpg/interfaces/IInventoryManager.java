package re.forestier.edu.rpg.interfaces;

import re.forestier.edu.rpg.ITEM;
import java.util.List;

/**
 * Interface pour la gestion de l'inventaire d'un joueur.
 * Définit les opérations liées à l'inventaire.
 */
public interface IInventoryManager {
    List<ITEM> getInventory();
    void addToInventory(ITEM item);
    boolean inventoryContains(ITEM item);
    void clearInventory();
    boolean isInventoryEmpty();
    int getInventorySize();
    ITEM getInventoryItem(int index);
    List<ITEM> getInventoryInternal();
}
