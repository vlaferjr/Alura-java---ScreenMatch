package br.com.alura.ScreenMatch;

import br.com.alura.ScreenMatch.principal.Principal;
import br.com.alura.ScreenMatch.service.ConsumoApiService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//implementa interface de linha de comando
public class ScreenMatchApplication implements CommandLineRunner {
	//quando chamar SpringApplication.run, chamar o método que foi criado run
	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchApplication.class, args);
	}

	@Override
	//método chamado na main
	public void run(String... args) throws Exception {

//----------------------Chamando o exibeMenu (classe Principal)
		//instanciando a classe principal
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
