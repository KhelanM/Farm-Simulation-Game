package basescreen;

import inventory.CropType;
import inventory.Plant;
import inventory.Plantable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import main.Main;
import main.PlayerInfo;

/**
 * A Object to represent each plot.
 *
 * @author Paul Case
 * @version 1.0
 */
public class Plot {
    private static final int MAX_WATER_LEVEL = 5;
    private static final int MIN_WATER_LEVEL = 0;
    private static boolean waterBtn = false;
    private static boolean seedBtn = false;
    private StackPane pane;
    private Text plotText = new Text();
    private Text waterLvl = new Text();
    private String seed;
    private Plantable armedSeed;
    private GrowthStatus isGrown;
    private int waterLevel;
    private int matureDay;
    private int deadDay;
    //creating Rectangle
    private Rectangle outPlot;

    /**
     * Complete Constructor. Takes in the seed type, and the x and y dimensions.
     *
     * @param seed seed type
     * @param x    width
     * @param y    height
     */
    public Plot(String seed, int x, int y) {
        isGrown = GrowthStatus.EMPTY;
        this.waterLevel = 0;
        this.seed = seed;

        // previously in getPlot
        pane = new StackPane();
        outPlot = new Rectangle();

        outPlot.setHeight(x);
        outPlot.setWidth(y);
        outPlot.setStroke(Color.BLACK);

        displayPlot(isGrown);

        waterLvl.setText("\n\nWater Level: " + getWaterLevel());
        pane.getChildren().addAll(outPlot, plotText, waterLvl);
        pane.setOnMouseClicked(event -> {
            if (isGrown.equals(GrowthStatus.MATURE) && !waterBtn && !seedBtn) {
                setSeed(this.seed);
                displayPlot(GrowthStatus.EMPTY);
                waterLvl.setText("\n\nWater Level: " + getWaterLevel());

                Main.PLAYER.inventory.add(new Plant(this.seed, CropType.findCropType(this.seed),
                        CropType.getBasePrice(CropType.findCropType(this.seed))));
            } else if (isGrown.equals(GrowthStatus.EMPTY)) {
                if (armedSeed != null) {
                    armedSeed.plant(this);
                }
            } else if (waterLevel >= 0) {
                waterPlants();
                waterLvl.setText("\n\nWater Level: " + getWaterLevel());
            }
            MainScreen.armSeedForPlanting(null);
        });
    }

    /**
     * Constructor. Takes in a seed type. Defaults to 100 by 100 for x and y.
     *
     * @param seed the seed type.
     */
    public Plot(String seed) {
        this(seed, 300, 300);
    }

    /**
     * Constructor. Default values are null for seed, 100 for x, and 100 for y.
     */
    public Plot() {
        this(null, 300, 300);
    }

    public static void setWaterBtn(boolean value) {
        waterBtn = value;
    }

    public static void setSeedBtn(boolean value) {
        seedBtn = value;
    }

    /**
     * returns the seed type.
     *
     * @return seed type
     */
    public String getSeed() {
        return seed;
    }

    /**
     * sets the seed type.
     *
     * @param seed the seed type
     */
    public void setSeed(String seed) {
        this.seed = seed;
    }

    /**
     * @return returns if the plot is grown
     */
    public GrowthStatus isGrown() {
        return isGrown;
    }

    /**
     * setter for if the plot is grown.
     *
     * @param grown if the plot is grown
     */
    public void setGrown(GrowthStatus grown) {
        if (seed != null) {
            isGrown = grown;
        } else {
            isGrown = GrowthStatus.EMPTY;
            displayPlot(isGrown);
        }
    }

    public int getDay() {
        return matureDay;
    }

    public void setDay(int x) {
        matureDay = x;
    }

    public int getWaterLevel() {
        if (this.waterLevel <= MIN_WATER_LEVEL && !isGrown.equals(GrowthStatus.EMPTY)) {
            //MarketUI.showAlert("Water Level running low", "Water your plants before they die");
            setGrown(GrowthStatus.DEAD);
            outPlot.setFill(Color.RED);
            plotText.setText("Dead Plant");
            setWaterLevel(waterLevelValues(GrowthStatus.DEAD));
        }
        return this.waterLevel;
    }

