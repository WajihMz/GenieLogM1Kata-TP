package re.forestier.edu.rpg.AvatarClasses;

import re.forestier.edu.rpg.AbstractPlayer;
import re.forestier.edu.rpg.ITEM;
import re.forestier.edu.rpg.STATS;

import java.util.ArrayList;

public class Dwarf extends AbstractPlayer {

    public Dwarf(String playerName, String avatarName, int maximumHealth, int money, ArrayList<String> inventory) {
        super(playerName, avatarName, maximumHealth, money, inventory);
    }

    @Override
    protected void initializeStatistics() {
        statistics.put(STATS.ALC, new Integer[]{4, 5, 5, 5, 5, 5, 5, 5, 5, 5});
        statistics.put(STATS.INT, new Integer[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        statistics.put(STATS.ATK, new Integer[]{3, 3, 4, 4, 4, 4, 4, 4, 4, 4});
        statistics.put(STATS.DEF, new Integer[]{0, 1, 1, 2, 2, 2, 2, 2, 2, 2});
        statistics.put(STATS.CHA, new Integer[]{0, 0, 0, 0, 1, 1, 1, 1, 1, 1});
        statistics.put(STATS.VIS, new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    }

    @Override
    protected void applyHealthRegeneration() {
        if (inventoryContains(ITEM.HOLY_ELIXIR.getName())) {
            addCurrentHealthPoints(2);
            return;
        }
        addCurrentHealthPoints(1);
    }
}

