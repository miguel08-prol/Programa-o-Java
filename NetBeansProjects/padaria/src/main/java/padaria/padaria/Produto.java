/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package padaria.padaria;

/**
 *
 * @author Java
 */

public class Produto {

    private int codigo;


    private int preco;
    private String descricao;
    private int saldoEstoque;

    public Produto(int codigo, String descricao) {
        this.codigo = codigo;
        this.preco = preco;
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Produto{" + "codigo=" + codigo + ", descricao=" + descricao + '}';
    }

    public Produto() {
        
    }
    
    public int getCodigo() {
        return codigo;
    }


    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

       public int getSaldoEstoque() {
        return saldoEstoque;
    }

    public void setSaldoEstoque(int saldoEstoque) {
        this.saldoEstoque = saldoEstoque;
    }
}