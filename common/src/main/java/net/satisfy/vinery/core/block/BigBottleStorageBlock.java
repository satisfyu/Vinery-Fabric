package net.satisfy.vinery.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.satisfy.vinery.core.registry.StorageTypeRegistry;
import org.jetbrains.annotations.NotNull;

import static net.satisfy.vinery.core.registry.ObjectRegistry.*;

public class BigBottleStorageBlock extends StorageBlock {

    public BigBottleStorageBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(OPEN, false));
    }

    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    private static final SoundEvent OPEN_SOUND = SoundEvents.BAMBOO_WOOD_DOOR_OPEN;


    @Override
    public @NotNull InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.isShiftKeyDown() && stack.isEmpty()) {
            if (!world.isClientSide()) {
                world.playSound(null, pos, OPEN_SOUND, SoundSource.BLOCKS, 0.4f, 0.4f);
                world.setBlock(pos, state.setValue(OPEN, !state.getValue(OPEN)), UPDATE_ALL);
            }
            return InteractionResult.sidedSuccess(world.isClientSide());
        } else if (state.getValue(OPEN)) {
            return super.use(state, world, pos, player, hand, hit);
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(OPEN);
    }

    @Override
    public boolean canInsertStack(ItemStack stack) {
        return stack.getItem() == MAGNETIC_WINE_ITEM.get() ||
                stack.getItem() == JO_SPECIAL_MIXTURE_ITEM.get() ||
                stack.getItem() == CRISTEL_WINE_ITEM.get() ||
                stack.getItem() == GLOWING_WINE_ITEM.get() ||
                stack.getItem() == JELLIE_WINE_ITEM.get() ||
                stack.getItem() == EISWEIN_ITEM.get() ||
                stack.getItem() == CHENET_WINE_ITEM.get() ||
                stack.getItem() == AEGIS_WINE_ITEM.get() ||
                stack.getItem() == MELLOHI_WINE_ITEM.get() ||
                stack.getItem() == STRAD_WINE_ITEM.get() ||
                stack.getItem() == APPLE_CIDER_ITEM.get() ||
                stack.getItem() == APPLE_WINE_ITEM.get() ||
                stack.getItem() == LILITU_WINE_ITEM.get() ||
                stack.getItem() == CHORUS_WINE_ITEM.get() ||
                stack.getItem() == CREEPERS_CRUSH_ITEM.get() ||
                stack.getItem() == MEAD_ITEM.get() ||
                stack.getItem() == CHERRY_WINE_ITEM.get() ||
                stack.getItem() == RED_WINE_ITEM.get() ||
                stack.getItem() == STAL_WINE_ITEM.get() ||
                stack.getItem() == BOLVAR_WINE_ITEM.get() ||
                stack.getItem() == SOLARIS_WINE_ITEM.get() ||
                stack.getItem() == KELP_CIDER_ITEM.get() ||
                stack.getItem() == CLARK_WINE_ITEM.get() ||
                stack.getItem() == BOTTLE_MOJANG_NOIR_ITEM.get() ||
                stack.getItem() == VILLAGERS_FRIGHT_ITEM.get() ||
                stack.getItem() == NOIR_WINE_ITEM.get();
    }


    @Override
    public int size(){
        return 1;
    }

    @Override
    public ResourceLocation type() {
        return StorageTypeRegistry.BIG_BOTTLE;
    }

    @Override
    public Direction[] unAllowedDirections() {
        return new Direction[]{Direction.DOWN, Direction.UP};
    }

    @Override
    public int getSection(Float x, Float y) {
        return 0;
    }
}
