package model.entities;

import java.sql.Connection;

public class AlterarSenha {
	
	private Connection connection;
	
	public AlterarSenha(Connection connection) {
        this.connection = connection;
    }

}
