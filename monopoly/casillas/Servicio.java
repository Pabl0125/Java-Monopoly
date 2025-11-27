package monopoly.casillas;
import partida.*;

public final class Servicio extends Propiedad{
    
    public Servicio(){
        super();                    //Llamammos al contructor de la clase padre

    }
    public Servicio(String nombre, String tipo, int posicion, Jugador duenho, float impuesto, float hipoteca, boolean estarHipotecada){
        this();
        this.nombre = nombre;
    }
}