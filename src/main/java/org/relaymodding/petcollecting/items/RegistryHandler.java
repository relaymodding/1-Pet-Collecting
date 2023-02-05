package org.relaymodding.petcollecting.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.relaymodding.petcollecting.data.Constants;

@Mod.EventBusSubscriber
public class RegistryHandler {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

	public static final RegistryObject<Item> SWIMMING_PET_OBJECT = ITEMS.register("water_pet", () -> new PetItem((new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC))));
	public static final RegistryObject<Item> FLYING_PET_OBJECT = ITEMS.register("air_pet", () -> new PetItem((new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC))));
	public static final RegistryObject<Item> TALL_GRASS_PET_OBJECT = ITEMS.register("grass_pet", () -> new PetItem((new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC))));
	public static final RegistryObject<Item> IN_LAVA_PET_OBJECT = ITEMS.register("lava_pet", () -> new PetItem((new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC))));

	public static final RegistryObject<Item> TREE_CHOP_PET_OBJECT = ITEMS.register("wood_pet", () -> new PetItem((new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC))));
	public static final RegistryObject<Item> STONE_BREAK_PET_OBJECT = ITEMS.register("stone_pet", () -> new PetItem((new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC))));
	public static final RegistryObject<Item> DIRT_DIG_PET_OBJECT = ITEMS.register("dirt_pet", () -> new PetItem((new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC))));
	public static final RegistryObject<Item> SAND_DIG_PET_OBJECT = ITEMS.register("sand_pet", () -> new PetItem((new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC))));

	public static final RegistryObject<Item> PLAYER_DEATH_PET_OBJECT = ITEMS.register("player_pet", () -> new PetItem((new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC))));
	public static final RegistryObject<Item> MONSTER_DEATH_PET_OBJECT = ITEMS.register("monster_pet", () -> new PetItem((new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC))));
	public static final RegistryObject<Item> ANIMAL_DEATH_PET_OBJECT = ITEMS.register("animal_pet", () -> new PetItem((new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC))));
}
