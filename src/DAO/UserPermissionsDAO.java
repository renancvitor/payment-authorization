package DAO;

import model.entities.UserType;
import model.entities.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserPermissionsDAO {
    private final Connection connection;

    public UserPermissionsDAO(Connection connection) {
        this.connection = connection;
    }

    public int getIdTipoUsuario(String nomeTipo) throws SQLException {
        String sql = "SELECT id FROM tipos_usuarios WHERE tipo_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nomeTipo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        throw new SQLException("Tipo de usuário não encontrado: " + nomeTipo);
    }

    public List<String> getPermissoesByTipo(String nomeTipo) throws SQLException {
        int idTipoUsuario = getIdTipoUsuario(nomeTipo);
        List<String> permissoes = new ArrayList<>();

        String sql = "SELECT p.nome_permissao FROM permissoes p "
                + "JOIN tipos_usuarios_permissoes tup ON p.id = tup.id_permissao "
                + "JOIN tipos_usuarios tu ON tu.id = tup.id_tipo_usuario "
                + "WHERE tu.tipo_usuario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTipoUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    permissoes.add(rs.getString("nome"));
                }
            }
        }
        return permissoes;
    }

    public List<String> getTiposUsuarios() throws SQLException {
        List<String> tiposUsuarios = new ArrayList<>();
        String query = "SELECT tipo_usuario FROM tipos_usuarios";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                tiposUsuarios.add(rs.getString("tipo_usuario"));
            }
        }
        return tiposUsuarios;
    }

    public int getIdTipoUsuarioByNome(String tipoUsuarioNome) throws SQLException {
        String sql = "SELECT id FROM tipos_usuarios WHERE tipo_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tipoUsuarioNome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        throw new SQLException("Tipo de usuário não encontrado");
    }
}
