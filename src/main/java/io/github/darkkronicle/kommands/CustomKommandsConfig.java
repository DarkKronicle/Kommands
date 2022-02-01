package io.github.darkkronicle.kommands;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.FileConfig;
import fi.dy.masa.malilib.util.FileUtils;
import io.github.darkkronicle.kommandlib.CommandManager;
import io.github.darkkronicle.kommandlib.command.CommandInvoker;
import io.github.darkkronicle.kommands.util.BasicCommandBuilder;
import io.github.darkkronicle.kommands.util.TomlUtil;
import lombok.Getter;
import net.minecraft.server.command.ServerCommandSource;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CustomKommandsConfig {

    @Getter
    private Path directory;

    @Getter
    private List<Path> configPaths = new ArrayList<>();

    private Map<Path, FileConfig> configs = new HashMap<>();

    public CustomKommandsConfig() {
        this(null);
    }

    public CustomKommandsConfig(Path directory) {
        if (directory == null) {
            directory = getDefaultPath();
        }
        this.directory = directory;
        this.directory.toFile().mkdirs();
    }

    public void load() {
        unload();
        setConfigPaths();
        if (configPaths.isEmpty()) {
            try {
                Files.copy(Kommands.getResource("sample_config.toml"), directory.resolve("sample_config.toml"));
                setConfigPaths();
            } catch (URISyntaxException | IOException e) {
                Kommands.LOGGER.log(Level.WARN, "Error copying default config!", e);
            }
        }
        for (Path path : configPaths) {
            setupPath(path);
        }
        loadCommands();
    }

    public void loadCommands() {
        for (FileConfig config : configs.values()) {
            loadCommand(config);
        }
    }

    public void unload() {
        for (FileConfig config : configs.values()) {
            config.close();
        }
        configs = new HashMap<>();
    }

    private void setupPath(Path path) {
        FileConfig config = TomlUtil.loadFile(path.toFile());
        configs.put(path, config);
    }

    private void loadCommand(FileConfig config) {
        Optional<List<Config>> commands = config.getOptional("command");
        if (commands.isEmpty()) {
            return;
        }
        for (Config commandConfig : commands.get()) {
            Optional<CommandInvoker<ServerCommandSource>> command = new BasicCommandBuilder().build(commandConfig);
            command.ifPresent(serverCommandSourceCommandInvoker -> CommandManager.getInstance().addCommand(serverCommandSourceCommandInvoker));
        }
    }

    public void setConfigPaths() {
        Optional<List<Path>> paths = TomlUtil.getFilesWithExtensionCaught(directory, ".toml");
        configPaths = paths.orElseGet(ArrayList::new);
    }

    public static Path getDefaultPath() {
        return FileUtils.getConfigDirectory().toPath().resolve(Kommands.MOD_ID).resolve("commands");
    }

}
