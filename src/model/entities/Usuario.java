package model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {
    private Integer id;
    private String login;
    private String senha;
    private String cpf;
    private UserType userType;
    private List<String> permissoes;

    public Usuario(Integer id, String login, String senha, List<String> permissoes, String cpf, int idTipoUsuario, UserType userType) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.permissoes = permissoes;
        this.cpf = cpf;
        this.userType = userType;
    }
    
    public Usuario(String login, String senha, String cpf, UserType userType) {
    	this.login = login;
    	this.senha = senha;
    	this.cpf = cpf;
        this.userType = userType;
    }

    public Usuario() {
    }

    public boolean hasPermission(List<String> permissao) {
        return permissoes.contains(permissao);
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public String getCpf() {
        return cpf;
    }

    public UserType getUserType() {
        return userType;
    }

    public List<String> getPermissoes() {
        return permissoes;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    
    public void setCpf(String cpf) {
    	this.cpf = cpf;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario usuario = (Usuario) obj;
        return login.equals(usuario.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    public void adicionarPermissao(String permissao) {
        if (this.permissoes == null) {
            this.permissoes = new ArrayList<>();
        }
        this.permissoes.add(permissao);
    }
}
