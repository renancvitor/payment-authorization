package model.dao;

import java.util.List;

import model.entities.UserType;
import model.entities.Usuario;

public interface UsuarioDao {
	
	void insert(Usuario obj);
	void update(Usuario obj);
	void deleteById(Integer id);
	Usuario findById(Integer id);
	List<Usuario> findAll();
	int getIdPessoaByCpf(String cpf);
	boolean isUsuarioExistente(Integer idPessoa);
	int getIdFromUserType(UserType userType);
	void consultarPermissoesUsuario(Usuario usuario);
	boolean verificarSenhaPorUsuario(String username, String senhaAtual);
	void atualizarSenhaParaHash(String username, String senhaHash);
	String hashSenha(String senha);
}
