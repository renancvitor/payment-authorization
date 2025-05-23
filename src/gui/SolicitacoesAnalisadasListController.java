package gui;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.SolicitacoesAnalisadas;
import model.entities.StatusSolicitacao;
import model.entities.Usuario;
import model.services.SolicitacoesAnalisadasService;
import model.services.UserLoginService;

public class SolicitacoesAnalisadasListController implements Initializable, DataChangeListener {
	
	private SolicitacoesAnalisadasService service;
	
	private Usuario usuario;
	
	@FXML
	private TableView<SolicitacoesAnalisadas> tableViewSolicitacoesAnalisadas;
	
	@FXML
	private TableColumn<SolicitacoesAnalisadas, Integer> tableColumnId;
	
	@FXML
	private TableColumn<SolicitacoesAnalisadas, String> tableColumnFornecedor;
	
	@FXML
	private TableColumn<SolicitacoesAnalisadas, String> tableColumnDescricao;
	
	@FXML
	private TableColumn<SolicitacoesAnalisadas, Timestamp> tableColumnDataCriacao;
	
	@FXML
	private TableColumn<SolicitacoesAnalisadas, Date> tableColumnDataPagamento;
	
	@FXML
	private TableColumn<SolicitacoesAnalisadas, String> tableColumnFormaPagamento;
	
	@FXML
	private TableColumn<SolicitacoesAnalisadas, Double> tableColumnValorTotal;
	
	@FXML
	private TableColumn<SolicitacoesAnalisadas, StatusSolicitacao> tableColumnStatus;
	
	@FXML
	private TableColumn<SolicitacoesAnalisadas, String> tableColumnLogin;
	
	@FXML
	private Button buttonLogout;
	
	@FXML
	public void onMenuItemLogoutAction() {
	    UserLoginService.logout();
	    
	    Stage currentStage = (Stage) Main.getMainScene().getWindow();
	    
	    Alerts.showAlertWithOwner("Logout", "Você foi desconectado",
				"Você foi desconectado do sistema.", AlertType.INFORMATION, currentStage);

	    currentStage.close();

	    try {
	        Stage loginStage = new Stage();
	        Main mainApp = new Main();
	        mainApp.start(loginStage);
	    } catch (Exception e) {
	        Alerts.showAlert("Erro", "Erro ao retornar à tela de login",
					e.getMessage(), AlertType.ERROR);
	        e.printStackTrace();
	    }
	}
	
	private ObservableList<SolicitacoesAnalisadas> obsList;
	
	public void setSolicitacoesAnalisadasService(SolicitacoesAnalisadasService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		initializeNodes();
	}
	
	public void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		
		tableColumnFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
		tableColumnFornecedor.setCellFactory(col -> {
			return new TableCell<SolicitacoesAnalisadas, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (item != null && !empty) {
						setText(item);
						Tooltip tooltip = new Tooltip(item);
						setTooltip(tooltip);
					} else {
						setText(null);
						setTooltip(null);
					}
				}
			};
		});
		
		tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		tableColumnDescricao.setCellFactory(col -> {
			return new TableCell<SolicitacoesAnalisadas, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (item != null && !empty) {
						setText(item);
						Tooltip tooltip = new Tooltip(item);
						setTooltip(tooltip);
					} else {
						setText(null);
						setTooltip(null);
					}
				}
			};
		});
		
		tableColumnDataCriacao.setCellValueFactory(new PropertyValueFactory<>("dataCriacao"));
		utils.formatTableColumnTimestamp(tableColumnDataCriacao, "dd/MM/yyyy");
		
		tableColumnDataPagamento.setCellValueFactory(new PropertyValueFactory<>("dataPagamento"));
		utils.formatTableColumnDate(tableColumnDataPagamento, "dd/MM/yyyy");
		
		tableColumnFormaPagamento.setCellValueFactory(new PropertyValueFactory<>("formaPagamento"));
		tableColumnFormaPagamento.setCellFactory(col -> {
			return new TableCell<SolicitacoesAnalisadas, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (item != null && !empty) {
						setText(item);
						Tooltip tooltip = new Tooltip(item);
						setTooltip(tooltip);
					}
				}
			};
		});
		
		tableColumnValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
		utils.formatTableColumnDouble(tableColumnValorTotal, 2);
		
		tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		tableColumnLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewSolicitacoesAnalisadas.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView(Usuario usuarioAtual) {
		usuario = usuarioAtual;
		int idTipoUsuario = usuario.getUserType().getId();
		int idUser = usuario.getId();
		StatusSolicitacao status = null;
		
		List<SolicitacoesAnalisadas> list = service.select(status, idTipoUsuario, idUser);
		obsList = FXCollections.observableArrayList(list);
		tableViewSolicitacoesAnalisadas.setItems(obsList);
	}
	
	@Override
	public void onDataChanged() {
		updateTableView(usuario);
	}
}