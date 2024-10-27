package fr.shoqapik.btemobs.menu.provider;

import fr.shoqapik.btemobs.menu.BlacksmithCraftMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.Nullable;

public class BlacksmithCraftProvider implements MenuProvider {

    private final int entityId;

    public BlacksmithCraftProvider(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("BlackSmith");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        return new BlacksmithCraftMenu(p_39954_, p_39956_.getInventory(), entityId);
    }
}
