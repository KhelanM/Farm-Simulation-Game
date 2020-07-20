package inventory;

/**
 * This abstract class will be implemented by InventoryObject classes
 * wanting to be sold in the market.
 *
 * @author Team 23
 * @version 1.0
 */
public abstract class MarketableInventoryObject extends InventoryObject implements Marketable {


    /**
     * Main Constructor.
     *
     * @param itemName   the string itemName
     * @param quantity   the amount
     * @param objectType the objectType
     */
    public MarketableInventoryObject(String itemName, int quantity, ObjectType objectType) {
        super(itemName, quantity, objectType);
    }

    /**
     * will return a price of the Object
     *
     * @return price
     */
    public abstract double getPrice();
}
