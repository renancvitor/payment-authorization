package gui;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.entities.NovaSolicitacao;
import model.services.NovaSolicitacaoService;

public class NovaSolicitacaoFormController implements Initializable {
	
	SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	
	private NovaSolicitacao novaSolicitacao;
	
	private NovaSolicitacaoService novaSolicitacaoService;
	
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
	
	public void setNovaSolicitacao(NovaSolicitacao novaSolicitacao) {
		this.novaSolicitacao = novaSolicitacao;
	}
	
	public void setNovaSolicitacaoService(NovaSolicitacaoService novaSolicitacaoService) {
		this.novaSolicitacaoService = novaSolicitacaoService;
	}
	
	@FXML
	public void onBtEnviarAction(ActionEvent event) {
		try {
			novaSolicitacao = getFormData();
			novaSolicitacaoService.saveOrUpdate(novaSolicitacao);
			utils.currentStage(event).close();
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private NovaSolicitacao getFormData() {
		NovaSolicitacao obj = new NovaSolicitacao();
		
		obj.setFornecedor(txtFornecedor.getText());
		obj.setDescricao(txtDescricao.getText());
		
		String dataPagamentoText = txtDataPagamento.getText();
	    if (dataPagamentoText != null && !dataPagamentoText.isEmpty()) {
	        try {
	            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	            Date dataPagamento = dateFormatter.parse(dataPagamentoText);
	            obj.setDataPagamento(dataPagamento);
	        } catch (Exception e) {
	            System.out.println("Data de pagamento inválida! Por favor, insira a data no formato dd/MM/yyyy.");
	        }
	    } else {
	        obj.setDataPagamento(null);
	    }
		
		obj.setFormaPagamento(txtFormaPagamento.getText());
		
		String valorTotalText = txtValorTotal.getText();
	    try {
	        double valorTotal = Double.parseDouble(valorTotalText.replace(",", "."));
	        obj.setValorTotal(valorTotal);
	    } catch (NumberFormatException e) {
	        System.out.println("Valor inválido! Por favor, insira um número válido.");
	    }
		
		return obj;
	}

	@FXML
	public void onBtCancelarAction(ActionEvent event) {
		utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializaNodes();
	}
	
	private void initializaNodes() {
		Constraints.setTextFieldInteger(txtValorTotal);
		Constraints.setTextFieldInteger(txtDataPagamento);
	}
	
	public void updateFormData() {
		txtFornecedor.setText(novaSolicitacao.getFornecedor());
		txtDescricao.setText(novaSolicitacao.getDescricao());
		//txtDataPagamento.setText(dateFormatter.format(novaSolicitacao.getDataPagamento()));
		
		if (novaSolicitacao.getDataPagamento() != null) {
	        txtDataPagamento.setText(dateFormatter.format(novaSolicitacao.getDataPagamento()));
	    } else {
	        txtDataPagamento.setText("");
	    }
		
		txtFormaPagamento.setText(novaSolicitacao.getFormaPagamento());
		txtValorTotal.setText(String.format("%.2f", novaSolicitacao.getValorTotal()));
	}

}
