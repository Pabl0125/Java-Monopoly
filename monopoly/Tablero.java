package monopoly;

import partida.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;


public class Tablero {
    //Atributos.
    private ArrayList<ArrayList<Casilla>> posiciones; //Posiciones del tablero: se define como un arraylist de arraylists de casillas (uno por cada lado del tablero).
    private HashMap<String, Grupo> grupos; //Grupos del tablero, almacenados como un HashMap con clave String (será el color del grupo).
    private Jugador banca; //Un jugador que será la banca.
    
    //Constructor: únicamente le pasamos el jugador banca (que se creará desde el menú).
    public Tablero(Jugador banca) {
        this.banca = banca;
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
        Casilla c21 = new Casilla("Parking", "Especial", 21, 0, this.banca, 0); // Valor 0 para bote
        Casilla c22 = new Casilla("Solar12", "Solar", 22, 2200000, this.banca, 180000, 1100000, false);
        Casilla c23 = new Casilla("Suerte", "Suerte", 23, this.banca);
        Casilla c24 = new Casilla("Solar13", "Solar", 24, 2200000, this.banca, 180000, 1100000, false);
        Casilla c25 = new Casilla("Solar14", "Solar", 25, 2400000, this.banca, 200000, 1200000, false);
        Casilla c26 = new Casilla("Trans3", "Transporte", 26, 500000, this.banca, 0); // 500.000€
        Casilla c27 = new Casilla("Solar15", "Solar", 27, 2600000, this.banca, 220000, 1300000, false);
        Casilla c28 = new Casilla("Solar16", "Solar", 28, 2600000, this.banca, 220000, 1300000, false);
        Casilla c29 = new Casilla("Serv2", "Servicios", 29, 500000, this.banca, 0); // 500.000€
        Casilla c30 = new Casilla("Solar17", "Solar", 30, 2800000, this.banca, 240000, 1400000, false);
 
        // GRUPOS
        Grupo gRojo = new Grupo(c22, c24, c25, Valor.RED); // 3 solares
        Grupo gAmarillo = new Grupo(c27, c28, c30, Valor.YELLOW); // 3 solares
        
        // Añadir al lado Norte
        norte.add(c21); norte.add(c22); norte.add(c23); norte.add(c24); norte.add(c25); 
        norte.add(c26); norte.add(c27); norte.add(c28); norte.add(c29); norte.add(c30);

        // Guardar grupos en el HashMap del Tablero
        this.grupos.put("Rojo", gRojo);
        this.grupos.put("Amarillo", gAmarillo);
        
        // Actualizar grupos especiales
        this.grupos.get("Transporte").anhadirCasilla(c26);
        this.grupos.get("Servicios").anhadirCasilla(c29);
    }
        

    // Método para insertar las casillas del lado sur. (Índice 0: Posiciones 1-10)
    private void insertarLadoSur() {
            ArrayList<Casilla> sur = this.posiciones.get(0);
            
            // CASILLAS (Pos 1-10)
            Casilla c1 = new Casilla("Salida", "Especial", 1, this.banca);
            Casilla c2 = new Casilla("Solar1", "Solar", 2, 600000, this.banca, 20000, 300000, false);
            Casilla c3 = new Casilla("Caja", "Comunidad", 3, this.banca);
            Casilla c4 = new Casilla("Solar2", "Solar", 4, 600000, this.banca, 40000, 300000, false);
            Casilla c5 = new Casilla("Imp1", 5, 2000000, this.banca); // Impuesto: 2.000.000€
            Casilla c6 = new Casilla("Trans1", "Transporte", 6, 500000, this.banca, 0); // 500.000€
            Casilla c7 = new Casilla("Solar3", "Solar", 7, 1000000, this.banca, 60000, 500000, false);
            Casilla c8 = new Casilla("Suerte", "Suerte", 8, this.banca);
            Casilla c9 = new Casilla("Solar4", "Solar", 9, 1000000, this.banca, 60000, 500000, false);
            Casilla c10 = new Casilla("Solar5", "Solar", 10, 1200000, this.banca, 80000, 600000, false);            

            // GRUPOS
            Grupo gMarron = new Grupo(c2, c4, Valor.MARRON); // 2 solares
            Grupo gCian = new Grupo(c7, c9, c10, Valor.CYAN); // 3 solares

            // Añadir al lado Sur
            sur.add(c1); sur.add(c2); sur.add(c3); sur.add(c4); sur.add(c5); 
            sur.add(c6); sur.add(c7); sur.add(c8); sur.add(c9); sur.add(c10);
            
            // Guardar grupos en el HashMap del Tablero
            this.grupos.put("Marron", gMarron);
            this.grupos.put("Cian", gCian);
            
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
        Casilla c31 = new Casilla("IrACarcel", "Especial", 31, this.banca);
        Casilla c32 = new Casilla("Solar18", "Solar", 32, 3000000, this.banca, 260000, 1500000, false);
        Casilla c33 = new Casilla("Solar19", "Solar", 33, 3000000, this.banca, 260000, 1500000, false);
        Casilla c34 = new Casilla("Caja", "Comunidad", 34, this.banca);
        Casilla c35 = new Casilla("Solar20", "Solar", 35, 3200000, this.banca, 280000, 1600000, false);
        Casilla c36 = new Casilla("Trans4", "Transporte", 36, 500000, this.banca, 0); // 500.000€
        Casilla c37 = new Casilla("Suerte", "Suerte", 37, this.banca);
        Casilla c38 = new Casilla("Solar21", "Solar", 38, 3500000, this.banca, 350000, 1750000, false);
        Casilla c39 = new Casilla("Imp2", 39, 2000000, this.banca); // Impuesto: 2.000.000€
        Casilla c40 = new Casilla("Solar22", "Solar", 40, 4000000, this.banca, 500000, 2000000, false);

        // GRUPOS
        Grupo gVerde = new Grupo(c32, c33, c35, Valor.GREEN); // 3 solares
        Grupo gAzulOscuro = new Grupo(c38, c40, Valor.BLUE); // 2 solares
        
        // Añadir al lado Este
        este.add(c31); este.add(c32); este.add(c33); este.add(c34); este.add(c35); 
        este.add(c36); este.add(c37); este.add(c38); este.add(c39); este.add(c40);

        // Guardar grupos en el HashMap del Tablero
        this.grupos.put("Verde", gVerde);
        this.grupos.put("Azul", gAzulOscuro);
        
        // Actualizar grupo de Transporte (Completa el grupo de 4)
        this.grupos.get("Transporte").anhadirCasilla(c36);
    }

