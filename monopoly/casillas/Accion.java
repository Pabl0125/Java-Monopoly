package monopoly.casillas;
import monopoly.Juego;
import partida.*;

public abstract class Accion extends Casilla{
    
    ///////////////CONSTRUCTOR///////////////
    public Accion(String nombre, int posicion, Juego juego){
        super(nombre, posicion,juego);     //Llamamos al contructor de la clase padre
    }
}