package org.relaymodding.petcollecting.abilities.pets;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import org.relaymodding.petcollecting.Main;
import org.relaymodding.petcollecting.api.PetAbility;

public class LavaPetAbility implements PetAbility {
    @Override
    public boolean processPetAbility(Level level, Player player, BlockPos clickPos, ItemStack itemStack) {
    	player.addEffect(new MobEffectInstance(Main.LAVA_WALKER.get(), 600));
        return true;
    }

    @Override
    public String descriptionId() {
        return "lava";
    }

    @Override
    public String foodId() {
        return descriptionId();
    }
}
