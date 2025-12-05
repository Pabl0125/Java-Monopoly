package monopoly.casillas;
import monopoly.*;
import monopoly.excepciones.MonopolyException;
import partida.*;
import java.util.ArrayList;


public abstract class Casilla{

    private String nombre;                  //Nombre de la casilla
    private String tipo;                    //Tipo de casilla (Solar, Especial, Transporte, Servicios, Comunidad, Suerte y Impuesto). Protected para que las subclases puedan acceder directamente a ella
    private int posicion;                   //Posición que ocupa la casilla en el tablero
    private ArrayList<Avatar> avatares;     //Avatares que están situados en la casilla.
    private int vecesVisitada;              //Número de veces que ha sido visitada la casilla

    ///////////////CONSTRUCTOR///////////////
    public Casilla(String nombre, int posicion){
        this.nombre = nombre;
        this.tipo = this.getClass().getSimpleName();      //El tipo de la casilla es equivalente al nombre de la clase
        this.avatares = new ArrayList<>(); 
        this.posicion = posicion;
        this.vecesVisitada = 0;
    }

    ///////////////METODOS ABSTRACTOS///////////////
    public abstract String infoCasilla();
    public abstract boolean evaluarCasilla(Tablero tablero, Jugador jugadorActual, int tirada) throws MonopolyException;
    
    ///////////////GETTERS////////////////

    public String getNombre() {
        return this.nombre;
    }
    public String getTipo() {
        return this.tipo;
    }
    public ArrayList<Avatar> getAvatares(){
        return this.avatares;
    }
    public int getPosicion() {
        return this.posicion;
    }
    public int getVecesVisitada(){
        return this.vecesVisitada;
    }

    ///////////////SETTERS////////////////
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    //////////////METODOS SOBREESCRITOS///////////////
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Si son el mismo objeto
        if (obj == null || getClass() != obj.getClass()) return false; // Comprobar null y clase
        Casilla otraCasilla = (Casilla) obj; // Hacemos casting seguro
        return this.nombre != null && this.nombre.equals(otraCasilla.nombre); // Comparamos nombres
    }
    @Override
    public String toString(){
        return nombre;
    }

    ///////////////METODOS GENERICOS///////////////
    public void anhadirAvatar(Avatar av) {
        this.avatares.add(av);
    }
    public void eliminarAvatar(Avatar av) {
        this.avatares.remove(av);
    }
    public void visitarCasilla(){
        this.vecesVisitada += 1;
    }
    public String getListaJugadoresEnCasilla(){
        StringBuilder jugadoresEnCasilla = new StringBuilder();
        if (this.avatares.isEmpty()) {
            jugadoresEnCasilla.append("Ninguno");
        } 
        else {
            ArrayList<String> nombresJugadores = new ArrayList<String>();
            this.avatares.forEach(avatar -> nombresJugadores.add(avatar.getJugador().getNombre()));
            jugadoresEnCasilla.append(String.join(", ", nombresJugadores));
        }
        return jugadoresEnCasilla.toString();
    }

}