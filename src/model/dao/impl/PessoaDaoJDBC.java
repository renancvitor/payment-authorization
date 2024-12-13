package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import db.DbException;
import model.dao.PessoaDao;
import model.entities.Pessoa;

public class PessoaDaoJDBC implements PessoaDao {
	
	private Connection connection;
	
	public PessoaDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Pessoa obj) {
		if (obj.getNome() == null || obj.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome da pessoa não pode ser vazio.");
        }
        String sql = "INSERT INTO pessoa (nome, datanascimento, iddepartamento, idcargo, cpf) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, obj.getNome());
            stmt.setDate(2, Date.valueOf(obj.getDatanascimento()));
            stmt.setInt(3, obj.getDepartamento().getId());
            stmt.setInt(4, obj.getCargo().getId());
            stmt.setString(5, obj.getCpf());
            stmt.executeUpdate();
        } catch (SQLException e) {
        	throw new DbException(e.getMessage());
        }
		
	}

	@Override
	public void update(Pessoa obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pessoa findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pessoa> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
