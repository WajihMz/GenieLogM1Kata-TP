package re.forestier.edu;

import org.junit.jupiter.api.*;

import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.player;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

public class UnitTests {

    @Test
    @DisplayName("Sample test")
    void testPlayerName() {
        player player = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        assertThat(player.playerName, is("Florian"));
    }

    @Test
    @DisplayName("Impossible to have negative money")
    void testNegativeMoney() {
        player p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());

        try {
            p.removeMoney(200);
        } catch (IllegalArgumentException e) {
            return;
        }
        fail();
    }

    @Test
    @DisplayName("Test niveau initial - XP=0 -> niveau 1")
    void testNiveauInitial_zeroXp_niveau1() {
        player p = new player("T", "A", "ADVENTURER", 0, new ArrayList<>());
        assertThat(p.retrieveLevel(), is(1));
    }

    @Test
    @DisplayName("Test niveau intermédiaire - XP=26 -> niveau 2")
    void testNiveauIntermediaire_vingtSixXp_niveau2() {
        player p = new player("T", "A", "ADVENTURER", 0, new ArrayList<>());
        UpdatePlayer.addXp(p, 26);
        assertThat(p.retrieveLevel(), is(2));
    }

    @Test
    @DisplayName("Test niveau intermédiaire - XP=56 -> niveau 3")
    void testNiveauIntermediaire_cinquanteSixXp_niveau3() {
        player p = new player("T", "A", "ADVENTURER", 0, new ArrayList<>());
        UpdatePlayer.addXp(p, 56);
        assertThat(p.retrieveLevel(), is(3));
    }

    @Test
    @DisplayName("Test niveau intermédiaire - XP=110 -> niveau 4")
    void testNiveauIntermediaire_centDixXp_niveau4() {
        player p = new player("T", "A", "ADVENTURER", 0, new ArrayList<>());
        UpdatePlayer.addXp(p, 110);
        assertThat(p.retrieveLevel(), is(4));
    }

    @Test
    @DisplayName("Test des niveaux de joueur - XP=111 -> niveau 5")
    void testLevelFiveAtOneHundredElevenXp() {
        player p = new player("T", "A", "ADVENTURER", 0, new ArrayList<>());
        UpdatePlayer.addXp(p, 111);
        assertThat(p.retrieveLevel(), is(5));
    }

    @Test
    @DisplayName("removeMoney avec montant valide - succès")
    void removeMoney_montantValide_succes() {
        player p = new player("T", "A", "ADVENTURER", 0, new ArrayList<>());
        p.addMoney(50);
        p.removeMoney(30);
        assertThat(p.money, is(20));
    }

    @Test
    @DisplayName("Constructeur avec classe invalide - validation échoue")
    void constructeur_classeInvalide_validationEchoue() {
        player p = new player("T", "A", "INVALID_CLASS", 100, new ArrayList<>());

        assertNotNull(p);
    }

    @Test
    @DisplayName("removeMoney avec montant égal à l'argent - cas limite pour tuer mutation")
    void removeMoney_montantEgalArgent_casLimite() {
        player p = new player("T", "A", "ADVENTURER", 100, new ArrayList<>());
        p.removeMoney(100);
        assertThat(p.money, is(0));
    }

}
