package model.dao;

import java.util.List;

import model.entities.UserType;

public interface UserTypeDao {
	
	List<UserType> loadUserTypes();
}
