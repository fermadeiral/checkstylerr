package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemMushroomStew extends ItemFood {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemMushroomStew create( int amount ) {
        return GoMint.instance().createItemStack( ItemMushroomStew.class, amount );
    }

}
