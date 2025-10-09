package monopoly;

import partida.*;
import java.util.ArrayList;
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
    Casilla c21 = new Casilla("Parking", "Especial", 21, 0, this.banca); // Valor 0 para bote
    Casilla c22 = new Casilla("Solar12", "Solar", 22, 2200000, this.banca, null);
    Casilla c23 = new Casilla("Suerte", "Suerte", 23, this.banca);
    Casilla c24 = new Casilla("Solar13", "Solar", 24, 2200000, this.banca, null);
    Casilla c25 = new Casilla("Solar14", "Solar", 25, 2400000, this.banca, null);
    Casilla c26 = new Casilla("Trans3", "Transporte", 26, 500000, this.banca, null); // 500.000€
    Casilla c27 = new Casilla("Solar15", "Solar", 27, 2600000, this.banca, null);
    Casilla c28 = new Casilla("Solar16", "Solar", 28, 2600000, this.banca, null);
    Casilla c29 = new Casilla("Serv2", "Servicios", 29, 500000, this.banca, null); // 500.000€
    Casilla c30 = new Casilla("Solar17", "Solar", 30, 2800000, this.banca, null);

    // GRUPOS
    Grupo gRojo = new Grupo(c22, c24, c25, "Rojo"); // 3 solares
    Grupo gAmarillo = new Grupo(c27, c28, c30, "Amarillo"); // 3 solares

    // Asignar los grupos a las casillas
    c22.setGrupo(gRojo); c24.setGrupo(gRojo); c25.setGrupo(gRojo);
    c27.setGrupo(gAmarillo); c28.setGrupo(gAmarillo); c30.setGrupo(gAmarillo);
    
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

    //Método para insertnullar las casillas del lado sur.
    
// ... dentro de la clase Tablero ...

// Método para insertar las casillas del lado sur. (Índice 0: Posiciones 1-10)
    private void insertarLadoSur() {
        ArrayList<Casilla> sur = this.posiciones.get(0);
        
        // CASILLAS (Pos 1-10)
        Casilla c1 = new Casilla("Salida", "Especial", 1, this.banca);
        Casilla c2 = new Casilla("Solar1", "Solar", 2, 600000, this.banca);
        Casilla c3 = new Casilla("Caja de Comunidad", "Comunidad", 3, this.banca);
        Casilla c4 = new Casilla("Solar2", "Solar", 4, 600000, this.banca);
        Casilla c5 = new Casilla("Imp1", 5, 2000000, this.banca); // Impuesto: 2.000.000€
        Casilla c6 = new Casilla("Trans1", "Transporte", 6, 500000, this.banca); // 500.000€
        Casilla c7 = new Casilla("Solar3", "Solar", 7, 1000000, this.banca);
        Casilla c8 = new Casilla("Suerte", "Suerte", 8, this.banca);
        Casilla c9 = new Casilla("Solar4", "Solar", 9, 1000000, this.banca);
        Casilla c10 = new Casilla("Solar5", "Solar", 10, 1200000, this.banca);

        // GRUPOS
        Grupo gMarron = new Grupo(c2, c4, "Marron"); // 2 solares
        Grupo gCian = new Grupo(c7, c9, c10, "Cian"); // 3 solares

        /*
        c2.setGrupo(gMarron);
        c4.setGrupo(gMarron);
        c7.setGrupo(gCian);            
        c9.setGrupo(gCian);
        c10.setGrupo(gCian);
        */
        
        // Añadir al lado Sur
        sur.add(c1); sur.add(c2); sur.add(c3); sur.add(c4); sur.add(c5); 
        sur.add(c6); sur.add(c7); sur.add(c8); sur.add(c9); sur.add(c10);
        
        // Guardar grupos en el HashMap del Tablero
        this.grupos.put("Marron", gMarron);
        this.grupos.put("Cian", gCian);
        
        // Inicializar el grupo de Transporte (solo 1, se completará en otros lados)
        Grupo gTransporte = new Grupo(); // Constructor vacío
        gTransporte.setcolorGrupo("Transporte");
        gTransporte.anhadirCasilla(c6);
        this.grupos.put("Transporte", gTransporte);
    }

    //Método que inserta casillas del lado oeste.
