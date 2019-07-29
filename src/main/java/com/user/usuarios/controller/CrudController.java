
package com.user.usuarios.controller;

import com.user.usuarios.dao.CrudDAO;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


public abstract class CrudController<U,D extends CrudDAO> {
    
    private U user;
    private List<U> users;
    public void logar(U user){
       mudarParaLogin();
       this.user=user;
       user=(U) getDAO().buscarlogin(user);
       validarlogin(user);
       mudarParaBuscar();
    }
    public void adicionar(){
       user=createUser();
       mudarParaIncluir();
    }
    public void incluir(){
       if(isIncluir()){
          getDAO().incluir(user);
          user=createUser();
          mudarParaBuscar();
          addmsg("usuario incluido",FacesMessage.SEVERITY_INFO);
       }
       else{
          getDAO().incluir(user);
          user=createUser();
          mudarParaBuscar();
          addmsg("usuario atualizado",FacesMessage.SEVERITY_INFO);
       }
       
    }
    public void alterar(U user){
       this.user=user;
       mudarParaAlterar();
    }
    public void remover(U user){
       getDAO().remover(user);
       users.remove(user);
       addmsg("usuario deletado",FacesMessage.SEVERITY_INFO);
    }
    public void listar(){
        if(!isBusca()){
            mudarParaBuscar();
            return;
        }
       users=getDAO().buscar();
       if(users==null){
          addmsg("nenhum usuario foi cadastrado",FacesMessage.SEVERITY_INFO);
       } 
    }
    public void addmsg(String msg, FacesMessage.Severity tipo){
        FacesMessage message=new FacesMessage(tipo,msg,null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    public U getUser(){
       return user;
    }
    public void setUser(U user){
       this.user=user;
    }
    public List<U> getUsers(){
       return users;
    }
    public void setUsers(List<U> users){
       this.users=users;
    }
    public abstract D getDAO();
    public abstract U createUser(); 
    private String Tela="buscar";
    public boolean isIncluir(){
        return "incluir".equals(Tela);
    }
    public boolean isAlterar(){
        return "alterar".equals(Tela);
    }
    public boolean isBusca(){
        return "buscar".equals(Tela);
    }
    public boolean isLogin(){
        return "login".equals(Tela);
    }
    public void mudarParaIncluir(){
        Tela="incluir";
    }
    public void mudarParaAlterar(){
        Tela="alterar";
    }
    public void mudarParaBuscar(){
        Tela="buscar";
    }
    public void mudarParaLogin(){
        Tela="login";
    }
    public void validarlogin(U user){
       if(user==null){
          addmsg("usuario invalido",FacesMessage.SEVERITY_INFO);
       }
       else{
          addmsg("voce fez login com sucesso",FacesMessage.SEVERITY_INFO);
       }
    }
    
}
