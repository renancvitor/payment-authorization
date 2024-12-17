package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.PessoaDao;
import model.entities.Cargo;
import model.entities.Departamento;
import model.entities.Pessoa;
import model.entities.Usuario;

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
		PreparedStatement stmt = null;
        ResultSet rs = null;
		
		List<Pessoa> pessoas = new ArrayList<>();
        String sql = "SELECT\r\n"
        		+ "	p.idpessoa,\r\n"
        		+ "    p.nome AS NomePessoa,\r\n"
        		+ "    p.datanascimento,\r\n"
        		+ "    d.nome AS NomeDepartamento,\r\n"
        		+ "    c.nome AS NomeCargo,\r\n"
        		+ "    p.cpf\r\n"
        		+ "FROM\r\n"
        		+ "	pessoa p\r\n"
        		+ "INNER JOIN\r\n"
        		+ "	cargo c ON p.idcargo = c.id\r\n"
        		+ "INNER JOIN\r\n"
        		+ "	departamento d ON p.iddepartamento = d.id";

        try {        	
        	stmt =  connection.prepareStatement(sql);
        	rs = stmt.executeQuery();

            while (rs.next()) {
            	Pessoa pessoa = new Pessoa();
            	
            	pessoa.setId(rs.getInt("idpessoa"));
            	pessoa.setNome(rs.getString("NomePessoa"));
            	pessoa.setDatanascimento(rs.getDate("datanascimento").toLocalDate());            	
            	pessoa.setDepartamento(new Departamento(rs.getString("NomeDepartamento")));            	
            	pessoa.setCargo(new Cargo(rs.getString("NomeCargo")));            	
            	pessoa.setCpf(rs.getString("cpf"));
            	
                pessoas.add(pessoa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
    		DB.closeStatement(stmt);
    		DB.closeResultSet(rs);
    	}
        
        return pessoas;
	}

}
