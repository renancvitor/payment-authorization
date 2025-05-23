package model.dao;

import java.util.List;

import model.entities.Departamento;

public interface DepartamentoDao {

	void insert(Departamento obj);
	void update(Departamento obj);
	void debeteById(Departamento obj);
	Departamento findById(Integer id);
	List<Departamento> findAll();
}