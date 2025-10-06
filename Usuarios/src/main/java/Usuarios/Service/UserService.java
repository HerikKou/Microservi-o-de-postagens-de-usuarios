package Usuarios.Service;
import Usuarios.Repository.*;
import Usuarios.Model.*;
import Usuarios.Config.TokenConfig;
import Usuarios.DTO.*;
import Usuarios.ExceptionHandlers.*;
import java.util.*;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private  final UserRepository userepository;
   
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;
    
    private final TokenConfig tokenConfig;
    
    public UserService(UserRepository userepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,TokenConfig tokenConfig) {
        this.userepository = userepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
    }

    public UserModel cadastrar(UserDTO userdto) throws Existente{
        if(userepository.findByEmail(userdto.getEmail()).isPresent()){
            throw new Existente("Email já cadastrado!");
        }
        String hashSenha = passwordEncoder.encode(userdto.getSenha());
       

        UserModel usuarios = new UserModel();
        usuarios.setNome(userdto.getNome());
        usuarios.setEmail(userdto.getEmail());
        usuarios.setSenha(hashSenha);

        return userepository.save(usuarios);
    } 

    public LoginDTO login(UserDTO userdto) throws NaoEncontrado{
       UsernamePasswordAuthenticationToken  usernameAndPassword = new UsernamePasswordAuthenticationToken(userdto.getEmail(),userdto.getSenha());
       Authentication authentication = authenticationManager.authenticate(usernameAndPassword);
       UserModel usuario = (UserModel) authentication.getPrincipal();
       String token = tokenConfig.generatedToken(usuario);
        return new LoginDTO(token,usuario.getEmail());
    }

    public List<UserModel> listarTodosUsuarios(){
        return userepository.findAll();
    }

    public UserModel encontrarUsuario(Long id) throws NaoEncontrado{
        return userepository.findById(id).orElseThrow(()-> new NaoEncontrado("Usuário não encontrado!"));
        
    }
}
