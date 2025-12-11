package re.forestier.edu.rpg;

import java.util.ArrayList;
import java.util.HashMap;

public class player {
    public String playerName;
    public String Avatar_name;
    private String AvatarClass;

    public Integer money;


    public int level;
    public int healthpoints;
    public int currenthealthpoints;
    protected int xp;


    public HashMap<String, Integer> abilities;
    public ArrayList<String> inventory;
    public player(String playerName, String avatar_name, String avatarClass, int money, ArrayList<String> inventory) {
        if (!avatarClass.equals("ARCHER") && !avatarClass.equals("ADVENTURER") && !avatarClass.equals("DWARF") ) {
            return;
        }

        this.playerName = playerName;
        Avatar_name = avatar_name;
        AvatarClass = avatarClass;
        this.money = Integer.valueOf(money);
        this.inventory = inventory;
        
        // Initialisation des abilities pour niveau 1 (pour compatibilité avec Affichage)
        // Cette classe sera supprimée, les nouvelles classes utilisent AbstractPlayer.getStatistic()
        this.abilities = new HashMap<>();
        initializeAbilitiesForLevel1(avatarClass);
    }

    public String getAvatarClass () {
        return AvatarClass;
    }

    public void removeMoney(int amount) throws IllegalArgumentException {
        if (money - amount < 0) {
            throw new IllegalArgumentException("Player can't have a negative money!");
        }

        money = Integer.parseInt(money.toString()) - amount;
    }
    public void addMoney(int amount) {
        var value = Integer.valueOf(amount);
        money = money + (value != null ? value : 0);
    }
    public int retrieveLevel() {
        // (lvl-1) * 10 + round((lvl * xplvl-1)/4)
        HashMap<Integer, Integer> levels = new HashMap<>();
        levels.put(2,10); // 1*10 + ((2*0)/4)
        levels.put(3,27); // 2*10 + ((3*10)/4)
        levels.put(4,57); // 3*10 + ((4*27)/4)
        levels.put(5,111); // 4*10 + ((5*57)/4)
        //TODO : ajouter les prochains niveaux

        if (xp < levels.get(2)) {
            return 1;
        }
        else if (xp < levels.get(3)) {return 2;
        }
        if (xp < levels.get(4)) {
            return 3;
        }
        if (xp < levels.get(5)) return 4;
        return 5;
    }

    public int getXp() {
        return this.xp;
    }

    private void initializeAbilitiesForLevel1(String avatarClass) {
        if ("ADVENTURER".equals(avatarClass)) {
            abilities.put("INT", 1);
            abilities.put("DEF", 1);
            abilities.put("ATK", 3);
            abilities.put("CHA", 2);
        } else if ("ARCHER".equals(avatarClass)) {
            abilities.put("INT", 1);
            abilities.put("ATK", 3);
            abilities.put("CHA", 1);
            abilities.put("VIS", 3);
        } else if ("DWARF".equals(avatarClass)) {
            abilities.put("ALC", 4);
            abilities.put("INT", 1);
            abilities.put("ATK", 3);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Joueur ").append(Avatar_name).append(" joué par ").append(playerName);
        sb.append("\nNiveau : ").append(retrieveLevel()).append(" (XP totale : ").append(xp).append(")");
        sb.append("\n\nCapacités :");
        abilities.forEach((name, level) -> {
            sb.append("\n   ").append(name).append(" : ").append(level);
        });
        sb.append("\n\nInventaire :");
        inventory.forEach(item -> {
            sb.append("\n   ").append(item);
        });
        return sb.toString();
    }

    /*
    Ингредиенты:
        Для теста:

            250 г муки
            125 г сливочного масла (холодное)
            70 г сахара
            1 яйцо
            1 щепотка соли
     */

}