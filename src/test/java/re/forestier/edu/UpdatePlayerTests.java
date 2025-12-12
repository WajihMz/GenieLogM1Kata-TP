package re.forestier.edu;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.AvatarClasses.Adventurer;
import re.forestier.edu.rpg.AvatarClasses.Archer;
import re.forestier.edu.rpg.AvatarClasses.Dwarf;

public class UpdatePlayerTests {
    
    @Test
    @DisplayName("addXp doit retourner true quand le joueur monte de niveau")
    void addXp_quandJoueurMonteNiveau_retourneTrue() {
        Adventurer p = new Adventurer("T", "A", 200, 0, new ArrayList<>());
        boolean result = p.addXp(10);
        assertThat(result, is(true));
        assertThat(p.getXp(), is(10));
        assertThat(p.retrieveLevel(), is(2));
    }

    @Test
    @DisplayName("addXp doit retourner false quand le joueur ne monte pas de niveau")
    void addXp_quandJoueurNeMontePasNiveau_retourneFalse() {
        Adventurer p = new Adventurer("T", "A", 200, 0, new ArrayList<>());
        boolean result = p.addXp( 5);
        assertThat(result, is(false));
        assertThat(p.getXp(), is(5));
        assertThat(p.retrieveLevel(), is(1));
    }

    @Test
    @DisplayName("addXp doit ajouter un objet aléatoire quand le joueur monte de niveau")
    void addXp_quandJoueurMonteNiveau_ajouteObjetAleatoire() {
        Adventurer p = new Adventurer("T", "A", 200, 0, new ArrayList<>());
        p.addXp( 10);
        assertThat(p.inventory.size(), is(1));
        assertThat(p.inventory.get(0), is(notNullValue()));
    }

    @Test
    @DisplayName("majFinDeTour doit gérer le cas où le joueur est KO")
    void majFinDeTour_quandJoueurKO_afficheMessageKO() {
        Adventurer p = new Adventurer("T", "A", 200, 20, new ArrayList<>());
        p.currenthealthpoints = 0;
        
        PrintStream originalOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        try {
            p.processEndOfTurn();
            String printed = out.toString();
            assertThat(printed, containsString("Le joueur est KO !"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("majFinDeTour pour ADVENTURER niveau < 3 doit réduire les HP")
    void majFinDeTour_adventurerNiveauBas_reduitHP() {
        Adventurer p = new Adventurer("T", "A", 200, 100, new ArrayList<>());
        p.healthpoints = 40;
        p.currenthealthpoints = 10;
        
        p.addXp( 5);
        
        p.processEndOfTurn();
        assertThat(p.currenthealthpoints, is(11));
    }

    @Test
    @DisplayName("majFinDeTour DWARF HP < 50% sans Holy Elixir - bonus simple")
    void majFinDeTour_dwarfSansHolyElixir_bonusSimple() {
        Dwarf p = new Dwarf("T", "A", 200, 100, new ArrayList<>());
        p.healthpoints = 40;
        p.currenthealthpoints = 10;
        
        p.processEndOfTurn();
        assertThat(p.currenthealthpoints, is(11));
    }

    @Test
    @DisplayName("majFinDeTour DWARF HP < 50% avec Holy Elixir - double bonus")
    void majFinDeTour_dwarfAvecHolyElixir_doubleBonus() {
        Dwarf p = new Dwarf("T", "A", 200, 100, new ArrayList<>());
        p.healthpoints = 40;
        p.currenthealthpoints = 10;
        p.inventory.add("Holy Elixir");
        
        p.processEndOfTurn();
        assertThat(p.currenthealthpoints, is(12));
    }

    @Test
    @DisplayName("majFinDeTour ARCHER HP < 50% sans Magic Bow - bonus simple")
    void majFinDeTour_archerSansMagicBow_bonusSimple() {
        Archer p = new Archer("T", "A", 200, 100, new ArrayList<>());
        p.healthpoints = 40;
        p.currenthealthpoints = 16;
        
        p.processEndOfTurn();
        assertThat(p.currenthealthpoints, is(17));
    }

    @Test
    @DisplayName("majFinDeTour ARCHER HP < 50% avec Magic Bow - bonus calculé")
    void majFinDeTour_archerAvecMagicBow_bonusCalcule() {
        Archer p = new Archer("T", "A", 200, 100, new ArrayList<>());
        p.healthpoints = 40;
        p.currenthealthpoints = 16;
        p.inventory.add("Magic Bow");
        
        p.processEndOfTurn();
        assertThat(p.currenthealthpoints, is(18));
    }

    @Test
    @DisplayName("majFinDeTour ADVENTURER niveau >= 3 - pas de réduction HP")
    void majFinDeTour_adventurerNiveauEleve_pasReductionHP() {
        Adventurer p = new Adventurer("T", "A", 200, 100, new ArrayList<>());
        p.healthpoints = 40;
        p.currenthealthpoints = 10;
        p.addXp( 27);
        
        p.processEndOfTurn();
        assertThat(p.currenthealthpoints, is(12));
    }

    @Test
    @DisplayName("majFinDeTour HP >= 50% et < max - pas de bonus")
    void majFinDeTour_hpSuperieurMoitie_pasBonus() {
        Adventurer p = new Adventurer("T", "A", 200, 100, new ArrayList<>());
        p.healthpoints = 40;
        p.currenthealthpoints = 25;
        
        p.processEndOfTurn();
        assertThat(p.currenthealthpoints, is(25));
    }

    @Test
    @DisplayName("majFinDeTour HP >= max - plafonné au maximum")
    void majFinDeTour_hpSuperieurMax_plafonneMax() {
        Adventurer p = new Adventurer("T", "A", 200, 100, new ArrayList<>());
        p.healthpoints = 40;
        p.currenthealthpoints = 45;
        
        p.processEndOfTurn();
        assertThat(p.currenthealthpoints, is(40));
    }

    @Test
    @DisplayName("majFinDeTour HP exactement à la moitié - pas de bonus")
    void majFinDeTour_hpExactementMoitie_pasBonus() {
        Adventurer p = new Adventurer("T", "A", 200, 100, new ArrayList<>());
        p.healthpoints = 40;
        p.currenthealthpoints = 20;
        
        p.processEndOfTurn();
        assertThat(p.currenthealthpoints, is(20));
    }

}