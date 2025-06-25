// package com.autobots.app.modelos.usuario;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;

// import com.autobots.app.entidades.Usuario;

// @Component
// public class UsuarioGetLoaded {

//     @Autowired
//     private UsuarioLoader usuarioLoader;

//     UsuarioGetLoaded(UsuarioLoader usuarioLoader) {
//         this.usuarioLoader = usuarioLoader;
//     }

//     public Usuario getOne(Usuario usuario) {
//         return this.usuarioLoader.loadFullUsuario(usuario);
//     }

//     public List<Usuario> getAll(List<Usuario> usuarios) {
//         return usuarios.stream()
//             .map(this.usuarioLoader::loadFullUsuario)
//             .toList();
//     }
// }
