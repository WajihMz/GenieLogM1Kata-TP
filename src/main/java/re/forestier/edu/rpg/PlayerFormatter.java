package re.forestier.edu.rpg;

import java.util.List;

/**
 * Classe responsable du formatage de l'affichage d'un joueur.
 * Extrait la logique de présentation de la classe AbstractPlayer.
 */
public class PlayerFormatter {
    private final AbstractPlayer player;
    
    public PlayerFormatter(AbstractPlayer player) {
        this.player = player;
    }
    
    public String format() {
        StringBuilder sb = new StringBuilder();
        sb.append("Joueur ").append(player.getAvatarName()).append(" joué par ").append(player.getPlayerName());
        sb.append("\nNiveau : ").append(player.retrieveLevel()).append(" (XP totale : ").append(player.getXp()).append(")");
        sb.append("\n\nCapacités :");
        
        STATS[] displayOrder = {STATS.DEF, STATS.ATK, STATS.CHA, STATS.INT, STATS.ALC, STATS.VIS};
        for (STATS stat : displayOrder) {
            int value = player.getStatistic(stat);
            if (value > 0) {
                sb.append("\n   ").append(stat.name()).append(" : ").append(value);
            }
        }
        
        sb.append("\n\nInventaire :");
        List<String> inventory = player.getInventory();
        for (String item : inventory) {
            sb.append("\n   ").append(item);
        }
        
        return sb.toString();
    }
}
