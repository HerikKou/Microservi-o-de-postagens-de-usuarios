package Postagens.Postagens.DTO;

import jakarta.validation.constraints.NotNull;

public class UserDTO {
    @NotNull(message = "Nome não pode estar vazio")
    private String nome;
    @NotNull(message = "Email não pode estar vazio")
    private String email;
    public UserDTO(String nome,String email) {
        this.nome = nome;
        this.email = email;
    }
     public UserDTO() {
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
}
