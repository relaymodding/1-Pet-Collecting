package org.relaymodding.petcollecting.encounters;

import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import org.relaymodding.petcollecting.Main;
import org.relaymodding.petcollecting.data.Constants;
import org.relaymodding.petcollecting.encounters.process.BlockBreakEncounter;
import org.relaymodding.petcollecting.encounters.process.LivingDeathEncounter;
import org.relaymodding.petcollecting.encounters.process.TickEncounter;
import org.relaymodding.petcollecting.items.PCItems;
import org.relaymodding.petcollecting.items.PetItem;
import org.relaymodding.petcollecting.util.CompareBlockFunctions;
import org.relaymodding.petcollecting.util.InventoryFunctions;
import org.relaymodding.petcollecting.util.MessageFunctions;
import org.relaymodding.petcollecting.util.TickFunctions;

import java.util.function.UnaryOperator;

public class EncounterHelper {

    private static final String PREV_ENCOUNTER_KEY = Constants.namespace("encounterTime");

    private static final ResourceLocation END_DRAGON_KILLED = new ResourceLocation("end/kill_dragon");

    /*
        Checks whether the player is encountering a pet, and drops it.
     */
    public static void checkForEncounter(Level level, Player player, BlockPos blockPos, EncounterType encounterType, OriginType originType) {

        if (encounterType == null) {
            return;
        }
        int currentTicks = (int) level.getGameTime();

        CompoundTag persistentTag = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);

        boolean shouldEncounter = true;
        if (persistentTag.contains(PREV_ENCOUNTER_KEY)) {
            int encounterTime = persistentTag.getInt(PREV_ENCOUNTER_KEY);
            long ticksPast = TickFunctions.ticksPast((int) level.getGameTime(), encounterTime);
            shouldEncounter = ticksPast - Main.config.petEncounterCooldownTicks > 0;
        }

        if (!shouldEncounter) {
            return;
        }

        double roll = level.getRandom().nextDouble();
        double chance = switch (originType) {
            case TICK -> Main.config.tickEncounterChance;
            case BLOCK_BREAK -> Main.config.blockBreakEncounterChance;
            case LIVING_DEATH -> Main.config.livingDeathEncounterChance;
        };

        if (roll > chance) {
            return;
        }

        boolean encounterResult = false;
        switch (originType) {
            case TICK -> encounterResult = TickEncounter.processTickEncounter(level, player, blockPos, encounterType);
            case BLOCK_BREAK ->
                    encounterResult = BlockBreakEncounter.processBlockBreakEncounter(level, player, blockPos, encounterType);
            case LIVING_DEATH ->
                    encounterResult = LivingDeathEncounter.processLivingDeathEncounter(level, player, blockPos, encounterType);
        }

        if (!encounterResult) {
            return;
        }

        BlockPos encounterPos = blockPos.immutable();
        if (originType.equals(OriginType.TICK)) {
            encounterPos = encounterPos.relative(player.getDirection(), 3);
        }

        showEncounterParticles(level, player, encounterPos, originType);
        playEncounterSound(level, player);

        persistentTag.putInt(PREV_ENCOUNTER_KEY, currentTicks);
        player.getPersistentData().put(Player.PERSISTED_NBT_TAG, persistentTag);
    }

    public static boolean dropPetItem(Level level, Player player, BlockPos blockPos, EncounterHelper.EncounterType encounterType) {

        PetItem petItem = encounterType.getItem().get();
        if (InventoryFunctions.doesInventoryContainPet(player, petItem)) {
            return false;
        }

        level.addFreshEntity(new ItemEntity(level, blockPos.getX(), blockPos.getY() + 0.5, blockPos.getZ(), new ItemStack(petItem)));
        MessageFunctions.sendMessage(player, encounterType.getEncounterString(petItem.getAbility().get().getDisplayName()));
        return true;
    }

    private static void showEncounterParticles(Level level, Player player, BlockPos encounterPos, OriginType originType) {
        level.levelEvent(2003, encounterPos.above(), 0); //TODO: placeholder? Currently same as eye of ender used up
    }

    private static void playEncounterSound(Level level, Player player) {
        player.playSound(SoundEvents.ENDER_EYE_DEATH, 1.0F, 1.0F); //TODO: placeholder
    }

    public enum EncounterType {
        SWIMMING(PCItems.WATER_PET, style -> style.applyFormat(ChatFormatting.AQUA)),
        FLYING(PCItems.AIR_PET),
        TALL_GRASS(PCItems.GRASS_PET),
        IN_LAVA(PCItems.LAVA_PET),

        END_NAVIGATION(PCItems.ENDER_PET),

        TREE_CHOP(PCItems.WOOD_PET),
        STONE_BREAK(PCItems.STONE_PET),
        DIRT_DIG(PCItems.DIRT_PET),
        SAND_DIG(PCItems.SAND_PET),

        PLAYER_DEATH(PCItems.PLAYER_PET),
        MONSTER_DEATH(PCItems.MONSTER_PET),
        ANIMAL_DEATH(PCItems.ANIMAL_PET);


        private final RegistryObject<PetItem> item;
        private final UnaryOperator<Style> styleFunction;

        EncounterType(RegistryObject<PetItem> item, UnaryOperator<Style> styleFunction) {
            this.item = item;
            this.styleFunction = styleFunction;
        }

        EncounterType(RegistryObject<PetItem> item) {
            this.item = item;
            this.styleFunction = style -> style.applyFormat(ChatFormatting.DARK_GREEN);
        }

        public RegistryObject<PetItem> getItem() {
            return item;
        }

        public UnaryOperator<Style> getStyleFunction() {
            return styleFunction;
        }

        public MutableComponent getEncounterString(MutableComponent petType) {
            return Component.translatable("petcollecting.encountered", petType).withStyle(getStyleFunction());
        }

        @Nullable
        public static EncounterType fromDeath(Entity entity) {
            if (entity instanceof Player) {
                return PLAYER_DEATH;
            } else if (entity instanceof Monster) {
                return MONSTER_DEATH;
            } else if (entity instanceof Animal) {
                return ANIMAL_DEATH;
            }
            return null;
        }

        @Nullable
        public static EncounterType fromBreak(Block block) {
            if (CompareBlockFunctions.isTreeLog(block)) {
                return TREE_CHOP;
            } else if (CompareBlockFunctions.isStone(block)) {
                return STONE_BREAK;
            } else if (CompareBlockFunctions.isDirt(block)) {
                return DIRT_DIG;
            } else if (CompareBlockFunctions.isSand(block)) {
                return SAND_DIG;
            }
            return null;
        }

        @Nullable
        public static EncounterType fromTick(Player player) {
            if (player.isSwimming()) {
                return SWIMMING;
            } else if (player.isInLava()) {
                return IN_LAVA;
            } else if (player.isFallFlying()) {
                ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);
                if (chestStack.getItem() instanceof ElytraItem) {
                    return FLYING;
                }
            } else if (player.level.getBlockState(player.blockPosition()).getBlock() instanceof TallGrassBlock) {
                return TALL_GRASS;
            }
            else if (player.level.dimension() == Level.END) {
                Advancement advancement = player.getServer().getAdvancements().getAdvancement(END_DRAGON_KILLED);
                if (player.getServer().getPlayerList().getPlayerAdvancements((ServerPlayer) player).getOrStartProgress(advancement).isDone()) return END_NAVIGATION;
            }
            return null;
        }
    }

    public enum OriginType {
        TICK,
        BLOCK_BREAK,
        LIVING_DEATH
    }
}
