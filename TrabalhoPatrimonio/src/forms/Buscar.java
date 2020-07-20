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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
public class Buscar extends javax.swing.JFrame {

    /**
     * Creates new form Buscar
     */
    //Variavel estatica para ir contando e acrescentar numeros no pdf para nunca repetir
    static int contador = 0;

    public Buscar() {
        initComponents();
        //Abrir tela no meio
        this.setLocationRelativeTo(null);
        //Chamando metodo que povoa a tabela com todos os dados no bd quando a tela for aberta
        lerJTable();
        //contandor recebe +1
        contador += 1;
        
    }
    //Metodo que limpa todos os campos
    public void limpaTextos() {
        jTxtNumPatrimonio.setText("");
        jTxtDescricao.setText("");
        jTxtSetor.setText("");
        jTxtResponsavel.setText("");
        jComboSituacao.setSelectedIndex(0);
        jComboEstadoConservacao.setSelectedIndex(0);
        jTxtNumPatrimonio.requestFocus();
    }
    //Metodo que povoa a tabela com todos os registros que tem no bd
    public void lerJTable() {
        //pegando o modelo
        DefaultTableModel model = (DefaultTableModel) jTableBuscar.getModel();
        try {
            //realizando a conexão com o bd
            Connection con = ConexaoMySQL.getInstance().getConnection();
            //Comando que será executado no bd
            String sql = "SELECT * FROM Register";
            //executando o comando e pegando o resultado
            ResultSet res = con.createStatement().executeQuery(sql);
            //setando o model numrows para 0, pois não deixa ficar povoando a tabelaa toda hora
            model.setNumRows(0);
            //While que pegara do banco e adiciona a tabela
            while (res.next()) {
                //Adicionando na tabela por meio do object
                ((DefaultTableModel) jTableBuscar.getModel()).addRow(new Object[]{
                    res.getInt("ID_Item"),
                    res.getString("NumeroPatrimonio"),
                    res.getString("Descricao"),
                    res.getString("Setor"),
                    res.getString("Responsavel"),
                    res.getString("Situacao"),
                    res.getString("EstadoConservacao")
                });
            }
            //Exceção
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
//gerador de PDF de todos os dados no bd
    public void gerarPDF() {
        //Definindo o nome do relatorio que sempre terá um numero diferente
        String nomeArquivo = "Relatorio_UFMS_TOTAL" + contador + ".pdf";
        contador += 1;
        //Parte onde o usuario escolhe o diretorio onde será salvo o pdf
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //Verificando se ele não anulou o chooser
        int retorno = fileChooser.showSaveDialog(null);
        if (retorno == JFileChooser.CANCEL_OPTION) {

        } else {
            
            File caminhoFile = fileChooser.getSelectedFile();
            //recebe o caminho do path e adiciona a string diretorio
            String diretorio = caminhoFile.getAbsolutePath();
            //verifica o SO para implementar a barra e concatenar o nomeArquivo
            String nomeSO = System.getProperty("os.name");
            if (nomeSO.contains("Linux")) {
                diretorio += "/" + nomeArquivo;
            } else {
                diretorio += "\\" + nomeArquivo;
            }
            //Criar o documento
            Document documento = new Document();
            //Conexão onde será pega os valores e inseridos no pdf
            try {
                //estabelecendo a conexão
                Connection con = ConexaoMySQL.getInstance().getConnection();
                //Comando sql que pega todos os dados do bd
                String sql = "SELECT * FROM Register;";
                //pegando o resultado do comando
                ResultSet res = con.createStatement().executeQuery(sql);
                //Criando o arquivo novo
                PdfWriter.getInstance(documento, new FileOutputStream(diretorio));
                //abrindo o arquivo
                documento.open();
                //"Criando" a imagem da UFMS
                Image imagem = Image.getInstance("ufms_logo_assinatura_horizontal_negativo.jpg");
                //Setando uma escala na imagem
                imagem.scaleToFit(200, 200);
                //Adicionando a imagem ao documento
                documento.add(imagem);
                //Criando paragrafos e adicionado-os
                Paragraph paragrafo = new Paragraph("Relatório de Todos Patrimonios");
                //Pula linha :)
                Paragraph paragrafo2 = new Paragraph(" ");
                //Criando a tabela com as 6 colunas
                PdfPTable tabela = new PdfPTable(6);
                //Adicionando os paragrafos que foram criados anteriormente
                documento.add(paragrafo);
                documento.add(paragrafo2);
                //Adicionado a tabela o que será o cabeçalho
                tabela.addCell("NumeroPatrimonio");
                tabela.addCell("Descrição");
                tabela.addCell("Setor");
                tabela.addCell("Responsável");
                tabela.addCell("Situação");
                tabela.addCell("Estado de Conservacao");
                while (res.next()) {
                    //Pegando os dados e inserindo no pdf em uma tabela
                    tabela.addCell(res.getString("NumeroPatrimonio"));
                    tabela.addCell(res.getString("Descricao"));
                    tabela.addCell(res.getString("Setor"));
                    tabela.addCell(res.getString("Responsavel"));
                    tabela.addCell(res.getString("Situacao"));
                    tabela.addCell(res.getString("EstadoConservacao"));
                }
                //Setando os tamanhos das tabelas
                tabela.setTotalWidth(590);
                tabela.setLockedWidth(true);
                tabela.setWidthPercentage(100f);
                //Adicionando a tabela ao documento
                documento.add(tabela);
                //fechando o documento
                documento.close();
                //Abrindo o documento com o leitor de pdf padrão
                Desktop.getDesktop().open(new File(diretorio));
                //Algumas exceções
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Buscar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(Buscar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Buscar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Buscar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
//gerador de PDF de dados unicos do bd
    //Diferente do gerarPDF, este ele gera relatorios a partir da busca que o usuario fez.
    public void gerarPDFUnique(String numeroPatrimonio, String descricao, String setor, String responsavel, String situacao, String estadoConservacao) throws IOException {
        //Criando o nomeArquivo e concatenando com o contador que nunca deixara ser igual
        String nomeArquivo = "Relatorio_UFMS" + contador + ".pdf";
        //Contando +1
        contador += 1;
        //Iniciando o chooser para o usuario escolher o diretorio e se ele cancelar nada acontece
        JFileChooser fileChooser = new JFileChooser();
        //setando somente diretorios
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //verificando se clicou em cancelar
        int retorno = fileChooser.showSaveDialog(null);
        if (retorno == JFileChooser.CANCEL_OPTION) {

        } else {
            File caminhoFile = fileChooser.getSelectedFile();
            //Criando diretorio e pegando o caminho
            String diretorio = caminhoFile.getAbsolutePath();
            //verificando qual so é para concatenar a barra
            String nomeSO = System.getProperty("os.name");
            if (nomeSO.contains("Linux")) {
                diretorio += "/" + nomeArquivo;
            } else {
                diretorio += "\\" + nomeArquivo;
            }
            //Criando o documento
            Document documento = new Document();

            try {
                //Conexão
                Connection con = ConexaoMySQL.getInstance().getConnection();
                //Comando do MYSQL
                String sql = "SELECT * FROM Register WHERE NumeroPatrimonio LIKE '" + numeroPatrimonio + "' OR Descricao LIKE '" + descricao + "' OR Setor LIKE '" + setor + "' OR Responsavel LIKE '" + responsavel + "' OR Situacao LIKE '" + situacao + "' OR EstadoConservacao LIKE '" + estadoConservacao + "';";
                //Pegando o resultado da execução
                ResultSet res = con.createStatement().executeQuery(sql);
                //Criando pdf com o diretorio
                PdfWriter.getInstance(documento, new FileOutputStream(diretorio));
                //abrindo o documento
                documento.open();
                //criando a imagem e colocando qual é
                Image imagem = Image.getInstance("ufms_logo_assinatura_horizontal_negativo.jpg");
                //setando a escala da imagem
                imagem.scaleToFit(200, 200);
                //adicionando a imagem ao documento
                documento.add(imagem);
                //Criandos 2 paragrafos 
                Paragraph paragrafo = new Paragraph("Relatório de Patrimonios");
                Paragraph paragrafo2 = new Paragraph(" ");
                //Criando a tabela com 6 colunas
                PdfPTable tabela = new PdfPTable(6);
                //adicionando os paragrafos ao documento
                documento.add(paragrafo);
                documento.add(paragrafo2);
                //Adicionando nomes as celulas que será o cabeçalho da tabela
                tabela.addCell("NumeroPatrimonio");
                tabela.addCell("Descrição");
                tabela.addCell("Setor");
                tabela.addCell("Responsável");
                tabela.addCell("Situação");
                tabela.addCell("Estado de Conservacao");
                while (res.next()) {
                    //PPegando os valores do bd e adicionando a tabela
                    tabela.addCell(res.getString("NumeroPatrimonio"));
                    tabela.addCell(res.getString("Descricao"));
                    tabela.addCell(res.getString("Setor"));
                    tabela.addCell(res.getString("Responsavel"));
                    tabela.addCell(res.getString("Situacao"));
                    tabela.addCell(res.getString("EstadoConservacao"));
                }
                //Setando valores ao tamanho da tabela
                tabela.setTotalWidth(590);
                tabela.setLockedWidth(true);
                tabela.setWidthPercentage(100f);
                //Adicionadno a tabela ao documento
                documento.add(tabela);
                //fechando documento
                documento.close();
                //Abrindo documento com leitor padrão do sistema
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
//Metodo que povoa a tabela com os dados que o usuario desejar vindo do bd
    public void lerJTableUnique(String numeroPatrimonio, String descricao, String setor, String responsavel, String situacao, String estadoConservacao) {
        //pegando modelo da tabela
        DefaultTableModel model = (DefaultTableModel) jTableBuscar.getModel();
        //setando valor 0 para não ir "somando" linhas na tabela e mostrar dados duplicados
        model.setNumRows(0);

        try {
            //Estabelecendo a conexão
            Connection con = ConexaoMySQL.getInstance().getConnection();
            //COmando para o banco de dados
            String sql = "SELECT * FROM Register WHERE NumeroPatrimonio LIKE '" + numeroPatrimonio + "' OR Descricao LIKE '" + descricao + "' OR Setor LIKE '" + setor + "' OR Responsavel LIKE '" + responsavel + "' OR Situacao LIKE '" + situacao + "' OR EstadoConservacao LIKE '" + estadoConservacao + "';";
            //pegando o resultado do comando
            ResultSet res = con.createStatement().executeQuery(sql);

            while (res.next()) {
                //Preenchendo a tabela com os dados
                ((DefaultTableModel) jTableBuscar.getModel()).addRow(new Object[]{
                    res.getInt("ID_Item"),
                    res.getString("NumeroPatrimonio"),
                    res.getString("Descricao"),
                    res.getString("Setor"),
                    res.getString("Responsavel"),
                    res.getString("Situacao"),
                    res.getString("EstadoConservacao")
                });
            }
            //capturando uma exceção
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    //pegando valor da linha que o usuario selecionou
    public void retornaValorLinha() {
        if (jTableBuscar.getSelectedRow() != -1) {
            jTxtNumPatrimonio.setText(jTableBuscar.getValueAt(jTableBuscar.getSelectedRow(), 1).toString());
            jTxtDescricao.setText(jTableBuscar.getValueAt(jTableBuscar.getSelectedRow(), 2).toString());
            jTxtSetor.setText(jTableBuscar.getValueAt(jTableBuscar.getSelectedRow(), 3).toString());
            jTxtResponsavel.setText(jTableBuscar.getValueAt(jTableBuscar.getSelectedRow(), 4).toString());
            if (jTableBuscar.getValueAt(jTableBuscar.getSelectedRow(), 5).toString().equals("Extraviado")) {
                jComboSituacao.setSelectedIndex(1);
            } else {
                jComboSituacao.setSelectedIndex(2);
            }
            if (jTableBuscar.getValueAt(jTableBuscar.getSelectedRow(), 6).toString().equals("Bem Conservado")) {
                jComboEstadoConservacao.setSelectedIndex(1);
            } else {
                jComboEstadoConservacao.setSelectedIndex(2);
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

        jPanel1 = new javax.swing.JPanel();
        jButtonBuscaVoltar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableBuscar = new javax.swing.JTable();
        jTxtSetor = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTxtResponsavel = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboSituacao = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jComboEstadoConservacao = new javax.swing.JComboBox<>();
        jTxtNumPatrimonio = new javax.swing.JTextField();
        jButtonEditar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTxtDescricao = new javax.swing.JTextField();
        jButtonRemover = new javax.swing.JButton();
        jButtonPDF = new javax.swing.JButton();
        jButtonLimpa = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(94, 90, 96));
        jPanel1.setBorder(null);

        jButtonBuscaVoltar.setBackground(new java.awt.Color(94, 90, 96));
        jButtonBuscaVoltar.setFont(new java.awt.Font("Abyssinica SIL", 1, 14)); // NOI18N
        jButtonBuscaVoltar.setForeground(new java.awt.Color(255, 255, 255));
        jButtonBuscaVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Voltar.png"))); // NOI18N
        jButtonBuscaVoltar.setText("Voltar");
        jButtonBuscaVoltar.setToolTipText("Voltar ao Menu Principal");
        jButtonBuscaVoltar.setBorder(null);
        jButtonBuscaVoltar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButtonBuscaVoltar.setFocusCycleRoot(true);
        jButtonBuscaVoltar.setFocusPainted(false);
        jButtonBuscaVoltar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonBuscaVoltar.setRequestFocusEnabled(false);
        jButtonBuscaVoltar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonBuscaVoltarMouseClicked(evt);
            }
        });
        jButtonBuscaVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscaVoltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonBuscaVoltar, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonBuscaVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(209, 209, 209));
        jPanel2.setBorder(null);
        jPanel2.setFocusable(false);

        jLabel1.setText("Número do Patrimônio");

        jButton1.setFont(new java.awt.Font("Noto Sans", 0, 12)); // NOI18N
        jButton1.setText("Buscar");
        jButton1.setToolTipText("Buscar Registro(s)");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTableBuscar.setAutoCreateRowSorter(true);
        jTableBuscar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Número do Patrimonio", "Descrição", "Setor", "Responsavel", "Situação", "Estado de Conservação"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableBuscarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableBuscar);

        jLabel2.setText("Setor");

        jLabel3.setText("Responsavel");

        jLabel4.setText("Situação");

        jComboSituacao.setMaximumRowCount(3);
        jComboSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Extraviado", "Não Extraviado" }));
        jComboSituacao.setToolTipText("");
        jComboSituacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboSituacaoActionPerformed(evt);
            }
        });

        jLabel5.setText("Estado de Conservação");

        jComboEstadoConservacao.setMaximumRowCount(3);
        jComboEstadoConservacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Bem Conservado", "Mal Conservado" }));

        jButtonEditar.setText("Editar");
        jButtonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarActionPerformed(evt);
            }
        });

        jLabel6.setText("Descrição");

        jButtonRemover.setText("Remover");
        jButtonRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoverActionPerformed(evt);
            }
        });

