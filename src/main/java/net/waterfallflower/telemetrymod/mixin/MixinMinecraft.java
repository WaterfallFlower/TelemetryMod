package net.waterfallflower.telemetrymod.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.Session;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import net.waterfallflower.telemetrymod.TelemetryHelper;
import net.waterfallflower.telemetrymod.ThreadLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Shadow public Session session;

    @Inject(method = "showLevelProgress", at = @At("TAIL"))
    private void loadTelemetry(Level message, String arg1, PlayerBase par3, CallbackInfo ci) {
        ThreadLoader.runThread(() -> {
            if(message != null)
                TelemetryHelper.INSTANCE.send(
                        TelemetryHelper.INSTANCE.build(message, "", session.sessionId)
                );
        }, 10000);
    }
}
