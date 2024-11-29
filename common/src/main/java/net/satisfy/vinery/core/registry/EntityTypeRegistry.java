package net.satisfy.vinery.core.registry;

import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.satisfy.vinery.core.Vinery;
import net.satisfy.vinery.core.block.entity.*;
import net.satisfy.vinery.core.entity.*;
import net.satisfy.vinery.core.util.VineryIdentifier;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class EntityTypeRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Vinery.MOD_ID, Registries.BLOCK_ENTITY_TYPE);
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Vinery.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<StorageBlockEntity>> STORAGE_ENTITY = registerBlockEntity("storage", () -> BlockEntityType.Builder.of(StorageBlockEntity::new, StorageTypeRegistry.registerBlocks(new HashSet<>()).toArray(new Block[0])).build(null));
    public static final RegistrySupplier<BlockEntityType<FlowerPotBlockEntity>> FLOWER_POT_ENTITY = registerBlockEntity("flower_pot", () -> BlockEntityType.Builder.of(FlowerPotBlockEntity::new, StorageTypeRegistry.registerBlocks(new HashSet<>()).toArray(new Block[0])).build(null));
    public static final RegistrySupplier<BlockEntityType<CabinetBlockEntity>> CABINET_BLOCK_ENTITY = registerBlockEntity("cabinet", () -> BlockEntityType.Builder.of(CabinetBlockEntity::new, StorageTypeRegistry.registerBlocks(new HashSet<>()).toArray(new Block[0])).build(null));

    public static final RegistrySupplier<EntityType<ModBoatEntity>> MOD_BOAT =
            ENTITY_TYPES.register("mod_boat", () -> EntityType.Builder.<ModBoatEntity>of(ModBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("mod_boat"));
    public static final RegistrySupplier<EntityType<ModChestBoatEntity>> MOD_CHEST_BOAT =
            ENTITY_TYPES.register("mod_chest_boat", () -> EntityType.Builder.<ModChestBoatEntity>of(ModChestBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("mod_chest_boat"));

    public static final RegistrySupplier<BlockEntityType<ApplePressBlockEntity>> APPLE_PRESS_BLOCK_ENTITY = registerBlockEntity("apple_press", () -> BlockEntityType.Builder.of(ApplePressBlockEntity::new, ObjectRegistry.APPLE_PRESS.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<FermentationBarrelBlockEntity>> FERMENTATION_BARREL_ENTITY = registerBlockEntity("fermentation_barrel", () -> BlockEntityType.Builder.of(FermentationBarrelBlockEntity::new, ObjectRegistry.FERMENTATION_BARREL.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<BasketBlockEntity>> BASKET_ENTITY = registerBlockEntity("basket", () -> BlockEntityType.Builder.of(BasketBlockEntity::new, ObjectRegistry.BASKET.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CompletionistBannerEntity>> VINERY_STANDARD = registerBlockEntity("vinery_standard", () -> BlockEntityType.Builder.of(CompletionistBannerEntity::new, ObjectRegistry.VINERY_STANDARD.get(), ObjectRegistry.VINERY_WALL_STANDARD.get()).build(null));

    public static final RegistrySupplier<EntityType<TraderMuleEntity>> MULE = registerEntity("mule", () -> EntityType.Builder.of(TraderMuleEntity::new, MobCategory.CREATURE).sized(0.9f, 1.87f).clientTrackingRange(10).build(new VineryIdentifier("mule").toString()));
    public static final RegistrySupplier<EntityType<WanderingWinemakerEntity>> WANDERING_WINEMAKER = registerEntity("wandering_winemaker", () -> EntityType.Builder.of(WanderingWinemakerEntity::new, MobCategory.CREATURE).sized(0.6f, 1.95f).clientTrackingRange(10).build(new VineryIdentifier("wandering_winemaker").toString()));
    public static final RegistrySupplier<EntityType<ChairEntity>> CHAIR = registerEntity("chair", () -> EntityType.Builder.of(ChairEntity::new, MobCategory.MISC).sized(0.001F, 0.001F).build((new VineryIdentifier("chair")).toString()));

    public static <T extends EntityType<?>> RegistrySupplier<T> registerEntity(final String path, final Supplier<T> type) {
        return ENTITY_TYPES.register(new VineryIdentifier(path), type);
    }

    private static <T extends BlockEntityType<?>> RegistrySupplier<T> registerBlockEntity(final String path, final Supplier<T> type) {
        return BLOCK_ENTITY_TYPES.register(new VineryIdentifier(path), type);
    }

    static void registerAttributes() {
        EntityAttributeRegistry.register(MULE, () -> Llama.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.2f));
        EntityAttributeRegistry.register(WANDERING_WINEMAKER, WanderingWinemakerEntity::createMobAttributes);
    }

    public static void init() {
        ENTITY_TYPES.register();
        BLOCK_ENTITY_TYPES.register();
        registerAttributes();
    }
}
