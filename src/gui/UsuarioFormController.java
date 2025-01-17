package gui;

import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.entities.UserType;
import model.entities.Usuario;
import model.exceptions.ValidationException;
import model.services.UserTypeService;
import model.services.UsuarioService;

public class UsuarioFormController implements Initializable {
	
	private Usuario usuario;
	
	private UsuarioService usuarioService;
	
	private UserTypeService userTypeService;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtLogin;
	
	@FXML
	private TextField txtSenha;
	
	@FXML
	private TextField txtCpf;
	
	@FXML
	private ComboBox<UserType> comboBoxUserType;
	
	@FXML
	private Label labelErrorLogin;
	
	@FXML
	private Label labelErrorSenha;
	
	@FXML
	private Label labelErrorCpf;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancelar;
	
	private ObservableList<UserType> obsListUserType;
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@FXML
	public void onBtSalvarAction(ActionEvent event) {
		try {
			usuario = getFormData();
			usuarioService.saveOrUpdate(usuario);
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
	
	private Usuario getFormData() {
		Usuario usuario = new Usuario(txtLogin.getText(), txtSenha.getText(), txtCpf.getText(), comboBoxUserType.getValue());
		
		ValidationException exception = new ValidationException("Validation Error");
		
		if (txtLogin.getText() == null || txtLogin.getText().trim().equals("")) {
			exception.addError("login", "Campo não pode ser vazio.");
		}
		if (txtSenha.getText() == null || txtSenha.getText().trim().equals("")) {
			exception.addError("senha", "Campo não pode ser vazio.");
		}
		if (txtCpf.getText() == null || txtCpf.getText().trim().equals("")) {
			exception.addError("cpf", "Campo não pode ser vazio.");
		}
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}
			
		return usuario;
	}

	@FXML
	public void onBtCancelarAction(ActionEvent event) {
		utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializaNodes();
	    loadData();
	}

	public void initializaNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtLogin, 10);
		Constraints.setTextFieldMaxLength(txtSenha, 8);
		utils.applyCpfMask(txtCpf);
		
		comboBoxUserType.getItems().clear();
	}
	
	public void loadData() {
		updateComboBoxUserType();
    }
	
	public void updateComboBoxUserType() {
		userTypeService = new UserTypeService();
		
		List<UserType> list = userTypeService.loadUserTypes();
		
		obsListUserType = FXCollections.observableArrayList(list);
		
		comboBoxUserType.setItems(obsListUserType);
		
	    comboBoxUserType.setCellFactory(param -> new ListCell<UserType>() {
	        @Override
	        protected void updateItem(UserType item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty || item == null) {
	                setText(null);
	            } else {
	                setText(item.name());
	            }
	        }
	    });
	    
	    comboBoxUserType.setButtonCell(new ListCell<UserType>() {
	        @Override
	        protected void updateItem(UserType item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty || item == null) {
	                setText(null);
	            } else {
	                setText(item.name());
	            }
	        }
	    });
	}
	
	public void updateFormData() {
		txtId.setText(String.valueOf(usuario.getId()));
		txtLogin.setText(usuario.getLogin());
		txtSenha.setText(usuario.getSenha());
		txtCpf.setText(usuario.getCpf());
		
		comboBoxUserType.setValue(usuario.getUserType());
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if (fields.contains("nome")) {
			labelErrorLogin.setText(errors.get("nome"));
		}
		if (fields.contains("senha")) {
			labelErrorSenha.setText(errors.get("senha"));
		}
		if (fields.contains("cpf")) {
			labelErrorCpf.setText(errors.get("cpf"));
		}
	}
	
}
