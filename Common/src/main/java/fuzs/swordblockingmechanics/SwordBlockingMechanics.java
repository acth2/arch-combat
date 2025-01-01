package fuzs.swordblockingmechanics;

import fuzs.puzzleslib.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.api.event.v1.entity.living.LivingAttackCallback;
import fuzs.puzzleslib.api.event.v1.entity.living.LivingHurtCallback;
import fuzs.puzzleslib.api.event.v1.entity.living.LivingKnockBackCallback;
import fuzs.puzzleslib.api.event.v1.entity.living.UseItemEvents;
import fuzs.puzzleslib.api.event.v1.entity.player.PlayerInteractEvents;
import fuzs.puzzleslib.api.event.v1.entity.player.PlayerTickEvents;
import fuzs.swordblockingmechanics.config.ClientConfig;
import fuzs.swordblockingmechanics.config.ServerConfig;
import fuzs.swordblockingmechanics.handler.SwordBlockingHandler;
import fuzs.swordblockingmechanics.init.ModRegistry;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SwordBlockingMechanics implements ModConstructor {
    public static final String MOD_ID = "swordblockingmechanics2";
    public static final String MOD_NAME = "Sword Blocking Mechanics";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    private static final String SERVER_MOD_NAME = "acth2practice";
    private static final String MOD_FOLDER = "mods";
    private static Boolean isServerEnvironmentCached = null;

    public static boolean isServerEnvironment() {
        if (isServerEnvironmentCached == null) {
            isServerEnvironmentCached = checkServerModPresence();
        }
        return isServerEnvironmentCached;
    }

    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID).client(ClientConfig.class).server(ServerConfig.class);

    private static boolean checkServerModPresence() {
        try {
            Path modsDir = Paths.get(MOD_FOLDER);
            if (Files.exists(modsDir) && Files.isDirectory(modsDir)) {
                File[] files = modsDir.toFile().listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.getName().contains(SERVER_MOD_NAME)) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error checking for server mod: " + e.getMessage());
        }
        return false;
    }

    @Override
    public void onConstructMod() {
        ModRegistry.touch();
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        UseItemEvents.START.register(SwordBlockingHandler::onUseItemStart);
        UseItemEvents.STOP.register(SwordBlockingHandler::onUseItemStop);
        PlayerInteractEvents.USE_ITEM.register(SwordBlockingHandler::onUseItem);
        LivingAttackCallback.EVENT.register(SwordBlockingHandler::onLivingAttack);
        LivingHurtCallback.EVENT.register(SwordBlockingHandler::onLivingHurt);
        LivingKnockBackCallback.EVENT.register(SwordBlockingHandler::onLivingKnockBack);
        PlayerTickEvents.END.register(SwordBlockingHandler::onEndPlayerTick);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocationHelper.fromNamespaceAndPath(MOD_ID, path);
    }
}
