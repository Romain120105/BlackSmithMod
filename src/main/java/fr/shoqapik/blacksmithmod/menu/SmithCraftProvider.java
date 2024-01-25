package fr.shoqapik.blacksmithmod.menu;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.Nullable;

public class SmithCraftProvider implements MenuProvider {


    @Override
    public Component getDisplayName() {
        return Component.literal("BlackSmith");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        return new SmithCraftMenu(p_39954_, p_39956_.getInventory());
    }
}
