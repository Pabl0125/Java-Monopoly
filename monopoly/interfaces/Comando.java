package monopoly.interfaces;

public interface Comando{
    
    void crearJugador(String nombre, String tipoAvatar);    //Implementado

    void mostrarJugadorTurno();  //Implementado

    void listarJugadores(); //Implementado

    void listarEnVenta();   //Implementado

    void listarEdificios(); //Implementado

    void listarEdificiosPorGrupo(String grupoNombre);  //Implementado

    void lanzarDados(); //Implementado
    
    void lanzarDados(String tirada); //Implementado

    void acabarTurno(); //Implementado

    void salirCarcel();     //Implementado

    void descJugador(String nombre);    //Implementado

    void descCasilla(String nombreCasilla); //Implementado

    void comprar(String nombreCasilla); //Implementado

    void venderEdificacion(String tipoEdificio, String nombreCasilla, String cantidadStr);  //Implementado

    void mostrarEstadisticasJuego(); //Implementado

    void mostrarEstadisticas(String jugador);   //Implementado

    void edificar(String tipoEdificio);  //Implementado

    void verTablero();  //Implementado

    void hipotecarPropiedad(String casilla);   //Implementado

    void deshipotecar(String casilla);      //Implementado
}  