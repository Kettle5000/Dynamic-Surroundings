package org.orecruncher.neoforge;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.orecruncher.dsurround.Client;
import org.orecruncher.dsurround.Constants;
import org.orecruncher.dsurround.gui.overlay.OverlayManager;
import org.orecruncher.dsurround.lib.di.ContainerManager;

@Mod(Constants.MOD_ID)
public final class NeoForgeMod {

    private final Client client;

    public NeoForgeMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::onInitializeClient);
        modBus.addListener(this::onRegisterGuiLayersEvent);

        this.client = new Client();
        this.client.construct();
        this.client.initializeClient();

        if (ModList.get().isLoaded(Constants.CLOTH_CONFIG_NEOFORGE))
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> ModConfigMenu.create(parent)));

    }

    @SubscribeEvent
    public void onRegisterGuiLayersEvent(RegisterGuiOverlaysEvent event) {
        // Add the overlay manager to the render layers of Gui
        OverlayManager dsurround_overlayManager = ContainerManager.resolve(OverlayManager.class);
        event.registerBelowAll(Constants.MOD_ID + "/layer/overlaymanager", (gui, context, partialTick, width, height) -> dsurround_overlayManager.render(context, partialTick));
    }

    @SubscribeEvent
    public void onInitializeClient(FMLClientSetupEvent setupEvent) {
        // Boot the mod
        //this.client.initializeClient();
    }
}
