package model.services;

import java.sql.SQLException;
import java.util.List;

import javafx.scene.control.Alert;
import model.dao.SolicitacoesAnalisadasDao;
import model.dao.SolicitacoesEnviadasDao;
import model.entities.SolicitacoesAnalisadas;
import model.entities.SolicitacoesEnviadas;
import model.entities.StatusSolicitacao;
import model.entities.UserType;
import model.entities.Usuario;

public class SolicitacaoService {
    private SolicitacoesAnalisadasDao solicitacaoDAO;
    private PermissaoService permissaoService;
    private SolicitacoesEnviadasDao solicitacoesEnviadasDao;

    public SolicitacaoService(SolicitacoesAnalisadasDao solicitacaoDAO, PermissaoService permissaoService) {
        this.solicitacaoDAO = solicitacaoDAO;
        this.permissaoService = permissaoService;
    }

    public List<SolicitacoesAnalisadas> getSolicitacoesVisiveisParaUsuario(Usuario usuario) throws SQLException {
        if (permissaoService.visualizarTodasSolicitacoes(usuario)) {
            return solicitacaoDAO.select(StatusSolicitacao.PENDENTE, usuario.getUserType().getId(), usuario.getId());
        }

        if (usuario.getUserType() == UserType.COMUM) {
            return solicitacaoDAO.select(StatusSolicitacao.PENDENTE, usuario.getUserType().getId(), usuario.getId());
        }
        return solicitacaoDAO.select(StatusSolicitacao.PENDENTE, usuario.getUserType().getId(), usuario.getId());
    }

    public List<SolicitacoesAnalisadas> getSolicitacoesPorStatus(StatusSolicitacao status, int idTipoUsuario, int idUser) throws SQLException {
        return solicitacaoDAO.select(status, idTipoUsuario, idUser);
    }

    public void atualizarStatusSolicitacao(Usuario usuario, PermissaoService permissaoService, SolicitacoesEnviadas solicitacao, StatusSolicitacao novoStatus) throws SQLException {
        if (permissaoService.aprovarReprovarSolicitacoes(usuario)) {
            solicitacao.setStatus(novoStatus);
            solicitacoesEnviadasDao.update(solicitacao);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Acesso negado: Usuário não tem permissão para aprovar ou reprovar solicitações.");
            alert.show();
        }
    }
}
