package inventory;

import basescreen.Plot;

/**
 * An interface for a class that can be planted into a plot.
 * @author Team 23
 * @version 1.0
 */
public interface Plantable {

    /**
     * A method to implement the planting.
     * @param plot the Plot to be planted in
     */
    void plant(Plot plot);
}
