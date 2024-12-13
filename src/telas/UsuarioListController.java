package telas;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Usuario;
import model.services.UsuarioService;

public class UsuarioListController implements Initializable {
	
	private UsuarioService service;
	
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
	
	public void setUsuarioService(UsuarioService service) {
		this.service = service;
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
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Usuario> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewUsuario.setItems(obsList);
	}

}
