package net.satisfy.vinery.item;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import de.cristelknight.doapi.common.block.entity.StorageBlockEntity;
import de.cristelknight.doapi.common.util.GeneralUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.vinery.registry.ObjectRegistry;
import net.satisfy.vinery.util.WineYears;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class DrinkBlockItem extends BlockItem {
    public DrinkBlockItem(Block block, Properties settings) {
        super(block, settings);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    protected BlockState getPlacementState(BlockPlaceContext context) {
        if (!Objects.requireNonNull(context.getPlayer()).isCrouching()) {
            return null;
        }

        BlockState blockState = this.getBlock().getStateForPlacement(context);
        return blockState != null && this.canPlace(context, blockState) ? blockState : null;
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos blockPos, Level level, @Nullable Player player, ItemStack itemStack, BlockState blockState) {
        if(level.getBlockEntity(blockPos) instanceof StorageBlockEntity wineEntity){
            wineEntity.setStack(0, itemStack.copyWithCount(1));
        }
        return super.updateCustomBlockEntityTag(blockPos, level, player, itemStack, blockState);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        List<Pair<MobEffectInstance, Float>> effects = getFoodProperties() != null ? getFoodProperties().getEffects() : Lists.newArrayList();

        if (effects.isEmpty()) {
            tooltip.add(Component.translatable("effect.none").withStyle(ChatFormatting.GRAY));
        } else {
            for(Pair<MobEffectInstance, Float> effectPair : effects) {
                MobEffectInstance effectInstance = effectPair.getFirst();
                MobEffect effect = effectInstance.getEffect();

                String effectName = effect.getDisplayName().getString();
                int amplifier = WineYears.getEffectLevel(stack, world);
                String amplifierRoman = amplifier > 0 ? " " + toRoman(amplifier) : "";
                int durationTicks = WineYears.getEffectDuration(stack, world);
                String formattedDuration = formatDuration(durationTicks);

                String tooltipText = effectName + amplifierRoman + " (" + formattedDuration + ")";
                tooltip.add(Component.literal(tooltipText).withStyle(effect.getCategory().getTooltipFormatting()));
            }
        }

        tooltip.add(Component.empty());

        if (world != null && WineYears.hasWineYear(stack)) {
            int age = WineYears.getWineAge(stack, world);
            tooltip.add(Component.translatable("tooltip.vinery.age", age).withStyle(ChatFormatting.WHITE));
        }
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        super.finishUsingItem(itemStack, level, livingEntity);
        return GeneralUtil.convertStackAfterFinishUsing(livingEntity, itemStack, ObjectRegistry.WINE_BOTTLE.get(), this);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        return ItemUtils.startUsingInstantly(level, player, interactionHand);
    }

    private String toRoman(int number) {
        return switch (number) {
            case 1 -> "I";
            case 2 -> "II";
            case 3 -> "III";
            case 4 -> "IV";
            case 5 -> "V";
            case 6 -> "VI";
            case 7 -> "VII";
            case 8 -> "VIII";
            case 9 -> "IX";
            case 10 -> "X";
            default -> String.valueOf(number);
        };
    }

    private String formatDuration(int ticks) {
        int totalSeconds = ticks / 20;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}
