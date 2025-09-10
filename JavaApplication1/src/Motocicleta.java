//  HERANÇA
// A classe Motocicleta também herda de Veiculo.
public class Motocicleta extends Veiculo {
private String inclinacao;
    
    public Motocicleta(String marca, String modelo) {
        super(marca, modelo);
    }

    // Implementação obrigatória e específica para a Motocicleta
    @Override
    public void emitirSom() {
        System.out.println(getModelo() + " faz: Vrummm!");
    }
    
   public String getInclinacao () {
   return inclinacao;
   }
    
    public void setInclinacao(String inclinacao) {
    this.inclinacao =  inclinacao;
    }
}