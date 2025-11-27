package monopoly.casillas;
import java.util.ArrayList;

import monopoly.Tablero;
import monopoly.edificios.Edificacion;
import partida.*;

public final class Solar extends Propiedad{
    private ArrayList<Edificacion> edificios;   //ArrayList de edificaciones que posee la casilla
    private float hipoteca;                     //Valor otorgado por hipotecar una casilla
    private boolean estarHipotecada;            //Booleano que indica si la casilla ha sido hipotecada o no

    public Solar(){
        super();                            //Llamammos al contructor de la clase pade
        this.edificios = new ArrayList<>();
        this.hipoteca = 0;
        this.edificios = new ArrayList<>(); // Inicializar edificios
        this.hipoteca = hipoteca;
    }
    public Solar(float hipoteca){
        this.estarHipotecada = false;
        this.hipoteca = hipoteca;

    }
    public void eliminarEdificacion(Edificacion edificacion){
        this.edificios.remove(edificacion);
    }

    public float impuestoTotalCasilla(){
        float total = this.impuesto;
        for (Edificacion edificio : this.edificios) {
            total += edificio.getAlquiler();
        }
        return total;
    }
    public void construirEdificio(String tipoEdificio, float coste){
        if (this.tipo.equals("Solar")){
            this.valor += coste;
            System.out.println("Se ha construido un " + tipoEdificio + " en " + this.nombre + " por " + coste + "€.");
        } else {
            System.out.println("No se pueden construir edificios en esta casilla.");
        }
    }

    public ArrayList<Edificacion> getEdificios(){
        return this.edificios;
    }
    public void anhadirEdificios(Edificacion edificio){
        this.edificios.add(edificio);
    }
    public int getNumEdificaciones(){
        return this.edificios.size();
    }

    public float getHipoteca(){
        return this.hipoteca;
    }

    public float getImpuesto(){
        return this.impuesto;
    }
    public boolean getEstarHipotecada(){
        return this.estarHipotecada;
    }

    public void setEstarHipotecada(boolean estarHipotecada){
        this.estarHipotecada = estarHipotecada;
    }

    private boolean pagoAlquiler(Jugador actual, Jugador banca, int tirada, Tablero tablero){
        
        if(this.estarHipotecada){ //Comprobar si la casilla está hipotecada
            System.out.println("La casilla " + this.nombre + " está hipotecada. No se paga alquiler.");
            return true; //El jugador es solvente al no tener que pagar alquiler
        }
        if(!this.duenho.equals(banca) && !this.duenho.equals(actual)){  //Caso de que la casilla sea de un tercero.
            float impuestoAPagar = this.impuestoTotalCasilla(); //Valor del impuesto a 
            if(this.tipo.equals("Impuesto")){
                actual.sumarGastos(impuestoAPagar);
                actual.sumarFortuna(-impuestoAPagar);
                actual.sumarDineroTasasImpuestos(impuestoAPagar);
                tablero.aumentarBoteParking(impuestoAPagar);
                return true;
            }
            if (actual.getFortuna() >= impuestoAPagar) { //Comprobar que el jugador tiene saldo suficiente.
                actual.sumarGastos(impuestoAPagar); //Añadir el valor del impuesto a los gastos del jugador.
                this.duenho.sumarFortuna(impuestoAPagar);//Sumar el valor del impuesto al saldo del dueño de la casilla.
                //Actualizamos las estadisticas del duenho y del jugador actual que paga
                this.duenho.sumardineroCobroAlquileres(impuestoAPagar); 
                actual.sumarDineroPagoAlquileres(impuestoAPagar);
                this.sumarRentabilidad(impuestoAPagar);

                actual.sumarFortuna(-impuestoAPagar);
                System.out.println(actual.getNombre() + " ha pagado " + impuestoAPagar + "€ a " + this.duenho.getNombre() + " por caer en " + this.nombre + ".");
                return true; //El jugador es solvente
            } else {
                System.out.println(actual.getNombre() + " no tiene saldo suficiente para pagar el alquiler de " + this.nombre + ".");
                return false; //El jugador no es solvente.
            }
        }
        else if (this.duenho.equals(banca)) { //Caso de que el dueño sea la banca.
            System.out.println("La casilla " + this.nombre + " está en venta por " + this.valor + "€.");
            this.casEnVenta();  //Imprimimos la info de la casilla en venta
            if (actual.getFortuna() >= this.valor) {
                System.out.println("Usa el comando 'comprar' para adquirirla.");
                return true; //El jugador es solvente.
            } else {
                System.out.println("No tienes saldo suficiente para comprar esta casilla.");
                return false; //El jugador no es solvente.
            }
        } else { //Caso de que el dueño sea el mismo jugador
            System.out.println("Has caído en una de tus propiedades: " + this.nombre + ".");
            return true; //El jugador necesariamente es solvente al no tener que pagar por estar en su propiedad
        }
    }
}