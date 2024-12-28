package gui;

import java.net.URL;
import java.time.format.DateTimeFormatter;
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
import model.entities.Pessoa;
import model.services.CargoService;
import model.services.DepartamentoService;

public class FuncionarioFormController implements Initializable {
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private DepartamentoService departamentoService;
	private CargoService cargoService;
	
	private Pessoa funcionario;
	
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
	
	public void setFuncionario(Pessoa funcionario) {
		this.funcionario = funcionario;
	}
	
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
		// txtDataNascimento.setText(funcionario.getDatanascimento().format(formatter));
		
		if (funcionario.getDatanascimento() != null) {
	        txtDataNascimento.setText(funcionario.getDatanascimento().format(formatter));
	    } else {
	        txtDataNascimento.setText("");
	    }
		
		/*String dataNascimentoStr = (funcionario.getDatanascimento() != null) 
                ? funcionario.getDatanascimento().format(formatter) 
                : "Data não informada";

		txtDataNascimento.setText(dataNascimentoStr);*/
		
		comboBoxDepartamento.setValue(funcionario.getDepartamento());
		comboBoxCargo.setValue(funcionario.getCargo());
		txtCpf.setText(funcionario.getCpf());
	}
	
}
