package monopoly.cartas;

import partida.Jugador;
import monopoly.Tablero;

import java.util.ArrayList;

public abstract class Carta {

    private final String descripcion;
    private final int num;


    public Carta(){
        this.descripcion = "";
        this.num = 0;   
    }


    public Carta(int num, String descripcion) {
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

}