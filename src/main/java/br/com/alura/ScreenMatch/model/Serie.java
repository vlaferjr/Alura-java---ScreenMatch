package br.com.alura.ScreenMatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonAlias é informar que no json terá um campo em inglês e será usado cada campo para cada variável
//@JsonAlias só serve para ler o json e não para escrever
@JsonIgnoreProperties(ignoreUnknown = true) //Ignorar propriedades que eu não quero mapear
public record Serie(@JsonAlias ("Title")String titulo,
                    @JsonAlias("totalSeasons") Integer totalTemporadas,
                    @JsonAlias("imdbRating") String avaliacao,
                    //Json propertie serve para serializar / desserializar
                    @JsonProperty("imdbVotes") String votos) {
}


