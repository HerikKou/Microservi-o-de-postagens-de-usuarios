package Usuarios.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import Usuarios.Service.*;
import jakarta.validation.Valid;
import Usuarios.Model.*;
import Usuarios.DTO.*;
import Usuarios.ExceptionHandlers.Existente;
import Usuarios.ExceptionHandlers.NaoEncontrado;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    private final UserService service;
    public UserController(UserService service) {
        this.service = service;
    }
    @PostMapping("/cadastro")
    public ResponseEntity<UserModel> cadastro(@RequestBody UserDTO userdto) throws Existente {
        UserModel usuarios = service.cadastrar(userdto);
         if(usuarios != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarios);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        
        
    }
    

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> logar(@Valid @RequestBody UserDTO userdto) throws NaoEncontrado{
        LoginDTO usuarios = service.login(userdto);
         if(usuarios != null){
            return ResponseEntity.status(HttpStatus.OK).body(usuarios);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    

    @GetMapping("/encontrarUsuario/{id}")
    public ResponseEntity<UserModel> encontrarUsuario(@Valid @PathVariable Long id) throws NaoEncontrado {
        UserModel usuarios = service.encontrarUsuario(id);
         if(usuarios != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarios);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    
    @GetMapping("/todosUsuarios")
    public ResponseEntity<List<UserModel>> listarTodosUsuarios(){
        List<UserModel> usuarios = service.listarTodosUsuarios();
       return ResponseEntity.ok(usuarios);
    }
    
    
    
}
