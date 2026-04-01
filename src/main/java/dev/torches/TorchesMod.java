package dev.torches;

import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@Mod(TorchesMod.MOD_ID)
public class TorchesMod {
    public static final String MOD_ID = "torches";

    // -- registries --
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, MOD_ID);

    // -- blocks --
    public static final DeferredBlock<Block> STONE_TORCH = BLOCKS.register("stone_torch",
            () -> new TorchBlock(ParticleTypes.FLAME,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH)
                            .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> STONE_WALL_TORCH = BLOCKS.register("stone_wall_torch",
            () -> new WallTorchBlock(ParticleTypes.FLAME,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.WALL_TORCH)
                            .sound(SoundType.STONE)
                            .lootFrom(STONE_TORCH)));

    // -- items --
    public static final DeferredItem<Item> STONE_STICK = ITEMS.registerSimpleItem("stone_stick");

    public static final DeferredItem<Item> STONE_TORCH_ITEM = ITEMS.register("stone_torch",
            () -> new StandingAndWallBlockItem(
                    STONE_TORCH.get(), STONE_WALL_TORCH.get(),
                    new Item.Properties(), Direction.DOWN));

    public static final DeferredItem<Item> TORCH_ARROW_ITEM = ITEMS.register("torch_arrow",
            () -> new TorchArrowItem(new Item.Properties()));

    // -- entities --
    public static final Supplier<EntityType<TorchArrowEntity>> TORCH_ARROW_ENTITY =
            ENTITY_TYPES.register("torch_arrow",
                    () -> EntityType.Builder.<TorchArrowEntity>of(TorchArrowEntity::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(4)
                            .updateInterval(20)
                            .build("torch_arrow"));

    public TorchesMod(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        ENTITY_TYPES.register(modEventBus);
        modEventBus.addListener(this::addCreativeTabItems);
    }

    private void addCreativeTabItems(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(STONE_TORCH_ITEM);
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(STONE_STICK);
        }
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(TORCH_ARROW_ITEM);
        }
    }
}
