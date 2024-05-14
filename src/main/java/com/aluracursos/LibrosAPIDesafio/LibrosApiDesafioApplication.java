package com.aluracursos.LibrosAPIDesafio;

import com.aluracursos.LibrosAPIDesafio.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibrosApiDesafioApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LibrosApiDesafioApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();

		principal.muestraMenu();
	}
}
