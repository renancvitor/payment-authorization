package application;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import gui.LoginFormController;
import gui.MainViewController;
import gui.util.Alerts;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.dao.DaoFactory;
import model.dao.UsuarioDao;
import model.entities.Usuario;
import model.services.UserLoginService;

public class Main extends Application {

    private static Scene mainScene;

    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button alterarSenhaButton;
    private Label labelErrorLogin;
    private Label labelErrorSenha;

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


    // AQUI É O ANTIGO LOGIN


//    @Override
//    public void start(Stage primaryStage) {
//
//        GridPane loginGrid = new GridPane();
//        loginGrid.setVgap(10);
//        loginGrid.setHgap(10);
//        loginGrid.setStyle("-fx-padding: 20; -fx-background-color: #f0f0f0;");
//
//        Label labelLogin = new Label("Login:");
//        loginGrid.add(labelLogin, 0, 0);
//
//        usernameField = new TextField();
//        usernameField.setPromptText("Nome de usuário");
//        loginGrid.add(usernameField, 1, 0);
//
//        Label labelSenha = new Label("Senha:");
//        loginGrid.add(labelSenha, 0, 1);
//
//        passwordField = new PasswordField();
//        passwordField.setPromptText("Senha");
//        loginGrid.add(passwordField, 1, 1);
//
//        labelErrorLogin = new Label();
//        labelErrorSenha = new Label();
//        loginGrid.add(labelErrorLogin, 1, 2);
//        loginGrid.add(labelErrorSenha, 1, 3);
//
//        loginButton = new Button("Login");
//        loginButton.setOnAction(this::onBtEntrarAction);
//        loginGrid.add(loginButton, 0, 4);
//
//        alterarSenhaButton = new Button("Alterar Senha");
//        alterarSenhaButton.setOnAction(this::onBtAlterarSenhaAction);
//        loginGrid.add(alterarSenhaButton, 1, 4);
//
//        loginGrid.setStyle("-fx-padding: 20; -fx-alignment: center;");
//
//        Scene loginScene = new Scene(loginGrid);
//
//        loginScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
//
//        primaryStage.setTitle("Login");
//        primaryStage.setScene(loginScene);
//        primaryStage.setResizable(true);
//        primaryStage.show();
//    }

//    private void onBtEntrarAction(ActionEvent event) {
//        String login = usernameField.getText().trim();
//        String senha = passwordField.getText().trim();
//
//        if (login.isEmpty()) {
//            labelErrorLogin.setText("O campo login é obrigatório.");
//            return;
//        } else {
//            labelErrorLogin.setText("");
//        }
//
//        if (senha.isEmpty()) {
//            labelErrorSenha.setText("O campo senha é obrigatório.");
//            return;
//        } else {
//            labelErrorSenha.setText("");
//        }
//
//        try {
//            Usuario usuario = validarLogin(login, senha);
//
//            if (usuario != null) {
//            	UserLoginService.setUsuarioLogado(usuario);
//                loadMainView(event);
//            } else {
//                Alerts.showAlert("Erro de Login", null, "Usuário ou senha inválidos.",
//                        AlertType.WARNING);
//            }
//        } catch (NoSuchAlgorithmException e) {
//            Alerts.showAlert("Erro de Conexão", null,
//                    "Erro ao conectar ao banco de dados. Tente novamente mais tarde.",
//                    AlertType.ERROR);
//            e.printStackTrace();
//        }
//    }

//    private Usuario validarLogin(String login, String senha) throws NoSuchAlgorithmException {
//		try {
//			UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
//	        Usuario usuario = usuarioDao.getUsuarioByLogin(login, senha);
//
//			if (usuario != null) {
//				UserLoginService.setUsuarioLogado(usuario);
//				return usuario;
//			} else {
//                Alerts.showAlert("Erro de Login", null, "Usuário ou senha inválidos.",
//                        AlertType.WARNING);
//            }
//		} catch (SQLException e) {
//			Alerts.showAlert("Erro de Conexão", null,
//                    "Erro ao conectar ao banco de dados. Tente novamente mais tarde.",
//                    AlertType.ERROR);
//            e.printStackTrace();
//		}
//
//		return null;
//	}

//    private void loadMainView(ActionEvent event) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
//            ScrollPane scrollPane = loader.load();
//
//            VBox mainBox = (VBox) scrollPane.getContent();
//
//            scrollPane.setFitToHeight(true);
//            scrollPane.setFitToWidth(true);
//
//            mainScene = new Scene(scrollPane);
//
//            if (UserLoginService.getUsuarioLogado() == null) {
//                Alerts.showAlert("Erro de Login", "Usuário não logado",
//                        "Você precisa fazer login para acessar a tela principal.", AlertType.ERROR);
//                return;
//            }
//
//            Stage loginStage = (Stage) loginButton.getScene().getWindow();
//            loginStage.close();
//
//            Stage primaryStage = new Stage();
//            primaryStage.setScene(mainScene);
//            primaryStage.setTitle("Tela Principal");
//            primaryStage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Alerts.showAlert("Erro", "Não foi possível carregar a tela principal.",
//                    e.getMessage(), AlertType.ERROR);
//        }
//    }

//    private void onBtAlterarSenhaAction(ActionEvent event) {
//        Stage parentStage = (Stage) alterarSenhaButton.getScene().getWindow();
//        createDialogForm("/gui/AlterarSenhaForm.fxml", parentStage);
//    }

//    private void createDialogForm(String absoluteName, Stage parentStage) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
//            VBox pane = loader.load();
//
//            Stage dialogStage = new Stage();
//            dialogStage.setTitle("Alterar Senha");
//            dialogStage.setScene(new Scene(pane));
//            dialogStage.setResizable(false);
//            dialogStage.initOwner(parentStage);
//            dialogStage.initModality(Modality.WINDOW_MODAL);
//            dialogStage.showAndWait();
//
//        } catch (IOException e) {
//            Alerts.showAlert("Erro", "Não foi possível carregar a tela de alteração de senha.",
//                    e.getMessage(), AlertType.ERROR);
//        }
//    }

    public static Scene getMainScene() {
        return mainScene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
