package net.satisfy.vinery.core.util;

import com.google.gson.JsonArray;
import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class GeneralUtil {

    public static final EnumProperty<LineConnectingType> LINE_CONNECTING_TYPE = EnumProperty.create("type", LineConnectingType.class);

    public static RotatedPillarBlock logBlock() {
        return new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG));
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(DeferredRegister<Block> registerB, Registrar<Block> registrarB, DeferredRegister<Item> registerI, Registrar<Item> registrarI, ResourceLocation name, Supplier<T> block) {
        RegistrySupplier<T> toReturn = registerWithoutItem(registerB, registrarB, name, block);
        registerItem(registerI, registrarI, name, () -> {
            return new BlockItem((Block)toReturn.get(), new Item.Properties());
        });
        return toReturn;
    }

    public static <T extends Block> RegistrySupplier<T> registerWithoutItem(DeferredRegister<Block> register, Registrar<Block> registrar, ResourceLocation path, Supplier<T> block) {
        return Platform.isForge() ? register.register(path.getPath(), block) : registrar.register(path, block);
    }

    public static <T extends Item> RegistrySupplier<T> registerItem(DeferredRegister<Item> register, Registrar<Item> registrar, ResourceLocation path, Supplier<T> itemSupplier) {
        return Platform.isForge() ? register.register(path.getPath(), itemSupplier) : registrar.register(path, itemSupplier);
    }



    public static Collection<ServerPlayer> tracking(ServerLevel world, ChunkPos pos) {
        Objects.requireNonNull(world, "The world cannot be null");
        Objects.requireNonNull(pos, "The chunk pos cannot be null");
        return world.getChunkSource().chunkMap.getPlayers(pos, false);
    }

    public static Collection<ServerPlayer> tracking(ServerLevel world, BlockPos pos) {
        Objects.requireNonNull(pos, "BlockPos cannot be null");
        return tracking(world, new ChunkPos(pos));
    }

    public static void popResourceFromFace(Level level, BlockPos blockPos, Direction side, ItemStack itemStack) {
        BlockState blockState = level.getBlockState(blockPos);
        double itemWidth = (double) EntityType.ITEM.getWidth();
        double itemHeight = (double)EntityType.ITEM.getHeight();
        VoxelShape shape = blockState.getCollisionShape(level, blockPos);
        double posX = (double)blockPos.getX() + 0.5;
        double posY = (double)blockPos.getY() + 0.5;
        double posZ = (double)blockPos.getZ() + 0.5;
        double offsetX = 0.0;
        double offsetY = 0.0;
        double offsetZ = 0.0;
        switch (side) {
            case DOWN:
                posY = (double)blockPos.getY() - shape.min(Direction.Axis.Y);
                offsetY = -itemHeight * 2.0;
                break;
            case UP:
                posY = (double)blockPos.getY() + shape.max(Direction.Axis.Y);
                break;
            case NORTH:
                posZ = (double)blockPos.getZ() + shape.min(Direction.Axis.Z);
                offsetZ = -itemWidth;
                break;
            case SOUTH:
                posZ = (double)blockPos.getZ() + shape.max(Direction.Axis.Z);
                offsetZ = itemWidth;
                break;
            case WEST:
                posX = (double)blockPos.getX() + shape.min(Direction.Axis.X);
                offsetX = -itemWidth;
                break;
            case EAST:
                posX = (double)blockPos.getX() + shape.max(Direction.Axis.X);
                offsetX = itemWidth;
        }

        int i = side.getStepX();
        int j = side.getStepY();
        int k = side.getStepZ();
        double deltaX = i == 0 ? Mth.nextDouble(level.random, -0.1, 0.1) : (double)i * 0.1;
        double deltaY = j == 0 ? Mth.nextDouble(level.random, 0.0, 0.1) : (double)j * 0.1 + 0.1;
        double deltaZ = k == 0 ? Mth.nextDouble(level.random, -0.1, 0.1) : (double)k * 0.1;
        popResource(level, new ItemEntity(level, posX + offsetX, posY + offsetY, posZ + offsetZ, itemStack, deltaX, deltaY, deltaZ), itemStack);
    }

    private static void popResource(Level level, ItemEntity itemEntity, ItemStack itemStack) {
        if (!level.isClientSide && !itemStack.isEmpty() && level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
            itemEntity.setDefaultPickUpDelay();
            level.addFreshEntity(itemEntity);
        }
    }

    public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{shape, Shapes.empty()};
        int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;

        for(int i = 0; i < times; ++i) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                buffer[1] = Shapes.joinUnoptimized(buffer[1], Shapes.box(1.0 - maxZ, minY, minX, 1.0 - minZ, maxY, maxX), BooleanOp.OR);
            });
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        return buffer[0];
    }

    public static Optional<Tuple<Float, Float>> getRelativeHitCoordinatesForBlockFace(BlockHitResult blockHitResult, Direction direction, Direction[] unAllowedDirections) {
        Direction direction2 = blockHitResult.getDirection();
        if (Arrays.stream(unAllowedDirections).toList().contains(direction2)) {
            return Optional.empty();
        } else if (direction != direction2 && direction2 != Direction.UP && direction2 != Direction.DOWN) {
            return Optional.empty();
        } else {
            BlockPos blockPos = blockHitResult.getBlockPos().relative(direction2);
            Vec3 vec3 = blockHitResult.getLocation().subtract((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ());
            float d = (float)vec3.x();
            float f = (float)vec3.z();
            float y = (float)vec3.y();
            if (direction2 == Direction.UP || direction2 == Direction.DOWN) {
                direction2 = direction;
            }

            Optional var10000;
            switch (direction2) {
                case NORTH:
                    var10000 = Optional.of(new Tuple((float)(1.0 - (double)d), y));
                    break;
                case SOUTH:
                    var10000 = Optional.of(new Tuple(d, y));
                    break;
                case WEST:
                    var10000 = Optional.of(new Tuple(f, y));
                    break;
                case EAST:
                    var10000 = Optional.of(new Tuple((float)(1.0 - (double)f), y));
                    break;
                case DOWN:
                case UP:
                    var10000 = Optional.empty();
                    break;
                default:
                    throw new IncompatibleClassChangeError();
            }

            return var10000;
        }
    }

    public static boolean isSolid(LevelReader levelReader, BlockPos blockPos) {
        return levelReader.getBlockState(blockPos.below()).isSolid();
    }

    public static NonNullList<Ingredient> deserializeIngredients(JsonArray json) {
        NonNullList<Ingredient> ingredients = NonNullList.create();

        for(int i = 0; i < json.size(); ++i) {
            Ingredient ingredient = Ingredient.fromJson(json.get(i));
            if (!ingredient.isEmpty()) {
                ingredients.add(ingredient);
            }
        }

        return ingredients;
    }

    public static ItemStack convertStackAfterFinishUsing(LivingEntity entity, ItemStack used, Item returnItem, Item usedItem) {
        if (entity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, used);
            serverPlayer.awardStat(Stats.ITEM_USED.get(usedItem));
        }

        if (used.isEmpty()) {
            return new ItemStack(returnItem);
        } else {
            if (entity instanceof Player) {
                Player player = (Player)entity;
                if (!((Player)entity).getAbilities().instabuild) {
                    ItemStack itemStack2 = new ItemStack(returnItem);
                    if (!player.getInventory().add(itemStack2)) {
                        player.drop(itemStack2, false);
                    }
                }
            }

            return used;
        }
    }

    public static enum LineConnectingType implements StringRepresentable {
        NONE("none"),
        MIDDLE("middle"),
        LEFT("left"),
        RIGHT("right");

        private final String name;

        private LineConnectingType(String type) {
            this.name = type;
        }

        public @NotNull String getSerializedName() {
            return this.name;
        }
    }
}
