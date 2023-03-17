package com.example.trabalho1.model;

public class Registro {
    private String nome;
    private int idade;
    private String sexo;
    private long contacto;
    private String horario;


    public Registro() {
    }

    public Registro(String nome, int idade, String sexo, long contacto, String horario) {
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
        this.contacto = contacto;
        this.horario = horario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public long getContacto() {
        return contacto;
    }

    public void setContacto(long contacto) {
        this.contacto = contacto;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
