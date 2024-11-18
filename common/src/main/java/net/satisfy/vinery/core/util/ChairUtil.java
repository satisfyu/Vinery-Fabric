package net.satisfy.vinery.core.util;

import com.mojang.datafixers.util.Pair;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.satisfy.vinery.core.entity.ChairEntity;
import net.satisfy.vinery.core.registry.EntityTypeRegistry;

public class ChairUtil {
    private static final Map<ResourceLocation, Map<BlockPos, Pair<ChairEntity, BlockPos>>> CHAIRS = new HashMap();

    public ChairUtil() {
    }

    public static InteractionResult onUse(Level world, Player player, InteractionHand hand, BlockHitResult hit, double extraHeight) {
        if (world.isClientSide) {
            return InteractionResult.PASS;
        } else if (player.isShiftKeyDown()) {
            return InteractionResult.PASS;
        } else if (isPlayerSitting(player)) {
            return InteractionResult.PASS;
        } else if (hit.getDirection() == Direction.DOWN) {
            return InteractionResult.PASS;
        } else {
            BlockPos hitPos = hit.getBlockPos();
            if (!isOccupied(world, hitPos) && player.getItemInHand(hand).isEmpty()) {
                ChairEntity chair = (ChairEntity)((EntityType) EntityTypeRegistry.CHAIR.get()).create(world);
                chair.moveTo((double)hitPos.getX() + 0.5, (double)hitPos.getY() + 0.25 + extraHeight, (double)hitPos.getZ() + 0.5, 0.0F, 0.0F);
                if (addChairEntity(world, hitPos, chair, player.blockPosition())) {
                    world.addFreshEntity(chair);
                    player.startRiding(chair);
                    return InteractionResult.SUCCESS;
                }
            }

            return InteractionResult.PASS;
        }
    }

    public static void onStateReplaced(Level world, BlockPos pos) {
        if (!world.isClientSide) {
            ChairEntity entity = getChairEntity(world, pos);
            if (entity != null) {
                removeChairEntity(world, pos);
                entity.ejectPassengers();
            }
        }

    }

    public static boolean addChairEntity(Level world, BlockPos blockPos, ChairEntity entity, BlockPos playerPos) {
        if (!world.isClientSide) {
            ResourceLocation id = getDimensionTypeId(world);
            if (!CHAIRS.containsKey(id)) {
                CHAIRS.put(id, new HashMap());
            }

            ((Map)CHAIRS.get(id)).put(blockPos, Pair.of(entity, playerPos));
            return true;
        } else {
            return false;
        }
    }

    public static boolean removeChairEntity(Level world, BlockPos pos) {
        if (!world.isClientSide) {
            ResourceLocation id = getDimensionTypeId(world);
            if (CHAIRS.containsKey(id)) {
                ((Map)CHAIRS.get(id)).remove(pos);
                return true;
            }
        }

        return false;
    }

    public static ChairEntity getChairEntity(Level world, BlockPos pos) {
        if (!world.isClientSide()) {
            ResourceLocation id = getDimensionTypeId(world);
            if (CHAIRS.containsKey(id) && ((Map)CHAIRS.get(id)).containsKey(pos)) {
                return (ChairEntity)((Pair)((Map)CHAIRS.get(id)).get(pos)).getFirst();
            }
        }

        return null;
    }

    public static BlockPos getPreviousPlayerPosition(Player player, ChairEntity chairEntity) {
        if (!player.level().isClientSide()) {
            ResourceLocation id = getDimensionTypeId(player.level());
            if (CHAIRS.containsKey(id)) {
                Iterator var3 = ((Map)CHAIRS.get(id)).values().iterator();

                while(var3.hasNext()) {
                    Pair<ChairEntity, BlockPos> pair = (Pair)var3.next();
                    if (pair.getFirst() == chairEntity) {
                        return (BlockPos)pair.getSecond();
                    }
                }
            }
        }

        return null;
    }

    public static boolean isOccupied(Level world, BlockPos pos) {
        ResourceLocation id = getDimensionTypeId(world);
        return CHAIRS.containsKey(id) && ((Map)CHAIRS.get(id)).containsKey(pos);
    }

    public static boolean isPlayerSitting(Player player) {
        Iterator var1 = CHAIRS.keySet().iterator();

        while(var1.hasNext()) {
            ResourceLocation i = (ResourceLocation)var1.next();
            Iterator var3 = ((Map)CHAIRS.get(i)).values().iterator();

            while(var3.hasNext()) {
                Pair<ChairEntity, BlockPos> pair = (Pair)var3.next();
                if (((ChairEntity)pair.getFirst()).hasPassenger(player)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static ResourceLocation getDimensionTypeId(Level world) {
        return world.dimensionTypeId().location();
    }
}

