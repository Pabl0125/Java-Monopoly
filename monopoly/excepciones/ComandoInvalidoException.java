package monopoly.excepciones;

public class ComandoInvalidoException extends ComandoException {
    public ComandoInvalidoException(String mensaje) {
        super(mensaje);
    }
}