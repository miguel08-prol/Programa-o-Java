package edu.senai.Miguel.simuladorrpg.services;

import edu.senai.Miguel.simuladorrpg.classes.Equipamento;
import edu.senai.Miguel.simuladorrpg.model.EquipamentoEnum;
import java.util.ArrayList;
import java.util.List;

public class EquipamentoService {
    public static List<Equipamento> ObterEquipamentos() {
        List<Equipamento> equipamentos = new ArrayList<>();
        for (EquipamentoEnum item : EquipamentoEnum.values()) {
            Equipamento e = new Equipamento();
            e.setNome(item.getNome());
            e.setBonus(item.getBonus());
            e.setTipo(e.getTipo());
            equipamentos.add(e);
        }        
        return equipamentos;
    }
}
