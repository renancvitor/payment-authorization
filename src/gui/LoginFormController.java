package gui;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.dao.DaoFactory;
import model.dao.UsuarioDao;
import model.entities.Usuario;
import model.services.UserLoginService;

public class LoginFormController implements Initializable {
	private Stage stage;
	private Runnable onLoginSuccess;

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@FXML
	private TextField txtLogin;

	@FXML
	private PasswordField txtSenha;

	@FXML
	private Label labelErrorLogin;

	@FXML
	private Label labelErrorSenha;

	@FXML
	private Button btEntrar;

	@FXML
	private Button btAlterarSenha;

	public void setOnLoginSuccess(Runnable onLoginSuccess) {
		this.onLoginSuccess = onLoginSuccess;
	}

	@FXML
	public void onBtEntrarAction(ActionEvent event) throws NoSuchAlgorithmException {
		String login = txtLogin.getText().trim();
		String senha = txtSenha.getText().trim();

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
				UserLoginService.setUsuarioLogado(usuario);
				loadMainView();

				if (onLoginSuccess != null) {
					onLoginSuccess.run();
				}
			} else {
				Alerts.showAlert("Erro de Login", null, "Usuário ou senha inválidos.",
						Alert.AlertType.WARNING);
			}
		} catch (NoSuchAlgorithmException e) {
			Alerts.showAlert("Erro de Conexão", null,
					"Erro ao conectar ao banco de dados. Tente novamente mais tarde.",
					Alert.AlertType.ERROR);
			e.printStackTrace();
		}
	}

	private Usuario validarLogin(String login, String senha) throws NoSuchAlgorithmException {
		try {
			UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
			return usuarioDao.getUsuarioByLogin(login, senha);
		} catch (SQLException e) {
			Alerts.showAlert("Erro de Conexão", null,
					"Erro ao conectar ao banco de dados. Tente novamente mais tarde.",
					Alert.AlertType.ERROR);
			e.printStackTrace();
		}
		return null;
	}

	private void loadMainView() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
			ScrollPane scrollPane = loader.load();

			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);

			Scene mainScene = new Scene(scrollPane);

			if (stage != null) {
				stage.setScene(mainScene);
				stage.setTitle("Tela Principal");
				stage.show();
			} else {
				System.out.println("A 'stage' da tela de login não está definida corretamente.");
			}

		} catch (IOException e) {
			Alerts.showAlert("Erro", "Não foi possível carregar a tela principal.",
					e.getMessage(), Alert.AlertType.ERROR);
			e.printStackTrace();
		}
	}

	@FXML
	public void onBtAlterarSenhaAction(ActionEvent event) {
		Stage parentStage = utils.currentStage(event);

		createDialogForm("/gui/AlterarSenhaForm.fxml", parentStage);
	}

	private void createDialogForm(String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			if (pane == null) {
				throw new IOException("Não foi possível carregar a tela de alteração de senha.");
			}

			//AlterarSenhaFormController controller = loader.getController();
			//controller.setAlterarSenhaService(null);

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Alterar Senha");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro ao carregar a visualização",
					e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializaNodes();
	}

	public void initializaNodes() {
		Constraints.setTextFieldMaxLength(txtSenha, 10);
	}
}