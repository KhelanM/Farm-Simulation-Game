package basescreen;

import javafx.scene.control.Alert;
import main.Main;
import market.NoDifficultyException;
import java.util.Random;

/**
 * A class to randomly create events to change plot states.
 *
 * @author Team 23
 * @version 1.0
 */
public class RandomEvent {
    private static final int RAIN_PERCENT = 10;
    private static final int DROUGHT_PERCENT = 5;
    private static final int LOCUSTS_PERCENT = 2;
    private static final String RAIN_WARNING =
            "There has been heavy rain which has increased water level in your plants!";
    private static final String DROUGHT_WARNING =
            "There has been a drought which has decreased water level in your plants!";
    private static final String LOCUSTS_WARNING =
            "Locusts have infiltrated your farm and killed some crops!";
    private final double updatedRainPercent;
    private final double updatedDroughtPercent;
    private final double updatedLocustsPercent;
    private final int randomEventChance;

    /**
     * Constructor calculates the chance of a random event occurring,
     */
    public RandomEvent() {
        double difficultyMultiplier = setPercentages();
        updatedRainPercent = RAIN_PERCENT * difficultyMultiplier;
        updatedDroughtPercent = (DROUGHT_PERCENT * difficultyMultiplier) + updatedRainPercent;
        updatedLocustsPercent = (LOCUSTS_PERCENT * difficultyMultiplier) + updatedDroughtPercent;
        randomEventChance = (int) (Math.random() * 100);
    }

    /**
     * Activates the event
     */
    public void trigger() {
        if (randomEventChance <= updatedRainPercent) {
            rain();
        } else if (randomEventChance <= updatedDroughtPercent) {
            drought();
        } else if (randomEventChance <= updatedLocustsPercent) {
            locusts();
        }

        MainScreen.updatePlots();
    }

    /**
     * Calculates chance multiplier based on difficulty.
     * @return difficulty multiplier
     */
    private static double setPercentages() {
        switch (Main.PLAYER.getDifficulty()) {
        case EASY:
            return 1.00;
        case MEDIUM:
            return 1.25;
        case HARD:
            return 1.50;
        default:
            throw new NoDifficultyException("Could not set difficulty multiplier.");
        }
    }

    /**
     * Rain event adds to water level of each plot.
     */
    private static void rain() {
        int random = randomLevel();
        for (Plot plot : MainScreen.getPlotArray()) {
            if (plot.getWaterLevel() > 0) {
                plot.setWaterLevel(plot.getWaterLevel() + random);
            }
        }
        showAlert(RAIN_WARNING);
    }

    /**
     * Drought event removes water level from each plot.
     */
    private static void drought() {
        int random = randomLevel();
        for (Plot plot : MainScreen.getPlotArray()) {
            if (plot.getWaterLevel() > 0) {
                int addVal = 0;
                if (plot.getWaterLevel() - random >= 0) {
                    addVal = plot.getWaterLevel() - random;
                }
                plot.setWaterLevel(addVal);
            }
        }
        showAlert(DROUGHT_WARNING);
    }

    /**
     * Locust event gives each plot a random chance to have its crops destroyed.
     */
    private static void locusts() {
        Random random = new Random();
        for (Plot plot : MainScreen.getPlotArray()) {
            if (random.nextBoolean()) {
                plot.setGrown(GrowthStatus.EMPTY);
            }
        }
        showAlert(LOCUSTS_WARNING);
    }

    /**
     * A random level of water
     * @return value from 1 to 3 inclusive
     */
    private static int randomLevel() {
        return (int) (Math.random() * 3) + 1;
    }

    /**
     *  Shows the player an alert that the event occurred.
     * @param eventType event warning message to player
     */
    private static void showAlert(String eventType) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Sorry!");
        alert.setHeaderText("Random Event");
        alert.setContentText(eventType);
        alert.showAndWait();
    }
}
