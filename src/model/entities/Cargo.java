package model.entities;

public class Cargo {

    private int id;
    private String nome;

    public Cargo(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    
    public Cargo(String nome) {
        this.nome = nome;
    }

    public Cargo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
