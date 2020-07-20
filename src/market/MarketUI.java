package market;

import basescreen.MainScreen;
import inventory.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import main.Main;
import main.*;

import java.util.NoSuchElementException;

import static inventory.CropType.*;

/**
 * The Graphical user interface for Market
 * The GUI has buy, sell, back, and confirm transaction features.
 * It displays money, available inventory space, and items that can be brought or sold.
 * The prices are assigned using a random algorithm
 */
public class MarketUI {

    private static double buyApplePrice;
    private static double buyCornPrice;
    private static double buyWheatPrice;
    private static double playerMoney = Main.PLAYER.getMoney();
    private static int availableStorage = Main.PLAYER.inventory.getUsedCapacity();
    private static Option buyOrSell;
    private static ToggleGroup sellSeedOption = new ToggleGroup();
    private static VBox sellSeedPane = new VBox();
    private static RadioButton appleOption;
    private static RadioButton cornOption;
    private static RadioButton wheatOption;
    private static Label inventoryLb;
    private static Label moneyLb;

    public static final Scene SCENE = init();

    public static Scene init() {
        updateBuyPrices();
        Button backBtn = new Button("Back");
        backBtn.setAlignment(Pos.BASELINE_CENTER);

        Button confirmBtn = new Button("Confirm");
        confirmBtn.setAlignment(Pos.CENTER);

        moneyLb = new Label("Money: " + playerMoney);
        inventoryLb = new Label("Inventory " + availableStorage + "/100");
        Label countLb = new Label("Count: ");
        countLb.setStyle("-fx-padding: 5px");

        TextField countTextField = new TextField();
        countTextField.setPromptText("Enter your Crop Count: ");
        countTextField.setStyle("-fx-min-width: 150px; -fx-pref-width: 50;"
                + "-fx-max-width: 70; -fx-alignment:left; -fx-padding:5px;");
        countLb.setVisible(false);
        countTextField.setVisible(false);
        sellSeedPane.getStyleClass().add("options");
        sellSeedPane.setStyle("-fx-background-color: #669933;" + "-fx-padding: inherit");
        sellSeedPane.setVisible(false);

        // The loop prints the items that are eligible to be sold
        displaySellableItems();

        sellSeedOption.selectedToggleProperty().addListener((ov, toggle, new_toggle) -> {
        });

        VBox buySeedPane = new VBox();
        buySeedPane.getStyleClass().add("options");
        buySeedPane.setStyle("-fx-background-color: #eea10b;");
        buySeedPane.setVisible(false);

        // Toggle group to list the different seeds that can be brought from the market
        ToggleGroup buySeedOption = new ToggleGroup();
        appleOption = new RadioButton("Buy Apple seed at $" + buyApplePrice + "/piece.");
        cornOption = new RadioButton("Buy Corn seed at $" + buyCornPrice + "/piece.");
        wheatOption = new RadioButton("Buy Wheat seed at $" + buyWheatPrice + "/piece.");
        appleOption.setUserData(APPLE);
        cornOption.setUserData(CORN);
        wheatOption.setUserData(WHEAT);
        appleOption.setToggleGroup(buySeedOption);
        cornOption.setToggleGroup(buySeedOption);
        wheatOption.setToggleGroup(buySeedOption);
        buySeedOption.selectedToggleProperty().addListener((ov, toggle, new_toggle) -> {
        });

        Button buyBtn = new Button("Buy");
        buyBtn.setOnAction(event -> {
            buyOrSell = Option.BUY;
            buySeedPane.setVisible(true);
            sellSeedPane.setVisible(false);
            countTextField.setVisible(true);
        });
        Button sellBtn = new Button("Sell");
        sellBtn.setOnAction(event -> {
            buyOrSell = Option.SELL;
            sellSeedPane.setVisible(true);
            buySeedPane.setVisible(false);
            countTextField.setVisible(true);
        });

        // when the user confirms the transaction, the code validates item count,
        // whether or not the item is selected,
        // player's available storage and if they have enough money
        confirmBtn.setOnAction(event -> {
            if (buyOrSell == null) {
                String title = "A Market Option is not selected";
                PlayerInfo.showAlert(title, "You need to select buy or sell.");
            } else if (buyOrSell == Option.BUY && buySeedOption.getSelectedToggle() == null) {
                String title = "No buy option is selected";
                PlayerInfo.showAlert(title, "You need to select a buy option.");
            } else if (buyOrSell == Option.SELL && sellSeedOption.getSelectedToggle() == null) {
                String title = "No sell option is selected";
                PlayerInfo.showAlert(title, "You need to select a sell option.");
            } else {
                int count = -1;
                try {
                    count = Integer.parseInt(countTextField.getText());
                    if (count < 1) {
                        String title = "Invalid input for count";
                        PlayerInfo.showAlert(title, "enter an integer above 0.");
                    }
                } catch (NumberFormatException e) {
                    String title = "Invalid input for count";
                    PlayerInfo.showAlert(title, "enter an integer above 0.");
                }
                if (buyOrSell == Option.BUY) {
                    processBuy(count, buySeedOption.getSelectedToggle());
                } else {
                    processSell(count, sellSeedOption.getSelectedToggle());
                }
            }
            countTextField.clear();
            countTextField.setPromptText("Enter your Crop Count: ");
            Toggle buySeedToggle = buySeedOption.getSelectedToggle();
            if (buySeedToggle != null) {
                buySeedToggle.setSelected(false);
            }
            Toggle sellSeedToggle = sellSeedOption.getSelectedToggle();
            if (sellSeedToggle != null) {
                sellSeedToggle.setSelected(false);
            }
        });

        // the backBtn returns back to the plot and resets their selection
        backBtn.setOnAction(event -> {
            backBtn.setOnAction(e -> {
                resetValues();
                updateStore();
                main.Main.setScene(MainScreen.SCENE);
            });
            main.Main.setScene(MainScreen.SCENE);
        });

        GridPane marketPane = new GridPane();
        marketPane.setStyle("    -fx-background-color: rgb(232, 217, 190);");
        marketPane.setAlignment(Pos.CENTER);
        marketPane.setPadding(new Insets(10, 10, 10, 10));
        buySeedPane.getChildren().addAll(appleOption, cornOption, wheatOption);

        marketPane.add(countTextField, 1, 9);

        marketPane.add(moneyLb, 3, 1);
        marketPane.add(inventoryLb, 0, 1);

        marketPane.add(sellSeedPane, 0, 3, 3, 6);
        marketPane.add(buySeedPane, 3, 3, 3, 6);

        marketPane.add(sellBtn, 0, 0);
        marketPane.add(buyBtn, 3, 0);

        marketPane.add(backBtn, 0, 12);
        marketPane.add(confirmBtn, 5, 9);

        Scene marketScene = new Scene(marketPane, 600, 400);
        marketScene.getStylesheets().add("style.css");
        // marketScene.setPrefSize(500, 500);
        return marketScene;
    }

