package io.github.darkkronicle.kommands;

import fi.dy.masa.malilib.event.InitializationHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class Kommands implements ClientModInitializer {

    public static final String MOD_ID = "kommands";

    @Override
    public void onInitializeClient() {
        InitializationHandler.getInstance().registerInitializationHandler(new KommandsInitializer());
    }

}