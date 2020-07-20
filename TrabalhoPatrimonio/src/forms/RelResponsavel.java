/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import PrincipalPatrimonio.ConexaoMySQL;
import PrincipalPatrimonio.Item;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static forms.Buscar.contador;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author elton
 */
public class RelResponsavel extends javax.swing.JFrame {

    /**
     * Creates new form RelResponsavel
     */
    //Iniciando o contador
    static int contador = 0;
    
    public RelResponsavel() {
        initComponents();   
        //Abrir tela no meio
        this.setLocationRelativeTo(null);
        //somando contador
        contador += 1;
        //Setando o botão de gerar pdf em não usavel, pois somente após o click em buscar ele será usavel
        jButtonGerarPDFResponsavel.setEnabled(false);
    }
    //Metodo que Preenche a tabela a partir do responsavel
    public void lerJTableUnique(String responsavel) {
        //seetando ao model o modelo
        DefaultTableModel model = (DefaultTableModel) jTableResponsavel.getModel();
        //Não deixando duplicar as linhas;
        model.setNumRows(0);

        try {
            //Pegando conexão
            Connection con = ConexaoMySQL.getInstance().getConnection();
            //Adicionando o comando que será responsavel por retornar os valores para tabela
            String sql = "Select Responsavel, COUNT(Responsavel) from Register where Responsavel LIKE '" + responsavel + "'GROUP BY Responsavel;";
            //pegando o result
            ResultSet res = con.createStatement().executeQuery(sql);

            while (res.next()) {
                //Adicionando na tabela conforme a query do bd
                ((DefaultTableModel) jTableResponsavel.getModel()).addRow(new Object[]{
                    res.getString("Responsavel"),
                    res.getString("COUNT(Responsavel)")
                });
            }
            //capturando uma exceção
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    //gerador de pdf a partir do responsavel onde pegara da tabela da tela
    public void gerarPDFUnique(String responsavel) throws IOException {
        //Criando o nome do arquivo
        String nomeArquivo = "Relatorio_UFMS_Responsavel" + contador + ".pdf";
        //Somando o contador
        contador += 1;
        //Iniciando o chooser para o usuario escolher qual diretorio deseja salvar o pdf
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //verifica se ele não clicou em cancelar, se clicar nada acontece somente fecha a chooser
        int retorno = fileChooser.showSaveDialog(null);
        if (retorno == JFileChooser.CANCEL_OPTION) {

        } else {
            
            File caminhoFile = fileChooser.getSelectedFile();
            //pegando o diretorio
            String diretorio = caminhoFile.getAbsolutePath();
            //verificando qual o so para concatenar a barra
            String nomeSO = System.getProperty("os.name");
            if (nomeSO.contains("Linux")) {
                diretorio += "/" + nomeArquivo;
            } else {
                diretorio += "\\" + nomeArquivo;
            }
            //Criando documento
            Document documento = new Document();
            
            try {
                //Pegando a conexão
                Connection con = ConexaoMySQL.getInstance().getConnection();
                //Comando que sera executado no bd
                String sql = "Select Responsavel, COUNT(Responsavel) from Register where Responsavel LIKE '" + responsavel + "'GROUP BY Responsavel;";
                //pegando o result
                ResultSet res = con.createStatement().executeQuery(sql);
                //instaciando o documento para o pdf
                PdfWriter.getInstance(documento, new FileOutputStream(diretorio));
                //abrindo o documento
                documento.open();
                //Criando imagem que é da ufms
                Image imagem = Image.getInstance("ufms_logo_assinatura_horizontal_negativo.jpg");
                //setando a escala da imagem
                imagem.scaleToFit(200, 200);
                //adicionando a imagem ao documento
                documento.add(imagem);
                //criando dois paragrafos
                Paragraph paragrafo = new Paragraph("Relatório de Patrimonios");
                Paragraph paragrafo2 = new Paragraph(" ");
                //criando tabela com 2 colunas
                PdfPTable tabela = new PdfPTable(2);
                //adicionando os 2 paragrafos criados
                documento.add(paragrafo);
                documento.add(paragrafo2);
                //adicionando celula na tabela que sera o cabeçalho da tabela
                tabela.addCell("Responsável");
                tabela.addCell("Quantidade de Patrimonios");
                
                while (res.next()) {
                    //pegando os valores do banco e adicionando na tabela do pdf
                    tabela.addCell(res.getString("Responsavel"));
                    tabela.addCell(res.getString("COUNT(Responsavel)"));
                }
                //setando valores para as dimensões da tabela
                tabela.setTotalWidth(590);
                tabela.setLockedWidth(true);
                tabela.setWidthPercentage(100f);
                //adicionando a tabela ao documento
                documento.add(tabela);
                //fechando o documento
                documento.close();
                //abrindo o documento com o leitor padrão
                Desktop.getDesktop().open(new File(diretorio));
                //capturando algumas exceções
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Buscar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(Buscar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Buscar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableResponsavel = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButtonBuscaVoltarRel = new javax.swing.JButton();
        jButtonGerarPDFResponsavel = new javax.swing.JButton();
        jTxtResponsavel = new javax.swing.JTextField();
        jButtonBuscar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableResponsavel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Responsável", "Quantidade de Patrimonios"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableResponsavel);

        jLabel1.setText("Responsável");

        jButtonBuscaVoltarRel.setBackground(new java.awt.Color(94, 90, 96));
        jButtonBuscaVoltarRel.setFont(new java.awt.Font("Abyssinica SIL", 1, 14)); // NOI18N
        jButtonBuscaVoltarRel.setForeground(new java.awt.Color(255, 255, 255));
        jButtonBuscaVoltarRel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Voltar.png"))); // NOI18N
        jButtonBuscaVoltarRel.setText("Voltar");
        jButtonBuscaVoltarRel.setToolTipText("Voltar ao Menu Principal");
        jButtonBuscaVoltarRel.setBorder(null);
        jButtonBuscaVoltarRel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButtonBuscaVoltarRel.setFocusCycleRoot(true);
        jButtonBuscaVoltarRel.setFocusPainted(false);
        jButtonBuscaVoltarRel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonBuscaVoltarRel.setRequestFocusEnabled(false);
        jButtonBuscaVoltarRel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonBuscaVoltarRelMouseClicked(evt);
            }
        });
        jButtonBuscaVoltarRel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscaVoltarRelActionPerformed(evt);
            }
        });

