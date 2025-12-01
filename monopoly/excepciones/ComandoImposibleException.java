package monopoly.excepciones;


public class ComandoImposibleException extends ComandoException {
    public ComandoImposibleException(String mensaje) {
        super(mensaje);
    }
}