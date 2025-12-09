package partida;
import monopoly.*;
import monopoly.casillas.*;
import monopoly.edificios.*;
import monopoly.excepciones.AccionInvalidaException;
import partida.*;
import java.util.ArrayList;

public class Jugador {

    //Atributos:
    private String nombre;                      //Nombre del jugador
    private Avatar avatar;                      //Avatar que tiene en la partida.
    private float fortuna;                      //Dinero que posee.
    private float gastos;                       //Gastos realizados a lo largo del juego.     
    private boolean enCarcel;                   //Será true si el jugador está en la carcel
    private int tiradasCarcel;                  //Cuando está en la carcel, contará las tiradas sin éxito que ha hecho allí para intentar salir (se usa para limitar el numero de intentos).
    private int vueltas;                        //Cuenta las vueltas dadas al tablero.
    private ArrayList<Propiedad> propiedades;   //Propiedades que posee el jugador.
    private Juego juego;                        //Juego al que pertenece el juegador
    private int vecesEnCarcel;                  //Cuenta las veces que el jugador ha ido a la carcel.
    private float dineroPremios;                //Cuenta el dinero que el jugador ha ganado en premios.        
    private float dineroSalida;                 //Cuenta el dinero que el jugador ha ganado al pasar por la salida.
    private float dineroCobroAlquileres;        //Cuenta el dinero que el jugador ha ganado por cobrar alquileres.
    private float dineroPagoAlquileres;         //Cuenta el dinero que el jugador ha pagado en alquileres.
    private float dineroTasasImpuestos;         //Cuenta el dinero que el jugador ha pagado en tasas e impuestos.
    private float dineroInvertido;              //Cuenta el dinero que el jugador ha invertido en comprar propiedades y construir edificios.
    private ArrayList<Trato> tratos;            //Tratos que tiene el jugador con otros jugadores.
    
    /////////////////////CONTRUCTORES/////////////////////
    
    //Constructor vacío. Se usará para crear la banca.
    public Jugador(Juego juego) {
        this.nombre="Banca";                //Se asigna el nombre del jugador.
        this.avatar = null;                 //La banca no tiene avatar.
        this.fortuna=Valor.FORTUNA_BANCA;   //La banca empieza con 1.
        this.gastos = 0;                    //Al crear el jugador, no ha realizado ningún gasto.
        this.enCarcel = false;              //Al crear el jugador, no está en la carcel.
        this.tiradasCarcel = 0;             //Al crear el jugador, no ha tirado para salir de la carcel.
        this.vueltas = 0;                   //Al crear el jugador, no ha dado ninguna vuelta.
        this.propiedades=new ArrayList<>(); //Al crear el jugador, la banca posee todas las propiedadades.
        this.avatar = null; 
        this.fortuna=Valor.FORTUNA_BANCA; 
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.propiedades=new ArrayList<>();
        this.vueltas = 0;
        this.juego = juego;
        this.tratos = new ArrayList<>();
    }
    public Jugador(String nombreJugador, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados, Juego juego) {
        this.nombre = nombreJugador;
        this.avatar = new Avatar(tipoAvatar, this, inicio, avCreados);
        this.fortuna=Valor.FORTUNA_INICIAL;
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.propiedades = new ArrayList<Propiedad>();
        this.vecesEnCarcel = 0;
        this.dineroPremios = 0;
        this.dineroSalida = 0;
        this.dineroCobroAlquileres = 0;
        this.dineroPagoAlquileres = 0;
        this.dineroTasasImpuestos = 0;
        this.dineroInvertido = 0;
        this.juego = juego;
        this.tratos = new ArrayList<>();
    }
    
