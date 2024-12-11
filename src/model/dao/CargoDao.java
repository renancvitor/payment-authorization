package model.dao;

import java.util.List;

import model.entities.Cargo;

public interface CargoDao {

	void insert(Cargo obj);
	void update(Cargo obj);
	void debeteById(Cargo obj);
	Cargo findById(Integer id);
	List<Cargo> findAll();
}
