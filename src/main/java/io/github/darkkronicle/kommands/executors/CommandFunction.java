package io.github.darkkronicle.kommands.executors;

import io.github.darkkronicle.Konstruct.functions.Function;
import io.github.darkkronicle.Konstruct.functions.NamedFunction;
import io.github.darkkronicle.Konstruct.nodes.Node;
import io.github.darkkronicle.Konstruct.parser.IntRange;
import io.github.darkkronicle.Konstruct.parser.ParseContext;
import io.github.darkkronicle.Konstruct.parser.Result;
import io.github.darkkronicle.Konstruct.type.NullObject;
import net.minecraft.client.MinecraftClient;

import java.util.List;

public class CommandFunction implements NamedFunction {

    @Override
    public String getName() {
        return "toCommand";
    }

    @Override
    public Result parse(ParseContext context, List<Node> input) {
        MinecraftClient.getInstance().player.sendChatMessage(Function.parseArgument(context, input, 0).getContent().getString());
        return Result.success(new NullObject());
    }

    @Override
    public IntRange getArgumentCount() {
        return IntRange.of(1);
    }

}
