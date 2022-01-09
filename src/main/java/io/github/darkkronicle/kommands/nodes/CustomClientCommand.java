package io.github.darkkronicle.kommands.nodes;

import com.mojang.brigadier.context.CommandContext;
import io.github.darkkronicle.Konstruct.NodeProcessor;
import io.github.darkkronicle.kommands.executors.IExecute;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.List;

public class CustomClientCommand {

    private List<ArgumentNode<?, ?>> arguments = new ArrayList<>();

    public void addArgument(ArgumentNode<?, ?> node) {
        arguments.add(node);
    }

    public void runCommand(CommandContext<ServerCommandSource> commandContext, NodeProcessor processor, List<IExecute> executes) {
        ArgumentNode.addVariables(commandContext, arguments, processor);
        for (IExecute execute : executes) {
            execute.execute(processor);
        }
    }

}
