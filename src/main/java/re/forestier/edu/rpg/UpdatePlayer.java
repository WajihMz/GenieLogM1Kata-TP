package re.forestier.edu.rpg;

public class UpdatePlayer {

    public static boolean addXp(player player, int xp) {
        int currentLevel = player.retrieveLevel();
        player.xp += xp;
        int newLevel = player.retrieveLevel();

        if (newLevel != currentLevel) {
            // Player leveled-up!
            // Give a random object
            player.inventory.add(ITEM.randomItem().toString());

            // Mise à jour des abilities pour compatibilité avec Affichage (player.java sera supprimé)
            updateAbilitiesForLevel(player, newLevel);
            
            return true;
        }
        return false;
    }

    private static void updateAbilitiesForLevel(player player, int level) {
        String avatarClass = player.getAvatarClass();
        player.abilities.clear();
        
        if ("ADVENTURER".equals(avatarClass)) {
            player.abilities.put("INT", level >= 2 ? 2 : 1);
            player.abilities.put("DEF", level >= 4 ? (level >= 5 ? 4 : 3) : 1);
            player.abilities.put("ATK", level >= 3 ? 5 : 3);
            player.abilities.put("CHA", level >= 2 ? 3 : 2);
            if (level >= 3) player.abilities.put("ALC", 1);
            if (level >= 5) player.abilities.put("VIS", 1);
        } else if ("ARCHER".equals(avatarClass)) {
            player.abilities.put("INT", 1);
            player.abilities.put("ATK", level >= 5 ? 4 : 3);
            player.abilities.put("CHA", level >= 2 ? 2 : 1);
            player.abilities.put("VIS", 3);
            if (level >= 2) player.abilities.put("DEF", level >= 4 ? 2 : 1);
        } else if ("DWARF".equals(avatarClass)) {
            player.abilities.put("ALC", level >= 2 ? 5 : 4);
            player.abilities.put("INT", 1);
            player.abilities.put("ATK", level >= 3 ? 4 : 3);
            if (level >= 2) player.abilities.put("DEF", level >= 4 ? 2 : 1);
            if (level >= 5) player.abilities.put("CHA", 1);
        }
    }

    // majFinDeTour met à jour les points de vie
    public static void majFinDeTour(player player) {
        if(player.currenthealthpoints == 0) {
            System.out.println("Le joueur est KO !");
            return;
        }

        boolean isAdventurer = "ADVENTURER".equals(player.getAvatarClass());
        boolean isDwarf = "DWARF".equals(player.getAvatarClass());
        boolean isArcher = "ARCHER".equals(player.getAvatarClass());

        if (player.currenthealthpoints < player.healthpoints / 2) {
            if (isAdventurer) {
                player.currenthealthpoints += 2;
                if (player.retrieveLevel() < 3) {
                    player.currenthealthpoints -= 1;
                }


            } else {
                if (isDwarf) {
                    if (player.inventory.contains("Holy Elixir")) {
                        player.currenthealthpoints += 1;
                    }
                    player.currenthealthpoints += 1;
                }
                if (isArcher) {
                        player.currenthealthpoints += 1;
                        if (player.inventory.contains("Magic Bow")) {
                            player.currenthealthpoints += player.currenthealthpoints / 8 - 1;
                        }
                    }
            }
        } else {
            if(player.currenthealthpoints >= player.healthpoints) {
                player.currenthealthpoints = player.healthpoints;
                return;
            }
        }
    }
}