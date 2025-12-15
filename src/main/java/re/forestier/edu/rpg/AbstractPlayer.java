package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    private GameLogger logger = new ConsoleLogger();
    private final HealthManager healthManager;
    private final ExperienceManager experienceManager;
    private final InventoryManager inventoryManager;
    private final StatisticsManager statisticsManager;

    public AbstractPlayer(String playerName, String avatarName, int maximumHealth, int money, ArrayList<String> inventory) {
        this.playerName = playerName;
        this.avatarName = avatarName;
        this.maximumHealth = maximumHealth;
        this.currentHP = maximumHealth;
        this.wallet = new Money(money);
        this.xp = 0;
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

    public List<String> getInventory() {
        return inventoryManager.getInventory();
    }

    public void addToInventory(String item) {
        inventoryManager.addToInventory(item);
    }

    public boolean inventoryContains(String item) {
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

    public String getInventoryItem(int index) {
        return inventoryManager.getInventoryItem(index);
    }

    // Méthodes pour les statistiques
    public int getStatistic(STATS stat) {
        return statisticsManager.getStatistic(stat);
    }

    // Méthode pour l'affichage
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Joueur ").append(avatarName).append(" joué par ").append(playerName);
        sb.append("\nNiveau : ").append(retrieveLevel()).append(" (XP totale : ").append(xp).append(")");
        sb.append("\n\nCapacités :");
        
        STATS[] displayOrder = {STATS.DEF, STATS.ATK, STATS.CHA, STATS.INT, STATS.ALC, STATS.VIS};
        for (STATS stat : displayOrder) {
            int value = getStatistic(stat);
            if (value > 0) {
                sb.append("\n   ").append(stat.name()).append(" : ").append(value);
            }
        }
        
        sb.append("\n\nInventaire :");
        for (String item : inventoryManager.getInventoryInternal()) {
            sb.append("\n   ").append(item);
        }

        return sb.toString();
    }

    protected void receiveRandomItem() {
        addToInventory(ITEM.randomItem().toString());
    }
}