private void insertarLadoEste() {
    ArrayList<Casilla> este = this.posiciones.get(3);
    
    // CASILLAS (Pos 31-40)
    Casilla c31 = new Casilla("IrACarcel", "Especial", 31, this.banca);
    Casilla c32 = new Casilla("Solar18", "Solar", 32, 3000000, this.banca);
    Casilla c33 = new Casilla("Solar19", "Solar", 33, 3000000, this.banca);
    Casilla c34 = new Casilla("Caja de Comunidad", "Comunidad", 34, this.banca);
    Casilla c35 = new Casilla("Solar20", "Solar", 35, 3200000, this.banca);
    Casilla c36 = new Casilla("Trans4", "Transporte", 36, 500000, this.banca); // 500.000€
    Casilla c37 = new Casilla("Suerte", "Suerte", 37, this.banca);
    Casilla c38 = new Casilla("Solar21", "Solar", 38, 3500000, this.banca);
    Casilla c39 = new Casilla("Imp2", 39, 2000000, this.banca); // Impuesto: 2.000.000€
    Casilla c40 = new Casilla("Solar22", "Solar", 40, 4000000, this.banca);

    // GRUPOS
    Grupo gVerde = new Grupo(c32, c33, c35, "Verde"); // 3 solares
    Grupo gAzulOscuro = new Grupo(c38, c40, "Azul Oscuro"); // 2 solares

    // Asignar los grupos a las casillas
    c32.setGrupo(gVerde); c33.setGrupo(gVerde); c35.setGrupo(gVerde);
    c38.setGrupo(gAzulOscuro); c40.setGrupo(gAzulOscuro);
    
    // Añadir al lado Este
    este.add(c31); este.add(c32); este.add(c33); este.add(c34); este.add(c35); 
    este.add(c36); este.add(c37); este.add(c38); este.add(c39); este.add(c40);

    // Guardar grupos en el HashMap del Tablero
    this.grupos.put("Verde", gVerde);
    this.grupos.put("Azul Oscuro", gAzulOscuro);
    
    // Actualizar grupo de Transporte (Completa el grupo de 4)
    this.grupos.get("Transporte").anhadirCasilla(c36);
}

    //Método que inserta las casillas del lado este.
private void insertarLadoOeste() {
    ArrayList<Casilla> oeste = this.posiciones.get(1);
    
    // CASILLAS (Pos 11-20)
    Casilla c11 = new Casilla("Carcel", "Especial", 11, this.banca);
    Casilla c12 = new Casilla("Solar6", "Solar", 12, 1400000, this.banca, null);
    Casilla c13 = new Casilla("Serv1", "Servicios", 13, 500000, this.banca, null); // 500.000€
    Casilla c14 = new Casilla("Solar7", "Solar", 14, 1400000, this.banca, null);
    Casilla c15 = new Casilla("Solar8", "Solar", 15, 1600000, this.banca, null);
    Casilla c16 = new Casilla("Trans2", "Transporte", 16, 500000, this.banca, null); // 500.000€
    Casilla c17 = new Casilla("Solar9", "Solar", 17, 1800000, this.banca, null);
    Casilla c18 = new Casilla("Caja de Comunidad", "Comunidad", 18, this.banca);
    Casilla c19 = new Casilla("Solar10", "Solar", 19, 1800000, this.banca, null);
    Casilla c20 = new Casilla("Solar11", "Solar", 20, 2200000, this.banca, null);

    // GRUPOS
    Grupo gMagenta = new Grupo(c12, c14, c15, "Magenta"); // 3 solares
    Grupo gNaranja = new Grupo(c17, c19, c20, "Naranja"); // 3 solares

    // Asignar los grupos a las casillas
    c12.setGrupo(gMagenta); c14.setGrupo(gMagenta); c15.setGrupo(gMagenta);
    c17.setGrupo(gNaranja); c19.setGrupo(gNaranja); c20.setGrupo(gNaranja);
    
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
    gServicios.setcolorGrupo("Servicios");
    gServicios.anhadirCasilla(c13);
    this.grupos.put("Servicios", gServicios);
}

    //Para imprimir el tablero, modificamos el método toString().
    @Override
    public String toString() {
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
}
