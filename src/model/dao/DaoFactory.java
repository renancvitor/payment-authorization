package model.dao;

import db.DB;
import model.dao.impl.AlterarSenhaDaoJDBC;
import model.dao.impl.CargoDaoJDBC;
import model.dao.impl.DepartamentoDaoJDBC;
import model.dao.impl.LoginDaoJDBC;
import model.dao.impl.NovaSolicitacaoDaoJDBC;
import model.dao.impl.PessoaDaoJDBC;
import model.dao.impl.SolicitacoesAnalisadasDaoJDBC;
import model.dao.impl.SolicitacoesEnviadasDaoJDBC;
import model.dao.impl.UserPermissionsDaoJDBC;
import model.dao.impl.UserTypeDaoJDBC;
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
	
	public static AlterarSenhaDao createAlterarSenhaDao() {
		return new AlterarSenhaDaoJDBC(DB.getConnection());
	}
	
	public static LoginDao createLoginDao() {
		return new LoginDaoJDBC(DB.getConnection());
	}
	
	public static NovaSolicitacaoDao createNovaSolicitacaoDao() {
		return new NovaSolicitacaoDaoJDBC(DB.getConnection());
	}
	
	public static SolicitacoesAnalisadasDao createSolicitacoesAnalisadasDao() {
		return new SolicitacoesAnalisadasDaoJDBC(DB.getConnection());
	}
	
	public static SolicitacoesEnviadasDao createSolicitacoesEnviadasDao() {
		return new SolicitacoesEnviadasDaoJDBC(DB.getConnection());
	}
	
	public static UserTypeDao createUserTypeDao() {
		return new UserTypeDaoJDBC(DB.getConnection());
	}
	
	public static UserPermissionsDao createUserPermissiosnDao() {
		return new UserPermissionsDaoJDBC(DB.getConnection());
	}
}
