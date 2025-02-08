package br.com.alura.ScreenMatch;

import br.com.alura.ScreenMatch.model.Serie;
import br.com.alura.ScreenMatch.service.ConsumoApiService;
import br.com.alura.ScreenMatch.service.ConverteDados;
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
		//sabe-se que está usando uma variável ConsumoApi pelo new
		var consumoApiService = new ConsumoApiService();
		//chamando método obterDados de consumoApiService
		var json = consumoApiService.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c");
		System.out.println(json);

		//Usar o conversor
		ConverteDados conversor = new ConverteDados();
		//informando o conversor para que transforme o json em Serie
		Serie dados = conversor.obterDados(json, Serie.class);
		System.out.println(dados);
	}
}
