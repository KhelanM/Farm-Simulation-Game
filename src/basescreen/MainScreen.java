package basescreen;

import inventory.InventoryScene;
import inventory.Plantable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import main.Main;

import java.util.Random;

/**
 * Main Screen for the Game.
 *
 * @author Paul Case
 * @version 1.0
 */
public class MainScreen {
    private static Text moneyText;
    private static HBox topUI;
    private static int day = 1;
    private static String dayString;
    private static Plot[] plotArray;
    private static BorderPane borderPane = new BorderPane();
    public static final Scene SCENE = init();

    /**
     * Will return a main scene to be put in a stage. Use MAIN_SCENE to access.
     *
     * @return The Main scene
     */
    private static Scene init() {
        //Setting base values
        //int startTime = 0;
        //int time = startTime;
        double moneyNum;
        //checking the difficulty to determine how much money to start with.
        moneyNum = Main.PLAYER.getStartingMoney();
        //main pane

        //creating top HUD
        topUI = new HBox(20);
        //Simple Money String
        String moneyString = "$" + moneyNum;
        //Turning day from int to string
        if (day % 10 >= 4 || day % 10 == 0) {
            dayString = day + "th";
        } else if (day % 10 == 1) {
            dayString = day + "st";
        } else if (day % 10 == 2) {
            dayString = day + "nd";
        } else {
            dayString = day + "rd";
        }
        String dateString = dayString + " day";
        //Turning time to string
        //int hour = time / (3600);
        //int min = time % (60 * 60) / 60;
        //int sec = time - hour * 3600 - min * 60;
        //String timeString = hour + ":" + min + ":" + sec;
        String nameString = Main.PLAYER.getName();
        //Making Text Objects for each node in the HUD
        Text nameText = new Text(nameString);
        Text dateText = new Text(dateString);
        //Text timeText = new Text(timeString);
        moneyText = new Text(moneyString);
        //adding to the topUI
        topUI.getChildren().addAll(nameText, dateText, moneyText);
        //creating GridPane for the Plots
        GridPane plots = new GridPane();
        //Generating plots to be added
        plotArray = new Plot[12];
        for (int i = 0; i < 12; i++) {

            Plot newPlot = new Plot(Main.PLAYER.getInitSeed().getItemName());
            Random rand = new Random();
            int growthNum = rand.nextInt(4);
            switch (growthNum) {
            case 0:
                newPlot.setGrown(GrowthStatus.EMPTY);
                newPlot.setWaterLevel(0);
                break;
            case 1:
                newPlot.setGrown(GrowthStatus.SEED);
                newPlot.setWaterLevel(1);
                break;
            case 2:
                newPlot.setGrown(GrowthStatus.IMMATURE);
                newPlot.setWaterLevel(2);
                break;
            default:
                newPlot.setGrown(GrowthStatus.MATURE);
                newPlot.setWaterLevel(3);
                newPlot.setDay(day);
            }
            plotArray[i] = newPlot;
        }
        //Adding plots to gridPane
        for (int i = 0; i < 12; i++) {
            plots.add(plotArray[i].getPlot(), i % 4, i / 4);
        }
        //setting topUI and plots to the border pane
        borderPane.setTop(topUI);
        borderPane.setCenter(plots);

        // temporary inventory and market interface connections
        Button openInventoryButton = new Button("Open Inventory");
        topUI.getChildren().add(openInventoryButton);
        openInventoryButton.setOnAction(e -> {
            InventoryScene.loadInventory();
            main.Main.setScene(inventory.InventoryScene.SCENE);
        });

        Button openMarketButton = new Button("Open Market");
        topUI.getChildren().add(openMarketButton);
        openMarketButton.setOnAction(e -> {
            market.MarketUI.updateStore();
            main.Main.setScene(market.MarketUI.SCENE);
        });

        Button clearDeadPlots = new Button("Clear Dead Plants");
        topUI.getChildren().add(clearDeadPlots);
        clearDeadPlots.setOnAction(e -> {
            GridPane newPlots = new GridPane();
            for (int i = 0; i < plotArray.length; i++) {
                Plot plot = plotArray[i];
                if (plot.isGrown().equals(GrowthStatus.DEAD)) {
                    plot.setGrown(GrowthStatus.EMPTY);
                }
                plotArray[i] = plot;
            }

            for (int i = 0; i < 12; i++) {
                newPlots.add(plotArray[i].getPlot(), i % 4, i / 4);
            }
            borderPane.setCenter(newPlots);
        });

        Button waterPlants = new Button("Water Plants");
        topUI.getChildren().add(waterPlants);
        waterPlants.setOnAction(e -> {
            Plot.setWaterBtn(true);
        });

        Button plantSeeds = new Button("Plant Seeds");
        topUI.getChildren().add(plantSeeds);
        plantSeeds.setOnAction(e -> {
            PlantScene.loadInventoryScene();
            main.Main.setScene(PlantScene.SCENE);
        });

        Button nextDay = new Button("Progress to Next Day");
        topUI.getChildren().add(nextDay);
        nextDay.setOnAction(progressDayEvent());

        //creating scene
        return new Scene(borderPane, 1200, 900);
    }

    public static void updatePlots() {
        for (Plot plot : plotArray) {
            plot.updatePlot();
        }
    }

    public static void setMainScreenMoney(double num) {
        topUI.getChildren().remove(moneyText);
        moneyText = new Text("$" + num);
        System.out.println();
        HBox temp = topUI;

        topUI.getChildren().add(2, moneyText);
    }

    public static void armSeedForPlanting(Plantable seed) {
        for (Plot plot : plotArray) {
            plot.armSeedForPlanting(seed);
        }
    }

    private static final String[] ORDINALS = {"th", "st",
        "nd", "rd", "th", "th", "th", "th", "th", "th"};

    /**
     * Event for progressing the day. Updates all plots, increments the day,
     * creates a random event, triggers it, then updates all relevant UI elements.
     *
     * @return event handler
     */
    private static EventHandler<ActionEvent> progressDayEvent() {
        return e -> {
            day++;
            progressPlots();
            updateDayText();
            RandomEvent event = new RandomEvent();
            event.trigger();
        };
    }

    private static void progressPlots() {
        Plot.setWaterBtn(false);
        for (Plot plot : plotArray) {
            switch (plot.isGrown()) {
            case IMMATURE:
                plot.setGrown(GrowthStatus.MATURE);
                plot.setWaterLevel(plot.getWaterLevel() - 1);
                plot.setDay(day);
                break;
            case SEED:
                plot.setGrown((GrowthStatus.IMMATURE));
                plot.setWaterLevel(plot.getWaterLevel() - 1);
                break;
            case MATURE:
                plot.setWaterLevel(plot.getWaterLevel() - 1);
                if (day - plot.getDay() == 3) {
                    plot.setGrown(GrowthStatus.DEAD);
                }
                break;
            default:
                break;
            }
            plot.getWaterLevel();
            plot.updatePlot();
        }
    }

    private static void updateDayText() {
        String newDayString;
        switch (day % 100) {
        case 11:
        case 12:
        case 13:
            newDayString = day + "th";
            break;
        default:
            newDayString = day + ORDINALS[day % 10];
        }

        ((Text) topUI.getChildren().get(1)).setText(newDayString + " day");
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
    public void setPlotArray(Plot[] plotArray) {
        this.plotArray = plotArray;
    }

    public static Plot[] getPlotArray() {
        return plotArray;
    }

    public String getDayString() {
        return dayString;
    }

    public void setDayString(String dayString) {
        this.dayString = dayString;
    }
}
