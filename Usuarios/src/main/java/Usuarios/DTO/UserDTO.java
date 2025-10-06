package Usuarios.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDTO {
    @NotNull(message = "Nome não pode estar vazio")
    private String nome;
    @NotNull(message = "Email não pode estar vazio")
    private String email;
    @Size(min = 8, max = 20 , message = "A senha deve ter no minimo 6 caracteres e no máximo 20 caracteres")
    private String senha;


    public UserDTO(){}

    public UserDTO( String nome,String email,String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
