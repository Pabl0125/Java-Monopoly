package monopoly.casillas;

import partida.*;
import java.util.ArrayList;

import monopoly.Grupo;
import monopoly.Tablero;
import monopoly.edificios.Edificacion;

public class Casilla{

    //Atributos:
    private String nombre;                      //Nombre de la casilla
    private String tipo;                        //Tipo de casilla (Solar, Especial, Transporte, Servicios, Comunidad, Suerte y Impuesto).
    private float valor;                        //Valor de esa casilla (en la mayoría será valor de compra, en la casilla parking se usará como el bote).
    private int posicion;                       //Posición que ocupa la casilla en el tablero (entero entre 1 y 40).
    private Jugador duenho;                     //Dueño de la casilla (por defecto sería la banca).
    private Grupo grupo;                        //Grupo al que pertenece la casilla (si es solar).
    private float impuesto;                     //Cantidad a pagar por caer en la casilla: el alquiler en solares/servicios/transportes o impuestos.
    private float hipoteca;                     //Valor otorgado por hipotecar una casilla
    private ArrayList<Avatar> avatares; //Av    atares que están situados en la casilla.
    private ArrayList<Edificacion> edificios;   //ArrayList de edificaciones que posee la casilla
    private boolean estarHipotecada;            //Booleano que indica si la casilla ha sido hipotecada o no
    private int vecesVisitada;                  //Número de veces que ha sido visitada la casilla
    private float rentabilidad;                 //Rentabilidad de la casilla contando alquileres cobrados y beneficios aportados por edificaciones                o
    
    //Constructores:
    public Casilla() {
        this.tipo = "Desconocido";
        this.avatares = new ArrayList<>(); 
        this.edificios = new ArrayList<>();
        this.grupo = null;
        this.impuesto = 0;
        this.hipoteca = 0;
        this.valor = 0;
        this.posicion = -1;
        this.duenho = null;
        this.nombre = "Casilla desconocida";

    }//Parámetros vacíos

    /*Constructor para casillas tipo Solar, Servicios o Transporte:
    * Parámetros: nombre casilla, tipo (debe ser solar, serv. o transporte), posición en el tablero, valor y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, float valor, Jugador duenho, float impuesto, float hipoteca, boolean estarHipotecada) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion= posicion;
        this.valor = valor;
        this.impuesto = impuesto;
        this.duenho = duenho;
        this.duenho.getPropiedades().add(this);
        this.avatares = new ArrayList<>(); // Inicializar avatares
        this.edificios = new ArrayList<>(); // Inicializar edificios
        this.hipoteca = hipoteca;
        this.estarHipotecada = estarHipotecada;
    }

    public Casilla(String nombre, String tipo, int posicion, float valor, Jugador duenho, float impuesto){
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion= posicion;
        this.valor = valor;
        this.impuesto = impuesto;
        this.duenho = duenho;
        this.duenho.getPropiedades().add(this);
        this.avatares = new ArrayList<>(); // Inicializar avatares
        this.edificios = new ArrayList<>(); // Inicializar edificios
    }
    /*Constructor utilizado para inicializar las casillas de tipo IMPUESTOS.
    * Parámetros: nombre, posición en el tablero, impuesto establecido y dueño.
     */
    public Casilla(String nombre, int posicion, float impuesto, Jugador duenho) {
        this.tipo = "Impuesto";
        this.nombre = nombre;
        this.posicion = posicion;
        this.impuesto = impuesto;
        this.duenho = duenho;
        this.duenho.getPropiedades().add(this);
        this.avatares = new ArrayList<>(); // Inicializar avatares
    }

    /*Constructor utilizado para crear las otras casillas (Suerte, Caja de comunidad y Especiales):
    * Parámetros: nombre, tipo de la casilla (será uno de los que queda), posición en el tablero y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion = posicion;
        this.duenho = duenho;
        this.duenho.getPropiedades().add(this);
        this.avatares = new ArrayList<>(); // Inicializar avatares

    }

    //Método utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {
        this.avatares.add(av);
    }

    //Método utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        this.avatares.remove(av);
    }

    //Método para evaluar qué hacer en una casilla concreta
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
                // Casillas "neutrales" como la "Salida" (donde solo cobras si pasas, 
                // no si caes) o cualquier otra que no esté definida.
                // No requieren acción de pago.
                return true;
        }
    }

    /*Metodo usado cuando un jugador cae en una casilla y debe pagar el alquiler correspondiente a un tercero
     * cuando la casilla en la que cae no le pertenece
    */
    //NOTA: Se sabe de antemano que la casilla no es de un tipo incompatible
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


    /*Método usado para comprar una casilla determinada. Parámetros:
    * - Jugador que solicita la compra de la casilla.
    * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
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

    /* Método para mostrar información de una casilla en venta.
     * Valor devuelto: texto con esa información.
     */
    public void casEnVenta() {
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

    //Contruir un edificio en una casilla solar
    public void construirEdificio(String tipoEdificio, float coste){
        if (this.tipo.equals("Solar")){
            this.valor += coste;
            System.out.println("Se ha construido un " + tipoEdificio + " en " + this.nombre + " por " + coste + "€.");
        } else {
            System.out.println("No se pueden construir edificios en esta casilla.");
        }

    }

    public float impuestoTotalCasilla(){
        float total = this.impuesto;
        for (Edificacion edificio : this.edificios) {
            total += edificio.getAlquiler();
        }
        return total;
    }

    public void sumarRentabilidad(float cantidad){
        this.rentabilidad += cantidad;
        this.grupo.sumarRentabilidad(cantidad);
    }   

    public void visitarCasilla(){
        this.vecesVisitada += 1;
    }


    //Getters y setters:

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }        
    public int getPosicion() {
        return this.posicion;
    }
    public String getNombre() {
        return this.nombre;
    }

    public float getValor() {
        return valor;
    }
    public void setValor(float valor) {
        this.valor = valor;
    }
    public String getTipo() {
        return this.tipo;
    }
    public Jugador getDuenho(){
        return this.duenho;
    }

    public float getHipoteca(){
        return this.hipoteca;
    }

    public float getImpuesto(){
        return this.impuesto;
    }
    public ArrayList<Avatar> getAvatares(){
        return this.avatares;
    }
    public Grupo getGrupo(){
        return this.grupo;
    }
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
    public ArrayList<Edificacion> getEdificios(){
        return this.edificios;
    }

    public void setEdificios(Edificacion edificio){
        this.edificios.add(edificio);
    }

    public boolean getEstarHipotecada(){
        return this.estarHipotecada;
    }

    public void setEstarHipotecada(boolean estarHipotecada){
        this.estarHipotecada = estarHipotecada;
    }

    public float getRentabilidad(){
        return this.rentabilidad;
    }

    public void setRentabilidad(float rentabilidad){
        this.rentabilidad = rentabilidad;
    }

    public int getVecesVisitada(){
        return this.vecesVisitada;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Si son el mismo objeto
        if (obj == null || getClass() != obj.getClass()) return false; // Comprobar null y clase
        Casilla otraCasilla = (Casilla) obj; // Hacemos casting seguro
        return this.nombre != null && this.nombre.equals(otraCasilla.nombre); // Comparamos nombres
    }
    @Override
    public String toString(){
        return nombre;
    }
    public void eliminarEdificacion(Edificacion edificacion){
        this.edificios.remove(edificacion);
    }
    public int getNumEdificaciones(){
        return this.edificios.size();
    }

}