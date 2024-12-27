package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NovaSolicitacaoFormController implements Initializable {
	
	@FXML
	private TextField txtFornecedor;
	
	@FXML
	private TextField txtDescricao;
	
	@FXML
	private TextField txtDataPagamento;
	
	@FXML
	private TextField txtFormaPagamento;
	
	@FXML
	private TextField txtValorTotal;
	
	@FXML
	private Label labelErrorFornecedor;
	
	@FXML
	private Label labelErrorDescricao;
	
	@FXML
	private Label labelErrorDataPagamento;
	
	@FXML
	private Label labelErrorFormaPagamento;
	
	@FXML
	private Label labelErrorValorTotal;
	
	@FXML
	private Button btEnviar;
	
	@FXML
	private Button btCancelar;
	
	@FXML
	public void onBtEnviarAction() {
		
	}
	
	@FXML
	public void onBtCancelarAction() {
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializaNodes();
	}
	
	private void initializaNodes() {
		Constraints.setTextFieldInteger(txtValorTotal);
		Constraints.setTextFieldInteger(txtDataPagamento);
	}

}
