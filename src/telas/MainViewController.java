package telas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.CargoService;
import model.services.DepartamentoService;
import model.services.FuncionarioService;
import model.services.SolicitacoesAnalisadasService;
import model.services.SolicitacoesEnviadasService;
import model.services.UsuarioService;
import telas.util.Alerts;

public class MainViewController implements Initializable {
	
	@FXML
	private MenuItem menuItemFuncionario;
	
	@FXML
	private MenuItem menuItemUsuario;
	
	@FXML
	private MenuItem menuItemDepartamento;
	
	@FXML
	private MenuItem menuItemCargo;
	
	@FXML
	private MenuItem menuItemSolicitacoesEnviadas;
	
	@FXML
	private MenuItem menuItemSolicitacoesAnalisadas;
	
	@FXML
	public void onMenuItemSolicitacoesEnviadasAction() {
		loadView("/telas/SolicitacoesEnviadas.fxml", (SolicitacoesEnviadasListController controller) -> {
			controller.setSolicitacoesEnviadasService(new SolicitacoesEnviadasService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemSolicitacoesAnalisadasAction() {
		loadView("/telas/SolicitacoesAnalisadas.fxml", (SolicitacoesAnalisadasListController controller) -> {
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
			
}
