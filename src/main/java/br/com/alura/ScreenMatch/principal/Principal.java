package br.com.alura.ScreenMatch.principal;

import br.com.alura.ScreenMatch.model.ClasseEpisodio;
import br.com.alura.ScreenMatch.model.RecordEpisodio;
import br.com.alura.ScreenMatch.model.Serie;
import br.com.alura.ScreenMatch.model.Temporada;
import br.com.alura.ScreenMatch.service.ConsumoApiService;
import br.com.alura.ScreenMatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    Scanner leitura = new Scanner(System.in);
    //endereco inicial
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    //Api Key
    private final String API_KEY = "&apikey=6585022c";

    private ConsumoApiService consumoApiService = new ConsumoApiService();

    //Usar o conversor
    private ConverteDados conversor = new ConverteDados();

    //---------------------- Exibição do menu
    public void exibeMenu(){
        System.out.println("Digite a série desejada: ");
        var nomeSerie = leitura.nextLine();

        //chamando método obterDados de consumoApiService
        //sempre que um nome tiver um espaço, será substituído por um +
        var json = this.consumoApiService.obterDados(ENDERECO + nomeSerie.replace(" ","+") + API_KEY);

//----------------------------- Conversão dos dados da série
        //usando o conversor
        Serie dados = conversor.obterDados(json, Serie.class);

        // imprimir os dados convertidos
        System.out.println("****** Impressão dos dados convertidos: *********");
        System.out.println(dados);

        //Criaando a lista de de temporadas
        List<Temporada> listaTemporadas = new ArrayList<>();

//--------------------- Convertendo os dados da temporada
        System.out.println(" \n *********************   Dados das Temporadas ************************ \n");
        //percorrendo as temporadas
        for (int i= 1; i <= dados.totalTemporadas(); i++) {

            // Atualizar o endereço //Buscando os episódios de todas as temporadas
            json = consumoApiService.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
            ;

            //usar o conversor
            Temporada dadosTemporada = conversor.obterDados(json, Temporada.class);

            //adcionando as temporadas numa lista
            listaTemporadas.add(dadosTemporada);

            // iterando a impressão de temporadas
            listaTemporadas.forEach(System.out::println);
        }

// --------------------------- Selecionar o que vai ser impresso (impressão dos títulos dos episódios)
        System.out.println("\n ****** IMPRESSÃO DOS TÍTULOS DOS EPISÓDIOS **********");
        //iterar por cada episódio

        /*lê-se: para cada temporada t, vou percorrer os episódios,
        *para cada  episódio e, vou pegar os títulos*/
        listaTemporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));


// ------------------------------- Pegar os 5 melhores episódios entre todas as temporadas
        /*ter uma lista de episódios que serão extraídos de todas as temporadas*/
        List<RecordEpisodio> listaEpisodios = listaTemporadas.stream()
                .flatMap(temporada -> temporada.episodios().stream())
                .collect(Collectors.toList());

        System.out.println("-------- Top 5 episódios ------------");
        listaEpisodios.stream()
                //não pega os que tem avaliação N/A
                .filter(episodio -> !episodio.avaliacao().equals("N/A"))
                //ordenando de forma decrescente pela avaliação de cada episódio
                .sorted(Comparator.comparing(RecordEpisodio::avaliacao).reversed())
                //limitando nos 5 primeiros episódios
                .limit(5)
                //imprimindo cada episódio
                .forEach(System.out::println);


// -------------saber qual temporada pertence a cada episódio
        /*abaixo temos:
        * uma lista de temporadas, onde para cada temporada temos uma lista de episódios
        * cada recordEpisódio é modificado em ClasseEpisódio pegando número da temporada e a lista de eposódios*/
        List<ClasseEpisodio> episodios = listaTemporadas.stream()
                .flatMap(temporada -> temporada.episodios()
                        .stream()
                        .map(recordEpisodio -> new ClasseEpisodio(temporada.numeroTemporada(), recordEpisodio)))
                .collect(Collectors.toList());
        //impressão da lista
        System.out.println("---------- Top 5 episódios exibindo as temporadas -------------");
        episodios.forEach(System.out::println);

// ---------------- Filtro para saber a partir de quando o usuário quer ver os episódios
        System.out.println("---------- Imprimir os episódios a partir de uma data -------------");
        System.out.println("A partir de qual ano você deseja ver os episódios?");
        int ano = leitura.nextInt();
        leitura.nextLine(); //sempre colocar o nextLine depois do nextInt

        //formar uma data a 01/01/ano digitado
        LocalDate dataBusca = LocalDate.of(ano,1,1);

        //formatador de data para padrão brasileiro
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        //usar a lista episódios já criada
        episodios.stream()
                //para cada episódio pegar a data de lançamento não nula e após(after) da data de busca
                .filter(ep -> !ep.equals(null) && ep.getDataLancamento().isAfter(dataBusca))
                //para cada episódio filtrado, imprimir
                .forEach(ep -> System.out.println(
                        "Temporada: " + ep.getTemporada() +
                                "\nEpisódio: " + ep.getTitulo() +
                                //formatando para padrão Brasil
                                "\nLançamnto: " + ep.getDataLancamento().format(formatador)
                ));
    }
}




