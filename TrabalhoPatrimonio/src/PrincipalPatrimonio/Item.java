/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PrincipalPatrimonio;

/**
 *
 * @author elton
 */
//Criação da clase do Item com o campos especificados no projeto
public class Item {
    private int id_Item;
    private int numeroPatrimonio;
    private String descricao;
    private String setor;
    private String responsavel;
    private String situacao;
    private String estadoConservacao;

    public int getId_Item() {
        return id_Item;
    }

    public void setId_Item(int id_Item) {
        this.id_Item = id_Item;
    }
    
    public int getNumeroPatrimonio() {
        return numeroPatrimonio;
    }

    public void setNumeroPatrimonio(int numeroPatrimonio) {
        this.numeroPatrimonio = numeroPatrimonio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getEstadoConservacao() {
        return estadoConservacao;
    }

    public void setEstadoConservacao(String estadoConservacao) {
        this.estadoConservacao = estadoConservacao;
    }

    


    
}
