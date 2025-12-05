package monopoly.casillas;
import monopoly.Juego;
import monopoly.Tablero;
import partida.*;

public final class Parking extends Accion{
    private float bote;
    public Parking(String nombre, int posicion){
        super(nombre,posicion);                    //Llamammos al contructor de la clase pade
    }
    //////////////METODOS GENERICOS///////////////
    public void sumarBoteParking(float bote){
        this.bote += bote;
    }

    ////////////METODOS SOBREESCRITOS/////////////
    @Override
    public String infoCasilla(){
        return "Nombre: " + this.getNombre() + "\nBote: " + this.bote + "\nJugadores: " + getAvatares().toString().trim();      
    }
    @Override
    public  boolean evaluarCasilla(Tablero tablero, Jugador jugadorActual, int tirada){
        //Si cae en el parking tenemos que ver si hay bote y si lo hay, actualizar el saldo tanto del jugador como el valor de la casilla
        if(this.bote > 0){
            Juego.consola.imprimir("El parking tiene dinero en el bote!");
            Juego.consola.imprimir(jugadorActual.getNombre() + "ha recibido el premio del bote. (" + this.bote + "€)");
            jugadorActual.sumarFortuna(this.bote);
            jugadorActual.sumarDineroPremios(this.bote);
            this.bote = 0;      //Tras el cobro el bote se reinicia a 0
        } else {
            Juego.consola.imprimir("El parking no tiene dinero en el bote.");
        }
        return true;
    }
    ////////////GETTERS Y SETTERS///////////////
    public float getBote(){
        return this.bote;
    }


}