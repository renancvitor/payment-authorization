package model.dao.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DbException;
import model.dao.AlterarSenhaDao;

public class AlterarSenhaDaoJDBC implements AlterarSenhaDao {

	private Connection connection;
	
	public AlterarSenhaDaoJDBC(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void insert(String username, String novaSenha) {
		// TODO Auto-generated method stub
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
