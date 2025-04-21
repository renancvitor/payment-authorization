package application;

import java.io.IOException;

import gui.LoginFormController;
import gui.MainViewController;
import gui.util.Alerts;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private static Scene mainScene;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LoginForm.fxml"));
            VBox loginVBox = loader.load();

            LoginFormController loginController = loader.getController();
            loginController.setStage(primaryStage);

            mainScene = new Scene(loginVBox);
            mainScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

            primaryStage.setTitle("Login");
            primaryStage.setScene(mainScene);
            primaryStage.setResizable(true);
            primaryStage.show();

            loginController.setOnLoginSuccess(() -> {
                try {
                    FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
                    ScrollPane mainScrollPane = mainLoader.load();

                    MainViewController mainController = mainLoader.getController();
                    mainController.setStage(primaryStage);

                    mainScene = new Scene(mainScrollPane);
                    primaryStage.setScene(mainScene);
                    primaryStage.setTitle("Tela Principal");
                    primaryStage.show();

                } catch (IOException e) {
                    Alerts.showAlert("Erro", "Não foi possível carregar a tela principal.",
                            e.getMessage(), Alert.AlertType.ERROR);
                    e.printStackTrace();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Não foi possível carregar a tela de login.",
                    e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public static Scene getMainScene() {
        return mainScene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}