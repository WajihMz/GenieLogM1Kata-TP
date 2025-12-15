package re.forestier.edu.rpg.AvatarClasses;

import re.forestier.edu.rpg.AbstractPlayer;
import re.forestier.edu.rpg.ITEM;
import re.forestier.edu.rpg.STATS;

import java.util.ArrayList;

public class Adventurer extends AbstractPlayer {

    public Adventurer(String playerName, String avatarName, int maximumHealth, int money, ArrayList<ITEM> inventory) {
        super(playerName, avatarName, maximumHealth, money, inventory);
    }

    @Override
    protected void initializeStatistics() {
        putStatistic(STATS.INT, new Integer[]{1, 2, 2, 2, 2, 2, 2, 2, 2, 2});
        putStatistic(STATS.DEF, new Integer[]{1, 1, 1, 1, 3, 4, 4, 4, 4, 4});
        putStatistic(STATS.ATK, new Integer[]{3, 3, 5, 5, 5, 5, 5, 5, 5, 5});
        putStatistic(STATS.CHA, new Integer[]{2, 3, 3, 3, 3, 3, 3, 3, 3, 3});
        putStatistic(STATS.ALC, new Integer[]{0, 0, 1, 1, 1, 1, 1, 1, 1, 1});
        putStatistic(STATS.VIS, new Integer[]{0, 0, 0, 0, 1, 1, 1, 1, 1, 1});
    }

    @Override
    protected void applyHealthRegeneration() {
        addCurrentHealthPoints(2);
        if (retrieveLevel() >= 3) {
            return;
        }
        removeCurrentHealthPoints(1);
    }
}

