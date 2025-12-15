package re.forestier.edu.rpg;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.AvatarClasses.Adventurer;
import re.forestier.edu.rpg.interfaces.IExperienceManager;

import java.util.ArrayList;
import java.lang.reflect.Field;

public class ExperienceManagerTests {

    @Test
    @DisplayName("getXp devrait retourner l'XP du joueur")
    void getXp_RetourneXpDuJoueur() throws Exception {
        Adventurer joueur = new Adventurer("Test", "Avatar", 100, 100, null);
        joueur.addXp(25);
        
        // Accéder à experienceManager via réflexion
        Field experienceManagerField = AbstractPlayer.class.getDeclaredField("experienceManager");
        experienceManagerField.setAccessible(true);
        IExperienceManager experienceManager = (IExperienceManager) experienceManagerField.get(joueur);
        
        int xp = experienceManager.getXp();
        assertEquals(25, xp);
    }
}
