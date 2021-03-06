package li.cil.tis3d.common.gui;

import li.cil.tis3d.common.init.Items;
import li.cil.tis3d.common.item.ItemModuleReadOnlyMemory;
import li.cil.tis3d.common.network.Network;
import li.cil.tis3d.common.network.message.MessageModuleReadOnlyMemoryData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

/**
 * GUI handler for the client side - which is, still, all we need.
 */
public class GuiHandlerCommon implements IGuiHandler {
    public enum GuiId {
        BOOK_MANUAL,
        BOOK_CODE,
        MODULE_TERMINAL,
        MODULE_MEMORY;

        public static final GuiId[] VALUES = values();
    }

    @Override
    @Nullable
    public Object getServerGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        switch (GuiId.VALUES[id]) {
            case MODULE_MEMORY:
                sendModuleMemory(player);
                break;
        }
        return null;
    }

    @Override
    @Nullable
    public Object getClientGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        return null;
    }

    // --------------------------------------------------------------------- //

    private void sendModuleMemory(final EntityPlayer player) {
        if (!(player instanceof EntityPlayerMP)) {
            return;
        }

        final ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);
        if (!Items.isModuleReadOnlyMemory(heldItem)) {
            return;
        }

        Network.INSTANCE.getWrapper().sendTo(new MessageModuleReadOnlyMemoryData(ItemModuleReadOnlyMemory.loadFromStack(heldItem)), (EntityPlayerMP) player);
    }
}