        jButtonPDF.setText("Gerar Relatorio");
        jButtonPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPDFActionPerformed(evt);
            }
        });

        jButtonLimpa.setText("Limpar Campos");
        jButtonLimpa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLimpaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButtonLimpa, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                            .addComponent(jTxtNumPatrimonio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jTxtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jTxtSetor, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jComboSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jComboEstadoConservacao, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 23, Short.MAX_VALUE))
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTxtSetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboEstadoConservacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtNumPatrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                    .addComponent(jButtonEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonRemover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonLimpa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonPDF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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

    private void jButtonBuscaVoltarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonBuscaVoltarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonBuscaVoltarMouseClicked

    private void jButtonBuscaVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscaVoltarActionPerformed
        //Botão que retorna a JanelaHome
        //Criando a janelaHome e depois setando ela como visivel e posterior utilizando o dispose para "fechar a relatorios"
        Home janelaHome = new Home();
        janelaHome.setVisible(rootPaneCheckingEnabled);
        dispose();
    }//GEN-LAST:event_jButtonBuscaVoltarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //Mostrando a tela de carregando
        new Carregando();
        //Verifica como esta os campos e direciona para qual preenchimento de tabela deve ser feito
        if (jTxtNumPatrimonio.getText().trim().equals("") && jTxtDescricao.getText().trim().equals("") && jTxtSetor.getText().trim().equals("") && jTxtResponsavel.getText().trim().equals("") && jComboSituacao.getSelectedIndex() == 0 && jComboEstadoConservacao.getSelectedIndex() == 0) {
            lerJTable();
        } else {
            lerJTableUnique(jTxtNumPatrimonio.getText(), jTxtDescricao.getText(), jTxtSetor.getText(), jTxtResponsavel.getText(), (String) jComboSituacao.getSelectedItem(), (String) jComboEstadoConservacao.getSelectedItem());
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboSituacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboSituacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboSituacaoActionPerformed

    private void jButtonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarActionPerformed
        //Mostra tela carregando
        new Carregando();
        //Iniciando variaveis para verificar os campos se estão preenchidos corretamente
        String numPatrimonioString = jTxtNumPatrimonio.getText().trim();
        String responsavel = jTxtResponsavel.getText().trim();
        String descricao = jTxtDescricao.getText().trim();
        String setor = jTxtSetor.getText().trim();
        //Abaixo verificação dos campos, alguns a partir de matches
        //Verifica se o campo está com o tamanho da string zerado se estiver mostra uma mensagem e seta o foco no campo em questão
        if (numPatrimonioString.length() == 0) {
            JOptionPane.showMessageDialog(null, "Numero Patrimonio é um campo obrigatorio e deve conter somente números");
            jTxtNumPatrimonio.requestFocus();
            jTxtNumPatrimonio.selectAll();
            return;
            //Verifica se o campo esta com o match correto e se não tiver set o foco nele e seleciona o texto total. Além de mostrar uma mensagem ao usuario
        } else if (!descricao.matches("[A-Z-ÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ][a-z-záàâãéèêíïóôõöúçñ]\\w*+ ([A-Z-ÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ]*[a-z-záàâãéèêíïóôõöúçñ]*\\w*+ )*[A-Z-ÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ]*[a-z-záàâãéèêíïóôõöúçñ]*\\w*\\d*+")) {
            JOptionPane.showMessageDialog(null, "Campo Descrição inválido!");
            jTxtDescricao.requestFocus();
            jTxtDescricao.selectAll();
            return;
            //Verifica se o campo esta atendendo o match, se não estiver mostra uma mensagem, seta o foco e seleciona o texto total.
        } else if (!setor.matches("[A-Z-ÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ][a-z-záàâãéèêíïóôõöúçñ]+( \\w*+[A-Z-ÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ]*[a-z-záàâãéèêíïóôõöúçñ]*+\\w*+ )*[A-Z-ÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ]*[a-z-záàâãéèêíïóôõöúçñ]*\\w*\\d*+")) {
            JOptionPane.showMessageDialog(null, "Campo Setor inválido!");
            jTxtSetor.requestFocus();
            jTxtSetor.selectAll();
            return;
            //Verifica se o campo esta atendendo o match, se não estiver mostra uma mensagem, seta o foco e seleciona o texto total.
        } else if (!responsavel.matches("[A-Z-ÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ][a-z-záàâãéèêíïóôõöúçñ]+ ([A-Z-ÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ]*[a-z-záàâãéèêíïóôõöúçñ]+ )*([A-Z-ÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ][a-z-záàâãéèêíïóôõöúçñ]+)")) {
            JOptionPane.showMessageDialog(null, "Campo Responsavel inválido!");
            jTxtResponsavel.requestFocus();
            jTxtResponsavel.selectAll();
            return;
            //Verifica se o usuario não selecionou nada, se sim mostra uma mensagem e seta o foco.
        }else if(jComboSituacao.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(null, "Campo Situação inválido!");
            jComboSituacao.requestFocus();
            return;
            //Verifica se o usuario não selecionou nada, se sim mostra uma mensagem e seta o foco.
        }else if(jComboEstadoConservacao.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(null, "Campo Estado de Conservação inválido!");
            jComboEstadoConservacao.requestFocus();
            return;
        }
        //Verifica se o usuario selecionou alguma linha
        if (jTableBuscar.getSelectedRow() != -1) {
            
            Item itens = new Item();
            
            int numPatrimonio = Integer.parseInt(jTxtNumPatrimonio.getText());
            //Setando valores a partir dos campos da gui
            itens.setNumeroPatrimonio(numPatrimonio);
            itens.setDescricao(jTxtDescricao.getText());
            itens.setSetor(jTxtSetor.getText());
            itens.setResponsavel(jTxtResponsavel.getText());
            itens.setSituacao((String) jComboSituacao.getSelectedItem());
            itens.setEstadoConservacao((String) jComboEstadoConservacao.getSelectedItem());
            itens.setId_Item((int) jTableBuscar.getValueAt(jTableBuscar.getSelectedRow(), 0));
            //chama o metodo que faz o update da linha
            ConexaoMySQL.getInstance().updateItem(itens);
            //preenche novamente a tabela com o metodo lerJTable
            lerJTable();

        }


    }//GEN-LAST:event_jButtonEditarActionPerformed

    private void jTableBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableBuscarMouseClicked
        //Chama o metodo que retorna o valor da linha da tabela quando clicada nela
        retornaValorLinha();

    }//GEN-LAST:event_jTableBuscarMouseClicked

    private void jButtonRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoverActionPerformed
        //mostra a tela carregando
        new Carregando();
        //verifica se ele não selecionou nenhuma linha
        //Se selecionou, quando clicado no botão remover sera removido a linha do banco de dados
        if (jTableBuscar.getSelectedRow() != -1) {
            
            Item itens = new Item();
            //Pega o ID da linha selecionada
            itens.setId_Item((int) jTableBuscar.getValueAt(jTableBuscar.getSelectedRow(), 0));
            //chama o metodo que remove a linha
            ConexaoMySQL.getInstance().removeItem(itens);
            //preenche a tabela novamente com o metodo lerJTable
            lerJTable();
            //Se não selecionar uma linha sera mostrada uma mensagem
        } else {
            JOptionPane.showMessageDialog(null, "Selecionar uma linha para remover!");
        }
    }//GEN-LAST:event_jButtonRemoverActionPerformed

    private void jButtonPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPDFActionPerformed
        //Mostra tela carregando
        new Carregando();
        //Verifica como os campos estão e direciona para qual pdf será gerado.
        //Chamando os metodos referente a cada um 
        if (jTxtNumPatrimonio.getText().trim().equals("") && jTxtDescricao.getText().trim().equals("") && jTxtSetor.getText().trim().equals("") && jTxtResponsavel.getText().trim().equals("") && jComboSituacao.getSelectedIndex() == 0 && jComboEstadoConservacao.getSelectedIndex() == 0) {
            gerarPDF();
        } else {
            try {
                gerarPDFUnique(jTxtNumPatrimonio.getText(), jTxtDescricao.getText(), jTxtSetor.getText(), jTxtResponsavel.getText(), (String) jComboSituacao.getSelectedItem(), (String) jComboEstadoConservacao.getSelectedItem());
            } catch (IOException ex) {
                Logger.getLogger(Buscar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_jButtonPDFActionPerformed

    private void jButtonLimpaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimpaActionPerformed
        //Chama o metodo para limpar os campos
        limpaTextos();
    }//GEN-LAST:event_jButtonLimpaActionPerformed

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
            java.util.logging.Logger.getLogger(Buscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Buscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Buscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Buscar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Buscar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonBuscaVoltar;
    private javax.swing.JButton jButtonEditar;
    private javax.swing.JButton jButtonLimpa;
    private javax.swing.JButton jButtonPDF;
    private javax.swing.JButton jButtonRemover;
    private javax.swing.JComboBox<String> jComboEstadoConservacao;
    private javax.swing.JComboBox<String> jComboSituacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableBuscar;
    private javax.swing.JTextField jTxtDescricao;
    private javax.swing.JTextField jTxtNumPatrimonio;
    private javax.swing.JTextField jTxtResponsavel;
    private javax.swing.JTextField jTxtSetor;
    // End of variables declaration//GEN-END:variables
}
