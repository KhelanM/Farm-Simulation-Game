package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main extends Application {

    public static final PlayerInfo PLAYER = new PlayerInfo();
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {

        primaryStage = stage;

        //Primary Stage Window
        primaryStage.setTitle("Farm Game");

        //Title Textbox in Scene
        Text title = new Text(100, 200, "Welcome to the Farm Game!");
        title.setFont(Font.font("Times New Roman", 40));

        //Start Button
        Button startBtn = new Button();
        startBtn.setText("Start");

        //Farm Image
        ImageView farmPicture = new ImageView("Assets/Images/FarmWallpaper.jpg");
        farmPicture.setFitHeight(600);
        farmPicture.setFitWidth(950);

        //Vbox Layout with all elements aligned to center
        VBox middle = new VBox(farmPicture, title, startBtn);
        middle.setPadding(new Insets(10, 0, 0, 0));
        title.setTextAlignment(TextAlignment.CENTER);
        startBtn.setAlignment(Pos.BOTTOM_CENTER);
        middle.setAlignment(Pos.CENTER);
        startBtn.setAlignment(Pos.CENTER);
        middle.setBackground(new Background(new BackgroundFill(Color.GREY,
                CornerRadii.EMPTY, Insets.EMPTY)));

        // when button is pressed
        startBtn.setOnAction(event -> {
            setScene(initialconfiguration.MenuScene.SCENE);
        });

        //Add stage to scene
        Scene scene = new Scene(middle, 1000, 800);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void setScene(Scene scene) {
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
