package rmc.mixins.ensorcellation_guard.inject;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.google.common.collect.ImmutableList;

import cofh.lib.util.helpers.AreaEffectHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rmc.libs.event_factory.EventFactory;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
@Mixin(value = AreaEffectHelper.class)
public abstract class AreaEffectHelperMixin {

    @Inject(method = "Lcofh/lib/util/helpers/AreaEffectHelper;getTillableBlocksRadius(Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;I)Lcom/google/common/collect/ImmutableList;",
            remap = false,
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "TAIL"))
    private static void guardTillableRadius(ItemStack stack, BlockPos pos, PlayerEntity player, int radius, CallbackInfoReturnable<ImmutableList<BlockPos>> mixin, List<BlockPos> area, World world) {
        for (BlockPos bpos : area) {
            if (!EventFactory.testBlockBreak(EventFactory.convert(player), world, bpos)) {
                mixin.setReturnValue(ImmutableList.of());
                break;
            }
        }
    }

    @Inject(method = "Lcofh/lib/util/helpers/AreaEffectHelper;getTillableBlocksLine(Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;I)Lcom/google/common/collect/ImmutableList;",
            remap = false,
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "TAIL"))
    private static void guardTillableLine(ItemStack stack, BlockPos pos, PlayerEntity player, int length, CallbackInfoReturnable<ImmutableList<BlockPos>> mixin, List<BlockPos> area, World world) {
        for (BlockPos bpos : area) {
            if (!EventFactory.testBlockBreak(EventFactory.convert(player), world, bpos)) {
                mixin.setReturnValue(ImmutableList.of());
                break;
            }
        }
    }

}