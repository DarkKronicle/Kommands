package io.github.darkkronicle.kommands.nodes;

import com.mojang.brigadier.context.CommandContext;
import io.github.darkkronicle.kommands.executors.IExecute;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomClientCommand {

    private List<ArgumentNode<?, ?>> arguments = new ArrayList<>();

    public void addArgument(ArgumentNode<?, ?> node) {
        arguments.add(node);
    }

    public void runCommand(CommandContext<ServerCommandSource> commandContext, List<IExecute> executes) {
        for (IExecute execute : executes) {
            execute.execute(ArgumentNode.formatExecute(commandContext, arguments, execute.getValue()));
        }
    }

}
