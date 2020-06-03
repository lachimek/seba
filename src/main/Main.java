package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    //statyczne odwolanie do glownej sceny programu
    private static Stage stage;

    //glowna metoda startujaca aplikacje
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("views/Strona_domowa_interface.fxml")));
        primaryStage.setTitle("System katalogowanie zleceń w serwisie informatycznym");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        stage = primaryStage;
        primaryStage.show();
    }

    //metoda ustawiajaca widok na scenie na podany w formie stringa nazwy widoku
    public static void setView(String fxml){
        Scene s = null;
        try {
            s = new Scene(FXMLLoader.load(Objects.requireNonNull(Main.class.getClassLoader().getResource(fxml))));
            stage.setScene(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //metoda tworzaca popup
    public static void createPopup(String fxml){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getClassLoader().getResource(fxml));
        Parent layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
