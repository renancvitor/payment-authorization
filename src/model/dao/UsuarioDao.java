package model.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import model.entities.UserType;
import model.entities.Usuario;

public interface UsuarioDao {
	
	void insert(Usuario obj);
	void update(Usuario obj);
	void deleteById(Integer id);
	Usuario findById(Integer id);
	List<Usuario> findAll();
	Integer getIdPessoaByCpf(String cpf);
	boolean isUsuarioExistente(Integer idPessoa);
	int getIdFromUserType(UserType userType);
	void consultarPermissoesUsuario(Usuario usuario);
	boolean verificarSenhaPorUsuario(String username, String senhaAtual);
	void atualizarSenhaParaHash(String username, String senhaHash);
	//String hashSenha(String senha);
	Usuario getUsuarioByLogin(String login, String senha) throws SQLException, NoSuchAlgorithmException;
	UserType getUserTypeFromId(int idTipoUsuario);
	List<String> getPermissoesByUsuarioId(int usuarioId) throws SQLException;
	boolean update(String username, String novaSenha);
}