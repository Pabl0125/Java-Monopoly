package monopoly;

import java.util.ArrayList;
import partida.*;

public class Menu {

    //Atributos
    private ArrayList<Jugador> jugadores; //Jugadores de la partida.
    private ArrayList<Avatar> avatares; //Avatares en la partida.
    private int turno = 0; //Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    private int lanzamientos; //Variable para contar el número de lanzamientos de un jugador en un turno.
    private Tablero tablero; //Tablero en el que se juega.
    private Dado dado1; //Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    private Jugador banca; //El jugador banca.
    private boolean tirado; //Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean solvente; //Booleano para comprobar si el jugador que tiene el turno es solvente, es decir, si ha pagado sus deudas.


    // Método para inciar una partida: crea los jugadores y avatares.
    private void iniciarPartida() {
        //Crear jugadores y avatares.
        jugadores = new ArrayList<Jugador>();
        avatares = new ArrayList<Avatar>();
        banca = new Jugador();
        jugadores.add(banca);

    }

    
    /*Método que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetro: cadena de caracteres (el comando).
    */
    private void analizarComando(String comando) {
    }

    /*Método que realiza las acciones asociadas al comando 'describir jugador'.
    * Parámetro: comando introducido
     */
    private void descJugador(String nombre) {
        int encontrado = 0;
        for (Jugador j:jugadores){
            if (j.getNombre().equals(nombre)){
                System.out.println(j.toString());
                encontrado = 1;
                break;
            }
        }
        if (encontrado == 0){
            System.out.println("No se encontro ningun jugador con el nombre '" + nombre + "'.");
        }
    }

    /*Método que realiza las acciones asociadas al comando 'describir avatar'.
    * Parámetro: id del avatar a describir.
    */
    private void descAvatar(String ID) {
    }

    /* Método que realiza las acciones asociadas al comando 'describir nombre_casilla'.
    * Parámetros: nombre de la casilla a describir.
    */
    private void descCasilla(String nombre) {
    }

    //Método que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    private void lanzarDados() {
    }

    /*Método que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {
    }

    //Método que ejecuta todas las acciones relacionadas con el comando 'salir carcel'. 
    private void salirCarcel() {
    }

    // Método que realiza las acciones asociadas al comando 'listar enventa'.
    private void listarVenta() {
        for (ArrayList<Casilla> casilla : tablero.getPosiciones()) {
            for (Casilla c : casilla) {
                ///
            }
        }

        

    }

    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {
        if(jugadores == null || jugadores.isEmpty()){
            System.out.println("No hay jugadores en la partida.");
            return;
        }
        for (Jugador jugador : jugadores) {
            System.out.println(jugador); //Se omite el toString() ya que se llama implícitamente.  
        }
    }
    

    // Método que realiza las acciones asociadas al comando 'listar avatares'.
    private void listarAvatares() {
        for (Avatar avatar : avatares) {
            avatar.toString();
        }
    }

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
        if(jugadores == null || jugadores.isEmpty()){
            System.out.println("No hay jugadores en la partida.");
            return;
        }
        if(turno == jugadores.size() - 1) {
            turno = 0;
        } else {
            turno++;
        }

        System.out.println("El jugador actual es " + jugadores.get(turno).getNombre() + ".");
    }

    private void imprimirJugadorTurno() {
        System.out.println("Nombre:" + this.jugadores.get(turno).getNombre() + "\nAvatar: " + this.jugadores.get(turno).getAvatar().getId());
    }
}
