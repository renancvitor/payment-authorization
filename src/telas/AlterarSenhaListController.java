package telas;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import model.services.AlterarSenhaService;

public class AlterarSenhaListController implements Initializable {
	
	private AlterarSenhaService service;
	
	public void setAlterarSenhaService(AlterarSenhaService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}

}
