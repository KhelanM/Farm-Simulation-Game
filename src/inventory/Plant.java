package inventory;

import main.Main;
import main.PlayerInfo;
import market.NoDifficultyException;
import market.PriceConstants;

/**
 * A class to represent a Plant. a implementation of MarketableInventoryObject.
 *
 * @author Team 23
 * @version 1.0
 */
public class Plant extends MarketableInventoryObject {
    private final double basePrice;
    private CropType cropType;

    /**
     * Constructor that defaults quantity to 1.
     *
     * @param item       the string itemName
     * @param cropType   the cropType
     * @param basePrice the base price of the object
     */
    public Plant(String item, CropType cropType, double basePrice) {
        this(item, 1, cropType, basePrice);
    }

    /**
     * Main Constructor
     *
     * @param item       the string itemName
     * @param quantity   the amount of the object
     * @param cropType   the cropType
     * @param basePrice the base price of the object
     */
    public Plant(String item, int quantity, CropType cropType, double basePrice) {
        super(item, quantity, ObjectType.PLANT);
        this.cropType = cropType;
        this.basePrice = basePrice;
    }

    /**
     * returns the price of the object.
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
     * getter for the CropType
     * @return the cropType
     */
    public CropType getCropType() {
        return cropType;
    }

    /**
     * Checks if the CropTypes are the same, and if they are both Plants.
     * @param object the object to compare
     * @return the equality
     */
    public boolean equals(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Error: tried to compare an "
                    + "Seed to a null.");
        }
        if (object instanceof Plant) {
            Plant castedObject = (Plant) object;
            return castedObject.getCropType().equals(this.cropType);
        } else {
            return false;
        }
    }
}
