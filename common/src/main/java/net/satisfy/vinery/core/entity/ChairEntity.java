package net.satisfy.vinery.core.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.satisfy.vinery.core.util.ChairUtil;

public class ChairEntity extends Entity {
    public ChairEntity(EntityType<?> type, Level world) {
        super(type, world);
    }

    protected void defineSynchedData() {
    }

    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        if (passenger instanceof Player p) {
            BlockPos pos = ChairUtil.getPreviousPlayerPosition(p, this);
            if (pos != null) {
                this.discard();
                return new Vec3((double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5);
            }
        }

        this.discard();
        return super.getDismountLocationForPassenger(passenger);
    }

    public void remove(Entity.RemovalReason reason) {
        super.remove(reason);
        ChairUtil.removeChairEntity(this.level(), this.blockPosition());
    }

    protected void readAdditionalSaveData(CompoundTag nbt) {
    }

    protected void addAdditionalSaveData(CompoundTag nbt) {
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}

