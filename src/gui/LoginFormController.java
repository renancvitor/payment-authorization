package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Usuario;

public class LoginFormController implements Initializable {
	
	private Usuario usuarioLogin;
	
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
	
	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}
	
	@FXML
	public void onBtEntrarAction() {
		
	}
	
	@FXML
	public void onBtAlterarSenhaAction() {
		
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializaNodes();
	}
	
	public void initializaNodes() {
		// TODO Auto-generated method stub
	}
	
	/* public void updateFormData() { *** LOGIN NÃO É UM FORMULÁRIO ***
		txtLogin.setText(usuarioLogin.getLogin());
		txtSenha.setText(usuarioLogin.getSenha());
	}*/

}
