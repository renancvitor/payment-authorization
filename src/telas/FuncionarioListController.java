package telas;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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
import model.entities.Cargo;
import model.entities.Departamento;
import model.entities.Pessoa;
import model.services.FuncionarioService;

public class FuncionarioListController implements Initializable {
	
	private FuncionarioService service;
	
	@FXML
	private TableView<Pessoa> tableViewFuncionario;
	
	@FXML
	private TableColumn<Pessoa, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Pessoa, String> tableColumnNome;
	
	@FXML
	private TableColumn<Pessoa, LocalDate> tableColumnDataNascimento;
	
	@FXML
	private TableColumn<Pessoa, Departamento> tableColumnDepartamento;
	
	@FXML
	private TableColumn<Pessoa, Cargo> tableColumnCargo;
	
	@FXML
	private TableColumn<Pessoa, String> tableColumnCpf;
	
	@FXML
	private Button btNew;
	
	private ObservableList<Pessoa> obsList;
	
	@FXML
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
	}
	
	public void setFuncionarioService(FuncionarioService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializableNodes();
	}

	private void initializableNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnDataNascimento.setCellValueFactory(new PropertyValueFactory<>("datanascimento"));
		tableColumnDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));
		tableColumnCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
		tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewFuncionario.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		
		List<Pessoa> list = service.findAll();
		
		obsList = FXCollections.observableArrayList(list);
		tableViewFuncionario.setItems(obsList);
	}

}
