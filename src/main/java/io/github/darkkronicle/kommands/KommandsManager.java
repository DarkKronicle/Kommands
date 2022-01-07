package io.github.darkkronicle.kommands;

import io.github.darkkronicle.kommandlib.CommandManager;
import io.github.darkkronicle.kommandlib.util.InfoUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Formatting;

public class KommandsManager {

    private final static KommandsManager INSTANCE = new KommandsManager();

    private CustomKommandsConfig config;

    public static KommandsManager getInstance() {
        return INSTANCE;
    }

    private KommandsManager() {
        config = new CustomKommandsConfig();
    }

    public void reload() {
        CommandManager.getInstance().unregister(invoker -> invoker.getModId().equals(Kommands.MOD_ID) && !invoker.getName().equals(Kommands.MOD_ID));
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            InfoUtil.sendChatMessage("Reloading Kommands", Formatting.GRAY);
        }
        config.load();
        if (player != null) {
            InfoUtil.sendChatMessage("Done!", Formatting.GREEN);
        }
    }
}
