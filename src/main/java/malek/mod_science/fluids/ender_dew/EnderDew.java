package malek.mod_science.fluids.ender_dew;

import malek.mod_science.fluids.BaseFluid;
import malek.mod_science.fluids.ModBuckets;
import malek.mod_science.fluids.ModFluidBlocks;
import malek.mod_science.fluids.ModFluids;
import malek.mod_science.fluids.rewater.Rewater;
import malek.mod_science.util.general.LoggerInterface;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnderDew extends BaseFluid implements LoggerInterface {




    @Override
    public Fluid getFlowing() {
        return ModFluids.FLOWING_ENDER_DEW;
    }

    @Override
    public Fluid getStill() {
        return ModFluids.STILL_ENDER_DEW;
    }

    @Override
    public Item getBucketItem() {
        return ModBuckets.ENDER_DEW_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return ModFluidBlocks.ENDER_DEW.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));
    }

    @Override
    public boolean isStill(FluidState state) {
        return false;
    }

    @Override
    public int getLevel(FluidState state) {
        return 0;
    }

    public static class Flowing extends EnderDew {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
    }

    public static class Still extends EnderDew {
        @Override
        public int getLevel(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }
    public Logger getLogger() {
        return LogManager.getLogger();
    }
}
