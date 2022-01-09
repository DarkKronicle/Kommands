package io.github.darkkronicle.kommands.executors;

import io.github.darkkronicle.Konstruct.NodeProcessor;
import io.github.darkkronicle.Konstruct.builder.NodeBuilder;
import io.github.darkkronicle.Konstruct.nodes.Node;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;

public class CommandExecute implements IExecute {

    @Getter
    private final Node node;

    public CommandExecute(String string) {
        this(new NodeBuilder(string).build());
    }

    public CommandExecute(Node node) {
        this.node = node;
    }

    @Override
    public void execute(NodeProcessor input) {
        MinecraftClient.getInstance().player.sendChatMessage(input.parse(node));
    }
}
