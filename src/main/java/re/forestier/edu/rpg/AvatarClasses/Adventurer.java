package re.forestier.edu.rpg.AvatarClasses;

import re.forestier.edu.rpg.AbstractPlayer;
import re.forestier.edu.rpg.STATS;

import java.util.ArrayList;

public class Adventurer extends AbstractPlayer {

    public Adventurer(String playerName, String avatarName, int maximumHealth, int money, ArrayList<String> inventory) {
        super(playerName, avatarName, maximumHealth, money, inventory);
        this.className = "Adventurer";
        this.classDescription = "A versatile warrior skilled in various combat techniques";
    }

    @Override
    protected void initializeStatistics() {
        statistics.put(STATS.INT, new Integer[]{1, 2, 2, 2, 2, 2, 2, 2, 2, 2});
        statistics.put(STATS.DEF, new Integer[]{1, 1, 1, 1, 3, 4, 4, 4, 4, 4});
        statistics.put(STATS.ATK, new Integer[]{3, 3, 5, 5, 5, 5, 5, 5, 5, 5});
        statistics.put(STATS.CHA, new Integer[]{2, 3, 3, 3, 3, 3, 3, 3, 3, 3});
        statistics.put(STATS.ALC, new Integer[]{0, 0, 1, 1, 1, 1, 1, 1, 1, 1});
        statistics.put(STATS.VIS, new Integer[]{0, 0, 0, 0, 1, 1, 1, 1, 1, 1});
    }

    @Override
    public void processEndOfTurn() {
        if (isKO()) {
            System.out.println("Le joueur est KO !");
            return;
        }

        if (currentHP < maximumHealth / 2) {
            addCurrentHealthPoints(2);
            if (retrieveLevel() < 3) {
                removeCurrentHealthPoints(1);
            }
        } else {
            if (currentHP > maximumHealth) {
                currentHP = maximumHealth;
            }
        }
    }
}

