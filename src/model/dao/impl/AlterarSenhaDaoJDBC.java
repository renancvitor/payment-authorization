package model.dao.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DbException;
import model.dao.AlterarSenhaDao;
import model.services.PermissaoService;

public class AlterarSenhaDaoJDBC implements AlterarSenhaDao {

	private Connection connection;
	private PermissaoService permissaoService;
	UsuarioDaoJDBC usuarioDao = new UsuarioDaoJDBC(connection, permissaoService);
	
	public AlterarSenhaDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public boolean update(String username, String novaSenha)  {
            String senhaHash = null;
			try {
				senhaHash = hashSenha(novaSenha);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
            String query = "UPDATE usuarios SET senha = ? WHERE login = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, senhaHash);
                stmt.setString(2, username);
                int rowsUpdated = stmt.executeUpdate();
                return rowsUpdated > 0;
            } catch (SQLException e) {
            	throw new DbException(e.getMessage());
            }
	}
	
	@Override
	public boolean verificarSenhaPorUsuario(String username, String senhaAtual) throws SQLException {
		try {
            String senhaHash = hashSenha(senhaAtual);

            String query = "SELECT senha FROM usuarios WHERE login = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, username);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String senhaArmazenada = rs.getString("senha");

                        if (senhaArmazenada.equals(senhaAtual)) {
                        	usuarioDao.atualizarSenhaParaHash(username, senhaHash);
                            return true;
                        } else if (senhaArmazenada.equals(senhaHash)) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
            return false;
        } catch (NoSuchAlgorithmException e) {
            throw new SQLException("Erro ao gerar hash da senha.", e);
        }
    }
	
	private String hashSenha(String senha) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(senha.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }	
}
