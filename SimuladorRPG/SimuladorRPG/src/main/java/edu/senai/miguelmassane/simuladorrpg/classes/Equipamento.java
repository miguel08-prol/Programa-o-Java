/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.senai.miguelmassane.simuladorrpg.classes;

import edu.senai.miguelmassane.simuladorrpg.model.TipoEquipamento;
/**
 *
 * @author Java_Motta
 */
public class Equipamento {
    private String nome; 
    private int bonus; 
    private TipoEquipamento tipo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public TipoEquipamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoEquipamento tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return  nome + " +" + bonus;
    }
    
}
