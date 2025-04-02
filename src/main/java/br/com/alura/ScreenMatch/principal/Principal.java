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
//        List<RecordEpisodio> listaEpisodios = listaTemporadas.stream()
//                .flatMap(temporada -> temporada.episodios().stream())
//                .collect(Collectors.toList());
//
//        System.out.println("-------- Top 5 episódios (letra maiúscula)------------");
//        listaEpisodios.stream()
//                //não pega os que tem avaliação N/A
//                .filter(episodio -> !episodio.avaliacao().equals("N/A"))
//                //ver cada etapa do stream depois que filtrou
//                .peek(recordEpisodio -> System.out.println("filtrando para que não apareça N/A " + recordEpisodio))
//                //ordenando de forma decrescente pela avaliação de cada episódio
//                .sorted(Comparator.comparing(RecordEpisodio::avaliacao).reversed())
//                //ver cada etapa do stream depois que ordenou
//                .peek(recordEpisodio -> System.out.println("ordenação em ordem alfabética " + recordEpisodio))
//                //limitando nos 5 primeiros episódios
//                .limit(5)
//                //ver cada etapa do stream limitar em 5
//                .peek(recordEpisodio -> System.out.println("limitação em 5 episódios " + recordEpisodio))
//                //colocando os títulos em letra maiúscula
//                .map(recordEpisodio -> recordEpisodio.titulo().toUpperCase())
//                //ver cada etapa do stream depois que transformou em maiúscula
//                .peek(recordEpisodio -> System.out.println("transformação em letra maiúscula " + recordEpisodio))
//                //imprimindo cada episódio
//                .forEach(System.out::println);


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

//  -------------Filtro para saber qual temporada pertence a cada episódio
//        System.out.println("\n---------- Pesquisando um título de um episódio -------------");
//        System.out.println("Digite o trecho de um título: ");
//        //declaração de trecho título (usuário digitar)
//        String trechoTitulo = leitura.nextLine();
//        //Optional -> Guarda um episódio e podemos ver se é nulo ou não
//        Optional <ClasseEpisodio> episodioBuscado = episodios.stream()
//                // transformar a pesqusiar em minúscula e se o título pesquisado, tiver um trecho do título
//                .filter(recordEpisodio -> recordEpisodio.getTitulo().toLowerCase().contains(trechoTitulo))
//                //encontrar a primeira referência a ser buscada
//                .findFirst();
//
//        //verificar se o episódio foi encontrado
//        if(episodioBuscado.isPresent()){
//            //imprime a temporada a que o episódio pertence
//            System.out.println("Episódio encontrado!");
//            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
//        } else{
//            //informna que o episódio não foi encontrado
//            System.out.println("Episódio não encontrado!");
//        }

// ---------------- Filtro para saber a partir de quando o usuário quer ver os episódios
//        System.out.println("---------- Imprimir os episódios a partir de uma data -------------");
//        System.out.println("A partir de qual ano você deseja ver os episódios?");
//        int ano = leitura.nextInt();
//        leitura.nextLine(); //sempre colocar o nextLine depois do nextInt
//
//        //formar uma data a 01/01/ano digitado
//        LocalDate dataBusca = LocalDate.of(ano,1,1);
//
//        //formatador de data para padrão brasileiro
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        //usar a lista episódios já criada
//        episodios.stream()
//                //para cada episódio pegar a data de lançamento não nula e após(after) da data de busca
//                .filter(ep -> !ep.equals(null) && ep.getDataLancamento().isAfter(dataBusca))
//                //para cada episódio filtrado, imprimir
//                .forEach(ep -> System.out.println(
//                        "Temporada: " + ep.getTemporada() +
//                                "\nEpisódio: " + ep.getTitulo() +
//                                //formatando para padrão Brasil
//                                "\nLançamnto: " + ep.getDataLancamento().format(formatador)
//                ));

// ---------------- Agrupoar as avaliações por temporada
        //map de integer para double
        System.out.println("\n---------- Imprimir as avaliações por temporada -------------");
        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                //filtrar os episódios com avaliação maior que 0
                .filter(e -> e.getAvaliacao() > 0)
                //agrupar os episódios por temporada
                .collect(Collectors.groupingBy(ClasseEpisodio::getTemporada,
                        //associar às temporadas pela média de avaliações
                        Collectors.averagingDouble(ClasseEpisodio::getAvaliacao)));
        //imprimindo as avaliações por temporada
        System.out.println(avaliacoesPorTemporada);

// ---------------- Coletando outras estatísticas
        System.out.println("\n---------- Imprimir estatísticas com base nas avaliações -------------");
        //Coletando os dados e retornando numa DoubleSummaryStatistics
        DoubleSummaryStatistics estatisticas = episodios.stream()
                //filtrando os episódio que tem avaliação maior que 0
                .filter(e -> e.getAvaliacao() > 0)
                //coletando os dados e passando para summarizingDouble com base na avaliação
                .collect(Collectors.summarizingDouble(ClasseEpisodio::getAvaliacao));
        //imprimindo as estatísticas
        System.out.println("Média: " + estatisticas.getAverage() + "\n" +
                "Maior Avaliação: " + estatisticas.getMax() + "\n" +
                "Menor Avaliação: " + estatisticas.getMin() + "\n" +
                "Quantidade de avaliações: " + estatisticas.getCount());
    }
}




