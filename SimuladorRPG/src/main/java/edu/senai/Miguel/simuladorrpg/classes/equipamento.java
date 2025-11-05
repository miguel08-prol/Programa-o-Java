package edu.senai.Miguel.simuladorrpg.classes;

import edu.senai.Miguel.simuladorrpg.model.TipoEquipamento;

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
    

}
