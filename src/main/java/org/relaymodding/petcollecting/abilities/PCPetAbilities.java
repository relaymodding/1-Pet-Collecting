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

    public static final DeferredRegister<PetAbility> PET_ABILITIES = DeferredRegister.create(ResourceKey.createRegistryKey(Constants.rl("pet_ability")), Constants.MOD_ID);
    public static final Supplier<IForgeRegistry<PetAbility>> PET_ABILITIES_REG = PET_ABILITIES.makeRegistry(RegistryBuilder::new);
    public static final RegistryObject<AirPetAbility> AIR = PET_ABILITIES.register("air", AirPetAbility::new);
    public static final RegistryObject<AnimalPetAbility> ANIMAL = PET_ABILITIES.register("animal", AnimalPetAbility::new);
    public static final RegistryObject<DirtPetAbility> DIRT = PET_ABILITIES.register("dirt", DirtPetAbility::new);
    public static final RegistryObject<GrassPetAbility> GRASS = PET_ABILITIES.register("grass", GrassPetAbility::new);
    public static final RegistryObject<LavaPetAbility> LAVA = PET_ABILITIES.register("lava", LavaPetAbility::new);
    public static final RegistryObject<MonsterPetAbility> MONSTER = PET_ABILITIES.register("monster", MonsterPetAbility::new);
    public static final RegistryObject<PlayerPetAbility> PLAYER = PET_ABILITIES.register("player", PlayerPetAbility::new);
    public static final RegistryObject<SandPetAbility> SAND = PET_ABILITIES.register("sand", SandPetAbility::new);
    public static final RegistryObject<StonePetAbility> STONE = PET_ABILITIES.register("stone", StonePetAbility::new);
    public static final RegistryObject<WaterPetAbility> WATER = PET_ABILITIES.register("water", WaterPetAbility::new);
    public static final RegistryObject<WoodPetAbility> WOOD = PET_ABILITIES.register("wood", WoodPetAbility::new);
    public static final RegistryObject<EndPetAbility> END = PET_ABILITIES.register("end", EndPetAbility::new);
    public static final RegistryObject<PetalPetAbility> PETAL = PET_ABILITIES.register("petal", PetalPetAbility::new);


}
