package monopoly.casillas;
import partida.*;

public final class Parking extends Accion{
    private float bote;
    public Parking(){
        super();                    //Llamammos al contructor de la clase pade
    }
    public void sumarBote(float bote){
        this.bote += bote;
    }
}