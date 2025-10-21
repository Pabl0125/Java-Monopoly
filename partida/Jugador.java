package partida;

import java.util.ArrayList;

import monopoly.*;

public class Jugador {

    //Atributos:
    private String nombre; //Nombre del jugador
    private Avatar avatar; //Avatar que tiene en la partida.
    private float fortuna; //Dinero que posee.
    private float gastos; //Gastos realizados a lo largo del juego.
    private boolean enCarcel; //Será true si el jugador está en la carcel
    private int tiradasCarcel; //Cuando está en la carcel, contará las tiradas sin éxito que ha hecho allí para intentar salir (se usa para limitar el numero de intentos).
    private int vueltas; //Cuenta las vueltas dadas al tablero.
    private ArrayList<Casilla> propiedades; //Propiedades que posee el jugador.

    //Constructor vacío. Se usará para crear la banca.
    public Jugador() {
        this.nombre="Banca"; //Se asigna el nombre del jugador.
        this.avatar = null; //La banca no tiene avatar.
        this.fortuna=Valor.FORTUNA_BANCA; //La banca empieza con 1.
        this.gastos = 0; //Al crear el jugador, no ha realizado ningún gasto.
        this.enCarcel = false; //Al crear el jugador, no está en la carcel.
        this.tiradasCarcel = 0; //Al crear el jugador, no ha tirado para salir de la carcel.
        this.vueltas = 0; //Al crear el jugador, no ha dado ninguna vuelta.
        this.propiedades=new ArrayList<Casilla>();//Al crear el jugador, la banca posee todas las propiedadades.
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
        this.propiedades = new ArrayList<Casilla>(); //Al crear el jugador, no tiene propiedades.
    }
    
    //Otros métodos:
    //Método para añadir una propiedad al jugador. Como parámetro, la casilla a añadir.
    public void anhadirPropiedad(Casilla casilla) {
        this.propiedades.add(casilla); //Se anhade al arraylist la casilla especificada como parametro
    }

    //Método para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Casilla casilla) {
        for(Casilla propiedad: propiedades){
            if (casilla.equals(propiedad)) {
                propiedades.remove(propiedad);
                break;
            }
        }
    }

    //Método para añadir fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
    public void sumarFortuna(float valor) {
        this.fortuna = this.fortuna + valor;
    }

    //Método para sumar gastos a un jugador.
    //Parámetro: valor a añadir a los gastos del jugador (será el precio de un solar, impuestos pagados...).
    public void sumarGastos(float valor) {
        this.gastos = this.gastos + valor;
    }

    /*Método para establecer al jugador en la cárcel. 
    * Se requiere disponer de las casillas del tablero para ello (por eso se pasan como parámetro).*/
    public void encarcelar(Tablero tablero) {
        Casilla casillaCarcel = tablero.encontrar_casilla("Carcel");
        if (casillaCarcel == null) {
            System.err.println("Error crítico: No se ha encontrado la casilla 'Carcel'.");
            return;
        }

        // 1. Eliminar el avatar de su casilla actual
        this.avatar.getLugar().eliminarAvatar(this.avatar);
        // 2. Mover el avatar a la cárcel
        this.avatar.setLugar(casillaCarcel);
        casillaCarcel.anhadirAvatar(this.avatar);
        this.enCarcel=true;
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
    public ArrayList<Casilla> getPropiedades() {
        return propiedades;
    }
    public void setPropiedades(ArrayList<Casilla> propiedades) {
        this.propiedades = propiedades;
    }
    
    
    @Override
    public String toString(){
        String avatarInfo = (this.avatar != null) ? this.avatar.getId() : "N/A (Banca)";
        return "Nombre: " + this.nombre +
               "\nAvatar: " + avatarInfo +
               "\nFortuna: " + this.fortuna +
               "\nPropiedades:" + this.propiedades;
    }

}
