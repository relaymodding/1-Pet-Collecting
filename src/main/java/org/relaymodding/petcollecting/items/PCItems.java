package org.relaymodding.petcollecting.items;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.relaymodding.petcollecting.abilities.PCPetAbilities;
import org.relaymodding.petcollecting.data.Constants;

public class PCItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

    public static final RegistryObject<PetItem> WATER_PET = ITEMS.register("water_pet", () -> new PetItem(PCPetAbilities.WATER, Ingredient.of(Items.KELP)));
    public static final RegistryObject<PetItem> AIR_PET = ITEMS.register("air_pet", () -> new PetItem(PCPetAbilities.AIR, Ingredient.of(Items.PHANTOM_MEMBRANE)));
    public static final RegistryObject<PetItem> GRASS_PET = ITEMS.register("grass_pet", () -> new PetItem(PCPetAbilities.GRASS, Ingredient.of(Items.BONE_MEAL)));
    public static final RegistryObject<PetItem> LAVA_PET = ITEMS.register("lava_pet", () -> new PetItem(PCPetAbilities.LAVA, Ingredient.of(Items.MAGMA_CREAM)));

    public static final RegistryObject<PetItem> WOOD_PET = ITEMS.register("wood_pet", () -> new PetItem(PCPetAbilities.WOOD, Ingredient.of(ItemTags.LOGS)));
    public static final RegistryObject<PetItem> STONE_PET = ITEMS.register("stone_pet", () -> new PetItem(PCPetAbilities.STONE, Ingredient.of(Items.OBSIDIAN)));
    public static final RegistryObject<PetItem> DIRT_PET = ITEMS.register("dirt_pet", () -> new PetItem(PCPetAbilities.DIRT, Ingredient.of(ItemTags.DIRT)));
    public static final RegistryObject<PetItem> SAND_PET = ITEMS.register("sand_pet", () -> new PetItem(PCPetAbilities.SAND, Ingredient.of(Tags.Items.SANDSTONE)));

    public static final RegistryObject<PetItem> PLAYER_PET = ITEMS.register("player_pet", () -> new PetItem(PCPetAbilities.PLAYER, Ingredient.of(Items.PLAYER_HEAD)));
    public static final RegistryObject<PetItem> MONSTER_PET = ITEMS.register("monster_pet", () -> new PetItem(PCPetAbilities.MONSTER, Ingredient.EMPTY));
    public static final RegistryObject<PetItem> ANIMAL_PET = ITEMS.register("animal_pet", () -> new PetItem(PCPetAbilities.ANIMAL, Ingredient.of(Items.BEETROOT)));
    public static final RegistryObject<PetItem> PETAL_PET = ITEMS.register("petal_pet", () -> new PetItem(PCPetAbilities.PETAL, Ingredient.of(ItemTags.FLOWERS)));
    public static final RegistryObject<PetItem> POTION_PET = ITEMS.register("potion_pet", () -> new PetItem(PCPetAbilities.POTION, Ingredient.of(Items.BLAZE_POWDER)));

    public static final RegistryObject<PetItem> ENDER_PET = ITEMS.register("end_pet", () -> new PetItem(PCPetAbilities.END, Ingredient.of(Items.ENDER_PEARL)));
}
