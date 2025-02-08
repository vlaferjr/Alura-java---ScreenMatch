package br.com.alura.ScreenMatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApiService {

    public String obterDados (String endereco){
        //cliente que faz a requisição
        HttpClient client = HttpClient.newHttpClient();
        //Pra qual endereço a requisição acontecerá
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = null;
        try{
            //client manda a requisição e aqui recebe a resposta
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e){
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //devolução da resposta
        String json = response.body();
        return json;
    }
}
