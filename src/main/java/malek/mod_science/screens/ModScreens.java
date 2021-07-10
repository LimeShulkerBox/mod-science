package malek.mod_science.screens;

import malek.mod_science.blocks.ModBlocks;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import static malek.mod_science.ModScience.MOD_ID;

public class ModScreens {
    public static ScreenHandlerType<TranfusionMatrixGuiDescription> TRANSFUSION_MATRIX_SCREEN_HANDLER;
    public static void init() {
        TRANSFUSION_MATRIX_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(MOD_ID, "transfusion_matrix"), (syncId, inventory) -> new TranfusionMatrixGuiDescription(syncId, inventory, ScreenHandlerContext.EMPTY));
    }
}