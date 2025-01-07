package fuzs.swordblockingmechanics.mixin.client;

import fuzs.swordblockingmechanics.SwordBlockingMechanics;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
// Adjust as needed

@Mixin(Item.class)
public abstract class ItemMixin {

    private static long lastBlockTime = 0;
    private static final long BLOCK_COOLDOWN_MS = 200;

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void onItemUse(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.getItem() instanceof SwordItem) {
            long currentTime = System.currentTimeMillis();

            if (currentTime - lastBlockTime < BLOCK_COOLDOWN_MS) {
                // Ignore l'input si le cooldown n'est pas écoulé et renvoie une valeur par défaut
                cir.setReturnValue(InteractionResultHolder.pass(stack));
                cir.cancel();
                return;
            }

            lastBlockTime = currentTime;

            if (!player.isUsingItem()) {
                player.startUsingItem(hand);
                cir.setReturnValue(InteractionResultHolder.consume(stack));
                // Pas besoin de cir.cancel() ici si on définit une valeur de retour
            }
        }
    }
}
