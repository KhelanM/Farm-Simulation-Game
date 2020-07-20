package market;

import inventory.MarketableInventoryObject;

/**
 * ItemPricePair stores the data for that is generated when an object can be sold.
 * The type of the plant and the price is stored
 */
public class ItemPricePair {
    private MarketableInventoryObject object;
    private double price;

    /**
     * Parameterized Constructor
     * Stores the kind of object and its price
     * @param object type of item
     * @param price item's Price
     */
    public ItemPricePair(MarketableInventoryObject object, double price) {
        this.object = object;
        this.price = price;
    }

    /**
     * Single argument Constructor to store the plant/seed
     * @param object MarketableInventoryObject
     */
    public ItemPricePair(MarketableInventoryObject object) {
        this(object, object.getPrice());
    }

    /**
     * Method to get the type of object
     * @return object
     */
    public MarketableInventoryObject getObject() {
        return object;
    }

    /**
     * @return the price of the plant/crop
     */
    public double getPrice() {
        return price;
    }
}
