package monopoly.casillas;
import monopoly.*;
import partida.*;

public final class Suerte extends Accion{
    
    public Suerte(String nombre, int posicion,Juego juego){
        super(nombre,posicion,juego);                    //Llamamos al contructor de la clase pade
    }
    ////////////METODOS SOBREESCRITOS/////////////
    @Override
    public String infoCasilla(){
        return "Nombre: " + this.getNombre() + "\nTipo: " + this.getTipo();
    }
    @Override
    public boolean evaluarCasilla(){
        this.getJuego().getConsola().imprimir(getJuego().getJugadorActual().getNombre() + " ha caído en " + getJuego().getJugadorActual().getAvatar().getLugar().getNombre() + " y debe tomar una carta de Suerte");
        this.getJuego().sacarCartaSuerte(getJuego().getJugadorActual());
        return getJuego().getJugadorActual().esSolvente();
    }
    
}