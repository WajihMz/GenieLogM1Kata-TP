package re.forestier.edu.rpg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.AvatarClasses.Adventurer;

public class AbstractPlayerTests {
    
   @Test 
   @DisplayName("Le constructeur devrait initialiser un inventory vide quand on passe null")
    void testConstructeurAvecInventoryNull() {
        Adventurer a1 = new Adventurer("A", "B", 100, 100, null);
        
        assertNotNull(a1.getInventory());
    }

    @Test
    @DisplayName("getStatistic devrait retourner 0 quand la statistique n'existe pas dans la map")
    void getStatistic_QuandStatistiqueAbsente_Retourne0() {
        AbstractPlayer joueur = new Adventurer("Test", "Avatar", 100, 100, null) {
            @Override
            protected void initializeStatistics() {
                putStatistic(STATS.ATK, new Integer[]{3, 3, 5, 5, 5, 5, 5, 5, 5, 5});
            }
        };
        int resultat = joueur.getStatistic(STATS.VIS);
        assertEquals(0, resultat);
    }

    @Test
    @DisplayName("getStatistic devrait retourner 0 quand l'index est négatif (niveau 0 ou moins)")
    void getStatistic_QuandIndexNegatif_Retourne0() {
        AbstractPlayer joueur = new Adventurer("Test", "Avatar", 100, 100, null) {
            @Override
            public int retrieveLevel() {
                return 0;
            }
        };        
        int resultat = joueur.getStatistic(STATS.ATK);        
        assertEquals(0, resultat);
    }

    @Test
    @DisplayName("getStatistic devrait retourner 0 quand l'index dépasse la taille du tableau")
    void getStatistic_QuandIndexTropGrand_Retourne0() {
        AbstractPlayer joueur = new Adventurer("Test", "Avatar", 100, 100, null) {
            @Override
            public int retrieveLevel() {
                return 15;
            }
        };
        int resultat = joueur.getStatistic(STATS.ATK);
        assertEquals(0, resultat);
    }
}
