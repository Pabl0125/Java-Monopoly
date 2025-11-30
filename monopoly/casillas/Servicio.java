package monopoly.casillas;
import monopoly.*;
import partida.*;
public final class Servicio extends Propiedad{
    
    public Servicio(String nombre, int posicion, Juego juego){
        super(nombre, posicion, juego);                //Llamammos al contructor de la clase pade
        this.setValor(500000);                  //Las casillas de transporte y de servicio tienen un precio de compra inicial de 500.000€. 
        this.setAlquiler(50000);           //El alquiler de una casilla de transporte es de 250.000€.
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
    public boolean evaluarCasilla(){
        Jugador jugadorActual = getJuego().getJugadorActual();        

        //CASO 1: El servico es de un tercero y se debe pagar el importe correspondiente al alquiler de la propiedad por el factor multiplicador
        if(!this.getDuenho().equals(getJuego().getBanca()) && !this.getDuenho().equals(jugadorActual)){
        //Evaluamos el numero de servicios que tiene el jugador poseedor de la  casilla
        int numeroServicios = this.getDuenho().numeroDeServicios();
        int multiplicador;
        switch (numeroServicios) {
            case 1:
                multiplicador = 4;
                break;
            case 2:
                multiplicador = 10;
                break;
            default:
                multiplicador = 1;
                break;
        }
            //En caso de que se detecte mas de un servicio en propiedad, se debe actualizar la variable alquiler
            this.setAlquiler(this.getAlquiler() * multiplicador);
            jugadorActual.cobrarAlquiler(this);
            this.setAlquiler(this.getAlquiler() / multiplicador);   //Reiniciamos el alquiler
            return jugadorActual.esSolvente();      //Nota: Si acaso el saldo del jugador era menor que el dinero del alquiler saldra como no solvente pues tendra saldo negativo
        }
        //CASO 2: El servicio es del jugador que ha caido en la casilla
        else if(this.getDuenho().equals(jugadorActual)){ //Caso de que el dueño sea el mismo jugador
            getJuego().getConsola().imprimir("Has caído en una de tus propiedades: " + this.getNombre() + ".");
            return true; //El jugador necesariamente es solvente al no tener que pagar por estar en su propiedad
        }
        //CASO 3: El servicio no pertenece a nadie y tiene, por lo tanto, opcion de compra
        else if (this.getDuenho().equals(getJuego().getBanca())) {
            this.infoCasillaEnVenta();  //Imprimimos la info de la casilla en venta
            if (jugadorActual.getFortuna() >= this.getValor()) {
                getJuego().getConsola().imprimir("Usa el comando 'comprar' para adquirirla.");
                return true;    //el jugador es solvente y puede comprar la propiedad
            } else {
                getJuego().getConsola().imprimir("No tienes saldo suficiente para comprar esta casilla.");
                return true; //El jugador sigue siendo solvente, dejara de serlo solo si compra la casilla, pues su saldo pasara a ser negativo
            }
        } 
        else return true;   //En caso de errores
    }
}