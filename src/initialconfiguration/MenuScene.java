package initialconfiguration;

import basescreen.MainScreen;
import inventory.CropType;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import main.PlayerInfo;
import inventory.Seed;
import market.PriceConstants;

public class MenuScene {

    public static final Scene SCENE = initScene();
    private static Seed newSeed;

    private static Scene initScene() {

        GridPane initialConfigUI = new GridPane();
        initialConfigUI.setHgap(10);
        initialConfigUI.setVgap(10);

        Label nameLabel = new Label("Name:");

        // Textfield for User to enter a name
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your full name");
        // show prompt even with focus
        nameField.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
        VBox nameFieldPane = new VBox();
        nameFieldPane.getChildren().addAll(nameLabel, nameField);
        initialConfigUI.add(nameFieldPane, 0, 0);

        // difficulty setting
        VBox difficultyPane = new VBox();
        difficultyPane.setStyle("-fx-background-color: #669933;");
        difficultyPane.getStyleClass().add("options");

        Label difficultyLabel = new Label("Difficulty:");

        ToggleGroup difficultyGroup = new ToggleGroup();
        RadioButton easyRadio = new RadioButton("Novice Farmer");
        RadioButton mediumRadio = new RadioButton("Run-of-the-mill");
        RadioButton hardRadio = new RadioButton("Expert Agronomist");
        easyRadio.setUserData(PlayerInfo.Difficulty.EASY);
        mediumRadio.setUserData(PlayerInfo.Difficulty.MEDIUM);
        hardRadio.setUserData(PlayerInfo.Difficulty.HARD);
        easyRadio.setToggleGroup(difficultyGroup);
        mediumRadio.setToggleGroup(difficultyGroup);
        hardRadio.setToggleGroup(difficultyGroup);

        difficultyGroup.selectedToggleProperty().addListener(
            (ov, toggle, new_toggle) -> {
                main.Main.PLAYER.setDifficulty(
                    (PlayerInfo.Difficulty) ((ToggleButton) new_toggle).getUserData()
                );
            });
        easyRadio.fire();

        difficultyPane.getChildren().addAll(difficultyLabel, easyRadio, mediumRadio, hardRadio);
        initialConfigUI.add(difficultyPane, 0, 1);

        // starting seed selection
        VBox seedPane = new VBox();
        seedPane.getStyleClass().add("options");
        seedPane.setStyle("-fx-background-color: #669933;");

        Label seedLabel = new Label("Seed:");

        ToggleGroup seedGroup = new ToggleGroup();
        RadioButton appleRadio = new RadioButton("Apple");
        RadioButton cornRadio = new RadioButton("Corn");
        RadioButton wheatRadio = new RadioButton("Wheat");
        appleRadio.setUserData(new Seed("Apple",  CropType.APPLE, PriceConstants.APPLE_SEED_PRICE));
        cornRadio.setUserData(new Seed("Corn", CropType.CORN, PriceConstants.CORN_SEED_PRICE));
        wheatRadio.setUserData(new Seed("Wheat", CropType.WHEAT, PriceConstants.WHEAT_SEED_PRICE));
        appleRadio.setToggleGroup(seedGroup);
        cornRadio.setToggleGroup(seedGroup);
        wheatRadio.setToggleGroup(seedGroup);
        appleRadio.fire();

        seedPane.getChildren().addAll(seedLabel, appleRadio, cornRadio, wheatRadio);
        initialConfigUI.add(seedPane, 0, 2);

        Button playButton = new Button("Play");
        Button cancelButton = new Button("Cancel");
        cancelButton.setCancelButton(true);

        playButton.setAlignment(Pos.BOTTOM_LEFT);

        Stage alertStage = new Stage();
        // when the play button is hit, a new scene appears
        // Validates for PlayerName.
        playButton.setOnAction((ActionEvent event1) -> {
            main.Main.PLAYER.setName(nameField.getText());
            Main.PLAYER.setInitSeed((Seed) seedGroup.getSelectedToggle().getUserData());
            if ((nameField.getText() == null || nameField.getText().trim().isEmpty())
                    || nameField.getText().equals("Enter your Full Name ")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("Looks file you forgot to enter your name");
                alert.setContentText("Enter a Valid Name");

                alert.showAndWait();

                Scene ns = new Scene(initialConfigUI, 1000, 600);
                ns.getStylesheets().add("style.css");
                alertStage.setScene(ns);
                alertStage.show();
            }

            GridPane initialUI = new GridPane();
            Label infoMsg = new Label("Initial Game UI pane");


            //The Initial UI pane goes here.
            Main.PLAYER.inventory.add(Main.PLAYER.getInitSeed());
            main.Main.setScene(MainScreen.SCENE);
        });

        // when the cancel button is hit, the program ends
        cancelButton.setOnAction(ev -> {
            alertStage.close();
            System.out.println("Cancel Button clicked.");
        });

        // setting scene
        HBox playCancelButtonPane = new HBox();
        playCancelButtonPane.setSpacing(10);
        playCancelButtonPane.setAlignment(Pos.CENTER);
        playCancelButtonPane.getChildren().addAll(playButton, cancelButton);
        initialConfigUI.add(playCancelButtonPane, 0, 3);
        initialConfigUI.getStyleClass().add("GridPane");

        Scene ns = new Scene(initialConfigUI, 1000, 600);
        ns.getStylesheets().add("style.css");

        return ns;
    }
}
