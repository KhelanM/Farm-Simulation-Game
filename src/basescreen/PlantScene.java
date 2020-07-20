package basescreen;

import inventory.InventoryObject;
import inventory.ObjectType;
import inventory.Plantable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.Main;


public class PlantScene {



    private static VBox vbox = new VBox();
    private static Plantable armedSeed = null;
    private static Text remainingInventoryText;
    private static Text title;
    private static Text maxInventoryText;
    public static final Scene SCENE = initScene();


    private static Scene initScene() {
        HBox hbox = new HBox();


        title = new Text("Plant some seeds:");
        title.setFont(Font.font(100));
        vbox.getChildren().add(title);


        maxInventoryText = new Text();
        maxInventoryText.setText("Max Inventory Size: " + Main.PLAYER.inventory.getMaxCapacity());
        maxInventoryText.setFont(Font.font(50));
        vbox.getChildren().add(maxInventoryText);

        remainingInventoryText = new Text();
        int remainingInventorySpace = (Main.PLAYER.inventory.getMaxCapacity()
                - Main.PLAYER.inventory.getUsedCapacity());
        remainingInventoryText.setText("Remaining Inventory Space: " + remainingInventorySpace);
        remainingInventoryText.setFont(Font.font(50));
        vbox.getChildren().add(remainingInventoryText);

        Button plantButton = new Button("Plant Seed");
        hbox.getChildren().add(plantButton);

        Button returnButton = new Button("Back");
        hbox.getChildren().add(returnButton);

        returnButton.setOnAction(e -> {
            main.Main.setScene(MainScreen.SCENE);
        });

        plantButton.setOnAction(e -> {
            main.Main.setScene(MainScreen.SCENE);
            MainScreen.armSeedForPlanting(armedSeed);
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


    public static void loadInventoryScene() {
        armedSeed = null;
        ToggleGroup plantingGroup = new ToggleGroup();
        vbox.getChildren().clear();
        resetInventoryText();
        vbox.getChildren().addAll(title, maxInventoryText, remainingInventoryText);
        for (int i = 0; i < Main.PLAYER.inventory.getSize(); i++) {
            InventoryObject inventoryItem = Main.PLAYER.inventory.get(i);
            if (inventoryItem instanceof Plantable) {
                if (inventoryItem.getQuantity() > 0) {
                    RadioButton itemRadio = new RadioButton(inventoryItem.toString());
                    itemRadio.setUserData(inventoryItem.getItemName());
                    itemRadio.setToggleGroup(plantingGroup);
                    if (inventoryItem.getObjectType() != ObjectType.SEED) {
                        itemRadio.setDisable(true);
                        itemRadio.setStyle("-fx-opacity: 1;");
                    } else {
                        itemRadio.setOnAction(e -> {
                            armedSeed = (Plantable) inventoryItem;
                        });
                    }
                    itemRadio.setFont(Font.font(25));
                    vbox.getChildren().add(vbox.getChildren().size(), itemRadio);
                }
            }


        }
    }
    private static void resetInventoryText() {
        remainingInventoryText.setText("Remaining Inventory Space: "
                + (Main.PLAYER.inventory.getMaxCapacity()
                - Main.PLAYER.inventory.getUsedCapacity()));
    }
}
