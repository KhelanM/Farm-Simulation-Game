package inventory;

import basescreen.GrowthStatus;
import basescreen.Plot;
import main.Main;
import main.PlayerInfo;
import market.NoDifficultyException;
import market.PriceConstants;

/**
 * A class to represent a Seed object. extends the MarketableInventoryObject
 * and Implements the plantable interface.
 *
 * @author Team 23
 * @version 1.0
 */
public class Seed extends MarketableInventoryObject implements Plantable {
    private final double basePrice;
    private CropType cropType;

    /**
     * Constructor that defaults quantity to 1.
     *
     * @param item       the string itemName
     * @param cropType   the cropType
     * @param basePrice the base price
     */
    public Seed(String item, CropType cropType, double basePrice) {
        this(item, 1, cropType, basePrice);
    }

    /**
     * Main Constructor
     *
     * @param item       the string itemName
     * @param quantity   the amount
     * @param cropType   the cropType
     * @param basePrice the base price
     */
    public Seed(String item, int quantity, CropType cropType, double basePrice) {
        super(item, quantity, ObjectType.SEED);
        this.cropType = cropType;
        this.basePrice = basePrice;
    }

    /**
     * getter for CropType
     *
     * @return the CropType
     */
    public CropType getCropType() {
        return cropType;
    }

    /**
     * plants the seed and removes 1 seed from the inventory.
     *
     * @param plot the Plot to be planted in
     */
    public void plant(Plot plot) {
        plot.setSeed(super.getItemName());
        plot.setGrown(GrowthStatus.SEED);
        plot.updatePlot();
        Main.PLAYER.inventory.remove(new Seed(super.getItemName(), cropType, basePrice));
    }

    /**
     * will return the price.
     *
     * @return the price
     */
    @Override
    public double getPrice() {
        PlayerInfo.Difficulty diff = Main.PLAYER.getDifficulty();
        double multiplier;
        if (diff == PlayerInfo.Difficulty.EASY) {
            multiplier = PriceConstants.EASY_MULTIPLIER;
        } else if (diff == PlayerInfo.Difficulty.MEDIUM) {
            multiplier = PriceConstants.MED_MULTIPLIER;
        } else if (diff == PlayerInfo.Difficulty.HARD) {
            multiplier = PriceConstants.HARD_MULTIPLIER;
        } else {
            throw new NoDifficultyException("Error: Couldn't find a difficulty");
        }
        return this.basePrice * multiplier + Math.round(Math.random() * PriceConstants.VARIANCE
                - (PriceConstants.VARIANCE / 2));
    }

    /**
     * Checks if the CropType is the same, and if they are both seeds.
     *
     * @param object the object to compare
     * @return the equality
     */
    public boolean equals(Object object) {

        if (object == null) {
            throw new IllegalArgumentException("Error: tried to compare an "
                    + "Seed to a null.");
        }

        if (object instanceof Seed) {
            System.out.println();
            Seed castedObject = (Seed) object;
            return castedObject.getCropType().equals(this.cropType);
        } else {
            return false;
        }
    }
}
