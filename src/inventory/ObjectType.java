package inventory;

/**
 * this enum is used by the InventoryObject to distinguish between different types of Object
 * (eg. PLANT or SEED).
 *
 * @author Team 23
 * @version 1.0
 */
public enum ObjectType {
    PLANT, SEED;

    /**
     * Generates a string representation.
     *
     * @return a string representation.
     */
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
