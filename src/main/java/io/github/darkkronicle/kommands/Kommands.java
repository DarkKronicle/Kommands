package io.github.darkkronicle.kommands;

import fi.dy.masa.malilib.event.InitializationHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

@Environment(EnvType.CLIENT)
public class Kommands implements ClientModInitializer {

    public static final String MOD_ID = "kommands";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        InitializationHandler.getInstance().registerInitializationHandler(new KommandsInitializer());
    }

    /**
     * Get's a resource from src/resources. Works in a emulated environment.
     *
     * @param path Path from the resources to get
     * @return Stream of the resource
     * @throws URISyntaxException If the resource doesn't exist
     * @throws IOException Can't be opened
     */
    public static InputStream getResource(String path) throws URISyntaxException, IOException {
        URI uri = Thread.currentThread().getContextClassLoader().getResource(path).toURI();
        if (uri.getScheme().contains("jar")) {
            // Not IDE
            // jar.toString() begins with file:
            // i want to trim it out...
            return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        } else {
            // IDE
            return new FileInputStream(Paths.get(uri).toFile());
        }
    }

}
