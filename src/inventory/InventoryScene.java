package inventory;

import basescreen.MainScreen;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.Main;


/**
 * The Inventory UI
 *
 * @author Team 23
 * @version 1.0
 */
public class InventoryScene {


    private static VBox vbox = new VBox();
    private static Text remainingInventoryText;
    private static Text title;
    private static Text maxInventoryText;
    public static final Scene SCENE = initScene();


    /**
     * creates the initial Scene
     *
     * @return the scene
     */
    private static Scene initScene() {
        HBox hbox = new HBox();


        title = new Text("Inventory");
        title.setFont(Font.font(100));
        vbox.getChildren().add(title);


        maxInventoryText = new Text();
        maxInventoryText.setText("Max Inventory Size: " + Main.PLAYER.inventory.getMaxCapacity());
        maxInventoryText.setFont(Font.font(50));
        vbox.getChildren().add(maxInventoryText);

        int remainingSpace = (Main.PLAYER.inventory.getMaxCapacity()
                - Main.PLAYER.inventory.getUsedCapacity());
        remainingInventoryText = new Text();
        remainingInventoryText.setText("Remaining Inventory Space: " + remainingSpace);
        remainingInventoryText.setFont(Font.font(50));
        vbox.getChildren().add(remainingInventoryText);

        Button returnButton = new Button("Back");
        hbox.getChildren().add(returnButton);

        returnButton.setOnAction(e -> {
            main.Main.setScene(MainScreen.SCENE);
        });

        BorderPane bp = new BorderPane();
        bp.setCenter(vbox);
        bp.setBottom(hbox);
        BorderPane.setMargin(vbox, new Insets(50, 12, 12, 50));
        BorderPane.setMargin(hbox, new Insets(50, 12, 12, 50));
        Scene scene = new Scene(bp, 1200, 900);
        scene.getStylesheets().add("style.css");

        return scene;
    }


    /**
     * This will update the inventory screen wil the latest version of the inventory.
     */
    public static void loadInventory() {
        String armedSeed = null;
        ToggleGroup plantingGroup = new ToggleGroup();
        vbox.getChildren().clear();
        resetInventoryText();
        vbox.getChildren().addAll(title, maxInventoryText, remainingInventoryText);
        for (int i = 0; i < Main.PLAYER.inventory.getSize(); i++) {
            InventoryObject inventoryItem = Main.PLAYER.inventory.get(i);

            if (inventoryItem.getQuantity() > 0) {
                Text itemText = new Text(inventoryItem.toString());
                itemText.setFont(Font.font(25));
                vbox.getChildren().add(vbox.getChildren().size(), itemText);
            }
        }
    }

    /**
     * This will update the Inventory Space text.
     */
    private static void resetInventoryText() {
        int remainingSpace = (Main.PLAYER.inventory.getMaxCapacity()
                - Main.PLAYER.inventory.getUsedCapacity());
        remainingInventoryText.setText("Remaining Inventory Space: " + remainingSpace);
    }
}
