package re.forestier.edu.rpg;

/**
 * Interface pour la gestion des logs du jeu.
 * Permet de découpler l'affichage de la logique métier.
 */
public interface GameLogger {
    void log(String message);
}
