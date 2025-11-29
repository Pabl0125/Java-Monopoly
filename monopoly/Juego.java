package monopoly;

import monopoly.casillas.Casilla;
import monopoly.edificios.*;
import monopoly.interfaces.Comando;
import monopoly.interfaces.Consola;
import monopoly.cartas.*;
import monopoly.edificios.Edificacion;
import monopoly.interfaces.Comando;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Iterator;
import java.util.stream.*;
import java.lang.Thread;

import partida.*;



public class Juego implements Comando{

    //Atributos
    public static ConsolaNormal consola;//Implementacion de la interfaz Consola de forma estática para que todos los métodos puedan usarla
    private ArrayList<Jugador> jugadores; //Jugadores de la partida.
    private ArrayList<Avatar> avatares; //Avatares en la partida.
    private ArrayList<Carta> mazoSuerte; //Mazo de cartas de suerte.
    private ArrayList<Carta> mazoCajaComunidad; //Mazo de cartas de caja de comunidad.
    private int indiceSuerte; //Índice de la siguiente carta de suerte a sacar.
    private int indiceCajaComunidad; //Índice de la siguiente carta de caja de comunidad a sacar.
    private int turno = 0; //Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    private int lanzamientos; //Variable para contar el número de lanzamientos de un jugador en un turno.
    private Tablero tablero; //Tablero en el que se juega.
    private Dado dado1; //Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    private Jugador banca; //El jugador banca.
    private boolean tirado; //Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean solvente; //Booleano para comprobar si el jugador que tiene el turno es solvente, es decir, si ha pagado sus deudas.
    private int numCartaSuerte; //Número de carta de suerte que se va a sacar.
    private int numCartaCajaCom; //Número de carta de caja de comunidad que se va a sacar.
    
    

    // Método para inciar una partida: crea los jugadores y avatares.
    public void iniciarPartida() {
        //Crear jugadores y avatares.
        inicializarMazos();
        int indiceSuerte = 0;
        int indiceCajaComunidad = 0;
        jugadores = new ArrayList<Jugador>();
        avatares = new ArrayList<Avatar>();
        banca = new Jugador();
        jugadores.add(banca);
        tablero = new Tablero(banca);
        turno = 1;
        dado1 = new Dado();
        dado2 = new Dado();
        numCartaSuerte = 1;
        numCartaCajaCom = 1;
        
    }
    //Metodo publico para obtener el numero de turno del juego
    public int getTurno(){
        return turno;
    }
    //Metodo publico para obtener la instancia del objeto Jugador Actual
    public Jugador getJugadorActual(){
        return jugadores.get(turno);
    }
    //Metodo para obtener el tablero de juego actual
    public Tablero getTablero(){
        return tablero;
    }
    public ConsolaNormal getConsola(){
        return consola;
    }
    //Mentodo para obtener el jugador Banca de juego actual
    public Jugador getBanca(){
        return banca;
    }
    
    public void lecturaFichero(String fichero){
        File file = new File(fichero);
        try(Scanner sc = new Scanner(file)){
            while(sc.hasNextLine()){
                String linea = sc.nextLine();
                analizarComando(linea);
                try{
                    Thread.sleep(0); //Se puede modificar este valor para incrementar el delay entre comandoas
                }
                catch(Exception e){
                    consola.imprimir("Error en el sleep");
                    return;
                }
            }
        } catch (FileNotFoundException e){
            consola.imprimir("Error: Fichero no encontrado.");
        }
    }

    private void inicializarMazos() {
        mazoSuerte = new ArrayList<>();
        // Se añaden las cartas de Suerte en orden
        mazoSuerte.add(new CartaSuerte(1, "Decides hacer un viaje de placer. Avanza hasta Solar19. Si pasas por la casilla de Salida, cobra 2.000.000€."));
        mazoSuerte.add(new CartaSuerte(2, "Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar los 2.000.000€."));
        mazoSuerte.add(new CartaSuerte(3, "¡Has ganado el bote de la lotería! Recibe 1.000.000€."));
        mazoSuerte.add(new CartaSuerte(4, "Has sido elegido presidente de la junta directiva. Paga a cada jugador 250.000€."));
        mazoSuerte.add(new CartaSuerte(5, "¡Hora punta de tráfico! Retrocede tres casillas."));
        mazoSuerte.add(new CartaSuerte(6, "Te multan por usar el móvil mientras conduces. Paga 150.000€."));
        mazoSuerte.add(new CartaSuerte(7, "Avanza hasta la casilla de transporte más cercana. Si no tiene dueño, puedes comprarla. Si tiene dueño, paga al dueño el doble de la operación indicada."));


        mazoCajaComunidad = new ArrayList<>();
        // Se añaden las cartas de Caja de Comunidad en orden
        mazoCajaComunidad.add(new CartaCajaComunidad(1, "Paga 500.000€ por un fin de semana en un balneario de 5 estrellas."));
        mazoCajaComunidad.add(new CartaCajaComunidad(2, "Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar los 2.000.000€."));
        mazoCajaComunidad.add(new CartaCajaComunidad(3, "Colócate en la casilla de Salida. Cobra 2.000.000€."));
        mazoCajaComunidad.add(new CartaCajaComunidad(4, "Devolución de Hacienda. Cobra 500.000€."));
        mazoCajaComunidad.add(new CartaCajaComunidad(5, "Retrocede hasta Solar1 para comprar antigüedades exóticas."));
        mazoCajaComunidad.add(new CartaCajaComunidad(6, "Ve a Solar20 para disfrutar del San Fermín.\\nSi pasas por la casilla de Salida, cobra 2.000.000€."));
    }

    public void sacarCartaSuerte(Jugador jugadorActual) {
        // Se saca la carta que indica el índice
        Carta carta = mazoSuerte.get(indiceSuerte);
        
        // Se ejecuta su acción
        carta.accion(this.tablero, jugadorActual, this.jugadores);
        
        // Se avanza el índice para la próxima vez, volviendo al inicio si es necesario
        indiceSuerte = (indiceSuerte + 1) % mazoSuerte.size();
    }

