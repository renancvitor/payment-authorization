package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Cargo;
import model.entities.Departamento;
import model.entities.Pessoa;
import model.services.FuncionarioService;
import model.services.UserLoginService;

public class FuncionarioListController implements Initializable, DataChangeListener {
	
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
	
	@FXML
	private Button buttonLogout;
	
	@FXML
	public void onMenuItemLogoutAction() {
	    UserLoginService.logout();
	    Alerts.showAlert("Logout", "Você foi desconectado", "Você foi desconectado do sistema.", AlertType.INFORMATION);

	    Stage currentStage = (Stage) Main.getMainScene().getWindow();

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
	
	private ObservableList<Pessoa> obsList;
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = utils.currentStage(event);
		Pessoa obj = new Pessoa();
		createDialogForm(obj, "/gui/FuncionarioForm.fxml", parentStage);
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
		utils.formatTableColumnLocalDate(tableColumnDataNascimento, "dd/MM/yyyy");
		
		tableColumnDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));
		tableColumnDepartamento.setCellFactory(col -> {
		    return new TableCell<Pessoa, Departamento>() {
		        @Override
		        protected void updateItem(Departamento item, boolean empty) {
		            super.updateItem(item, empty);
		            if (item != null && !empty) {
		                setText(item.getNome());
		                Tooltip tooltip = new Tooltip(item.getNome());
		                setTooltip(tooltip);
		            } else {
		                setText(null);
		                setTooltip(null);
		            }
		        }
		    };
		});
		
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

	private void createDialogForm(Pessoa obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			FuncionarioFormController controller = loader.getController();
			controller.setFuncionario(obj);
			controller.setFuncionarioService(new FuncionarioService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Cadastrar funcionário");
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
