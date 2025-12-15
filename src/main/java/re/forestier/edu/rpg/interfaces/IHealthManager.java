package re.forestier.edu.rpg.interfaces;

/**
 * Interface pour la gestion de la santé d'un joueur.
 * Définit les opérations liées aux points de vie (HP).
 */
public interface IHealthManager {
    void addCurrentHealthPoints(int amount);
    void removeCurrentHealthPoints(int amount);
    boolean isKO();
    void normalizeHealthPoints();
    boolean shouldRegenerate();
}