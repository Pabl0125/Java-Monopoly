package monopoly.casillas;
import monopoly.Juego;
import partida.*;

public abstract class Accion extends Casilla{
    
    ///////////////CONSTRUCTOR///////////////
    public Accion(String nombre, int posicion){
        super(nombre, posicion);     //Llamamos al contructor de la clase padre
    }
}