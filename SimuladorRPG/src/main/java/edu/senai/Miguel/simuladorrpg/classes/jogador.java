/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.senai.Miguel.simuladorrpg.classes;

import edu.senai.Miguel.simuladorrpg.model.TipoEquipamento;
import java.util.List;


/**
 *
 * @author Java
 */
public class jogador {
    private String nome;
    private int nivel;
    private equipamento cabeca;
    private equipamento corpo;
    private equipamento pes;
    private equipamento mao;
    private equipamento ItemgGeral;
    private List<equipamento> inventario;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public equipamento getCabeca() {
        return cabeca;
    }

    public boolean setCabeca(equipamento cabeca) {
      if(cabeca.getTipo() == TipoEquipamento.CABECAL) {
      this.cabeca = cabeca;
      return true;}else {return false;}
    }

    public equipamento getCorpo() {
        return corpo;
    }

    public boolean setCorpo(equipamento corpo) {
     if(corpo.getTipo() == TipoEquipamento.ARMADURA) {
     this.corpo = corpo;
     return true;}else {return false;}
    }

    public equipamento getPes() {
        return pes;
    }

    public boolean setPes(equipamento pes) {
    if(pes.getTipo() == TipoEquipamento.CALCADO) {
    this.pes = pes;
    return true;}else {return false;}
    }

    public equipamento getMao() {
        return mao;
    }

    public boolean setMao(equipamento mao) {
    if(mao.getTipo() == TipoEquipamento.MAO) {
    this.mao = mao;
    return true;}else {return false;}
    }

    public equipamento getItemgGeral() {
        return ItemgGeral;
    }

    public boolean setItemgGeral(equipamento ItemgGeral) {
    if(ItemgGeral.getTipo() == TipoEquipamento.ITEM_GERAL) {
    this.ItemgGeral = ItemgGeral;
    return true;}else {return false;}
    }
}


