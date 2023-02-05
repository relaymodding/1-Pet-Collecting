package org.relaymodding.petcollecting;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.relaymodding.petcollecting.config.ConfigSchema;
import org.relaymodding.petcollecting.data.Constants;
import org.relaymodding.petcollecting.events.PetEncounterEvents;
import org.relaymodding.petcollecting.events.PlayerInteractionEvents;
import org.relaymodding.petcollecting.items.PetItem;
import org.relaymodding.petcollecting.items.PetItems;
import org.relaymodding.petcollecting.items.RegistryHandler;

@Mod(Constants.MOD_ID)
public class Main {
	public static ConfigSchema config;

    public Main() {
		config = ConfigSchema.load(FMLPaths.CONFIGDIR.get().resolve(Constants.MOD_ID + ".json").toFile());

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		RegistryHandler.ITEMS.register(modEventBus);
		modEventBus.addListener(this::loadComplete);
	}

	private void loadComplete(final FMLLoadCompleteEvent event) {
        registerPetItems();
        registerEvents();
	}

    private void registerPetItems() {
        PetItems.WATER_PET = (PetItem)RegistryHandler.SWIMMING_PET_OBJECT.get();
        PetItems.AIR_PET = (PetItem)RegistryHandler.FLYING_PET_OBJECT.get();
        PetItems.GRASS_PET = (PetItem)RegistryHandler.TALL_GRASS_PET_OBJECT.get();
        PetItems.LAVA_PET = (PetItem)RegistryHandler.IN_LAVA_PET_OBJECT.get();

        PetItems.WOOD_PET = (PetItem)RegistryHandler.TREE_CHOP_PET_OBJECT.get();
        PetItems.STONE_PET = (PetItem)RegistryHandler.STONE_BREAK_PET_OBJECT.get();
        PetItems.DIRT_PET = (PetItem)RegistryHandler.DIRT_DIG_PET_OBJECT.get();
        PetItems.SAND_PET = (PetItem)RegistryHandler.SAND_DIG_PET_OBJECT.get();

        PetItems.PLAYER_PET = (PetItem)RegistryHandler.PLAYER_DEATH_PET_OBJECT.get();
        PetItems.MONSTER_PET = (PetItem)RegistryHandler.MONSTER_DEATH_PET_OBJECT.get();
        PetItems.ANIMAL_PET = (PetItem)RegistryHandler.ANIMAL_DEATH_PET_OBJECT.get();
    }

    private void registerEvents() {
        MinecraftForge.EVENT_BUS.register(new PetEncounterEvents());
        MinecraftForge.EVENT_BUS.register(new PlayerInteractionEvents());
    }
}
