package com.deliverytouroptimizer.deliverytouroptimizerv2;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Deliverytouroptimizerv2Application {

	public static void main(String[] args) {
		loadEnv();
		SpringApplication.run(Deliverytouroptimizerv2Application.class, args);
	}

	private static void loadEnv(){
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		dotenv.entries().forEach(entry -> {
			if(entry.getKey().equalsIgnoreCase("ACTIVE_PROFILE")){
				var dotenv1 = Dotenv.configure().filename(".env."+entry.getValue()).ignoreIfMissing().load();
				dotenv1.entries().forEach(entry2 -> {
					System.setProperty(entry2.getKey(), entry2.getValue());
				});
			}
			System.setProperty(entry.getKey(), entry.getValue());
		});
	}
}
