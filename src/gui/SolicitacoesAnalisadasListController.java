package gui;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.SolicitacoesAnalisadas;
import model.entities.StatusSolicitacao;
import model.services.SolicitacoesAnalisadasService;

public class SolicitacoesAnalisadasListController implements Initializable {
	
	private SolicitacoesAnalisadasService service;
	
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
		tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		tableColumnDataCriacao.setCellValueFactory(new PropertyValueFactory<>("dataCriacao"));
		tableColumnDataPagamento.setCellValueFactory(new PropertyValueFactory<>("dataPagamento"));
		tableColumnFormaPagamento.setCellValueFactory(new PropertyValueFactory<>("formaPagamento"));
		tableColumnValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
		tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		tableColumnLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewSolicitacoesAnalisadas.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		int idTipoUsuario = 0;
		int idUser = 0;
		
		List<SolicitacoesAnalisadas> list = service.select(idTipoUsuario, idUser);
		obsList = FXCollections.observableArrayList(list);
		tableViewSolicitacoesAnalisadas.setItems(obsList);
	}

}
