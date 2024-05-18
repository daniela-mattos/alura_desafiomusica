package br.com.alura.desafiomusicas.desafiomusicas.principal;

import br.com.alura.desafiomusicas.desafiomusicas.model.Artista;
import br.com.alura.desafiomusicas.desafiomusicas.model.Musica;
import br.com.alura.desafiomusicas.desafiomusicas.model.TipoArtista;
import br.com.alura.desafiomusicas.desafiomusicas.repository.ArtistaRepository;
import br.com.alura.desafiomusicas.desafiomusicas.service.ConsultaChatGPT;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ArtistaRepository artistaRepository;
    private List<Artista> artistas;

    public Principal(ArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    *** Screen Sound ***
                    1 - Cadastrar Artista
                    2 - Cadastrar Música
                    3 - Listar músicas
                    4 - Buscar músicas por artistas
                    5 - Pesquisar dados sobre um artista
                                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastraArtista();
                    break;
                case 2:
                    cadastraMusica();
                    break;
                case 3:
                    listaMusicas();
                    break;
                case 4:
                    buscaMusicaPorArtista();
                    break;
                case 5:
                    pesquisaDadosArtista();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void cadastraArtista() {
        var opcao = "s";
        while (opcao.equalsIgnoreCase("s")) {
            System.out.println("Informe o nome do artista: ");
            String nome = leitura.nextLine();
            System.out.println("Qual o tipo do artista? (solo, dupla, banda, boyband)");
            String tipo = leitura.nextLine();
            TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());

            Artista artista = new Artista(nome, tipoArtista);
            artistaRepository.save(artista);

            System.out.println("Cadastrar outro artista? S/N");
            opcao = leitura.nextLine();
        }
    }

    private void cadastraMusica() {
        var opcao = "s";
        while (opcao.equalsIgnoreCase("s")) {
            System.out.println("Informe o artista: ");
            String nomeArtista = leitura.nextLine();
            Optional<Artista> artista = artistaRepository.findByNomeContainingIgnoreCase(nomeArtista);

            if (artista.isPresent()) {
                System.out.println("Informe o titulo da música: ");
                String titulo = leitura.nextLine();
                Musica musica = new Musica(titulo, artista.get());
                artista.get().getMusicas().add(musica);
                artistaRepository.save(artista.get());
            } else {
                System.out.println("Artista não encontrado!");
            }

            System.out.println("Cadastrar outra musica? S/N");
            opcao = leitura.nextLine();
        }
    }

    private void listaMusicas() {
        List<Artista> artistas = artistaRepository.findAll();
        artistas.forEach(e -> e.getMusicas().forEach(System.out::println));
    }

    private void buscaMusicaPorArtista() {
        var opcao = "s";
        while (opcao.equalsIgnoreCase("s")) {
            System.out.println("Informe o artista: ");
            String nomeArtista = leitura.nextLine();
            List<Musica> musicas = artistaRepository.buscaMusicasPorArtistas(nomeArtista);
            musicas.forEach(System.out::println);

            System.out.println("Buscar outro artista? S/N");
            opcao = leitura.nextLine();
        }
    }

    private void pesquisaDadosArtista() {
//        API Não funciona
        System.out.println("Pesquisar dados sobre qual artista? ");
        var nome = leitura.nextLine();
        var resposta = ConsultaChatGPT.obterDadosArtista(nome);
        System.out.println(resposta.trim());
    }
}




