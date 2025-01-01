package fuzs.swordblockingmechanics.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.ServerSelectionList;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.TransferState;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.server.LanServer;
import net.minecraft.network.chat.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.net.URI;

@Mixin(TitleScreen.class)
public abstract class MenuMixin extends Screen{

    private static final ResourceLocation CUSTOM_LOGO = ResourceLocation.fromNamespaceAndPath("swordblockingmechanics", "textures/gui/logo.png");

    protected MenuMixin(Component title) {
        super(title);
    }

    @Overwrite
    protected void init() {
        this.clearWidgets();

        int y = this.height / 4 + 48;
        int spacingY = 24;

        this.addRenderableWidget(Button.builder(Component.literal("Quit"), button -> {
            System.exit(0);
        }).bounds(this.width / 2 - 100, y + spacingY, 200, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Play"), button -> {
            connectToServer("176.166.184.139", 25565);
        }).bounds(this.width / 2 - 100, y, 200, 20).build());
    }


    @Overwrite
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        float f = 1.0F;
        this.renderPanorama(guiGraphics, partialTick);
        int i = Mth.ceil(f * 255.0F) << 24;
        if ((i & -67108864) != 0) {
            super.render(guiGraphics, mouseX, mouseY, partialTick);

            String string = "Minecraft " + SharedConstants.getCurrentVersion().getName();
            if (this.minecraft.isDemo()) {
                string = string + " Demo";
            } else {
                string = string + ("release".equalsIgnoreCase(this.minecraft.getVersionType()) ? "" : "/" + this.minecraft.getVersionType());
            }

            if (Minecraft.checkModStatus().shouldReportAsModified()) {
                string = string + I18n.get("menu.modded", new Object[0]);
            }

            guiGraphics.drawString(this.font, string, 2, this.height - 10, 16777215 | i);

        }
        this.renderBlurredBackground(partialTick);

        int logoX = this.width / 2 - 32;
        int logoY = this.height / 3 - 60;
        guiGraphics.blit(CUSTOM_LOGO, logoX, logoY, 0, 0, 64, 64, 64, 64);
        
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    private void connectToServer(String address, int port) {
        Minecraft minecraft = Minecraft.getInstance();
        ServerAddress serverAddress = ServerAddress.parseString(address + ":" + port);
        ServerData serverData = new ServerData("Main server", address + ":" + port, ServerData.Type.LAN);
        ConnectScreen.startConnecting(null, minecraft, serverAddress, serverData, false, null);
    }
}
