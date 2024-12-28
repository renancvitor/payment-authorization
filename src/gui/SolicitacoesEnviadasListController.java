package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.NovaSolicitacao;
import model.entities.SolicitacoesEnviadas;
import model.services.SolicitacoesEnviadasService;

public class SolicitacoesEnviadasListController implements Initializable {
	
	private SolicitacoesEnviadasService service;
	
	@FXML
	private TableView<SolicitacoesEnviadas> tableViewSolicitacoesEnviadas;
	
	@FXML
	private TableColumn<SolicitacoesEnviadas, Integer> tableColumnId;
	
	@FXML
	private TableColumn<SolicitacoesEnviadas, String> tableColumnFornecedor;
	
	@FXML
	private TableColumn<SolicitacoesEnviadas, String> tableColumnDescricao;
	
	@FXML
	private TableColumn<SolicitacoesEnviadas, Timestamp> tableColumnDataCriacao;
	
	@FXML
	private TableColumn<SolicitacoesEnviadas, Date> tableColumnDataPagamento;
	
	@FXML
	private TableColumn<SolicitacoesEnviadas, String> tableColumnFormaPagamento;
	
	@FXML
	private TableColumn<SolicitacoesEnviadas, Double> tableColumnValorTotal;
	
	@FXML
	private TableColumn<SolicitacoesEnviadas, String> tableColumnStatus;
	
	@FXML
	private TableColumn<SolicitacoesEnviadas, String> tableColumnLogin;
	
	@FXML
	private Button btnNovaSolicitacao;
	
	@FXML
	public void onBtnNovaSolicitacaoAction(ActionEvent event) {
		Stage parentStage = utils.currentStage(event);
		NovaSolicitacao obj = new NovaSolicitacao();
		createDialogForm(obj, "/gui/NovaSolicitacao.fxml", parentStage);
	}
	
	@FXML
	private Button btnAprovar;
	
	@FXML
	public void onBtnAprovarAction() {
		
	}
	
	@FXML
	private Button btnReprovar;
	
	@FXML
	public void onBtnReprovarAction() {
		
	}
	
	private ObservableList<SolicitacoesEnviadas> obsList;
	
	public void setSolicitacoesEnviadasService(SolicitacoesEnviadasService service) {
		this.service = service;
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		initializeNodes();
	}
	
	public void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
		tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		tableColumnDataCriacao.setCellValueFactory(new PropertyValueFactory<>("dataCriacao"));
		tableColumnDataPagamento.setCellValueFactory(new PropertyValueFactory<>("dataPagamento"));
		tableColumnFormaPagamento.setCellValueFactory(new PropertyValueFactory<>("formaPagamento"));
		tableColumnValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
		tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		tableColumnLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewSolicitacoesEnviadas.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
			
		}
		int idTipoUsuario = 0;
		int idUser = 0;
		
		List<SolicitacoesEnviadas> list = service.findAll(idTipoUsuario, idUser);
		obsList = FXCollections.observableArrayList(list);
		tableViewSolicitacoesEnviadas.setItems(obsList);
	}
	
	private void createDialogForm(NovaSolicitacao obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			NovaSolicitacaoFormController controller = loader.getController();
			controller.setNovaSolicitacao(obj);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter cargo data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

}
