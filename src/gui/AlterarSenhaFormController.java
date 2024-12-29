package gui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dao.impl.UsuarioDaoJDBC;
import model.entities.Usuario;
import model.services.AlterarSenhaService;

public class AlterarSenhaFormController implements Initializable {
	
	private Usuario alterarSenhaUsuario;
	private UsuarioDaoJDBC usuarioDao;
	
	private AlterarSenhaService alterarSenhaService;
	
	@FXML
	private TextField txtLogin;
	
	@FXML
	private TextField txtSenhaAtual;
	
	@FXML
	private TextField txtNovaSenha;
	
	@FXML
	private TextField txtRepetirNovaSenha;
	
	@FXML
	private Label labelErrorLogin;
	
	@FXML
	private Label labelErrorSenhaAtual;
	
	@FXML
	private Label labelErrorNovaSenha;
	
	@FXML
	private Label labelErrorRepetirNovaSenha;
	
	@FXML
	private Button btSalvar;
	
	public void setAlterarSenhaUsuario(Usuario alterarSenhaUsuario) {
		this.alterarSenhaUsuario = alterarSenhaUsuario;
	}
	
	public void setAlterarSenhaService(AlterarSenhaService alterarSenhaService) {
		this.alterarSenhaService = alterarSenhaService;
	}
	
	@FXML
	public void onBtSalvarAction(ActionEvent event) {
		try {
	        String username = txtLogin.getText();
	        String senhaAtual = txtSenhaAtual.getText();
	        String novaSenha = txtNovaSenha.getText();
	        Stage stage = (Stage) btSalvar.getScene().getWindow();
	        
	        alterarSenha(username, senhaAtual, novaSenha, stage);
	        
	        utils.currentStage(event).close();
	    } catch (SQLException e) {
	        Alerts.showAlert(
	            "Erro",
	            null,
	            "Erro ao processar a alteração de senha: " + e.getMessage(),
	            Alert.AlertType.ERROR
	        );
	        e.printStackTrace();
	    }
	}

	private void alterarSenha(String username, String senhaAtual, String novaSenha, Stage stage) throws SQLException {
		if (!usuarioDao.verificarSenhaPorUsuario(username, senhaAtual)) {
	        Alerts.showAlert(
	            "Erro",
	            null,
	            "Nome de usuário ou senha atual inválidos.",
	            Alert.AlertType.ERROR
	        );
	        return;
	    }

	    if (alterarSenhaService.saveOrUpdate(username, novaSenha)) {
	        Alerts.showAlert(
	            "Sucesso",
	            null,
	            "Senha alterada com sucesso.",
	            Alert.AlertType.INFORMATION
	        );
	        stage.close();
	    } else {
	        Alerts.showAlert(
	            "Erro",
	            null,
	            "Erro ao alterar a senha.",
	            Alert.AlertType.ERROR
	        );
	    }
	}


	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializaNodes();
	}
	
	public void initializaNodes() {
		Constraints.setTextFieldMaxLength(txtNovaSenha, 8);
	}
	
	public void updateFormData() {
		txtLogin.setText(alterarSenhaUsuario.getLogin());
		txtSenhaAtual.setText(alterarSenhaUsuario.getSenha());
	}
	
}
