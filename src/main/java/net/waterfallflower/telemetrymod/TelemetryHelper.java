package net.waterfallflower.telemetrymod;

import moresteck.TelemetryData;
import moresteck.TelemetryRequest;
import net.minecraft.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TelemetryHelper {
    public enum OS {
        LINUX,
        SOLARIS,
        WINDOWS,
        MACOS,
        UNKNOWN
    }

    public @NotNull OS getOperatingSystem() {
        String var0 = System.getProperty("os.name").toLowerCase();
        if (var0.contains("win")) {
            return OS.WINDOWS;
        } else if (var0.contains("mac")) {
            return OS.MACOS;
        } else if (var0.contains("solaris")) {
            return OS.SOLARIS;
        } else if (var0.contains("sunos")) {
            return OS.SOLARIS;
        } else if (var0.contains("linux")) {
            return OS.LINUX;
        } else {
            return var0.contains("unix") ? OS.LINUX : OS.UNKNOWN;
        }
    }

    public void send(@NotNull TelemetryData telemetryData) {
        new Thread(() -> new TelemetryRequest(telemetryData).perform()).start();
    }

    public @NotNull TelemetryData build(@NotNull Level level, @Nullable String userID, @Nullable String clientID) {
        return new TelemetryData(
                "b1.7.3", //Minecraft version
                level.isClient ? "remote" : "local", //Gaming type
                getOperatingSystem().name().toLowerCase(), //OS Windows
                System.getProperty("os.name"), //OS name
                System.getProperty("java.version"), //Java version
                "survival", //Gamemode type
                true, //Modded
                userID, //USER ID
                clientID //CLIENT ID
        );
    }

    private TelemetryHelper() {}
    public static TelemetryHelper INSTANCE = new TelemetryHelper();
}
