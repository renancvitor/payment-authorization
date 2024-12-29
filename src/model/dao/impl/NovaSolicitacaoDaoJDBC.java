package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DbException;
import model.dao.NovaSolicitacaoDao;
import model.entities.NovaSolicitacao;

public class NovaSolicitacaoDaoJDBC implements NovaSolicitacaoDao {
	
	private Connection connection;
	
	public NovaSolicitacaoDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(NovaSolicitacao obj) {
		String sql = "INSERT INTO solicitacoes (fornecedor, descricao, data_criacao, data_pagamento, forma_pagamento, valor_total, id_usuario, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, obj.getFornecedor());
            pstmt.setString(2, obj.getDescricao());
            pstmt.setTimestamp(3, obj.getDataCriacao());
            pstmt.setDate(4, obj.getDataPagamento());
            pstmt.setString(5, obj.getFormaPagamento());
            pstmt.setDouble(6, obj.getValorTotal());
            pstmt.setInt(7, obj.getIdUsuario());
            pstmt.setString(8, obj.getStatus().name());

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
