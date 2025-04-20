package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.CargoDao;
import model.entities.Cargo;

public class CargoDaoJDBC implements CargoDao {

	private Connection connection;
	
	public CargoDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Cargo obj) {
		if (obj.getNome() == null || obj.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome do cargo não pode ser vazio.");
        }
        String sql = "INSERT INTO cargo (nome) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, obj.getNome());
            stmt.executeUpdate();
        } catch (SQLException e) {
        	throw new DbException(e.getMessage());
        }
		
	}

	@Override
	public void update(Cargo obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debeteById(Cargo obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Cargo findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cargo> findAll() {
		PreparedStatement stmt = null;
        ResultSet rs = null;
		
		List<Cargo> cargos = new ArrayList<>();
        String sql = "SELECT * FROM cargo";

        try {        	
        	stmt =  connection.prepareStatement(sql);
        	rs = stmt.executeQuery();

            while (rs.next()) {
                Cargo cargo = new Cargo();
                cargo.setId(rs.getInt("id"));
                cargo.setNome(rs.getString("nome"));
                cargos.add(cargo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
    		DB.closeStatement(stmt);
    		DB.closeResultSet(rs);
    	}
        return cargos;
	}
}