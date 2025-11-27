package monopoly.casillas;
import partida.*;

public class Accion extends Casilla{
    
    public Accion(){
        super();                    //Llamammos al contructor de la clase pade
        this.tipo = this.getClass().getName();  //La casilla se llama como el nombre de la clase
    }
}