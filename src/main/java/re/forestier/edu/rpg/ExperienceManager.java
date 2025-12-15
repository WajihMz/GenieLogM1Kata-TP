package re.forestier.edu.rpg;

import re.forestier.edu.rpg.interfaces.IExperienceManager;

/**
 * Manager responsable de la gestion de l'expérience (XP) et des niveaux d'un joueur.
 * Encapsule toute la logique liée à l'expérience et au calcul des niveaux.
 */
class ExperienceManager implements IExperienceManager {
    private static final int[] XP_THRESHOLDS = {10, 27, 57, 111};
    private final AbstractPlayer player;
    
    ExperienceManager(AbstractPlayer player) {
        this.player = player;
    }
    
    @Override
    public int getXp() {
        return player.xp;
    }
    
    @Override
    public boolean addXp(int amount) {
        int currentLevel = retrieveLevel();
        player.xp += amount;
        int newLevel = retrieveLevel();
        
        if (newLevel != currentLevel) {
            player.receiveRandomItem();
            return true;
        }
        return false;
    }
    
    @Override
    public int retrieveLevel() {
        int level = 1;
        int xp = player.xp;
        for (int i = 0; i < XP_THRESHOLDS.length; i++) {
            if (xp >= XP_THRESHOLDS[i]) {
                level = i + 2;
            }
        }
        return level;
    }
}
