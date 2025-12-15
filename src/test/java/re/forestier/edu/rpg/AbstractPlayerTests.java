package re.forestier.edu.rpg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.Exceptions.InventoryException;
import re.forestier.edu.rpg.AvatarClasses.Adventurer;

public class AbstractPlayerTests {
    
   @Test 
   @DisplayName("Le constructeur devrait initialiser un inventory vide quand on passe null")
    void testConstructeurAvecInventoryNull() {
        Adventurer a1 = new Adventurer("A", "B", 100, 100, null);
        
        assertNotNull(a1.getInventory());
    }

    @Test
    @DisplayName("getDescription devrait retourner la description de l'objet")
    void getDescription_RetourneDescription() {
        assertEquals("Prevents surprise attacks", ITEM.LOOKOUT_RING.getDescription());
        assertEquals("Increases XP gained by 100%", ITEM.DRAUPNIR.getDescription());
        assertEquals("Recover your HP", ITEM.HOLY_ELIXIR.getDescription());
        assertEquals("A powerful magical bow", ITEM.MAGIC_BOW.getDescription());
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

    // Tests pour la gestion du poids (capacity)
    
    @Test
    @DisplayName("getCapacity devrait retourner 50 par défaut")
    void getCapacity_Retourne50ParDefaut() {
        Adventurer joueur = new Adventurer("Test", "Avatar", 100, 100, null);
        assertEquals(50, joueur.getCapacity());
    }

    @Test
    @DisplayName("setCapacity devrait modifier la capacité")
    void setCapacity_ModifieLaCapacite() {
        Adventurer joueur = new Adventurer("Test", "Avatar", 100, 100, null);
        joueur.setCapacity(100);
        assertEquals(100, joueur.getCapacity());
    }

    @Test
    @DisplayName("getCurrentInventoryWeight devrait retourner 0 pour un inventaire vide")
    void getCurrentInventoryWeight_InventaireVide_Retourne0() {
        Adventurer joueur = new Adventurer("Test", "Avatar", 100, 100, null);
        assertEquals(0, joueur.getCurrentInventoryWeight());
    }

    @Test
    @DisplayName("getCurrentInventoryWeight devrait calculer correctement le poids total")
    void getCurrentInventoryWeight_CalculePoidsTotal() {
        Adventurer joueur = new Adventurer("Test", "Avatar", 100, 100, null);
        joueur.addToInventory(ITEM.LOOKOUT_RING);
        joueur.addToInventory(ITEM.HOLY_ELIXIR);
        assertEquals(2, joueur.getCurrentInventoryWeight());
    }

    @Test
    @DisplayName("getRemainingCapacity devrait retourner la capacité totale pour un inventaire vide")
    void getRemainingCapacity_InventaireVide_RetourneCapaciteTotale() {
        Adventurer joueur = new Adventurer("Test", "Avatar", 100, 100, null);
        assertEquals(50, joueur.getRemainingCapacity());
    }

    @Test
    @DisplayName("getRemainingCapacity devrait calculer correctement la capacité restante")
    void getRemainingCapacity_CalculeCapaciteRestante() {
        Adventurer joueur = new Adventurer("Test", "Avatar", 100, 100, null);
        joueur.addToInventory(ITEM.LOOKOUT_RING);
        joueur.addToInventory(ITEM.HOLY_ELIXIR);
        assertEquals(48, joueur.getRemainingCapacity());
    }

    @Test
    @DisplayName("addToInventory devrait lancer InventoryException si le poids dépasse la capacité")
    void addToInventory_PoidsDepasseCapacite_LanceInventoryException() {
        Adventurer joueur = new Adventurer("Test", "Avatar", 100, 100, null);
        joueur.setCapacity(2);
        
        joueur.addToInventory(ITEM.LOOKOUT_RING);
        
        InventoryException exception = assertThrows(InventoryException.class, 
            () -> joueur.addToInventory(ITEM.DRAUPNIR));
        assertTrue(exception.getMessage().contains("trop lourd"));
    }

    @Test
    @DisplayName("addToInventory avec ITEM devrait lancer InventoryException si le poids dépasse la capacité")
    void addToInventoryItem_PoidsDepasseCapacite_LanceInventoryException() {
        Adventurer joueur = new Adventurer("Test", "Avatar", 100, 100, null);
        joueur.setCapacity(1);
        
        InventoryException exception = assertThrows(InventoryException.class, 
            () -> joueur.addToInventory(ITEM.DRAUPNIR));
        assertTrue(exception.getMessage().contains("trop lourd"));
    }


    // Tests pour la méthode sell()

    @Test
    @DisplayName("sell devrait lancer InventoryException si l'objet n'est pas dans l'inventaire")
    void sell_ObjetAbsent_LanceInventoryException() {
        Adventurer joueur = new Adventurer("Test", "Avatar", 100, 100, null);
        
        InventoryException exception = assertThrows(InventoryException.class, 
            () -> joueur.sell(ITEM.LOOKOUT_RING));
        assertTrue(exception.getMessage().contains("pas dans l'inventaire"));
    }

    @Test
    @DisplayName("sell devrait retirer l'objet et ajouter l'argent")
    void sell_RetireObjetEtAjouteArgent() {
        Adventurer joueur = new Adventurer("Test", "Avatar", 100, 0, null);
        joueur.addToInventory(ITEM.LOOKOUT_RING); // valeur 50
        
        joueur.sell(ITEM.LOOKOUT_RING);
        
        assertEquals(50, joueur.getMoney());
        assertTrue(!joueur.inventoryContains(ITEM.LOOKOUT_RING));
    }

    @Test
    @DisplayName("sell avec buyer devrait lancer InventoryException si l'objet n'est pas dans l'inventaire du vendeur")
    void sellAvecBuyer_ObjetAbsent_LanceInventoryException() {
        Adventurer vendeur = new Adventurer("Vendeur", "Avatar", 100, 100, null);
        Adventurer acheteur = new Adventurer("Acheteur", "Avatar", 100, 100, null);
        
        InventoryException exception = assertThrows(InventoryException.class, 
            () -> vendeur.sell(ITEM.LOOKOUT_RING, acheteur));
        assertTrue(exception.getMessage().contains("pas dans l'inventaire du vendeur"));
    }

    @Test
    @DisplayName("sell avec buyer devrait lancer InventoryException si l'acheteur n'a pas assez d'argent")
    void sellAvecBuyer_AcheteurPasAssezArgent_LanceInventoryException() {
        Adventurer vendeur = new Adventurer("Vendeur", "Avatar", 100, 100, null);
        Adventurer acheteur = new Adventurer("Acheteur", "Avatar", 100, 10, null);
        
        vendeur.addToInventory(ITEM.DRAUPNIR);
        
        InventoryException exception = assertThrows(InventoryException.class, 
            () -> vendeur.sell(ITEM.DRAUPNIR, acheteur));
        assertTrue(exception.getMessage().contains("pas assez d'argent"));
    }

    @Test
    @DisplayName("sell avec buyer devrait transférer l'objet et l'argent correctement")
    void sellAvecBuyer_TransfereObjetEtArgent() {
        Adventurer vendeur = new Adventurer("Vendeur", "Avatar", 100, 0, null);
        Adventurer acheteur = new Adventurer("Acheteur", "Avatar", 100, 100, null);
        
        vendeur.addToInventory(ITEM.LOOKOUT_RING);
        
        vendeur.sell(ITEM.LOOKOUT_RING, acheteur);
        
        assertEquals(50, vendeur.getMoney());
        assertEquals(50, acheteur.getMoney());
        
        assertTrue(!vendeur.inventoryContains(ITEM.LOOKOUT_RING));
        assertTrue(acheteur.inventoryContains(ITEM.LOOKOUT_RING));
    }

    // Test pour couvrir le catch dans receiveRandomItem()
    
    @Test
    @DisplayName("receiveRandomItem devrait gérer silencieusement les objets trop lourds")
    void receiveRandomItem_ObjetTropLourd_GereSilencieusement() throws Exception {

        Adventurer joueur = new Adventurer("Test", "Avatar", 100, 100, null);
        joueur.setCapacity(1);
        
        joueur.addToInventory(ITEM.LOOKOUT_RING);
                
        java.lang.reflect.Method method = AbstractPlayer.class.getDeclaredMethod("receiveRandomItem");
        method.setAccessible(true);
        
        for (int i = 0; i < 50; i++) {
            method.invoke(joueur);
        }
        
        assertTrue(joueur.getCurrentInventoryWeight() <= joueur.getCapacity());
    }

    @Test
    @DisplayName("isKO devrait retourner false pour un joueur vivant")
    void isKO_JoueurVivant_RetourneFalse() {
        Adventurer joueur = new Adventurer("Test", "Avatar", 100, 100, null);
        assertTrue(!joueur.isKO());
    }

    @Test
    @DisplayName("isInventoryEmpty devrait retourner false pour un inventaire non vide")
    void isInventoryEmpty_InventaireNonVide_RetourneFalse() {
        Adventurer joueur = new Adventurer("Test", "Avatar", 100, 100, null);
        joueur.addToInventory(ITEM.LOOKOUT_RING);
        assertTrue(!joueur.isInventoryEmpty());
    }

    @Test
    @DisplayName("normalizeHealthPoints devrait être appelé après processEndOfTurn")
    void processEndOfTurn_NormaliseHealthPoints() {
        Adventurer joueur = new Adventurer("Test", "Avatar", 100, 100, null);
        // Forcer HP > maxHealth en utilisant la réflexion ou en modifiant directement
        // Pour tester, on peut vérifier que les HP ne dépassent pas maxHealth après processEndOfTurn
        joueur.addCurrentHealthPoints(150); // Dépasser maxHealth
        
        joueur.processEndOfTurn();
        
        assertTrue(joueur.getCurrentHP() <= joueur.getMaximumHealth());
    }

    @Test
    @DisplayName("sell avec buyer ayant exactement le montant nécessaire devrait fonctionner")
    void sellAvecBuyer_MontantExact_Fonctionne() {
        Adventurer vendeur = new Adventurer("Vendeur", "Avatar", 100, 0, null);
        Adventurer acheteur = new Adventurer("Acheteur", "Avatar", 100, 50, null); // Exactement la valeur de LOOKOUT_RING
        
        vendeur.addToInventory(ITEM.LOOKOUT_RING); // valeur 50
        
        vendeur.sell(ITEM.LOOKOUT_RING, acheteur);
        
        assertEquals(50, vendeur.getMoney());
        assertEquals(0, acheteur.getMoney());
        assertTrue(!vendeur.inventoryContains(ITEM.LOOKOUT_RING));
        assertTrue(acheteur.inventoryContains(ITEM.LOOKOUT_RING));
    }

    @Test
    @DisplayName("toMarkdown avec inventaire non vide devrait afficher les objets")
    void toMarkdown_InventaireNonVide_AfficheObjets() {
        Adventurer joueur = new Adventurer("Test", "Avatar", 100, 100, null);
        joueur.addToInventory(ITEM.LOOKOUT_RING);
        joueur.addToInventory(ITEM.HOLY_ELIXIR);
        
        String markdown = joueur.toMarkdown();
        
        assertTrue(markdown.contains("Lookout Ring"));
        assertTrue(markdown.contains("Holy Elixir"));
        assertTrue(!markdown.contains("(vide)"));
    }
    
}
