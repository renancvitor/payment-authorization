package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class DB {
	
	private static Connection

	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("config.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	} 
}
