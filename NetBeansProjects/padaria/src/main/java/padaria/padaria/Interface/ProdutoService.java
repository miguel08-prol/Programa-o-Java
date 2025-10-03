/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package padaria.padaria.Interface;

import java.util.ArrayList;
import java.util.List;
import padaria.padaria.Produto;

/**
 *
 * @author Java
 */
public class ProdutoService {
    
   public static List<Produto> inicializarProdutos(){
  List<Produto> produtos = new ArrayList<>();
  for (ProdutoEnum item : ProdutoEnum.values()) {
  Produto produto = new Produto();
  produto.setCodigo((int) item.getCodigo());
  produto.setDescricao(item.getDescricao());
  produto.setPreco((int) item.getPreco());
  produtos.add(produto);
  
}
       return produtos;
}
}