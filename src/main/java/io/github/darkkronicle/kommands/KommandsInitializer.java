package io.github.darkkronicle.kommands;

import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import io.github.darkkronicle.kommandlib.CommandManager;
import io.github.darkkronicle.kommandlib.command.ClientCommand;
import io.github.darkkronicle.kommandlib.invokers.BaseCommandInvoker;
import io.github.darkkronicle.kommandlib.util.CommandUtil;
import io.github.darkkronicle.kommandlib.util.InfoUtil;

public class KommandsInitializer implements IInitializationHandler {

    @Override
    public void registerModHandlers() {
        KommandsManager.getInstance().setupProcessor();
        KommandsManager.getInstance().reload();
        CommandManager.getInstance().addCommand(
                new BaseCommandInvoker(Kommands.MOD_ID,"kommands",
                        CommandUtil.literal("kommands").executes(ClientCommand.of(commandContext -> InfoUtil.sendChatMessage("Running!")))
                                .then(CommandUtil.literal("reload").executes(ClientCommand.of(context -> KommandsManager.getInstance().reload()))).build())
        );
    }

}
