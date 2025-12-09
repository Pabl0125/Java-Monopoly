package monopoly.interfaces;

import monopoly.excepciones.*;

public interface Comando{
    
    void crearJugador(String nombre, String tipoAvatar) throws ComandoImposibleException;

    void mostrarJugadorTurno();

    void listarJugadores();

    void listarEnVenta();

    void listarEdificios() throws ComandoImposibleException;

    void listarEdificiosPorGrupo(String grupoNombre) throws ComandoImposibleException;

    void lanzarDados() throws AccionInvalidaException;
    
    void lanzarDados(String tirada) throws AccionInvalidaException;

    void acabarTurno() throws ComandoImposibleException;

    void salirCarcel() throws ComandoImposibleException, DineroInsuficienteException;

    void descJugador(String nombre);

    void descCasilla(String nombreCasilla) throws ComandoImposibleException;

    void comprar(String nombreCasilla) throws ComandoImposibleException, DineroInsuficienteException;

    void venderEdificacion(String tipoEdificio, String nombreCasilla, String cantidadStr) throws ComandoImposibleException, ArgumentosComandoException;

    void mostrarEstadisticasJuego();

    void mostrarEstadisticas(String jugador) throws ComandoImposibleException;

    void edificar(String tipoEdificio) throws ConstruccionException, DineroInsuficienteException;

    void verTablero();

    void hipotecarPropiedad(String casilla) throws ComandoImposibleException;

    void deshipotecar(String casilla) throws ComandoImposibleException, DineroInsuficienteException;

    void proponerTrato(String jugadorDestino, String propiedadOfrecida, String propiedadSolicitada, String dineroOfrecido, String dineroSolicitado) throws TratoException, ComandoImposibleException;

    void aceptarTrato(String trato) throws ComandoImposibleException, DineroInsuficienteException;

    void listarTratos();

    void eliminarTrato(String stringTrato) throws ComandoImposibleException;
}