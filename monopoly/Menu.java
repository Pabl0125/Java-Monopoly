package monopoly;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Iterator;

import java.lang.Thread;
import partida.*;

public class Menu {

    //Atributos
    private ArrayList<Jugador> jugadores; //Jugadores de la partida.
    private ArrayList<Avatar> avatares; //Avatares en la partida.
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
    
    public void lecturaFichero(String fichero){
        File file = new File(fichero);
        try(Scanner sc = new Scanner(file)){
            while(sc.hasNextLine()){
                String linea = sc.nextLine();
                analizarComando(linea);
                try{
                    Thread.sleep(250);
                }
                catch(Exception e){
                    System.out.println("Error en el sleep");
                    return;
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("Error: Fichero no encontrado.");
        }
    }

    /**
    * Método que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetro: cadena de caracteres (el comando).
    */
    private void analizarComando(String comando) {
        System.out.println(">> " + comando);     //Impresion del comando insertado
        String[] partesComando = comando.split(" ");
        System.out.println("");
            if(partesComando.length > 0){
                String comandoPrincipal = partesComando[0];
            switch (comandoPrincipal) {
                case "crear":
                    if(partesComando[1].equals("jugador") && !partesComando[3].isEmpty()){ //Si se selecciona la opcion jugador dentro de crear y se anhade un campo avatar 
                        Jugador nuevoJugador = new Jugador(partesComando[2], partesComando[3], tablero.getPosiciones().get(0).get(0), avatares);
                        jugadores.add(nuevoJugador);
                        System.out.println("{\n"+ nuevoJugador.toString()+"\n}");
                    }
                    else System.err.println("Invalid command");
                    break;
                    
                case "jugador":
                    if (partesComando.length == 1) {
                        System.out.println("{\n"+ this.jugadorConTurno(turno).toString() +"\n}");
                    }
                    else System.err.println("Invalid command");
                    break;

                case "listar":
                    if(partesComando.length==2 && partesComando[1].equals("jugadores")){
                        this.listarJugadores();
                    }
                    else if(partesComando.length ==2 && partesComando[1].equals("enventa")){
                        this.listarVenta();
                    }
                    else if(partesComando.length == 3 && partesComando[1].equals("edificios") && partesComando[2].equals("construidos")){
                        this.listarEdificios();
                    }
                    else if(partesComando.length == 3 && partesComando[1].equals("edificios")){
                        Grupo grupo = StringToGrupo(partesComando[2]);
                        if(grupo != null){
                            this.listarEdificiosPorGrupo(grupo);
                        }
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
                        System.out.println("{");
                        this.descJugador(partesComando[2]);
                        System.out.println("}");
                    }
                    else if (partesComando.length == 2 && ((tablero.encontrar_casilla(partesComando[1]) instanceof Casilla))) {
                        System.out.println("{");
                        this.descCasilla(partesComando[1]);
                        System.out.println("},");
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
                    else System.err.println("Invalid command. Formato: vender <tipo_edificio> <nombre_casilla> <cantidad>");
                    break;
                case "estadisticas":
                    if(partesComando.length == 2){
                        Jugador jugador = jugadores.get(turno);
                        mostrarEstadisticas(jugador);
                    }
                    else System.err.println("Invalid command");
                    break;
                case "edificar":
                    //casa
                    if(partesComando.length == 2 && partesComando[1].equals("casa")) edificar("casa");
                    else if(partesComando.length == 2 && partesComando[1].equals("hotel")) edificar("hotel");
                    else if(partesComando.length == 2 && partesComando[1].equals("pista deportiva")) edificar("pista deportiva");
                    else if(partesComando.length == 2 && partesComando[1].equals("piscina")) edificar("piscina");
                    else System.err.println("Invalid command");
                    break;
                case "ver":
                    if(partesComando.length == 2 && partesComando[1].equals("tablero")){
                        tablero.imprimirTablero();
                    }
                    else System.err.println("Invalid command");
                    break;
                default:
                    // Se ejecuta si el comando no coincide con ningún case
                    System.out.println("Error: Comando desconocido '" + comandoPrincipal + "'");
                    break;
            }
        }
    }

    /*Método que realiza las acciones asociadas al comando 'crear jugador'.
    Parametro: Comando introducido + nombre del jugador
    */
    public void crearJugador(String comando, String nombre, String tipoAvatar){
        //Comprobamos si existe ya un jugador con el nombre introducido
        for(Jugador j: jugadores){
            if(j.getNombre().equals(nombre)){
                System.out.println("Ya existe un jugador con el nombre '" + nombre + "'.");
                return;
            }
        }
        //Obtenemos la casilla inicial
        Casilla inicial = null;
        for(ArrayList<Casilla> lado : tablero.getPosiciones()){
            for(Casilla c : lado){
                if(c.getNombre().equals("Salida")){
                    inicial = c;
                    break;
                }
            }
            if(inicial != null){
                break;
            }
        }
        
        if(inicial == null){
            System.out.println("Error al crear el jugaor.");
            return;
        }
        //Crear el jugador y añadirlo (el propio constructor de jugador ya crea el avatar, llamando al constructor de avatar)
        Jugador nuevoJugador = new Jugador(nombre, tipoAvatar, inicial, avatares);
        jugadores.add(nuevoJugador);
        
        //Añadir el avatar (ya creado)
        //El identificador del avatar se genera automaticamente en el constructor
        

        System.out.println("Jugador '" + nombre + "' creado correctamente.");
        System.out.println("Avatar: " + nuevoJugador.getAvatar());

    }
    /**
     * Devuelgetpove el Jugador con el turno actual
     * @param turno
     * @return
    */
    public Jugador jugadorConTurno(int turno){
        return jugadores.get(turno);
    }

    
    /*Método que realiza las acciones asociadas al comando 'describir jugador'.
    * Parámetro: comando introducido
     */
    private void descJugador(String nombre) {
        int encontrado = 0;
        for (Jugador j:jugadores){
            if (j.getNombre().equals(nombre)){
                System.out.println(j.toString());
                encontrado = 1;
                break;
            }
        }
        if (encontrado == 0){
            System.out.println("No se encontro ningun jugador con el nombre '" + nombre + "'.");
        }
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
    private void descCasilla(String nombre) {
        //Buscamos la casilla a describir
        Casilla c = tablero.encontrar_casilla(nombre);

        //Si no existe ninguna casilla con ese nombre
        if(c == null){
            System.out.println("Error: no existe ninguna casilla con el nombre '" + nombre + "'.");
        }
        String info = c.infoCasilla();
        
        //Si la casilla tiene informacion para describirla
        if(info != null){
            System.out.println(info);
        } else{ //Si la casilla no tiene informacion
            System.out.println("La casilla '" + nombre + "' no tiene informacion detallada (es una casilla especial).");
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
                System.out.println("Formato de tirada inválido. Usa el formato 'x+y'.");
                return;
            }

            int valor1, valor2;
            try {
                valor1 = Integer.parseInt(partes[0].trim());
                valor2 = Integer.parseInt(partes[1].trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: los valores deben ser números enteros.");
                return;
            }

            // Asignar los valores a los dados (igual que si se hubiesen lanzado)
            this.dado1.setValor(valor1);
            this.dado2.setValor(valor2);
            int total = valor1 + valor2;

            System.out.println("Dados forzados: " + valor1 + " y " + valor2 + " (total: " + total + ")");

            // Comprobar dobles
            /*if (valor1 == valor2) {
                doblesSeguidos++;
                if (doblesSeguidos == 3) {
                    avatarActual.setLugar(tablero.encontrar_casilla("Carcel"));
                    avatarActual.getJugador().encarcelar(this.tablero);
                    System.out.println("Tercer doble consecutivo: " + jugadorActual.getNombre() + " va directamente a la cárcel.");
                    break;
                }
                volverATirar = true;
                System.out.println("¡Doble forzado! " + jugadorActual.getNombre() + " puede volver a tirar después de evaluar la casilla.");
            } else {
                volverATirar = false;
                doblesSeguidos = 0;
            }*/
            volverATirar = false;

            // Mover avatar y mostrar movimiento
            Casilla casillaInicial = avatarActual.getLugar();
            avatarActual.moverAvatar(this.tablero, valor1+valor2);
            Casilla casillaFinal = avatarActual.getLugar();
            

            System.out.println("El avatar " + avatarActual.getId() +
                    " avanza desde " + casillaInicial.getNombre() +
                    " hasta " + casillaFinal.getNombre() + ".");

            // Evaluar la casilla
            boolean solvente = casillaFinal.evaluarCasilla(jugadorActual, banca, total, this.tablero);
            if (!solvente) {
                System.out.println(jugadorActual.getNombre() + " no puede cumplir con sus obligaciones en " + casillaFinal.getNombre());
            }

            // Si la casilla es de tipo Suerte o Comunidad, ejecutar la carta desde Menu
            if (casillaFinal.getTipo() != null) {
                if (casillaFinal.getTipo().equals("Suerte")) {
                    this.cartaSuerte(this.numCartaSuerte);
                } else if (casillaFinal.getTipo().equals("Comunidad")) {
                    this.cartaCajaComunidad(this.numCartaCajaCom);
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

            System.out.println("Dados: " + valor1 + " y " + valor2 + " (total: " + total + ")");

            // Comprobar dobles
            if (valor1 == valor2) {
                doblesSeguidos++;
                if (doblesSeguidos == 3) {
                    avatarActual.setLugar(tablero.encontrar_casilla("Carcel"));
                    jugadorActual.encarcelar(this.tablero);
                    System.out.println("Tercer doble consecutivo: " + jugadorActual.getNombre() + " va directamente a la cárcel.");
                    break;
                }
                volverATirar = true;
                System.out.println("¡Doble! " + jugadorActual.getNombre() + " puede volver a tirar después de evaluar la casilla.");
            } else {
                volverATirar = false;
                doblesSeguidos = 0;
            }

            // Mover avatar y mostrar movimiento
            Casilla casillaInicial = avatarActual.getLugar();
            avatarActual.moverAvatar(this.tablero, valor1+valor2);
            Casilla casillaFinal = avatarActual.getLugar();

            System.out.println("El avatar " + avatarActual.getId() +
                    " avanza desde " + casillaInicial.getNombre() +
                    " hasta " + casillaFinal.getNombre() + ".");

            // Evaluar la casilla usando su propio método
            boolean solvente = casillaFinal.evaluarCasilla(jugadorActual, banca, total, this.tablero);
            if (!solvente) {
                System.out.println(jugadorActual.getNombre() + " no puede cumplir con sus obligaciones en " + casillaFinal.getNombre());
            }

            // Si la casilla es de tipo Suerte o Comunidad, ejecutar la carta desde Menu
            if (casillaFinal.getTipo() != null) {
                if (casillaFinal.getTipo().equals("Suerte")) {
                    this.cartaSuerte(this.numCartaSuerte);
                } else if (casillaFinal.getTipo().equals("Comunidad")) {
                    this.cartaCajaComunidad(this.numCartaCajaCom);
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
            System.out.println("No existe la casilla '" + nombre + "'.");
            return;
        }
        if (this.jugadores.get(turno).getAvatar().getLugar() != c) {
            System.out.println("El jugador " + this.jugadores.get(turno).getNombre() + " no está en la casilla '" + nombre + "'.");
            return;
        }
        if (!c.getDuenho().equals(jugadores.get(turno))) {
            System.out.println("El jugador " + this.jugadores.get(turno).getNombre() + " ya es dueño de la casilla '" + nombre + "'.");
            return;
        }
        //Obtengo su tipo y si se puede comprar o no
        String tipo = c.getTipo();
        if (!tipo.equals("Solar") && !tipo.equals("Transporte") && !tipo.equals("Servicios")) {
            System.out.println("La casilla '" + nombre + "' no se puede comprar.");
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
            System.out.println("No hay jugadores en la partida.");
            return;
        }
        Jugador jugadorActual = jugadores.get(turno);
        if (!jugadorActual.isEnCarcel()) {
            System.out.println("El jugador " + jugadorActual.getNombre() + " no está en la cárcel.");
            return;
        }
        float fianza = 500000f;

        if(jugadorActual.getFortuna() >= fianza){
            jugadorActual.sumarFortuna(-fianza);
            jugadorActual.sumarGastos(fianza);

            System.out.println(jugadorActual.getNombre() + "paga" + fianza + "€ y sale de la cárcel. Puede lanzar los dados."); //aqui se ha sustituido fianza por String.format("%,.f", fianza).replace(",", ".")
        }else{
            System.out.println(jugadorActual.getNombre() + " no tiene suficiente dinero para pagar la fianza de " + String.format("%,.0f", fianza).replace(",",".") + "€.");
            System.out.println("Debe intentar sacar dobles o declararse en bancarrota si no puede conseguir el dinero.");
            //POSTERIORMENTE SE IMPLEMENTARÁ LA CASUÍSTICA DE SACAR DOBLES/BANCARROTA/CARTA DE LA SUERTE
        }
     
    }

    // Método que realiza las acciones asociadas al comando 'listar enventa'.
    private void listarVenta() {
        for (ArrayList<Casilla> lado : tablero.getPosiciones()) {
            for (Casilla c : lado) {
                c.casEnVenta(); // Cada casilla decide si imprime info
            }
        }
    }


    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {
        if(jugadores == null || jugadores.isEmpty()){
            System.out.println("No hay jugadores en la partida.");
            return;
        }
        for (Jugador jugador : jugadores) {
            if(jugador.getAvatar()==null){  //La banca no tiene avatar, y por lo tanto no debe imprimirse
                continue;
            }
            System.out.println("{");
            System.out.println(jugador); //Se omite el toString() ya que se llama implícitamente.  
            System.out.println("},");
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
    private void listarEdificios(){
        ArrayList<ArrayList<Casilla>> posiciones = tablero.getPosiciones();

        for (ArrayList<Casilla> lado : posiciones) {
            for (Casilla c : lado) {

                if(c.getTipo().equals("Solar") && !c.getDuenho().equals("Banca")){

                    ArrayList<Edificacion> edificios = c.getEdificios();

                    for(Edificacion edificio : edificios){
                        
                        Jugador dueño = c.getDuenho();
                        Avatar avatarDueño = dueño.getAvatar();
                        System.out.println(
                            "{\n" +
                            "\tid: " + edificio.toString() + ",\n" +
                            "\tpropietario: " + dueño.getNombre() + ",\n" +
                            "\tcasilla: " + c.getNombre() + ",\n" +
                            "\tgrupo: " + c.getGrupo().colorToNombreGrupo() + ",\n" +
                            "\tcoste: " + edificio.getValor() + "€,\n" +
                            "}");
                    }
                }
            }
        }
    }
    public Grupo StringToGrupo(String color){
        //Recorremos el hasmap de grupos
        for(String clave: tablero.getGrupos().keySet()){
            if(clave.equals(color)) return tablero.getGrupos().get(clave);
        }
        return null;
    }
    //Función para listar los edificios de un grupo concreto
    private void listarEdificiosPorGrupo(Grupo grupo){

        ArrayList<ArrayList<Casilla>> posiciones = tablero.getPosiciones();

        for (ArrayList<Casilla> lado : posiciones) {
            for (Casilla c : lado) {

                if(c.getTipo().equals("Solar") && !c.getDuenho().equals("Banca") && c.getGrupo().equals(grupo)){

                    ArrayList<Edificacion> edificios = c.getEdificios();
                    ArrayList<Edificacion> hoteles = new ArrayList<Edificacion>();
                    ArrayList<Edificacion> casas = new ArrayList<Edificacion>();
                    ArrayList<Edificacion> piscinas = new ArrayList<Edificacion>();
                    ArrayList<Edificacion> pistasDeportivas = new ArrayList<Edificacion>();
                    
                    for(Edificacion edificio : edificios){
                        
                        Jugador dueño = c.getDuenho();
                        Avatar avatarDueño = dueño.getAvatar();

                        switch(edificio.getTipo()){
                            case "hotel":
                                hoteles.add(edificio);
                                break;
                            case "casa":
                                casas.add(edificio);
                                break;
                            case "piscina":
                                piscinas.add(edificio);
                                break;
                            case "pista deportiva":
                                pistasDeportivas.add(edificio);
                                break;
                        }

                    }
                    System.out.println(
                            "{\n" +
                            "\tpropiedad: " + c.getNombre() + ",\n" +
                            "\thoteles: " + hoteles + ",\n" +
                            "\tcasas: " + casas + ",\n" +
                            "\tpiscinas: " + piscinas + ",\n" +
                            "\tpistas deportivas: " + pistasDeportivas + ",\n" +
                            "\talquiler: " + c.getImpuesto()+ "€,\n" +
                            "}");
                }
            }
        }
    }
    

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
        if(jugadores == null || jugadores.isEmpty()){
            System.out.println("No hay jugadores en la partida.");
            return;
        }
        // Si es el turno del último jugador, el turno pasa al primer jugador real (índice 1),
        // saltándose a la banca (índice 0).
        if(turno >= jugadores.size() - 1) { 
            turno = 1;  //Reiniciar vuelta al primer jugador (no a la banca)

        } else {
            turno++;
        }

        System.out.println("El jugador actual es " + jugadores.get(turno).getNombre() + ".");
    }

    private void imprimirJugadorTurno() {
        System.out.println("Nombre:" + this.jugadores.get(turno).getNombre() + "\nAvatar: " + this.jugadores.get(turno).getAvatar().getId());
    }

    private void edificar(String tipoEdificio) {
        Jugador jugadorActual = jugadores.get(turno);
        Avatar avatarActual = jugadorActual.getAvatar();
        Casilla casillaActual = avatarActual.getLugar();

        //Comprobar que la casilla es un solar
        if (!casillaActual.getTipo().equals("Solar")) {
            System.out.println("No se puede edificar en una casilla de tipo '" + casillaActual.getTipo() + "'.");
            return;
        }

        // Comprobar que el jugador es el dueño de la casilla
        if (!casillaActual.getDuenho().equals(jugadorActual)) {
            System.out.println("No eres el dueño de la casilla '" + casillaActual.getNombre() + "'.");
            return;
        }

        //Comprobar que el jugador posee todas las casillas del grupo
        Grupo grupo = casillaActual.getGrupo();
        if (grupo == null) {
            System.out.println("Error: La casilla no pertenece a ningún grupo.");
            return;
        }
        if (!grupo.esDuenhoGrupo(jugadorActual)) {
            System.out.println("Debes ser dueño de todas las propiedades del grupo " + grupo.colorToNombreGrupo() + " para poder edificar.");
            return;
        }

        // Comprobar que el tipo de edificio es válido
        float costeEdificio = grupo.getPrecioEdificioPorGrupo(tipoEdificio);
        if (costeEdificio <= 0) {
            System.out.println("El tipo de edificio '" + tipoEdificio + "' no es válido.");
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
            if(edificioRecorrido.getTipo().equals("piscina")) casillaTieneHotel =true;
            if(edificioRecorrido.getTipo().equals("pista deportiva")) casillaTienePiscina=true;
            if(edificioRecorrido.getTipo().equals("piscina")) casillaTienePiscina =true;
            if(edificioRecorrido.getTipo().equals("casa")) numeroCasas++;
        }
        //Comprobar que hay sitio para edificar
        if((casillaTieneHotel && casillaTienePiscina && casillaTienePistaDeportiva)){
            System.out.println("No se puede edificar ningun edificio mas en esta casilla ni en el grupo al que la casillla pertenece");
            return;
        }
        //Comprobar si acaso ya existe un hotel, pista deportiva o piscina cuando se quiere crear una
        if(casillaTieneHotel && tipoEdificio.equals("hotel")){
            System.out.println("No se puede edificar un hotel ya que ya existe un hotel en la casilla " + casillaActual.getNombre());
            return;
        }
        //Comprobar si acasa ya existe un hotel, pista deportiva o piscina cuando se quiere crear una
        if(casillaTieneHotel && tipoEdificio.equals("pista deportiva")){
            System.out.println("No se puede edificar una pista deportiva ya que ya existe un hotel en la casilla " + casillaActual.getNombre());
            return;
        }
        if(casillaTienePiscina && tipoEdificio.equals("piscina")){
            System.out.println("No se puede edificar una piscina ya que ya existe una en la casilla " + casillaActual.getNombre());
            return;
        }
        if(casillaTienePistaDeportiva && tipoEdificio.equals("pista deportiva")){
            System.out.println("No se puede edificar una pista deportiva ya que ya existe una en la casilla " + casillaActual.getNombre());
            return;
        }
        // Comprobar que la casilla dispone de un hotel para crear una piscina
        if(!casillaTieneHotel && tipoEdificio.equals("piscina")){
            System.out.println("No se puede edificar una piscina ya que no se dispone de un hotel.");
            return;
        }
        // Comprobar si acaso se quiere construir un hotel que el jugador tiene 4 casas en ese hotel
        if(!(tipoEdificio.equals("hotel") && numeroCasas==4)){
            System.out.println("No se disponen de las cuatro casa necesarias para contruir el hotel");
            return;
        }
        //Comprobar que el jugador tiene suficiente dinero
        if (jugadorActual.getFortuna() < costeEdificio) {
            System.out.println("La fortuna de "+ jugadorActual + " no es suficinete para edificar un" + tipoEdificio + " en la casilla " + casillaActual.getNombre());
            return;
        }
        //Hacer el pago por la contruccion y realizar la edificacion
        jugadorActual.sumarFortuna(-costeEdificio);
        jugadorActual.sumarGastos(costeEdificio);
        Edificacion nuevaEdificacion = new Edificacion(casillaActual,tipoEdificio);
        //Si vamos a crear un hotel y hemos comprobado que existen 4 casas tenemos que eliminar las casa
        if(tipoEdificio.equals("hotel")){
            casillaActual.getEdificios().removeIf(edificio -> edificio.getTipo().equals("casa"));
            System.out.println("Se han eliminado las casas para poder construir el hotel");
        }
        casillaActual.setEdificios(nuevaEdificacion);
        System.out.println("Se ha edificado un " + tipoEdificio + " en " + casillaActual.getNombre() + ".La fortuna de " + jugadorActual + "se reduce en " + costeEdificio + "€");
    }
    
    //Función que realiza las acciones asociadas a las cartas de suerte
    private void cartaSuerte(int numCartaSuerte){
        Jugador jugadorActual = jugadores.get(turno);
        switch(numCartaSuerte){
            case 1:
                System.out.println("Decides hacer un viaje de placer. Avanza hasta Solar19. \nSi pasas por la casilla de Salida, cobra 2.000.000€.");
                int posDestino = tablero.encontrar_casilla("Solar19").getPosicion();
                int posActual = jugadorActual.getAvatar().getLugar().getPosicion();
                int desplazamiento = posDestino - posActual;
                if (desplazamiento < 0) {
                    desplazamiento += 40; // Ajustar si es negativo
                }
                jugadorActual.getAvatar().moverAvatar(tablero, desplazamiento); //Mover al jugador a la casilla Solar19
                break;
            case 2:
                System.out.println("Los acreedores te persiguen por impago. Ve a la Cárcel.\nVe directamente sin pasar por la casilla de Salida y sin cobrar los 2.000.000€.");
                jugadorActual.encarcelar(tablero); //Encarcelar al jugador
                break;
            case 3:
                System.out.println("¡Has ganado el bote de la lotería! Recibe 1.000.000€.");
                jugadorActual.sumarFortuna(1000000f); //Sumar dinero a la fortuna del jugador
                jugadorActual.sumarDineroPremios(1000000f); //Sumar dinero a los premios/inversiones/bote del jugador
                break;
            case 4:
                System.out.println("Has sido elegido presidente de la junta directiva. Paga a cada jugador 250.000€.");
                float totalAPagar = 250000f * (jugadores.size() - 1); //Calcular el total a pagar
                jugadorActual.sumarFortuna(-totalAPagar); //Restar el total a pagar de la fortuna del jugador actual
                jugadorActual.sumarGastos(totalAPagar); //Sumar el total a pagar a los gastos del jugador actual
                for (Jugador j : jugadores) { //Pagar a cada jugador
                    if (!j.equals(jugadorActual)) { 
                    }
                }
                break;
            case 5:
                System.out.println("¡Hora punta de tráfico! Retrocede tres casillas.");
                int nuevaPosicion = jugadorActual.getAvatar().getLugar().getPosicion() - 3;
                if (nuevaPosicion < 0) {
                    nuevaPosicion += 40; // Ajustar si es negativo
                }
                jugadorActual.getAvatar().moverAvatar(tablero, nuevaPosicion); //Retroceder tres casillas
                break;
            case 6:
                System.out.println("Te multan por usar el móvil mientras conduces. Paga 150.000€.");
                jugadorActual.sumarFortuna(-150000f); //Restar dinero de la fortuna del jugador
                jugadorActual.sumarGastos(150000f); //Sumar dinero a los gastos del jugador
                break;
            case 7:
                System.out.println("Avanza hasta la casilla de transporte más cercana.\nSi no tiene dueño, puedes comprarla.\nSi tiene dueño, paga al dueño el doble de la operación indicada.");
                int posicion = jugadorActual.getAvatar().getLugar().getPosicion();
                int posicionTrans1 = tablero.encontrar_casilla("Transporte1").getPosicion();
                int posicionTrans2 = tablero.encontrar_casilla("Transporte2").getPosicion();
                int posicionTrans3 = tablero.encontrar_casilla("Transporte3").getPosicion();
                int posicionTrans4 = tablero.encontrar_casilla("Transporte4").getPosicion();
                
                if (posicion < posicionTrans1 || posicion >= posicionTrans4) {
                    jugadorActual.getAvatar().moverAvatar(tablero, posicionTrans1);
                } else if (posicion < posicionTrans2) {
                    jugadorActual.getAvatar().moverAvatar(tablero, posicionTrans2);
                } else if (posicion < posicionTrans3) {
                    jugadorActual.getAvatar().moverAvatar(tablero, posicionTrans3);
                } else {
                    jugadorActual.getAvatar().moverAvatar(tablero, posicionTrans4);
                }
                if (jugadorActual.getAvatar().getLugar().evaluarCasilla(jugadorActual, banca, 0, tablero)) {

                    System.out.println("El jugador " + jugadorActual.getNombre() + " ha llegado a la casilla " + 
                    jugadorActual.getAvatar().getLugar().getNombre() + " y ha cumplido con sus obligaciones.");
                    float pagoRealizado = jugadorActual.getAvatar().getLugar().getImpuesto();
                    System.out.println("Por sacar la carta 7 de Suerte, el jugador paga otra vez " + pagoRealizado + "€ al dueño de la casilla.");
                    jugadorActual.sumarFortuna(-pagoRealizado);
                    jugadorActual.sumarGastos(pagoRealizado);

                }
                
                    

            default:
                // Es una buena práctica tener un 'default' por si numCarta tiene un valor inesperado
                System.out.println("Error: Número de carta no válido.");
                break;
        }
        numCartaSuerte++;
        if(numCartaSuerte > 7){
            numCartaSuerte = 1;
        }
    }

    private void cartaCajaComunidad(int numCartaCajaComunidad){
        Jugador jugadorActual = jugadores.get(turno);
        switch (numCartaCajaComunidad) {
            case 1:
                System.out.println("Paga 500.000€ por un fin de semana en un balneario de 5 estrellas.");
                jugadorActual.sumarFortuna(-500000f);
                jugadorActual.sumarGastos(500000f);
                break;
            case 2:
                System.out.println("Te investigan por fraude de identidad. Ve a la Cárcel.\nVe directamente sin pasar por la casilla de Salida y sin cobrar los 2.000.000€.");
                jugadorActual.encarcelar(tablero);
                break;
            case 3:
                System.out.println("Colócate en la casilla de Salida. Cobra 2.000.000€.");
                jugadorActual.getAvatar().moverAvatar(tablero, 1);
                break;
            case 4:
                System.out.println("Devolución de Hacienda. Cobra 500.000€.");
                jugadorActual.sumarFortuna(500000f);
                jugadorActual.sumarDineroPremios(500000f);
                break;
            case 5:
                System.out.println("Retrocede hasta Solar1 para comprar antigüedades exóticas.");
                int posicionSolar1 = tablero.encontrar_casilla("Solar1").getPosicion();
                int posicionActual = jugadorActual.getAvatar().getLugar().getPosicion();
                int desplazamiento = posicionSolar1 - posicionActual;
                jugadorActual.getAvatar().moverAvatar(tablero, desplazamiento);
                break;
            case 6:
                System.out.println("Ve a Solar20 para disfrutar del San Fermín.\nSi pasas por la casilla de Salida, cobra 2.000.000€.");
                break;
            default:
                System.out.println("Error: Número de carta no válido.");
                break;
        }
        numCartaCajaComunidad++;
        if(numCartaCajaComunidad > 6){
            numCartaCajaComunidad = 1;
        }
    }

    // Helper: tras un movimiento efectuado por una carta, evaluar la casilla destino
    // y ejecutar (si procede) otra carta. Devuelve true si el jugador sigue siendo solvente.
    private boolean evaluarTrasMovimientoPorCarta(Jugador jugadorActual, int tirada) {
        Casilla destino = jugadorActual.getAvatar().getLugar();
        boolean solvente = destino.evaluarCasilla(jugadorActual, banca, tirada, this.tablero);
        if (!solvente) {
            System.out.println(jugadorActual.getNombre() + " no puede cumplir con sus obligaciones en " + destino.getNombre());
            return false;
        }
        if (destino.getTipo() != null) {
            if (destino.getTipo().equals("Suerte")) {
                this.cartaSuerte(this.numCartaSuerte);
            } else if (destino.getTipo().equals("Comunidad")) {
                this.cartaCajaComunidad(this.numCartaCajaCom);
            }
        }
        return true;
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
                System.out.println("La cantidad a vender debe ser un número positivo.");
                return;
            }
        } catch (Exception e) {
            System.out.println("La cantidad debe ser un número entero válido.");
            return;
        }

        Casilla casilla = tablero.encontrar_casilla(nombreCasilla);
        if (casilla == null) {
            System.out.println("No se ha encontrado la casilla '" + nombreCasilla + "'.");
            return;
        }

        if (!casilla.getDuenho().equals(jugadorActual)) {
            System.out.println("No eres el dueño de la casilla '" + nombreCasilla + "'.");
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
            System.out.println("No existen edificios de tipo '" + tipoEdificio + "' en la casilla '" + nombreCasilla + "'.");
            return;
        }

        if (cantidad > edificiosAVender.size()) {
            System.out.println("No tienes " + cantidad + " edificios de tipo '" + tipoEdificio + "' para vender en '" + nombreCasilla + "'. Solo tienes " + edificiosAVender.size() + ".");
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

        System.out.println(jugadorActual.getNombre() + " ha vendido " + cantidad + " " + tipoEdificio + "(s) en " + nombreCasilla + " por " + totalVenta + "€.");
        System.out.println("Tu fortuna actual es de " + jugadorActual.getFortuna() + "€.");

        // Lógica especial para hoteles: si se vende un hotel, se recuperan 4 casas.
        if (tipoEdificio.equalsIgnoreCase("hotel")) {
            System.out.println("Al vender el hotel, recuperas 4 casas en " + nombreCasilla + ".");
            for (int i = 0; i < 4; i++) new Edificacion(casilla, "casa");
        }
    }

    public void hipotecar_propiedad(String casilla){
        Casilla c = tablero.encontrar_casilla(casilla);
        Jugador JugadorActual = jugadores.get(turno);

        if(c == null){
            System.out.println("Error: no existe ninguna casilla con el nombre '" + casilla + "'.");
            return;
        }

        //Comprobar que el jugador sea duenho de la casilla
        if(!c.getDuenho().equals(JugadorActual)){
            System.out.println("El jugador " + JugadorActual.getNombre() + "no puede hipotecar " + c.getNombre() +". No es una propiedad que le pertenece.");
            return;
        }
        //Comprobar que la casilla no este ya hipotecada
        if(c.getEstarHipotecada()){
            System.out.println("El jugador " + JugadorActual.getNombre() + " no puede hipotecar" + c.getNombre() + ". Ya esta hipotecada");  
            return;
        } 
        
        //Si la casilla es solar y tiene edificaciones
        if(c.getTipo().equals("Solar") && !c.getEdificios().isEmpty()){
            System.out.println("El jugador " + JugadorActual.getNombre() + " no puede hipotecar" + c.getNombre() + ". Primero debe vender todos sus edificios.");
        }else if(c.getTipo().equals("Solar") && c.getEdificios().isEmpty()){
            banca.sumarFortuna(-c.getHipoteca());
            JugadorActual.sumarFortuna(c.getHipoteca());
            c.setEstarHipotecada(true);
            System.out.println("El jugador " + JugadorActual.getNombre() + "recibe " + c.getHipoteca() + "€ por hipotecar " + c.getNombre());
            if(c.getGrupo() != null && c.getGrupo().esDuenhoGrupo(JugadorActual)){
                System.out.println("No puede recibir alquileres ni edificar en el grupo " + c.getGrupo().colorToNombreGrupo());
            }else{
                System.out.println("No se puede recibir alquiletes.");
            }
        }else{
            System.out.println("La casilla " + c.getNombre() + "no es de tipo Solar. No se puede hipotecar.");
        }
        return;

    }

    public void deshipotecar(String casilla){
        Casilla c = tablero.encontrar_casilla(casilla);
        Jugador JugadorActual = jugadores.get(turno);

        if(c == null){
            System.out.println("Error: no existe ninguna casilla con el nombre '" + casilla + "'.");
            return;
        }

        if(!c.getTipo().equals("Solar")){
            System.out.println("La casilla " + c.getNombre() + "no es de tipo Solar. No se puede deshipotecar.");
            return;
        }   
        //Comprobar que el jugador sea duenho de la casilla
        if(!c.getDuenho().equals(JugadorActual)){
            System.out.println("El jugador " + JugadorActual.getNombre() + "no puede deshipotecar " + c.getNombre() +". No es una propiedad que le pertenece.");
            return;
        }
        //Comprobar que la casilla no este ya hipotecada
        if(!c.getEstarHipotecada()){
            System.out.println("El jugador " + JugadorActual.getNombre() + " no puede deshipotecar" + c.getNombre() + ". No esta hipotecada");  
        } 

        if(JugadorActual.getFortuna() < c.getHipoteca()){
            System.out.println("El jugador " + JugadorActual.getNombre() + " no tiene suficiente dinero para deshipotecar " + c.getNombre() +". Se necesitan " + c.getHipoteca() + "€.");
            return;
        }
        
        JugadorActual.sumarFortuna(-c.getHipoteca());
        JugadorActual.sumarGastos(c.getHipoteca());
        banca.sumarFortuna(c.getHipoteca());
        c.setEstarHipotecada(false);

        System.out.println("El jugador " + JugadorActual.getNombre() + "paga " + c.getHipoteca() + "€ por deshipotecar " + c.getNombre() + ".");
        if(c.getGrupo() != null && c.getGrupo().esDuenhoGrupo(JugadorActual)){
            System.out.println("Ahora puede recibir alquileres y edificar en el grupo " + c.getGrupo().colorToNombreGrupo() + ".");
        }else{
            System.out.println("Ahora se puede recibir alquiletes.");
        }
        return;
    }
    

    public void mostrarEstadisticas(Jugador jugador){
        System.out.println("{");
        System.out.println("dineroInvertido: " + jugador.getDineroInvertido());
        System.out.println("pagoTasasEIMpuestos: " + jugador.getDineroTasasImpuestos());
        System.out.println("pagoDeAlquileres: " + jugador.getDineroPagoAlquileres());
        System.out.println("cobroDeAlquileres: " + jugador.getDineroCobroAlquileres());
        System.out.println("pasarPorCasillaDeSalida: " + jugador.getDineroSalida());
        System.out.println("premiosInversionesOBote: " + jugador.getDineroPremios());
        System.out.println("vecesEnLaCarcel: " + jugador.getTiradasCarcel());
        System.out.println("}");

    }
    public void mostrarEstadisticasJuego(){
        System.out.println("{");
        System.out.println("dinero");
    }
}