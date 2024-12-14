package telas;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entities.SolicitacoesEnviadas;
import model.services.CargoService;
import model.services.DepartamentoService;
import model.services.FuncionarioService;
import model.services.NovaSolicitacaoService;
import model.services.SolicitacoesAnalisadasService;
import model.services.SolicitacoesEnviadasService;
import model.services.UsuarioService;
import telas.util.Alerts;

public class MainViewController implements Initializable {

	private SolicitacoesEnviadasService service;
	
	@FXML
	private MenuItem menuItemFuncionario;
	
	@FXML
	private MenuItem menuItemUsuario;
	
	@FXML
	private MenuItem menuItemDepartamento;
	
	@FXML
	private MenuItem menuItemCargo;
	
	@FXML
	private Button btnNovaSolicitacao;
	
	@FXML
	private Button btnSolicitacoesAnalisadas;
	
	@FXML
	public void onBtnNovaSolicitacao() {
		loadView("/telas/NovaSolicitacao.fxml", (NovaSolicitacaoListController controller) -> {
			controller.setNovaSolicitacaoService(new NovaSolicitacaoService());
		});
	}
	
	@FXML
	public void onBtnSolicitacoesAnalisadas() {
		loadView("/telas/SolicitacoesAnalisadas.fxml", (SolicitacoesAnalistadasListController controller) -> {
			controller.setSolicitacoesAnalisadasService(new SolicitacoesAnalisadasService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemFuncionarioAction() {
		loadView("/telas/CadastroFuncionario.fxml", (FuncionarioListController controller) -> {
			controller.setFuncionarioService(new FuncionarioService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemUsuarioAction() {
		loadView("/telas/CadastroUsuario.fxml", (UsuarioListController controller) -> {
			controller.setUsuarioService(new UsuarioService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemDepartamentoAction() {
		loadView("/telas/CadastroDepartamento.fxml", (DepartamentoListController controller) -> {
			controller.setDepartamentoService(new DepartamentoService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemCargoAction() {
		loadView("/telas/CadastroCargo.fxml", (CargoListController controller) -> {
			controller.setCargoService(new CargoService());
			controller.updateTableView();
		});
	}
	
	@FXML
	private TableView<SolicitacoesEnviadas> tableViewMainView;
	
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
	}
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainManu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainManu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			T controller = loader.getController();
			initializingAction.accept(controller);
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
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
		tableViewMainView.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
			
		}
		List<SolicitacoesEnviadas> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewMainView.setItems(obsList);
	}
		
}
