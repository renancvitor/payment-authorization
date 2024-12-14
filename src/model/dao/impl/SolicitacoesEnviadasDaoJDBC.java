package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DbException;
import model.dao.SolicitacoesEnviadasDao;
import model.entities.SolicitacoesEnviadas;
import model.entities.StatusSolicitacao;

public class SolicitacoesEnviadasDaoJDBC implements SolicitacoesEnviadasDao {
	
	private Connection connection;
	
	public SolicitacoesEnviadasDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public int countAll(StatusSolicitacao status) {
		String sql = "SELECT COUNT(*) FROM solicitacoes WHERE status = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status.name());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
        	throw new DbException(e.getMessage());
        }
        return 0;
	}

	@Override
	public List<SolicitacoesEnviadas> findAll(int idTipoUsuario, int idUser) {
		List<SolicitacoesEnviadas> solicitacoes = new ArrayList<>();
        String sql;

        if (idTipoUsuario == 4) {
            sql = "SELECT " +
                    "s.*, u.login " +
                    "FROM " +
                    "solicitacoes s " +
                    "INNER JOIN usuarios u ON s.id_usuario = u.id " +
                    "WHERE status = 'PENDENTE' " +
                    "AND id_usuario = ?";
        } else {
            sql = "SELECT " +
                    "s.*, u.login " +
                    "FROM " +
                    "solicitacoes s " +
                    "INNER JOIN usuarios u ON s.id_usuario = u.id " +
                    "WHERE s.status = 'PENDENTE'";
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            if (idTipoUsuario == 4) {
                stmt.setInt(1, idUser);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	SolicitacoesEnviadas solicitacao = new SolicitacoesEnviadas(
                        rs.getInt("id"),
                        rs.getString("fornecedor"),
                        rs.getString("descricao"),
                        rs.getTimestamp("data_criacao"),
                        rs.getDate("data_pagamento"),
                        rs.getString("forma_pagamento"),
                        rs.getDouble("valor_total"),
                        rs.getInt("id_usuario"),
                        StatusSolicitacao.valueOf(rs.getString("status")),
                        rs.getString("login")
                );
                solicitacoes.add(solicitacao);
            }
        } catch (SQLException e) {
        	throw new DbException(e.getMessage());
        }
        return solicitacoes;
	}

	@Override
	public void update(SolicitacoesEnviadas obj) {
		String sql = "UPDATE solicitacoes SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, obj.getStatus().toString());
            stmt.setInt(2, obj.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
        	throw new DbException(e.getMessage());
        }		
	}
	
}
