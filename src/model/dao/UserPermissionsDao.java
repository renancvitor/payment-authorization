package model.dao;

import java.sql.SQLException;
import java.util.List;

public interface UserPermissionsDao {

	int getIdTipoUsuario(String nomeTipo) throws SQLException;
	List<String> getPermissoesByTipo(String nomeTipo);
	List<String> getTiposUsuarios();
	int getIdTipoUsuarioByNome(String tipoUsuarioNome) throws SQLException;
}
