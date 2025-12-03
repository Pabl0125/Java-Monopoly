package monopoly.cartas;

import partida.Jugador;
import monopoly.Tablero;
import monopoly.ConsolaNormal;
import monopoly.Juego;

import java.util.ArrayList;

public abstract class Carta {

    private final String descripcion;
    private final int num;
    private final Juego juego;

    public Carta(){
        this.juego = null;
        this.descripcion = "";
        this.num = 0;   
    }


    public Carta(int num, Juego juego, String descripcion){
        this.juego = juego;
        this.descripcion = descripcion;
        this.num = num;
    }

    //Método para ejecutar la acción de la carta
    public abstract void accion(Tablero tablero, Jugador jugadorActual, ArrayList<Jugador> jugadores);

    //Getters
    public String getDescripcion() {
        return descripcion;
    }
    public int getNum() {
        return num;
    }
    public Juego getJuego() {
        return juego;
    }
    public ConsolaNormal getConsola() {
        return this.juego.getConsola();
    }
}
