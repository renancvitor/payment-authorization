package model.dao.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DbException;
import model.dao.LoginDao;
import model.entities.UserType;
import model.entities.Usuario;
import model.services.PermissaoService;

public class LoginDaoJDBC implements LoginDao {
	
	private Connection connection;
	private final PermissaoService permissaoService;
	
	public LoginDaoJDBC(Connection connection) {
		this.connection = connection;
		this.permissaoService = new PermissaoService();
	}

	@Override
	public Usuario selectUser(String login, String senha) throws NoSuchAlgorithmException {
		String sql = "SELECT id, login, senha, cpf, id_tipo_usuario FROM usuarios WHERE login = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String retrievedLogin = rs.getString("login");
                    String retrievedSenha = rs.getString("senha");
                    String cpf = rs.getString("cpf");
                    int idTipoUsuario = rs.getInt("id_tipo_usuario");

                    String senhaHash = hashSenha(senha);

                    if (senhaHash.equals(retrievedSenha)) {
                        UserType userType = getUserTypeFromId(idTipoUsuario);
                        List<String> permissoes = getPermissoesByUsuarioId(id);

                        return new Usuario(id, retrievedLogin, retrievedSenha, permissoes, cpf, idTipoUsuario, userType);
                    }
                }
            }
        } catch (SQLException e) {
        	throw new DbException(e.getMessage());
        }
        return null;
	}

	@Override
	public List<String> getPermissoesByUsuarioId(int usuarioId) {
		String sql = "SELECT id_tipo_usuario FROM usuarios WHERE id = ?";
        int tipoUsuarioId = -1;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tipoUsuarioId = rs.getInt("id_tipo_usuario");
                }
            }
        } catch (SQLException e) {
        	throw new DbException(e.getMessage());
        }

        UserType userType = switch (tipoUsuarioId) {
            case 1 -> UserType.ADMIN;
            case 2 -> UserType.GESTOR;
            case 3 -> UserType.VISUALIZADOR;
            case 4 -> UserType.COMUM;
            default -> null;
        };

        return (userType != null) ? permissaoService.getPermissoesByUserType(userType) : List.of();
	}

	@Override
	public UserType getUserTypeFromId(int idTipoUsuario) {
		return switch (idTipoUsuario) {
        case 1 -> UserType.ADMIN;
        case 2 -> UserType.GESTOR;
        case 3 -> UserType.VISUALIZADOR;
        case 4 -> UserType.COMUM;
        default -> throw new IllegalArgumentException("Tipo de usuário inválido: " + idTipoUsuario);
    };
	}
	
	private String hashSenha(String senha) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(senha.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}