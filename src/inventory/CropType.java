package inventory;

import market.PriceConstants;

/**
 * A enum to tell what a plant or a seed type is.
 *
 * @author Team 23
 * @version 1.0
 */
public enum CropType {
    APPLE, CORN, WHEAT;

    /**
     * This will look up the base price of a certain CropType and return it.
     *
     * @param cropType A CropType to find the base price of
     * @return the base price
     * @throws IllegalArgumentException if the CropType is not found
     */
    public static double getBasePrice(CropType cropType) {
        if (cropType.equals(APPLE)) {
            return PriceConstants.APPLE_SEED_PRICE;
        } else if (cropType.equals(WHEAT)) {
            return PriceConstants.WHEAT_SEED_PRICE;
        } else if (cropType.equals(CORN)) {
            return PriceConstants.CORN_SEED_PRICE;
        } else {
            throw new IllegalArgumentException("Error: could not find that cropType");
        }
    }

    /**
     * this will return the CropType based on a inputted string
     *
     * @param name a string form of the enum (eg. "apple")
     * @return the CropType
     * @throws IllegalArgumentException if the CropType is not found
     */
    public static CropType findCropType(String name) {
        try {
            return CropType.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
}
