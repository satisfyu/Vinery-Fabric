package net.satisfy.vinery.core.item;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.satisfy.vinery.core.entity.ModBoatEntity;
import net.satisfy.vinery.core.entity.ModChestBoatEntity;

import java.util.List;
import java.util.function.Predicate;
public class ModBoatItem extends Item {

    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);

    private final ModBoatEntity.Type type;
    private final boolean hasChest;

    public ModBoatItem(boolean pHasChest, ModBoatEntity.Type pType, Item.Properties pProperties) {
        super(pProperties);
        this.hasChest = pHasChest;
        this.type = pType;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

        ItemStack itemstack = player.getItemInHand(interactionHand);
        HitResult hitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);

        if (hitresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        }
        else {

            Vec3 vec3 = player.getViewVector(1.0F);
            List<Entity> list = level.getEntities(player, player.getBoundingBox().expandTowards(vec3.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);

            if (!list.isEmpty()) {

                Vec3 eyePos = player.getEyePosition();
                for(Entity entity : list) {
                    AABB aabb = entity.getBoundingBox().inflate((double)entity.getPickRadius());
                    if (aabb.contains(eyePos)) {
                        return InteractionResultHolder.pass(itemstack);
                    }
                }
            }
            if (hitresult.getType() == HitResult.Type.BLOCK) {
                Boat boat = this.getBoat(level, hitresult);

                if (boat instanceof ModChestBoatEntity chestBoat) {
                    chestBoat.setVariant(this.type);
                }
                else if (boat instanceof ModBoatEntity) {
                    ((ModBoatEntity)boat).setVariant(this.type);
                }

                boat.setYRot(player.getYRot());

                if (!level.noCollision(boat, boat.getBoundingBox())) {
                    return InteractionResultHolder.fail(itemstack);
                }
                else {

                    if (!level.isClientSide) {

                        level.addFreshEntity(boat);
                        level.gameEvent(player, GameEvent.ENTITY_PLACE, hitresult.getLocation());

                        if (!player.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
                }
            }
            else {
                return InteractionResultHolder.pass(itemstack);
            }
        }
    }

    private Boat getBoat(Level level, HitResult hitResult) {
        return (this.hasChest ? new ModChestBoatEntity(level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z) :
                new ModBoatEntity(level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z));
    }
}
