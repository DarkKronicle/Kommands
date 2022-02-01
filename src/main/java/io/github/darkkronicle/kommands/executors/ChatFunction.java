package io.github.darkkronicle.kommands.executors;

import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.functions.NamedFunction;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.parser.IntRange;
import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;
import io.github.darkkronicle.Konstruct.type.NullObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;

import java.util.List;

public class ChatFunction implements NamedFunction {

    @Override
    public String getName() {
        return "toChat";
    }

    @Override
    public Result parse(ParseContext context, List<Node> input) {
        MinecraftClient.getInstance().player.sendMessage(new LiteralText(Function.parseArgument(context, input, 0).getContent().getString()), false);
        return Result.success(new NullObject());
    }

    @Override
    public IntRange getArgumentCount() {
        return IntRange.of(1);
    }

}
