package monopoly.casillas;
import monopoly.Juego;
import partida.*;

public final class CajaComunidad extends Casilla{
    public CajaComunidad(String nombre, int posicion, Juego juego){
        super(nombre,posicion,juego);                    //Llamammos al contructor de la clase pade
    }
    ////////////METODOS SOBREESCRITOS/////////////
    @Override
    public String infoCasilla(){
        return "Nombre: " + this.getNombre() + "\nTipo: " + this.getTipo();
    }
    @Override
    public boolean evaluarCasilla(){
        getJuego().getConsola().imprimir(getJuego().getJugadorActual().getNombre() + " ha caído en " + getJuego().getJugadorActual().getAvatar().getLugar().getNombre() + " y debe tomar una carta de Caja de Comunidad.");
        
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //A continuacion aqui se debe implementar la logica de coger una carta y ejecutar la accion correspondiente
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /// 
        return getJuego().getJugadorActual().esSolvente();
    }
}