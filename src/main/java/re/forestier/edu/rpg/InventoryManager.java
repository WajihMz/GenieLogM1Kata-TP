package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager responsable de la gestion de l'inventaire d'un joueur.
 * Encapsule toute la logique liée à l'inventaire.
 */
class InventoryManager {
    private final AbstractPlayer player;
    private final List<String> inventory;
    
    InventoryManager(AbstractPlayer player, List<String> initialInventory) {
        this.player = player;
        this.inventory = initialInventory != null ? new ArrayList<>(initialInventory) : new ArrayList<>();
    }
    
    List<String> getInventory() {
        return new ArrayList<>(inventory);
    }
    
    void addToInventory(String item) {
        inventory.add(item);
    }
    
    boolean inventoryContains(String item) {
        return inventory.contains(item);
    }
    
    void clearInventory() {
        inventory.clear();
    }
    
    boolean isInventoryEmpty() {
        return inventory.isEmpty();
    }
    
    int getInventorySize() {
        return inventory.size();
    }
    
    String getInventoryItem(int index) {
        return inventory.get(index);
    }
    
    List<String> getInventoryInternal() {
        return inventory;
    }
}
