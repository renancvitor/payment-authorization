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

public class AlterarSenhaFormController implements Initializable {
	
	private Usuario alterarSenhaUsuario;
	
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
	
	@FXML
	public void onBtSalvarAction() {
		
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
