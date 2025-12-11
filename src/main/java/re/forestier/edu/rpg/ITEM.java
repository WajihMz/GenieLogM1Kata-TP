package re.forestier.edu.rpg;

import java.util.Random;

/**
 * Énumération représentant les différents objets disponibles dans le jeu.
 * Chaque objet a un nom, une description, un poids et une valeur.
 */
public enum ITEM {
    LOOKOUT_RING("Lookout Ring", "Prevents surprise attacks", 1, 50),
    SCROLL_OF_STUPIDITY("Scroll of Stupidity", "INT-2 when applied to an enemy", 1, 30),
    DRAUPNIR("Draupnir", "Increases XP gained by 100%", 2, 200),
    MAGIC_CHARM("Magic Charm", "Magic +10 for 5 rounds", 1, 80),
    RUNE_STAFF_OF_CURSE("Rune Staff of Curse", "May burn your ennemies... Or yourself. Who knows?", 3, 150),
    COMBAT_EDGE("Combat Edge", "Well, that's an edge", 2, 40),
    HOLY_ELIXIR("Holy Elixir", "Recover your HP", 1, 100),
    MAGIC_BOW("Magic Bow", "A powerful magical bow", 2, 120);

    private final String name;
    private final String description;
    private final int weight;
    private final int value;

    ITEM(String name, String description, int weight, int value) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    public static ITEM randomItem() {
        ITEM[] items = {LOOKOUT_RING, SCROLL_OF_STUPIDITY, DRAUPNIR, MAGIC_CHARM, 
                       RUNE_STAFF_OF_CURSE, COMBAT_EDGE, HOLY_ELIXIR};
        Random random = new Random();
        return items[random.nextInt(items.length)];
    }

    @Override
    public String toString() {
        return name + " : " + description;
    }
}
