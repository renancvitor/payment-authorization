package model.dao.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import gui.util.Alerts;
import javafx.scene.control.Alert.AlertType;
import model.dao.UsuarioDao;
import model.entities.UserType;
import model.entities.Usuario;
import model.services.PermissaoService;

public class UsuarioDaoJDBC implements UsuarioDao {
	
	private Connection connection;
	private PermissaoService permissaoService;
	
	public UsuarioDaoJDBC(Connection connection, PermissaoService permissaoService) {
		this.connection = connection;
		this.permissaoService = permissaoService;
	}

	@Override
	public void insert(Usuario obj) {
		try {
			Integer idPessoa = getIdPessoaByCpf(obj.getCpf());
			
	        if (idPessoa == null) {
	            throw new NullPointerException("CPF não cadastrado na tabela Pessoa!");
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
		PreparedStatement stmt = null;
        ResultSet rs = null;
		
		List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id, login, cpf FROM usuarios";

        try {        	
        	stmt =  connection.prepareStatement(sql);
        	rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setLogin(rs.getString("login"));
                usuario.setCpf(rs.getString("cpf"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
    		DB.closeStatement(stmt);
    		DB.closeResultSet(rs);
    	}
        return usuarios;
	}
	
	@Override
	public Integer getIdPessoaByCpf(String cpf) {
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
        return null;
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
	
	@Override
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
	
	@Override
	public void atualizarSenhaParaHash(String username, String senhaHash) {
        String query = "UPDATE usuarios SET senha = ? WHERE login = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, senhaHash);
            stmt.setString(2, username);
            stmt.executeUpdate();
            Alerts.showAlert("Sucesso", null, "Senha migrada para hash com sucesso para o usuário: " + username, AlertType.INFORMATION);
        } catch (SQLException e) {
        	throw new DbException(e.getMessage());
        }
    }
	
	@Override
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
	
	@Override
	public Usuario getUsuarioByLogin(String login, String senha) throws SQLException, NoSuchAlgorithmException {
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
        }
        return null;
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
	
	@Override
	public List<String> getPermissoesByUsuarioId(int usuarioId) throws SQLException {
        String sql = "SELECT id_tipo_usuario FROM usuarios WHERE id = ?";
        int tipoUsuarioId = -1;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tipoUsuarioId = rs.getInt("id_tipo_usuario");
                }
            }
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
	public boolean update(String username, String novaSenha)  {
            String senhaHash = null;
			senhaHash = hashSenha(novaSenha);
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

}
