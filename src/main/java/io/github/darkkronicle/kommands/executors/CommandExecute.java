package io.github.darkkronicle.kommands.executors;

import lombok.Getter;
import net.minecraft.client.MinecraftClient;

public class CommandExecute implements IExecute {

    @Getter
    private final String value;

    public CommandExecute(String value) {
        this.value = value;
    }

    @Override
    public void execute(String input) {
        MinecraftClient.getInstance().player.sendChatMessage(input);
    }
}