        jButtonGerarPDFResponsavel.setBackground(new java.awt.Color(94, 90, 96));
        jButtonGerarPDFResponsavel.setFont(new java.awt.Font("Abyssinica SIL", 1, 14)); // NOI18N
        jButtonGerarPDFResponsavel.setForeground(new java.awt.Color(255, 255, 255));
        jButtonGerarPDFResponsavel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/RelatorioBranco.png"))); // NOI18N
        jButtonGerarPDFResponsavel.setText("Gerar PDF");
        jButtonGerarPDFResponsavel.setToolTipText("Voltar ao Menu Principal");
        jButtonGerarPDFResponsavel.setBorder(null);
        jButtonGerarPDFResponsavel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButtonGerarPDFResponsavel.setFocusCycleRoot(true);
        jButtonGerarPDFResponsavel.setFocusPainted(false);
        jButtonGerarPDFResponsavel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonGerarPDFResponsavel.setRequestFocusEnabled(false);
        jButtonGerarPDFResponsavel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonGerarPDFResponsavelMouseClicked(evt);
            }
        });
        jButtonGerarPDFResponsavel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGerarPDFResponsavelActionPerformed(evt);
            }
        });

        jButtonBuscar.setBackground(new java.awt.Color(94, 90, 96));
        jButtonBuscar.setFont(new java.awt.Font("Abyssinica SIL", 1, 14)); // NOI18N
        jButtonBuscar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Buscar.png"))); // NOI18N
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.setToolTipText("Voltar ao Menu Principal");
        jButtonBuscar.setBorder(null);
        jButtonBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButtonBuscar.setFocusCycleRoot(true);
        jButtonBuscar.setFocusPainted(false);
        jButtonBuscar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonBuscar.setRequestFocusEnabled(false);
        jButtonBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonBuscarMouseClicked(evt);
            }
        });
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonBuscaVoltarRel, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 205, Short.MAX_VALUE)
                        .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonGerarPDFResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jTxtResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBuscaVoltarRel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonGerarPDFResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBuscaVoltarRelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonBuscaVoltarRelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonBuscaVoltarRelMouseClicked

    private void jButtonBuscaVoltarRelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscaVoltarRelActionPerformed
        //Botão onde volta a tela de relatorios
        //Criando telarelatorios e tornando ela visivel, depois dando dispose na relresponsavel
        Relatorios telaRelatorios = new Relatorios();
        telaRelatorios.setVisible(rootPaneCheckingEnabled);
        dispose();
    }//GEN-LAST:event_jButtonBuscaVoltarRelActionPerformed

    private void jButtonGerarPDFResponsavelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonGerarPDFResponsavelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonGerarPDFResponsavelMouseClicked

    private void jButtonGerarPDFResponsavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGerarPDFResponsavelActionPerformed
        //gerando pdf com a chamada do metodo
        try {
            gerarPDFUnique(jTxtResponsavel.getText());
        } catch (IOException ex) {
            Logger.getLogger(RelResponsavel.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Mostra tela carregando
        new Carregando();
    }//GEN-LAST:event_jButtonGerarPDFResponsavelActionPerformed

    private void jButtonBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonBuscarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonBuscarMouseClicked

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        //Quando clicar em buscar verifica se o match corresponde, se não corresponder, aparecera uma mensagem e o foco ira para o campo
        String responsavel = jTxtResponsavel.getText().trim();
        if (!responsavel.matches("(\\%)*([A-Z-ÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ])*[a-z-záàâãéèêíïóôõöúçñ]+(\\%)*")) {
            JOptionPane.showMessageDialog(null, "Campo Responsavel inválido!");
            jTxtResponsavel.requestFocus();
            jTxtResponsavel.selectAll();
            return;
            //Se tudo ocorrer bem chama o metodo que preenche a tabela e habilita o botão gerar pdf
        }else{
            lerJTableUnique(jTxtResponsavel.getText());
            jButtonGerarPDFResponsavel.setEnabled(true);
        }
        
    }//GEN-LAST:event_jButtonBuscarActionPerformed

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
            java.util.logging.Logger.getLogger(RelResponsavel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RelResponsavel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RelResponsavel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RelResponsavel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RelResponsavel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscaVoltarRel;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonGerarPDFResponsavel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableResponsavel;
    private javax.swing.JTextField jTxtResponsavel;
    // End of variables declaration//GEN-END:variables
}
