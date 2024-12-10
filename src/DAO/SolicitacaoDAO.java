package DAO;

import model.entities.Solicitacao;
import model.entities.StatusSolicitacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolicitacaoDAO {
    private Connection connection;

    public SolicitacaoDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserirSolicitacao(Solicitacao solicitacao) throws SQLException {
        String sql = "INSERT INTO solicitacoes (fornecedor, descricao, data_criacao, data_pagamento, forma_pagamento, valor_total, id_usuario, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, solicitacao.getFornecedor());
            pstmt.setString(2, solicitacao.getDescricao());
            pstmt.setTimestamp(3, solicitacao.getDataCriacao());
            pstmt.setDate(4, solicitacao.getDataPagamento());
            pstmt.setString(5, solicitacao.getFormaPagamento());
            pstmt.setDouble(6, solicitacao.getValorTotal());
            pstmt.setInt(7, solicitacao.getIdUsuario());
            pstmt.setString(8, solicitacao.getStatus().name());

            pstmt.executeUpdate();
        }
    }

    public void atualizarSolicitacao(Solicitacao solicitacao) throws SQLException {
        String sql = "UPDATE solicitacoes SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, solicitacao.getStatus().toString());
            stmt.setInt(2, solicitacao.getId());
            stmt.executeUpdate();
        }
    }

    public int contarSolicitacoesPorStatus(StatusSolicitacao status) throws SQLException {
        String sql = "SELECT COUNT(*) FROM solicitacoes WHERE status = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status.name());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Solicitacao> getSolicitacoesAnalisadas(int idTipoUsuario, int idUsuario) {
        List<Solicitacao> solicitacoes = new ArrayList<>();
        String sql;

        if (idTipoUsuario == 4) {
            sql = "SELECT " +
                    "s.*, u.login " +
                    "FROM " +
                    "solicitacoes s " +
                    "INNER JOIN usuarios u ON s.id_usuario = u.id " +
                    "WHERE status IN ('APROVADA', 'REPROVADA') " +
                    "AND id_usuario = ?";
        } else {
            sql = "SELECT " +
                    "s.*, u.login " +
                    "FROM " +
                    "solicitacoes s " +
                    "INNER JOIN usuarios u ON s.id_usuario = u.id " +
                    "WHERE s.status IN ('APROVADA', 'REPROVADA')";
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            if (idTipoUsuario == 4) {
                stmt.setInt(1, idUsuario);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Solicitacao solicitacao = new Solicitacao(
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
            e.printStackTrace();
        }
        return solicitacoes;
    }

    public List<Solicitacao> getSolicitacoesPorStatus(StatusSolicitacao status, int idTipoUsuario, int idUser) {
        List<Solicitacao> solicitacoes = new ArrayList<>();
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
                Solicitacao solicitacao = new Solicitacao(
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
            e.printStackTrace();
        }
        return solicitacoes;
    }
}