    public void setWaterLevel(int x) {
        this.waterLevel = x;
    }

    public boolean isFull() {
        return !isGrown.equals(GrowthStatus.EMPTY) && !isGrown.equals(GrowthStatus.DEAD);
    }

    /**
     * This method validates if the water button is pressed and plot is not empty
     * Two alert Messages: if the water Level exceeds 3, Excess Water Warning
     * if the water level reaches 5, then the plant dies. (Sets back to an empty plot)
     * Also an error message denying the user to water an empty plot
     */
    public void waterPlants() {
        if (waterBtn && isFull()) {
            setWaterLevel(getWaterLevel() + 1);
            if (getWaterLevel() > waterLevelValues(GrowthStatus.MATURE)
                    && getWaterLevel() < MAX_WATER_LEVEL) {
                PlayerInfo.showAlert("Too much Water!", "You are over watering your plants");
            } else if (getWaterLevel() >= MAX_WATER_LEVEL) {
                PlayerInfo.showAlert("That's it!", "Your plant is dead!");
                setGrown(GrowthStatus.DEAD);
                outPlot.setFill(Color.RED);
                plotText.setText("Dead Plant");
                setWaterLevel(waterLevelValues(GrowthStatus.DEAD));
            }

        } else if (!isFull() && waterBtn) {
            if (isGrown.equals(GrowthStatus.EMPTY)) {
                setWaterLevel(waterLevelValues(GrowthStatus.EMPTY));
                PlayerInfo.showAlert("Empty Plot", "You cant water an empty plot");
            } else {
                setWaterLevel(waterLevelValues(GrowthStatus.DEAD));
                PlayerInfo.showAlert("Dead Plant", "You cant water a dead plant");
            }
        }
    }

    /**
     * Will return a JavaFX Rectangle to be used to represent the plot. Gray
     * plot means it is unseeded, brown plot means it is seeded and not grown,
     * and green plot means it is seeded and grown.
     *
     * @return A Rectangle to represent the plot
     */
    public Pane getPlot() {
        updatePlot();
        return pane;
    }

    public void updatePlot() {
        switch (isGrown) {
        case EMPTY:
            outPlot.setFill(Color.GREY);
            plotText.setText("no plant");
            setWaterLevel(waterLevelValues(GrowthStatus.EMPTY));
            break;
        case SEED:
            outPlot.setFill(Color.BROWN);
            plotText.setText(this.seed + " seed");
            setWaterLevel(waterLevelValues(GrowthStatus.SEED));
            break;
        case IMMATURE:
            outPlot.setFill(Color.YELLOW);
            plotText.setText("immature " + this.seed + " plant");
            setWaterLevel(waterLevelValues(GrowthStatus.IMMATURE));
            break;
        case DEAD:
            outPlot.setFill(Color.RED);
            plotText.setText("Dead plant");
            setWaterLevel(waterLevelValues(GrowthStatus.DEAD));
            break;
        default:
            outPlot.setFill(Color.GREEN);
            plotText.setText("mature " + this.seed + " plant");
            break;
        }
        waterLvl.setText("\n\nWater Level: " + getWaterLevel());
    }

    /**
     * @param growthStatus growth status variable
     * @return the default water level values for each scenario.
     */
    public int waterLevelValues(GrowthStatus growthStatus) {
        if (growthStatus.equals(GrowthStatus.EMPTY) || growthStatus.equals(GrowthStatus.DEAD)) {
            return 0;
        } else if (growthStatus.equals(GrowthStatus.SEED)) {
            return 1;
        } else if (growthStatus.equals(GrowthStatus.IMMATURE)) {
            return 2;
        } else if (growthStatus.equals(GrowthStatus.MATURE)) {
            return 3;
        } else {
            return -1;
        }
    }

    public void displayPlot(GrowthStatus isGrown) {
        setGrown(isGrown);
        updatePlot();
    }

    public void armSeedForPlanting(Plantable seed) {
        armedSeed = seed;
    }
}