    public void sacarCartaCajaComunidad(Jugador jugadorActual) {
        // Se saca la carta que indica el índice
        Carta carta = mazoCajaComunidad.get(indiceCajaComunidad);

        // Se ejecuta su acción
        carta.accion(this.tablero, jugadorActual, this.jugadores);

        // Se avanza el índice y se vuelve al principio si se llega al final
        indiceCajaComunidad = (indiceCajaComunidad + 1) % mazoCajaComunidad.size();
    }

    /**
    * Método que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetro: cadena de caracteres (el comando).
    */
    private void analizarComando(String comando) {
        consola.imprimir("\n>> " + comando );     //Impresion del comando insertado
        String[] partesComando = comando.split(" ");
        consola.imprimir("");
            if(partesComando.length > 0){
                String comandoPrincipal = partesComando[0];
            switch (comandoPrincipal) {
                case "crear":
                    if(partesComando[1].equals("jugador") && !partesComando[3].isEmpty()){ //Si se selecciona la opcion jugador dentro de crear y se anhade un campo avatar 
                        crearJugador(partesComando[2], partesComando[3]);
                    }
                    else System.err.println("Invalid command");
                    break;
                    
                case "jugador":
                    if (partesComando.length == 1) {
                        mostrarJugadorTurno();
                    }
                    else System.err.println("Invalid command");
                    break;

                case "listar":
                    if(partesComando.length==2 && partesComando[1].equals("jugadores")){
                        this.listarJugadores();
                    }
                    else if(partesComando.length ==2 && partesComando[1].equals("enventa")){
                        this.listarEnVenta();
                    }
                    else if(partesComando.length == 2 && partesComando[1].equals("edificios")){
                        this.listarEdificios();
                    }
                    else if(partesComando.length == 3 && partesComando[1].equals("edificios")){
                        this.listarEdificiosPorGrupo(partesComando[2]);
                    }
                    else System.err.println("Invalid command");    
                    break;
                case "lanzar":
                    if (partesComando.length == 2 && partesComando[1].equals("dados")){
                            lanzarDados();
                        }
                    else if(partesComando.length ==3 && partesComando[1].equals("dados") && partesComando[2].contains("+") && partesComando[2].length()==3){
                        lanzarDados(partesComando[2]);
                    }
                    else System.err.println("Invalid command");  
                    break;
                case "acabar":
                    if(partesComando.length ==2 && partesComando[1].equals("turno")){
                        this.acabarTurno();
                    }
                    else System.err.println("Invalid command");
                    break;
                case "salir":
                    if(partesComando.length ==2 && partesComando[1].equals("carcel")){
                        this.salirCarcel();
                    }
                    else System.err.println("Invalid command");
                    break;
                case "describir":
                    if(partesComando.length == 3 && partesComando[1].equals("jugador") && this.encontrar_jugador(partesComando[2])){
                        this.descJugador(partesComando[2]);
                    }
                    else if (partesComando.length == 2 && ((tablero.encontrar_casilla(partesComando[1]) instanceof Casilla))) {
                        this.descCasilla(partesComando[1]);
                    }
                    else System.err.println("Invalid command");  
                    break;
                case "comprar":
                    if(partesComando.length ==2 && ((tablero.encontrar_casilla(partesComando[1]) instanceof Casilla))){
                        comprar(partesComando[1]);
                    }
                    else System.err.println("Invalid command");
                    break;
                case "vender":
                    if(partesComando.length == 4){
                        // vender [tipo] [casilla] [cantidad]
                        venderEdificacion(partesComando[1], partesComando[2], partesComando[3]);
                    }
                    else System.err.println("Invalid command");
                    break;
                case "estadisticas":
                    if(partesComando.length == 1){
                        mostrarEstadisticasJuego();
                    }
                    else if(partesComando.length == 2){
                        String jugador = partesComando[1];
                        mostrarEstadisticas(jugador);
                    }
                    else System.err.println("Invalid command");
                    break;
                case "edificar":
                    //casa
                    if(partesComando.length == 2 && partesComando[1].equals("casa")) edificar("casa");
                    else if(partesComando.length == 2 && partesComando[1].equals("hotel")) edificar("hotel");
                    else if(partesComando.length == 3 && partesComando[1].equals("pista") && partesComando[2].equals("deportiva")) edificar("pista deportiva");
                    else if(partesComando.length == 2 && partesComando[1].equals("piscina")) edificar("piscina");
                    else System.err.println("Invalid command");
                    break;
                case "ver":
                    if(partesComando.length == 2 && partesComando[1].equals("tablero")){
                        tablero.imprimirTablero();
                    }
                    else System.err.println("Invalid command");
                    break;
                case "hipotecar":
                    if(partesComando.length == 2){
                        hipotecarPropiedad(partesComando[1]);
                    }
                    else System.err.println("Invalid command");
                    break;
                case "deshipotecar":
                    if(partesComando.length == 2){
                        deshipotecar(partesComando[1]);
                    }
                    else System.err.println("Invalid command");
                    break;
                
                default:
                    // Se ejecuta si el comando no coincide con ningún case
                    consola.imprimir("Error: Comando desconocido '" + comandoPrincipal + "'");
                    break;
            }
        }
    }

    /*Método que realiza las acciones asociadas al comando 'crear jugador'.
    Parametro: Comando introducido + nombre del jugador
    */
   @Override 
    public void crearJugador(String nombre, String tipoAvatar){
        //Comprobamos si existe ya un jugador con el nombre introducido
        for(Jugador j: jugadores){
            if(j.getNombre().equals(nombre)){
                consola.imprimir("Ya existe un jugador con el nombre '" + nombre + "'.");
                return;
            }
        }
        
        //Comprobamos si el tipo de avatar es válido
        for(Avatar av: avatares){
            if(av.getTipo().equals(tipoAvatar)){
                consola.imprimir("El tipo de avatar '" + tipoAvatar + "' ya está en uso. Elija otro tipo.");
                return;
            }
        }

        //Obtenemos la casilla inicial
        Casilla inicial = tablero.encontrar_casilla("Salida");
        if(inicial == null){
            consola.imprimir("Error: no se encontro la casilla de salida.");
            return;
        }
        
        //Crear el jugador y añadirlo (el propio constructor de jugador ya crea el avatar, llamando al constructor de avatar)
        Jugador nuevoJugador = new Jugador(nombre, tipoAvatar, inicial, avatares);
        jugadores.add(nuevoJugador);
        
        //Imprimir la información del nuevo jugador creado
        consola.imprimir("{\n"+ nuevoJugador.toString()+"\n}");

    }
    /**
     * Devuelve el Jugador con el turno actual
     * @param turno
     * @return
    */

