package inventory;

/**
 * This interface will be used by Objects that want to be sold in the market
 *
 * @author Team 23
 * @version 1.0
 */
public interface Marketable {
    /**
     * Will return a price of the Object
     *
     * @return the price
     */
    double getPrice();
}
