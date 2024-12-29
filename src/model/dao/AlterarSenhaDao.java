package model.dao;

public interface AlterarSenhaDao {
	
	boolean update(String username, String novaSenha);
	void insert(String username, String novaSenha); 
}
