
package com.user.usuarios.controller;


import com.user.usuarios.dao.UsuarioDAO;
import com.user.usuarios.model.Usuario;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class UsuarioController extends CrudController<Usuario, UsuarioDAO> {
    private UsuarioDAO usuarioDAO;
    @Override
    public UsuarioDAO getDAO() {
        if(usuarioDAO==null){
           usuarioDAO=new UsuarioDAO();
        }
        return usuarioDAO;
    }

    @Override
    public Usuario createUser() {
        return new Usuario();
    }
 
}
