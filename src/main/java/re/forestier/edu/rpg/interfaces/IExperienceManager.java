package re.forestier.edu.rpg.interfaces;

/**
 * Interface pour la gestion de l'expérience (XP) et des niveaux d'un joueur.
 * Définit les opérations liées à l'expérience et au calcul des niveaux.
 */
public interface IExperienceManager {
    int getXp();
    boolean addXp(int amount);
    int retrieveLevel();
}
