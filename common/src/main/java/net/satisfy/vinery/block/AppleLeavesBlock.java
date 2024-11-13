package net.satisfy.vinery.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.satisfy.vinery.platform.PlatformHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class AppleLeavesBlock extends LeavesBlock {
    public static final BooleanProperty VARIANT = BooleanProperty.create("can_have_apples");
    public static final BooleanProperty HAS_APPLES = BooleanProperty.create("has_apples");
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);

    public AppleLeavesBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PERSISTENT, false)
                .setValue(DISTANCE, 7)
                .setValue(VARIANT, false)
                .setValue(HAS_APPLES, false)
                .setValue(AGE, 0));
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (state.getValue(VARIANT) && state.getValue(HAS_APPLES)) {
            if (!world.isClientSide()) {
                int dropCount = world.getRandom().nextBoolean() ? world.getRandom().nextInt(1, 4) : 1;
                ItemStack dropStack = new ItemStack(Items.APPLE, dropCount);
                AppleLeavesBlock.popResourceFromFace(world, pos, hit.getDirection(), dropStack);
                world.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1F, 1F);
                world.setBlock(pos, state.setValue(HAS_APPLES, false).setValue(AGE, 0), 2);
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(VARIANT) && !state.getValue(HAS_APPLES);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Player p = ctx.getPlayer();
        boolean b = p != null && p.isShiftKeyDown();
        return Objects.requireNonNull(super.getStateForPlacement(ctx))
                .setValue(VARIANT, ctx.getPlayer().getAbilities().instabuild && b)
                .setValue(AGE, 0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(VARIANT, HAS_APPLES, AGE);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int age = state.getValue(AGE);
        double growthChance = PlatformHelper.getAppleGrowthChance();
        if (age < 3 && random.nextDouble() < growthChance && canGrowPlace(world, pos)) {
            BlockState newState;
            if (age + 1 >= 3) {
                newState = state.setValue(HAS_APPLES, true).setValue(AGE, 0);
            } else {
                newState = state.setValue(AGE, age + 1);
            }
            world.setBlock(pos, newState, 2);
            world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(newState));
        }
        super.randomTick(state, world, pos, random);
    }

    private boolean canGrowPlace(LevelReader world, BlockPos pos) {
        return world.getRawBrightness(pos, 0) > 9;
    }
}
