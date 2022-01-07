package io.github.darkkronicle.kommands.nodes;

import com.electronwill.nightconfig.core.Config;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.server.command.ServerCommandSource;

public abstract class Node {

    public abstract CommandNode<ServerCommandSource> getCommandNode();

}
