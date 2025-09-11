/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


package edu.miguelmassane.e.shop;

import Produto.Produto;

/**
 *
 * @author Java
 */
public class EShop {

    public static void main(String[] args) {
     
        Produto queijo = new Produto("queijo", 6, "fedido");
        System.out.println("O produto que você comprou é: " + queijo.getNome());
        System.out.println("O preço do queijo é: " + queijo.getPreco());
        System.out.println("Descrição: " + queijo.getDescricao());
        
       
    }
}
