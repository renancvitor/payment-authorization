package model.entities;

import java.sql.Connection;

public class Login {
	
	private Connection connection;
	
	public Login(Connection connection) {
		this.connection = connection;
	}

}
