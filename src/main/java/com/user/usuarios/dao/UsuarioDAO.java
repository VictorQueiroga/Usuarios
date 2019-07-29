
package com.user.usuarios.dao;

import com.user.usuarios.db.Conexaodb;
import com.user.usuarios.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UsuarioDAO implements CrudDAO<Usuario> {
    @Override
    public void incluir(Usuario usuario){
        PreparedStatement ps;
        try {
            Connection conexao=Conexaodb.getConexao();
            if(usuario.getId()==null){
                ps=conexao.prepareCall("INSERT INTO usuarios VALUES (default,?,?,?)");
                ps.setString(1,usuario.getNome());
                ps.setString(2,usuario.getEmail());
                ps.setString(3,usuario.getSenha());
                ps.execute();
                ps=conexao.prepareCall("INSERT INTO telefones VALUES (default,?,?,?,?)");
                ps.setInt(1, usuario.getId());
                ps.setInt(2,usuario.getDdd());
                ps.setString(3,usuario.getNumero());
                ps.setString(4,usuario.getTipo());
            }
            else{
             
                ps=conexao.prepareStatement("update usuarios set nome=?,email=?,senha=? on id=?");
                ps.setString(1,usuario.getNome());
                ps.setString(2,usuario.getEmail());
                ps.setString(3,usuario.getSenha());
                ps.setInt(4, usuario.getId());
                ps.execute();
                ps=conexao.prepareStatement("update telefones set ddd=?,numero=?,tipo=? on iduser=?");
                ps.setInt(1,usuario.getDdd());
                ps.setString(2,usuario.getNumero());
                ps.setString(3,usuario.getTipo());
                ps.setInt(4, usuario.getId());
                ps.execute();
            }
            Conexaodb.fecharConexao();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void remover(Usuario usuario){
        try {
            Connection conexao=Conexaodb.getConexao();
            PreparedStatement ps=conexao.prepareStatement("delete from telefones where iduser=?");
            ps.setInt(1, usuario.getId());
            ps.execute(); 
            ps=conexao.prepareStatement("delete from usuarios where id=?");
            ps.setInt(1, usuario.getId());
            ps.execute();
            Conexaodb.fecharConexao();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    @Override
    public Usuario buscarlogin(Usuario usuario){
        Usuario u=new Usuario();
        try {
            
            Connection conexao=Conexaodb.getConexao();
            PreparedStatement ps= conexao.prepareStatement("select * from usuarios where email=? and senha=?");
            ps.setString(1,usuario.getEmail());
            ps.setString(2,usuario.getSenha());
            ResultSet resultSet=ps.executeQuery();
            u.setId(resultSet.getInt("id"));
            u.setEmail(resultSet.getString("email"));
            u.setSenha(resultSet.getString("senha"));
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        Conexaodb.fecharConexao();
        return u;
       
    }
    @Override
    public List<Usuario> buscar(){
        try {
            Connection conexao=Conexaodb.getConexao();
            PreparedStatement ps= conexao.prepareStatement("select * from usuarios inner join telefones on id=iduser");
            ResultSet resultSet=ps.executeQuery();
            List<Usuario> Usuarios=new ArrayList<>();
            while(resultSet.next()){
               Usuario usuario=new Usuario();
               usuario.setId(resultSet.getInt("id"));
               usuario.setNome(resultSet.getString("nome"));
               usuario.setEmail(resultSet.getString("email"));
               usuario.setDdd(resultSet.getInt("ddd"));
               usuario.setNumero(resultSet.getString("numero"));
               usuario.setTipo(resultSet.getString("tipo"));
               Usuarios.add(usuario);
            }
            Conexaodb.fecharConexao();
            return Usuarios;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
