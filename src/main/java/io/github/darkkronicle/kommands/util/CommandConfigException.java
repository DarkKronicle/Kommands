package io.github.darkkronicle.kommands.util;

import com.electronwill.nightconfig.core.Config;

public class CommandConfigException extends IllegalArgumentException {

    public CommandConfigException(String s, Config config) {
        super(s);

    }

}
