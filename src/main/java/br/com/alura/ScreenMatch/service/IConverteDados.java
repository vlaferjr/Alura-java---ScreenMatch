package br.com.alura.ScreenMatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IConverteDados {
    //obterDados vai receber o json e devolve o tipo desejado

    //<T> é Generics => vai me retornar algo que eu não sei o que é
    <T> T obterDados (String json, Class<T>classe) throws JsonProcessingException;

}
