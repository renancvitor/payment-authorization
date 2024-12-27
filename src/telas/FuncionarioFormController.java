package telas;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Cargo;
import model.entities.Departamento;
import model.services.CargoService;
import model.services.DepartamentoService;
import telas.util.Constraints;

public class FuncionarioFormController implements Initializable {
	
	private DepartamentoService serviceDepartamento;
	private CargoService serviceCargo;
	
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
	
	public void setDepartamentoService(DepartamentoService serviceDepartamento) {
		this.serviceDepartamento = serviceDepartamento;
	}
	
	public void setCargoService(CargoService serviceCargo) {
		this.serviceCargo = serviceCargo;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializaNodes();
	}
	
	public void initializaNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 30);
		Constraints.setTextFieldInteger(txtDataNascimento);
		Constraints.setTextFieldInteger(txtCpf);
		
		updatecomboBoxDepartamento();
		updatecomboBoxCargo();
	}
	
	public void updatecomboBoxDepartamento() {
		if (serviceDepartamento == null) {
			throw new IllegalStateException("Service was null");
		}
		
		List<Departamento> list = serviceDepartamento.findAll();
		
		obsListDepartamento = FXCollections.observableArrayList(list);
		comboBoxDepartamento.setItems(obsListDepartamento);
	}
	
	public void updatecomboBoxCargo() {
		if (serviceDepartamento == null) {
			throw new IllegalStateException("Service was null");
		}
		
		List<Cargo> list = serviceCargo.findAll();
		
		obslistCargo = FXCollections.observableArrayList(list);
		comboBoxCargo.setItems(obslistCargo);
	}
	
}
