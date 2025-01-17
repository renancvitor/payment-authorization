package model.dao;

import java.sql.SQLException;

public interface AlterarSenhaDao {
	
	boolean update(String username, String novaSenha);
	boolean verificarSenhaPorUsuario(String username, String senhaAtual) throws SQLException;
}
