package monopoly.casillas;
import monopoly.*;
import monopoly.edificios.*;
import monopoly.excepciones.ConstruccionException;
import monopoly.excepciones.DineroInsuficienteException;
import partida.*;
import java.util.ArrayList;
import java.util.Iterator;

public final class Solar extends Propiedad{
    private final ArrayList<Edificacion> edificios;   //ArrayList de edificaciones que posee la casilla
    private final float hipoteca;                     //Valor otorgado por hipotecar una casilla
    private boolean estarHipotecada;            //Booleano que indica si la casilla ha sido hipotecada o no
    private float precioCasa;
    private float precioHotel;
    private float precioPiscina;
    private float precioPistaDeportiva;
    private float alquilerCasa;
    private float alquilerHotel;
    private float alquilerPiscina;
    private float alquilerPistaDeportiva;

    ////////////////CONSTRUCTOR/////////////////////

    public Solar(String nombre, int posicion, float valor, float alquiler, float precioCasa,float precioHotel, float precioPiscina, float precioPistaDeportiva, float alquilerCasa, float alquilerHotel, Jugador duenho){
        super(nombre, posicion,valor,alquiler,duenho);  //Llamammos al contructor de la clase pade
        this.edificios = new ArrayList<>();
        this.hipoteca = valor/2;            // En la tabla la hipoteca
        this.estarHipotecada = false;
        this.precioCasa = precioCasa;
        this.precioHotel = precioHotel;
        this.precioPiscina = precioPiscina;
        this.precioPistaDeportiva = precioPistaDeportiva;
        this.alquilerCasa = alquilerCasa;
        this.alquilerHotel = alquilerHotel;
        this.alquilerPiscina = alquilerHotel/5;
        this.alquilerPistaDeportiva = alquilerHotel/5;
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
    private void anhadirEdificacion(Edificacion edificio){
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
    
    public void Edificar(Edificacion edificacion, Jugador jugadorActual) throws ConstruccionException, DineroInsuficienteException{
                
        // Comprobar que el jugador es el dueño de la casilla
        if (!this.getDuenho().equals(jugadorActual)) {
            throw new ConstruccionException("No eres el dueño de la casilla '" + this.getNombre() + "'.");        }
        //Comprobar que el jugador posee todas las casillas del grupo
        Grupo grupo = this.getGrupo();
        if (grupo == null) {
            throw new ConstruccionException("Error: La casilla no pertenece a ningún grupo.");
        }
        if (!grupo.esDuenhoGrupo(jugadorActual)) {
            throw new ConstruccionException("Debes ser dueño de todas las propiedades del grupo " + grupo.colorToNombreGrupo() + " para poder edificar.");
        }
        // Comprobar que el tipo de edificio es válido
        float costeEdificio = grupo.getPrecioEdificioPorGrupo(edificacion.getTipo());
        if (costeEdificio <= 0) {
            throw new ConstruccionException("El tipo de edificio '" + edificacion.getTipo() + "' no es válido.");
        }

        //comprobaciones con respecto a las casillas que ya existen
        boolean casillaTieneHotel = false;
        boolean casillaTienePiscina = false;
        boolean casillaTienePistaDeportiva =false;
        int numeroCasas=0;
        Iterator<Edificacion> iterator = this.getEdificios().iterator();
        while(iterator.hasNext()){
            Edificacion edificioRecorrido = iterator.next();
            if(edificioRecorrido.getTipo().equals("Piscina")) casillaTienePiscina=true;
            if(edificioRecorrido.getTipo().equals("PistaDeportiva")) casillaTienePistaDeportiva=true;
            if(edificioRecorrido.getTipo().equals("Hotel")) casillaTieneHotel =true;
            if(edificioRecorrido.getTipo().equals("Casa")) numeroCasas++;
        }
        //Si tienes hotel o 4 casas no puedes contruir mas casas
        if((numeroCasas ==4 || casillaTieneHotel) && edificacion.getTipo().equals("Casa")){
            throw new ConstruccionException("No se puede edificar mas casas en la casilla " + this.getNombre());
        }
        //Comprobar que hay sitio para edificar
        if((casillaTieneHotel && casillaTienePiscina && casillaTienePistaDeportiva)){
            throw new ConstruccionException("No se puede edificar ningun edificio mas en esta casilla ni en el grupo al que la casillla pertenece");
        }
        //Comprobar si acaso ya existe un hotel, pista deportiva o piscina cuando se quiere crear una
        if(casillaTieneHotel && edificacion.getTipo().equals("Hotel")){
            throw new ConstruccionException("No se puede edificar un hotel ya que ya existe un hotel en la casilla " + this.getNombre());
        }
        //No se puede adificar mas de una piscina
        if(casillaTienePiscina && edificacion.getTipo().equals("Piscina")){
            throw new ConstruccionException("No se puede edificar una piscina ya que ya existe una en la casilla " + this.getNombre());
        }
        //Comprobar si acasa ya existe un hotel, pista deportiva o piscina cuando se quiere crear una
        if(casillaTienePistaDeportiva && edificacion.getTipo().equals("pista deportiva")){
            throw new ConstruccionException("No se puede edificar una pista deportiva ya que ya existe una en la casilla " + this.getNombre());
        }
        // Comprobar que la casilla dispone de un hotel para crear una piscina
        if(!casillaTieneHotel && edificacion.getTipo().equals("Piscina")){
            throw new ConstruccionException("No se puede edificar una piscina ya que no se dispone de un hotel.");
        }
        // Comprobar si acaso se quiere construir un hotel que el jugador tiene 4 casas en ese hotel
        if(!(numeroCasas==4) && edificacion.getTipo().equals("Hotel")){
            throw new ConstruccionException("No se disponen de las cuatro casa necesarias para contruir el hotel");
        }
        //Comprobar que el jugador tiene suficiente dinero
        if (jugadorActual.getFortuna() < costeEdificio) {
            throw new DineroInsuficienteException("La fortuna de "+ jugadorActual.getNombre() + " no es suficinete para edificar un" + jugadorActual.getNombre() + " en la casilla " + this.getNombre());
        }
        //Hacer el pago por la contruccion y realizar la edificacion
        jugadorActual.sumarFortuna(-costeEdificio);
        jugadorActual.sumarGastos(costeEdificio);
        //Si vamos a crear un hotel y hemos comprobado que existen 4 casas tenemos que eliminar las casas
        if(edificacion.getTipo().equals("Hotel")){
            this.getEdificios().removeIf(edificio -> edificio.getTipo().equals("casa"));
            Juego.consola.imprimir("Se han eliminado las casas para poder construir el hotel");
            
        }
        //Tras todas las comprobaciones pertinentes finalemente edificamos en el solar
        anhadirEdificacion(edificacion);
        Juego.consola.imprimir("Se ha edificado un/a " + edificacion.getTipo() + " en " + this.getNombre() + ". La fortuna de " + jugadorActual.getNombre() + " se reduce en " + costeEdificio + "€");
    }

    
    /////////////////METODOS SOBREESCRITOS/////////////////////
    @Override
    public String infoCasilla(){
        return "Nombre: " + this.getNombre() +
                "\nTipo: " + this.getTipo() +
                "\nValor: " + this.getValor() +
                "\nDueño: " + this.getDuenho().getNombre() +
                "\nGrupo: " + this.getGrupo().colorToNombreGrupo() +
                "\nEdificaciones: " + this.getNumEdificaciones() +
                "\nRentabilidad: " + this.getRentabilidad() +
                "\nHipotecada: " + this.getEstarHipotecada()+
                "\nAlquiler: " + this.getAlquiler() +
                "\nPrecio Casa: " + this.precioCasa +
                "\nPrecio Hotel: " + this.precioHotel +
                "\nPrecio Piscina: " + this.precioPiscina +
                "\nPrecio Pista de Deporte: " + this.precioPistaDeportiva +
                "\nAlquiler Casa: " + this.alquilerCasa +
                "\nAlquiler Hotel: " + this.alquilerHotel +
                "\nAlquiler Piscina: " + this.alquilerPiscina +
                "\nAlquiler Pista de Deporte: " + this.alquilerPistaDeportiva +
                "\nJugadores: " + this.getListaJugadoresEnCasilla().trim();
    }
    @Override
    public boolean evaluarCasilla(Tablero tablero, Jugador jugadorActual, int tirada){
        //CASO 1: El solar es de un tercero y se debe pagar el importe correspondiente al alquiler de la propiedad
        if(!this.getDuenho().getNombre().equals("Banca") && !this.getDuenho().equals(jugadorActual)){
            if(this.estarHipotecada){
                Juego.consola.imprimir("Has caído en una propiedad hipotecada. No pagas alquiler.");
                return true;
            }else{
                jugadorActual.cobrarAlquiler(this);
                return jugadorActual.esSolvente();      //Nota: Si acaso el saldo del jugador era menor que el dinero del alquiler saldra como no solvente pues tendra saldo negativo
            }
            
        }
        //CASO 2: El solar es del jugador que ha caido en la casilla
        else if(this.getDuenho().equals(jugadorActual)){ //Caso de que el dueño sea el mismo jugador
            Juego.consola.imprimir("Has caído en una de tus propiedades: " + this.getNombre() + ".");
            return true; //El jugador necesariamente es solvente al no tener que pagar por estar en su propiedad
        }
        //CASO 3: El solar no pertenece a nadie y tiene, por lo tanto, opcion de compra
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
