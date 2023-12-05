package com.example;

import com.example.db.DBHandler;
import com.example.db.DBHandlerImpl;
import com.example.db.DatabaseManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestApplication {
	public static DBHandler dbHandler;

	public static void main(String[] args) {
		DatabaseManager.getInstance();
		DatabaseManager.populateTable();
		dbHandler = new DBHandlerImpl();

		SpringApplication.run(RestApplication.class, args);
	}

}
//http://localhost:8080/swagger-ui/index.html