// package com.autobots.app.modelos.usuario;

// import org.hibernate.Hibernate;
// import org.springframework.stereotype.Component;

// import com.autobots.app.entidades.Usuario;
// import com.autobots.app.entidades.Endereco;

// @Component
// public class UsuarioLoader {
//     public Usuario loadFullUsuario(Usuario usuario) {
//         Hibernate.initialize(usuario.getDocumentos());
//         Hibernate.initialize(usuario.getTelefones());
//         Hibernate.initialize(usuario.getEndereco());

//         usuario.setEndereco((Endereco) Hibernate.unproxy(usuario.getEndereco()));

//         return usuario;
//     }
// }
