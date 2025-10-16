package monopoly;

import partida.*;
import java.util.ArrayList;


public class Casilla {

    //Atributos:
    private String nombre; //Nombre de la casilla
    private String tipo; //Tipo de casilla (Solar, Especial, Transporte, Servicios, Comunidad, Suerte y Impuesto).
    private float valor; //Valor de esa casilla (en la mayoría será valor de compra, en la casilla parking se usará como el bote).
    private int posicion; //Posición que ocupa la casilla en el tablero (entero entre 1 y 40).
    private Jugador duenho; //Dueño de la casilla (por defecto sería la banca).
    private Grupo grupo; //Grupo al que pertenece la casilla (si es solar).
    private float impuesto; //Cantidad a pagar por caer en la casilla: el alquiler en solares/servicios/transportes o impuestos.
    private float hipoteca; //Valor otorgado por hipotecar una casilla
    private ArrayList<Avatar> avatares; //Avatares que están situados en la casilla.

    //Constructores:
    public Casilla() {
    }//Parámetros vacíos

    /*Constructor para casillas tipo Solar, Servicios o Transporte:
    * Parámetros: nombre casilla, tipo (debe ser solar, serv. o transporte), posición en el tablero, valor y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, float valor, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion= posicion;
        this.valor = valor;
        this.duenho = duenho;
    }

    /*Constructor utilizado para inicializar las casillas de tipo IMPUESTOS.
    * Parámetros: nombre, posición en el tablero, impuesto establecido y dueño.
     */
    public Casilla(String nombre, int posicion, float impuesto, Jugador duenho) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.impuesto = impuesto;
        this.duenho = duenho;
    }

    /*Constructor utilizado para crear las otras casillas (Suerte, Caja de comunidad y Especiales):
    * Parámetros: nombre, tipo de la casilla (será uno de los que queda), posición en el tablero y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion = posicion;
        this.duenho = duenho;

    }

    //Método utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {
        this.avatares.add(av);
    }

    //Método utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        this.avatares.remove(av);
    }

    /*Método para evaluar qué hacer en una casilla concreta. Parámetros:
    * - Jugador cuyo avatar está en esa casilla.
    * - La banca (para ciertas comprobaciones).
    * - El valor de la tirada: para determinar impuesto a pagar en casillas de servicios.
    * Valor devuelto: true en caso de ser solvente (es decir, de cumplir las deudas), y false
    * en caso de no cumplirlas.*/
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {
        
        switch (Casilla.this.tipo) {
            case "Solar":
                if(!this.duenho.equals(banca) && !this.duenho.equals(actual)){  //Caso de que la casilla sea de un tercero.
                    float impuestoAPagar = this.impuesto; //Valor del impuesto a pagar
                    if (actual.getFortuna() >= impuestoAPagar) { //Comprobar que el jugador tiene saldo suficiente.
                        actual.sumarGastos(impuestoAPagar); //Añadir el valor del impuesto a los gastos del jugador.
                        this.duenho.sumarFortuna(impuestoAPagar);//Sumar el valor del impuesto al saldo del dueño de la casilla.
                        System.out.println(actual.getNombre() + " ha pagado " + impuestoAPagar + "€ a " + this.duenho.getNombre() + " por caer en " + this.nombre + ".");
                        return true; //El jugador es solvente.
                    } else {
                        System.out.println(actual.getNombre() + " no tiene saldo suficiente para pagar el alquiler de " + this.nombre + ".");
                        return false; //El jugador no es solvente.
                    }
                } 
                else if (this.duenho.equals(banca)) { //Caso de que el dueño sea la banca.
                    System.out.println("La casilla " + this.nombre + " está en venta por " + this.valor + "€.");
                    if (actual.getFortuna() >= this.valor) {
                        System.out.println("Usa el comando 'comprar' para adquirirla.");
                        return true; //El jugador es solvente.
                    } else {
                        System.out.println("No tienes saldo suficiente para comprar esta casilla.");\
                        return false; //El jugador no es solvente.
                    }
                } else { //Caso de que el dueño sea el mismo jugador
                    System.out.println("Has caído en una de tus propiedades: " + this.nombre + ".");
                }
                break;
            case "Transporte":
                if(!this.duenho.equals(banca) && !this.duenho.equals(actual)){

                    float impuestoAPagar = this.impuesto; //Valor del impuesto a pagar.
                    if (actual.getFortuna() >= impuestoAPagar) { //Comprobar que el jugador tiene saldo suficiente.
                        actual.sumarGastos(impuestoAPagar); //Añadir el valor del impuesto a los gastos del jugador.
                        this.duenho.sumarFortuna(impuestoAPagar);//Sumar el valor del impuesto al saldo del dueño de la casilla.
                        System.out.println(actual.getNombre() + " ha pagado " + impuestoAPagar + "€ a " + this.duenho.getNombre() + " por caer en " + this.nombre + ".");
                        return true; //El jugador es solvente.
                    } else {
                        System.out.println(actual.getNombre() + " no tiene saldo suficiente para pagar el alquiler de " + this.nombre + ".");
                        return false; //El jugador no es solvente.
                    }
                } else if (this.duenho.equals(banca)) {
                    System.out.println("La casilla " + this.nombre + " está en venta por " + this.valor + "€. Usa el comando 'comprar' para adquirirla.");
                } else {
                    System.out.println("Has caído en una de tus propiedades: " + this.nombre + ".");
                }
            case "Servicios":
                if(!this.duenho.equals(banca) && !this.duenho.equals(actual)){
                    //El jugador debe pagar el impuesto (alquiler) al dueño de la casilla.
                    float impuestoAPagar = this.impuesto * tirada; //Valor del impuesto a pagar.
                    if (actual.getFortuna() >= impuestoAPagar) { //Comprobar que el jugador tiene saldo suficiente.
                        actual.sumarGastos(impuestoAPagar); //Añadir el valor del impuesto a los gastos del jugador.
                        this.duenho.sumarFortuna(impuestoAPagar);//Sumar el valor del impuesto al saldo del dueño de la casilla.
                        System.out.println(actual.getNombre() + " ha pagado " + impuestoAPagar + "€ a " + this.duenho.getNombre() + " por caer en " + this.nombre + ".");
                        return true; //El jugador es solvente.
                    } else {
                        System.out.println(actual.getNombre() + " no tiene saldo suficiente para pagar el alquiler de " + this.nombre + ".");
                        return false; //El jugador no es solvente.
                    }
                } else if (this.duenho.equals(banca)) {
                    System.out.println("La casilla " + this.nombre + " está en venta por " + this.valor + "€. Usa el comando 'comprar' para adquirirla.");
                } else {
                    System.out.println("Has caído en una de tus propiedades: " + this.nombre + ".");
                }
                break;
            default:
                
                break;
        }

    }

    /*Método usado para comprar una casilla determinada. Parámetros:
    * - Jugador que solicita la compra de la casilla.
    * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
    public void comprarCasilla(Jugador solicitante, Jugador banca) {
        if (this.duenho.equals(banca)) { //Solo se puede comprar si el dueño es la banca.
            if (solicitante.getFortuna() >= this.valor) { //Comprobar que el jugador tiene saldo suficiente.
                solicitante.setGastos(this.valor); //Añadir el valor de la casilla a los gastos del jugador.
                banca.setFortuna(this.valor);//Sumar el valor de la casilla al saldo de la banca.
                this.duenho = solicitante; //Cambiar el dueño de la casilla al jugador que la compra.
                System.out.println(solicitante.getNombre() + " ha comprado la casilla " + this.nombre + " por " + this.valor + "€.");
            } else {
                System.out.println("No tienes saldo suficiente para comprar esta casilla.");
            }
        } else {
            System.out.println("Esta casilla ya tiene dueño.");
        }
    }


    /*Método para añadir valor a una casilla. Utilidad:
    * - Sumar valor a la casilla de parking.
    * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de todos los jugadores.
    * Este método toma como argumento la cantidad a añadir del valor de la casilla.*/
    public void sumarValor(float suma) {

        if (this.tipo.equals("Parking")){
            this.valor += suma;
        } else if (this.tipo.equals("Solar") && this.duenho.getNombre().equals("Banca")) {
            this.valor += suma;
        }
    }

    /*Método para mostrar información sobre una casilla.
    * Devuelve una cadena con información específica de cada tipo de casilla.*/
    public String infoCasilla() {
        if (this.nombre.equals("Caja de Comunidad") || this.nombre.equals("Suerte") || this.nombre.equals("IrACarcel")){
            return null;
        }
        return "Nombre: " + this.nombre+
        "\nTipo: " + this.tipo +
        "\nValor: " + this.valor + 
        "\nPosición: " + this.posicion +
        "\nDueño: " + this.duenho +
        "\nGrupo: " + this.grupo +
        "\nImpuesto: " + this.impuesto +
        "\nHipoteca: " + this.hipoteca +
        "\nAvatares: " + this.avatares;
    }

    /* Método para mostrar información de una casilla en venta.
     * Valor devuelto: texto con esa información.
     */
    public String casEnVenta() {
    }

    //Getters y setters:
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }        
    public int getPosicion() {
        return this.posicion;
    }
    public String getNombre() {
        return this.nombre;
    }

@Override
public boolean equals(Object obj) {
    if (this == obj) return true; // Si son el mismo objeto
    if (obj == null || getClass() != obj.getClass()) return false; // Comprobar null y clase
    Casilla otraCasilla = (Casilla) obj; // Hacemos casting seguro
    return this.nombre != null && this.nombre.equals(otraCasilla.nombre); // Comparamos nombres
}

}
