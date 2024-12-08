package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Cargo;

public class CargoService {
	
	public List<Cargo> findAll() {
		List<Cargo> list = new ArrayList<>();
		list.add(new Cargo(1, "Analista Jr"));
		list.add(new Cargo(2, "Consultor Externo"));
		return list;
	}
}