    //Método que inserta las casillas del lado oeste.
    private void insertarLadoOeste() {
        ArrayList<Casilla> oeste = this.posiciones.get(1);
        
        // CASILLAS (Pos 11-20)
        Casilla c11 = new Casilla("Carcel", "Especial", 11, this.banca);
        Casilla c12 = new Casilla("Solar6", "Solar", 12, 1400000, this.banca, 100000, 700000, false);
        Casilla c13 = new Casilla("Serv1", "Servicios", 13, 500000, this.banca, 0); // 500.000€
        Casilla c14 = new Casilla("Solar7", "Solar", 14, 1400000, this.banca, 100000, 700000, false);
        Casilla c15 = new Casilla("Solar8", "Solar", 15, 1600000, this.banca, 120000, 800000, false);
        Casilla c16 = new Casilla("Trans2", "Transporte", 16, 500000, this.banca, 0); // 500.000€
        Casilla c17 = new Casilla("Solar9", "Solar", 17, 1800000, this.banca, 140000, 900000, false);
        Casilla c18 = new Casilla("Caja", "Comunidad", 18, this.banca);
        Casilla c19 = new Casilla("Solar10", "Solar", 19, 1800000, this.banca, 140000, 900000, false);
        Casilla c20 = new Casilla("Solar11", "Solar", 20, 2000000, this.banca, 160000, 1000000, false);

        // GRUPOS
        Grupo gMagenta = new Grupo(c12, c14, c15, Valor.PURPLE); // 3 solares
        Grupo gNaranja = new Grupo(c17, c19, c20, Valor.NARANJA); // 3 solares
        
        // Añadir al lado Oeste
        oeste.add(c11); oeste.add(c12); oeste.add(c13); oeste.add(c14); oeste.add(c15); 
        oeste.add(c16); oeste.add(c17); oeste.add(c18); oeste.add(c19); oeste.add(c20);

        // Guardar grupos en el HashMap del Tablero
        this.grupos.put("Magenta", gMagenta);
        this.grupos.put("Naranja", gNaranja);
        
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

        // 3. AHORA, añadir el color (si existe) al texto BASE formateado
        Grupo g = c.getGrupo();
        if(g != null && g.getcolorGrupo() != null){
            // Concatenamos las secuencias ANSI al String que ya tiene 10 caracteres
            return g.getcolorGrupo() + texto_base + Valor.RESET;
        } else {
            return texto_base;
        }
    }
    
    //Metodo para aumentar el bote del parking
    public void aumentarBoteParking(float valor){
        Casilla parking = this.encontrar_casilla("Parking");
        parking.setValor(parking.getValor() + valor);
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
        if (posicion < 1 || posicion > 40) return null;

        // Calculamos el lado y el índice dentro de ese lado
        int lado = (posicion - 1) / 10;
        int indiceEnLado = (posicion - 1) % 10;

        // Devolvemos la casilla directamente
        return this.posiciones.get(lado).get(indiceEnLado);
    }

    //Metodo para buscar la casilla más rentable del juego
    public ArrayList<String> buscarCasillaMasRentable(){
        ArrayList <String> masRentables = new ArrayList<String>();
        float maxRentabilidad = 0.0f;

        for (ArrayList<Casilla> lado : this.posiciones) {
            for (Casilla c : lado) {
                if (c.getRentabilidad() > maxRentabilidad) {
                    maxRentabilidad = c.getRentabilidad();
                    masRentables.clear();
                    masRentables.add(c.getNombre());
                }else if(c.getRentabilidad() == maxRentabilidad){
                    masRentables.add(c.getNombre());
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
        ArrayList <String> masRentables = new ArrayList<String>();
        float maxRentabilidad = 0.0f;

        Iterator<HashMap.Entry<String, Grupo>> iterator = this.grupos.entrySet().iterator();
        while (iterator.hasNext()) {
            HashMap.Entry<String, Grupo> entry = iterator.next();
            Grupo grupo = entry.getValue();
            float rentabilidadGrupo = grupo.getRentabilidad();

            if (rentabilidadGrupo > maxRentabilidad) {
                maxRentabilidad = rentabilidadGrupo;
                masRentables.clear();
                masRentables.add(entry.getKey());
            } else if (rentabilidadGrupo == maxRentabilidad) {
                masRentables.add(entry.getKey());
            }
        }

        return masRentables;
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