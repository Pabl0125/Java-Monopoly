package monopoly.casillas;
import java.util.ArrayList;

import monopoly.Grupo;
import monopoly.Tablero;
import partida.*;

public class Propiedad extends Casilla{
    protected Jugador duenho;                     //Dueño de la casilla (por defecto sería la banca).
    protected float rentabilidad;                 //Rentabilidad de la casilla contando alquileres cobrados y beneficios aportados por edificaciones                o
    protected Grupo grupo;                        //Grupo al que pertenece la casilla (si es solar).
    protected float valor;                        //Valor de esa casilla
    protected float impuesto;

    public Propiedad(){
        super();                    //Llamammos al contructor de la clase pade
        this.grupo = null;
        this.impuesto = 0;
        this.valor = 0;
        this.duenho = null;
        this.duenho.getPropiedades().add(this);
    }
    public Propiedad(String nombre, String tipo, int posicion, Jugador duenho, float impuesto){
        this();
        this.nombre = nombre;
        this.tipo = this.getClass().getName();  //El tipo de la casilla es equivalente al de la clase
        this.posicion= posicion;
        this.avatares = new ArrayList<>(); // Inicializar avatares
    }

    public String infoCasilla(){
        // Construir la lista de jugadores en la casilla (no carcel)
        StringBuilder jugadoresEnCasilla = new StringBuilder();
        if (this.avatares.isEmpty()) {
            jugadoresEnCasilla.append("Ninguno");
        } else {
            ArrayList<String> nombresJugadores = new ArrayList<String>();
            this.avatares.forEach(avatar -> nombresJugadores.add(avatar.getJugador().getNombre()));
            jugadoresEnCasilla.append(String.join(", ", nombresJugadores));
        }
        //Stringbuilder para jugadores en la carcel y sus tiradas
        StringBuilder jugadoresEnCasillaCarcel = new StringBuilder();
        if(this.avatares.isEmpty()){
            jugadoresEnCasillaCarcel.append("Ninguno");
        } else{
            ArrayList<String> nombresJugadoresCarcel = new ArrayList<String>();
            this.avatares.forEach(avatar -> nombresJugadoresCarcel.add("[" + avatar.getJugador().getNombre() + ", " + avatar.getJugador().getTiradasCarcel() + "]"));
            jugadoresEnCasillaCarcel.append(String.join(" ", nombresJugadoresCarcel));
        }
        String grupoInfo = (this.grupo != null) ? this.grupo.colorToNombreGrupo() : "N/A"; // Manejar grupo nulo

        switch(this.tipo) {
            case "Comunidad":
                return "Casilla de tipo" + this.tipo + ". ¡Toma una carta!";
            case "Suerte":
                return "Casilla de tipo" + this.tipo + ". ¡Toma una carta!";

            case "Impuesto": // Casillas de Impuestos
                return "Nombre: " + this.nombre +
                       "\nTipo: " + this.tipo +
                       "\nImpuesto a pagar: " + this.impuesto;

            case "Especial": // Casillas especiales como Salida o IrACarcel
                if(this.nombre.equals("Carcel")){
                    return "Nombre: " + this.nombre +
                       "\nTipo: " + this.tipo +
                       "\nJugadores: " + jugadoresEnCasillaCarcel.toString().trim();
                }
                if(this.nombre.equals("Salida")){
                    return "\nJugadores: " + jugadoresEnCasilla.toString().trim();
                }
                if(this.nombre.equals("Parking")){
                    return "Nombre: " + this.nombre +
                       "\nTipo: " + this.tipo ;
                }
                if(this.nombre.equals("IrACarcel")){
                    return "Esta casilla te envía directamente a la cárcel.";
                }

            case "Servicios":
            case "Transporte": // Casillas de Servicios o Transporte
                return "Nombre: " + this.nombre +
                       "\nTipo: " + this.tipo +
                       "\nValor: " + this.valor +
                       "\nDueño: " + this.duenho.getNombre() +
                       "\nJugadores: " + jugadoresEnCasilla.toString().trim();

            case "Solar":
                return "Nombre: " + this.nombre +
                       "\nTipo: " + this.tipo +
                       "\nValor: " + this.valor +
                       "\nPosición: " + this.posicion +
                       "\nDueño: " + this.duenho.getNombre() +
                       "\nGrupo: " + grupoInfo +
                       "\nAlquiler: " + this.impuesto +
                       "\nJugadores: " + jugadoresEnCasilla.toString().trim();
            
            default:
                return "Información no disponible para la casilla '" + this.nombre + "'.";
        }
    }

