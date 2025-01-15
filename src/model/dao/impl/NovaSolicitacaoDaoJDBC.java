package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import db.DbException;
import model.dao.NovaSolicitacaoDao;
import model.entities.NovaSolicitacao;

public class NovaSolicitacaoDaoJDBC implements NovaSolicitacaoDao {
	
	private Connection connection;
	
	public NovaSolicitacaoDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(NovaSolicitacao obj, int idUsuario) {
		String sql = "INSERT INTO solicitacoes (fornecedor, descricao, data_criacao, data_pagamento, forma_pagamento, valor_total, status, id_usuario) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        	
        	Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        	
            pstmt.setString(1, obj.getFornecedor());
            pstmt.setString(2, obj.getDescricao());
            pstmt.setTimestamp(3, currentTimestamp);
            pstmt.setDate(4, java.sql.Date.valueOf(obj.getDataPagamento()));
            pstmt.setString(5, obj.getFormaPagamento());
            pstmt.setDouble(6, obj.getValorTotal());
            pstmt.setString(7, "PENDENTE");
            pstmt.setInt(8, idUsuario);

            pstmt.executeUpdate();
        } catch (SQLException e) {
        	throw new DbException(e.getMessage());
        }
	}
	
	@Override
	public void update(NovaSolicitacao obj) {
		// TODO Auto-generated method stub
	}

}
