package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:glass_pane", id = 102)
public class ItemGlassPane extends ItemStack implements io.gomint.inventory.item.ItemGlassPane {

    @Override
    public ItemType getItemType() {
        return ItemType.GLASS_PANE;
    }

}
