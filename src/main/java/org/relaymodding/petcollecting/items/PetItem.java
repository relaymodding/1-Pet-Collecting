package org.relaymodding.petcollecting.items;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import org.relaymodding.petcollecting.Main;
import org.relaymodding.petcollecting.api.PetAbility;

import java.util.List;

public class PetItem extends Item {
    private final RegistryObject<? extends PetAbility> ability;
    private final Ingredient foodItem;
    public static String UNBREAKING_CHANCE = "unbreakingChance";

    private static final int MAX_DAMAGE = 16;

    public PetItem(RegistryObject<? extends PetAbility> ability, Ingredient food) {
        super(new Item.Properties().stacksTo(1).tab(Main.PC_ITEMS_TAB).durability(food == Ingredient.EMPTY ? 0 : MAX_DAMAGE));
        this.ability = ability;
        this.foodItem = food;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag isAdvanced) {
        components.add(ability.get().getHoverText());
        if (ability.get().getFood() != null) components.add(ability.get().getFood());
    }

    protected final boolean processAbility(Level level, Player player, BlockPos position, ItemStack stack) {
        return ability.map(petAbility -> petAbility.processPetAbility(level, player, position, stack)).orElse(false);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (foodItem == Ingredient.EMPTY) return;
        if (!pLevel.isClientSide && this.getDamage(pStack) > 0 && pEntity instanceof ServerPlayer player && pLevel.getGameTime() % 100 == 0) {
            Inventory inv = player.getInventory();
            for (int i = 0; i < 36; i++) {
                ItemStack slotItemStack = inv.getItem(i);
                if (foodItem.test(slotItemStack)) {
                    //This feels horrible, but I can't think of other ways to get data from the Ingredient.
                    slotItemStack.shrink(1);
                    pStack.setDamageValue(pStack.getDamageValue() -1);
                    pLevel.playSound(null, pEntity.getX(), pEntity.getY(), pEntity.getZ(), SoundEvents.GENERIC_EAT, SoundSource.AMBIENT, 1.0f, pLevel.random.nextFloat());
                }
            }
        }
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
                float unbreakingChance = stack.getOrCreateTag().getFloat(UNBREAKING_CHANCE);
                if (!(foodItem == Ingredient.EMPTY)  && level.random.nextFloat() > unbreakingChance) {
                	stack.hurtAndBreak(1, player, plyr ->{}); 
                	stack.getTag().putFloat(UNBREAKING_CHANCE, unbreakingChance + Main.config.petUnbreakingGrowth);
                }
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
                float unbreakingChance = stack.getOrCreateTag().getFloat(UNBREAKING_CHANCE);
                if (!(foodItem == Ingredient.EMPTY) && level.random.nextFloat() > unbreakingChance) {
                	stack.hurtAndBreak(1, player, plyr -> {});
                	stack.getTag().putFloat(UNBREAKING_CHANCE, unbreakingChance + Main.config.petUnbreakingGrowth);
                }
            }
            return InteractionResultHolder.sidedSuccess(stack, tookAction);
        }

        return InteractionResultHolder.success(stack);
    }

    public RegistryObject<? extends PetAbility> getAbility() {
        return ability;
    }
}