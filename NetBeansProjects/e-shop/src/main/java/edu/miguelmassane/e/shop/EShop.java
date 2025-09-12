/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


package edu.miguelmassane.e.shop;

import Produto.Produto;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Java
 */
public class EShop {

    public static void main(String[] args) throws IOException {
      
        Produto queijo = new Produto("Queijo",6, "Fedido");
        System.out.println("O produto que você comprou é: " + queijo.getNome());
        System.out.println("O preço do queijo é: " + queijo.getPreco());
        System.out.println("Descrição: " + queijo.getDescricao());
        System.out.println("\n");
        
        Produto maionese = new Produto("Maionese", 15, "branca");
        System.out.println("O produto que você comprou é: " + maionese.getNome());
        System.out.println("O preço da maionese é: " + maionese.getPreco());
        System.out.println("Descrição: " + maionese.getDescricao());
        System.out.println("\n");
        
        Produto alface = new Produto("Alface",200,"Azedo");
        System.out.println("O produto  que você comprou é:"+ alface.getNome());
        System.out.println("O preço do alface é: " + alface.getPreco());
        System.out.println("Descrição: " + alface.getDescricao());
        System.out.println("\n");
        
        Produto mostarda = new Produto("Mostarda",20,"amarela");
        System.out.println("O produto  que você comprou é:"+ mostarda.getNome());
        System.out.println("O preço do mostarda é: " + mostarda.getPreco());
        System.out.println("Descrição: " + mostarda.getDescricao());
        
        
        InputStreamReader isr = new InputStreamReader(System.in);BufferedReader reader = new BufferedReader(isr);
        
        
       int qtd;;
       qtd = Integer.parseInt(reader.readLine());
      System.out.println("venda:" + queijo.getNome() + " \n" + queijo.getPreco() + "X" +qtd+ "=" + (qtd*queijo.getPreco()));
      System.out.println("Produtos:"
       +"Nome:" + queijo.getNome()
       +"Preco:" + queijo.getPreco());
      
      
      
   
        int Produtos; 
        int Preco;
        String Descricao; 

        System.out.println("Digite o nome do produto:");
        String Produtos_Input = reader.readLine();
        
        System.out.println("Digite o preço:");
        String Preco_Input_String = reader.readLine();
        int Preco_Input = Integer.parseInt(Preco_Input_String);

        System.out.println("Digite a descrição:");
        String Descricao_Input = reader.readLine();
        
        System.out.println("\n--- Dados do Produto ---");
        System.out.println("Produto: " + Produtos_Input);
        System.out.println("Preço: " + Preco_Input);
        System.out.println("Descrição: " + Descricao_Input);

     
  
    
      
      
 
    }
}