    ///////////////GETTERS Y SETTERS///////////////////
    public Juego getJuego(){
        return this.juego;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Avatar getAvatar() {
        return avatar;
    }
    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
    public float getFortuna() {
        return fortuna;
    }
    public float getGastos() {
        return gastos;
    }
    public boolean isEnCarcel() {
        return enCarcel;
    }
    public void setEnCarcel(boolean enCarcel) {
        this.enCarcel = enCarcel;
    }
    public int getTiradasCarcel() {
        return tiradasCarcel;
    }
    public void setTiradasCarcel(int tiradasCarcel) {
        this.tiradasCarcel = tiradasCarcel;
    }
    public int getVueltas() {
        return vueltas;
    }
    public void setVueltas(int vueltas) {
        this.vueltas = vueltas;
    }
    public ArrayList<Propiedad> getPropiedades() {
        return propiedades;
    }
    public void setPropiedades(ArrayList<Propiedad> propiedades) {
        this.propiedades = propiedades;
    }
    public int getVecesEnCarcel(){
        return this.vecesEnCarcel;
    }
    public float getDineroPremios(){
        return this.dineroPremios;
    }
    public float getDineroSalida(){
        return this.dineroSalida;
    }
    public float getDineroCobroAlquileres(){
        return this.dineroCobroAlquileres;
    }
    public float getDineroPagoAlquileres(){
        return this.dineroPagoAlquileres;
    }
    public float getDineroTasasImpuestos(){
        return this.dineroTasasImpuestos;
    }
    public float getDineroInvertido(){
        return this.dineroInvertido;
    }

    public ArrayList<Trato> getTratos() {
        return tratos;
    }

    
    //////////////////METODOS GENERICOS/////////////////////

    public void sumarFortuna(float valor) {
        this.fortuna = this.fortuna + valor;
    }
    public void sumarGastos(float valor) {
        this.gastos = this.gastos + valor;
    }
    public void sumarvecesEnCarcel(int valor){
        this.vecesEnCarcel = this.vecesEnCarcel + valor;
    }
    public void sumarDineroPremios(float valor){
        this.dineroPremios = this.dineroPremios + valor;
    }
    public void sumarDineroSalida(float valor){
        this.dineroSalida = this.dineroSalida + valor;
    }
    public void sumardineroCobroAlquileres(float valor){
        this.dineroCobroAlquileres = this.dineroCobroAlquileres + valor;
    }
    public void sumarDineroPagoAlquileres(float valor){
        this.dineroPagoAlquileres = this.dineroPagoAlquileres + valor;
    }
    public void sumarDineroTasasImpuestos(float valor){
        this.dineroTasasImpuestos = this.dineroTasasImpuestos + valor;
    }
    public void sumarDineroInvertido(float valor){
        this.dineroInvertido = this.dineroInvertido + valor;
    }
    public void sumarVueltas(int valor){
        this.vueltas = this.vueltas + valor;
    }
    public void anhadirPropiedad(Propiedad propiedad) {
        this.propiedades.add(propiedad); 
    }

    public void eliminarPropiedad(Propiedad propiedad) {
        for(Propiedad p: propiedades){
            if (p.equals(propiedad)) {
                propiedades.remove(propiedad);
                break;
            }
        }
    }

    public void pagarDineroFijo(float cantidad){
        this.sumarFortuna(cantidad);
        this.sumarGastos(cantidad);
        this.sumarDineroTasasImpuestos(cantidad);

        juego.getTablero().aumentarBoteParking(cantidad);
        juego.getConsola().imprimir("Al jugador " + this.getNombre() + " se le ha cobrado " + cantidad +"€.");
    }
    
    public void cobrarAlquiler(Propiedad propiedad){
        float alquiler = propiedad.getAlquiler();
        this.sumarFortuna(-alquiler);
        this.sumarGastos(alquiler);
        this.sumarDineroPagoAlquileres(alquiler);

        propiedad.getDuenho().sumarFortuna(alquiler);
        propiedad.getDuenho().sumardineroCobroAlquileres(alquiler);
        propiedad.sumarRentabilidad(alquiler);

        juego.getConsola().imprimir("Al jugador" + this.getNombre() + "se le ha cobrado" + alquiler + "€ de alquiler.");
        if(!this.esSolvente()) juego.getConsola().imprimir("El jugador ya no es solverte");
    }

    public void cobrarImpuesto(Impuesto casillaImpuesto){
        float impuesto = casillaImpuesto.getImpuesto();
        pagarDineroFijo(impuesto);
        if(!this.esSolvente()) juego.getConsola().imprimir("El jugador ya no es solverte");
        
    }

    public void encarcelar() throws AccionInvalidaException {
        Tablero tablero = juego.getTablero();
        Casilla casillaCarcel = tablero.encontrar_casilla("Carcel");
        if (casillaCarcel == null) {
            throw new AccionInvalidaException("Error crítico: No se ha encontrado la casilla 'Carcel'.");
        }
        // Eliminar el avatar de su casilla actual
        this.avatar.getLugar().eliminarAvatar(this.avatar);
        // Mover el avatar a la cárcel
        this.avatar.setLugar(casillaCarcel);
        casillaCarcel.anhadirAvatar(this.avatar);
        this.enCarcel=true;
        this.vecesEnCarcel ++;  //Aumenta el numero de veces que el jugador ha ido a la carcel en 1
    }
    public int numeroDeServicios(){
        int numServicios = 0;
        for(Casilla c: this.getPropiedades()){
            if(c instanceof Servicio){
                numServicios++;
            }
        }
        return numServicios;
    }
    public int numeroDeTransportes(){
        int numTransportes = 0;
        for(Casilla c: this.getPropiedades()){
            if(c instanceof Transporte){
                numTransportes++;
            }
        }
        return numTransportes;
    }
    public String edificiosEnPropiedad(){
        String edificiosFormato = "";
        ArrayList<Edificacion> edificaciones = new ArrayList<>();
        for(Propiedad  p: this.getPropiedades()){
            if (p instanceof Solar solar){
                edificaciones.addAll(solar.getEdificios());
            }
        }
        if(edificaciones.isEmpty()){
            edificiosFormato = " Ninguno";
        }else{
            for(Edificacion e: edificaciones){
                edificiosFormato += e.toString() + ", ";
            }
        }
        return edificiosFormato;
    }
        
    public boolean esSolvente(){
        return this.fortuna >= 0;
    }


    public Trato buscarTratoPorId(int id){
        if(tratos.isEmpty()) return null;
        for(Trato trato: tratos){
            if(trato.getId() == id){
                return trato;
            }
        }
        return null;
    }

    public void eliminarTrato(Trato trato){
        for(Trato t: tratos){
            if(t.equals(trato)){
                tratos.remove(trato);
                Juego.consola.imprimir("Se ha eliminado el trato" + t.getId());
                break;
            }
        }
    }



    ////////////////METODOS SOBREESCRITOS//////////////////

    @Override
    public String toString(){
        String avatarInfo = (this.avatar != null) ? this.avatar.getId() : "(Banca)";
        return "Nombre: " + this.nombre +
               "\nAvatar: " + avatarInfo +
               "\nFortuna: " + this.fortuna +
               "\nPropiedades:" + this.propiedades +
                "\nEdificios:" + this.edificiosEnPropiedad();
            
    }

}
