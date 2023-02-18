package org.relaymodding.petcollecting.blocks;

import org.relaymodding.petcollecting.data.Constants;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PCBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);
	
	public static final RegistryObject<CooledLava> COOLED_LAVA = BLOCKS.register("cooled_lava", () -> new CooledLava(Properties.of(Material.ICE)));
}
