package monopoly.casillas;
import java.util.ArrayList;

import partida.*;

public class Impuesto extends Casilla{
    private float impuesto;             //Cantidad a pagar por caer en la casilla que se añade al bote del parking

    public Impuesto(String nombre, int posicion){
        super(nombre,posicion);         //Llamammos al contructor de la clase padre
        this.impuesto = 0;   
    }

    public String infoCasilla(){
        return "Nombre: " + this.getNombre() + "\nTipo: " + this.getTipo() + "\nImpuesto a pagar: " + this.impuesto;
    }
}