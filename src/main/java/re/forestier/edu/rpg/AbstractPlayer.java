package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe abstraite de base pour tous les types de joueurs.
 * Contient toute la logique commune aux différents types d'avatars.
 */
public abstract class AbstractPlayer {
    protected String playerName;
    protected String avatarName;
    protected Money wallet;
    protected int maximumHealth;
    protected int currentHP;
    protected int xp;
    protected ArrayList<String> inventory;
    
    protected HashMap<STATS, Integer[]> statistics;
    protected String className;
    protected String classDescription;

    public AbstractPlayer(String playerName, String avatarName, int maximumHealth, int money, ArrayList<String> inventory) {
        this.playerName = playerName;
        this.avatarName = avatarName;
        this.maximumHealth = maximumHealth;
        this.currentHP = maximumHealth;
        this.wallet = new Money(money);
        this.inventory = inventory != null ? inventory : new ArrayList<>();
        this.xp = 0;
        this.statistics = new HashMap<>();
        initializeStatistics();
    }

    protected abstract void initializeStatistics();

    public abstract void processEndOfTurn();

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

    public void addXp(int amount) {
        xp += amount;
    }

    public int retrieveLevel() {
        int[] XP_THRESHOLDS = {10, 27, 57, 111};
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

    // Méthodes pour les points de vie
    public int getCurrentHealthPoints() {
        return currentHP;
    }

    public void addCurrentHealthPoints(int amount) {
        currentHP = Math.min(currentHP + amount, maximumHealth);
    }

    public void removeCurrentHealthPoints(int amount) {
        currentHP = Math.max(currentHP - amount, 0);
    }

    public boolean isKO() {
        return currentHP == 0;
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

    public HashMap<STATS, Integer> getStatistics() {
        HashMap<STATS, Integer> currentStats = new HashMap<>();
        for (STATS stat : STATS.values()) {
            int value = getStatistic(stat);
            if (value > 0) {
                currentStats.put(stat, value);
            }
        }
        return currentStats;
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
        for (String item : inventory) {
            sb.append("\n   ").append(item);
        }

        return sb.toString();
    }

    // Getters
    public String getPlayerName() {
        return playerName;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public String getClassName() {
        return className;
    }

    public ArrayList<String> getInventory() {
        return inventory;
    }
}