    @Override 
    public void mostrarJugadorTurno(){
        consola.imprimir("{\n"+ this.jugadores.get(turno).toString() +"\n}");
    }

    
    /*Método que realiza las acciones asociadas al comando 'describir jugador'.
    * Parámetro: comando introducido
     */
    @Override
    public void descJugador(String nombre) {
        int encontrado = 0;
        consola.imprimir("{");
        for (Jugador j:jugadores){
            if (j.getNombre().equals(nombre)){
                consola.imprimir(j.toString());
                encontrado = 1;
                break;
            }
        }
        if (encontrado == 0){
            consola.imprimir("No se encontro ningun jugador con el nombre '" + nombre + "'.");
        }
        consola.imprimir("}");
    }

    /*Método que realiza las acciones asociadas al comando 'describir avatar'.
    * Parámetro: id del avatar a describir.
    */
   //NO HACE FALTA IMPLEMENTAR, NO SE PIDE
    private void descAvatar(String ID) {
        for(Avatar av: avatares){
            if (av.getId().equals(ID)){
                av.toString();
            }
        }
    }

    /* Método que realiza las acciones asociadas al comando 'describir nombre_casilla'.
    * Parámetros: nombre de la casilla a describir.
    */
    @Override
    public void descCasilla(String nombreCasilla) {
        //Buscamos la casilla a describir
        Casilla c = tablero.encontrar_casilla(nombreCasilla);

        //Si no existe ninguna casilla con ese nombre
        if(c == null){
            consola.imprimir("Error: no existe ninguna casilla con el nombre '" + nombreCasilla + "'.");
            return;
        }

        String info = c.infoCasilla();
        
        //Si la casilla tiene informacion para describirla
        if(info != null){
            consola.imprimir("{");
            consola.imprimir(info);
            consola.imprimir("}");
        } else{ //Si la casilla no tiene informacion
            consola.imprimir("La casilla '" + nombreCasilla + "' no tiene informacion detallada (es una casilla especial).");
        }
    }
    
    private void lanzarDados(String tirada) {
        Jugador jugadorActual = jugadores.get(turno);
        Avatar avatarActual = jugadorActual.getAvatar();
        int doblesSeguidos = 0;

        boolean volverATirar;
        do { // Bucle para permitir volver a tirar si saca dobles
            // Extraer los valores de la jugada forzada
            String[] partes = tirada.split("\\+"); // Dividir por el carácter '+'
            if (partes.length != 2) {
                consola.imprimir("Formato de tirada inválido. Usa el formato 'x+y'.");
                return;
            }

            int valor1, valor2;
            try {
                valor1 = Integer.parseInt(partes[0].trim());
                valor2 = Integer.parseInt(partes[1].trim());
            } catch (NumberFormatException e) {
                consola.imprimir("Error: los valores deben ser números enteros.");
                return;
            }

            // Asignar los valores a los dados (igual que si se hubiesen lanzado)
            this.dado1.setValor(valor1);
            this.dado2.setValor(valor2);
            int total = valor1 + valor2;

            consola.imprimir("Dados forzados: " + valor1 + " y " + valor2 + " (total: " + total + ")");

            // Comprobar dobles
            /*if (valor1 == valor2) {
                doblesSeguidos++;
                if (doblesSeguidos == 3) {
                    avatarActual.setLugar(tablero.encontrar_casilla("Carcel"));
                    avatarActual.getJugador().encarcelar(this.tablero);
                    consola.imprimir("Tercer doble consecutivo: " + jugadorActual.getNombre() + " va directamente a la cárcel.");
                    break;
                }
                volverATirar = true;
                consola.imprimir("¡Doble forzado! " + jugadorActual.getNombre() + " puede volver a tirar después de evaluar la casilla.");
            } else {
                volverATirar = false;
                doblesSeguidos = 0;
            }*/
            volverATirar = false;

            // Mover avatar y mostrar movimiento
            Casilla casillaInicial = avatarActual.getLugar();
            avatarActual.moverAvatar(this.tablero, valor1+valor2);
            Casilla casillaFinal = avatarActual.getLugar();
            

            consola.imprimir("El avatar " + avatarActual.getId() +
                    " avanza desde " + casillaInicial.getNombre() +
                    " hasta " + casillaFinal.getNombre() + ".");

            // Evaluar la casilla
            boolean solvente = casillaFinal.evaluarCasilla(jugadorActual, banca, total, this.tablero);
            if (!solvente) {
                consola.imprimir(jugadorActual.getNombre() + " no puede cumplir con sus obligaciones en " + casillaFinal.getNombre());
            }

            // Si la casilla es de tipo Suerte o Comunidad, ejecutar la carta desde Menu
            if (casillaFinal.getTipo() != null) {
                if (casillaFinal.getTipo().equals("Suerte")) {
                    this.cartaSuerte();
                } else if (casillaFinal.getTipo().equals("Comunidad")) {
                    this.cartaCajaComunidad();
                }
            }

            // Si se está leyendo de un fichero, no se puede pedir una nueva tirada interactiva.
            // Por lo tanto, si salen dobles, el jugador podrá volver a tirar en el siguiente
            // comando del fichero, pero no se detiene la ejecución para pedir input.
            // if (volverATirar) {
            //     System.out.print("Introduce una nueva tirada forzada (formato x+y): ");
            //     Scanner sc = new Scanner(System.in);
            //     tirada = sc.nextLine();
            //     sc.close();
            // }

        } while (volverATirar);

    }

    
    private void lanzarDados() {
        Jugador jugadorActual = jugadores.get(turno);
        Avatar avatarActual = jugadorActual.getAvatar();
        int doblesSeguidos = 0;

        boolean volverATirar;
        do {
            // Lanzar los dados
            this.dado1.hacerTirada();
            this.dado2.hacerTirada();
            int valor1 = this.dado1.getValor();
            int valor2 = this.dado2.getValor();
            int total = valor1 + valor2;

            consola.imprimir("Dados: " + valor1 + " y " + valor2 + " (total: " + total + ")");

            // Comprobar dobles
            if (valor1 == valor2) {
                doblesSeguidos++;
                if (doblesSeguidos == 3) {
                    avatarActual.setLugar(tablero.encontrar_casilla("Carcel"));
                    jugadorActual.encarcelar(this.tablero);
                    consola.imprimir("Tercer doble consecutivo: " + jugadorActual.getNombre() + " va directamente a la cárcel.");
                    break;
                }
                volverATirar = true;
                consola.imprimir("¡Doble! " + jugadorActual.getNombre() + " puede volver a tirar después de evaluar la casilla.");
            } else {
                volverATirar = false;
                doblesSeguidos = 0;
            }

            // Mover avatar y mostrar movimiento
            Casilla casillaInicial = avatarActual.getLugar();
            avatarActual.moverAvatar(this.tablero, valor1+valor2);
            Casilla casillaFinal = avatarActual.getLugar();

            consola.imprimir("El avatar " + avatarActual.getId() +
                    " avanza desde " + casillaInicial.getNombre() +
                    " hasta " + casillaFinal.getNombre() + ".");

            // Evaluar la casilla usando su propio método
            boolean solvente = casillaFinal.evaluarCasilla(jugadorActual, banca, total, this.tablero);
            if (!solvente) {
                consola.imprimir(jugadorActual.getNombre() + " no puede cumplir con sus obligaciones en " + casillaFinal.getNombre());
            }

            // Si la casilla es de tipo Suerte o Comunidad, ejecutar la carta desde Menu
            if (casillaFinal.getTipo() != null) {
                if (casillaFinal.getTipo().equals("Suerte")) {
                    this.cartaSuerte();
                } else if (casillaFinal.getTipo().equals("Comunidad")) {
                    this.cartaCajaComunidad();
                }
            }

        } while (volverATirar);

    }

