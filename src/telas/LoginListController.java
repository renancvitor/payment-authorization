package telas;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import model.services.LoginService;

public class LoginListController implements Initializable {
	
	private LoginService service;
	
	public void setLoginService(LoginService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}

}
