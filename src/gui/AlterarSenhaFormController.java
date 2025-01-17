package gui;

import java.net.URL;
import java.sql.Connection;
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
import model.exceptions.ValidationException;
import model.services.AlterarSenhaService;
import model.services.PermissaoService;
import model.services.UsuarioService;

public class AlterarSenhaFormController implements Initializable {
		
	private UsuarioService userService = new UsuarioService();
	private AlterarSenhaService senhaService = new AlterarSenhaService();
		
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
		this.senhaService = alterarSenhaService;
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
	    if (userService.verificarSenha(username, senhaAtual)) {
		    if (senhaService.saveOrUpdate(username, novaSenha)) {
		        System.out.println("Senha alterada com sucesso.");
		        stage.close();
		    } else {
		        System.out.println("Erro ao alterar a senha.");
		    }
		} else {
		    System.out.println("Nome de usuário ou senha atual inválidos.");
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
