package Postagens.Postagens.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import Postagens.Postagens.DTO.PostagensDTO;
import Postagens.Postagens.DTO.UserDTO;
import Postagens.Postagens.ExceptionsHandle.ArquivoInvalido;
import Postagens.Postagens.ExceptionsHandle.ArquivoVazio;
import Postagens.Postagens.ExceptionsHandle.NaoEncontrado;
import Postagens.Postagens.Model.Postagens;
import Postagens.Postagens.Repository.PostagensRepository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class PostagensService {

    private final PostagensRepository repository;
    private final WebClient.Builder webClientBuilder;
    @Value("${usuario.service.url}")
    private String baseUrl;

    public PostagensService(PostagensRepository repository, WebClient.Builder webClientBuilder) {
        this.repository = repository;
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<Postagens> salvarPostagem(Long userId, MultipartFile file, PostagensDTO postagensDTO, String token) {
        // Cria um WebClient com token dinâmico
        WebClient webClient = webClientBuilder
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + token)
                .build();

        return webClient.get()
                .uri("/encontrarUsuario/{id}", userId)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .switchIfEmpty(Mono.error(new NaoEncontrado("Usuário não encontrado.")))
                .flatMap(user -> {

                    if (file.isEmpty()) {
                        return Mono.error(new ArquivoVazio("O arquivo está vazio."));
                    }

                    String nomeOriginal = file.getOriginalFilename();
                    if (nomeOriginal == null || 
                        !(nomeOriginal.endsWith(".png") || nomeOriginal.endsWith(".jpg") || nomeOriginal.endsWith(".jpeg"))) {
                        return Mono.error(new ArquivoInvalido("Tipo de arquivo inválido. Apenas PNG, JPG e JPEG são permitidos."));
                    }

                    String pasta = "downloads";
                    File diretorio = new File(pasta);
                    if (!diretorio.exists()) {
                        diretorio.mkdirs();
                    }

                    String nomeDoArquivo = UUID.randomUUID() + "_" + nomeOriginal;
                    Path caminhoArquivo = Paths.get(pasta, nomeDoArquivo);

                    // Salva o arquivo de forma reativa
                    return Mono.fromCallable(() -> {
                                file.transferTo(caminhoArquivo.toFile());
                                return caminhoArquivo.toString();
                            })
                            .subscribeOn(Schedulers.boundedElastic())
                            .flatMap(caminho -> {
                                Postagens postagem = new Postagens();
                                postagem.setUserId(userId);
                                postagem.setImgUrl(caminho);
                                postagem.setDataPostagem(LocalDateTime.now());
                                postagem.setDescricao(postagensDTO.getDescricao());
                                postagem.setCurtidas(postagensDTO.getCurtidas());

                                // Salva no repositório de forma não bloqueante
                                return Mono.fromCallable(() -> repository.save(postagem))
                                           .subscribeOn(Schedulers.boundedElastic());
                            });
                });
    }
}
