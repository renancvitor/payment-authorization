package telas;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemFuncionario;
	
	@FXML
	private MenuItem menuItemUsuario;
	
	@FXML
	private MenuItem menuItemDepartamento;
	
	@FXML
	private MenuItem menuItemCargo;
	
	@FXML
	public void onMenuItemFuncionarioAction() {
		System.out.println("onMenuItemFuncionarioAction");
	}
	
	@FXML
	public void onMenuItemUsuario() {
		System.out.println("onMenuItemUsuario");
	}
	
	@FXML
	public void onMenuItemDepartamento() {
		System.out.println("onMenuItemDepartamento");
	}
	
	@FXML
	public void onMenuItemCargo() {
		System.out.println("onMenuItemCargo");
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}

}
