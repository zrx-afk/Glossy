package com.example.glossy;

import net.fabricmc.api.ClientModInitializer;

public class GlossyClient implements ClientModInitializer {
    public static final String MOD_ID = "glossy";
    public static GlossyConfig config = new GlossyConfig();

    @Override
    public void onInitializeClient() {
        config = GlossyConfig.load();
        GlossyEffects.register();
    }
}
