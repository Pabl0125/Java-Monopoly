package monopoly;
import partida.*;
import monopoly.casillas.*;
import java.util.ArrayList;
import java.util.Iterator;


import java.util.HashMap;


public class Tablero {
    //Atributos.
    private ArrayList<ArrayList<Casilla>> posiciones; //Posiciones del tablero: se define como un arraylist de arraylists de casillas (uno por cada lado del tablero).
    private HashMap<String, Grupo> grupos; //Grupos del tablero, almacenados como un HashMap con clave String (será el color del grupo).
    private Jugador banca; //Un jugador que será la banca.
    private final Juego juego;
    
    //Constructor: únicamente le pasamos el jugador banca (que se creará desde el menú).
    public Tablero(Jugador banca, Juego juego) {
        this.banca = banca;
        this.juego = juego;
        this.posiciones = new ArrayList<ArrayList<Casilla>>(); //Inicializamos el array de posiciones.
        this.grupos = new HashMap<String, Grupo>(); //Inicializamos el hashmap de grupos.
        this.posiciones.add(new ArrayList<Casilla>()); // Índice 0: Lado Sur
        this.posiciones.add(new ArrayList<Casilla>()); // Índice 1: Lado Oeste
        this.posiciones.add(new ArrayList<Casilla>()); // Índice 2: Lado Norte
        this.posiciones.add(new ArrayList<Casilla>()); // Índice 3: Lado Este
        this.generarCasillas(); //Generamos las casillas del tablero.
    }

    
    //Método para crear todas las casillas del tablero. Formado a su vez por cuatro métodos (1/lado).
    private void generarCasillas() {
        this.insertarLadoSur();
        this.insertarLadoOeste();
        this.insertarLadoNorte();
        this.insertarLadoEste();
    }
    
    private void insertarLadoNorte() {
        ArrayList<Casilla> norte = this.posiciones.get(2);
        
        // CASILLAS (Pos 21-30)
        Parking c21 = new Parking("Parking", 20, this.juego);
        Solar c22 = new Solar("Solar12", 21, this.juego);
        Suerte c23 = new Suerte("Suerte", 22, this.juego);
        Solar c24 = new Solar("Solar13", 23, this.juego);
        Solar c25 = new Solar("Solar14", 24, this.juego);
        Transporte c26 = new Transporte("Trans3", 25, this.juego);
        Solar c27 = new Solar("Solar15", 26, this.juego);
        Solar c28 = new Solar("Solar16", 27, this.juego);
        Servicio c29 = new Servicio("Serv2", 28, this.juego);
        Solar c30 = new Solar("Solar17", 29, this.juego);
 
        // GRUPOS
        Grupo gRojo = new Grupo(c22, c24, c25, Valor.RED); // 3 solares
        Grupo gAmarillo = new Grupo(c27, c28, c30, Valor.YELLOW); // 3 solares
        
        // Añadir al lado Norte
        norte.add(c21); norte.add(c22); norte.add(c23); norte.add(c24); norte.add(c25);
        norte.add(c26); norte.add(c27); norte.add(c28); norte.add(c29); norte.add(c30);

        // Guardar grupos en el HashMap del Tablero
        this.grupos.put(Valor.RED, gRojo);
        this.grupos.put(Valor.YELLOW, gAmarillo);
        
        // Actualizar grupos especiales
        this.grupos.get("Transporte").anhadirCasilla(c26);
        this.grupos.get("Servicios").anhadirCasilla(c29);
    }
        

    // Método para insertar las casillas del lado sur. (Índice 0: Posiciones 1-10)
    private void insertarLadoSur() {
        ArrayList<Casilla> sur = this.posiciones.get(0);
        
        // CASILLAS (Pos 1-10)
        Especial c1 = new Especial("Salida", 0, this.juego);
        Solar c2 = new Solar("Solar1", 1, this.juego);
        Casilla c3 = new CajaComunidad("Caja", 2, this.juego);
        Propiedad c4 = new Solar("Solar2", 3, this.juego);
        Casilla c5 = new Impuesto("Imp1", 4, this.juego);
        Transporte c6 = new Transporte("Trans1", 5, this.juego);
        Solar c7 = new Solar("Solar3", 6, this.juego);
        Suerte c8 = new Suerte("Suerte", 7, this.juego);
        Solar c9 = new Solar("Solar4", 8, this.juego);
        Solar c10 = new Solar("Solar5", 9, this.juego);
        
        // GRUPOS
        Grupo gMarron = new Grupo(c2, c4, Valor.MARRON); // 2 solares
        Grupo gCian = new Grupo(c7, c9, c10, Valor.CYAN); // 3 solares
        
        // Añadir al lado Sur
        sur.add(c1); sur.add(c2); sur.add(c3); sur.add(c4); sur.add(c5);
        sur.add(c6); sur.add(c7); sur.add(c8); sur.add(c9); sur.add(c10);
        
        // Guardar grupos en el HashMap del Tablero
        this.grupos.put(Valor.MARRON, gMarron);
        this.grupos.put(Valor.CYAN, gCian);
        
        // Inicializar el grupo de Transporte (solo 1, se completará en otros lados)
        Grupo gTransporte = new Grupo(); // Constructor vacío
        gTransporte.setcolorGrupo(Valor.GRIS);
        gTransporte.anhadirCasilla(c6);
        this.grupos.put("Transporte", gTransporte);
    }

