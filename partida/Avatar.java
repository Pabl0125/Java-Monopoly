package partida;

import monopoly.*;
import monopoly.casillas.Casilla;

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
    * ESTA VERSIÓN ACEPTA MOVIMIENTOS HACIA ATRÁS (VALORES NEGATIVOS).
     */
    public void moverAvatar(Tablero tablero, int valorTirada) {
        if (this.lugar == null) {
            System.err.println("Error: el avatar no está en ninguna casilla.");
            return;
        }

        // 1. Eliminar el avatar de la casilla actual
        this.lugar.eliminarAvatar(this);

        // 2. Calcular la nueva posición, dando la vuelta al tablero si es necesario y 
        //sumando 2000000 al jugador si pasa por la casilla de salida
        int posicionActual = this.lugar.getPosicion();
        int nuevaPosicion = posicionActual + valorTirada;
        boolean pasoPorSalida = false;

        if (valorTirada > 0) {
            // Movimiento hacia adelante: si se supera 39, se considera haber pasado por Salida
            if (nuevaPosicion > 40) {
                nuevaPosicion = nuevaPosicion % 40;
                pasoPorSalida = true;
            }
        } else if (valorTirada < 0) {
            // Movimiento hacia atrás: si queda negativo, envolvemos al tablero sin
            // considerar que se ha pasado por la casilla de Salida (no hay bonificación).
            // Normalizamos la posición para que quede en el rango [0,39].
            while (nuevaPosicion < 0) {
                nuevaPosicion += 40;
            }
            // No se marca pasoPorSalida cuando se retrocede
        }

        if (pasoPorSalida) {
            // El avatar ha pasado por la casilla de salida en movimiento hacia adelante
            this.jugador.sumarFortuna(2000000f); // Añadimos 2000000 al saldo del jugador
            this.jugador.sumarDineroSalida(2000000f);  //Sumamos el dinero a la estadistica
            this.jugador.sumarNumeroDeVueltas(1);       //Sumamos otra vuelta
            System.out.println("El avatar " + this.id + " ha pasado por la casilla de salida. Se añaden 2000000€ al saldo del jugador " + this.jugador.getNombre() + ".");
        }

        // 3. Encontrar la nueva casilla y actualizar el estado
        Casilla nuevaCasilla = tablero.encontrar_casilla_por_posicion(nuevaPosicion);
        this.setLugar(nuevaCasilla); // Actualizamos la casilla en el avatar
        
        this.getLugar().visitarCasilla(); // Incrementamos el contador de visitas de la casilla

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
