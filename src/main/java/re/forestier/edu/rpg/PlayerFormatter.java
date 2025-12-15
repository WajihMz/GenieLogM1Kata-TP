package re.forestier.edu.rpg;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe responsable du formatage de l'affichage d'un joueur.
 * Utilise FreeMarker pour séparer la logique de présentation du code métier.
 */
public class PlayerFormatter {
    private final AbstractPlayer player;
    private static Configuration cfg;
    
    static {
        cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setClassForTemplateLoading(PlayerFormatter.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
    }
    
    public PlayerFormatter(AbstractPlayer player) {
        this.player = player;
    }
    
    public String format() {
        try {
            Template template = cfg.getTemplate("player.ftl");
            
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("avatarName", player.getAvatarName());
            dataModel.put("playerName", player.getPlayerName());
            dataModel.put("level", player.retrieveLevel());
            dataModel.put("xp", player.getXp());
            
            // Préparer les statistiques pour le template
            List<Map<String, Object>> statsList = new ArrayList<>();
            STATS[] displayOrder = {STATS.DEF, STATS.ATK, STATS.CHA, STATS.INT, STATS.ALC, STATS.VIS};
            for (STATS stat : displayOrder) {
                int value = player.getStatistic(stat);
                if (value > 0) {
                    Map<String, Object> statMap = new HashMap<>();
                    statMap.put("name", stat.name());
                    statMap.put("value", value);
                    statsList.add(statMap);
                }
            }
            dataModel.put("stats", statsList);
            
            // Préparer l'inventaire
            dataModel.put("inventory", player.getInventory());
            
            StringWriter writer = new StringWriter();
            template.process(dataModel, writer);
            return writer.toString();
        } catch (IOException | TemplateException e) {
            throw new RuntimeException("Erreur lors du formatage avec FreeMarker", e);
        }
    }
}
