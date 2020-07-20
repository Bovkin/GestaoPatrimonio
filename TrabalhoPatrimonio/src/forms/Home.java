/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import javax.swing.JOptionPane;

/**
 *
 * @author elton
 */
public class Home extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
        //Abrir tela no meio
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonBusca = new javax.swing.JButton();
        jButtonCadastro = new javax.swing.JButton();
        jButtonSair = new javax.swing.JButton();
        jButtonRelatorios = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(94, 90, 96));
        jPanel1.setBorder(null);

        jButtonBusca.setBackground(new java.awt.Color(94, 90, 96));
        jButtonBusca.setFont(new java.awt.Font("Abyssinica SIL", 1, 14)); // NOI18N
        jButtonBusca.setForeground(new java.awt.Color(255, 255, 255));
        jButtonBusca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Buscar.png"))); // NOI18N
        jButtonBusca.setText("Busca");
        jButtonBusca.setToolTipText("Acesso a Busca de Itens");
        jButtonBusca.setBorder(null);
        jButtonBusca.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButtonBusca.setFocusCycleRoot(true);
        jButtonBusca.setFocusPainted(false);
        jButtonBusca.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonBusca.setPreferredSize(new java.awt.Dimension(97, 60));
        jButtonBusca.setRequestFocusEnabled(false);
        jButtonBusca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonBuscaMouseClicked(evt);
            }
        });
        jButtonBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscaActionPerformed(evt);
            }
        });

        jButtonCadastro.setBackground(new java.awt.Color(94, 90, 96));
        jButtonCadastro.setFont(new java.awt.Font("Abyssinica SIL", 1, 14)); // NOI18N
        jButtonCadastro.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCadastro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Cadastro.png"))); // NOI18N
        jButtonCadastro.setText("Cadastro");
        jButtonCadastro.setToolTipText("Acesso ao Cadastro de Itens");
        jButtonCadastro.setBorder(null);
        jButtonCadastro.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButtonCadastro.setFocusCycleRoot(true);
        jButtonCadastro.setFocusPainted(false);
        jButtonCadastro.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonCadastro.setRequestFocusEnabled(false);
        jButtonCadastro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonCadastroMouseClicked(evt);
            }
        });
        jButtonCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCadastroActionPerformed(evt);
            }
        });

        jButtonSair.setBackground(new java.awt.Color(94, 90, 96));
        jButtonSair.setFont(new java.awt.Font("Abyssinica SIL", 1, 14)); // NOI18N
        jButtonSair.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Sair.png"))); // NOI18N
        jButtonSair.setText("Sair");
        jButtonSair.setToolTipText("Sair da aplicação");
        jButtonSair.setBorder(null);
        jButtonSair.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButtonSair.setFocusCycleRoot(true);
        jButtonSair.setFocusPainted(false);
        jButtonSair.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonSair.setRequestFocusEnabled(false);
        jButtonSair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSairMouseClicked(evt);
            }
        });
        jButtonSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSairActionPerformed(evt);
            }
        });

        jButtonRelatorios.setBackground(new java.awt.Color(94, 90, 96));
        jButtonRelatorios.setFont(new java.awt.Font("Abyssinica SIL", 1, 14)); // NOI18N
        jButtonRelatorios.setForeground(new java.awt.Color(255, 255, 255));
        jButtonRelatorios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/PDFBranco.png"))); // NOI18N
        jButtonRelatorios.setText("Relatórios");
        jButtonRelatorios.setToolTipText("Sair da aplicação");
        jButtonRelatorios.setBorder(null);
        jButtonRelatorios.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButtonRelatorios.setFocusCycleRoot(true);
        jButtonRelatorios.setFocusPainted(false);
        jButtonRelatorios.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonRelatorios.setRequestFocusEnabled(false);
        jButtonRelatorios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonRelatoriosMouseClicked(evt);
            }
        });
        jButtonRelatorios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRelatoriosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                    .addComponent(jButtonBusca, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonSair, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                    .addComponent(jButtonRelatorios, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonRelatorios, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonSair, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(140, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(209, 209, 209));
        jPanel2.setBorder(null);
        jPanel2.setFocusable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 579, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBuscaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonBuscaMouseClicked
        
    }//GEN-LAST:event_jButtonBuscaMouseClicked

    private void jButtonCadastroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonCadastroMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonCadastroMouseClicked

    private void jButtonCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCadastroActionPerformed
        
        //Criando a tela e depois setando visivel e dispose na Home
        Cadastrar JanelaCadastrar = new Cadastrar();
        JanelaCadastrar.setVisible(true);
        dispose();
        //Mostrando a tela de carregando
        new Carregando();
    }//GEN-LAST:event_jButtonCadastroActionPerformed

    private void jButtonBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscaActionPerformed
        
        //Criando a tela e depois setando visivel e dispose na Home
        Buscar JanelaBuscar = new Buscar();
        JanelaBuscar.setVisible(true);
        dispose();
        //Mostrando a tela de carregando
        new Carregando();
    }//GEN-LAST:event_jButtonBuscaActionPerformed

    private void jButtonSairMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSairMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSairMouseClicked

    private void jButtonSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSairActionPerformed
        //Botão sair do sistema, que apos ser clicado verifica se realmente o usuario deseja sair. Se clicado em sim encerra o programa, não ele volta ao home
        int t = JOptionPane.showConfirmDialog(null, "Deseja realmente sair?", "Sair", JOptionPane.YES_NO_OPTION);
        if(t == 1){
            
        }else{
            System.exit(t);
        }
        
    }//GEN-LAST:event_jButtonSairActionPerformed

    private void jButtonRelatoriosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonRelatoriosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonRelatoriosMouseClicked

    private void jButtonRelatoriosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRelatoriosActionPerformed
        
        //Criando a tela e depois setando visivel e dispose na Home
        Relatorios telaRelatorios = new Relatorios();
        telaRelatorios.setVisible(true);
        this.dispose();
        //Mostrando a tela de carregando
        new Carregando();
    }//GEN-LAST:event_jButtonRelatoriosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBusca;
    private javax.swing.JButton jButtonCadastro;
    private javax.swing.JButton jButtonRelatorios;
    private javax.swing.JButton jButtonSair;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}