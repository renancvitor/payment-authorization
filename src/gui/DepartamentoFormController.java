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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Departamento;
import model.exceptions.ValidationException;
import model.services.DepartamentoService;

public class DepartamentoFormController implements Initializable {
	
	private Departamento departamento;
	
	private DepartamentoService departamentoService;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private Label labelErrorNome;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancelar;
	
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	public void setDepartamentoService(DepartamentoService departamentoService) {
		this.departamentoService = departamentoService;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@FXML
	public void onBtSalvarAction(ActionEvent event) {
		try {
			departamento = getFormData();
			departamentoService.saveOrUpdate(departamento);
			notifyDataChangeListeners();
			utils.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		} catch (DbException e) {
			Alerts.showAlert("Erro ao salvar.", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	private Departamento getFormData() {
		Departamento obj = new Departamento();
		
		ValidationException exception = new ValidationException("Erro de validação.");
		
		obj.setId(utils.tryParseToInt(txtId.getText()));
		
		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "Campo não pode ser vazio.");
		}
		obj.setNome(txtNome.getText());
		
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
		initializaNodes();
	}
	
	private void initializaNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 30);
	}
	
	public void updateFormData() {
		txtId.setText(String.valueOf(departamento.getId()));
		txtNome.setText(departamento.getNome());
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if (fields.contains("nome")) {
			labelErrorNome.setText(errors.get("nome"));
		}
	}

}
