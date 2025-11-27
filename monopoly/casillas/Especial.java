package monopoly.casillas;
import partida.*;

public class Especial extends Casilla{
    
    public Especial(String nombre, int posicion){
        super(nombre, posicion);                    //Llamammos al contructor de la clase padre
    }

    public String infoCasilla(){
        if(getNombre().equals("Carcel")) return "Nombre: " + this.getNombre() + "\nTipo: " + this.getTipo() + "\nJugadores: " + getAvatares().toString().trim();
        else if(getNombre().equals("Salida")) return "\nJugadores: " + getAvatares().toString().trim();
        else if(getNombre().equals("IrACarcel")) return "Esta casilla te envía directamente a la cárcel.";
        else return "Información no disponible para la casilla '" + this.getNombre() + "'.";        
    }
}