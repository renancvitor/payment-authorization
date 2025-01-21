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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.dao.DaoFactory;
import model.dao.UsuarioDao;
import model.entities.Usuario;
import model.services.UserLoginService;

public class LoginFormController implements Initializable {
			
	@FXML
	private TextField txtLogin;
	
	@FXML
	private TextField txtSenha;
	
	@FXML
	private Label labelErrorLogin;
	
	@FXML
	private Label labelErrorSenha;
	
	@FXML
	private Button btEntrar;
	
	@FXML
	private Button btAlterarSenha;
	
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

        Usuario usuario = validarLogin(login, senha);

        if (usuario != null) {
            try {                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
                ScrollPane mainPane = loader.load();

                Stage loginStage = (Stage) btEntrar.getScene().getWindow();
                loginStage.close();

                Stage primaryStage = new Stage();
                Scene newScene = new Scene(mainPane);
                primaryStage.setScene(newScene);
                primaryStage.setTitle("Main View");
                primaryStage.show();
                
            } catch (IOException e) {
                Alerts.showAlert("Erro", "Não foi possível carregar a tela principal.", e.getMessage(), AlertType.ERROR);
                e.printStackTrace();
            }
        } else {
            Alerts.showAlert("Erro de Login", null, "Usuário ou senha inválidos.", AlertType.WARNING);
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
			
			AlterarSenhaFormController controller = loader.getController();
			controller.setAlterarSenhaService(null);
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Alterar Senha");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializaNodes();
	}
	
	public void initializaNodes() {
		Constraints.setTextFieldMaxLength(txtSenha, 8);
	}
	
	private Usuario validarLogin(String login, String senha) throws NoSuchAlgorithmException {
		try {
			UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
	        Usuario usuario = usuarioDao.getUsuarioByLogin(login, senha);
			
			if (usuario != null) {
				UserLoginService.setUsuarioLogado(usuario);
				return usuario;
			} else {
                Alerts.showAlert("Erro de Login", null, "Usuário ou senha inválidos.", AlertType.WARNING);
            }
		} catch (SQLException e) {
			Alerts.showAlert("Erro de Conexão", null, "Erro ao conectar ao banco de dados. Tente novamente mais tarde.", AlertType.ERROR);
            e.printStackTrace();
		}
		
		return null;
	}

}
