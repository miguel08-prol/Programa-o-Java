package edu.senai.Miguel.simuladorrpg.classes;

import edu.senai.Miguel.simuladorrpg.model.TipoEquipamento;
import java.util.List;

public class Jogador {

    private String nome;
    private int nivel;
    private Equipamento cabeca;
    private Equipamento armadura;
    private Equipamento calcado;
    private Equipamento mao;
    private List<Equipamento> inventario;

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

    public Equipamento getCabeca() {
        return cabeca;
    }

    public boolean setCabeca(Equipamento cabeca) {
        if (cabeca.getTipo() == TipoEquipamento.CABECAL) {
            this.cabeca = cabeca;
            return true;
        } else {
            return false;
        }
    }

    public Equipamento getArmadura() {
        return armadura;
    }

    public boolean setArmadura(Equipamento armadura) {
        if (armadura.getTipo() == TipoEquipamento.ARMADURA) {
            this.armadura = armadura;
            return true;
        } else {
            return false;
        }
    }

    public Equipamento getCalcado() {
        return calcado;
    }

    public boolean setCalcado(Equipamento calcado) {
        if (calcado.getTipo() == TipoEquipamento.CALCADO) {
            this.calcado = calcado;
            return true;
        } else {
            return false;
        }
    }

    public Equipamento getMao() {
        return mao;
    }

    public boolean setMao(Equipamento mao) {
        if (mao.getTipo() == TipoEquipamento.MAO) {
            this.mao = mao;
            return true;
        } else {
            return false;
        }
    }

    public List<Equipamento> getInventario() {
        return inventario;
    }

    public void setInventario(List<Equipamento> inventario) {
        this.inventario = inventario;
    }

}
