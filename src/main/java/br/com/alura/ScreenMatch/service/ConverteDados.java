package br.com.alura.ScreenMatch.service;

import br.com.alura.ScreenMatch.model.Serie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//implementa a interface IConverteDados
public class ConverteDados implements IConverteDados{
    //ObjectMapper é uma classe usada para converter json no Jackson
    private ObjectMapper mapper = new ObjectMapper();

    //método da IConverteDados
    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            //pegar o json, pegar a classe e tentar converter
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            //senão, trate o erro com a exceção
            throw new RuntimeException(e);
        }
    }
}
