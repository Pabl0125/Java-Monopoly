package monopoly.interfaces;

public interface Comando{
    
    void crearJugador(String nombre, String tipoAvatar);    //Implementado

    void mostrarJugadorTurno();  //Implementado

    void listarJugadores(); //Implementado

    void listarEnVenta();   //Implementado

    void listarEdificios(); //Implementado

    void listarEdificiosPorGrupo(String grupoNombre);  //Implementado

    void lanzarDados();
    
    void lanzarDados(String tiradaForzada);

    void acabarTurno();

    void salirCarcel();

    void descJugador(String nombre);    //Implementado

    void descCasilla(String nombreCasilla); //Implementado

    void comprar(String nombreCasilla);

    void venderEdificacion(String tipo, String casilla, String cantidad);

    void mostrarEstadisticas();

    void mostrarEstadisticas(String nombreJugador);

    void edificar(String tipoEdificio);

    void verTablero();

    void hipotecar(String nombreCasilla);

    void deshipotecar(String nombreCasilla);
}