package com.example.glossy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.fabricmc.loader.api.FabricLoader;

/** Settings stored in .minecraft/config/glossy.json. Edit the file and restart the game. */
public class GlossyConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public boolean sprintTrail = true;
    public boolean hitEffects = true;
    public boolean enchantSparkles = true;
    /** 0 = none, 1 = light, 2 = normal, up to 10. */
    public int particleAmount = 2;

    public int amount() {
        return Math.max(0, Math.min(10, particleAmount));
    }

    public static GlossyConfig load() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve("glossy.json");
        GlossyConfig config = new GlossyConfig();
        try {
            if (Files.exists(path)) {
                GlossyConfig read = GSON.fromJson(Files.readString(path), GlossyConfig.class);
                if (read != null) {
                    config = read;
                }
            }
            Files.writeString(path, GSON.toJson(config));
        } catch (IOException | RuntimeException e) {
            System.err.println("[GLOSSY] Could not read or write config, using defaults: " + e);
        }
        return config;
    }
}
