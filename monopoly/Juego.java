package monopoly;

import monopoly.cartas.*;
import monopoly.casillas.*;
import monopoly.edificios.*;
import monopoly.excepciones.*;
import monopoly.interfaces.*;

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
    
    

    ////////////////////CONSTRUCTOR////////////////////
    public void iniciarPartida() {
        //Crear jugadores y avatares.
        inicializarMazos();
        int indiceSuerte = 0;
        int indiceCajaComunidad = 0;
        jugadores = new ArrayList<Jugador>();
        avatares = new ArrayList<Avatar>();
        banca = new Jugador(this);
        jugadores.add(banca);
        tablero = new Tablero(banca, this);
        turno = 1;
        dado1 = new Dado();
        dado2 = new Dado();
        numCartaSuerte = 1;
        numCartaCajaCom = 1;
        
    }
    ////////////////////GETTERS Y SETTERS////////////////////
    public int getTurno(){
        return turno;
    }
    public Jugador getJugadorActual(){
        return jugadores.get(turno);
    }
    public Tablero getTablero(){
        return tablero;
    }
    public ConsolaNormal getConsola(){
        return consola;
    }
    public Jugador getBanca(){
        return banca;
    }
    
    public void lecturaFichero(String fichero) throws MonopolyException {
        File file = new File(fichero);
        try(Scanner sc = new Scanner(file)){
            while(sc.hasNextLine()){
                String linea = sc.nextLine();
                analizarComando(linea);
                try{
                    Thread.sleep(0); //Se puede modificar este valor para incrementar el delay entre comandos
                }
                catch(Exception e){
                    throw new JuegoException("Error en el sleep");
                }
            }
        } catch (FileNotFoundException e){
            throw new JuegoException("Error: Fichero no encontrado.");
        }
    }

    private void inicializarMazos() {
        mazoSuerte = new ArrayList<>();
        mazoSuerte.add(new CartaSuerte(1, this, "Decides hacer un viaje de placer. Avanza hasta Solar19. Si pasas por la casilla de Salida, cobra 2.000.000€."));
        mazoSuerte.add(new CartaSuerte(2, this, "Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar los 2.000.000€."));
        mazoSuerte.add(new CartaSuerte(3, this, "¡Has ganado el bote de la lotería! Recibe 1.000.000€."));
        mazoSuerte.add(new CartaSuerte(4, this, "Has sido elegido presidente de la junta directiva. Paga a cada jugador 250.000€."));
        mazoSuerte.add(new CartaSuerte(5, this, "¡Hora punta de tráfico! Retrocede tres casillas."));
        mazoSuerte.add(new CartaSuerte(6, this, "Te multan por usar el móvil mientras conduces. Paga 150.000€."));
        mazoSuerte.add(new CartaSuerte(7, this, "Avanza hasta la casilla de transporte más cercana. Si no tiene dueño, puedes comprarla. Si tiene dueño, paga al dueño el doble de la operación indicada."));
        
        mazoCajaComunidad = new ArrayList<>();
        mazoCajaComunidad.add(new CartaCajaComunidad(1, this,"Paga 500.000€ por un fin de semana en un balneario de 5 estrellas."));
        mazoCajaComunidad.add(new CartaCajaComunidad(2, this, "Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar los 2.000.000€."));
        mazoCajaComunidad.add(new CartaCajaComunidad(3, this, "Colócate en la casilla de Salida. Cobra 2.000.000€."));
        mazoCajaComunidad.add(new CartaCajaComunidad(4, this, "Devolución de Hacienda. Cobra 500.000€."));
        mazoCajaComunidad.add(new CartaCajaComunidad(5, this, "Retrocede hasta Solar1 para comprar antigüedades exóticas."));
        mazoCajaComunidad.add(new CartaCajaComunidad(6, this, "Ve a Solar20 para disfrutar del San Fermín.\\nSi pasas por la casilla de Salida, cobra 2.000.000€."));
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
    private void analizarComando(String comando) throws MonopolyException {
        consola.imprimir("\n>> " + comando );     //Impresion del comando insertado
        String[] partesComando = comando.split(" ");
        consola.imprimir("");
            if(partesComando.length > 0){
                String comandoPrincipal = partesComando[0];
            switch (comandoPrincipal) {
                case "crear":
                    if(partesComando.length == 4 && partesComando[1].equals("jugador") && !partesComando[3].isEmpty()){ //Si se selecciona la opcion jugador dentro de crear y se anhade un campo avatar 
                        crearJugador(partesComando[2], partesComando[3]);
                    }
                    else throw new ArgumentosComandoException("Argumentos inválidos para crear jugador. Uso: crear jugador <nombre> <avatar>");
                    break;
                    
                case "jugador":
                    if (partesComando.length == 1) {
                        mostrarJugadorTurno();
                    }
                    else throw new ArgumentosComandoException("Comando inválido, uso: jugador");
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
                    else throw new ArgumentosComandoException("Argumentos inválidos para listar. Uso: listar <jugadores|enventa|edificios> [grupo]");
                    break;
                case "lanzar":
                    if (partesComando.length == 2 && partesComando[1].equals("dados")){
                            lanzarDados();
                        }
                    else if(partesComando.length ==3 && partesComando[1].equals("dados") && partesComando[2].contains("+") && partesComando[2].length()==3){
                        lanzarDados(partesComando[2]);
                    }
                    else throw new ArgumentosComandoException("Argumentos inválidos para lanzar dados. Uso: lanzar dados [x+y]");
                    break;
                case "acabar":
                    if(partesComando.length ==2 && partesComando[1].equals("turno")){
                        this.acabarTurno();
                    }
                    else throw new ArgumentosComandoException("Comando inválido, uso: acabar turno");
                    break;
                case "salir":
                    if(partesComando.length ==2 && partesComando[1].equals("carcel")){
                        this.salirCarcel();
                    }
                    else throw new ArgumentosComandoException("Comando inválido, uso: salir carcel");
                    break;
                case "describir":
                    if(partesComando.length == 3 && partesComando[1].equals("jugador") && this.encontrar_jugador(partesComando[2])){
                        this.descJugador(partesComando[2]);
                    }
                    else if (partesComando.length == 2 && ((tablero.encontrar_casilla(partesComando[1]) instanceof Casilla))) {
                        this.descCasilla(partesComando[1]);
                    }
                    else throw new ArgumentosComandoException("Argumentos inválidos para describir. Uso: describir <jugador|casilla> <nombre>");
                    break;
                case "comprar":
                    if(partesComando.length ==2 && ((tablero.encontrar_casilla(partesComando[1]) instanceof Casilla))){
                        comprar(partesComando[1]);
                    }
                    else throw new ArgumentosComandoException("Argumentos inválidos para comprar. Uso: comprar <casilla>");
                    break;
                case "vender":
                    if(partesComando.length == 4){
                        // vender [tipo] [casilla] [cantidad]
                        venderEdificacion(partesComando[1], partesComando[2], partesComando[3]);
                    }
                    else throw new ArgumentosComandoException("Argumentos inválidos para vender. Uso: vender <tipo> <casilla> <cantidad>");
                    break;
                case "estadisticas":
                    if(partesComando.length == 1){
                        mostrarEstadisticasJuego();
                    }
                    else if(partesComando.length == 2){
                        mostrarEstadisticas(partesComando[1]);
                    }
                    else throw new ArgumentosComandoException("Argumentos inválidos para estadisticas. Uso: estadisticas [jugador]");
                    break;
                case "edificar":
                    //casa
                    if(partesComando.length == 2 && partesComando[1].equals("casa")) edificar("casa");
                    else if(partesComando.length == 2 && partesComando[1].equals("hotel")) edificar("hotel");
                    else if(partesComando.length == 3 && partesComando[1].equals("pista") && partesComando[2].equals("deportiva")) edificar("pista deportiva");
                    else if(partesComando.length == 2 && partesComando[1].equals("piscina")) edificar("piscina");
                    else throw new ArgumentosComandoException("Argumentos inválidos para edificar. Uso: edificar <casa|hotel|piscina|pista deportiva>");
                    break;
                case "ver":
                    if(partesComando.length == 2 && partesComando[1].equals("tablero")){
                        verTablero();
                    }
                    else throw new ArgumentosComandoException("Comando inválido, uso: ver tablero");
                    break;
                case "hipotecar":
                    if(partesComando.length == 2){
                        hipotecarPropiedad(partesComando[1]);
                    }
                    else throw new ArgumentosComandoException("Argumentos inválidos para hipotecar. Uso: hipotecar <casilla>");
                    break;
                case "deshipotecar":
                    if(partesComando.length == 2){
                        deshipotecar(partesComando[1]);
                    }
                    else throw new ArgumentosComandoException("Argumentos inválidos para deshipotecar. Uso: deshipotecar <casilla>");
                    break;
                
                default:
                    // Se ejecuta si el comando no coincide con ningún case
                    throw new ComandoInvalidoException("Comando desconocido '" + comandoPrincipal + "'");

            }
        }
    }

    /*Método que realiza las acciones asociadas al comando 'crear jugador'.
    Parametro: Comando introducido + nombre del jugador
    */
   @Override 
    public void crearJugador(String nombre, String tipoAvatar) throws ComandoImposibleException{
        //Comprobamos si existe ya un jugador con el nombre introducido
        for(Jugador j: jugadores){
            if(j.getNombre().equals(nombre)){
                throw new ComandoImposibleException("Ya existe un jugador con el nombre '" + nombre + "'.");
            }
        }
        
        //Comprobamos si el tipo de avatar es válido
        for(Avatar av: avatares){
            if(av.getTipo().equals(tipoAvatar)){
                throw new ComandoImposibleException("El tipo de avatar '" + tipoAvatar + "' ya está en uso. Elija otro tipo.");
            }
        }

        //Obtenemos la casilla inicial
        Casilla inicial = tablero.encontrar_casilla("Salida");
        if(inicial == null){
            throw new ComandoImposibleException("Error: no se encontro la casilla de salida.");
        }
        
        //Crear el jugador y añadirlo (el propio constructor de jugador ya crea el avatar, llamando al constructor de avatar)
        Jugador nuevoJugador = new Jugador(nombre, tipoAvatar, inicial, avatares, this);
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
    private void descAvatar(String ID){
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
    public void descCasilla(String nombreCasilla) throws ComandoImposibleException {
        //Buscamos la casilla a describir
        Casilla c = tablero.encontrar_casilla(nombreCasilla);

        //Si no existe ninguna casilla con ese nombre
        if(c == null){
            throw new ComandoImposibleException("Error: no existe ninguna casilla con el nombre '" + nombreCasilla + "'.");
        }

        String info = c.infoCasilla();
        
        //Si la casilla tiene informacion para describirla
        if(info != null){
            consola.imprimir("{");
            consola.imprimir(info);
            consola.imprimir("}");
        } else{ //Si la casilla no tiene informacion
            throw new ComandoImposibleException("La casilla '" + nombreCasilla + "' no tiene informacion detallada (es una casilla especial).");
        }
    }
    
    @Override   
    public void lanzarDados(String tirada) throws AccionInvalidaException, BancarrotaException {
        Jugador jugadorActual = jugadores.get(turno);
        Avatar avatarActual = jugadorActual.getAvatar();
        int doblesSeguidos = 0;

        boolean volverATirar;
        do { // Bucle para permitir volver a tirar si saca dobles
            // Extraer los valores de la jugada forzada
            String[] partes = tirada.split("\\+"); // Dividir por el carácter '+'
            if (partes.length != 2) {
                throw new AccionInvalidaException("Formato de tirada inválido. Usa el formato 'x+y'.");
            }

            int valor1, valor2;
            try {
                valor1 = Integer.parseInt(partes[0].trim());
                valor2 = Integer.parseInt(partes[1].trim());
            } catch (NumberFormatException e) {
                throw new AccionInvalidaException("Error: los valores deben ser números enteros.");
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
                    jugadorActual.encarcelar(this.tablero);
                    throw new AccionInvalidaException("Tercer doble consecutivo: " + jugadorActual.getNombre() + " va directamente a la cárcel.");
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
            boolean solvente = casillaFinal.evaluarCasilla();
            if (!solvente) {
                throw new BancarrotaException(jugadorActual.getNombre() + " no puede cumplir con sus obligaciones en " + casillaFinal.getNombre());
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

    @Override
    public void lanzarDados() throws BancarrotaException, AccionInvalidaException {
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
                    throw new AccionInvalidaException("Tercer doble consecutivo: " + jugadorActual.getNombre() + " va directamente a la cárcel.");
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
            boolean solvente = casillaFinal.evaluarCasilla();
            if (!solvente) {
                throw new BancarrotaException(jugadorActual.getNombre() + " no puede cumplir con sus obligaciones en " + casillaFinal.getNombre());
            }

        } while (volverATirar);

    }

    /*Método que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    @Override
    public void comprar(String nombre) throws ComandoImposibleException, DineroInsuficienteException {
        //Busco si existe una casilla con ese nombre
        Casilla c = tablero.encontrar_casilla(nombre);
        if (c == null) {
            throw new ComandoImposibleException("No existe la casilla '" + nombre + "'.");
        }
        if (!(c instanceof Propiedad)) {
            throw new ComandoImposibleException("La casilla '" + nombre + "' no se puede comprar, pues no es una propiedad que es pueda adquirir.");
        }
        Propiedad p = (Propiedad) c;
        if (this.jugadores.get(turno).getAvatar().getLugar() != c) {
            throw new ComandoImposibleException("El jugador " + this.jugadores.get(turno).getNombre() + " no está en la casilla '" + nombre + "'.");
        }
        if (p.getDuenho().equals(jugadores.get(turno))) {
            throw new ComandoImposibleException("El jugador " + this.jugadores.get(turno).getNombre() + " ya es dueño de la casilla '" + nombre + "'.");
        }
        
        p.comprarPropiedad(jugadores.get(turno));

        Jugador jugadorActual = jugadores.get(turno);
        if (!jugadorActual.getPropiedades().contains(c)) {
            jugadorActual.anhadirPropiedad(p);
        }
    }


    //Método que ejecuta todas las acciones relacionadas con el comando 'salir carcel'. 
    @Override
    public void salirCarcel() throws ComandoImposibleException, DineroInsuficienteException {
        if (jugadores == null || jugadores.isEmpty()) {
            throw new ComandoImposibleException("No hay jugadores en la partida.");
        }
        Jugador jugadorActual = jugadores.get(turno);
        if (!jugadorActual.isEnCarcel()) {
            throw new ComandoImposibleException("El jugador " + jugadorActual.getNombre() + " no está en la cárcel.");
        }
        float fianza = 500000f;

        if(jugadorActual.getFortuna() >= fianza){
            jugadorActual.sumarFortuna(-fianza);
            jugadorActual.sumarGastos(fianza);

            consola.imprimir(jugadorActual.getNombre() + "paga" + fianza + "€ y sale de la cárcel. Puede lanzar los dados."); //aqui se ha sustituido fianza por String.format("%,.f", fianza).replace(",", ".")
        }else{
            throw new DineroInsuficienteException(jugadorActual.getNombre() + " no tiene suficiente dinero para pagar la fianza de " + String.format("%,.0f", fianza).replace(",",".") + "€.");
        }
     
    }

    // Método que realiza las acciones asociadas al comando 'listar enventa'.
    @Override
    public void listarEnVenta() {
        for (ArrayList<Casilla> lado : tablero.getPosiciones()) {
            for (Casilla c : lado) {
                if(c instanceof Propiedad){
                    Propiedad p = (Propiedad) c;
                    p.infoCasillaEnVenta(); // Cada casilla decide si imprime info
                }
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
    public void listarEdificios() throws ComandoImposibleException{
        ArrayList<ArrayList<Casilla>> posiciones = tablero.getPosiciones();
        boolean edificaciones = false;
        for (ArrayList<Casilla> lado : posiciones) {
            for (Casilla c : lado) {
                if(!(c instanceof Solar)){
                    continue;       //Pasamos a analizar la siguiente casillas
                }
                Solar solar = (Solar) c;
                if(!solar.getDuenho().getNombre().equals("Banca") && !solar.getEdificios().isEmpty()){
                    edificaciones = true;
                    for(Edificacion edificio : solar.getEdificios()){
                        consola.imprimir(
                            "{\n" +
                            "\tid: " + edificio.toString() + ",\n" +
                            "\tpropietario: " + solar.getDuenho().getNombre() + ",\n" +
                            "\tcasilla: " + solar.getNombre() + ",\n" +
                            "\tgrupo: " + solar.getGrupo().colorToNombreGrupo() + ",\n" +
                            "\tcoste: " + edificio.getValor() + "€,\n" +
                            "}");
                    }
                }
            }
        }
        if (edificaciones==true){
            throw new ComandoImposibleException("No hay edificaciones en la partida");
        }
    }
    public Grupo StringToGrupo(String color) throws ComandoImposibleException{
        // Se convierte el color de entrada a minúsculas para asegurar la coincidencia
        // y se busca directamente en el HashMap. Es mucho más eficiente.
        Grupo grupo = tablero.getGrupos().get(color);
        if (grupo == null) {
            throw new ComandoImposibleException("Error: El grupo '" + color + "' no existe.");
        }
        return grupo; 
    }
    //Función para listar los edificios de un grupo concreto
    @Override
    public void listarEdificiosPorGrupo(String grupoNombre) throws ComandoImposibleException{
        Grupo grupo = StringToGrupo(grupoNombre);
        if(grupo == null){
            throw new ComandoImposibleException("El grupo especificado no existe.");
        }
        ArrayList<Propiedad> miembrosGrupo = grupo.getMiembros();
        boolean puedeConstruirCasa = false;
        boolean puedeConstruirHotel = false;
        boolean puedeConstruirPiscina = false;
        boolean puedeConstruirPista = false;

        for (Propiedad s : miembrosGrupo) {
            if (!(s instanceof Solar)) {
                this.consola.imprimir("Las casillas del grupo " + grupo.getcolorGrupo() + " no son solares.");
                return; // Saltar si no es un solar
            }
            Solar solar = (Solar) s;
            ArrayList<Edificacion> edificios = solar.getEdificios();
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
        String alquilerStr = String.valueOf(s.getAlquiler());

        consola.imprimir("{\n" +
            "\tpropiedad: " + s.getNombre() + ",\n" +
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
    @Override
    public void acabarTurno() throws ComandoImposibleException {
        if(jugadores == null || jugadores.isEmpty()){
            throw new ComandoImposibleException("No hay jugadores en la partida.");
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

    @Override
    public void edificar(String tipoEdificio) throws ConstruccionException, DineroInsuficienteException {
        Jugador jugadorActual = jugadores.get(turno);
        Avatar avatarActual = jugadorActual.getAvatar();
        Casilla casillaActual = avatarActual.getLugar();

        //Comprobar que la casilla es un solar
        if (!casillaActual.getTipo().equals("Solar")) {
            throw new ConstruccionException("No se puede edificar en una casilla de tipo '" + casillaActual.getTipo() + "'.");        }
        //Casting explicito para asegurarnos de que aplicamos metodos de propiedad sobre la casilla
        Solar solarActual = (Solar) casillaActual;

        // Comprobar que el jugador es el dueño de la casilla
        if (!solarActual.getDuenho().equals(jugadorActual)) {
            throw new ConstruccionException("No eres el dueño de la casilla '" + solarActual.getNombre() + "'.");        }
        //Comprobar que el jugador posee todas las casillas del grupo
        Grupo grupo = solarActual.getGrupo();
        if (grupo == null) {
            throw new ConstruccionException("Error: La casilla no pertenece a ningún grupo.");
        }
        if (!grupo.esDuenhoGrupo(jugadorActual)) {
            throw new ConstruccionException("Debes ser dueño de todas las propiedades del grupo " + grupo.colorToNombreGrupo() + " para poder edificar.");
        }

        // Comprobar que el tipo de edificio es válido
        float costeEdificio = grupo.getPrecioEdificioPorGrupo(tipoEdificio);
        if (costeEdificio <= 0) {
            throw new ConstruccionException("El tipo de edificio '" + tipoEdificio + "' no es válido.");
        }

        //comprobaciones con respecto a las casillas que ya existen
        boolean casillaTieneHotel = false;
        boolean casillaTienePiscina = false;
        boolean casillaTienePistaDeportiva =false;
        int numeroCasas=0;
        Iterator<Edificacion> iterator = solarActual.getEdificios().iterator();
        while(iterator.hasNext()){
            Edificacion edificioRecorrido = iterator.next();
            if(edificioRecorrido.getTipo().equals("piscina")) casillaTienePiscina=true;
            if(edificioRecorrido.getTipo().equals("pista deportiva")) casillaTienePistaDeportiva=true;
            if(edificioRecorrido.getTipo().equals("hotel")) casillaTieneHotel =true;
            if(edificioRecorrido.getTipo().equals("casa")) numeroCasas++;
        }
        //Si tienes hotel o 4 casas no puedes contruir mas casas
        if((numeroCasas ==4 || casillaTieneHotel) && tipoEdificio.equals("casa")){
            throw new ConstruccionException("No se puede edificar mas casas en la casilla " + casillaActual.getNombre());
        }
        //Comprobar que hay sitio para edificar
        if((casillaTieneHotel && casillaTienePiscina && casillaTienePistaDeportiva)){
            throw new ConstruccionException("No se puede edificar ningun edificio mas en esta casilla ni en el grupo al que la casillla pertenece");
        }
        //Comprobar si acaso ya existe un hotel, pista deportiva o piscina cuando se quiere crear una
        if(casillaTieneHotel && tipoEdificio.equals("hotel")){
            throw new ConstruccionException("No se puede edificar un hotel ya que ya existe un hotel en la casilla " + casillaActual.getNombre());
        }
        //No se puede adificar mas de una piscina
        if(casillaTienePiscina && tipoEdificio.equals("piscina")){
            throw new ConstruccionException("No se puede edificar una piscina ya que ya existe una en la casilla " + solarActual.getNombre());
        }
        //Comprobar si acasa ya existe un hotel, pista deportiva o piscina cuando se quiere crear una
        if(casillaTienePistaDeportiva && tipoEdificio.equals("pista deportiva")){
            throw new ConstruccionException("No se puede edificar una pista deportiva ya que ya existe una en la casilla " + solarActual.getNombre());
        }
        // Comprobar que la casilla dispone de un hotel para crear una piscina
        if(!casillaTieneHotel && tipoEdificio.equals("piscina")){
            throw new ConstruccionException("No se puede edificar una piscina ya que no se dispone de un hotel.");
        }
        // Comprobar si acaso se quiere construir un hotel que el jugador tiene 4 casas en ese hotel
        if(!(numeroCasas==4) && tipoEdificio.equals("hotel")){
            throw new ConstruccionException("No se disponen de las cuatro casa necesarias para contruir el hotel");
        }
        //Comprobar que el jugador tiene suficiente dinero
        if (jugadorActual.getFortuna() < costeEdificio) {
            throw new DineroInsuficienteException("La fortuna de "+ jugadorActual + " no es suficinete para edificar un" + tipoEdificio + " en la casilla " + solarActual.getNombre());
        }
        //Hacer el pago por la contruccion y realizar la edificacion
        jugadorActual.sumarFortuna(-costeEdificio);
        jugadorActual.sumarGastos(costeEdificio);
        //Si vamos a crear un hotel y hemos comprobado que existen 4 casas tenemos que eliminar las casas
        if(tipoEdificio.equals("hotel")){
            solarActual.getEdificios().removeIf(edificio -> edificio.getTipo().equals("casa"));
            consola.imprimir("Se han eliminado las casas para poder construir el hotel");
        }
        //Ahora cuando instanciamos un Edificio especificamos el tipo
        switch (tipoEdificio) {
            case "hotel":
                solarActual.anhadirEdificacion(new Hotel());
                break;
            case "piscina":
                solarActual.anhadirEdificacion(new Piscina());
                break;
            case "pista deportiva":
                solarActual.anhadirEdificacion(new PistaDeportiva());
                break;
            case "casa":
                solarActual.anhadirEdificacion(new Casa());
                break;
            default:
                break;
        }        
        consola.imprimir("Se ha edificado un " + tipoEdificio + " en " + casillaActual.getNombre() + ". La fortuna de " + jugadorActual.getNombre() + " se reduce en " + costeEdificio + "€");
    }
    
    @Override
    public void venderEdificacion(String tipoEdificio, String nombreCasilla, String cantidadStr) throws ComandoImposibleException, ArgumentosComandoException{
        Jugador jugadorActual = jugadores.get(turno);
        int cantidad;
        //Comprobacion del valor cantidadStr es un valor positivo
        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                throw new ArgumentosComandoException("La cantidad a vender debe ser un número positivo.");
            }
        } catch (Exception e) {
            throw new ArgumentosComandoException("La cantidad debe ser un número entero válido.");
        }

        Casilla casillaInsertada = tablero.encontrar_casilla(nombreCasilla);
        if (casillaInsertada == null) {
            throw new ComandoImposibleException("No se ha encontrado la casilla '" + nombreCasilla + "'.");
        }
        if(!(casillaInsertada instanceof Solar)){
            throw new ComandoImposibleException("La casilla no es un solar y por lo tanto no se puede edificar sobre ella");
        }

        Solar casilla = (Solar) casillaInsertada;

        if (!casilla.getDuenho().equals(jugadorActual)) {
            throw new ComandoImposibleException("No eres el dueño de la casilla '" + nombreCasilla + "'.");
        }

        ArrayList<Edificacion> edificiosEnCasilla = casilla.getEdificios();
        ArrayList<Edificacion> edificiosAVender = new ArrayList<>();

        for (Edificacion e : edificiosEnCasilla) {
            if (e.getTipo().equalsIgnoreCase(tipoEdificio)) {
                edificiosAVender.add(e);
            }
        }

        if (edificiosAVender.isEmpty()) {
            throw new ComandoImposibleException("No existen edificios de tipo '" + tipoEdificio + "' en la casilla '" + nombreCasilla + "'.");
        }

        if (cantidad > edificiosAVender.size()) {
            throw new ComandoImposibleException("No tienes " + cantidad + " edificios de tipo '" + tipoEdificio + "' para vender en '" + nombreCasilla + "'. Solo tienes " + edificiosAVender.size() + ".");
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
            for (int i = 0; i < 4; i++) {
                casilla.anhadirEdificacion(new Casa());
            }
        }
    }

    @Override
    public void hipotecarPropiedad(String casilla) throws ComandoImposibleException{
        Casilla c = tablero.encontrar_casilla(casilla);
        Jugador JugadorActual = jugadores.get(turno);

        if(c == null){
            throw new ComandoImposibleException("Error: no existe ninguna casilla con el nombre '" + casilla + "'.");
        }
        if(!(c instanceof Solar)){
            throw new ComandoImposibleException("No se puede hipotecar una casilla que no es de tipo Solar.");
        }
        Solar solar = (Solar) c;
        
        //Comprobar que el jugador sea duenho de la casilla
        if(!solar.getDuenho().equals(JugadorActual)){
            throw new ComandoImposibleException("El jugador " + JugadorActual.getNombre() + " no puede hipotecar " + c.getNombre() +". No es una propiedad que le pertenece.");
        }
        //Comprobar que la casilla no este ya hipotecada
        if(solar.getEstarHipotecada()){
            throw new ComandoImposibleException("El jugador " + JugadorActual.getNombre() + " no puede hipotecar " + c.getNombre() + ". Ya esta hipotecada");  
        } 
        
        //Si la casilla es solar y tiene edificaciones
        if(solar.getTipo().equals("Solar") && !solar.getEdificios().isEmpty()){
            throw new ComandoImposibleException("El jugador " + JugadorActual.getNombre() + " no puede hipotecar " + c.getNombre() + ". Primero debe vender todos sus edificios.");
        }
        else if(solar.getEdificios().isEmpty()){
            banca.sumarFortuna(-solar.getHipoteca());
            JugadorActual.sumarFortuna(solar.getHipoteca());
            solar.setEstarHipotecada(true);
            consola.imprimir("El jugador " + JugadorActual.getNombre() + " recibe " + solar.getHipoteca() + "€ por hipotecar " + c.getNombre());
            if(solar.getGrupo() != null && solar.getGrupo().esDuenhoGrupo(JugadorActual)){
                consola.imprimir("No puede recibir alquileres ni edificar en el grupo " + solar.getGrupo().colorToNombreGrupo());
            }else{
                consola.imprimir("No se puede recibir alquileres.");
            }
        }
        return;

    }

    @Override
    public void deshipotecar(String casilla) throws ComandoImposibleException, DineroInsuficienteException{
        Casilla c = tablero.encontrar_casilla(casilla);
        Jugador JugadorActual = jugadores.get(turno);

        if(c == null){
            throw new ComandoImposibleException("Error: no existe ninguna casilla con el nombre '" + casilla + "'.");
        }
        
        if(!(c instanceof Solar)){
            throw new ComandoImposibleException("No se puede hipotecar una casilla que no es de tipo Solar.");
        }

        Solar solar = (Solar) c;

        if(!solar.getTipo().equals("Solar")){
            throw new ComandoImposibleException("La casilla " + solar.getNombre() + " no es de tipo Solar. No se puede deshipotecar.");
        }   
        //Comprobar que el jugador sea duenho de la casilla
        if(!solar.getDuenho().equals(JugadorActual)){
            throw new ComandoImposibleException("El jugador " + JugadorActual.getNombre() + " no puede deshipotecar " + solar.getNombre() +". No es una propiedad que le pertenece.");
        }
        //Comprobar que la casilla no este ya hipotecada
        if(!solar.getEstarHipotecada()){
            throw new ComandoImposibleException("El jugador " + JugadorActual.getNombre() + " no puede deshipotecar " + solar.getNombre() + ". No esta hipotecada");  
        } 

        if(JugadorActual.getFortuna() < solar.getHipoteca()){
            throw new DineroInsuficienteException("El jugador " + JugadorActual.getNombre() + " no tiene suficiente dinero para deshipotecar " + solar.getNombre() +". Se necesitan " + solar.getHipoteca() + "€.");
        }
        
        JugadorActual.sumarFortuna(-solar.getHipoteca());
        JugadorActual.sumarGastos(solar.getHipoteca());
        banca.sumarFortuna(solar.getHipoteca());
        solar.setEstarHipotecada(false);

        consola.imprimir("El jugador " + JugadorActual.getNombre() + " paga " + solar.getHipoteca() + "€ por deshipotecar " + solar.getNombre() + ".");
        if(solar.getGrupo() != null && solar.getGrupo().esDuenhoGrupo(JugadorActual)){
            consola.imprimir("Ahora puede recibir alquileres y edificar en el grupo " + solar.getGrupo().colorToNombreGrupo() + ".");
        }else{
            consola.imprimir("Ahora se puede recibir alquileres.");
        }
        return;
    }
    
    @Override
    public void mostrarEstadisticas(String jugador) throws ComandoImposibleException{
        Jugador jugadorEncontrado = null;
        for (Jugador jugadorE: jugadores){
            if(jugadorE.getNombre().equals(jugador)){
                jugadorEncontrado = jugadorE;
                break;
            }
        }
        if(jugadorEncontrado == null){
            throw new ComandoImposibleException("Jugador no encontrado");
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
            if(numeroMaximoVueltas < jugador.getVueltas()){
                numeroMaximoVueltas = jugador.getVueltas();
                jugadoresMasVueltas.clear(); //Limpio el arraylist
                jugadoresMasVueltas.add(jugador.getNombre()); //Añado el nuevo jugador con mas vueltas
                
            }else if(numeroMaximoVueltas == jugador.getVueltas() && jugador.getNombre() != "Banca"){
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
            for(Propiedad p :jugador.getPropiedades()){
                fortunaTotal += p.getValor();
                
                if(p instanceof Solar){
                    Solar s = (Solar) p;
                    if(s.getEdificios() != null){
                        for(Edificacion e: s.getEdificios()){
                            fortunaTotal += s.getValor();
                        }
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

    @Override
    public void mostrarEstadisticasJuego(){
        consola.imprimir("{");
        consola.imprimir("casillaMasRentable: " + corchetesToComas(tablero.buscarCasillaMasRentable()) + ",");
        consola.imprimir("grupoMasRentable: " + corchetesToComas(tablero.buscarGrupoMasRentable()) +  ",");
        consola.imprimir("casillaMasFrecuentada: " + corchetesToComas(tablero.buscarCasillaMasVisitada()) + ",");
        consola.imprimir("jugador(es)MasVueltas: " + corchetesToComas(buscarJugadorConMasVueltas()) + ",");
        consola.imprimir("jugadorEnCabeza: " + corchetesToComas(buscarJugadorEnCabeza()) + ",");
        consola.imprimir("}");
    }

    @Override
    public void verTablero() {
            String[][] mostrar = new String[11][11];
            // Lado Sur 
            ArrayList<Casilla> sur = tablero.getPosiciones().get(0);
            for (int i = 0; i < 10; i++) {  //Se insertaron las casillas del lado sur en orden inverso (salida empieza a la izquierda)
                mostrar[10][10 - i] = formatearCasilla(sur.get(i));    //Por lo que se deben imprimir en orden inverso.
            }
            // Lado Oeste 
            ArrayList<Casilla> oeste = tablero.getPosiciones().get(1);
            for (int i = 0; i < 10; i++) {
                mostrar[10 - i][0] = formatearCasilla(oeste.get(i));
            }
            // Lado Norte
            ArrayList<Casilla> norte = tablero.getPosiciones().get(2);
            for (int i = 0; i < 10; i++) {
                mostrar[0][i] = formatearCasilla(norte.get(i));
            }
            // Lado Este
            ArrayList<Casilla> este = tablero.getPosiciones().get(3);
            for (int i = 0; i < 10; i++) {
                mostrar[i][10] = formatearCasilla(este.get(i));
            }
            // Rellenar espacios vacíos
            for (int i = 1; i < 10; i++) {
                for (int j = 1; j < 10; j++) {
                    // Ajustar el espacio central al ancho de la casilla + 3 caracteres ([... ])
                    mostrar[i][j] = "               ";
                }
            }
            // Imprimir el tablero
            for (int i = 0; i < mostrar.length; i++) {
                for (int j = 0; j < mostrar.length; j++){
                    if(i == 0 || i == 10 || j == 0 || j == 10){
                        System.out.print("[" + mostrar[i][j] + " ]");
                    } else {
                        System.out.print(mostrar[i][j]);
                    }
            }
            System.out.println();
        }
    }

    /*
    Método privado para darle formato a cada casilla que
    se imprimirá en el tablero.
    */
    private String formatearCasilla(Casilla c){
        String nombre = c.getNombre();
        String avatares = "";
        // 1. Obtener la cadena de texto base (sin colores)
        if(c.getAvatares() != null && !c.getAvatares().isEmpty()){
            avatares = "&";
            for(Avatar a : c.getAvatares()){
                avatares += a.getId();
            }
        }

        String texto_base = nombre + " " + avatares; // Combinar nombre y avatares

        // 2. Aplicar el ancho fijo de 10 caracteres al texto BASE
        if(texto_base.length() < 12){
            texto_base = String.format("%-12s", texto_base);
        } else if(texto_base.length() > 12){
            texto_base = texto_base.substring(0, 12);
        }

        // AHORA, añadir el color (si existe) al texto BASE formateado
        if(c instanceof Propiedad){
            Propiedad p = (Propiedad) c;
            Grupo g = p.getGrupo();
            if(g != null && g.getcolorGrupo() != null){
                // Concatenamos las secuencias ANSI al String que ya tiene 10 caracteres
                return g.getcolorGrupo() + texto_base + Valor.RESET;
            } else {
                return texto_base;
            }            
        }
        return texto_base;
    }
}