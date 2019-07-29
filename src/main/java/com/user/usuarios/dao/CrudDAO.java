
package com.user.usuarios.dao;

import java.util.List;


public interface CrudDAO<U> {
    public void incluir(U user);
    public void remover(U user);
    public List<U> buscar();
    public U buscarlogin(U user);
}
