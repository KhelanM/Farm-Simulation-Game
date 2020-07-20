package main;

import inventory.Inventory;
import inventory.Seed;
import javafx.scene.control.Alert;
import market.NoDifficultyException;

/**
 * PlayerInfo Class stores the data that is generated from the initial Config UI.
 * Has some default values for name, seedType, difficultyLevel.
 */
public class PlayerInfo {

    public static final String DEFAULT_NAME = "Billy Bob Joe";
    public final Inventory inventory = new Inventory(100);
    private String name;
    private Seed initSeed;
    private Difficulty difficulty;
    private float money;

    /**
     * Default constructor allows setting of fields later
     */
    public PlayerInfo() {
    }

    /**
     * Constructor sets name, difficulty, and starting seed attributes
     *
     * @param name       player name
     * @param difficulty game difficulty
     * @param initSeed   initial seed type
     */
    public PlayerInfo(String name, Difficulty difficulty, Seed initSeed) {
        this.name = name;
        this.difficulty = difficulty;
        this.initSeed = initSeed;
    }

    /**
     * Displays a player-specific pop-up
     *
     * @param title   title string
     * @param message message for player
     */
    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * @return player name
     */
    public String getName() {
        return name;
    }

    /**
     * Set player name
     *
     * @param name new name
     */
    public void setName(String name) {
        this.name = name.isEmpty() ? DEFAULT_NAME : name;
    }

    /**
     * @return game difficulty
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Set game difficulty
     *
     * @param difficulty game difficulty
     */
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * @return player money
     */
    public float getMoney() {
        return money;
    }

    /**
     * Set player money
     *
     * @param money new player money
     */
    public void setMoney(float money) {
        this.money = money;
    }

    /**
     * @return initial player seed
     */
    public Seed getInitSeed() {
        return this.initSeed;
    }

    /**
     * set initial player seed
     *
     * @param seed initial player seed
     */
    public void setInitSeed(Seed seed) {
        this.initSeed = seed;
    }

    /**
     * @return starting funds based on difficulty
     */
    public float getStartingMoney() {
        switch (difficulty) {
        case EASY:
            money = 100;
            return money;
        case MEDIUM:
            money = 50;
            return money;
        case HARD:
            money = 30;
            return money;
        default:
            throw new NoDifficultyException("Could not set starting money.");
        }
    }

    /**
     * Possible game difficulties
     */
    public enum Difficulty {
        EASY, MEDIUM, HARD
    }
}
