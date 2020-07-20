package PrincipalPatrimonio;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.io.PrintStream;
import java.sql.PreparedStatement;

/**
 *
 * @author elton
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
//Classe de conexão com MySQL
public class ConexaoMySQL {

    private static ConexaoMySQL instance = null;
    private Connection connection = null;
    private PreparedStatement stm;

    public ConexaoMySQL() {
        try {
            //Definindo o nome do driver
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName);
            //Definindo a porta de conexão
            String serverName = "0.0.0.0";
            //Nome do DB
            String dbName = "Cadastro";
            //realizando a concatenação
            String url = "jdbc:mysql://"
                    + serverName + "/"
                    + dbName;

            //Username e password do mysql
            String username = "root";
            String password = "1234";

            connection = DriverManager.getConnection(url,
                    username, password);
//Verificações de erros
            if (connection != null) {
                System.out.println("STATUS--->Conectado "
                        + "com sucesso!");
            } else {
                System.err.println("STATUS--->Não foi "
                        + "possivel realizar conexão");
            }
            connection.setAutoCommit(true);
        } catch (ClassNotFoundException e) {

            System.out.println("O driver expecificado"
                    + " nao foi encontrado.");
        } catch (SQLException e) {

            System.out.println("Nao foi possivel"
                    + " conectar ao Banco de Dados.");
            e.printStackTrace();
        }
    }
//Verificando se há alguma outra conexão
    public static ConexaoMySQL getInstance() {
        if (instance == null) {
            instance = new ConexaoMySQL();
        }
        return instance;
    }
//Chamando a conexao
    public java.sql.Connection getConnection() {
        return connection;
    }
//Metodo que adicionado o item ao BD
    public void addItem(Item itens) {
//String onde será passado o comando do Mysql
        String sql = "";
        
        try {
            //Atribuindo o comando mysql ao sql
            sql = "INSERT INTO Register(NumeroPatrimonio, Descricao, Setor, Responsavel, Situacao, EstadoConservacao) VALUES(?,?,?,?,?,?);";
            //realizando a conexão
            Connection con = ConexaoMySQL.getInstance().getConnection();
            //preparando os parametros para inserção de dados com o prepareStatement
            stm = con.prepareStatement(sql);
            //Pegando os valores para serem inseridos
            stm.setInt(1, itens.getNumeroPatrimonio());
            stm.setString(2, itens.getDescricao());
            stm.setString(3, itens.getSetor());
            stm.setString(4, itens.getResponsavel());
            stm.setString(5, itens.getSituacao());
            stm.setString(6, itens.getEstadoConservacao());
            //Executando o comando no banco de dados
            stm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Salvo com Sucesso!");
            //Verifcando se há alguma excessão de numero iguais
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Número de Patrimonio já cadastrado!");
        } 

    }
    
    //Metodo que atualiza item no BD
    public void updateItem(Item itens) {
        //String onde será passado o comando do Mysql
        String sql = "";

        try {
            //Atribuindo o comando mysql ao sql
            sql = "UPDATE Register SET NumeroPatrimonio = ?, Descricao = ?, Setor = ?, Responsavel = ?, Situacao = ?, EstadoConservacao = ? WHERE ID_Item = ?;";
            //realizando a conexão
            Connection con = ConexaoMySQL.getInstance().getConnection();
            //preparando os parametros para inserção de dados com o prepareStatement
            stm = con.prepareStatement(sql);
            //Inserindo os valores novos
            stm.setInt(1, itens.getNumeroPatrimonio());
            stm.setString(2, itens.getDescricao());
            stm.setString(3, itens.getSetor());
            stm.setString(4, itens.getResponsavel());
            stm.setString(5, itens.getSituacao());
            stm.setString(6, itens.getEstadoConservacao());
            stm.setInt(7, itens.getId_Item());
            //Executando o comando no banco de dados
            stm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com Sucesso!");
            //Tratando algumas exceções referente ao número de patrimonio
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n"
                    + "Número de Patrimonio já existente!");
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Campo Número Patrimonio é obrigatorio e deve conter apenas números");
        }

    }
    //Metodo que remove item no bd
    public void removeItem(Item itens) {
        //String onde será passado o comando do Mysql
        String sql = "";

        try {
            //Atribuindo o comando mysql ao sql
            sql = "DELETE FROM Register WHERE ID_Item = ?;";
            //realizando a conexão
            Connection con = ConexaoMySQL.getInstance().getConnection();
            //preparando os parametros para inserção de dados com o prepareStatement
            stm = con.prepareStatement(sql);
            //Pegando o valor do ID para ser removido do banco de dados
            stm.setInt(1, itens.getId_Item());
            //Executando o comando de remoção no BD
            stm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Remoção realizada com Sucesso!");
            //Pegando alguma exceção de remoção!
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao remover!");
        }

    }
    

    
}
