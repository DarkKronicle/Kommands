package io.github.darkkronicle.kommands;

import io.github.darkkronicle.Konstruct.NodeProcessor;
import io.github.darkkronicle.addons.CalculatorFunction;
import io.github.darkkronicle.addons.GetFunction;
import io.github.darkkronicle.addons.RoundFunction;
import io.github.darkkronicle.kommandlib.CommandManager;
import io.github.darkkronicle.kommandlib.util.InfoUtil;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Formatting;

public class KommandsManager {

    private final static KommandsManager INSTANCE = new KommandsManager();

    private CustomKommandsConfig config;

    @Getter
    private NodeProcessor baseProcessor;

    public static KommandsManager getInstance() {
        return INSTANCE;
    }

    private KommandsManager() {
        config = new CustomKommandsConfig();
        baseProcessor = new NodeProcessor();
    }

    public void setupProcessor() {
        baseProcessor.getFunctions().clear();
        baseProcessor.getVariables().clear();
        CalculatorFunction calc = new CalculatorFunction() ;
        baseProcessor.addFunction(calc.getName(), calc);
        GetFunction get = new GetFunction();
        baseProcessor.addFunction(get.getName(), get);
        RoundFunction round = new RoundFunction();
        baseProcessor.addFunction(round.getName(), round);

        baseProcessor.addVariable("x", () -> String.valueOf(MinecraftClient.getInstance().player.getX()));
        baseProcessor.addVariable("y", () -> String.valueOf(MinecraftClient.getInstance().player.getY()));
        baseProcessor.addVariable("z", () -> String.valueOf(MinecraftClient.getInstance().player.getZ()));
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
