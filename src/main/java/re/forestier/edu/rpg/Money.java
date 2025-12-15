package re.forestier.edu.rpg;

import re.forestier.edu.Exceptions.NotEnoughMoneyException;

/**
 * Classe encapsulant la gestion de l'argent d'un joueur.
 * Assure la validation des montants et empêche les valeurs négatives.
 */
public class Money {
    private int amount;

    public Money() {
        this.amount = 0;
    }

    public Money(int initialAmount) {
        if (initialAmount < 0) {
            throw new IllegalArgumentException("Initial amount cannot be negative!");
        }
        this.amount = initialAmount;
    }

    public void addMoney(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add negative amount!");
        }
        this.amount += amount;
    }

    public void removeMoney(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot remove negative amount!");
        }
        if (this.amount - amount < 0) {
            throw new NotEnoughMoneyException("Player can't have a negative money!");
        }
        this.amount -= amount;
    }

    public int getAmount() {
        return this.amount;
    }
}

