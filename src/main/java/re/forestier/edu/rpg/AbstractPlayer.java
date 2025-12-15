package re.forestier.edu.rpg;

import re.forestier.edu.Exceptions.InventoryException;
import re.forestier.edu.rpg.interfaces.IHealthManager;
import re.forestier.edu.rpg.interfaces.IExperienceManager;
import re.forestier.edu.rpg.interfaces.IInventoryManager;
import re.forestier.edu.rpg.interfaces.IStatisticsManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite de base pour tous les types de joueurs.
 * Contient toute la logique commune aux différents types d'avatars.
 */
public abstract class AbstractPlayer {
    static final double HEALTH_REGEN_THRESHOLD = 0.5;
    
    private String playerName;
    private String avatarName;
    protected Money wallet;
    private int maximumHealth;
    private int currentHP;
    int xp;
    private int capacity;
    
    private GameLogger logger = new ConsoleLogger();
    private final IHealthManager healthManager;
    private final IExperienceManager experienceManager;
    private final IInventoryManager inventoryManager;
    private final IStatisticsManager statisticsManager;

    public AbstractPlayer(String playerName, String avatarName, int maximumHealth, int money, ArrayList<ITEM> inventory) {
        this.playerName = playerName;
        this.avatarName = avatarName;
        this.maximumHealth = maximumHealth;
        this.currentHP = maximumHealth;
        this.wallet = new Money(money);
        this.xp = 0;
        this.capacity = 50;
        this.healthManager = new HealthManager(this);
        this.experienceManager = new ExperienceManager(this);
        this.inventoryManager = new InventoryManager(this, inventory);
        this.statisticsManager = new StatisticsManager(this);
        initializeStatistics();
    }

    protected abstract void initializeStatistics();
    
    protected void putStatistic(STATS stat, Integer[] values) {
        statisticsManager.putStatistic(stat, values);
    }

    public void setLogger(GameLogger logger) {
        this.logger = logger;
    }

    public void processEndOfTurn() {
        if (healthManager.isKO()) {
            logger.log("Le joueur est KO !");
            return;
        }

        if (healthManager.shouldRegenerate()) {
            applyHealthRegeneration();
        }
        
        healthManager.normalizeHealthPoints();
    }

    protected abstract void applyHealthRegeneration();

    // Méthodes pour l'argent
    public int getMoney() {
        return wallet.getAmount();
    }

    public void addMoney(int amount) {
        wallet.addMoney(amount);
    }

    public void removeMoney(int amount) {
        wallet.removeMoney(amount);
    }

    // Méthodes pour l'XP et les niveaux
    public int getXp() {
        return xp;
    }

    public boolean addXp(int amount) {
        return experienceManager.addXp(amount);
    }

    public int retrieveLevel() {
        return experienceManager.retrieveLevel();
    }

    public void addCurrentHealthPoints(int amount) {
        healthManager.addCurrentHealthPoints(amount);
    }

    public void removeCurrentHealthPoints(int amount) {
        healthManager.removeCurrentHealthPoints(amount);
    }

    public boolean isKO() {
        return healthManager.isKO();
    }

    // Getters et setters pour l'encapsulation
    public String getPlayerName() {
        return playerName;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public int getMaximumHealth() {
        return maximumHealth;
    }

    public void setMaximumHealth(int maximumHealth) {
        this.maximumHealth = maximumHealth;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = Math.max(0, Math.min(currentHP, maximumHealth));
    }

    public List<ITEM> getInventory() {
        return inventoryManager.getInventory();
    }

    public void addToInventory(ITEM item) {
        int itemWeight = item.getWeight();
        if (getCurrentInventoryWeight() + itemWeight > capacity) {
            throw new InventoryException("L'objet est trop lourd ! Capacité maximale dépassée.");
        }
        inventoryManager.addToInventory(item);
    }
    
    public int getCurrentInventoryWeight() {
        int totalWeight = 0;
        for (ITEM item : inventoryManager.getInventoryInternal()) {
            totalWeight += item.getWeight();
        }
        return totalWeight;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public int getRemainingCapacity() {
        return capacity - getCurrentInventoryWeight();
    }

    public boolean inventoryContains(ITEM item) {
        return inventoryManager.inventoryContains(item);
    }

    public void clearInventory() {
        inventoryManager.clearInventory();
    }

    public boolean isInventoryEmpty() {
        return inventoryManager.isInventoryEmpty();
    }

    public int getInventorySize() {
        return inventoryManager.getInventorySize();
    }

    public ITEM getInventoryItem(int index) {
        return inventoryManager.getInventoryItem(index);
    }

    // Méthodes pour les statistiques
    public int getStatistic(STATS stat) {
        return statisticsManager.getStatistic(stat);
    }

    // Méthode pour l'affichage
    @Override
    public String toString() {
        return new PlayerFormatter(this).format();
    }

    protected void receiveRandomItem() {
        ITEM randomItem = ITEM.randomItem();
        try {
            addToInventory(randomItem);
        } catch (InventoryException e) {
            // Si l'objet est trop lourd, on ne l'ajoute pas (pas d'exception)
            // Le joueur ne reçoit pas l'objet
        }
    }
    
    public void sell(ITEM item) {
        if (!inventoryContains(item)) {
            throw new InventoryException("L'objet n'est pas dans l'inventaire !");
        }
        
        // Retirer l'objet de l'inventaire
        List<ITEM> inventory = inventoryManager.getInventoryInternal();
        inventory.remove(item);
        
        // Ajouter l'argent
        addMoney(item.getValue());
    }
    
    public void sell(ITEM item, AbstractPlayer buyer) {
        if (!inventoryContains(item)) {
            throw new InventoryException("L'objet n'est pas dans l'inventaire du vendeur !");
        }
        
        if (buyer.getMoney() < item.getValue()) {
            throw new InventoryException("L'acheteur n'a pas assez d'argent !");
        }
        
        // Retirer l'objet de l'inventaire du vendeur
        List<ITEM> inventory = inventoryManager.getInventoryInternal();
        inventory.remove(item);
        
        // Transférer l'argent
        buyer.removeMoney(item.getValue());
        addMoney(item.getValue());
        
        // Ajouter l'objet à l'inventaire de l'acheteur
        buyer.addToInventory(item);
    }
}
