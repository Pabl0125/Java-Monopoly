package monopoly.casillas;
import monopoly.*;
import monopoly.edificios.*;
import partida.*;
import java.util.ArrayList;

public final class Solar extends Propiedad{
    private ArrayList<Edificacion> edificios;   //ArrayList de edificaciones que posee la casilla
    private float hipoteca;                     //Valor otorgado por hipotecar una casilla
    private boolean estarHipotecada;            //Booleano que indica si la casilla ha sido hipotecada o no

    public Solar(String nombre, int posicion, Juego juego){
        super(nombre, posicion, juego);                            //Llamammos al contructor de la clase pade
        this.edificios = new ArrayList<>();
        this.hipoteca = 0;
        this.estarHipotecada = false;
    }
    ////////////////GETTERS Y SETTERS/////////////////////

    public ArrayList<Edificacion> getEdificios(){
        return this.edificios;
    }
    public float getHipoteca(){
        return this.hipoteca;
    }
    public boolean getEstarHipotecada(){
        return this.estarHipotecada;
    }
    public void setEstarHipotecada(boolean estarHipotecada){
        this.estarHipotecada = estarHipotecada;
    }

    ////////////////METODOS GENERICOS/////////////////////
    
    public void eliminarEdificacion(Edificacion edificacion){
        this.edificios.remove(edificacion);
    }
    public void anhadirEdificacion(Edificacion edificio){
        this.edificios.add(edificio);
    }
    public int getNumEdificaciones(){
        return this.edificios.size();
    }
    public void actulizarAlquilerTotalSolar(){
        float total = this.getAlquiler();
        for (Edificacion edificio : this.edificios) {
            total += edificio.getAlquiler();
        }
        setAlquiler(total);
    }
    
    /////////////////METODOS SOBREESCRITOS/////////////////////
    @Override
    public String infoCasilla(){        // --> OJO: FALTA ADJUNTAR LOS VALORES DE LOS PRECIOS DE COMPRA Y ALQUILER DE LAS EDIFICACIONES 
        return "Nombre: " + this.getNombre() +
                "\nTipo: " + this.getTipo() +
                "\nValor: " + this.getValor() +
                "\nDueño: " + this.getDuenho().getNombre() +
                "\nGrupo: " + this.getGrupo().colorToNombreGrupo() +
                "\nEdificaciones: " + this.getNumEdificaciones() +
                "\nRentabilidad: " + this.getRentabilidad() +
                "\nHipotecada: " + this.getEstarHipotecada()+
                "\nAlquiler: " + this.getAlquiler() +
                "\nJugadores: " + this.getListaJugadoresEnCasilla().toString().trim();
    }
    @Override
    public boolean evaluarCasilla(){
        Jugador jugadorActual = getJuego().getJugadorActual();
        //CASO 1: El solar es de un tercero y se debe pagar el importe correspondiente al alquiler de la propiedad
        if(!this.getDuenho().equals(getJuego().getBanca()) && !this.getDuenho().equals(jugadorActual)){
            jugadorActual.cobrarAlquiler(this);
            return jugadorActual.esSolvente();      //Nota: Si acaso el saldo del jugador era menor que el dinero del alquiler saldra como no solvente pues tendra saldo negativo
        }
        //CASO 2: El solar es del jugador que ha caido en la casilla
        else if(this.getDuenho().equals(jugadorActual)){ //Caso de que el dueño sea el mismo jugador
            getJuego().getConsola().imprimir("Has caído en una de tus propiedades: " + this.getNombre() + ".");
            return true; //El jugador necesariamente es solvente al no tener que pagar por estar en su propiedad
        }
        //CASO 3: El solar no pertenece a nadie y tiene, por lo tanto, opcion de compra
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
