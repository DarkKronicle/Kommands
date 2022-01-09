package io.github.darkkronicle.kommands.util;

import com.electronwill.nightconfig.core.Config;

public class CommandConfigException extends IllegalArgumentException {

    private final Config config;

    public CommandConfigException(String s, Config config) {
        super(s);
        this.config = config;
    }

    public String getConfigValues() {
        StringBuilder builder = new StringBuilder();
        for (Config.Entry entry : config.entrySet()) {
            builder.append(entry.getKey());
        }
        return super.getMessage() + "\n Configuration keys: " + builder;
    }
}
