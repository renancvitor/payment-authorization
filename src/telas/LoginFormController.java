package telas;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

}