    /**
     * updateBuyPrices method updates the buying price of each seed.
     * Contains an try catch block to deal with exception.
     * In the case of an exception, the price is set to 0
     */
    private static void updateBuyPrices() {
        try {
            buyApplePrice = getBuyPrice(PriceConstants.APPLE_SEED_PRICE, ObjectType.SEED);
        } catch (NoSuchElementException e) {
            buyApplePrice = 0;
        }
        try {
            buyCornPrice = getBuyPrice(PriceConstants.CORN_SEED_PRICE, ObjectType.SEED);
        } catch (NoSuchElementException e) {
            buyCornPrice = 0;
        }
        try {
            buyWheatPrice = getBuyPrice(PriceConstants.WHEAT_SEED_PRICE, ObjectType.SEED);
        } catch (NoSuchElementException e) {
            buyWheatPrice = 0;
        }


    }

    /**
     * resetValues resets the price of the plant every time confirm/ back button is hit
     */
    private static void resetValues() {
        playerMoney = Main.PLAYER.getMoney();
        updateBuyPrices();
        availableStorage = Main.PLAYER.inventory.getUsedCapacity();
    }

    /**
     * Updates the contents of the store (items are eligible to be sold)
     */
    public static void updateStore() {
        resetValues();
        sellSeedPane.getChildren().clear();
        sellSeedOption = new ToggleGroup();
        displaySellableItems();

        appleOption.setText("Buy Apple seed at $" + buyApplePrice + "/piece.");
        cornOption.setText("Buy Corn seed at $" + buyCornPrice + "/piece.");
        wheatOption.setText("Buy Wheat seed at $" + buyWheatPrice + "/piece.");
        inventoryLb.setText("Inventory " + availableStorage + "/100");
        moneyLb.setText("Money: " + playerMoney);
        MainScreen.setMainScreenMoney(playerMoney);
    }

