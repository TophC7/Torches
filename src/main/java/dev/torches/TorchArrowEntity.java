package dev.torches;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

public class TorchArrowEntity extends AbstractArrow {

    public TorchArrowEntity(EntityType<? extends TorchArrowEntity> type, Level level) {
        super(type, level);
    }

    public TorchArrowEntity(Level level, LivingEntity shooter, ItemStack pickupStack, @Nullable ItemStack weapon) {
        super(TorchesMod.TORCH_ARROW_ENTITY.get(), shooter, level, pickupStack, weapon);
    }

    // brief fire on hit
    // 1 second = 1 fire damage tick
    private static final int FIRE_SECONDS = 1;

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!level().isClientSide()) {
            result.getEntity().igniteForSeconds(FIRE_SECONDS);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!level().isClientSide()) {
            if (placeTorch(result)) {
                this.discard();
            }
        }
    }

    /** Try to place a torch at the hit location. Returns true if placed. */
    private boolean placeTorch(BlockHitResult result) {
        Level level = level();
        Direction face = result.getDirection();
        BlockPos placePos = result.getBlockPos().relative(face);

        // pick standing or wall torch based on which face was hit
        BlockState torchState;
        if (face == Direction.UP) {
            torchState = Blocks.TORCH.defaultBlockState();
        } else if (face != Direction.DOWN) {
            torchState = Blocks.WALL_TORCH.defaultBlockState()
                    .setValue(WallTorchBlock.FACING, face);
        } else {
            // can't hang a torch from a ceiling
            return false;
        }

        if (!level.getBlockState(placePos).canBeReplaced()) return false;
        if (!torchState.canSurvive(level, placePos)) return false;

        level.setBlock(placePos, torchState, 3);
        level.playSound(null, placePos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
        return true;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(TorchesMod.TORCH_ARROW_ITEM.get());
    }
}
