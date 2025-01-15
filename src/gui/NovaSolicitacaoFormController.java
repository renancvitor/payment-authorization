package gui;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.NovaSolicitacao;
import model.exceptions.ValidationException;
import model.services.NovaSolicitacaoService;

public class NovaSolicitacaoFormController implements Initializable {
	
	SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	
	private NovaSolicitacao novaSolicitacao;
	
	private NovaSolicitacaoService novaSolicitacaoService;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtFornecedor;
	
	@FXML
	private TextField txtDescricao;
	
	@FXML
	private DatePicker dpDataPagamento;
	
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
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@FXML
	public void onBtEnviarAction(ActionEvent event) {
		try {
			novaSolicitacao = getFormData();
			novaSolicitacaoService.saveOrUpdate(novaSolicitacao);
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
	
	private NovaSolicitacao getFormData() {
		NovaSolicitacao obj = new NovaSolicitacao();
		
		ValidationException exception = new ValidationException("Validation Error");
		
		if (txtFornecedor.getText() == null || txtFornecedor.getText().trim().equals("")) {
			exception.addError("fornecedor", "Campo não pode ser vazio.");
		}
		obj.setFornecedor(txtFornecedor.getText());
		
		if (txtDescricao.getText() == null || txtDescricao.getText().trim().equals("")) {
			exception.addError("descricao", "Campo não pode ser vazio.");
		}
		obj.setDescricao(txtDescricao.getText());
		
		if (dpDataPagamento.getValue() == null) {
		    exception.addError("datapagamento", "Campo não pode ser vazio.");
		} else {
		    LocalDate dataPagamentoLocalDate = dpDataPagamento.getValue();
		    Date dataPagamento = Date.from(dataPagamentoLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		    obj.setDataPagamento(dataPagamento);
		}

		
	    if (txtFormaPagamento.getText() == null || txtFormaPagamento.getText().trim().equals("")) {
			exception.addError("formapagamento", "Campo não pode ser vazio.");
		}
		obj.setFormaPagamento(txtFormaPagamento.getText());
		
		if (txtValorTotal.getText() == null || txtValorTotal.getText().trim().equals("")) {
			exception.addError("valortotal", "Campo não pode ser vazio.");
		}
		String valorTotalText = txtValorTotal.getText();
	    try {
	        double valorTotal = Double.parseDouble(valorTotalText.replace(",", "."));
	        obj.setValorTotal(valorTotal);
	    } catch (NumberFormatException e) {
	        System.out.println("Valor inválido! Por favor, insira um número válido.");
	    }
	    
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
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializaNodes();
	}
	
	private void initializaNodes() {
		Constraints.setTextFieldDouble(txtValorTotal);
		utils.formatDatePicker(dpDataPagamento, "dd/MM/yyyy");
	}
	
	public void updateFormData() {
		txtFornecedor.setText(novaSolicitacao.getFornecedor());
		txtDescricao.setText(novaSolicitacao.getDescricao());
		//txtDataPagamento.setText(dateFormatter.format(novaSolicitacao.getDataPagamento()));
		
		if (novaSolicitacao.getDataPagamento() != null) {
			dpDataPagamento.setValue(LocalDate.ofInstant(novaSolicitacao.getDataPagamento().toInstant(), ZoneId.systemDefault()));
	        // dpDataPagamento.setValue(dateFormatter.format(novaSolicitacao.getDataPagamento()));
	    } else {
	        dpDataPagamento.setPromptText("");
	    }
		
		txtFormaPagamento.setText(novaSolicitacao.getFormaPagamento());
		txtValorTotal.setText(String.format("%.2f", novaSolicitacao.getValorTotal()));
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if (fields.contains("fornecedor")) {
			labelErrorFornecedor.setText(errors.get("fornecedor"));
		}
		if (fields.contains("descricao")) {
			labelErrorDescricao.setText(errors.get("descricao"));
		}
		if (fields.contains("datapagamento")) {
			labelErrorDataPagamento.setText(errors.get("datapagamento"));
		}
		if (fields.contains("formapagamento")) {
			labelErrorFormaPagamento.setText(errors.get("formapagamento"));
		}
		if (fields.contains("valortotal")) {
			labelErrorValorTotal.setText(errors.get("valortotal"));
		}
	}

}
