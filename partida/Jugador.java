package partida;
import monopoly.*;
import monopoly.casillas.Casilla;
import monopoly.casillas.Impuesto;
import monopoly.casillas.Propiedad;
import monopoly.edificios.Edificacion;
import partida.*;
import java.util.ArrayList;

import monopoly.casillas.Casilla;
import monopoly.casillas.Propiedad;
import monopoly.casillas.Impuesto;
import monopoly.casillas.Transporte;
import monopoly.casillas.Servicio;
import monopoly.edificios.Edificacion;
import monopoly.Tablero;
import monopoly.Valor;
import monopoly.Juego;

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
    private int vecesEnCarcel;                  //Implementado
    private float dineroPremios;                //Implementado         
    private float dineroSalida;                 //Implementado
    private float dineroCobroAlquileres;        //Implementado
    private float dineroPagoAlquileres;         //Implementado
    private float dineroTasasImpuestos;         //Implementado
    private float dineroInvertido;              //Implementado 
    
    /////////////////////CONTRUCTORES/////////////////////
    
    //Constructor vacío. Se usará para crear la banca.
    public Jugador() {
        this.nombre="Banca"; //Se asigna el nombre del jugador.
        this.avatar = null; //La banca no tiene avatar.
        this.fortuna=Valor.FORTUNA_BANCA; //La banca empieza con 1.
        this.gastos = 0; //Al crear el jugador, no ha realizado ningún gasto.
        this.enCarcel = false; //Al crear el jugador, no está en la carcel.
        this.tiradasCarcel = 0; //Al crear el jugador, no ha tirado para salir de la carcel.
        this.vueltas = 0; //Al crear el jugador, no ha dado ninguna vuelta.
        this.propiedades=new ArrayList<>();//Al crear el jugador, la banca posee todas las propiedadades.
        this.nombre="Banca"; 
        this.avatar = null; 
        this.fortuna=Valor.FORTUNA_BANCA; 
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.propiedades=new ArrayList<>();
        this.vueltas = 0;
    }

    /*Constructor principal. Requiere parámetros:
    * Nombre del jugador, tipo del avatar que tendrá, casilla en la que empezará y ArrayList de
    * avatares creados (usado para dos propósitos: evitar que dos jugadores tengan el mismo nombre y
    * que dos avatares tengan mismo ID). Desde este constructor también se crea el avatar.
     */
    public Jugador(String nombre, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados) {
        this.nombre = nombre;//Se asigna el nombre del jugador.
        this.avatar = new Avatar(tipoAvatar, this, inicio, avCreados);//Se crea el avatar del jugador.
        this.fortuna=Valor.FORTUNA_INICIAL; //Cada jugador empieza con Valor.Fortunainicial
        this.gastos = 0; //Al crear el jugador, no ha realizado ningún gasto.
        this.enCarcel = false; //Al crear el jugador, no está en la car
        this.tiradasCarcel = 0; //Al crear el jugador, no ha tirado para salir de la carcel.
        this.vueltas = 0; //Al crear el jugador, no ha dado ninguna vuelta.
        this.propiedades = new ArrayList<>(); //Al crear el jugador, no tiene propiedades.
        this.vecesEnCarcel = 0;
        this.dineroPremios = 0;
        this.dineroSalida = 0;
    }


    public Jugador(String nombreJugador, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados) {
        this.nombre = nombreJugador;
        this.avatar = new Avatar(tipoAvatar, this, inicio, avCreados);
        this.fortuna=Valor.FORTUNA_INICIAL;
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.propiedades = new ArrayList<Propiedad>();.
        this.vecesEnCarcel = 0;
        this.dineroPremios = 0;
        this.dineroSalida = 0;
        this.dineroCobroAlquileres = 0;
        this.dineroPagoAlquileres = 0;
        this.dineroTasasImpuestos = 0;
        this.dineroInvertido = 0;
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
    public void cobrarAlquiler(Propiedad propiedad){
        float alquiler = propiedad.getAlquiler();
        this.sumarFortuna(-alquiler);
        this.sumarGastos(0);
        this.sumarDineroTasasImpuestos(alquiler);
        propiedad.sumarRentabilidad(alquiler);
        getJuego().getConsola().imprimir("Al jugador" + this.getNombre() + "se le ha cobrado" + alquiler + "€ de alquiler.");
    }

    public void encarcelar(Tablero tablero) {
        Casilla casillaCarcel = tablero.encontrar_casilla("Carcel");
        if (casillaCarcel == null) {
            System.err.println("Error crítico: No se ha encontrado la casilla 'Carcel'.");
            return;
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
            if(c.getTipo().equals("Servicio")){
                numServicios++;
            }
        }
        return numServicios;
    }
    public int numeroDeTransportes(){
        int numTransportes = 0;
        for(Casilla c: this.getPropiedades()){
            if(c.getTipo().equals("Transporte")){
                numTransportes++;
            }
        }
        return numTransportes;
    }
    public String edificiosEnPropiedad(){
        String edificiosFormato = "";
        ArrayList<Edificacion> edificaciones = new ArrayList<>();
        for(Casilla c: this.getPropiedades()){
            if (c instanceof Solar solar){
                edificaciones.addAll(solar.getEdificios());
        ArrayList<Edificacion> edificaciones = new ArrayList<Edificacion>();

        for(Propiedad c: this.getPropiedades()){

            for(Edificacion e: c.getEdificios()){
                 edificaciones.add(e);
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
    
    public void cobrarImpuesto(Juego juego){
        float impuesto = ((Impuesto) this.getAvatar().getLugar()).getImpuesto();
        this.sumarFortuna(-impuesto);
        this.sumarDineroTasasImpuestos(impuesto);
        juego.getTablero().aumentarBoteParking(impuesto);
        juego.getBanca().sumarFortuna(impuesto);
    }

    public void cobrarAlquiler(Propiedad p){
        float alquiler = p.getAlquiler();
        this.sumarFortuna(-alquiler);
        this.sumarDineroPagoAlquileres(alquiler);
        p.getDuenho().sumarFortuna(alquiler);
        p.getDuenho().sumarDineroCobroAlquileres(alquiler);
    }

    public int numeroDeTransportes(){
        int contador = 0;
        for(Casilla c: this.getPropiedades()){
            if(c instanceof Transporte){
                contador++;
            }

        }
        return contador;
    }

    public int numeroDeServicios(){
        int contador = 0;
        for(Casilla c: this.getPropiedades()){
            if(c instanceof Servicio){
                contador++;
            }

        }
        return contador;
    }
    public boolean esSolvente(){
        return this.fortuna >= 0;
    }


    //Cobra el impuesto correspondiente a la casilla en la que esta 
    // el jugador y actualizar estadisticas y bote del parking
    public void cobrarImpuesto(Juego juego){
        //Verificamos si acaso la casilla es una instancia de impuesto
        Casilla casilla = this.getAvatar().getLugar();
        if(casilla instanceof Impuesto){
            int impuesto = casilla.getImpuesto();
            this.sumarFortuna(-impuesto);
            this.sumarGastos(impuesto);
            this.sumarDineroTasasImpuestos(impuesto);
            juego.getTablero().aumentarBoteParking(impuesto);
            //Mensaje informativo
            juego.getConsola().imprimir("Al jugador" + this.getNombre() + "se le ha cobrado" + );
        }
    }

    

    //Getters y setters: 
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
    public void setFortuna(float fortuna) {
        this.fortuna = fortuna;
    }
    public float getGastos() {
        return gastos;
    }
    public void setGastos(float gastos) {
        this.gastos = gastos;
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
    public void setPropiedades(ArrayList<Casilla> propiedades) {
        this.propiedades = propiedades;
    }


    //Getters y setteres para las estadisticas
    public int getVecesEnCarcel(){
        return this.vecesEnCarcel;
    }

    public void setVecesEnCarcel(int vecesEnCarcel){
        this.vecesEnCarcel = vecesEnCarcel;
    }

    public float getDineroPremios(){
        return this.dineroPremios;
    }
    
    public void setDineroPremios(float dineroPremios){
        this.dineroPremios = dineroPremios;
    }

    public float getDineroSalida(){
        return this.dineroSalida;
    }

    public void setDineroSalida(float dineroSalida){
        this.dineroSalida = dineroSalida;
    }

    public float getDineroCobroAlquileres(){
        return this.dineroCobroAlquileres;
    }

    public void setDineroCobroAlquileres(float dineroCobroAlquileres){
        this.dineroCobroAlquileres = dineroCobroAlquileres;
    }

    public float getDineroPagoAlquileres(){
        return this.dineroPagoAlquileres;
    }

    public void setDineroPagoAlquileres(float dineroPagoAlquileres){
        this.dineroPagoAlquileres = dineroPagoAlquileres;
    }

    public float getDineroTasasImpuestos(){
        return this.dineroTasasImpuestos;
    }
    
    public void setDineroTasasImpuestos(float dineroTasasImpuestos){
        this.dineroTasasImpuestos = dineroTasasImpuestos;
    }

    public void setDineroInvertido(float valor){
        this.dineroInvertido = valor;
    }
    public float getDineroInvertido(){
        return this.dineroInvertido;
    }
    //Metodo para ver si acaso un jugador es solvente
    public boolean esSolvente(){
        if(this.getFortuna() > 0){
            return true;
        } return false;    
    }

    @Override
    public String toString(){
        String avatarInfo = (this.avatar != null) ? this.avatar.getId() : "(Banca)";
        return "Nombre: " + this.nombre +
               "\nAvatar: " + avatarInfo +
               "\nFortuna: " + this.fortuna +
               "\nPropiedades:" + this.propiedades +
               "\nEdificios:" + this.edificiosJugador();
               
    }

}
