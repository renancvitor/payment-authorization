package model.dao.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.UsuarioDao;
import model.entities.UserType;
import model.entities.Usuario;

public class UsuarioDaoJDBC implements UsuarioDao {
	
	private Connection connection;
	
	public UsuarioDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Usuario obj) {
		try {
			int idPessoa = getIdPessoaByCpf(obj.getCpf());
	        if (idPessoa == -1) {
	            throw new IllegalArgumentException("CPF não cadastrado na tabela Pessoa!");
	        }

	        if (isUsuarioExistente(idPessoa)) {
	            throw new IllegalArgumentException("Esta pessoa já tem um usuário vinculado!");
	        }

	        String sql = "INSERT INTO usuarios (login, senha, idpessoa, id_tipo_usuario, cpf) VALUES (?, ?, ?, ?, ?)";
	        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
	            stmt.setString(1, obj.getLogin());
	            stmt.setString(2, obj.getSenha());
	            stmt.setInt(3, idPessoa);
	            stmt.setInt(4, getIdFromUserType(obj.getUserType()));
	            stmt.setString(5, obj.getCpf());

	            stmt.executeUpdate();

	            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    int userId = generatedKeys.getInt(1);
	                    obj.setId(userId);
	                    consultarPermissoesUsuario(obj);
	                }
	            } finally {
	        		DB.closeStatement(stmt);
	        	}
	        }
		} catch (SQLException e) {
        	throw new DbException(e.getMessage());
        }	
	}

	@Override
	public void update(Usuario obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Usuario findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getIdPessoaByCpf(String cpf) {
        String sql = "SELECT idpessoa FROM pessoa WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idpessoa");
                }
            }
        } catch (SQLException e) {
        	throw new DbException(e.getMessage());
        }
        return -1;
    }
		
	@Override
	public int getIdFromUserType(UserType userType) {
        return switch (userType) {
            case ADMIN -> 1;
            case GESTOR -> 2;
            case VISUALIZADOR -> 3;
            case COMUM -> 4;
        };
    }
	
	@Override
	public void consultarPermissoesUsuario(Usuario usuario) {
        String sql = "SELECT p.nome_permissao FROM tipos_usuarios_permissoes tp "
                + "JOIN permissoes p ON tp.id_permissao = p.id "
                + "WHERE tp.id_tipo_usuario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, usuario.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String permissao = rs.getString("nome_permissao");
                    usuario.adicionarPermissao(permissao);
                }
            } 
        } catch (SQLException e) {
        	throw new DbException(e.getMessage());
        }
    }
	
	public boolean verificarSenhaPorUsuario(String username, String senhaAtual) {
        try {
            String senhaHash = hashSenha(senhaAtual);

            String query = "SELECT senha FROM usuarios WHERE login = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, username);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String senhaArmazenada = rs.getString("senha");

                        if (senhaArmazenada.equals(senhaAtual)) {
                            atualizarSenhaParaHash(username, senhaHash);
                            return true;
                        } else if (senhaArmazenada.equals(senhaHash)) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        } catch (SQLException e) {
        	throw new DbException(e.getMessage());
        }
        return false;
    }
	
	public void atualizarSenhaParaHash(String username, String senhaHash) {
        String query = "UPDATE usuarios SET senha = ? WHERE login = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, senhaHash);
            stmt.setString(2, username);
            stmt.executeUpdate();
            System.out.println("Senha migrada para hash com sucesso para o usuário: " + username);
        } catch (SQLException e) {
        	throw new DbException(e.getMessage());
        }
    }
	
	public String hashSenha(String senha) {
        MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        byte[] hashBytes = md.digest(senha.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

	@Override
	public boolean isUsuarioExistente(Integer idPessoa) {
		String sql = "SELECT COUNT(*) FROM usuarios WHERE idpessoa = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPessoa);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
        	throw new DbException(e.getMessage());
        }
		return false;
	}

}
