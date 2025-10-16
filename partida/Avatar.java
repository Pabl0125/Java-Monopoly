package partida;

import monopoly.*;

import java.util.ArrayList;

import java.util.Random;

public class Avatar {

    //Atributos
    private String id; //Identificador: una letra generada aleatoriamente.
    private String tipo; //Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; //Un jugador al que pertenece ese avatar.
    private Casilla lugar; //Los avatares se sitúan en casillas del tablero.

    //Constructor vacío
    public Avatar() {
    }

    /*Constructor principal. Requiere éstos parámetros:
    * Tipo del avatar, jugador al que pertenece, lugar en el que estará ubicado, y un arraylist con los
    * avatares creados (usado para crear un ID distinto del de los demás avatares).
     */
    public Avatar(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        this.generarId(avCreados);
        this.tipo = tipo;
        this.jugador = jugador;
        this.lugar = lugar;
    }
    //A continuación, tenemos otros métodos útiles para el desarrollo del juego.
    /*Método que permite mover a un avatar a una casilla concreta. Parámetros:
    * - Un array con las casillas del tablero. Se trata de un arrayList de arrayList de casillas (uno por lado).
    * - Un entero que indica el numero de casillas a moverse (será el valor sacado en la tirada de los dados).
    * EN ESTA VERSIÓN SUPONEMOS QUE valorTirada siemrpe es positivo.
     */
    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        //Nos movemos tantas casillas como indique valorTirada.
        int posicionActual = this.lugar.getPosicion(); //Obtenemos la posición actual del avatar.
        int nuevaPosicion = posicionActual + valorTirada; //Calculamos la nueva posición
        for (ArrayList<Casilla> cs:casillas){
            for (Casilla c:cs){
                if (c.getPosicion()==nuevaPosicion){
                    c.anhadirAvatar(this);    
                }
                if (c.getPosicion()==posicionActual){
                    c.eliminarAvatar(this);
                }
            }
        }
    }

    /*Método que permite  mayúscula. Parámetros:
    * - Un arraylist de logenerar un ID para un avatar. Sólo lo usamos en esta clase (por ello es privado).
    * El ID generado será una letras avatares ya creados, con el objetivo de evitar que se generen dos ID iguales.
     */
    private void generarId(ArrayList<Avatar> avCreados) {
    Random rand = new Random();
    char letra;
    boolean idRepetido;

    do {
        // Genera una letra mayúscula aleatoria (A-Z)
        letra = (char) ('A' + rand.nextInt(26));
        idRepetido = false;

        // Comprueba si ya existe un avatar con ese ID
        for (Avatar av : avCreados) {
            if (av.id.equals(String.valueOf(letra))) {
                idRepetido = true;
                break;
            }
        }
    } while (idRepetido);

    // Asigna el ID antes de añadir el avatar a la lista
    this.id = String.valueOf(letra);
    avCreados.add(this);
}




    //Getters y setters:
    public String getId() {
        return id;
    }
    public String getTipo() {
        return tipo;
    }
    public Jugador getJugador() {
        return jugador;
    }
    public Casilla getLugar() {
        return lugar;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
    public void setLugar(Casilla lugar) {
        this.lugar = lugar;
    }
    @Override
    public String toString() {
        System.out.println("Avatar " + this.id + " (" + this.tipo + ") del jugador " + this.jugador.getNombre() +
                " está en la casilla " + this.lugar.getNombre() + " (posición " + this.lugar.getPosicion() + ").");
        return "";
    }

}
