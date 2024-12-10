package DAO;

import model.entities.UserType;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class UserTypeDAO {
    private final Connection connection;

    private static final Map<UserType, Integer> userTypeToIdMap = new HashMap<>();
    private static final Map<Integer, UserType> idToUserTypeMap = new HashMap<>();

    public UserTypeDAO(Connection connection) {
        this.connection = connection;
        loadUserTypes();
    }

    private void loadUserTypes() {
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
            e.printStackTrace();
            System.out.println("Erro ao carregar tipos de usuários do banco.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: Tipo de usuário no banco não corresponde ao enum UserType.");
        }
    }
}
