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
    private static final int[] XP_THRESHOLDS = {10, 27, 57, 111};
    static final double HEALTH_REGEN_THRESHOLD = 0.5;
    
    private String playerName;
    private String avatarName;
    protected Money wallet;
    private int maximumHealth;
    private int currentHP;
    private int xp;
    private List<String> inventory;
    
    protected Map<STATS, Integer[]> statistics;
    private GameLogger logger = new ConsoleLogger();
    private final HealthManager healthManager;

    public AbstractPlayer(String playerName, String avatarName, int maximumHealth, int money, ArrayList<String> inventory) {
        this.playerName = playerName;
        this.avatarName = avatarName;
        this.maximumHealth = maximumHealth;
        this.currentHP = maximumHealth;
        this.wallet = new Money(money);
        this.inventory = inventory != null ? inventory : new ArrayList<>();
        this.xp = 0;
        this.statistics = new HashMap<>();
        this.healthManager = new HealthManager(this);
        initializeStatistics();
    }

    protected abstract void initializeStatistics();

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
        int currentLevel = retrieveLevel();
        xp += amount;
        int newLevel = retrieveLevel();

        if (newLevel != currentLevel) {
            receiveRandomItem();
            return true;
        }
        return false;
    }

    public int retrieveLevel() {
        int level = 1;
        int i = 0;
        while (i < XP_THRESHOLDS.length) {
            if (xp >= XP_THRESHOLDS[i]) {
                level = i + 2;
            }
            i++;
        }
        return level;
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
        return new ArrayList<>(inventory); // Copie défensive
    }

    public void addToInventory(String item) {
        inventory.add(item);
    }

    public boolean inventoryContains(String item) {
        return inventory.contains(item);
    }

    public void clearInventory() {
        inventory.clear();
    }

    public boolean isInventoryEmpty() {
        return inventory.isEmpty();
    }

    public int getInventorySize() {
        return inventory.size();
    }

    public String getInventoryItem(int index) {
        return inventory.get(index);
    }

    // Méthodes pour les statistiques
    public int getStatistic(STATS stat) {
        if (!statistics.containsKey(stat)) {
            return 0;
        }
        Integer[] statValues = statistics.get(stat);
        int level = retrieveLevel();
        int index = level - 1;
        if (index < 0 || index >= statValues.length) {
            return 0;
        }
        return statValues[index];
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
        for (String item : getInventory()) {
            sb.append("\n   ").append(item);
        }

        return sb.toString();
    }

    protected void receiveRandomItem() {
        addToInventory(ITEM.randomItem().toString());
    }
}
