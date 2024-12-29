package fuzs.swordblockingmechanics.config;

import fuzs.puzzleslib.api.config.v3.Config;
import fuzs.puzzleslib.api.config.v3.ConfigCore;
import fuzs.swordblockingmechanics.handler.SwordBlockingHandler;

public class ServerConfig implements ConfigCore {
    public boolean allowBlockingAndParrying = true;
    public boolean prioritizeOffHand = true;
    public double blockedDamage = 0.15;
    public boolean damageSwordOnBlock = false;
    public double knockbackReduction = 0.2;
    public double protectionArc = 360.0;
    public int parryWindow = 10;
    public double parryKnockbackStrength = 0.5;
    public boolean damageSwordOnParry = false;
    public boolean requireBothHands = false;
    public boolean deflectProjectiles = false;
    public double blockingSlowdown = 0.2;
    public double requiredAttackStrength = 0.0;
}
