package monopoly;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
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


    // Método para inciar una partida: crea los jugadores y avatares.
    public void iniciarPartida() {
        //Crear jugadores y avatares.
        jugadores = new ArrayList<Jugador>();
        avatares = new ArrayList<Avatar>();
        banca = new Jugador();
        jugadores.add(banca);
        tablero = new Tablero(banca);
    }

    
    public void lecturaFichero(String fichero){
        File file = new File(fichero);
        try{
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String linea = sc.nextLine();
                analizarComando(linea);
                try{
                    Thread.sleep(1000);
                }
                catch(Exception e){
                    System.out.println("Error en el sleep");
                }
            }
            sc.close();
        } catch (FileNotFoundException e){
            System.out.println("Error: Fichero no encontrado.");
            return;
        }
    }
    
    /**
    * Método que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetro: cadena de caracteres (el comando).
    */
    private void analizarComando(String comando) {
        String[] partesComando = comando.split(" ");
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
                    if(partesComando.length ==2 && partesComando[1].equals("enventa")){
                        this.listarVenta();
                    }
                    else System.err.println("Invalid command");    
                    break;
                    
                case "lanzar":  // NOTA -> Por ahora el comando no acepta operaciones aritmeticas del tipo "2+4"
                    if (partesComando.length == 1) { 
                            lanzarDados();
                        }
                    if(partesComando.length ==2){
                        lanzarDados(partesComando[1]);
                    }
                    else System.err.println("Invalid command");  
                    break;
                case "acabar":
                    if(partesComando.length ==2 && partesComando[1].equals("turno")){
                        this.acabarTurno();
                    }
                    break;
                case "salir":
                    if(partesComando.length ==2 && partesComando[1].equals("carcel")){
                        this.salirCarcel();
                    }
                    break;
                case "describir":
                    if(partesComando.length == 2 && partesComando[1].equals("jugador") && this.encontrar_jugador(partesComando[2])){
                        this.descJugador(partesComando[2]);
                    }if (partesComando.length == 2 && partesComando[1].equals("casilla") && ((tablero.encontrar_casilla(partesComando[2]) instanceof Casilla))) {
                        this.descCasilla(partesComando[2]);
                    }
                    break;
                case "comprar":
                    if(partesComando.length ==2 && ((tablero.encontrar_casilla(partesComando[1]) instanceof Casilla))){
                        comprar(partesComando[1]);
                    }
                    break;
                case "ver":
                    if(partesComando.length == 2 && partesComando[1].equals("tablero")){
                        tablero.imprimirTablero();
                    }
                default:
                    // Se ejecuta si el comando no coincide con ningún case
                    System.out.println("Error: Comando desconocido '" + comandoPrincipal + "'.");
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
        avatares.add(nuevoJugador.getAvatar());

        System.out.println("Jugador '" + nombre + "' creado correctamente.");
        System.out.println("Avatar: " + nuevoJugador.getAvatar());

        //REPINTAR EL TABLERO
        
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
    do {
        // Extraer los valores de la jugada forzada
        String[] partes = tirada.split("\\+");
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
        if (valor1 == valor2) {
            doblesSeguidos++;
            if (doblesSeguidos == 3) {
                avatarActual.setLugar(tablero.encontrar_casilla("Carcel"));
                avatarActual.getJugador().encarcelar(tablero.getPosiciones());
                System.out.println("Tercer doble consecutivo: " + jugadorActual.getNombre() + " va directamente a la cárcel.");
                break;
            }
            volverATirar = true;
            System.out.println("¡Doble forzado! " + jugadorActual.getNombre() + " puede volver a tirar después de evaluar la casilla.");
        } else {
            volverATirar = false;
            doblesSeguidos = 0;
        }

        // Mover avatar y mostrar movimiento
        Casilla casillaInicial = avatarActual.getLugar();
        avatarActual.moverAvatar(tablero.getPosiciones(),valor1+valor2);
        Casilla casillaFinal = avatarActual.getLugar();
        

        System.out.println("El avatar " + avatarActual.getId() +
                " avanza desde " + casillaInicial.getNombre() +
                " hasta " + casillaFinal.getNombre() + ".");

        // Evaluar la casilla
        boolean solvente = casillaFinal.evaluarCasilla(jugadorActual, banca, total);
        if (!solvente) {
            System.out.println(jugadorActual.getNombre() + " no puede cumplir con sus obligaciones en " + casillaFinal.getNombre());
        }

        // Si sale doble, se permite una nueva tirada forzada
        if (volverATirar) {
            System.out.print("Introduce una nueva tirada forzada (formato x+y): ");
            Scanner sc = new Scanner(System.in);
            tirada = sc.nextLine();
            sc.close();
        }

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
        // Mover avatar y mostrar movimiento
        Casilla casillaInicial = avatarActual.getLugar();
        avatarActual.moverAvatar(tablero.getPosiciones(),valor1+valor2);
        Casilla casillaFinal = avatarActual.getLugar();

        System.out.println("El avatar " + avatarActual.getId() +
                " avanza desde " + casillaInicial.getNombre() +
                " hasta " + casillaFinal.getNombre() + ".");

        // Evaluar la casilla usando su propio método
        boolean solvente = casillaFinal.evaluarCasilla(jugadorActual, banca, total);
        if (!solvente) {
            System.out.println(jugadorActual.getNombre() + " no puede cumplir con sus obligaciones en " + casillaFinal.getNombre());
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
        //Obtengo su tipo y si se puede comprar o no
        String tipo = c.getTipo();
        if (!tipo.equals("Solar") && !tipo.equals("Transporte") && !tipo.equals("Servicios")) {
            System.out.println("La casilla '" + nombre + "' no se puede comprar.");
            return;
        }

        c.comprarCasilla(jugadores.get(turno), banca);

        Jugador jugadorActual = jugadores.get(turno);
        if (!jugadorActual.getPropiedades().contains(c) && !c.getDuenho().equals(banca)) {
            jugadorActual.anhadirPropiedad(c);
        }
    }


    //Método que ejecuta todas las acciones relacionadas con el comando 'salir carcel'. 
    private void salirCarcel() {
        
        
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
            System.out.println(jugador); //Se omite el toString() ya que se llama implícitamente.  
        }
    }

    //Devuelve true si el jugador buscado por string de caracteres existe en la lista de jugadores
    private boolean encontrar_jugador(String jugadorBuscado) {
        if(jugadores == null || jugadores.isEmpty()){
            return false;
        }
        for (Jugador jugador : jugadores) {
            System.out.println(jugador); //Se omite el toString() ya que se llama implícitamente.  
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

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
        if(jugadores == null || jugadores.isEmpty()){
            System.out.println("No hay jugadores en la partida.");
            return;
        }
        if(turno == jugadores.size() - 1) {
            turno = 0;
        } else {
            turno++;
        }

        System.out.println("El jugador actual es " + jugadores.get(turno).getNombre() + ".");
    }

    private void imprimirJugadorTurno() {
        System.out.println("Nombre:" + this.jugadores.get(turno).getNombre() + "\nAvatar: " + this.jugadores.get(turno).getAvatar().getId());
    }
}
