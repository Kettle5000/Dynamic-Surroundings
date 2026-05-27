package org.orecruncher.dsurround.mixins;

import net.minecraftforge.fml.loading.LoadingModList;
import org.orecruncher.dsurround.Constants;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

/**
 * Skips OpenAL context mixins when Sound Physics Remastered is present so both mods can patch {@code Library#init}.
 */
public final class DSurroundMixinPlugin implements IMixinConfigPlugin {

    private static final String OPEN_AL_CONTEXT_MIXIN =
            "org.orecruncher.dsurround.mixins.audio.MixinSoundLibraryOpenAL";

    private static boolean soundPhysicsRemasteredLoaded;

    @Override
    public void onLoad(String mixinPackage) {
        soundPhysicsRemasteredLoaded = isModLoaded(Constants.MOD_SOUND_PHYSICS_REMASTERED);
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (OPEN_AL_CONTEXT_MIXIN.equals(mixinClassName)) {
            return !soundPhysicsRemasteredLoaded;
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, org.objectweb.asm.tree.ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, org.objectweb.asm.tree.ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    private static boolean isModLoaded(String modId) {
        try {
            var modList = LoadingModList.get();
            if (modList == null) {
                return false;
            }
            return modList.getModFileById(modId) != null;
        } catch (Throwable ignored) {
            return false;
        }
    }
}
