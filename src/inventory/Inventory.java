package inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * A class to represent an inventory for the player.
 * Should have an object of this stored in PlayerInfo.
 *
 * @author Team 23
 * @version 1.0
 */
public class Inventory {
    private ArrayList<InventoryObject> inventory;
    private final int maxCapacity;
    private int usedCapacity;

    /**
     * Constructor can be used to add a bunch of objects from a Collection to the inventory
     *
     * @param inventoryObjects a collection of inventoryObjects
     * @param maxCapacity      the max storage space
     */
    public Inventory(Collection<InventoryObject> inventoryObjects, int maxCapacity) {
        this.usedCapacity = 0;
        this.maxCapacity = maxCapacity;
        inventory = new ArrayList<>();
        if (inventoryObjects != null) {
            for (InventoryObject obj : inventoryObjects) {
                this.add(obj);
            }
        }
    }

    /**
     * maxStorage set at param, contains no items
     *
     * @param maxCapacity the max storage.
     */
    public Inventory(int maxCapacity) {
        this(null, maxCapacity);
    }

    /**
     * maxStorage is set at 100. Contains no Items.
     */
    public Inventory() {
        this(100);
    }

    /**
     * Adds InventoryObjects to seeds. If the objects already exists in the inventory, it
     * will just append the current object stored to reflect you adding more of that item.
     *
     * @param object object to add
     * @throws IllegalArgumentException if the object is null or
     * the object quantity will exceed the max inventory
     */
    public void add(InventoryObject object) {
        if (object == null) {
            throw new IllegalArgumentException("Error: object is null.");
        } else {
            if (maxCapacity < usedCapacity + object.getQuantity()) {
                String erMsg = "Error:Object quantity is too large, will exceed max storage space.";
                throw new IllegalArgumentException(erMsg);
            } else {
                setUsedCapacity(this.usedCapacity + object.getQuantity());
                if (inventory.contains(object)) {
                    int index = inventory.lastIndexOf(object);
                    InventoryObject newObject = inventory.remove(index);
                    newObject.setQuantity(newObject.getQuantity() + object.getQuantity());
                    inventory.add(newObject);
                } else {
                    inventory.add(object);
                }
            }
        }
    }

    /**
     * Will remove an inventory object from the inventory.
     * if the quantity of the object you enter is less than the stored amount,
     * it will only change the currently stored amount
     * to reflect the "amount" of seeds or whatever removed.
     *
     * @param object a InventoryObject implementation
     * @return the new object being held in the inventory
     * @throws IllegalArgumentException if the object is null,
     *                                  or if the request amount exceeds the amount in the
     *                                  inventory
     * @throws NoSuchElementException   if the object does not exist in the inventory
     */
    public InventoryObject remove(InventoryObject object) {
        if (object == null) {
            throw new IllegalArgumentException("Error: object is null");
        }
        InventoryObject inventoryObject;
        if (getQuantity(object) - object.getQuantity() < 0) {
            throw new IllegalArgumentException("Error: trying to remove too much of an Item.");
        } else if (!inventory.contains(object)) {
            throw new NoSuchElementException("Error: object does not exist in the inventory");
        } else if (getQuantity(object) - object.getQuantity() == 0) {
            int i = getQuantity(object);
            int index = inventory.lastIndexOf(object);
            inventoryObject = inventory.remove(index);
            setUsedCapacity(this.usedCapacity - object.getQuantity());
        } else {
            int index = inventory.lastIndexOf(object);
            InventoryObject newObject = inventory.remove(index);
            newObject.setQuantity(newObject.getQuantity() - object.getQuantity());
            inventory.add(newObject);
            setUsedCapacity(this.usedCapacity - object.getQuantity());
            inventoryObject = newObject;
        }
        return inventoryObject;
    }


    /**
     * Will get a inventory object inside the inventory.
     *
     * @param object the object to find
     * @return the object used to store the seed or plant
     * @throws IllegalArgumentException if the object is null
     * @throws NoSuchElementException   if the object is not in the inventory
     */
    public InventoryObject get(InventoryObject object) {
        if (object == null) {
            throw new IllegalArgumentException("Error: object is null");
        }
        if (inventory.contains(object)) {
            int index = inventory.lastIndexOf(object);
            return inventory.get(index);
        } else {
            throw new NoSuchElementException("Error: Object does not exist in the inventory");
        }
    }

    /**
     * similar to the other get, but does it based on index, for for loops and such.
     *
     * @param index the index of the inventory
     * @return The inventory object stored
     * @throws IndexOutOfBoundsException if the index is < 0 or is >= inventory.size()
     */
    public InventoryObject get(int index) {
        if (index < 0 || index >= inventory.size()) {
            throw new IndexOutOfBoundsException("Error: index is out of bounds");
        } else {
            return inventory.get(index);
        }
    }

    /**
     * Will return how much of a object you have
     *
     * @param object the InventoryObject to find quantity
     * @return the amount of the object inputted
     * @throws IllegalArgumentException if the object is null
     */
    public int getQuantity(InventoryObject object) {
        if (object == null) {
            throw new IllegalArgumentException("Error: Object is null");
        }
        if (inventory.contains(object)) {
            int index = inventory.lastIndexOf(object);
            return inventory.get(index).getQuantity();
        } else {
            return 0;
        }

    }

    /**
     * sets the available storage to 0 and clears the inventory.
     */
    public void clear() {
        inventory = new ArrayList<InventoryObject>();
        usedCapacity = 0;
    }

    /**
     * Will check if an object exists in the inventory.
     *
     * @param object the Inventory object to be checked
     * @throws IllegalArgumentException if the object is null
     * @return boolean value
     */
    public boolean has(InventoryObject object) {
        if (object == null) {
            throw new IllegalArgumentException("Error: object is null");
        }
        return inventory.contains(object);
    }

    /**
     * gets the size of the arraylist holding the inventory
     *
     * @return the size of the arraylist "inventory"
     */
    public int getSize() {
        return inventory.size();
    }

    /**
     * returns the current Capacity
     *
     * @return current capacity
     */
    public int getUsedCapacity() {
        return this.usedCapacity;
    }

    /**
     * Set used capacity
     *
     * @param usedCapacity use capacity
     */
    public void setUsedCapacity(int usedCapacity) {
        this.usedCapacity = usedCapacity;
    }

    /**
     * returns the max capacity
     *
     * @return max capacity
     */
    public int getMaxCapacity() {
        return this.maxCapacity;
    }
}
