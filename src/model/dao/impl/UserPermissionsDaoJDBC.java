package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DbException;
import model.dao.UserPermissionsDao;

public class UserPermissionsDaoJDBC implements UserPermissionsDao {
	
	private Connection connection;
	
	public UserPermissionsDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
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

	@Override
	public List<String> getPermissoesByTipo(String nomeTipo) {
		int idTipoUsuario = 0;
		try {
			idTipoUsuario = getIdTipoUsuario(nomeTipo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
        } catch (SQLException e) {
        	throw new DbException(e.getMessage());
        }
        return permissoes;
	}

	@Override
	public List<String> getTiposUsuarios() {
		List<String> tiposUsuarios = new ArrayList<>();
        String query = "SELECT tipo_usuario FROM tipos_usuarios";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                tiposUsuarios.add(rs.getString("tipo_usuario"));
            }
        } catch (SQLException e) {
        	throw new DbException(e.getMessage());
        }
        return tiposUsuarios;
	}

	@Override
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
