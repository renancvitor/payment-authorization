package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Usuario;

public class UsuarioFormController implements Initializable {
	
	private Usuario usuario;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtLogin;
	
	@FXML
	private TextField txtSenha;
	
	@FXML
	private Label labelErrorLogin;
	
	@FXML
	private Label labelErrorSenha;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancelar;
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@FXML
	public void onBtSalvarAction() {
		
	}
	
	@FXML
	public void onBtCancelarAction() {
		
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializaNodes();
	}

	public void initializaNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtLogin, 10);
		Constraints.setTextFieldMaxLength(txtSenha, 8);
	}
	
	public void updateFormData() {
		txtId.setText(String.valueOf(usuario.getId()));
		txtLogin.setText(usuario.getLogin());
		txtSenha.setText(usuario.getSenha());
	}
	
}
