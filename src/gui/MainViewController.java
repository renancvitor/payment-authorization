package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.entities.Usuario;
import model.services.CargoService;
import model.services.DepartamentoService;
import model.services.FuncionarioService;
import model.services.SolicitacoesAnalisadasService;
import model.services.SolicitacoesEnviadasService;
import model.services.UserLoginService;
import model.services.UsuarioService;

public class MainViewController implements Initializable {
	
	private Usuario usuarioAtual;
	
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
	private Button buttonLogout;
	
	@FXML
	public void onMenuItemSolicitacoesEnviadasAction() {
		this.usuarioAtual = UserLoginService.getUsuarioLogado();
		loadView("/gui/SolicitacoesEnviadas.fxml", (SolicitacoesEnviadasListController controller) -> {
			controller.setSolicitacoesEnviadasService(new SolicitacoesEnviadasService());
			controller.updateTableView(usuarioAtual);
		});
	}
	
	@FXML
	public void onMenuItemSolicitacoesAnalisadasAction() {
		this.usuarioAtual = UserLoginService.getUsuarioLogado();
		loadView("/gui/SolicitacoesAnalisadas.fxml", (SolicitacoesAnalisadasListController controller) -> {
			controller.setSolicitacoesAnalisadasService(new SolicitacoesAnalisadasService());
			controller.updateTableView(usuarioAtual);
		});
	}
	
	@FXML
	public void onMenuItemFuncionarioAction() {
		this.usuarioAtual = UserLoginService.getUsuarioLogado();
		loadView("/gui/CadastroFuncionario.fxml", (FuncionarioListController controller) -> {
			controller.setFuncionarioService(new FuncionarioService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemUsuarioAction() {
		this.usuarioAtual = UserLoginService.getUsuarioLogado();
		loadView("/gui/CadastroUsuario.fxml", (UsuarioListController controller) -> {
			controller.setUsuarioService(new UsuarioService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemDepartamentoAction() {
		this.usuarioAtual = UserLoginService.getUsuarioLogado();
		loadView("/gui/CadastroDepartamento.fxml", (DepartamentoListController controller) -> {
			controller.setDepartamentoService(new DepartamentoService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemCargoAction() {
		this.usuarioAtual = UserLoginService.getUsuarioLogado();
		loadView("/gui/CadastroCargo.fxml", (CargoListController controller) -> {
			controller.setCargoService(new CargoService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemLogoutAction() {
	    UserLoginService.logout();
	    Alerts.showAlert("Logout", "Você foi desconectado", "Você foi desconectado do sistema.", AlertType.INFORMATION);

	    // loadLoginView();
	}
		
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		this.usuarioAtual = UserLoginService.getUsuarioLogado();
		
		if (usuarioAtual.getUserType().getId() != 1) {
	        menuItemFuncionario.setVisible(false);
	        menuItemUsuario.setVisible(false);
	        menuItemDepartamento.setVisible(false);
	        menuItemCargo.setVisible(false);
	    }
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
