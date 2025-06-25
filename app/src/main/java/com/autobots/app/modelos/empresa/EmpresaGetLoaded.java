// package com.autobots.app.modelos.empresa;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;

// import com.autobots.app.entidades.Empresa;

// @Component
// public class EmpresaGetLoaded {
//     @Autowired
//     private EmpresaLoader empresaLoader;

//     EmpresaGetLoaded(EmpresaLoader empresaLoader) {
//         this.empresaLoader = empresaLoader;
//     }

//     public Empresa getOne(Empresa empresa) {
//         return this.empresaLoader.loadFullEmpresa(empresa);
//     }

//     public List<Empresa> getAll(List<Empresa> empresas) {
//         return empresas.stream()
//             .map(this.empresaLoader::loadFullEmpresa)
//             .toList();
//     }
// }
