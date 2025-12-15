package re.forestier.edu.rpg;

import re.forestier.edu.rpg.interfaces.IHealthManager;

/**
 * Manager responsable de la gestion de la santé d'un joueur.
 * Encapsule toute la logique liée aux points de vie (HP).
 */
class HealthManager implements IHealthManager {
    private final AbstractPlayer player;
    
    HealthManager(AbstractPlayer player) {
        this.player = player;
    }
    
    @Override
    public void addCurrentHealthPoints(int amount) {
        int newHP = Math.min(player.getCurrentHP() + amount, player.getMaximumHealth());
        player.setCurrentHP(newHP);
    }
    
    @Override
    public void removeCurrentHealthPoints(int amount) {
        int newHP = Math.max(player.getCurrentHP() - amount, 0);
        player.setCurrentHP(newHP);
    }
    
    @Override
    public boolean isKO() {
        return player.getCurrentHP() == 0;
    }
    
    @Override
    public void normalizeHealthPoints() {
        int normalized = Math.min(player.getCurrentHP(), player.getMaximumHealth());
        player.setCurrentHP(normalized);
    }
    
    @Override
    public boolean shouldRegenerate() {
        return player.getCurrentHP() < player.getMaximumHealth() * AbstractPlayer.HEALTH_REGEN_THRESHOLD;
    }
}
