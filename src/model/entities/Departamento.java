package model.entities;

public class Departamento {

    private Integer id;
    private String nome;
    
    public Departamento(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    
    public Departamento(String nome) {
        this.nome = nome;
    }

    public Departamento() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

