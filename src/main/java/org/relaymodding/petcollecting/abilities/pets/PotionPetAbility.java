package org.relaymodding.petcollecting.abilities.pets;

import com.google.common.base.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import org.relaymodding.petcollecting.Main;
import org.relaymodding.petcollecting.api.PetAbility;

public class PotionPetAbility implements PetAbility {

    @Override
    public boolean processPetAbility(Level level, Player player, BlockPos clickPos, ItemStack itemStack) {

        switch (level.random.nextInt(7)) {

            case 0:
                player.addEffect(new MobEffectInstance(MobEffects.BAD_OMEN, 60 * 20));
                break;
            case 1:
                player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 15 * 20));
                break;
            case 2:
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 120 * 20));
                break;
            case 3:
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 30 * 20));
                break;
            case 4:
                player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 120 * 20));
                break;
            case 5:
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 30 * 20));
                break;
            case 6:
                player.addEffect(new MobEffectInstance(MobEffects.POISON, 10 * 20));
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public String descriptionId() {
        return "potion";
    }

    @Override
    public String foodId() {
        return descriptionId();
    }
}
