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
        //Añadimos el avatar a la lista de avatares de su casilla inicial
        if (lugar != null) {
            lugar.anhadirAvatar(this);
        }
    }
    //A continuación, tenemos otros métodos útiles para el desarrollo del juego.
    /*Método que permite mover a un avatar a una casilla concreta. Parámetros:
    * - El tablero, para poder encontrar la nueva casilla.
    * - Un entero que indica el numero de casillas a moverse (será el valor sacado en la tirada de los dados).
    * EN ESTA VERSIÓN SUPONEMOS QUE valorTirada siemrpe es positivo.
     */
    public void moverAvatar(Tablero tablero, int valorTirada) {
        if (this.lugar == null) {
            System.err.println("Error: el avatar no está en ninguna casilla.");
            return;
        }

        // 1. Eliminar el avatar de la casilla actual
        this.lugar.eliminarAvatar(this);

        // 2. Calcular la nueva posición, dando la vuelta al tablero si es necesario
        int posicionActual = this.lugar.getPosicion();
        int nuevaPosicion = (posicionActual + valorTirada - 1) % 40 + 1;

        // 3. Encontrar la nueva casilla y actualizar el estado
        Casilla nuevaCasilla = tablero.encontrar_casilla_por_posicion(nuevaPosicion);
        this.setLugar(nuevaCasilla); // Actualizamos la casilla en el avatar

        // 4. Añadir el avatar a la lista de la nueva casilla
        this.lugar.anhadirAvatar(this);
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
