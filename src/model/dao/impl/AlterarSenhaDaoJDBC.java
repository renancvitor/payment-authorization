package model.dao.impl;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DbException;
import model.dao.AlterarSenhaDao;
import model.entities.Usuario;
import model.services.PermissaoService;

public class AlterarSenhaDaoJDBC implements AlterarSenhaDao {

//	private Connection connection;
//	private PermissaoService permissaoService;
//	UsuarioDaoJDBC usuarioDao = new UsuarioDaoJDBC(connection, permissaoService);
//
//	public AlterarSenhaDaoJDBC(Connection connection) {
//		this.connection = connection;
//	}

    private Connection connection;
    private PermissaoService permissaoService;
    private UsuarioDaoJDBC usuarioDao;

    public AlterarSenhaDaoJDBC(Connection connection) {
        this.connection = connection;
        this.permissaoService = new PermissaoService();
        this.usuarioDao = new UsuarioDaoJDBC(connection, permissaoService);
    }

	@Override
	public boolean update(String username, String novaSenha)  {
        try {
            String senhaHash = Usuario.gerarHash(novaSenha);

            String query = "UPDATE usuarios SET senha = ? WHERE login = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, senhaHash);
                stmt.setString(2, username);
                int rowsUpdated = stmt.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            throw new DbException("Erro ao atualizar senha: " + e.getMessage());
        }
	}

    public boolean verificarSenhaPorUsuario(String username, String senhaAtual) throws SQLException {
        try {
            String query = "SELECT senha FROM usuarios WHERE login = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, username);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String senhaArmazenada = rs.getString("senha");

                        if (senhaArmazenada.equals(senhaAtual)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (SQLException e) {
            throw new SQLException("Erro ao gerar hash da senha.", e);
        }
    }

//	private String hashSenha(String senha) throws NoSuchAlgorithmException {
//        MessageDigest md = MessageDigest.getInstance("SHA-256");
//        //byte[] hashBytes = md.digest(senha.getBytes(StandardCharsets.UTF_8));
//        byte[] hashBytes = md.digest(senha.getBytes(StandardCharsets.UTF_8));
//        StringBuilder hexString = new StringBuilder();
//        for (byte b : hashBytes) {
//            hexString.append(String.format("%02x", b));
//        }
//        return hexString.toString();
//    }
}
