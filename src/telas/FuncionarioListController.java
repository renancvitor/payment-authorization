package telas;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.entities.Cargo;
import model.entities.Departamento;
import model.entities.Pessoa;

public class FuncionarioListController implements Initializable {
	
	@FXML
	private TableView<Pessoa> tableViewFuncionario;
	
	@FXML
	private TableColumn<Pessoa, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Pessoa, String> tableColumnNome;
	
	@FXML
	private TableColumn<Pessoa, LocalDate> tableColumnDataNascimento;
	
	@FXML
	private TableColumn<Pessoa, Departamento> tableColumnDepartamento;
	
	@FXML
	private TableColumn<Pessoa, Cargo> tableColumnCargo;
	
	@FXML
	private TableColumn<Pessoa, String> tableColumnCpf;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}

}
