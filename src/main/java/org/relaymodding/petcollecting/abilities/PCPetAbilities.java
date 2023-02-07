package org.relaymodding.petcollecting.abilities;

import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.relaymodding.petcollecting.abilities.pets.*;
import org.relaymodding.petcollecting.api.PetAbility;
import org.relaymodding.petcollecting.data.Constants;

import java.util.function.Supplier;

public class PCPetAbilities {

    public static DeferredRegister<PetAbility> PET_ABILITIES = DeferredRegister.create(ResourceKey.createRegistryKey(Constants.rl("pet_ability")), Constants.MOD_ID);
    public static Supplier<IForgeRegistry<PetAbility>> PET_ABILITIES_REG = PET_ABILITIES.makeRegistry(RegistryBuilder::new);
    public static RegistryObject<AirPetAbility> AIR = PET_ABILITIES.register("air", AirPetAbility::new);
    public static RegistryObject<AnimalPetAbility> ANIMAL = PET_ABILITIES.register("animal", AnimalPetAbility::new);
    public static RegistryObject<DirtPetAbility> DIRT = PET_ABILITIES.register("dirt", DirtPetAbility::new);
    public static RegistryObject<GrassPetAbility> GRASS = PET_ABILITIES.register("grass", GrassPetAbility::new);
    public static RegistryObject<LavaPetAbility> LAVA = PET_ABILITIES.register("lava", LavaPetAbility::new);
    public static RegistryObject<MonsterPetAbility> MONSTER = PET_ABILITIES.register("monster", MonsterPetAbility::new);
    public static RegistryObject<PlayerPetAbility> PLAYER = PET_ABILITIES.register("player", PlayerPetAbility::new);
    public static RegistryObject<SandPetAbility> SAND = PET_ABILITIES.register("sand", SandPetAbility::new);
    public static RegistryObject<StonePetAbility> STONE = PET_ABILITIES.register("stone", StonePetAbility::new);
    public static RegistryObject<WaterPetAbility> WATER = PET_ABILITIES.register("water", WaterPetAbility::new);
    public static RegistryObject<WoodPetAbility> WOOD = PET_ABILITIES.register("wood", WoodPetAbility::new);


}
