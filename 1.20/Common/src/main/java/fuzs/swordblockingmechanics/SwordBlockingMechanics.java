package fuzs.swordblockingmechanics;

import fuzs.puzzleslib.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.event.v1.entity.living.LivingAttackCallback;
import fuzs.puzzleslib.api.event.v1.entity.living.LivingHurtCallback;
import fuzs.puzzleslib.api.event.v1.entity.living.LivingKnockBackCallback;
import fuzs.puzzleslib.api.event.v1.entity.living.UseItemEvents;
import fuzs.puzzleslib.api.event.v1.entity.player.PlayerInteractEvents;
import fuzs.swordblockingmechanics.config.ClientConfig;
import fuzs.swordblockingmechanics.config.ServerConfig;
import fuzs.swordblockingmechanics.handler.SwordBlockingHandler;
import fuzs.swordblockingmechanics.init.ModRegistry;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwordBlockingMechanics implements ModConstructor {
    public static final String MOD_ID = "swordblockingmechanics";
    public static final String MOD_NAME = "Sword Blocking Mechanics";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID).client(ClientConfig.class).server(ServerConfig.class);

    @Override
    public void onConstructMod() {
        ModRegistry.touch();
        registerHandlers();
    }

    private static void registerHandlers() {
        UseItemEvents.START.register(SwordBlockingHandler::onUseItemStart);
        PlayerInteractEvents.USE_ITEM.register(SwordBlockingHandler::onUseItem);
        LivingAttackCallback.EVENT.register(SwordBlockingHandler::onLivingAttack);
        LivingHurtCallback.EVENT.register(SwordBlockingHandler::onLivingHurt);
        LivingKnockBackCallback.EVENT.register(SwordBlockingHandler::onLivingKnockBack);
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
