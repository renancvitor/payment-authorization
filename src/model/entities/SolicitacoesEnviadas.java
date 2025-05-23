package model.entities;

import java.sql.Timestamp;
import java.util.Date;

public class SolicitacoesEnviadas {
	
	private Integer id;
    private String fornecedor;
    private String descricao;
    private Timestamp dataCriacao;
    private Date dataPagamento;
    private String formaPagamento;
    private double valorTotal;
    private int idUsuario;
    private StatusSolicitacao status;
    private String login;

    public SolicitacoesEnviadas(Integer id, String fornecedor, String descricao, Timestamp dataCriacao,
                                Date dataPagamento, String formaPagamento, double valorTotal,
                                int idUsuario, StatusSolicitacao status, String login) {
        this.id = id;
        this.fornecedor = fornecedor;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.dataPagamento = dataPagamento;
        this.formaPagamento = formaPagamento;
        this.valorTotal = valorTotal;
        this.idUsuario = idUsuario;
        this.status = status;
        this.login = login;
    }

    public SolicitacoesEnviadas() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Timestamp getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Timestamp dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public java.sql.Date getDataPagamento() {
        return (java.sql.Date) dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}