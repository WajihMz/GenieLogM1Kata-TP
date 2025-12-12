package re.forestier.edu.rpg.AvatarClasses;

import re.forestier.edu.rpg.AbstractPlayer;
import re.forestier.edu.rpg.ITEM;
import re.forestier.edu.rpg.STATS;

import java.util.ArrayList;

public class Archer extends AbstractPlayer {

    public Archer(String playerName, String avatarName, int maximumHealth, int money, ArrayList<String> inventory) {
        super(playerName, avatarName, maximumHealth, money, inventory);
        this.className = "Archer";
    }

    @Override
    protected void initializeStatistics() {
        statistics.put(STATS.INT, new Integer[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        statistics.put(STATS.ATK, new Integer[]{3, 3, 3, 3, 4, 4, 4, 4, 4, 4});
        statistics.put(STATS.CHA, new Integer[]{1, 2, 2, 2, 2, 2, 2, 2, 2, 2});
        statistics.put(STATS.VIS, new Integer[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3});
        statistics.put(STATS.DEF, new Integer[]{0, 1, 1, 2, 2, 2, 2, 2, 2, 2});
        statistics.put(STATS.ALC, new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    }

    @Override
    protected void applyHealthRegeneration() {
        addCurrentHealthPoints(1);
        if (!inventory.contains(ITEM.MAGIC_BOW.getName())) {
            return;
        }
        int bonusHP = currentHP / 8 - 1;
        if (bonusHP > 0) {
            addCurrentHealthPoints(bonusHP);
        }
    }
}

