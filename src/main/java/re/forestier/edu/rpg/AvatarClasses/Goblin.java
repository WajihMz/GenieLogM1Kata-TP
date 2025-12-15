package re.forestier.edu.rpg.AvatarClasses;

import re.forestier.edu.rpg.AbstractPlayer;
import re.forestier.edu.rpg.STATS;

import java.util.ArrayList;

public class Goblin extends AbstractPlayer {
    private static final double SURVIVAL_THRESHOLD = 0.25;

    public Goblin(String playerName, String avatarName, int maximumHealth, int money, ArrayList<String> inventory) {
        super(playerName, avatarName, maximumHealth, money, inventory);
    }

    @Override
    protected void initializeStatistics() {
        statistics.put(STATS.INT, new Integer[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2});
        statistics.put(STATS.ATK, new Integer[]{2, 3, 3, 3, 4, 4, 4, 4, 4, 4});
        statistics.put(STATS.ALC, new Integer[]{1, 4, 4, 4, 4, 4, 4, 4, 4, 4});
        statistics.put(STATS.VIS, new Integer[]{0, 0, 1, 1, 1, 1, 1, 1, 1, 1});
        statistics.put(STATS.DEF, new Integer[]{0, 0, 0, 1, 2, 2, 2, 2, 2, 2});
        statistics.put(STATS.CHA, new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    }

    @Override
    protected void applyHealthRegeneration() {
        boolean bonusSurvie = getCurrentHP() < getMaximumHealth() * SURVIVAL_THRESHOLD;
        
        addCurrentHealthPoints(1);
        
        if (bonusSurvie) {
            addCurrentHealthPoints(1);
        }
    }
}