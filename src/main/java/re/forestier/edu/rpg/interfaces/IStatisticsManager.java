package re.forestier.edu.rpg.interfaces;

import re.forestier.edu.rpg.STATS;

/**
 * Interface pour la gestion des statistiques d'un joueur.
 * Définit les opérations liées aux statistiques.
 */
public interface IStatisticsManager {
    void putStatistic(STATS stat, Integer[] values);
    int getStatistic(STATS stat);
}
