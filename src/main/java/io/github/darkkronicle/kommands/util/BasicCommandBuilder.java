package io.github.darkkronicle.kommands.util;

import com.electronwill.nightconfig.core.Config;
import io.github.darkkronicle.kommandlib.command.CommandInvoker;
import io.github.darkkronicle.kommandlib.invokers.BaseCommandInvoker;
import io.github.darkkronicle.kommands.Kommands;
import io.github.darkkronicle.kommands.nodes.KommandNode;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Optional;

public class BasicCommandBuilder {

    public Optional<CommandInvoker<ServerCommandSource>> build(Config config) {
        KommandNode node = KommandNode.of(config);
        return Optional.of(new BaseCommandInvoker(Kommands.MOD_ID, node.getName(), node.getCommandNode()));
    }

}
