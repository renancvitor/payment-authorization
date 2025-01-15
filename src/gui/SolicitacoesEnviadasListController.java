package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.dao.DaoFactory;
import model.dao.SolicitacoesEnviadasDao;
import model.entities.NovaSolicitacao;
import model.entities.SolicitacoesEnviadas;
import model.entities.StatusSolicitacao;
import model.services.NovaSolicitacaoService;
import model.services.SolicitacoesEnviadasService;

public class SolicitacoesEnviadasListController implements Initializable, DataChangeListener {
	
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
	private TableColumn<SolicitacoesEnviadas, SolicitacoesEnviadas> tableColumnApprove;
	
	@FXML
	private TableColumn<SolicitacoesEnviadas, SolicitacoesEnviadas> tableColumnReprove;
	
	@FXML
	private Button btnNovaSolicitacao;
	
	@FXML
	public void onBtnNovaSolicitacaoAction(ActionEvent event) {
		Stage parentStage = utils.currentStage(event);
		NovaSolicitacao obj = new NovaSolicitacao();
		createDialogForm(obj, "/gui/NovaSolicitacaoForm.fxml", parentStage);
	}
	
	@FXML
	private Button btnAprovar;
	
	@FXML
	public void onBtnAprovarAction() { 
		List<SolicitacoesEnviadas> selectedSolicitacoes = tableViewSolicitacoesEnviadas.getSelectionModel().getSelectedItems();
	    if (selectedSolicitacoes.isEmpty()) {
	        Alerts.showAlert("Aviso", "Aviso", "Selecione uma solicitação para aprovar.", AlertType.WARNING);
	    } else {
	        for (SolicitacoesEnviadas solicitacao : selectedSolicitacoes) {
	            try {
	                aprovarSolicitacao(solicitacao);
	            } catch (SQLException e) {
	                e.printStackTrace();
	                Alerts.showAlert("Erro", "Erro", "Erro ao aprovar a solicitação: " + e.getMessage(), AlertType.ERROR);
	            }
	        }
	    }
	}
	
	@FXML
	private Button btnReprovar;
	
	@FXML
	public void onBtnReprovarAction() {
		List<SolicitacoesEnviadas> selectedSolicitacoes = tableViewSolicitacoesEnviadas.getSelectionModel().getSelectedItems();
	    if (selectedSolicitacoes.isEmpty()) {
	        Alerts.showAlert("Aviso", "Aviso", "Selecione uma solicitação para reprovar.", AlertType.WARNING);
	    } else {
	        for (SolicitacoesEnviadas solicitacao : selectedSolicitacoes) {
	            try {
	                reprovarSolicitacao(solicitacao);
	            } catch (SQLException e) {
	                e.printStackTrace();
	                Alerts.showAlert("Erro", "Erro", "Erro ao reprovar a solicitação: " + e.getMessage(), AlertType.ERROR);
	            }
	        }
	    }
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
		utils.formatTableColumnTimestamp(tableColumnDataCriacao, "dd/MM/yyyy");
		
		tableColumnDataPagamento.setCellValueFactory(new PropertyValueFactory<>("dataPagamento"));
		utils.formatTableColumnDate(tableColumnDataPagamento, "dd/MM/yyyy");
		
		tableColumnFormaPagamento.setCellValueFactory(new PropertyValueFactory<>("formaPagamento"));
		tableColumnValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
		utils.formatTableColumnDouble(tableColumnValorTotal, 2);
		
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
		
		initApproveColumn();
		initReproveColumn();
	}
	
	private void createDialogForm(NovaSolicitacao obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			NovaSolicitacaoFormController controller = loader.getController();
			controller.setNovaSolicitacao(obj);
			controller.setNovaSolicitacaoService(new NovaSolicitacaoService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter cargo data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			
			if (pane == null || dialogStage == null) {
			    System.out.println("Erro: pane ou dialogStage está nulo.");
			} else {
			    dialogStage.showAndWait();
			}
			
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	@Override
	public void onDataChanged() {
		updateTableView();
	}
	
	private void aprovarSolicitacao(SolicitacoesEnviadas solicitacao) throws SQLException {

		SolicitacoesEnviadasDao solicitacoesEnviadasDao = DaoFactory.createSolicitacoesEnviadasDao();

	    solicitacao.setStatus(StatusSolicitacao.APROVADA);
	    solicitacoesEnviadasDao.update(solicitacao);
	    tableViewSolicitacoesEnviadas.getItems().remove(solicitacao);
	}

	public void initApproveColumn() {
	    tableColumnApprove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
	    tableColumnApprove.setCellFactory(param -> new TableCell<SolicitacoesEnviadas, SolicitacoesEnviadas>() {
	        private final Button button = new Button("Aprovar");

	        @Override
	        protected void updateItem(SolicitacoesEnviadas obj, boolean empty) {
	            super.updateItem(obj, empty);
	            
	            if (empty || obj == null) {
	                setGraphic(null);
	                return;
	            }
	            setGraphic(button);
	            button.setOnAction(event -> {
	                try {
						aprovarSolicitacao(obj);
					} catch (SQLException e) {
						e.printStackTrace();
					}	                
	            });
	        }
	    });
	}
	
	private void reprovarSolicitacao(SolicitacoesEnviadas solicitacao) throws SQLException {

		SolicitacoesEnviadasDao solicitacoesEnviadasDao = DaoFactory.createSolicitacoesEnviadasDao();
		
	    solicitacao.setStatus(StatusSolicitacao.REPROVADA);
	    solicitacoesEnviadasDao.update(solicitacao);
	    tableViewSolicitacoesEnviadas.getItems().remove(solicitacao);
	}
	
	private void initReproveColumn() {
		tableColumnReprove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnReprove.setCellFactory(param -> new TableCell<SolicitacoesEnviadas, SolicitacoesEnviadas>() {
			private final Button button = new Button("Reprovar");

	        @Override
	        protected void updateItem(SolicitacoesEnviadas obj, boolean empty) {
	            super.updateItem(obj, empty);
	            
	            if (empty || obj == null) {
	                setGraphic(null);
	                return;
	            }
	            setGraphic(button);
	            button.setOnAction(event -> {
	                try {
	                	reprovarSolicitacao(obj);
					} catch (SQLException e) {
						e.printStackTrace();
					}	                
	            });
	        }
		});
	}

}
