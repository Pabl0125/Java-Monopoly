package monopoly.casillas;
import monopoly.*;
import partida.*;

public final class Transporte extends Propiedad{
    
    public Transporte(String nombre, int posicion, float valor, float alquiler,Jugador duenho){
        super(nombre, posicion, valor, alquiler, duenho);                //Llamammos al contructor de la clase pade
    }
    ///////////////////////METODOS SOBREESCRITOS///////////////////////
    @Override
    public String infoCasilla(){
        return "Nombre: " + this.getNombre() +
                       "\nTipo: " + this.getTipo() +
                       "\nValor: " + this.getValor() +
                       "\nDueño: " + this.getDuenho().getNombre() +
                       "\nJugadores: " + getListaJugadoresEnCasilla();
    }
    @Override
    public boolean evaluarCasilla(Tablero tablero, Jugador jugadorActual, int tirada){
        //CASO 1: El transporte es de un tercero y se debe pagar el importe correspondiente al alquiler de la propiedad
        if(!this.getDuenho().getNombre().equals("Banca") && !this.getDuenho().equals(jugadorActual)){
            int numeroTransportes = this.getDuenho().numeroDeTransportes();
            //En caso de que se detecte mas de un trasnporte en propiedad, se debe actualizar la variable alquiler
            this.setAlquiler(this.getAlquiler() * numeroTransportes);
            jugadorActual.cobrarAlquiler(this);
            this.setAlquiler(this.getAlquiler() / numeroTransportes);   //Reiniciamos el alquiler
            return jugadorActual.esSolvente();      //Nota: Si acaso el saldo del jugador era menor que el dinero del alquiler saldra como no solvente pues tendra saldo negativo
        }
        //CASO 2: El transporte es del jugador que ha caido en la casilla
        else if(this.getDuenho().equals(jugadorActual)){ //Caso de que el dueño sea el mismo jugador
            Juego.consola.imprimir("Has caído en una de tus propiedades: " + this.getNombre() + ".");
            return true; //El jugador necesariamente es solvente al no tener que pagar por estar en su propiedad
        }
        //CASO 3: El transporte no pertenece a nadie y tiene, por lo tanto, opcion de compra
        else if (this.getDuenho().getNombre().equals("Banca")) {
            this.infoCasillaEnVenta();  //Imprimimos la info de la casilla en venta
            if (jugadorActual.getFortuna() >= this.getValor()) {
                Juego.consola.imprimir("Usa el comando 'comprar' para adquirirla.");
                return true;    //el jugador es solvente y puede comprar la propiedad
            } else {
                Juego.consola.imprimir("No tienes saldo suficiente para comprar esta casilla.");
                return true; //El jugador sigue siendo solvente, dejara de serlo solo si compra la casilla, pues su saldo pasara a ser negativo
            }
        } 
        else return true;   //En caso de errores
    }
}