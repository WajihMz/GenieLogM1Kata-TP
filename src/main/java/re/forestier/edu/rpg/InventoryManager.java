package re.forestier.edu.rpg;

import re.forestier.edu.rpg.interfaces.IInventoryManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager responsable de la gestion de l'inventaire d'un joueur.
 * Encapsule toute la logique liée à l'inventaire.
 */
class InventoryManager implements IInventoryManager {
    private final List<ITEM> inventory;
    
    InventoryManager(AbstractPlayer player, List<ITEM> initialInventory) {
        this.inventory = initialInventory != null ? new ArrayList<>(initialInventory) : new ArrayList<>();
    }
    
    @Override
    public List<ITEM> getInventory() {
        return new ArrayList<>(inventory);
    }
    
    @Override
    public void addToInventory(ITEM item) {
        inventory.add(item);
    }
    
    @Override
    public boolean inventoryContains(ITEM item) {
        return inventory.contains(item);
    }
    
    @Override
    public void clearInventory() {
        inventory.clear();
    }
    
    @Override
    public boolean isInventoryEmpty() {
        return inventory.isEmpty();
    }
    
    @Override
    public int getInventorySize() {
        return inventory.size();
    }
    
    @Override
    public ITEM getInventoryItem(int index) {
        return inventory.get(index);
    }
    
    @Override
    public List<ITEM> getInventoryInternal() {
        return inventory;
    }
}
