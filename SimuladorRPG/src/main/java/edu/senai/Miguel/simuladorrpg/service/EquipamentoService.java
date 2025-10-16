/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.senai.Miguel.simuladorrpg.service;

/**
 *
 * @author Java
 */
public class EquipamentoService {
public static List<equipamento> ObterEquipamento() {
List<equipamento> equpamento = new ArrayList();
for (EquipamentoEnum item : EquipamentoEnum.values)
Equipamento e = new Equipamento();
e.setNome(item.getNome());
e.setBonus(item.getBonus());
e.setTipo(item.getTipo());
equipamentos.add(e);
} 
return equipamentos;
}
