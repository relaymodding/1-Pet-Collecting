package org.relaymodding.petcollecting.events;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.relaymodding.petcollecting.abilities.AbilityHelper;
import org.relaymodding.petcollecting.items.PetItem;

@Mod.EventBusSubscriber
public class PlayerInteractionEvents {
	@SubscribeEvent
	public void onRightClickBlock(PlayerInteractEvent.RightClickBlock e) {
		Level level = e.getLevel();
		if (level.isClientSide) {
			return;
		}

		ItemStack itemStack = e.getItemStack();
		Item item = itemStack.getItem();

		if (!(item instanceof PetItem)) {
			return;
		}

		PetItem petItem = (PetItem)item;
		if (AbilityHelper.abilityCheck(level, e.getEntity(), e.getPos(), itemStack, petItem)) {
			e.setCancellationResult(InteractionResult.SUCCESS);
			e.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onRightClickItem(PlayerInteractEvent.RightClickItem e) {
		Level level = e.getLevel();
		if (level.isClientSide) {
			return;
		}

		if (e.isCanceled()) {
			return;
		}

		ItemStack itemStack = e.getItemStack();
		Item item = itemStack.getItem();

		if (!(item instanceof PetItem)) {
			return;
		}

		Player player = e.getEntity();
		PetItem petItem = (PetItem)item;

		if (AbilityHelper.abilityCheck(level, e.getEntity(), e.getPos().relative(player.getDirection(), 2), itemStack, petItem)) {
			e.setCancellationResult(InteractionResult.SUCCESS);
			e.setCanceled(true);
		}
	}
}