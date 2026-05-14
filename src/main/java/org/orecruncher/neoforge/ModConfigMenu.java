package org.orecruncher.neoforge;

import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.orecruncher.dsurround.Configuration;
import org.orecruncher.dsurround.lib.config.IConfigScreenFactoryProvider;
import org.orecruncher.dsurround.lib.config.IScreenFactory;
import org.orecruncher.dsurround.lib.di.ContainerManager;
import org.orecruncher.dsurround.lib.logging.IModLog;

public class ModConfigMenu {
    public static @Nullable Screen create(@NotNull Screen arg) {
        var factory = acquireFactory();
        if (factory != null)
            return factory.create(arg);
        return null;
    }

    private static IScreenFactory<?> acquireFactory() {
        var logger = ContainerManager.resolve(IModLog.class);
        var provider = ContainerManager.resolve(IConfigScreenFactoryProvider.class);

        logger.info("NeoForge calling to get config screen");
        var factory = provider.getModConfigScreenFactory(Configuration.class);
        return factory.orElse(null);
    }
}
