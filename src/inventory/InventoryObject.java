package inventory;

/**
 * An abstract class for holding Items in the inventory
 *
 * @author Team 23
 * @version 1.0
 */
public abstract class InventoryObject implements Cloneable {

    private String itemName;
    private int quantity;
    private ObjectType objectType;

    /**
     * Main Constructor.
     *
     * @param itemName   the item's name
     * @param quantity   the amount of the item
     * @param objectType the ObjectType of the object(eg. SEED, PLANT)
     */
    public InventoryObject(String itemName, int quantity, ObjectType objectType) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.objectType = objectType;
    }

    /**
     * getter for itemName.
     *
     * @return the item name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * setter for itemName.
     *
     * @param itemName the new itemName
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * getter for objectType
     *
     * @return the object type
     */
    public ObjectType getObjectType() {
        return objectType;
    }

    /**
     * getter for quantity.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * setter for quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * checks if the itemName and the ObjectType are the same.
     *
     * @param object the object to compare
     * @return if they are equal
     */
    public boolean equals(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Error: tried to compare an "
                    + "InventoryObject to a null.");
        }
        if (object instanceof InventoryObject) {
            InventoryObject castedObject = (InventoryObject) object;
            boolean isName = castedObject.getItemName().equals(this.itemName);
            boolean isType = castedObject.getObjectType().equals(this.objectType);
            return isName && isType;
        } else {
            return false;
        }
    }

    /**
     * generates a string representation.
     *
     * @return the string representation
     */
    public String toString() {
        String desc = quantity + " " + itemName + " " + objectType;
        if (quantity > 1) {
            desc += "s";
        }
        return desc;
    }

    public Object clone() {
        Object object = null;
        try {
            object = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return object;
    }
}