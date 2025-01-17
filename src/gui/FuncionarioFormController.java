package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import model.entities.Cargo;
import model.entities.Departamento;
import model.entities.Pessoa;
import model.exceptions.ValidationException;
import model.services.CargoService;
import model.services.DepartamentoService;
import model.services.FuncionarioService;

public class FuncionarioFormController implements Initializable {
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private DepartamentoService departamentoService;
	private CargoService cargoService;
	
	private Pessoa funcionario;
	
	private FuncionarioService funcionarioService;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private DatePicker dpDataNascimento;
	
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
	
	public void setFuncionario(Pessoa funcionario) {
		this.funcionario = funcionario;
	}
	
	public void setFuncionarioService(FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@FXML
	public void onBtSalvarAction(ActionEvent event) {
		try {
			funcionario = getFormData();
			funcionarioService.saveOrUpdate(funcionario);
			notifyDataChangeListeners();
			utils.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}
	
	private Pessoa getFormData() {
		Pessoa obj = new Pessoa();
		
		ValidationException exception = new ValidationException("Validation Error");
			
		obj.setId(utils.tryParseToInt(txtId.getText()));
		
		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "Campo não pode ser vazio.");
		}
		obj.setNome(txtNome.getText());
		
		if (dpDataNascimento.getValue() == null) {
		    exception.addError("datanascimento", "Campo não pode ser vazio.");
		} else {
		    try {
		        LocalDate dataNascimento = dpDataNascimento.getValue();
		        obj.setDatanascimento(dataNascimento);
		    } catch (Exception e) {
		        exception.addError("datanascimento", "Data inválida! Por favor, insira uma data no formato dd/MM/yyyy.");
		    }
		}

	    
	    obj.setDepartamento(comboBoxDepartamento.getValue());
	    obj.setCargo(comboBoxCargo.getValue());
	    
	    if (txtCpf.getText() == null || txtCpf.getText().trim().equals("")) {
			exception.addError("cpf", "Campo não pode ser vazio.");
		}
	    obj.setCpf(txtCpf.getText());
	    
	    if (exception.getErrors().size() > 0) {
			throw exception;
		}
			
		return obj;
	}

	@FXML
	public void onBtCancelarAction(ActionEvent event) {
		utils.currentStage(event).close();
	}
		
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		loadData();
	}
	
	public void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtNome, 30);
        utils.applyCpfMask(txtCpf);
        utils.formatDatePicker(dpDataNascimento, "dd/MM/yyyy");
        
        comboBoxDepartamento.getItems().clear();
        comboBoxCargo.getItems().clear();
    }
	
	public void loadData() {
		updateComboBoxDepartamento();
        updateComboBoxCargo();
    }
	
	public void updateComboBoxDepartamento() {
		departamentoService = new DepartamentoService();

	    List<Departamento> list = departamentoService.findAll();

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
		cargoService = new CargoService();

	    List<Cargo> list = cargoService.findAll();

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
	
	public void updateFormData() {
		txtId.setText(String.valueOf(funcionario.getId()));
		txtNome.setText(funcionario.getNome());
		
		if (funcionario.getDatanascimento() != null) {
			dpDataNascimento.setValue(funcionario.getDatanascimento());
	    } else {
	    	dpDataNascimento.setPromptText("");
	    }
		
		comboBoxDepartamento.setValue(funcionario.getDepartamento());
		comboBoxCargo.setValue(funcionario.getCargo());
		txtCpf.setText(funcionario.getCpf());
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if (fields.contains("nome")) {
			labelErrorNome.setText(errors.get("nome"));
		}
		if (fields.contains("datanascimento")) {
			labelErrorDataNascimento.setText(errors.get("datanascimento"));
		}
		if (fields.contains("cpf")) {
			labelErrorCpf.setText(errors.get("cpf"));
		}
	}
	
}
