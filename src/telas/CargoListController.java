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
import model.entities.Cargo;
import model.entities.Departamento;
import model.services.CargoService;
import model.services.DepartamentoService;

public class CargoListController implements Initializable {
	
	private CargoService service;

	@FXML
	private TableView<Cargo> tableViewCargo;
	
	@FXML
	private TableColumn<Cargo, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Cargo, String> tableColumnNome;
	
	@FXML
	private Button btNew;
	
	private ObservableList<Cargo> obsList;
	
	@FXML
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
	}
	
	public void setCargoService(CargoService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		initializaNodes();
		
	}

	private void initializaNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewCargo.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Cargo> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewCargo.setItems(obsList);
	}

}
