package com.example.willian.prandium5.Classes;

import com.google.firebase.database.Exclude;

public class Usuario {
    private String email;
    private String senha;
    private String nome;
    private Float saldo;
    private String CPF;
    private String ID_TIPO;
    private String keyUsuario;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    @Exclude
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getID_TIPO() {
        return ID_TIPO;
    }

    public void setID_TIPO(String ID_TIPO) {
        this.ID_TIPO = ID_TIPO;
    }

    public Float getSaldo() {
        return saldo;
    }
    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }


    public String getKeyUsuario() {
        return keyUsuario;
    }

    public void setKeyUsuario(String keyUsuario) {
        this.keyUsuario = keyUsuario;
    }
}


