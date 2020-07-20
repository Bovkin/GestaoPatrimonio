/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PrincipalPatrimonio;

import forms.Home;

/**
 *
 * @author elton
 */
public class PrincipalPatrimonio {

    /**
     * @param args the command line arguments
     */
    //Classe main onde executa o projeto
    public static void main(String[] args) {
        //Execução do LookAndFellInfo Nimbus;
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch(Exception e ){
            System.out.println("Erro no tema");
        }
        
        //Criação da tela Home e setando ela em visivel
        Home tela1 = new Home();
        tela1.setVisible(true);
    }
    
}
