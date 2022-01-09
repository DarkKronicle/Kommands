package io.github.darkkronicle.kommands.nodes;

import com.electronwill.nightconfig.core.Config;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.CommandNode;
import io.github.darkkronicle.Konstruct.NodeProcessor;
import io.github.darkkronicle.kommandlib.util.ArgumentTypes;
import io.github.darkkronicle.kommandlib.util.CommandUtil;
import io.github.darkkronicle.kommands.util.CommandConfigException;
import lombok.Getter;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ArgumentNode<S, T extends ArgumentType<S>> extends Node {

    @Getter
    private final boolean required;

    @Getter
    private final String defaultArgument;

    @Getter
    private final ArgumentTypes.Entry<S, T> argumentType;

    @Getter
    private final String name;

    public ArgumentNode(String name, ArgumentTypes.Entry<S, T> type, boolean required, String defaultArgument) {
        this.argumentType = type;
        this.required = required;
        this.defaultArgument = defaultArgument;
        this.name = name;
    }

    public CommandNode<ServerCommandSource> getCommandNode(Command<ServerCommandSource> command) {
        T argument = argumentType.supplier.get();
        return CommandUtil.argument(getName(), argument).executes(command).build();
    }

    @Override
    public CommandNode<ServerCommandSource> getCommandNode() {
        T argument = argumentType.supplier.get();
        return CommandUtil.argument(getName(), argument).build();
    }

    public String getReplacement(CommandContext<ServerCommandSource> source) {
        Optional<S> optional = CommandUtil.getArgument(source, getName(), getArgumentType().valueClass);
        String replace = getDefaultArgument();
        if (optional.isPresent()) {
            replace = getArgumentType().stringFunction.apply(optional.get(), source.getSource());
        }
        return replace;
    }

    public static ArgumentNode<?, ?> of(Config config) {
        Optional<String> string = config.getOptional("name");
        if (string.isEmpty()) {
            throw new CommandConfigException("No name specified!", config);
        }
        Identifier identifier = new Identifier((String) config.getOptional("type").orElse("string"));
        Optional<ArgumentTypes.Entry<Object, ArgumentType<Object>>> argumentType = ArgumentTypes.getInstance().get(identifier);
        if (argumentType.isEmpty()) {
            throw new CommandConfigException("Invalid argument type! " + identifier, config);
        }
        return new ArgumentNode<>(string.get(), argumentType.get(), (boolean) config.getOptional("required").orElse(false), (String) config.getOptional("default").orElse(""));
    }

    public static void addVariables(CommandContext<ServerCommandSource> source, List<ArgumentNode<?, ?>> arguments, NodeProcessor processor) {
        for (ArgumentNode<?, ?> argument : arguments) {
            String replace = argument.getReplacement(source);
            processor.addVariable(argument.getName(), replace);
        }
    }

}
