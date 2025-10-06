package Postagens.Postagens.Controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import Postagens.Postagens.DTO.PostagensDTO;
import Postagens.Postagens.Model.Postagens;
import Postagens.Postagens.Service.PostagensService;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/postagens")
public class PostagensController {

    private final PostagensService postagensService;
    private final ObjectMapper objectMapper;

    public PostagensController(PostagensService postagensService, ObjectMapper objectMapper) {
        this.postagensService = postagensService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(
            value = "/salvar/{userId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Mono<ResponseEntity<Postagens>> salvarPostagem(
            @PathVariable Long userId,
            @RequestPart("file") MultipartFile file,
            @RequestPart("postagem") String postagemJson,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        try {
            // Converte JSON do form-data para DTO
            PostagensDTO postagemDTO = objectMapper.readValue(postagemJson, PostagensDTO.class);

            // Extrai token (sem Bearer)
            String token = authorizationHeader.replace("Bearer ", "").trim();

            return postagensService.salvarPostagem(userId, file, postagemDTO, token)
                    .map(postagem -> ResponseEntity.ok(postagem)).onErrorResume(e-> {
                        return Mono.just(ResponseEntity.badRequest().body(null));
                    });
                    

        } catch (Exception e) {
            return Mono.just(ResponseEntity.badRequest().body(null));
        }
    }
}