package re.forestier.edu.AvatarClasses;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.STATS;
import re.forestier.edu.rpg.AvatarClasses.Goblin;

import static org.junit.jupiter.api.Assertions.*;

public class GoblinTests {

    @Test
    @DisplayName("Gobelin niveau 1 devrait avoir INT=2, ATK=2, ALC=1")
    void gobelin_Niveau1_StatsCorrectes() {
        Goblin gobelin = new Goblin("Test", "Goblin", 100, 50, null);
        
        assertEquals(1, gobelin.retrieveLevel());
        assertEquals(2, gobelin.getStatistic(STATS.INT));
        assertEquals(2, gobelin.getStatistic(STATS.ATK));
        assertEquals(1, gobelin.getStatistic(STATS.ALC));
        assertEquals(0, gobelin.getStatistic(STATS.VIS));
        assertEquals(0, gobelin.getStatistic(STATS.DEF));
        assertEquals(0, gobelin.getStatistic(STATS.CHA));
    }

    @Test
    @DisplayName("Gobelin niveau 2 devrait avoir ATK=3, ALC=4")
    void gobelin_Niveau2_StatsCorrectes() {
        Goblin gobelin = new Goblin("Test", "Goblin", 100, 50, null);
        gobelin.addXp(10);
        
        assertEquals(2, gobelin.retrieveLevel());
        assertEquals(2, gobelin.getStatistic(STATS.INT));
        assertEquals(3, gobelin.getStatistic(STATS.ATK));
        assertEquals(4, gobelin.getStatistic(STATS.ALC));
        assertEquals(0, gobelin.getStatistic(STATS.VIS));
        assertEquals(0, gobelin.getStatistic(STATS.DEF));
        assertEquals(0, gobelin.getStatistic(STATS.CHA));
    }

    @Test
    @DisplayName("Gobelin niveau 3 devrait avoir VIS=1")
    void gobelin_Niveau3_StatsCorrectes() {
        Goblin gobelin = new Goblin("Test", "Goblin", 100, 50, null);
        gobelin.addXp(27);
        
        assertEquals(3, gobelin.retrieveLevel());
        assertEquals(2, gobelin.getStatistic(STATS.INT));
        assertEquals(3, gobelin.getStatistic(STATS.ATK));
        assertEquals(4, gobelin.getStatistic(STATS.ALC));
        assertEquals(1, gobelin.getStatistic(STATS.VIS));
        assertEquals(0, gobelin.getStatistic(STATS.DEF));
        assertEquals(0, gobelin.getStatistic(STATS.CHA));
    }

    @Test
    @DisplayName("Gobelin niveau 4 devrait avoir DEF=1")
    void gobelin_Niveau4_StatsCorrectes() {
        Goblin gobelin = new Goblin("Test", "Goblin", 100, 50, null);
        gobelin.addXp(57);
        
        assertEquals(4, gobelin.retrieveLevel());
        assertEquals(2, gobelin.getStatistic(STATS.INT));
        assertEquals(3, gobelin.getStatistic(STATS.ATK));
        assertEquals(4, gobelin.getStatistic(STATS.ALC));
        assertEquals(1, gobelin.getStatistic(STATS.VIS));
        assertEquals(1, gobelin.getStatistic(STATS.DEF));
        assertEquals(0, gobelin.getStatistic(STATS.CHA));
    }

    @Test
    @DisplayName("Gobelin niveau 5 devrait avoir DEF=2, ATK=4")
    void gobelin_Niveau5_StatsCorrectes() {
        Goblin gobelin = new Goblin("Test", "Goblin", 100, 50, null);
        gobelin.addXp(111);
        
        assertEquals(5, gobelin.retrieveLevel());
        assertEquals(2, gobelin.getStatistic(STATS.INT));
        assertEquals(4, gobelin.getStatistic(STATS.ATK));
        assertEquals(4, gobelin.getStatistic(STATS.ALC));
        assertEquals(1, gobelin.getStatistic(STATS.VIS));
        assertEquals(2, gobelin.getStatistic(STATS.DEF));
        assertEquals(0, gobelin.getStatistic(STATS.CHA));
    }

    // ========== Tests de la régénération de santé ==========

    @Test
    @DisplayName("Gobelin avec HP >= 50% ne devrait pas régénérer")
    void gobelin_HpSuperieur50Pourcent_PasDeRegeneration() {
        Goblin gobelin = new Goblin("Test", "Goblin", 100, 50, null);
        gobelin.removeCurrentHealthPoints(40);
        
        gobelin.processEndOfTurn();
        
        assertEquals(60, gobelin.getCurrentHP());
    }

    @Test
    @DisplayName("Gobelin avec HP < 50% devrait régénérer 1 HP")
    void gobelin_HpInferieur50Pourcent_Regenere1HP() {
        Goblin gobelin = new Goblin("Test", "Goblin", 100, 50, null);
        gobelin.removeCurrentHealthPoints(60);
        
        gobelin.processEndOfTurn();
        
        assertEquals(41, gobelin.getCurrentHP());
    }

