package monopoly.interfaces;

import monopoly.excepciones.MonopolyException;

public interface Comando{
    
    void crearJugador(String nombre, String tipoAvatar) throws MonopolyException;

    void mostrarJugadorTurno() throws MonopolyException;

    void listarJugadores() throws MonopolyException;

    void listarEnVenta() throws MonopolyException;

    void listarEdificios() throws MonopolyException;

    void listarEdificiosPorGrupo(String grupoNombre) throws MonopolyException;

    void lanzarDados() throws MonopolyException;
    
    void lanzarDados(String tirada) throws MonopolyException;

    void acabarTurno() throws MonopolyException;

    void salirCarcel() throws MonopolyException;

    void descJugador(String nombre) throws MonopolyException;

    void descCasilla(String nombreCasilla) throws MonopolyException;

    void comprar(String nombreCasilla) throws MonopolyException;

    void venderEdificacion(String tipoEdificio, String nombreCasilla, String cantidadStr) throws MonopolyException;

    void mostrarEstadisticasJuego() throws MonopolyException;

    void mostrarEstadisticas(String jugador) throws MonopolyException;

    void edificar(String tipoEdificio) throws MonopolyException;

    void verTablero() throws MonopolyException;

    void hipotecarPropiedad(String casilla) throws MonopolyException;

    void deshipotecar(String casilla) throws MonopolyException;

    void proponerTrato(String jugadorDestino, String propiedadOfrecida, String propiedadSolicitada, String dineroOfrecido, String dineroSolicitado) throws MonopolyException;

    void aceptarTrato(String trato) throws MonopolyException;

    void listarTratos();

    void eliminarTrato(String stringTrato);
}