package monopoly.casillas;
import java.util.ArrayList;
import monopoly.*;
import partida.*;

public abstract class Propiedad extends Casilla{
    private Jugador duenho;       //Dueño de la casilla (por defecto sería la banca).
    private float rentabilidad;   //Rentabilidad de la casilla contando alquileres cobrados y beneficios aportados por edificaciones                o
    private Grupo grupo;          //Grupo al que pertenece la casilla (si es solar).
                                    //NOTA: La logica de grupos se implementa fuera de la clase, si acaso una propiedad quiere
                                    //pertenecer a un grupo esta sera actualizada por el contructor o metodos correspondientes
                                    //de la clase grupo, dejando esta tarea en manos de la clase grupo
    private float valor;          //Valor de esa casilla
    private float alquiler;       //Suma que otros jugadores deberan abonar tras caer en la casilla

    public Propiedad(String nombre, int posicion, Juego juego){
        super(nombre,posicion,juego);                    //Llamammos al contructor de la clase padre
        this.valor = 0;
        this.alquiler = 0;
        this.grupo = null;
        this.duenho = juego.getBanca();     //OJO --> FALTA NOTIFICAR A LA BANCA QUE TIENE LA PROPIEDAD
        this.rentabilidad = 0;
    }
    ////////////////GETERS Y SETTERS////////////////
    public Jugador getDuenho(){
        return this.duenho;
    }
    public Jugador setDuenho(){
        return this.duenho;
    }
    public float getRentabilidad(){
        return this.rentabilidad;
    }
    public Grupo getGrupo(){
        return this.grupo;
    }
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
    public float getAlquiler() {
        return alquiler;
    }
    public float setAlquiler(float alquiler){
        return this.alquiler;
    }
    public float getValor() {
        return valor;
    }
    public void setValor(float valor) {
        this.valor = valor;
    }
    ////////////////METODOS GENERICOS////////////////

    public void sumarRentabilidad(float cantidad){
        this.rentabilidad += cantidad;
        this.grupo.sumarRentabilidad(cantidad);
    }
    //Si acaso la propiedad esta en venta la compra el juegdor solicitante, sino imprime un mensaje de error
    public void comprarPropiedad(Jugador solicitante) {
        if (this.duenho.equals(getJuego().getBanca())) {            //Solo se puede comprar si el dueño es la banca.
            if (solicitante.getFortuna() >= this.valor) {           //Comprobar que el jugador tiene saldo suficiente.
                solicitante.sumarGastos(this.valor);                //Añadir el valor de la casilla a los gastos del jugador.
                solicitante.sumarFortuna(-this.valor);              //Restar el importe correspondiente a la casilla que se ha pagado
                solicitante.sumarDineroInvertido(valor);            //Actulizamos las estadisticas
                getJuego().getBanca().sumarFortuna(this.valor);     //Sumar el valor de la casilla al saldo de la banca.
                this.duenho = solicitante;                          //Cambiar el dueño de la casilla al jugador que la compra.
                this.duenho.getPropiedades().add(this);             //El duenho tiene que anhadirlo a su lista de propiedades
                getJuego().getConsola().imprimir(solicitante.getNombre() + " ha comprado la casilla " + this.getNombre() + " por " + this.valor + "€.");
            }
            else getJuego().getConsola().imprimir("No tienes saldo suficiente para comprar esta casilla.");
        } 
        else getJuego().getConsola().imprimir("Esta casilla ya tiene dueño.");
    }

    public void infoCasillaEnVenta() {
    if (this.duenho != null && this.duenho.getNombre().equals("Banca")) {
        getJuego().getConsola().imprimir("La casilla " + this.getNombre() + " está en venta por " + this.getValor() + "€.");
        getJuego().getConsola().imprimir("{");
        getJuego().getConsola().imprimir("nombre: " + this.getNombre() + ",");
        getJuego().getConsola().imprimir("tipo: " + this.getTipo() + ",");
        getJuego().getConsola().imprimir("valor: " + this.valor + "\n},");
        if (this.getTipo().equals("Solar") && this.grupo != null) getJuego().getConsola().imprimir("grupo: " + this.grupo.colorToNombreGrupo() + ",\n");
        }
    }
}