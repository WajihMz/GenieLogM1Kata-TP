package re.forestier.edu.rpg;

import re.forestier.edu.rpg.interfaces.IStatisticsManager;
import java.util.HashMap;
import java.util.Map;

/**
 * Manager responsable de la gestion des statistiques d'un joueur.
 * Encapsule toute la logique liée aux statistiques.
 */
class StatisticsManager implements IStatisticsManager {
    private final AbstractPlayer player;
    private final Map<STATS, Integer[]> statistics;
    
    StatisticsManager(AbstractPlayer player) {
        this.player = player;
        this.statistics = new HashMap<>();
    }
    
    @Override
    public void putStatistic(STATS stat, Integer[] values) {
        statistics.put(stat, values);
    }
    
    @Override
    public int getStatistic(STATS stat) {
        if (!statistics.containsKey(stat)) {
            return 0;
        }
        Integer[] statValues = statistics.get(stat);
        int level = player.retrieveLevel();
        int index = level - 1;
        if (index < 0 || index >= statValues.length) {
            return 0;
        }
        return statValues[index];
    }
}
