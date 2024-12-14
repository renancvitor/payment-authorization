package telas;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import model.services.NovaSolicitacaoService;

public class NovaSolicitacaoListController implements Initializable {
	
	private NovaSolicitacaoService service;
	
	public void setNovaSolicitacaoService(NovaSolicitacaoService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}

}
