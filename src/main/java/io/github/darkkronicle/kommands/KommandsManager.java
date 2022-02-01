package io.github.darkkronicle.kommands;

import io.github.darkkronicle.Konstruct.functions.GetFunction;
import io.github.darkkronicle.Konstruct.functions.Variable;
import io.github.darkkronicle.Konstruct.parser.NodeProcessor;
import io.github.darkkronicle.Konstruct.type.DoubleObject;
import io.github.darkkronicle.addons.*;
import io.github.darkkronicle.kommandlib.CommandManager;
import io.github.darkkronicle.kommandlib.util.InfoUtil;
import io.github.darkkronicle.kommands.executors.ActionbarFunction;
import io.github.darkkronicle.kommands.executors.ChatFunction;
import io.github.darkkronicle.kommands.executors.CommandFunction;
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
        baseProcessor.addFunction(new CalculatorFunction());
        baseProcessor.addFunction(new OwOFunction());
        baseProcessor.addFunction(new IsMatchFunction());
        baseProcessor.addFunction(new ReplaceFunction());
        baseProcessor.addFunction(new RoundFunction());
        baseProcessor.addFunction(new RandomFunction());
        baseProcessor.addFunction(new RoundFunction());
        baseProcessor.addFunction(new ChatFunction());
        baseProcessor.addFunction(new CommandFunction());
        baseProcessor.addFunction(new ActionbarFunction());

        baseProcessor.addVariable("x", () -> new DoubleObject(MinecraftClient.getInstance().player.getX()));
        baseProcessor.addVariable("y", () -> new DoubleObject(MinecraftClient.getInstance().player.getY()));
        baseProcessor.addVariable("z", () -> new DoubleObject(MinecraftClient.getInstance().player.getZ()));
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
