package re.forestier.edu.rpg;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.AvatarClasses.Adventurer;

import java.util.ArrayList;

public class PlayerFormatterTests {

    @Test
    @DisplayName("format devrait afficher les stats avec valeur 1")
    void format_StatAvecValeur1_AfficheStat() {

        Adventurer joueur = new Adventurer("Test", "Avatar", 100, 100, null);

        PlayerFormatter formatter = new PlayerFormatter(joueur);
        String result = formatter.format();
        
        assertThat(result, containsString("DEF"));
        assertThat(result, containsString("1"));
    }

    @Test
    @DisplayName("format ne devrait pas afficher les stats avec valeur 0")
    void format_StatAvecValeur0_NeAffichePas() {

        Adventurer joueur = new Adventurer("Test", "Avatar", 100, 100, null);

        PlayerFormatter formatter = new PlayerFormatter(joueur);
        String result = formatter.format();
        
        assertThat(result, not(containsString("ALC : 0")));
        assertThat(result, not(containsString("VIS : 0")));
    }

    @Test
    @DisplayName("format devrait lancer RuntimeException si le template est invalide")
    void format_TemplateInvalide_LanceRuntimeException() {
        Adventurer joueur = new Adventurer("Test", "Avatar", 100, 100, null);
        
        try {
            java.lang.reflect.Field cfgField = PlayerFormatter.class.getDeclaredField("cfg");
            cfgField.setAccessible(true);
            freemarker.template.Configuration originalCfg = (freemarker.template.Configuration) cfgField.get(null);
            
            // Créer une nouvelle configuration qui charge depuis un répertoire inexistant
            freemarker.template.Configuration badCfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_30);
            badCfg.setClassForTemplateLoading(PlayerFormatter.class, "/templates-inexistant");
            badCfg.setDefaultEncoding("UTF-8");
            badCfg.setTemplateExceptionHandler(freemarker.template.TemplateExceptionHandler.RETHROW_HANDLER);
            
            cfgField.set(null, badCfg);
            
            PlayerFormatter formatter = new PlayerFormatter(joueur);
            RuntimeException exception = assertThrows(RuntimeException.class, 
                () -> formatter.format());
            assertThat(exception.getMessage(), containsString("Erreur lors du formatage avec FreeMarker"));
            
            cfgField.set(null, originalCfg);
        } catch (Exception e) {
            fail("Erreur lors du test avec réflexion: " + e.getMessage());
        }
    }
}
