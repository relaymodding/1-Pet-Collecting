package org.relaymodding.petcollecting;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import org.relaymodding.petcollecting.abilities.PCPetAbilities;
import org.relaymodding.petcollecting.blocks.PCBlocks;
import org.relaymodding.petcollecting.config.ConfigSchema;
import org.relaymodding.petcollecting.data.Constants;
import org.relaymodding.petcollecting.events.EffectEvent;
import org.relaymodding.petcollecting.items.PCItems;
import org.relaymodding.petcollecting.network.PacketHandler;


@Mod(Constants.MOD_ID)
public class Main {

    public static ConfigSchema config;

    public static final CreativeModeTab PC_ITEMS_TAB = new CreativeModeTab(CreativeModeTab.getGroupCountSafe(), "petcollecting") {
        @Override
        public ItemStack makeIcon() {
            return PCItems.ANIMAL_PET.get().getDefaultInstance();
        }
    };

    public Main() {
        config = ConfigSchema.load(FMLPaths.CONFIGDIR.get().resolve(Constants.MOD_ID + ".json").toFile());

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        PCItems.ITEMS.register(modEventBus);
        PCBlocks.BLOCKS.register(modEventBus);
        EFFECTS.register(modEventBus);
        PCPetAbilities.PET_ABILITIES.register(modEventBus);
        
        MinecraftForge.EVENT_BUS.addListener(EffectEvent::onPlayerTick);

        PacketHandler.init();
    }
    
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Constants.MOD_ID);
    public static final RegistryObject<MobEffect> LAVA_WALKER = EFFECTS.register("lava_walker", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0xF06C01));
}
