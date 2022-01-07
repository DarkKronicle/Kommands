package io.github.darkkronicle.kommands.nodes;

import com.electronwill.nightconfig.core.Config;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import io.github.darkkronicle.kommandlib.command.CommandInvoker;
import io.github.darkkronicle.kommandlib.util.CommandUtil;
import io.github.darkkronicle.kommands.executors.CommandExecute;
import io.github.darkkronicle.kommands.executors.IExecute;
import io.github.darkkronicle.kommands.util.CommandConfigException;
import lombok.Getter;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class KommandNode extends Node {

    @Getter
    private final String name;
    @Getter
    private final List<ArgumentNode<?, ?>> argumentNodes;
    @Getter
    private final List<Node> children;
    @Getter
    private final List<IExecute> executes;

    public KommandNode(String name, List<ArgumentNode<?, ?>> argumentNodes, List<Node> children, List<IExecute> executes) {
        this.name = name;
        this.argumentNodes = argumentNodes;
        this.children = children;
        this.executes = executes;
    }

    private CommandNode<ServerCommandSource> getArguments(CustomClientCommand command, Command<ServerCommandSource> runnable, ArgumentNode<?, ?>[] nodes) {
        command.addArgument(nodes[0]);
        if (nodes.length == 1) {
            return nodes[0].getCommandNode(runnable);
        }
        CommandNode<ServerCommandSource> node;
        if (!nodes[1].isRequired()) {
            node = nodes[0].getCommandNode(runnable);
        } else {
            node = nodes[0].getCommandNode();
        }
        node.addChild(getArguments(command, runnable, Arrays.copyOfRange(nodes, 1, nodes.length)));
        return node;
    }

    @Override
    public CommandNode<ServerCommandSource> getCommandNode() {
        ArgumentBuilder<ServerCommandSource, ?> builder = CommandUtil.literal(name);
        CustomClientCommand command = new CustomClientCommand();
        Command<ServerCommandSource> runnable = context -> {
                command.runCommand(context, executes);
                return 0;
        };
        if (argumentNodes.size() > 0) {
            if (!argumentNodes.get(0).isRequired()) {
                builder.executes(runnable);
            }
            builder.then(getArguments(command, runnable, argumentNodes.toArray(new ArgumentNode[0])));
        } else {
            builder.executes(runnable);
        }
        for (Node child : children) {
            builder.then(child.getCommandNode());
        }

        return builder.build();
    }

    public static KommandNode of(Config config) {
        List<ArgumentNode<?, ?>> argumentNodes = new ArrayList<>();
        List<Node> children = new ArrayList<>();
        List<IExecute> onExecute = new ArrayList<>();
        Optional<String> name = config.getOptional("name");
        if (name.isEmpty()) {
            throw new CommandConfigException("No name is specified!", config);
        }
        Optional<List<Config>> executes = config.getOptional("executes");
        if (executes.isEmpty()) {
            throw new CommandConfigException("No executes are specified!", config);
        }
        for (Config executeConfig : executes.get()) {
            if (executeConfig.getOptional("type").orElse("command").equals("command")) {
                onExecute.add(new CommandExecute((String) executeConfig.getOptional("value").orElse("")));
            } else {
                throw new CommandConfigException("Invalid execute setup!", config);
            }
        }

        Optional<List<Config>> arguments = config.getOptional("arguments");
        if (arguments.isPresent()) {
            for (Config argumentConfig : arguments.get()) {
                argumentNodes.add(ArgumentNode.of(argumentConfig));
            }
        }
        Optional<List<Config>> subcommands = config.getOptional("subcommands");
        if (subcommands.isPresent()) {
            for (Config subcommandConfig : subcommands.get()) {
                argumentNodes.add(ArgumentNode.of(subcommandConfig));
            }
        }
        return new KommandNode(name.get(), argumentNodes, children, onExecute);
    }
}
