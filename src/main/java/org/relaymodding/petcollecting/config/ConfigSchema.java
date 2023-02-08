package org.relaymodding.petcollecting.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.relaymodding.petcollecting.data.Constants;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ConfigSchema {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

    @Expose
    public int petEncounterCooldownTicks = FMLEnvironment.production ? 10 * 20 * 60 : 2 * 20;
    @Expose
    public int petUseAbilityCooldownTicks = 100;

    @Expose
    public boolean sendChatMessages = true;
    @Expose
    public boolean sendClientMessages = true;

    @Expose
    public double tickEncounterChance = 0.01;
    @Expose
    public double blockBreakEncounterChance = 0.1;
    @Expose
    public double livingDeathEncounterChance = 0.1;

    @Expose
    public double elytraJumpBoost = 1.5;

    @Expose
    public double endPetRange = 10;

    public static ConfigSchema load(File configFile) {

        ConfigSchema config = new ConfigSchema();

        // Attempt to load existing config file
        if (configFile.exists()) {

            try (FileReader reader = new FileReader(configFile)) {

                config = GSON.fromJson(reader, ConfigSchema.class);
                Constants.LOG.info("Loaded config file.");
            }

            catch (Exception e) {

                Constants.LOG.error("Could not read config file {}. Defaults will be used.", configFile.getAbsolutePath());
                Constants.LOG.catching(e);
            }
        }

        else {

            Constants.LOG.info("Creating a new config file at {}.", configFile.getAbsolutePath());
            configFile.getParentFile().mkdirs();
        }

        try (FileWriter writer = new FileWriter(configFile)) {

            GSON.toJson(config, writer);
            Constants.LOG.info("Saved config file.");
        }

        catch (Exception e) {

            Constants.LOG.error("Could not write config file '{}'!", configFile.getAbsolutePath());
            Constants.LOG.catching(e);
        }

        return config;
    }
}