    @Test
    @DisplayName("Gobelin avec HP < 25% devrait régénérer 2 HP (bonus survie)")
    void gobelin_HpInferieur25Pourcent_Regenere2HP() {
        Goblin gobelin = new Goblin("Test", "Goblin", 100, 50, null);
        gobelin.removeCurrentHealthPoints(80);
        
        gobelin.processEndOfTurn();
        
        assertEquals(22, gobelin.getCurrentHP());
    }

    @Test
    @DisplayName("Gobelin KO ne devrait pas régénérer")
    void gobelin_QuandKO_PasDeRegeneration() {
        Goblin gobelin = new Goblin("Test", "Goblin", 100, 50, null);
        gobelin.removeCurrentHealthPoints(100);
        
        gobelin.processEndOfTurn();
        
        assertEquals(0, gobelin.getCurrentHP());
        assertTrue(gobelin.isKO());
    }

    @Test
    @DisplayName("Gobelin régénération ne devrait pas dépasser HP max")
    void gobelin_RegenerationPlafonnee_AuMaximum() {
        Goblin gobelin = new Goblin("Test", "Goblin", 100, 50, null);
        gobelin.removeCurrentHealthPoints(51);
        
        gobelin.processEndOfTurn();
        
        assertEquals(50, gobelin.getCurrentHP());
    }

    @Test
    @DisplayName("Gobelin avec HP < 25% et régénération plafonnée au max")
    void gobelin_BonusSurvie_PlafonneeAuMaximum() {
        Goblin gobelin = new Goblin("Test", "Goblin", 100, 50, null);
        gobelin.removeCurrentHealthPoints(76);
        
        gobelin.processEndOfTurn();
        
        assertEquals(26, gobelin.getCurrentHP());
    }

    @Test
    @DisplayName("Gobelin avec HP exactement à 25% du max ne devrait pas avoir le bonus survie")
    void gobelin_HpExactement25Pourcent_PasDeBonusSurvie() {
        Goblin gobelin = new Goblin("Test", "Goblin", 100, 50, null);
        gobelin.removeCurrentHealthPoints(75);
        
        gobelin.processEndOfTurn();
        
        assertEquals(26, gobelin.getCurrentHP());
    }

    // ========== Tests de création et initialisation ==========

    @Test
    @DisplayName("Création d'un Gobelin avec inventaire null devrait initialiser un inventaire vide")
    void gobelin_AvecInventaireNull_InitialiseInventaireVide() {
        Goblin gobelin = new Goblin("Test", "Goblin", 100, 50, null);
        
        assertNotNull(gobelin.inventory);
        assertTrue(gobelin.inventory.isEmpty());
    }

    @Test
    @DisplayName("Création d'un Gobelin devrait initialiser correctement tous les champs")
    void gobelin_Creation_InitialiseCorrectement() {
        Goblin gobelin = new Goblin("Joueur1", "Snaga", 150, 75, null);
        
        assertEquals("Joueur1", gobelin.getPlayerName());
        assertEquals("Snaga", gobelin.getAvatarName());
        assertEquals(150, gobelin.getMaximumHealth());
        assertEquals(150, gobelin.getCurrentHP());
        assertEquals(75, gobelin.getMoney());
        assertEquals(0, gobelin.getXp());
        assertEquals(1, gobelin.retrieveLevel());
    }

    // ========== Tests de gain d'XP et de niveau ==========

    @Test
    @DisplayName("Gobelin devrait gagner un objet en montant de niveau")
    void gobelin_QuandMonteNiveau_GagneObjet() {
        Goblin gobelin = new Goblin("Test", "Goblin", 100, 50, null);
        int tailleInitiale = gobelin.inventory.size();
        
        boolean aMonteNiveau = gobelin.addXp(10);
        
        assertTrue(aMonteNiveau);
        assertEquals(2, gobelin.retrieveLevel());
        assertEquals(tailleInitiale + 1, gobelin.inventory.size());
    }

    @Test
    @DisplayName("Gobelin ne devrait pas monter de niveau avec peu d'XP")
    void gobelin_AvecPeuXP_NeMontePasNiveau() {
        Goblin gobelin = new Goblin("Test", "Goblin", 100, 50, null);
        
        boolean aMonteNiveau = gobelin.addXp(5);
        
        assertFalse(aMonteNiveau);
        assertEquals(1, gobelin.retrieveLevel());
    }

    // ========== Tests d'affichage ==========

    @Test
    @DisplayName("toString() devrait afficher correctement un Gobelin")
    void gobelin_ToString_AfficheCorrectement() {
        Goblin gobelin = new Goblin("Joueur1", "Goblin le Rusé", 100, 50, null);
        
        String affichage = gobelin.toString();
        
        assertTrue(affichage.contains("Goblin le Rusé"));
        assertTrue(affichage.contains("Joueur1"));
        assertTrue(affichage.contains("Niveau : 1"));
        assertTrue(affichage.contains("INT : 2"));
        assertTrue(affichage.contains("ATK : 2"));
        assertTrue(affichage.contains("ALC : 1"));
    }
}