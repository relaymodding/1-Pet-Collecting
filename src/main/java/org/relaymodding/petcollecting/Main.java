package org.relaymodding.petcollecting;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.relaymodding.petcollecting.abilities.PCPetAbilities;
import org.relaymodding.petcollecting.config.ConfigSchema;
import org.relaymodding.petcollecting.data.Constants;
import org.relaymodding.petcollecting.events.PetEncounterEvents;
import org.relaymodding.petcollecting.items.PCItems;

@Mod(Constants.MOD_ID)
public class Main {
    public static ConfigSchema config;

    public Main() {
        config = ConfigSchema.load(FMLPaths.CONFIGDIR.get().resolve(Constants.MOD_ID + ".json").toFile());

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        PCItems.ITEMS.register(modEventBus);
        PCPetAbilities.PET_ABILITIES.register(modEventBus);
    }


}
