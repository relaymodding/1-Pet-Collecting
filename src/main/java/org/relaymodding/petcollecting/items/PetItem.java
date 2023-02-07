package org.relaymodding.petcollecting.items;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import org.relaymodding.petcollecting.Main;
import org.relaymodding.petcollecting.api.PetAbility;

import java.util.List;

public class PetItem extends Item {
    private final RegistryObject<? extends PetAbility> ability;

    public PetItem(RegistryObject<? extends PetAbility> ability) {
        super(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC));
        this.ability = ability;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag isAdvanced) {
        components.add(ability.get().getHoverText());
    }

    protected final boolean processAbility(Level level, Player player, BlockPos position, ItemStack stack) {
        return ability.map(petAbility -> petAbility.processPetAbility(level, player, position, stack)).orElse(false);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        Level level = context.getLevel();
        Player player = context.getPlayer();

        if (!level.isClientSide && player != null) {
            boolean tookAction = processAbility(level, player, context.getClickedPos(), stack);
            if (tookAction) {
                player.getCooldowns().addCooldown(this, Main.config.petUseAbilityCooldownTicks);
            }
            return InteractionResult.sidedSuccess(tookAction);
        }


        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (!level.isClientSide) {
            boolean tookAction = processAbility(level, player, player.blockPosition().relative(player.getDirection(), 2), stack);
            if (tookAction) {
                player.getCooldowns().addCooldown(this, Main.config.petUseAbilityCooldownTicks);
            }
            return InteractionResultHolder.sidedSuccess(stack, tookAction);
        }

        return InteractionResultHolder.success(stack);
    }

    public RegistryObject<? extends PetAbility> getAbility() {
        return ability;
    }
}