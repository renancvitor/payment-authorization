package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Cargo;
import model.services.CargoService;
import model.services.UserLoginService;

public class CargoListController implements Initializable, DataChangeListener {
	
	private CargoService service;

	@FXML
	private TableView<Cargo> tableViewCargo;
	
	@FXML
	private TableColumn<Cargo, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Cargo, String> tableColumnNome;
	
	@FXML
	private Button btNew;
	
	@FXML
	private Button buttonLogout;
	
	@FXML
	public void onMenuItemLogoutAction() {
	    UserLoginService.logout();
	    
	    Stage currentStage = (Stage) Main.getMainScene().getWindow();
	    
	    Alerts.showAlertWithOwner("Logout", "Você foi desconectado", "Você foi desconectado do sistema.", AlertType.INFORMATION, currentStage);

	    currentStage.close();

	    try {
	        Stage loginStage = new Stage();
	        Main mainApp = new Main();
	        mainApp.start(loginStage);
	    } catch (Exception e) {
	        Alerts.showAlert("Erro", "Erro ao retornar à tela de login", e.getMessage(), AlertType.ERROR);
	        e.printStackTrace();
	    }
	}
	
	private ObservableList<Cargo> obsList;
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = utils.currentStage(event);
		Cargo obj = new Cargo();
		createDialogForm(obj, "/gui/CargoForm.fxml", parentStage);
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
	
	private void createDialogForm(Cargo obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			CargoFormController controller = loader.getController();
			controller.setCargo(obj);
			controller.setCargoService(new CargoService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Cadastrar cargo");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	@Override
	public void onDataChanged() {
		updateTableView();
	}

}
