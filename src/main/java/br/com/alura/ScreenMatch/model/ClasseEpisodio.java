package br.com.alura.ScreenMatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ClasseEpisodio {

    //Declaração dos atributos
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private double avaliacao;
    private LocalDate dataLancamento;

    //Getters e Setters
    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    //Criação do construtor
    public ClasseEpisodio(Integer numeroTemporada, RecordEpisodio recordEpisodios) {
        this.temporada = numeroTemporada;
        this.titulo = recordEpisodios.titulo();
        this.numeroEpisodio = recordEpisodios.numeroEpisodio();
        try{
            //vai ler da string e transfomar num Double
            this.avaliacao = Double.valueOf(recordEpisodios.avaliacao());
        } //caso tenhamos NumberFormatException, avaliação recebe 0
        catch (NumberFormatException e){
            this.avaliacao = 0.0;
        }
        try {
            //vai ler como string e transformar num localDate
            this.dataLancamento = LocalDate.parse(recordEpisodios.dataLancamento());
            //caso tenhamos DateTimeParseException, dataLancamento recebe null
        } catch (DateTimeParseException e){
            this.dataLancamento = null;
        }
    }

    //criação do ToString
    @Override
    public String toString() {
        return "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", avaliacao=" + avaliacao +
                ", dataLancamento=" + dataLancamento;
    }
}
