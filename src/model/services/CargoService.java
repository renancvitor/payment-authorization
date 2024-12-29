package model.services;

import java.util.List;

import model.dao.CargoDao;
import model.dao.DaoFactory;
import model.entities.Cargo;

public class CargoService {
	
	private CargoDao dao = DaoFactory.createCargoDao();
	
	public List<Cargo> findAll() {
		return dao.findAll();
	}
	
	public void saveOrUpdate(Cargo obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		} else {
			dao.update(obj);
		}
	}
}
