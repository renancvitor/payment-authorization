package application;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import gui.util.Alerts;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
    private Button alterarSenhaButton; // Novo botão
    private Label labelErrorLogin;
    private Label labelErrorSenha;

    @Override
    public void start(Stage primaryStage) {
        VBox loginBox = new VBox(10);

        usernameField = new TextField();
        usernameField.setPromptText("Nome de usuário");

        passwordField = new PasswordField();
        passwordField.setPromptText("Senha");

        loginButton = new Button("Login");
        loginButton.setOnAction(this::onBtEntrarAction);

        alterarSenhaButton = new Button("Alterar Senha"); // Novo botão
        alterarSenhaButton.setOnAction(this::onBtAlterarSenhaAction); // Ação para abrir a tela de alteração de senha

        labelErrorLogin = new Label();
        labelErrorSenha = new Label();

        loginBox.getChildren().addAll(usernameField, passwordField, loginButton, alterarSenhaButton, labelErrorLogin, labelErrorSenha);

        Scene loginScene = new Scene(loginBox, 300, 250); // Tamanho ajustado para incluir o novo botão
        primaryStage.setTitle("Tela de Login");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void onBtEntrarAction(ActionEvent event) {
        String login = usernameField.getText().trim();
        String senha = passwordField.getText().trim();

        if (login.isEmpty()) {
            labelErrorLogin.setText("O campo login é obrigatório.");
            return;
        } else {
            labelErrorLogin.setText("");
        }

        if (senha.isEmpty()) {
            labelErrorSenha.setText("O campo senha é obrigatório.");
            return;
        } else {
            labelErrorSenha.setText("");
        }

        try {
            Usuario usuario = validarLogin(login, senha);

            if (usuario != null) {
                loadMainView(event);
            } else {
                Alerts.showAlert("Erro de Login", null, "Usuário ou senha inválidos.", AlertType.WARNING);
            }
        } catch (NoSuchAlgorithmException | SQLException e) {
            Alerts.showAlert("Erro de Conexão", null, "Erro ao conectar ao banco de dados. Tente novamente mais tarde.", AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private Usuario validarLogin(String login, String senha) throws NoSuchAlgorithmException, SQLException {
        UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
        Usuario usuario = usuarioDao.getUsuarioByLogin(login, senha);

        if (usuario != null) {
            UserLoginService.setUsuarioLogado(usuario);
            return usuario;
        }
        return null;
    }

    private void loadMainView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
            ScrollPane scrollPane = loader.load();

            VBox mainBox = (VBox) scrollPane.getContent();

            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);

            mainScene = new Scene(scrollPane);

            Stage loginStage = (Stage) loginButton.getScene().getWindow();
            loginStage.close();

            Stage primaryStage = new Stage();
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("Tela Principal");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", "Não foi possível carregar a tela principal.", e.getMessage(), AlertType.ERROR);
        }
    }

    private void onBtAlterarSenhaAction(ActionEvent event) {
        Stage parentStage = (Stage) alterarSenhaButton.getScene().getWindow();
        createDialogForm("/gui/AlterarSenhaForm.fxml", parentStage);
    }

    private void createDialogForm(String absoluteName, Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox pane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Alterar Senha");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();

        } catch (IOException e) {
            Alerts.showAlert("Erro", "Não foi possível carregar a tela de alteração de senha.", e.getMessage(), AlertType.ERROR);
        }
    }

    public static Scene getMainScene() {
        return mainScene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
