package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartamentoDao;
import model.entities.Departamento;

public class DepartamentoDaoJDBC implements DepartamentoDao {
	
	private Connection connection;
	
	public DepartamentoDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Departamento obj) {
		if (obj.getNome() == null || obj.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome do departamento não pode ser vazio.");
        }
        String sql = "INSERT INTO departamento (nome) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, obj.getNome());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
		
	}

	@Override
	public void update(Departamento obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debeteById(Departamento obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Departamento findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Departamento> findAll() {
		PreparedStatement stmt = null;
        ResultSet rs = null;
		
		List<Departamento> departamentos = new ArrayList<>();
        String sql = "SELECT * FROM departamento";
    
        try {        	
        	stmt =  connection.prepareStatement(sql);
        	rs = stmt.executeQuery();

            while (rs.next()) {
                Departamento departamento = new Departamento();
                departamento.setId(rs.getInt("id"));
                departamento.setNome(rs.getString("nome"));
                departamentos.add(departamento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
    		DB.closeStatement(stmt);
    		DB.closeResultSet(rs);
    	}
        return departamentos;
	}

}
