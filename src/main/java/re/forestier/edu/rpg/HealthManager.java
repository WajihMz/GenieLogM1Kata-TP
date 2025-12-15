package re.forestier.edu.rpg;

/**
 * Manager responsable de la gestion de la santé d'un joueur.
 * Encapsule toute la logique liée aux points de vie (HP).
 */
class HealthManager {
    private final AbstractPlayer player;
    
    HealthManager(AbstractPlayer player) {
        this.player = player;
    }
    
    void addCurrentHealthPoints(int amount) {
        int newHP = Math.min(player.getCurrentHP() + amount, player.getMaximumHealth());
        player.setCurrentHP(newHP);
    }
    
    void removeCurrentHealthPoints(int amount) {
        int newHP = Math.max(player.getCurrentHP() - amount, 0);
        player.setCurrentHP(newHP);
    }
    
    boolean isKO() {
        return player.getCurrentHP() == 0;
    }
    
    void normalizeHealthPoints() {
        int normalized = Math.min(player.getCurrentHP(), player.getMaximumHealth());
        player.setCurrentHP(normalized);
    }
    
    boolean shouldRegenerate() {
        return player.getCurrentHP() < player.getMaximumHealth() * AbstractPlayer.HEALTH_REGEN_THRESHOLD;
    }
}
