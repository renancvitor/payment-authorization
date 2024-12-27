package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import model.entities.Cargo;
import model.entities.Departamento;
import model.services.CargoService;
import model.services.DepartamentoService;

public class FuncionarioFormController implements Initializable {
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private TextField txtDataNascimento;
	
	@FXML
	private ComboBox<Departamento> comboBoxDepartamento;
	
	@FXML
	private ComboBox<Cargo> comboBoxCargo;
	
	@FXML
	private TextField txtCpf;
	
	@FXML
	private Label labelErrorNome;
	
	@FXML
	private Label labelErrorDataNascimento;
	
	@FXML
	private Label labelErrorCpf;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancelar;
	
	private ObservableList<Departamento> obsListDepartamento;
	private ObservableList<Cargo> obslistCargo;
	
	@FXML
	public void onBtSalvarAction() {
		
	}
	
	@FXML
	public void onBtCancelarAction() {
		
	}
		
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		loadData();
	}
	
	public void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtNome, 30);
        Constraints.setTextFieldInteger(txtDataNascimento);
        Constraints.setTextFieldInteger(txtCpf);
        
        comboBoxDepartamento.getItems().clear();
        comboBoxCargo.getItems().clear();
    }
	
	public void loadData() {
		updateComboBoxDepartamento();
        updateComboBoxCargo();
    }
	
	public void updateComboBoxDepartamento() {
		DepartamentoService serviceDepartamento = new DepartamentoService();		

	    List<Departamento> list = serviceDepartamento.findAll();

	    obsListDepartamento = FXCollections.observableArrayList(list);

	    comboBoxDepartamento.setItems(obsListDepartamento);

	    comboBoxDepartamento.setCellFactory(param -> new ListCell<Departamento>() {
	        @Override
	        protected void updateItem(Departamento item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty || item == null) {
	                setText(null);
	            } else {
	                setText(item.getNome());
	            }
	        }
	    });

	    comboBoxDepartamento.setButtonCell(new ListCell<Departamento>() {
	        @Override
	        protected void updateItem(Departamento item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty || item == null) {
	                setText(null);
	            } else {
	                setText(item.getNome());
	            }
	        }
	    });
	}
	
	public void updateComboBoxCargo() {
		CargoService serviceCargo = new CargoService();

	    List<Cargo> list = serviceCargo.findAll();

	    obslistCargo = FXCollections.observableArrayList(list);

	    comboBoxCargo.setItems(obslistCargo);

	    comboBoxCargo.setCellFactory(param -> new ListCell<Cargo>() {
	        @Override
	        protected void updateItem(Cargo item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty || item == null) {
	                setText(null);
	            } else {
	                setText(item.getNome());
	            }
	        }
	    });
	    
	    comboBoxCargo.setButtonCell(new ListCell<Cargo>() {
	        @Override
	        protected void updateItem(Cargo item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty || item == null) {
	                setText(null);
	            } else {
	                setText(item.getNome());
	            }
	        }
	    });
	}
	
}