    //Método que inserta casillas del lado este.
    private void insertarLadoEste() {
        ArrayList<Casilla> este = this.posiciones.get(3);
        
        // CASILLAS (Pos 31-40)
        Especial c31 = new Especial("IrACarcel", 30, this.juego);
        Solar c32 = new Solar("Solar18", 31, this.juego);
        Solar c33 = new Solar("Solar19", 32, this.juego);
        CajaComunidad c34 = new CajaComunidad("Caja", 33, this.juego);
        Solar c35 = new Solar("Solar20", 34, this.juego);
        Transporte c36 = new Transporte("Trans4", 35, this.juego);
        Suerte c37 = new Suerte("Suerte", 36, this.juego);
        Solar c38 = new Solar("Solar21", 37, this.juego);
        Impuesto c39 = new Impuesto("Imp2", 38, this.juego);
        Solar c40 = new Solar("Solar22", 39, this.juego);

        // GRUPOS
        Grupo gVerde = new Grupo(c32, c33, c35, Valor.GREEN); // 3 solares
        Grupo gAzulOscuro = new Grupo(c38, c40, Valor.BLUE); // 2 solares
        
        // Añadir al lado Este
        este.add(c31); este.add(c32); este.add(c33); este.add(c34); este.add(c35);
        este.add(c36); este.add(c37); este.add(c38); este.add(c39); este.add(c40);

        // Guardar grupos en el HashMap del Tablero
        this.grupos.put(Valor.GREEN, gVerde);
        this.grupos.put(Valor.BLUE, gAzulOscuro);
        
        // Actualizar grupo de Transporte (Completa el grupo de 4)
        this.grupos.get("Transporte").anhadirCasilla(c36);
    }

    //Método que inserta las casillas del lado oeste.
    private void insertarLadoOeste() {
        ArrayList<Casilla> oeste = this.posiciones.get(1);
        
        // CASILLAS (Pos 11-20)
        Especial c11 = new Especial("Carcel", 10, this.juego);
        Solar c12 = new Solar("Solar6", 11, this.juego);
        Servicio c13 = new Servicio("Serv1", 12, this.juego);
        Solar c14 = new Solar("Solar7", 13, this.juego);
        Solar c15 = new Solar("Solar8", 14, this.juego);
        Transporte c16 = new Transporte("Trans2", 15, this.juego);
        Solar c17 = new Solar("Solar9", 16, this.juego);
        CajaComunidad c18 = new CajaComunidad("Caja", 17, this.juego);
        Solar c19 = new Solar("Solar10", 18, this.juego);
        Solar c20 = new Solar("Solar11", 19, this.juego);

        // GRUPOS
        Grupo gPurpura = new Grupo(c12, c14, c15, Valor.PURPLE); // 3 solares
        Grupo gNaranja = new Grupo(c17, c19, c20, Valor.NARANJA); // 3 solares
        
        // Añadir al lado Oeste
        oeste.add(c11); oeste.add(c12); oeste.add(c13); oeste.add(c14); oeste.add(c15);
        oeste.add(c16); oeste.add(c17); oeste.add(c18); oeste.add(c19); oeste.add(c20);

        // Guardar grupos en el HashMap del Tablero
        this.grupos.put(Valor.PURPLE, gPurpura);
        this.grupos.put(Valor.NARANJA, gNaranja);
        
        // Actualizar grupos especiales
        Grupo gTransporte = this.grupos.get("Transporte");
        gTransporte.anhadirCasilla(c16);

        Grupo gServicios = new Grupo(); // Constructor vacío
        gServicios.setcolorGrupo(Valor.GRIS);
        gServicios.anhadirCasilla(c13);
        this.grupos.put("Servicios", gServicios);
    }

    public void imprimirTablero() {
            String[][] tablero = new String[11][11];
            // Lado Sur 
            ArrayList<Casilla> sur = this.posiciones.get(0);
            for (int i = 0; i < 10; i++) {  //Se insertaron las casillas del lado sur en orden inverso (salida empieza a la izquierda)
                tablero[10][10 - i] = formatearCasilla(sur.get(i));    //Por lo que se deben imprimir en orden inverso.
            }
            // Lado Oeste 
            ArrayList<Casilla> oeste = this.posiciones.get(1);
            for (int i = 0; i < 10; i++) {
                tablero[10 - i][0] = formatearCasilla(oeste.get(i));
            }
            // Lado Norte
            ArrayList<Casilla> norte = this.posiciones.get(2);
            for (int i = 0; i < 10; i++) {
                tablero[0][i] = formatearCasilla(norte.get(i));
            }
            // Lado Este
            ArrayList<Casilla> este = this.posiciones.get(3);
            for (int i = 0; i < 10; i++) {
                tablero[i][10] = formatearCasilla(este.get(i));
            }
            // Rellenar espacios vacíos
            for (int i = 1; i < 10; i++) {
                for (int j = 1; j < 10; j++) {
                    // Ajustar el espacio central al ancho de la casilla + 3 caracteres ([... ])
                    tablero[i][j] = "               ";
                }
            }
            // Imprimir el tablero
            for (int i = 0; i < tablero.length; i++) {
                for (int j = 0; j < tablero.length; j++){
                    if(i == 0 || i == 10 || j == 0 || j == 10){
                        System.out.print("[" + tablero[i][j] + " ]");
                    } else {
                        System.out.print(tablero[i][j]);
                    }
            }
            System.out.println();
        }
    }

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
    
