package Postagens.Postagens.Model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Document
public class Postagens {
    @Id
    private Long id;
    @NotNull(message = "A descrição não pode ser nula")
    private String descricao;
    @NotNull(message = "A URL da imagem não pode ser nula")
    private String imgUrl;
    @NotBlank(message = "O ID do usuário não pode ser nulo")
    private Long userId;
    @NotBlank(message = "O número de curtidas não pode ser nulo")
    private int curtidas;
    @NotBlank(message = "O número de comentários não pode ser nulo")
    private int comentarios;
     private LocalDateTime dataPostagem = LocalDateTime.now();
    public Postagens() {
    }
    public Postagens(Long id, String descricao, String imgUrl, Long userId, int curtidas, int comentarios) {
        this.id = id;
        this.descricao = descricao;
        this.imgUrl = imgUrl;
        this.userId = userId;
        this.curtidas = curtidas;
        this.comentarios = comentarios;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public int getCurtidas() {
        return curtidas;
    }
    public void setCurtidas(int curtidas) {
        this.curtidas = curtidas;
    }
    public int getComentarios() {
        return comentarios;
    }
    public void setComentarios(int comentarios) {
        this.comentarios = comentarios;
    }
    public void setDataPostagem(LocalDateTime dataPostagem) {
        this.dataPostagem = dataPostagem;
    }
    
}
