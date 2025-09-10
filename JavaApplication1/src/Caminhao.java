/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Java
 */
public class Caminhao extends Veiculo{
    private String Npneus;
    
    
    public Caminhao(String marca, String modelo) {
        super(marca, modelo);
    }

    // Implementação obrigatória e específica para a Motocicleta
    @Override
    public void emitirSom() {
        System.out.println(getModelo() + " faz: Vrummm!");
    }
    
     public String getNpneus () {
   return Npneus;
   }
    
    public void setInclinacao(String Npneus) {
    this.Npneus =  Npneus;
    }
}