    /**
     * method that displays the sellable items of the inventory
     */
    public static void displaySellableItems() {
        for (int i = 0; i < Main.PLAYER.inventory.getSize(); i++) {
            if (Main.PLAYER.inventory.get(i) instanceof MarketableInventoryObject) {
                MarketableInventoryObject newObject =
                        (MarketableInventoryObject) Main.PLAYER.inventory.get(i);
                double price = newObject.getPrice();
                RadioButton newRadio = new RadioButton("Available " + newObject.getQuantity() + " "
                        + newObject.getItemName() + " "
                        + newObject.getObjectType().toString().toLowerCase()
                        + "(s) at $" + price + " a piece.");
                newRadio.setUserData(new ItemPricePair((MarketableInventoryObject) newObject.clone(), price));
                newRadio.setToggleGroup(sellSeedOption);
                sellSeedPane.getChildren().add(newRadio);
            }
        }
    }

    /**
     * processBuy method Checks the transaction of the Player and updates the inventory
     * Validates for type of crop, space in inventory, and player's ability to buy the item
     *
     * @param count  Crop Quantity
     * @param toggle Type of Crop selected
     */
    private static void processBuy(int count, Toggle toggle) {
        double currentPrice;
        CropType userData = ((CropType) toggle.getUserData());
        if (userData == APPLE) {
            currentPrice = buyApplePrice;
        } else if (userData == CORN) {
            currentPrice = buyCornPrice;
        } else {
            currentPrice = buyWheatPrice;
        }
        if (count >= 1) {
            if (availableStorage + count > 100) {
                PlayerInfo.showAlert("Not enough space in Inventory", "select a smaller quantity");
            } else {
                if (playerMoney - currentPrice * count < 0) {
                    PlayerInfo.showAlert("Not enough money", "get more money");
                } else {
                    CropType seed = (CropType) toggle.getUserData();
                    String seedName = "";
                    double basePrice = 0;
                    if (seed == APPLE) {
                        seedName = "Apple";
                        basePrice = PriceConstants.APPLE_SEED_PRICE;
                    } else if (seed == CORN) {
                        seedName = "Corn";
                        basePrice = PriceConstants.CORN_SEED_PRICE;
                    } else if (seed == WHEAT) {
                        seedName = "Wheat";
                        basePrice = PriceConstants.WHEAT_SEED_PRICE;
                    }
                    Seed newSeed = new Seed(seedName, count, seed, basePrice);

                    Main.PLAYER.inventory.add(newSeed);
                    Main.PLAYER.setMoney((float) (playerMoney - currentPrice * count));
                    updateStore();
                    main.Main.setScene(MainScreen.SCENE);
                }
            }
        }
    }

    /**
     * Confirms the players selling transaction
     * Validates for quantity and type available in the inventory
     *
     * @param count  Crop/ Seed Quantity
     * @param toggle Type of Crop/ Seed
     */
    private static void processSell(int count, Toggle toggle) {
        double currentPrice;
        ItemPricePair ipp = (ItemPricePair) toggle.getUserData();
        MarketableInventoryObject sell = ipp.getObject();
        int sellItemCount = Main.PLAYER.inventory.getQuantity(sell);
        if (sellItemCount == 0) {
            PlayerInfo.showAlert("Could not find seed.", "Enter valid seed.");
        }
        if (sellItemCount < count) {
            String title = "Not enough of that item to sell";
            PlayerInfo.showAlert(title, "reduce your amount you are selling");
        } else {
            sell.setQuantity(count);
            Main.PLAYER.inventory.remove(sell);
            Main.PLAYER.setMoney((float) (playerMoney + ipp.getPrice() * count));
            updateStore();
            main.Main.setScene(MainScreen.SCENE);
        }
    }

    /**
     * getBuyPrice method returns the buy price of each crop using a random algorithm
     *
     * @param basePrice  Constant starting price for each crop
     * @param objectType Type of plant
     * @return price of Specific Crop
     */
    private static double getBuyPrice(double basePrice, ObjectType objectType) {
        MarketableInventoryObject mio;
        if (objectType == ObjectType.SEED) {
            mio = new Seed("Apple", 1, APPLE, basePrice);
            return mio.getPrice();
        } else if (objectType == ObjectType.PLANT) {
            mio = new Plant("Apple", 1, APPLE, basePrice);
            return mio.getPrice();
        } else {
            throw new IllegalArgumentException("Error: Could not find valid ObjectType");
        }
    }

    /**
     * Enum options: Buy or Sell a plant/ seed
     */
    private enum Option {BUY, SELL}
}
