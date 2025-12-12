package re.forestier.edu.rpg;

/**
 * Classe utilitaire temporaire pour gérer les opérations sur AbstractPlayer.
 * 
 * Cette classe sera simplifiée/supprimée prochainement.
 * Les nouvelles classes (Adventurer, Archer, Dwarf) utilisent déjà les méthodes
 * d'AbstractPlayer (addXp(), processEndOfTurn()) et n'ont pas besoin de cette classe.
 */
public class UpdatePlayer {

    public static boolean addXp(AbstractPlayer player, int xp) {
        int currentLevel = player.retrieveLevel();
        player.addXp(xp);
        int newLevel = player.retrieveLevel();
        
        return newLevel != currentLevel;
    }

    public static void majFinDeTour(AbstractPlayer player) {
        player.currentHP = player.currenthealthpoints;
        player.maximumHealth = player.healthpoints;
        
        player.processEndOfTurn();
        
        player.currenthealthpoints = player.currentHP;
        player.healthpoints = player.maximumHealth;
    }
}