package telas;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.UserType;
import model.entities.Usuario;

public class UsuarioListController implements Initializable {
	
	@FXML
	private TableView<Usuario> tableViewUsuario;
	
	@FXML
	private TableColumn<Usuario, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Usuario, String> tableColumnLogin;
	
	@FXML
	private TableColumn<Usuario, String> tableColumnSenha;
	
	@FXML
	private TableColumn<Usuario, String> tableColumnCpf;
	
	@FXML
	private Button btNew;
	
	private ObservableList<Usuario> obsList;
	
	@FXML
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializableNodes();
	}

	private void initializableNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnLogin.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnSenha.setCellValueFactory(new PropertyValueFactory<>("senhs"));
		tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewUsuario.prefHeightProperty().bind(stage.heightProperty());
	}

}
