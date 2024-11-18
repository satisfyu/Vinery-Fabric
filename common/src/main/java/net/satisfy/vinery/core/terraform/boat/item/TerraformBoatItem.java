package net.satisfy.vinery.core.terraform.boat.item;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class TerraformBoatItem extends Item {
    private static final Predicate<Entity> RIDERS;
    private final ResourceLocation boatTypeName;
    private final boolean chest;

    public TerraformBoatItem(ResourceLocation boatTypeName, boolean chest, Item.Properties settings) {
        super(settings);
        this.boatTypeName = boatTypeName;
        this.chest = chest;
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        HitResult hitResult = Item.getPlayerPOVHitResult(world, user, Fluid.ANY);
        if (((HitResult)hitResult).getType() == Type.MISS) {
            return InteractionResultHolder.pass(stack);
        } else {
            Vec3 rotationVec = user.getViewVector(1.0F);
            List<Entity> riders = world.getEntities(user, user.getBoundingBox().expandTowards(rotationVec.scale(5.0)).inflate(1.0), RIDERS);
            if (!riders.isEmpty()) {
                Vec3 eyePos = user.getEyePosition();
                Iterator var9 = riders.iterator();

                while(var9.hasNext()) {
                    Entity entity = (Entity)var9.next();
                    AABB box = entity.getBoundingBox().inflate((double)entity.getPickRadius());
                    if (box.contains(eyePos)) {
                        return InteractionResultHolder.pass(stack);
                    }
                }
            }

            if (((HitResult)hitResult).getType() == Type.BLOCK) {
                double x = ((HitResult)hitResult).getLocation().x;
                double y = ((HitResult)hitResult).getLocation().y;
                double z = ((HitResult)hitResult).getLocation().z;
                Boat boatEntity = DoApiCommonEP.createBoat(this.boatTypeName, world, x, y, z, this.chest);
                boatEntity.setYRot(user.getYRot());
                if (!world.noCollision(boatEntity, boatEntity.getBoundingBox().inflate(-0.1))) {
                    return InteractionResultHolder.fail(stack);
                } else {
                    if (!world.isClientSide()) {
                        world.addFreshEntity(boatEntity);
                        world.gameEvent(user, GameEvent.ENTITY_PLACE, BlockPos.containing(((HitResult)hitResult).getLocation()));
                        if (!user.getAbilities().instabuild) {
                            stack.shrink(1);
                        }
                    }

                    user.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
                }
            } else {
                return InteractionResultHolder.pass(stack);
            }
        }
    }

    static {
        RIDERS = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    }
}

