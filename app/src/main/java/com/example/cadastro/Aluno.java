package com.example.cadastro;

// interface serializable para permitir a serialização dos objetos Aluno
import java.io.Serializable;


public class Aluno implements Serializable { // classe aluno


    // declaração dos campos de dados do aluno
    private Integer id;

    private String nome;
    private String cpf;
    private String telefone;

    public Integer getId() {
        return id;
    }
    // método getter and setter
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override

    public String toString (){
        return nome;
    }



}
