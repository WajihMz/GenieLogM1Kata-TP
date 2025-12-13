package re.forestier.edu.rpg;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class MoneyTests {

    @Test
    @DisplayName("Constructeur par défaut - initialise à 0")
    void constructeurParDefaut_initialiseAZero() {
        Money money = new Money();
        assertThat(money.getAmount(), is(0));
    }

    @Test
    @DisplayName("Constructeur avec montant positif - initialise correctement")
    void constructeur_montantPositif_initialiseCorrectement() {
        Money money = new Money(100);
        assertThat(money.getAmount(), is(100));
    }

    @Test
    @DisplayName("Constructeur avec montant initial négatif - lance exception")
    void constructeur_montantInitialNegatif_lanceException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Money(-100)
        );
        assertThat(exception.getMessage(), containsString("Initial amount cannot be negative"));
    }

    @Test
    @DisplayName("addMoney avec montant positif - ajoute correctement")
    void addMoney_montantPositif_ajouteCorrectement() {
        Money money = new Money(50);
        money.addMoney(30);
        assertThat(money.getAmount(), is(80));
    }

    @Test
    @DisplayName("addMoney avec montant négatif - lance exception")
    void addMoney_montantNegatif_lanceException() {
        Money money = new Money(100);
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> money.addMoney(-10)
        );
        assertThat(exception.getMessage(), containsString("Cannot add negative amount"));
    }

    @Test
    @DisplayName("removeMoney avec montant positif valide - retire correctement")
    void removeMoney_montantPositifValide_retireCorrectement() {
        Money money = new Money(100);
        money.removeMoney(30);
        assertThat(money.getAmount(), is(70));
    }

    @Test
    @DisplayName("removeMoney avec montant égal à l'argent - retire tout")
    void removeMoney_montantEgalArgent_retireTout() {
        Money money = new Money(100);
        money.removeMoney(100);
        assertThat(money.getAmount(), is(0));
    }

    @Test
    @DisplayName("removeMoney avec montant supérieur à l'argent - lance exception")
    void removeMoney_montantSuperieurArgent_lanceException() {
        Money money = new Money(100);
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> money.removeMoney(150)
        );
        assertThat(exception.getMessage(), containsString("Player can't have a negative money"));
    }

    @Test
    @DisplayName("removeMoney avec montant négatif - lance exception")
    void removeMoney_montantNegatif_lanceException() {
        Money money = new Money(100);
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> money.removeMoney(-10)
        );
        assertThat(exception.getMessage(), containsString("Cannot remove negative amount"));
    }

    @Test
    @DisplayName("getAmount retourne le montant actuel")
    void getAmount_retourneMontantActuel() {
        Money money = new Money(50);
        assertThat(money.getAmount(), is(50));
        
        money.addMoney(25);
        assertThat(money.getAmount(), is(75));
    }
}

