package model.dao;

import db.DB;
import model.dao.impl.CargoDaoJDBC;
import model.dao.impl.DepartamentoDaoJDBC;
import model.dao.impl.PessoaDaoJDBC;
import model.dao.impl.UsuarioDaoJDBC;

public class DaoFactory {

	public static DepartamentoDao createDepartamentoDao() {
		return new DepartamentoDaoJDBC(DB.getConnection());
	}
	
	public static CargoDao createCargoDao() {
		return new CargoDaoJDBC(DB.getConnection());
	}
	
	public static UsuarioDao createUsuarioDao() {
		return new UsuarioDaoJDBC(DB.getConnection());
	}
	
	public static PessoaDao createPessoaDao() {
		return new PessoaDaoJDBC(DB.getConnection());
	}
}
