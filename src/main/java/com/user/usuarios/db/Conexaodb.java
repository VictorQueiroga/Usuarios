
package com.user.usuarios.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Conexaodb {
    private static Connection conexao;
    public static Connection getConexao(){
        if(conexao==null){
           try{
              Class.forName("com.mysql.jdbc.Driver");
              conexao=DriverManager.getConnection("jdbc:mysql://localhost/cadastro","root","root");
           } catch (SQLException ex){
              Logger.getLogger(Conexaodb.class.getName()).log(Level.SEVERE,null,ex);
           } catch (ClassNotFoundException ex){
              Logger.getLogger(Conexaodb.class.getName()).log(Level.SEVERE,null,ex);          
           }
        }
        return conexao;
    }
    public static void fecharConexao(){
        if(conexao!=null){
            try {
                conexao.close();
                conexao=null;
            } catch (SQLException ex) {
                Logger.getLogger(Conexaodb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
