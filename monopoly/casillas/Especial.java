package monopoly.casillas;
import monopoly.*;
import monopoly.excepciones.AccionInvalidaException;
import partida.*;


public class Especial extends Casilla{
    
    public Especial(String nombre, int posicion){
        super(nombre,posicion);                    //Llamamos al contructor de la clase padre
    }
    /////////////METODOS SOBREESCRITOS/////////////
    @Override
    public String infoCasilla(){
        if(getNombre().equals("Carcel")) return "Nombre: " + this.getNombre() + "\nTipo: " + this.getTipo() + "\nJugadores: " + getAvatares().toString().trim();
        else if(getNombre().equals("Salida")) return "\nJugadores: " + getAvatares().toString().trim();
        else if(getNombre().equals("IrACarcel")) return "Esta casilla te envía directamente a la cárcel.";
        else return "Información no disponible para la casilla '" + this.getNombre() + "'.";        
    }
    @Override
    public boolean evaluarCasilla(Tablero tablero, Jugador jugadorActual, int tirada){
        switch (this.getNombre()) {
            case "Carcel":
                Juego.consola.imprimir(jugadorActual.getNombre() + " ha caido en la la casilla Carcel");
                return true;
            case "Salida":
                Juego.consola.imprimir("¡" + jugadorActual.getNombre() + " ha llegado a la salida!");
                ////////////////////////////////////////////////////////////////////////////////////////////
                //Al jugador luego se le debe sumar la cantidad correspondiente por pasar por la salida
                //Esto está implementado en el método de la clase Avatar --> moverAvatar();
                ////////////////////////////////////////////////////////////////////////////////////////////
                return true;
            case "IrACarcel":
                Juego.consola.imprimir("¡" + jugadorActual.getNombre() + " va a la cárcel!");
                try{
                    jugadorActual.encarcelar();
                }
                catch(AccionInvalidaException e){
                    Juego.consola.imprimir(e.getMessage());
                    Juego.consola.imprimir("No se ha podido encarcelar al jugador por caer en la casilla carcel");
                }
                return true;     //Ir a la carcel no cuesta dinero, por lo que es imposible quebrar
            default:
                return true;    //Por defecto diremos que el jugador es solvente
        }
    }
}