package org.relaymodding.petcollecting.abilities.pets;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.relaymodding.petcollecting.api.PetAbility;

public class PlayerPetAbility implements PetAbility {
    @Override
    public boolean processPetAbility(Level level, Player player, BlockPos clickPos, ItemStack itemStack) {
    	player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 600));
    	player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600));
    	player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 600));
    	player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 600));
    	player.removeEffect(MobEffects.POISON);
    	player.removeEffect(MobEffects.WITHER);
    	player.removeEffect(MobEffects.HUNGER);
    	player.removeEffect(MobEffects.LEVITATION);
    	player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
    	player.removeEffect(MobEffects.DIG_SLOWDOWN);
    	itemStack.hurtAndBreak(6, player, p -> {});
        return true;
    }

    @Override
    public String descriptionId() {
        return "player";
    }
}
