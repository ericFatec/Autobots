// package com.autobots.app.modelos.empresa;

// import org.hibernate.Hibernate;
// import org.springframework.stereotype.Component;

// import com.autobots.app.entidades.Empresa;
// import com.autobots.app.entidades.Endereco;

// @Component
// public class EmpresaLoader {
//     public Empresa loadFullEmpresa(Empresa empresa) {
//         Hibernate.initialize(empresa.getTelefones());
//         Hibernate.initialize(empresa.getEndereco());

//         empresa.setEndereco((Endereco) Hibernate.unproxy(empresa.getEndereco()));

//         return empresa;
//     }
// }
