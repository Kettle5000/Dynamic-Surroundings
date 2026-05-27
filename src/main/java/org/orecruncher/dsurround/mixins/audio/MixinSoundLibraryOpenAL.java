package org.orecruncher.dsurround.mixins.audio;

import com.mojang.blaze3d.audio.Library;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.EXTEfx;
import org.lwjgl.openal.SOFTOutputLimiter;
import org.lwjgl.system.MemoryStack;
import org.orecruncher.dsurround.runtime.audio.AudioUtilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.nio.IntBuffer;

/**
 * Applied only when Sound Physics Remastered is not loaded ({@link org.orecruncher.dsurround.mixins.DSurroundMixinPlugin}).
 */
@Mixin(Library.class)
public class MixinSoundLibraryOpenAL {

    @Redirect(
            method = "init(Ljava/lang/String;Z)V",
            at = @At(value = "INVOKE", target = "Lorg/lwjgl/openal/ALC10;alcCreateContext(JLjava/nio/IntBuffer;)J", remap = false)
    )
    private long dsurround_createContext(long deviceHandle, IntBuffer attrList) {
        if (!AudioUtilities.doEnhancedSounds()) {
            return ALC10.alcCreateContext(deviceHandle, attrList);
        }

        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            IntBuffer buffer = memoryStack.callocInt(5)
                    .put(SOFTOutputLimiter.ALC_OUTPUT_LIMITER_SOFT)
                    .put(ALC10.ALC_TRUE)
                    .put(EXTEfx.ALC_MAX_AUXILIARY_SENDS)
                    .put(4)
                    .put(0)
                    .flip();
            return ALC10.alcCreateContext(deviceHandle, buffer);
        }
    }
}
