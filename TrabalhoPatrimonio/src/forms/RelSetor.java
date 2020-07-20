/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import PrincipalPatrimonio.ConexaoMySQL;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static forms.RelDescricao.contador;
import static forms.RelResponsavel.contador;
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
public class RelSetor extends javax.swing.JFrame {

    /**
     * Creates new form RelSetor
     */
    static int contador = 0;

    public RelSetor() {
        initComponents();
        //Abrir tela no meio
        this.setLocationRelativeTo(null);
        //somando contador
        contador += 1;
        //Setando o botão de gerar pdf em não usavel, pois somente após o click em buscar ele será usavel
        jButtonGerarPDFSetor.setEnabled(false);
    }
//Metodo que Preenche a tabela a partir do setor
    public void lerJTableUnique(String setor) {
        //setando ao model o modelo
        DefaultTableModel model = (DefaultTableModel) jTableSetor.getModel();
        //Não deixando duplicar as linhas;
        model.setNumRows(0);

        try {
            //Pegando conexão
            Connection con = ConexaoMySQL.getInstance().getConnection();
            //Adicionando o comando que será responsavel por retornar os valores para tabela
            String sql = "SELECT Setor, COUNT(Setor) FROM Register WHERE Setor LIKE '" + setor + "'GROUP BY Setor;";
            //pegando o result
            ResultSet res = con.createStatement().executeQuery(sql);
            
            while (res.next()) {
                //Adicionando na tabela conforme a query do bd
                ((DefaultTableModel) jTableSetor.getModel()).addRow(new Object[]{
                    res.getString("Setor"),
                    res.getString("COUNT(Setor)")
                });
            }
            //capturando exceção
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
//gerador de pdf a partir do setor onde pegara da tabela da tela
    public void gerarPDFUnique(String setor) throws IOException {
        //Criando o nome do arquivo
        String nomeArquivo = "Relatorio_UFMS_Setor" + contador + ".pdf";
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
                String sql = "Select Setor, COUNT(Setor) from Register where Setor LIKE '" + setor + "'GROUP BY Setor;";
                
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
                tabela.addCell("Setor");
                tabela.addCell("Quantidade de Patrimonios");

                while (res.next()) {
                    //pegando os valores do banco e adicionando na tabela do pdf
                    tabela.addCell(res.getString("Setor"));
                    tabela.addCell(res.getString("COUNT(Setor)"));
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

        jButtonGerarPDFSetor = new javax.swing.JButton();
        jButtonBuscaVoltarRel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSetor = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTxtSetor = new javax.swing.JTextField();
        jBuutonBuscar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButtonGerarPDFSetor.setBackground(new java.awt.Color(94, 90, 96));
        jButtonGerarPDFSetor.setFont(new java.awt.Font("Abyssinica SIL", 1, 14)); // NOI18N
        jButtonGerarPDFSetor.setForeground(new java.awt.Color(255, 255, 255));
        jButtonGerarPDFSetor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/RelatorioBranco.png"))); // NOI18N
        jButtonGerarPDFSetor.setText("Gerar PDF");
        jButtonGerarPDFSetor.setToolTipText("Voltar ao Menu Principal");
        jButtonGerarPDFSetor.setBorder(null);
        jButtonGerarPDFSetor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButtonGerarPDFSetor.setFocusCycleRoot(true);
        jButtonGerarPDFSetor.setFocusPainted(false);
        jButtonGerarPDFSetor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonGerarPDFSetor.setRequestFocusEnabled(false);
        jButtonGerarPDFSetor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonGerarPDFSetorMouseClicked(evt);
            }
        });
        jButtonGerarPDFSetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGerarPDFSetorActionPerformed(evt);
            }
        });

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

        jTableSetor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Setor", "Quantidade"
            }
        ));
        jScrollPane1.setViewportView(jTableSetor);

        jLabel1.setText("Setor");

        jBuutonBuscar.setBackground(new java.awt.Color(94, 90, 96));
        jBuutonBuscar.setFont(new java.awt.Font("Abyssinica SIL", 1, 14)); // NOI18N
        jBuutonBuscar.setForeground(new java.awt.Color(255, 255, 255));
        jBuutonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Buscar.png"))); // NOI18N
        jBuutonBuscar.setText("Buscar");
        jBuutonBuscar.setToolTipText("Voltar ao Menu Principal");
        jBuutonBuscar.setBorder(null);
        jBuutonBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBuutonBuscar.setFocusCycleRoot(true);
        jBuutonBuscar.setFocusPainted(false);
        jBuutonBuscar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jBuutonBuscar.setRequestFocusEnabled(false);
        jBuutonBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBuutonBuscarMouseClicked(evt);
            }
        });
        jBuutonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBuutonBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonBuscaVoltarRel, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 258, Short.MAX_VALUE)
                        .addComponent(jBuutonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonGerarPDFSetor, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jTxtSetor, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtSetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBuscaVoltarRel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonGerarPDFSetor, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBuutonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonGerarPDFSetorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonGerarPDFSetorMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonGerarPDFSetorMouseClicked

    private void jButtonGerarPDFSetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGerarPDFSetorActionPerformed
        //gerando pdf com a chamada do metodo
        try {
            gerarPDFUnique(jTxtSetor.getText());
        } catch (IOException ex) {
            Logger.getLogger(RelSetor.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Mostra tela carregando
        new Carregando();
    }//GEN-LAST:event_jButtonGerarPDFSetorActionPerformed

    private void jButtonBuscaVoltarRelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonBuscaVoltarRelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonBuscaVoltarRelMouseClicked

    private void jButtonBuscaVoltarRelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscaVoltarRelActionPerformed
        //Botão onde volta a tela de relatorios
        //Criando telarelatorios e tornando ela visivel, depois dando dispose na relresponsavel
        Relatorios telaRelatorios = new Relatorios();
        telaRelatorios.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButtonBuscaVoltarRelActionPerformed

    private void jBuutonBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBuutonBuscarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jBuutonBuscarMouseClicked

    private void jBuutonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBuutonBuscarActionPerformed
         //Quando clicar em buscar verifica se o match corresponde, se não corresponder, aparecera uma mensagem e o foco ira para o campo
        String setor = jTxtSetor.getText().trim();
        if (!setor.matches("(\\%)*([A-Z-ÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ])*[a-z-záàâãéèêíïóôõöúçñ]+(\\%)*")) {
            JOptionPane.showMessageDialog(null, "Campo Responsavel inválido!");
            jTxtSetor.requestFocus();
            jTxtSetor.selectAll();
            return;
            //Se tudo ocorrer bem chama o metodo que preenche a tabela e habilita o botão gerar pdf
        } else {
            lerJTableUnique(jTxtSetor.getText());
            jButtonGerarPDFSetor.setEnabled(true);
        }
    }//GEN-LAST:event_jBuutonBuscarActionPerformed

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
            java.util.logging.Logger.getLogger(RelSetor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RelSetor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RelSetor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RelSetor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RelSetor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscaVoltarRel;
    private javax.swing.JButton jButtonGerarPDFSetor;
    private javax.swing.JButton jBuutonBuscar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableSetor;
    private javax.swing.JTextField jTxtSetor;
    // End of variables declaration//GEN-END:variables
}
