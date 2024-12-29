package fuzs.swordblockingmechanics.mixin.client;

import com.mojang.blaze3d.platform.InputConstants;
import fuzs.swordblockingmechanics.client.handler.KeyTransferer;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.Arrays;

@Mixin(Options.class)
public class OptionsMixin {
    @Shadow
    @Final
    @Mutable
    private KeyMapping[] keyMappings;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void constructorMixin(Minecraft minecraft, File gameDirectory, CallbackInfo ci) {
        KeyTransferer.KEY_BLOCK = new KeyMapping(
                "swordblockingmechanics.key.block",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_APOSTROPHE,
                "swordblockingmechanics.key.category"
        );
        KeyMapping[] newKeyMappings = Arrays.copyOf(this.keyMappings, this.keyMappings.length + 1);
        newKeyMappings[newKeyMappings.length - 1] = KeyTransferer.KEY_BLOCK;

        this.keyMappings = newKeyMappings;
    }
}
