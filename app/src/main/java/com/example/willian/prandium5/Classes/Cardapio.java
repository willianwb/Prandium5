package com.example.willian.prandium5.Classes;

public class Cardapio {
    private String KeyProduto;
    private String KeyLojista;
    private String nomePrato;
    private String urlImagem;
    private String descricao;
    private Float precoProduto;


    public String getKeyProduto() {
        return KeyProduto;
    }

    public void setKeyProduto(String keyProduto) {
        KeyProduto = keyProduto;
    }

    public String getKeyLojista() {
        return KeyLojista;
    }

    public void setKeyLojista(String keyLojista) {
        KeyLojista = keyLojista;
    }

    public String getNomePrato() {
        return nomePrato;
    }

    public void setNomePrato(String nomePrato) {
        this.nomePrato = nomePrato;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Float getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(Float precoProduto) {
        this.precoProduto = precoProduto;
    }
}