    //Metodo para aumentar el bote del parking
    public void aumentarBoteParking(float valor){
        Parking parking = (Parking) this.encontrar_casilla("Parking");
        parking.sumarBoteParking(valor);
    }

    //Método usado para buscar la casilla con el nombre pasado como argumento:
    public Casilla encontrar_casilla(String nombre){
        for (ArrayList<Casilla> cs:posiciones){
            for (Casilla c:cs){
                if (c.getNombre().equals(nombre)){
                    return c;
                }
            }
        }
        return null;
    }

    //Método para encontrar una casilla por su número de posición (1-40)
    public Casilla encontrar_casilla_por_posicion(int posicion) {
        if (posicion < 0 || posicion > 39) return null;

        // Calculamos el lado y el índice dentro de ese lado
        int lado = posicion / 10;
        int indiceEnLado = posicion % 10;

        // Devolvemos la casilla directamente
        return this.posiciones.get(lado).get(indiceEnLado);
    }

    //Metodo para buscar la casilla más rentable del juego
    public ArrayList<String> buscarCasillaMasRentable(){
        ArrayList<String> masRentables = new ArrayList<>();
        float maxRentabilidad = 0.0f;

        for (ArrayList<Casilla> lado : this.posiciones) {
            for (Casilla c : lado) {
                if (c instanceof Propiedad) {
                    Propiedad p = (Propiedad) c;
                    if (p.getRentabilidad() > maxRentabilidad) {
                        maxRentabilidad = p.getRentabilidad();
                        masRentables.clear();
                        masRentables.add(c.getNombre());
                    } 
                    else if (p.getRentabilidad() == maxRentabilidad) {
                    masRentables.add(c.getNombre());
                    }
                }
            }
        }
        return masRentables;
    }
    
    //Método para buscar la casilla más visitada del juego
    public ArrayList<String> buscarCasillaMasVisitada(){
        ArrayList<String> masVisitadas = new ArrayList<String>();
        int maxVisitas = 0;

        for (ArrayList<Casilla> lado : this.posiciones) {
            for (Casilla c : lado) {
                if (c.getVecesVisitada() > maxVisitas) {
                    maxVisitas = c.getVecesVisitada();
                    masVisitadas.clear();
                    masVisitadas.add(c.getNombre());
                }else if(c.getVecesVisitada() == maxVisitas){
                    masVisitadas.add(c.getNombre());
                }
            }
        }
        return masVisitadas;
    }

    //Método para buscar el grupo más rentable del juego
    public ArrayList<String> buscarGrupoMasRentable(){
        ArrayList<String> masRentables = new ArrayList<>();
        float maxRentabilidad = 0.0f;

        Iterator<HashMap.Entry<String, Grupo>> iterator = this.grupos.entrySet().iterator();
        while (iterator.hasNext()) {
            HashMap.Entry<String, Grupo> entry = iterator.next();
            Grupo grupo = entry.getValue();
            float rentabilidadGrupo = grupo.getRentabilidad();

            if (rentabilidadGrupo > maxRentabilidad) {
                maxRentabilidad = rentabilidadGrupo;
                masRentables.clear();
                masRentables.add(grupo.colorToNombreGrupo());
            } else if (rentabilidadGrupo == maxRentabilidad) {
                masRentables.add(grupo.colorToNombreGrupo());
            }
        }

        return masRentables;
    }
    

    public void moverA(String nombreCasilla, Jugador jugador) {
        Casilla destino = this.encontrar_casilla(nombreCasilla);
        if (destino != null) {
            int posActual = jugador.getAvatar().getLugar().getPosicion();
            int posDestino = destino.getPosicion();
            int desplazamiento = posDestino - posActual;
            jugador.getAvatar().moverAvatar(this, desplazamiento);
        }
    }

    public void pagarImpuesto(Jugador jugador, float cantidad) {
        jugador.sumarFortuna(-cantidad);
        jugador.sumarGastos(cantidad);
        jugador.sumarDineroTasasImpuestos(cantidad);
        this.aumentarBoteParking(cantidad);
    }

    //Getters y setters.
    public ArrayList<ArrayList<Casilla>> getPosiciones() {
        return posiciones;
    }

    public void setPosiciones(ArrayList<ArrayList<Casilla>> posiciones) {
        this.posiciones = posiciones;
    }

    public HashMap<String, Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(HashMap<String, Grupo> grupos) {
        this.grupos = grupos;
    }

    public Jugador getBanca() {
        return banca;
    }

    public void setBanca(Jugador banca) {
        this.banca = banca;
    }

}