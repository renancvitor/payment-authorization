package model.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import db.DbException;
import model.dao.UserTypeDao;
import model.entities.UserType;

public class UserTypeDaoJDBC implements UserTypeDao {
	
	private Connection connection;
	
	private static final Map<UserType, Integer> userTypeToIdMap = new HashMap<>();
    private static final Map<Integer, UserType> idToUserTypeMap = new HashMap<>();
	
	public UserTypeDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void loadUserTypes() {
		String query = "SELECT id, nome FROM tipos_usuarios";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");

                UserType userType = UserType.valueOf(nome.toUpperCase());
                userTypeToIdMap.put(userType, id);
                idToUserTypeMap.put(id, userType);
            }

        } catch (SQLException e) {
        	throw new DbException(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: Tipo de usuário no banco não corresponde ao enum UserType.");
        }
    }

}
