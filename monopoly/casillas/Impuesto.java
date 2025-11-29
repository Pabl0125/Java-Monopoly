package monopoly.casillas;
import java.util.ArrayList;
import monopoly.*;
import partida.*;

public class Impuesto extends Casilla{
    private float impuesto;                         //Cantidad a pagar por caer en la casilla que se añade al bote del parking

public Impuesto(String nombre, int posicion,Juego juego){
        super(nombre,posicion,juego);                     //Llamammos al contructor de la clase padre
        this.impuesto = 0;   
    }
    ///////////////////////METODOS SOBREESCRITOS///////////////////////
    @Override
    public String infoCasilla(){
        return "Nombre: " + this.getNombre() + "\nTipo: " + this.getTipo() + "\nImpuesto a pagar: " + this.impuesto;
    }
    @Override
    public boolean evaluarCasilla(){
        getJuego().getJugadorActual().cobrarImpuesto(getJuego());     //En el metodo cobrarImpuesto ya se implementa la logica de las estadisticas y del bote del parking        
        if(getJuego().getJugadorActual().esSolvente())return true;
        else return false;
    }
    ///////////////////////GETERS///////////////////////
    public float getImpuesto(){
        return this.impuesto;
    }
}