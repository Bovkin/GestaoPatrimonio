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
public class RelDescricao extends javax.swing.JFrame {

    /**
     * Creates new form RelDescricao
     */
    static int contador = 0;

    public RelDescricao() {

        initComponents();
        //Abrir tela no meio
        this.setLocationRelativeTo(null);
        //somando contador
        contador += 1;
        //Torna o botão gerar pdf não usavel
        jButtonGerarPDFDescricao.setEnabled(false);
    }
//Metodo que preenche a tabela após a busca a partir da descrição
    public void lerJTableUnique(String descricao) {
        //setando ao model o modelo
        DefaultTableModel model = (DefaultTableModel) jTableDescricao.getModel();
        //Não deixando duplicar as linhas;
        model.setNumRows(0);
        
        try {
            //Pegando conexão
            Connection con = ConexaoMySQL.getInstance().getConnection();
            //Adicionando o comando que será responsavel por retornar os valores para tabela
            String sql = "SELECT Descricao, COUNT(Descricao) FROM Register WHERE Descricao LIKE '" + descricao + "'GROUP BY Descricao;";
            //pegando o result
            ResultSet res = con.createStatement().executeQuery(sql);

            while (res.next()) {
                //Adicionando na tabela conforme a query do bd
                ((DefaultTableModel) jTableDescricao.getModel()).addRow(new Object[]{
                    res.getString("Descricao"),
                    res.getString("COUNT(Descricao)")
                });
            }
            //capturando exceção
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
//gerador de pdf a partir do descricao onde pegara da tabela da tela
    public void gerarPDFUnique(String descricao) throws IOException {
        //Criando o nome do arquivo
        String nomeArquivo = "Relatorio_UFMS_Descricao" + contador + ".pdf";
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
                String sql = "Select Descricao, COUNT(Descricao) from Register where Descricao LIKE '" + descricao + "'GROUP BY Descricao;";
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
                tabela.addCell("Descrição");
                tabela.addCell("Quantidade de Patrimonios");

                while (res.next()) {
                    //pegando os valores do banco e adicionando na tabela do pdf
                    tabela.addCell(res.getString("Descricao"));
                    tabela.addCell(res.getString("COUNT(Descricao)"));
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
        jTableDescricao = new javax.swing.JTable();
        jButtonGerarPDFDescricao = new javax.swing.JButton();
        jButtonBuscaVoltarRel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTxtDescricao = new javax.swing.JTextField();
        jButtonBuscarRelDescricao = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableDescricao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Descrição", "Quantidade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableDescricao);

        jButtonGerarPDFDescricao.setBackground(new java.awt.Color(94, 90, 96));
        jButtonGerarPDFDescricao.setFont(new java.awt.Font("Abyssinica SIL", 1, 14)); // NOI18N
        jButtonGerarPDFDescricao.setForeground(new java.awt.Color(255, 255, 255));
        jButtonGerarPDFDescricao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/RelatorioBranco.png"))); // NOI18N
        jButtonGerarPDFDescricao.setText("Gerar PDF");
        jButtonGerarPDFDescricao.setToolTipText("Voltar ao Menu Principal");
        jButtonGerarPDFDescricao.setBorder(null);
        jButtonGerarPDFDescricao.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButtonGerarPDFDescricao.setFocusCycleRoot(true);
        jButtonGerarPDFDescricao.setFocusPainted(false);
        jButtonGerarPDFDescricao.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonGerarPDFDescricao.setRequestFocusEnabled(false);
        jButtonGerarPDFDescricao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonGerarPDFDescricaoMouseClicked(evt);
            }
        });
        jButtonGerarPDFDescricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGerarPDFDescricaoActionPerformed(evt);
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

        jLabel1.setText("Descrição");

        jButtonBuscarRelDescricao.setBackground(new java.awt.Color(94, 90, 96));
        jButtonBuscarRelDescricao.setFont(new java.awt.Font("Abyssinica SIL", 1, 14)); // NOI18N
        jButtonBuscarRelDescricao.setForeground(new java.awt.Color(255, 255, 255));
        jButtonBuscarRelDescricao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Buscar.png"))); // NOI18N
        jButtonBuscarRelDescricao.setText("Buscar");
        jButtonBuscarRelDescricao.setToolTipText("Voltar ao Menu Principal");
        jButtonBuscarRelDescricao.setBorder(null);
        jButtonBuscarRelDescricao.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButtonBuscarRelDescricao.setFocusCycleRoot(true);
        jButtonBuscarRelDescricao.setFocusPainted(false);
        jButtonBuscarRelDescricao.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonBuscarRelDescricao.setRequestFocusEnabled(false);
        jButtonBuscarRelDescricao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonBuscarRelDescricaoMouseClicked(evt);
            }
        });
        jButtonBuscarRelDescricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarRelDescricaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonBuscaVoltarRel, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonBuscarRelDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonGerarPDFDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jTxtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBuscaVoltarRel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonGerarPDFDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonBuscarRelDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonGerarPDFDescricaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonGerarPDFDescricaoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonGerarPDFDescricaoMouseClicked

    private void jButtonGerarPDFDescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGerarPDFDescricaoActionPerformed
        //gerando pdf com a chamada do metodo
        try {
            gerarPDFUnique(jTxtDescricao.getText());
        } catch (IOException ex) {
            Logger.getLogger(RelDescricao.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Mostra tela carregando
        new Carregando();
    }//GEN-LAST:event_jButtonGerarPDFDescricaoActionPerformed

    private void jButtonBuscaVoltarRelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonBuscaVoltarRelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonBuscaVoltarRelMouseClicked

    private void jButtonBuscaVoltarRelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscaVoltarRelActionPerformed
        Relatorios telaRelatorios = new Relatorios();
        telaRelatorios.setVisible(rootPaneCheckingEnabled);
        dispose();
        new Carregando();
    }//GEN-LAST:event_jButtonBuscaVoltarRelActionPerformed

    private void jButtonBuscarRelDescricaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonBuscarRelDescricaoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonBuscarRelDescricaoMouseClicked

    private void jButtonBuscarRelDescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarRelDescricaoActionPerformed
        //Quando clicar em buscar verifica se o match corresponde, se não corresponder, aparecera uma mensagem e o foco ira para o campo
        String descricao = jTxtDescricao.getText().trim();
        if (!descricao.matches("(\\%)*([A-Z-ÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ])*[a-z-záàâãéèêíïóôõöúçñ]+(\\%)*")) {
            JOptionPane.showMessageDialog(null, "Campo Responsavel inválido!");
            jTxtDescricao.requestFocus();
            jTxtDescricao.selectAll();
            return;
            //Se tudo ocorrer bem chama o metodo que preenche a tabela e habilita o botão gerar pdf
        } else {
            lerJTableUnique(jTxtDescricao.getText());
            jButtonGerarPDFDescricao.setEnabled(true);
        }
    }//GEN-LAST:event_jButtonBuscarRelDescricaoActionPerformed

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
            java.util.logging.Logger.getLogger(RelDescricao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RelDescricao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RelDescricao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RelDescricao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RelDescricao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscaVoltarRel;
    private javax.swing.JButton jButtonBuscarRelDescricao;
    private javax.swing.JButton jButtonGerarPDFDescricao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableDescricao;
    private javax.swing.JTextField jTxtDescricao;
    // End of variables declaration//GEN-END:variables
}
