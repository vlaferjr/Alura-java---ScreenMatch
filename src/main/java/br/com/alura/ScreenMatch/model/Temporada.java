package br.com.alura.ScreenMatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) /*Ignorar as demais propriedades*/
public record Temporada(@JsonAlias("Season") Integer numeroTemporada,
                        @JsonAlias("Episodes")List<RecordEpisodio>episodios) {
}
