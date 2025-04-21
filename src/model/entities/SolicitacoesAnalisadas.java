package model.entities;

import java.sql.Timestamp;
import java.util.Date;

public class SolicitacoesAnalisadas {
    private Integer id;
    private String fornecedor;
    private String descricao;
    private Timestamp data_criacao;
    private Date data_pagamento;
    private String forma_pagamento;
    private double valor_total;
    private int id_usuario;
    private StatusSolicitacao status;
    private String login;

    public SolicitacoesAnalisadas(Integer id, String fornecedor, String descricao,
                                  Timestamp data_criacao, Date data_pagamento, String forma_pagamento,
                                  double valor_total, int id_usuario, StatusSolicitacao status, String login) {
        this.id = id;
        this.fornecedor = fornecedor;
        this.descricao = descricao;
        this.data_criacao = data_criacao;
        this.data_pagamento = data_pagamento;
        this.forma_pagamento = forma_pagamento;
        this.valor_total = valor_total;
        this.id_usuario = id_usuario;
        this.status = status;
        this.login = login;
    }

    public SolicitacoesAnalisadas() {
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
        return data_criacao;
    }

    public void setDataCriacao(Timestamp dataCriacao) {
        this.data_criacao = dataCriacao;
    }

    public java.sql.Date getDataPagamento() {
        return (java.sql.Date) data_pagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.data_pagamento = dataPagamento;
    }

    public String getFormaPagamento() {
        return forma_pagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.forma_pagamento = formaPagamento;
    }

    public double getValorTotal() {
        return valor_total;
    }

    public void setValorTotal(double valorTotal) {
        this.valor_total = valorTotal;
    }

    public int getIdUsuario() {
        return id_usuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.id_usuario = idUsuario;
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