    public void comprarCasilla(Jugador solicitante, Jugador banca) {
        if (this.duenho.equals(banca)) { //Solo se puede comprar si el dueño es la banca.
            if (solicitante.getFortuna() >= this.valor) { //Comprobar que el jugador tiene saldo suficiente.
                solicitante.sumarGastos(this.valor); //Añadir el valor de la casilla a los gastos del jugador.
                solicitante.sumarFortuna(-this.valor); //Restar el importe correspondiente a la casilla que se ha pagado
                //Actulizamos las estadisticas
                solicitante.sumarDineroInvertido(valor);
        
                banca.sumarFortuna(this.valor);//Sumar el valor de la casilla al saldo de la banca.
                this.duenho = solicitante; //Cambiar el dueño de la casilla al jugador que la compra.
                System.out.println(solicitante.getNombre() + " ha comprado la casilla " + this.nombre + " por " + this.valor + "€.");
            } else {
                System.out.println("No tienes saldo suficiente para comprar esta casilla.");
            }
        } else {
            System.out.println("Esta casilla ya tiene dueño.");
        }
    }

    public void casillaEnVenta() {
    if (this.duenho != null && this.duenho.getNombre().equals("Banca") &&
        (this.tipo.equals("Solar") || this.tipo.equals("Transporte") || this.tipo.equals("Servicios"))) {
        
        System.out.print("{\n");
        System.out.println("nombre: " + this.nombre);
        System.out.print("tipo: " + this.tipo + ",\n");
        if (this.tipo.equals("Solar") && this.grupo != null) {
            System.out.print("grupo: " + this.grupo.colorToNombreGrupo() + ",\n");
        }
        System.out.println("valor: " + this.valor + "\n},");
        }
    }
    public void sumarRentabilidad(float cantidad){
        this.rentabilidad += cantidad;
        this.grupo.sumarRentabilidad(cantidad);
    }  


    ////////////////GETERS Y SETTERS////////////////
    
    public float getValor() {
        return valor;
    }
        public void setValor(float valor) {
        this.valor = valor;
    }
    public Jugador getDuenho(){
        return this.duenho;
    }
    public Jugador setDuenho(){
        return this.duenho;
    }
    public float getRentabilidad(){
        return this.rentabilidad;
    }

    public void setRentabilidad(float rentabilidad){
        this.rentabilidad = rentabilidad;
    }

    public Grupo getGrupo(){
        return this.grupo;
    }
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
        /*Metodo llamado cada vez que se cae en una casilla evaluando que hacer en cada caso*/
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero) {
        switch (this.tipo) {
            case "Solar":
            case "Transporte":
            case "Servicios":
                return pagoAlquiler(actual, banca, tirada, tablero);

            case "Especial":
                switch (this.nombre){
                    case "Parking":
                        System.out.println("¡" + actual.getNombre() + " ha llegado al parking!");
                        //Si cae en el parking tenemos que ver si hay bote y si lo hay, actualizar el saldo
                        //tanto del jugador como el valor de la casilla
                        if(this.valor > 0){
                            System.out.println("El parking tiene dinero en el bote!");
                            System.err.println(actual.getNombre() + "ha recibido el premio del bote. (" + this.valor + "€)");
                            actual.sumarFortuna(this.valor);
                            actual.sumarDineroPremios(this.valor);
                            this.valor = 0;
                        } else {
                            System.out.println("El parking no tiene dinero en el bote.");
                        }
                        break;
                    case "IrACarcel":
                        System.out.println("¡" + actual.getNombre() + " va a la cárcel!");
                        actual.encarcelar(tablero);
                        // Ir a la cárcel no cuesta dinero en el acto, por lo que no 
                        // puedes quebrar solo por caer aquí.
                        return true;
                    case "Suerte":
                        // Se notifica que el jugador debe tomar una carta de Suerte.
                        // La ejecución efectiva de la carta (movimientos, pagos, cárcel, ...)
                        // la debe manejar el `Menu` porque necesita el estado de la partida
                        // (contadores de cartas, referencia a jugadores, tablero, etc.).
                        System.out.println(actual.getNombre() + " ha caído en " + 
                            actual.getAvatar().getLugar().getNombre() + " y debe tomar una carta de Suerte.");
                            
                        return true;
                    case "CajaComunidad":
                        // Igual que con Suerte: solo se notifica aquí. La ejecución
                        // real de la carta la realiza `Menu`.
                        System.out.println(actual.getNombre() + " ha caído en " + 
                            actual.getAvatar().getLugar().getNombre() + " y debe tomar una carta de Caja de Comunidad.");
                        return true;
                    default:
                        // Otras casillas especiales que no requieren acción de pago.
                        return true;
                }
            default:
                return true;
        }
    }
}