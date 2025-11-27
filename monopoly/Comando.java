package monopoly.interfaces;

public interface Comando{
    
    void crearJugador(String nombre, String tipoAvatar);

    void mostrarJugadorTurno();

    void listarJugadores();

    void listarEnVenta();

    void listarEdificios();

    void listarEdificios(String grupo);

    void lanzarDados();
    
    void lanzarDados(String tiradaForzada);

    void acabarTurno();

    void salirCarcel();

    void describirJugador(String nombre);

    void describirCasilla(String nombreCasilla);

    void comprar(String nombreCasilla);

    void venderEdificacion(String tipo, String casilla, String cantidad);

    void mostrarEstadisticas();

    void mostrarEstadisticas(String nombreJugador);

    void edificar(String tipoEdificio);

    void verTablero();

    void hipotecar(String nombreCasilla);

    void deshipotecar(String nombreCasilla);
}