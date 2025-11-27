package monopoly.excepciones;


public class ComandoImposible extends Exception {
    public ComandoImposible(String mensaje) {
        super(mensaje + " Comando Imposible.");
    }
}