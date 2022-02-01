package io.github.darkkronicle.kommands.nodes;

import com.mojang.brigadier.context.CommandContext;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.parser.NodeProcessor;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.List;

public class CustomClientCommand {

    private List<ArgumentNode<?, ?>> arguments = new ArrayList<>();

    public void addArgument(ArgumentNode<?, ?> node) {
        arguments.add(node);
    }

    public void runCommand(CommandContext<ServerCommandSource> commandContext, NodeProcessor processor, Node node) {
        ArgumentNode.addVariables(commandContext, arguments, processor);
        processor.parse(node);
    }

}
