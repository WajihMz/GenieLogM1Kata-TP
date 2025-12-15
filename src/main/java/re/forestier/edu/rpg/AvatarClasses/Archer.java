package re.forestier.edu.rpg.AvatarClasses;

import re.forestier.edu.rpg.AbstractPlayer;
import re.forestier.edu.rpg.ITEM;
import re.forestier.edu.rpg.STATS;

import java.util.ArrayList;

public class Archer extends AbstractPlayer {
    private static final int ARCHER_BONUS_DIVISOR = 8;
    private static final int ARCHER_BONUS_SUBTRACT = 1;

    public Archer(String playerName, String avatarName, int maximumHealth, int money, ArrayList<String> inventory) {
        super(playerName, avatarName, maximumHealth, money, inventory);
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
        int bonusHP = getCurrentHP() / ARCHER_BONUS_DIVISOR - ARCHER_BONUS_SUBTRACT;
        if (bonusHP > 0) {
            addCurrentHealthPoints(bonusHP);
        }
    }
}

