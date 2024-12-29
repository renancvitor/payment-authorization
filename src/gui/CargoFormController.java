package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
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
import model.entities.Cargo;
import model.services.CargoService;

public class CargoFormController implements Initializable {
	
	private Cargo cargo;
	
	private CargoService cargoService;
	
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
	
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	public void setCargoService(CargoService cargoService) {
		this.cargoService = cargoService;
	}
	
	@FXML
	public void onBtSalvarAction(ActionEvent event) {
		try {
			cargo = getFormData();
			cargoService.saveOrUpdate(cargo);
			utils.currentStage(event).close();
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private Cargo getFormData() {
		Cargo obj = new Cargo();
			
		obj.setId(utils.tryParseToInt(txtId.getText()));
		obj.setNome(txtNome.getText());
			
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

	public void initializaNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 30);
	}
	
	public void updateFormData() {
		txtId.setText(String.valueOf(cargo.getId()));
		txtNome.setText(cargo.getNome());
	}

}