    /*Método que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {
        //Busco si existe una casilla con ese nombre
        Casilla c = tablero.encontrar_casilla(nombre);
        if (c == null) {
            consola.imprimir("No existe la casilla '" + nombre + "'.");
            return;
        }
        if (this.jugadores.get(turno).getAvatar().getLugar() != c) {
            consola.imprimir("El jugador " + this.jugadores.get(turno).getNombre() + " no está en la casilla '" + nombre + "'.");
            return;
        }
        if (c.getDuenho().equals(jugadores.get(turno))) {
            consola.imprimir("El jugador " + this.jugadores.get(turno).getNombre() + " ya es dueño de la casilla '" + nombre + "'.");
            return;
        }
        //Obtengo su tipo y si se puede comprar o no
        String tipo = c.getTipo();
        if (!tipo.equals("Solar") && !tipo.equals("Transporte") && !tipo.equals("Servicios")) {
            consola.imprimir("La casilla '" + nombre + "' no se puede comprar.");
            return;
        }

        c.comprarCasilla(jugadores.get(turno), banca);

        Jugador jugadorActual = jugadores.get(turno);
        if (!jugadorActual.getPropiedades().contains(c)) {
            jugadorActual.anhadirPropiedad(c);
        }
    }


    //Método que ejecuta todas las acciones relacionadas con el comando 'salir carcel'. 
    private void salirCarcel() {
        if (jugadores == null || jugadores.isEmpty()) {
            consola.imprimir("No hay jugadores en la partida.");
            return;
        }
        Jugador jugadorActual = jugadores.get(turno);
        if (!jugadorActual.isEnCarcel()) {
            consola.imprimir("El jugador " + jugadorActual.getNombre() + " no está en la cárcel.");
            return;
        }
        float fianza = 500000f;

        if(jugadorActual.getFortuna() >= fianza){
            jugadorActual.sumarFortuna(-fianza);
            jugadorActual.sumarGastos(fianza);

            consola.imprimir(jugadorActual.getNombre() + "paga" + fianza + "€ y sale de la cárcel. Puede lanzar los dados."); //aqui se ha sustituido fianza por String.format("%,.f", fianza).replace(",", ".")
        }else{
            consola.imprimir(jugadorActual.getNombre() + " no tiene suficiente dinero para pagar la fianza de " + String.format("%,.0f", fianza).replace(",",".") + "€.");
            consola.imprimir("Debe intentar sacar dobles o declararse en bancarrota si no puede conseguir el dinero.");
            //POSTERIORMENTE SE IMPLEMENTARÁ LA CASUÍSTICA DE SACAR DOBLES/BANCARROTA/CARTA DE LA SUERTE
        }
     
    }

    // Método que realiza las acciones asociadas al comando 'listar enventa'.
    @Override
    public void listarEnVenta() {
        for (ArrayList<Casilla> lado : tablero.getPosiciones()) {
            for (Casilla c : lado) {
                c.casEnVenta(); // Cada casilla decide si imprime info
            }
        }
    }


    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    @Override
    public void listarJugadores() {
        if(jugadores == null || jugadores.isEmpty()){
            consola.imprimir("No hay jugadores en la partida.");
            return;
        }
        for (Jugador jugador : jugadores) {
            if(jugador.getAvatar()==null){  //La banca no tiene avatar, y por lo tanto no debe imprimirse
                continue;
            }
            consola.imprimir("{");
            consola.imprimir(jugador.toString()); //Cada jugador imprime su información  
            consola.imprimir("},");
        }
    }

    //Devuelve true si el jugador buscado por string de caracteres existe en la lista de jugadores
    private boolean encontrar_jugador(String jugadorBuscado) {
        if(jugadores == null || jugadores.isEmpty()){
            return false;
        }
        for (Jugador jugador : jugadores) {
            if(jugadorBuscado.equals(jugador.getNombre())) return true;
        }
        return false;
    }

    //NO HAY QUE HACERLO, NO SE PIDE
    // Método que realiza las acciones asociadas al comando 'listar avatares'.
    private void listarAvatares() {
        for (Avatar avatar : avatares) {
            avatar.toString();
        }
    }

    //Función para listar todos los edificios de la partida
    @Override
    public void listarEdificios(){
        ArrayList<ArrayList<Casilla>> posiciones = tablero.getPosiciones();
        int edificaciones = 0;
        for (ArrayList<Casilla> lado : posiciones) {
            for (Casilla c : lado) {
                if(c.getTipo().equals("Solar") && !c.getDuenho().getNombre().equals("Banca") && !c.getEdificios().isEmpty()){
                    edificaciones = 1;
                    for(Edificacion edificio : c.getEdificios()){
                        Jugador duenho = c.getDuenho();
                        Avatar avatarDueño = duenho.getAvatar();
                        consola.imprimir(
                            "{\n" +
                            "\tid: " + edificio.toString() + ",\n" +
                            "\tpropietario: " + duenho.getNombre() + ",\n" +
                            "\tcasilla: " + c.getNombre() + ",\n" +
                            "\tgrupo: " + c.getGrupo().colorToNombreGrupo() + ",\n" +
                            "\tcoste: " + edificio.getValor() + "€,\n" +
                            "}");
                    }
                }
            }
        }
        if (edificaciones==0){
            consola.imprimir("No hay edificaciones en la partida");
        }
    }
    public Grupo StringToGrupo(String color){
        // Se convierte el color de entrada a minúsculas para asegurar la coincidencia
        // y se busca directamente en el HashMap. Es mucho más eficiente.
        Grupo grupo = tablero.getGrupos().get(color);
        if (grupo == null) {
            System.err.println("Error: El grupo '" + color + "' no existe.");
        }
        return grupo; 
    }
    //Función para listar los edificios de un grupo concreto
    @Override
    public void listarEdificiosPorGrupo(String grupoNombre){
        Grupo grupo = StringToGrupo(grupoNombre);
        if(grupo == null){
            consola.imprimir("El grupo especificado no existe.");
        }
        ArrayList<Casilla> miembrosGrupo = grupo.getMiembros();
        boolean puedeConstruirCasa = false;
        boolean puedeConstruirHotel = false;
        boolean puedeConstruirPiscina = false;
        boolean puedeConstruirPista = false;

        for (Casilla c : miembrosGrupo) {
            ArrayList<Edificacion> edificios = c.getEdificios();

            // Agrupar por tipo
            ArrayList<String> casas = new ArrayList<>();
            ArrayList<String> hoteles = new ArrayList<>();
            ArrayList<String> piscinas = new ArrayList<>();
            ArrayList<String> pistas = new ArrayList<>();

            int numeroCasas = 0;
            boolean casillaTieneHotel = false;
            boolean casillaTienePiscina = false;
            boolean casillaTienePista = false;

            if (edificios != null && !edificios.isEmpty()) {
                for (Edificacion e : edificios) {
                    String tipo = e.getTipo();
                    switch (tipo) {
                        case "casa":
                            casas.add(e.toString());
                            numeroCasas++;
                            break;
                        case "hotel":
                            hoteles.add(e.toString());
                            casillaTieneHotel = true;
                            break;
                        case "piscina":
                            piscinas.add(e.toString());
                            casillaTienePiscina = true;
                            break;
                        case "pista deportiva":
                            pistas.add(e.toString());
                            casillaTienePista = true;
                            break;
                        default:
                            // Otros tipos
                            break;
                    }
                }
            }

            // Determinar si en esta casilla aún se puede construir cada tipo (reglas derivadas de edificar())
            boolean puedeCasa = !casillaTieneHotel && numeroCasas < 4;
            boolean puedeHotel = !casillaTieneHotel && numeroCasas == 4;
            boolean puedePiscina = !casillaTienePiscina && casillaTieneHotel; // requiere hotel existente
            boolean puedePista = !casillaTienePista && casillaTienePiscina; // solo debe no existir ya

            // Acumular a nivel de grupo
            if (puedeCasa) puedeConstruirCasa = true;
            if (puedeHotel) puedeConstruirHotel = true;
            if (puedePiscina) puedeConstruirPiscina = true;
            if (puedePista) puedeConstruirPista = true;

        // Construir una sola cadena con toda la información y hacer un único println
        String hotelesStr = hoteles.isEmpty() ? "-" : hoteles.toString();
        String casasStr = casas.isEmpty() ? "-" : casas.toString();
        String piscinasStr = piscinas.isEmpty() ? "-" : piscinas.toString();
        String pistasStr = pistas.isEmpty() ? "-" : pistas.toString();
        String alquilerStr = String.valueOf(c.impuestoTotalCasilla());

        consola.imprimir("{\n" +
            "\tpropiedad: " + c.getNombre() + ",\n" +
            "\thoteles: " + hotelesStr + ",\n" +
            "\tcasas: " + casasStr + ",\n" +
            "\tpiscinas: " + piscinasStr + ",\n" +
            "\tpistasDeDeporte: " + pistasStr + ",\n" +
            "\talquiler: " + alquilerStr + "\n" +
            "},");

        }

        // Resumen de lo que aún puede construirse en el grupo
        ArrayList<String> listaPuede = new ArrayList<>();
        ArrayList<String> listaNoPuede = new ArrayList<>();

        if (puedeConstruirPista) listaPuede.add("pista de deporte"); else listaNoPuede.add("pistas de deporte");
        if (puedeConstruirPiscina) listaPuede.add("piscina"); else listaNoPuede.add("piscinas");
        if (puedeConstruirHotel) listaPuede.add("hoteles"); else listaNoPuede.add("hoteles");
        if (puedeConstruirCasa) listaPuede.add("casas"); else listaNoPuede.add("casas");

        consola.imprimir("Aún se pueden edificar: " + String.join(", ", listaPuede) + ".");
        consola.imprimir("No se pueden construir : " + String.join(", ", listaNoPuede) + ".");
        
    }
    

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
        if(jugadores == null || jugadores.isEmpty()){
            consola.imprimir("No hay jugadores en la partida.");
            return;
        }
        // Si es el turno del último jugador, el turno pasa al primer jugador real (índice 1),
        // saltándose a la banca (índice 0).
        if(turno >= jugadores.size() - 1) { 
            turno = 1;  //Reiniciar vuelta al primer jugador (no a la banca)

        } else {
            turno++;
        }

        consola.imprimir("El jugador actual es " + jugadores.get(turno).getNombre() + ".");
    }

    private void imprimirJugadorTurno() {
        consola.imprimir("Nombre:" + this.jugadores.get(turno).getNombre() + "\nAvatar: " + this.jugadores.get(turno).getAvatar().getId());
    }

    private void edificar(String tipoEdificio) {
        Jugador jugadorActual = jugadores.get(turno);
        Avatar avatarActual = jugadorActual.getAvatar();
        Casilla casillaActual = avatarActual.getLugar();

        //Comprobar que la casilla es un solar
        if (!casillaActual.getTipo().equals("Solar")) {
            consola.imprimir("No se puede edificar en una casilla de tipo '" + casillaActual.getTipo() + "'.");
            return;
        }

        // Comprobar que el jugador es el dueño de la casilla
        if (!casillaActual.getDuenho().equals(jugadorActual)) {
            consola.imprimir("No eres el dueño de la casilla '" + casillaActual.getNombre() + "'.");
            return;
        }

        //Comprobar que el jugador posee todas las casillas del grupo
        Grupo grupo = casillaActual.getGrupo();
        if (grupo == null) {
            consola.imprimir("Error: La casilla no pertenece a ningún grupo.");
            return;
        }
        if (!grupo.esDuenhoGrupo(jugadorActual)) {
            consola.imprimir("Debes ser dueño de todas las propiedades del grupo " + grupo.colorToNombreGrupo() + " para poder edificar.");
            return;
        }

        // Comprobar que el tipo de edificio es válido
        float costeEdificio = grupo.getPrecioEdificioPorGrupo(tipoEdificio);
        if (costeEdificio <= 0) {
            consola.imprimir("El tipo de edificio '" + tipoEdificio + "' no es válido.");
            return;
        }

        //comprobaciones con respecto a las casillas que ya existen
        boolean casillaTieneHotel = false;
        boolean casillaTienePiscina = false;
        boolean casillaTienePistaDeportiva =false;
        int numeroCasas=0;
        Iterator<Edificacion> iterator = casillaActual.getEdificios().iterator();
        while(iterator.hasNext()){
            Edificacion edificioRecorrido = iterator.next();
            if(edificioRecorrido.getTipo().equals("piscina")) casillaTienePiscina=true;
            if(edificioRecorrido.getTipo().equals("pista deportiva")) casillaTienePistaDeportiva=true;
            if(edificioRecorrido.getTipo().equals("hotel")) casillaTieneHotel =true;
            if(edificioRecorrido.getTipo().equals("casa")) numeroCasas++;
        }
        //Si tienes hotel o 4 casas no puedes contruir mas casas
        if((numeroCasas ==4 || casillaTieneHotel) && tipoEdificio.equals("casa")){
            consola.imprimir("No se puede edificar mas casas en la casilla " + casillaActual.getNombre());
            return;
        }
        //Comprobar que hay sitio para edificar
        if((casillaTieneHotel && casillaTienePiscina && casillaTienePistaDeportiva)){
            consola.imprimir("No se puede edificar ningun edificio mas en esta casilla ni en el grupo al que la casillla pertenece");
            return;
        }
        //Comprobar si acaso ya existe un hotel, pista deportiva o piscina cuando se quiere crear una
        if(casillaTieneHotel && tipoEdificio.equals("hotel")){
            consola.imprimir("No se puede edificar un hotel ya que ya existe un hotel en la casilla " + casillaActual.getNombre());
            return;
        }
        //No se puede adificar mas de una piscina
        if(casillaTienePiscina && tipoEdificio.equals("piscina")){
            consola.imprimir("No se puede edificar una piscina ya que ya existe una en la casilla " + casillaActual.getNombre());
            return;
        }
        //Comprobar si acasa ya existe un hotel, pista deportiva o piscina cuando se quiere crear una
        if(casillaTienePistaDeportiva && tipoEdificio.equals("pista deportiva")){
            consola.imprimir("No se puede edificar una pista deportiva ya que ya existe una en la casilla " + casillaActual.getNombre());
            return;
        }
        // Comprobar que la casilla dispone de un hotel para crear una piscina
        if(!casillaTieneHotel && tipoEdificio.equals("piscina")){
            consola.imprimir("No se puede edificar una piscina ya que no se dispone de un hotel.");
            return;
        }
        // Comprobar si acaso se quiere construir un hotel que el jugador tiene 4 casas en ese hotel
        if(!(numeroCasas==4) && tipoEdificio.equals("hotel")){
            consola.imprimir("No se disponen de las cuatro casa necesarias para contruir el hotel");
            return;
        }
        //Comprobar que el jugador tiene suficiente dinero
        if (jugadorActual.getFortuna() < costeEdificio) {
            consola.imprimir("La fortuna de "+ jugadorActual + " no es suficinete para edificar un" + tipoEdificio + " en la casilla " + casillaActual.getNombre());
            return;
        }
        //Hacer el pago por la contruccion y realizar la edificacion
        jugadorActual.sumarFortuna(-costeEdificio);
        jugadorActual.sumarGastos(costeEdificio);
        //Si vamos a crear un hotel y hemos comprobado que existen 4 casas tenemos que eliminar las casas
        if(tipoEdificio.equals("hotel")){
            casillaActual.getEdificios().removeIf(edificio -> edificio.getTipo().equals("casa"));
            consola.imprimir("Se han eliminado las casas para poder construir el hotel");
        }
        //Ahora cuando instanciamos un Edificio especificamos el tipo
        switch (tipoEdificio) {
            case "hotel":
                new Hotel(casillaActual,tipoEdificio);
                break;
            case "piscina":
                new Piscina(casillaActual,tipoEdificio); 
                break;
            case "pista deportiva":
                new PistaDeportiva(casillaActual,tipoEdificio); 
                break;
            case "casa":
                new Casa(casillaActual,tipoEdificio);
                break;
            default:
                break;
        }        
        consola.imprimir("Se ha edificado un " + tipoEdificio + " en " + casillaActual.getNombre() + ". La fortuna de " + jugadorActual.getNombre() + " se reduce en " + costeEdificio + "€");
    }
    

    // Método que realiza las acciones asociadas al comando 'vender tipo_edificio nombre_casilla cantidad'.
    /*Paramámetros:
    - tipo_edificio: tipo de edificio a vender (casa, hotel, piscina,
    pista deportiva).
    - nombre_casilla: nombre de la casilla donde se encuentran los edificios.
    - cantidad: número de edificios a vender.
    */
    public void venderEdificacion(String tipoEdificio, String nombreCasilla, String cantidadStr){
        Jugador jugadorActual = jugadores.get(turno);
        int cantidad;
        //Comprobacion del valor cantidadStr es un valor positivo
        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                consola.imprimir("La cantidad a vender debe ser un número positivo.");
                return;
            }
        } catch (Exception e) {
            consola.imprimir("La cantidad debe ser un número entero válido.");
            return;
        }

        Casilla casilla = tablero.encontrar_casilla(nombreCasilla);
        if (casilla == null) {
            consola.imprimir("No se ha encontrado la casilla '" + nombreCasilla + "'.");
            return;
        }

        if (!casilla.getDuenho().equals(jugadorActual)) {
            consola.imprimir("No eres el dueño de la casilla '" + nombreCasilla + "'.");
            return;
        }

        ArrayList<Edificacion> edificiosEnCasilla = casilla.getEdificios();
        ArrayList<Edificacion> edificiosAVender = new ArrayList<>();

        for (Edificacion e : edificiosEnCasilla) {
            if (e.getTipo().equalsIgnoreCase(tipoEdificio)) {
                edificiosAVender.add(e);
            }
        }

        if (edificiosAVender.isEmpty()) {
            consola.imprimir("No existen edificios de tipo '" + tipoEdificio + "' en la casilla '" + nombreCasilla + "'.");
            return;
        }

        if (cantidad > edificiosAVender.size()) {
            consola.imprimir("No tienes " + cantidad + " edificios de tipo '" + tipoEdificio + "' para vender en '" + nombreCasilla + "'. Solo tienes " + edificiosAVender.size() + ".");
            return;
        }


        float totalVenta = 0;
        for (int i = 0; i < cantidad; i++) {
            Edificacion edificio = edificiosAVender.get(i);
            float precioVenta = edificio.getValor(); // Se vende por el coste original
            totalVenta += precioVenta;
            casilla.eliminarEdificacion(edificio);
        }

        jugadorActual.sumarFortuna(totalVenta);

        consola.imprimir(jugadorActual.getNombre() + " ha vendido " + cantidad + " " + tipoEdificio + "(s) en " + nombreCasilla + " por " + totalVenta + "€.");
        consola.imprimir("Tu fortuna actual es de " + jugadorActual.getFortuna() + "€.");

        // Lógica especial para hoteles: si se vende un hotel, se recuperan 4 casas.
        if (tipoEdificio.equalsIgnoreCase("hotel")) {
            consola.imprimir("Al vender el hotel, recuperas 4 casas en " + nombreCasilla + ".");
            for (int i = 0; i < 4; i++) new Edificacion(casilla, "casa");
        }
    }

    public void hipotecarPropiedad(String casilla){
        Casilla c = tablero.encontrar_casilla(casilla);
        Jugador JugadorActual = jugadores.get(turno);

        if(c == null){
            consola.imprimir("Error: no existe ninguna casilla con el nombre '" + casilla + "'.");
            return;
        }

        //Comprobar que el jugador sea duenho de la casilla
        if(!c.getDuenho().equals(JugadorActual)){
            consola.imprimir("El jugador " + JugadorActual.getNombre() + " no puede hipotecar " + c.getNombre() +". No es una propiedad que le pertenece.");
            return;
        }
        //Comprobar que la casilla no este ya hipotecada
        if(c.getEstarHipotecada()){
            consola.imprimir("El jugador " + JugadorActual.getNombre() + " no puede hipotecar " + c.getNombre() + ". Ya esta hipotecada");  
            return;
        } 
        
        //Si la casilla es solar y tiene edificaciones
        if(c.getTipo().equals("Solar") && !c.getEdificios().isEmpty()){
            consola.imprimir("El jugador " + JugadorActual.getNombre() + " no puede hipotecar " + c.getNombre() + ". Primero debe vender todos sus edificios.");
        }
        else if(c.getTipo().equals("Solar") && c.getEdificios().isEmpty()){
            banca.sumarFortuna(-c.getHipoteca());
            JugadorActual.sumarFortuna(c.getHipoteca());
            c.setEstarHipotecada(true);
            consola.imprimir("El jugador " + JugadorActual.getNombre() + " recibe " + c.getHipoteca() + "€ por hipotecar " + c.getNombre());
            if(c.getGrupo() != null && c.getGrupo().esDuenhoGrupo(JugadorActual)){
                consola.imprimir("No puede recibir alquileres ni edificar en el grupo " + c.getGrupo().colorToNombreGrupo());
            }else{
                consola.imprimir("No se puede recibir alquileres.");
            }
        }else{
            consola.imprimir("La casilla " + c.getNombre() + " no es de tipo Solar. No se puede hipotecar.");
        }
        return;

    }

    public void deshipotecar(String casilla){
        Casilla c = tablero.encontrar_casilla(casilla);
        Jugador JugadorActual = jugadores.get(turno);

        if(c == null){
            consola.imprimir("Error: no existe ninguna casilla con el nombre '" + casilla + "'.");
            return;
        }

        if(!c.getTipo().equals("Solar")){
            consola.imprimir("La casilla " + c.getNombre() + " no es de tipo Solar. No se puede deshipotecar.");
            return;
        }   
        //Comprobar que el jugador sea duenho de la casilla
        if(!c.getDuenho().equals(JugadorActual)){
            consola.imprimir("El jugador " + JugadorActual.getNombre() + " no puede deshipotecar " + c.getNombre() +". No es una propiedad que le pertenece.");
            return;
        }
        //Comprobar que la casilla no este ya hipotecada
        if(!c.getEstarHipotecada()){
            consola.imprimir("El jugador " + JugadorActual.getNombre() + " no puede deshipotecar " + c.getNombre() + ". No esta hipotecada");  
        } 

        if(JugadorActual.getFortuna() < c.getHipoteca()){
            consola.imprimir("El jugador " + JugadorActual.getNombre() + " no tiene suficiente dinero para deshipotecar " + c.getNombre() +". Se necesitan " + c.getHipoteca() + "€.");
            return;
        }
        
        JugadorActual.sumarFortuna(-c.getHipoteca());
        JugadorActual.sumarGastos(c.getHipoteca());
        banca.sumarFortuna(c.getHipoteca());
        c.setEstarHipotecada(false);

        consola.imprimir("El jugador " + JugadorActual.getNombre() + " paga " + c.getHipoteca() + "€ por deshipotecar " + c.getNombre() + ".");
        if(c.getGrupo() != null && c.getGrupo().esDuenhoGrupo(JugadorActual)){
            consola.imprimir("Ahora puede recibir alquileres y edificar en el grupo " + c.getGrupo().colorToNombreGrupo() + ".");
        }else{
            consola.imprimir("Ahora se puede recibir alquileres.");
        }
        return;
    }
    

    public void mostrarEstadisticas(String jugador){
        Jugador jugadorEncontrado = null;
        for (Jugador jugadorE: jugadores){
            if(jugadorE.getNombre().equals(jugador)){
                jugadorEncontrado = jugadorE;
                break;
            }
        }
        if(jugadorEncontrado == null){
            consola.imprimir("Jugador no encontrado");
            return;
        }
        consola.imprimir("{");
        consola.imprimir("dineroInvertido: " + jugadorEncontrado.getDineroInvertido());
        consola.imprimir("pagoTasasEImpuestos: " + jugadorEncontrado.getDineroTasasImpuestos());
        consola.imprimir("pagoDeAlquileres: " + jugadorEncontrado.getDineroPagoAlquileres());
        consola.imprimir("cobroDeAlquileres: " + jugadorEncontrado.getDineroCobroAlquileres());
        consola.imprimir("pasarPorCasillaDeSalida: " + jugadorEncontrado.getDineroSalida());
        consola.imprimir("premiosInversionesOBote: " + jugadorEncontrado.getDineroPremios());
        consola.imprimir("vecesEnLaCarcel: " + jugadorEncontrado.getVecesEnCarcel());
        consola.imprimir("}");

    }
    public ArrayList<String> buscarJugadorConMasVueltas(){
        ArrayList<String> jugadoresMasVueltas = new ArrayList<String>();
        int numeroMaximoVueltas = 0;
        
        //Recoro el arraylist de jugadores buscando el numero maximo de vueltas
        for(Jugador jugador: jugadores){
            if(numeroMaximoVueltas < jugador.getNumeroDeVueltas()){
                numeroMaximoVueltas = jugador.getNumeroDeVueltas();
                jugadoresMasVueltas.clear(); //Limpio el arraylist
                jugadoresMasVueltas.add(jugador.getNombre()); //Añado el nuevo jugador con mas vueltas
                
            }else if(numeroMaximoVueltas == jugador.getNumeroDeVueltas() && jugador.getNombre() != "Banca"){
                jugadoresMasVueltas.add(jugador.getNombre());
            }
        }
        return jugadoresMasVueltas;
    }

    public ArrayList<String> buscarJugadorEnCabeza(){
        ArrayList<String> jugadoresCabeza = new ArrayList<String>();
        float fortunaTotal = 0.0f;
        float maximaFortuna = 0.0f;
        
        for(Jugador jugador: jugadores){
            fortunaTotal = 0.0f;
            fortunaTotal += jugador.getFortuna();
            for(Casilla c :jugador.getPropiedades()){
                fortunaTotal += c.getValor();
                if(c.getEdificios() != null){
                    for(Edificacion e: c.getEdificios()){
                        fortunaTotal += e.getValor();
                    }
                }
            }
            if(fortunaTotal == maximaFortuna){
                jugadoresCabeza.add(jugador.getNombre());
            }else if(fortunaTotal > maximaFortuna){
                jugadoresCabeza.clear();
                jugadoresCabeza.add(jugador.getNombre());
            }
        }
        return jugadoresCabeza;
    }
    
    public String corchetesToComas(ArrayList<String> array){
        return array.stream().collect(Collectors.joining(","));
    }

    public void mostrarEstadisticasJuego(){
        consola.imprimir("{");
        consola.imprimir("casillaMasRentable: " + corchetesToComas(tablero.buscarCasillaMasRentable()) + ",");
        consola.imprimir("grupoMasRentable: " + corchetesToComas(tablero.buscarGrupoMasRentable()) +  ",");
        consola.imprimir("casillaMasFrecuentada: " + corchetesToComas(tablero.buscarCasillaMasVisitada()) + ",");
        consola.imprimir("jugador(es)MasVueltas: " + corchetesToComas(buscarJugadorConMasVueltas()) + ",");
        consola.imprimir("jugadorEnCabeza: " + corchetesToComas(buscarJugadorEnCabeza()) + ",");
        consola.imprimir("}");
    }
}