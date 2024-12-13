package model.dao;

import db.DB;
import model.dao.impl.CargoDaoJDBC;
import model.dao.impl.DepartamentoDaoJDBC;

public class DaoFactory {

	public static DepartamentoDao createDepartamentoDao() {
		return new DepartamentoDaoJDBC(DB.getConnection());
	}
	
	public static CargoDao createCargoDao() {
		return new CargoDaoJDBC(DB.getConnection());
	}
}
