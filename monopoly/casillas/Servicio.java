package monopoly.casillas;
import monopoly.*;
import monopoly.excepciones.BancarrotaException;
import monopoly.excepciones.DineroInsuficienteException;
import partida.*;
public final class Servicio extends Propiedad{
    
    public Servicio(String nombre, int posicion, float valor, float alquiler, Jugador duenho){
        super(nombre, posicion,valor,alquiler,duenho);                //Llamammos al contructor de la clase pade
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
    public boolean evaluarCasilla(Tablero tablero, Jugador jugadorActual, int tirada) throws BancarrotaException, DineroInsuficienteException{

        //CASO 1: El servico es de un tercero y se debe pagar el importe correspondiente al alquiler de la propiedad por el factor multiplicador
        if(!this.getDuenho().getNombre().equals("Banca") && !this.getDuenho().equals(jugadorActual)){
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
            if(!jugadorActual.esSolvente())
                throw new BancarrotaException("El jugador " + jugadorActual.getNombre() + " ha entrado en bancarrota.");
            return true;
        }
        //CASO 2: El servicio es del jugador que ha caido en la casilla
        else if(this.getDuenho().equals(jugadorActual)){ //Caso de que el dueño sea el mismo jugador
            Juego.consola.imprimir("Has caído en una de tus propiedades: " + this.getNombre() + ".");
            return true; //El jugador necesariamente es solvente al no tener que pagar por estar en su propiedad
        }
        //CASO 3: El servicio no pertenece a nadie y tiene, por lo tanto, opcion de compra
        else if (this.getDuenho().getNombre().equals("Banca")) {
            this.infoCasillaEnVenta();  //Imprimimos la info de la casilla en venta
            if (jugadorActual.getFortuna() >= this.getValor()) {
                Juego.consola.imprimir("Usa el comando 'comprar' para adquirirla.");
                return true;    //el jugador es solvente y puede comprar la propiedad
            } else {
                throw new DineroInsuficienteException("No tienes saldo suficiente para comprar esta casilla.");
            }
        } 
        else return true;   //En caso de errores
    }
}