package org.relaymodding.petcollecting.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.relaymodding.petcollecting.abilities.PCPetAbilities;
import org.relaymodding.petcollecting.data.Constants;

public class PCItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

    public static final RegistryObject<PetItem> WATER_PET = ITEMS.register("water_pet", () -> new PetItem(PCPetAbilities.WATER));
    public static final RegistryObject<PetItem> AIR_PET = ITEMS.register("air_pet", () -> new PetItem(PCPetAbilities.AIR));
    public static final RegistryObject<PetItem> GRASS_PET = ITEMS.register("grass_pet", () -> new PetItem(PCPetAbilities.GRASS));
    public static final RegistryObject<PetItem> LAVA_PET = ITEMS.register("lava_pet", () -> new PetItem(PCPetAbilities.LAVA));

    public static final RegistryObject<PetItem> WOOD_PET = ITEMS.register("wood_pet", () -> new PetItem(PCPetAbilities.WOOD));
    public static final RegistryObject<PetItem> STONE_PET = ITEMS.register("stone_pet", () -> new PetItem(PCPetAbilities.STONE));
    public static final RegistryObject<PetItem> DIRT_PET = ITEMS.register("dirt_pet", () -> new PetItem(PCPetAbilities.DIRT));
    public static final RegistryObject<PetItem> SAND_PET = ITEMS.register("sand_pet", () -> new PetItem(PCPetAbilities.SAND));

    public static final RegistryObject<PetItem> PLAYER_PET = ITEMS.register("player_pet", () -> new PetItem(PCPetAbilities.PLAYER));
    public static final RegistryObject<PetItem> MONSTER_PET = ITEMS.register("monster_pet", () -> new PetItem(PCPetAbilities.MONSTER));
    public static final RegistryObject<PetItem> ANIMAL_PET = ITEMS.register("animal_pet", () -> new PetItem(PCPetAbilities.ANIMAL));

}
