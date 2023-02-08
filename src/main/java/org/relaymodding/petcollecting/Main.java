package org.relaymodding.petcollecting;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.relaymodding.petcollecting.abilities.PCPetAbilities;
import org.relaymodding.petcollecting.config.ConfigSchema;
import org.relaymodding.petcollecting.data.Constants;
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
        PCPetAbilities.PET_ABILITIES.register(modEventBus);

        PacketHandler.init();
    }
}
