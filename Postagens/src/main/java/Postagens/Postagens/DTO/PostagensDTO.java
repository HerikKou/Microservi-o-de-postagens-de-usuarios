package Postagens.Postagens.DTO;

import jakarta.validation.constraints.NotNull;

public class PostagensDTO {
    @NotNull(message = "A descrição não pode ser nula")
    private String descricao;
    @NotNull(message = "A URL da imagem não pode ser nula")
    private String imgUrl;
    @NotNull(message = "O ID do usuário não pode ser nulo")
    private Long userId;
    @NotNull(message = "O número de curtidas não pode ser nulo")
    private int curtidas;
    @NotNull(message = "O número de comentários não pode ser nulo")
    private int comentarios;
   
    public PostagensDTO() {
    }
    public PostagensDTO(String descricao, String imgUrl, Long userId, int curtidas, int comentarios) {
        this.descricao = descricao;
        this.imgUrl = imgUrl;
        this.userId = userId;
        this.curtidas = curtidas;
        this.comentarios = comentarios;
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
       this.userId = userId;}
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
    
    
    
}
