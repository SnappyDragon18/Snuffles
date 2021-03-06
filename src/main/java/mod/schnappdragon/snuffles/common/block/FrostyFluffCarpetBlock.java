package mod.schnappdragon.snuffles.common.block;

import mod.schnappdragon.snuffles.core.registry.SnufflesBlocks;
import mod.schnappdragon.snuffles.core.registry.SnufflesParticleTypes;
import mod.schnappdragon.snuffles.core.registry.SnufflesSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class FrostyFluffCarpetBlock extends CarpetBlock {
    protected static final AABB TOUCH_AABB = new AABB(0.0D, 0.0625D, 0.0D, 1.0D, 0.125D, 1.0D);

    public FrostyFluffCarpetBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onCaughtFire(BlockState state, Level world, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        world.setBlock(pos, SnufflesBlocks.SNUFFLE_FLUFF_CARPET.get().defaultBlockState(), 2);
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (!world.isClientSide) {
            AABB aabb = TOUCH_AABB.move(pos);
            if (world.getEntities(null, aabb).contains(entity)) {
                if (!entity.isSteppingCarefully() && (entity.xOld != entity.getX() || entity.yOld != entity.getY() || entity.zOld != entity.getZ()) && world.getRandom().nextFloat() <= 0.3F)
                    ((ServerLevel) world).sendParticles(SnufflesParticleTypes.SNOWFLAKE.get(), entity.getX(), entity.getY(), entity.getZ(), 0, Mth.randomBetween(world.getRandom(), -1.0F, 1.0F) * 0.083F, 0.05F, Mth.randomBetween(world.getRandom(), -1.0F, 1.0F) * 0.083F, 1.0F);
            }
        }

        super.entityInside(state, world, pos, entity);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(Items.MAGMA_CREAM)) {
            if (!world.isClientSide) {
                if (!player.isCreative())
                    stack.shrink(1);

                world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                world.setBlockAndUpdate(pos, SnufflesBlocks.SNUFFLE_FLUFF_CARPET.get().defaultBlockState());
                world.playSound(null, pos, SnufflesSoundEvents.FROSTY_FLUFF_THAW.get(), SoundSource.BLOCKS, 0.7F, 1.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.4F);
            }

            return InteractionResult.sidedSuccess(world.isClientSide);
        }

        return super.use(state, world, pos, player, hand, hitResult);
    }
}