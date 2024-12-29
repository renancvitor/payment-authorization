package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.UserTypeDao;
import model.entities.UserType;

public class UserTypeService {
	
	private UserTypeDao dao = DaoFactory.createUserTypeDao();
	
	public List<UserType> loadUserTypes() {
		return dao.loadUserTypes();
	}

}
