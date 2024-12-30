package gui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import gui.listeners.DataChangeListener;
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
import model.exceptions.ValidationException;
import model.services.AlterarSenhaService;

public class AlterarSenhaFormController implements Initializable {
	
	private UsuarioDaoJDBC usuarioDao;
	
	private AlterarSenhaService alterarSenhaService;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
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
	
	/*public void setAlterarSenhaUsuario(AlterarSenha alterarSenhaUsuario) {
		this.alterarSenhaUsuario = alterarSenhaUsuario;
	}*/
	
	public void setAlterarSenhaService(AlterarSenhaService alterarSenhaService) {
		this.alterarSenhaService = alterarSenhaService;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@FXML
	public void onBtSalvarAction(ActionEvent event) {
		try {
	        String username = txtLogin.getText();
	        String senhaAtual = txtSenhaAtual.getText();
	        String novaSenha = txtNovaSenha.getText();
	        Stage stage = (Stage) btSalvar.getScene().getWindow();
	        
	        alterarSenha(username, senhaAtual, novaSenha, stage);
	        
	        notifyDataChangeListeners();
	        utils.currentStage(event).close();
	    } catch (ValidationException e) {
			setErrorMessages(e.getErrors());
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
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	private void alterarSenha(String username, String senhaAtual, String novaSenha, Stage stage) throws SQLException {
	    ValidationException exception = new ValidationException("Validation Error");
	    
	    if (username == null || username.trim().isEmpty()) {
	        exception.addError("login", "Campo não pode ser vazio.");
	    }
	    if (senhaAtual == null || senhaAtual.trim().isEmpty()) {
	        exception.addError("senhaatual", "Campo não pode ser vazio.");
	    }
	    if (novaSenha == null || novaSenha.trim().isEmpty()) {
	        exception.addError("novasenha", "Campo não pode ser vazio.");
	    }
	    if (!txtNovaSenha.getText().equals(txtRepetirNovaSenha.getText())) {
	        exception.addError("repetirnovasenha", "As senhas não coincidem.");
	    }
	    if (!exception.getErrors().isEmpty()) {
	        throw exception;
	    }

	    if (!usuarioDao.verificarSenhaPorUsuario(username, senhaAtual)) {
	        Alerts.showAlert(
	            "Erro",
	            null,
	            "Nome de usuário ou senha atual inválidos.",
	            Alert.AlertType.ERROR
	        );
	        return;
	    }

	    boolean sucesso = alterarSenhaService.saveOrUpdate(username, novaSenha);

	    if (sucesso) {
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
	
	/*public void updateFormData() {
		txtLogin.setText(alterarSenhaUsuario.getLogin());
		txtSenhaAtual.setText(alterarSenhaUsuario.getSenha());
	}*/
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if (fields.contains("login")) {
			labelErrorLogin.setText(errors.get("login"));
		}
		if (fields.contains("senhaatual")) {
			labelErrorSenhaAtual.setText(errors.get("senhaatual"));
		}
		if (fields.contains("novasenha")) {
			labelErrorNovaSenha.setText(errors.get("novasenha"));
		}
		if (fields.contains("repetirnovasenha")) {
			labelErrorRepetirNovaSenha.setText(errors.get("repetirnovasenha"));
		}
	}
	
}
