package monopoly;
import partida.*;
import monopoly.casillas.*;
import monopoly.excepciones.JuegoException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

public class Tablero {
    //Atributos.
    private ArrayList<ArrayList<Casilla>> casillas;     //Arralist con los arraylist de casillas norte, sur, este y oeste.
    private HashMap<String, Grupo> grupos;              //Grupos del tablero, almacenados como un HashMap con clave String (será el color del grupo).
    private Jugador banca;                              //Un jugador que será la banca.
    private final Juego juego;
    
    //////////////////////////////////CONSTRUCTOR////////////////////////////////////////
    public Tablero(Jugador banca, Juego juego) {
        this.banca = banca;
        this.juego = juego;
        this.casillas = new ArrayList<>();
        this.grupos = new HashMap<>();
        
        generarGrupos();
        generarCasillas();
    }

    ////////////////////GETTERS Y SETTERS//////////////////////////
    public ArrayList<ArrayList<Casilla>> getCasillas() {
        return casillas;
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
    
    private void generarGrupos() {
        grupos.put(Valor.MARRON, new Grupo(Valor.MARRON));
        grupos.put(Valor.CYAN, new Grupo(Valor.CYAN));
        grupos.put(Valor.PURPLE, new Grupo(Valor.PURPLE));
        grupos.put(Valor.NARANJA, new Grupo(Valor.NARANJA));
        grupos.put(Valor.RED, new Grupo(Valor.RED));
        grupos.put(Valor.YELLOW, new Grupo(Valor.YELLOW));
        grupos.put(Valor.GREEN, new Grupo(Valor.GREEN));
        grupos.put(Valor.BLUE, new Grupo(Valor.BLUE));
        grupos.put("Transporte", new Grupo(Valor.GRIS));
        grupos.put("Servicios", new Grupo(Valor.GRIS));
    }

    private void generarCasillas() {
        
        // Lado Sur
        ArrayList<Casilla> ladoSur = new ArrayList<>();
        ladoSur.add(new Especial("Salida", 0));
        Solar s1 = new Solar("Solar1", 1, 600000, 20000, 500000, 500000, 100000, 200000, 400000, 2500000, banca);
        ladoSur.add(s1);
        grupos.get(Valor.MARRON).anhadirCasilla(s1);
        ladoSur.add(new CajaComunidad("Caja", 2));
        Solar s2 = new Solar("Solar2", 3, 600000, 40000, 500000, 500000, 100000, 200000, 800000, 4500000, banca);
        ladoSur.add(s2);
        grupos.get(Valor.MARRON).anhadirCasilla(s2);
        ladoSur.add(new Impuesto("Imp1", 4));
        Transporte t1 = new Transporte("Trans1", 5, 500000, 250000, banca);
        ladoSur.add(t1);
        grupos.get("Transporte").anhadirCasilla(t1);
        Solar s3 = new Solar("Solar3", 6, 1000000, 60000, 500000, 500000, 100000, 200000, 1000000, 5500000, banca);
        ladoSur.add(s3);
        grupos.get(Valor.CYAN).anhadirCasilla(s3);
        ladoSur.add(new Suerte("Suerte", 7, juego.getJugadores()));
        Solar s4 = new Solar("Solar4", 8, 1000000, 60000, 500000, 500000, 100000, 200000, 1000000, 5500000, banca);
        ladoSur.add(s4);
        grupos.get(Valor.CYAN).anhadirCasilla(s4);
        Solar s5 = new Solar("Solar5", 9, 1200000, 80000, 500000, 500000, 100000, 200000, 1250000, 6000000, banca);
        ladoSur.add(s5);
        grupos.get(Valor.CYAN).anhadirCasilla(s5);

        casillas.add(ladoSur);
        
        // Lado Oeste
        ArrayList<Casilla> ladoOeste = new ArrayList<>();
        ladoOeste.add(new Especial("Carcel", 10));
        Solar s6 = new Solar("Solar6", 11, 1400000, 100000, 1000000, 1000000, 200000, 400000, 1500000, 7500000, banca);
        ladoOeste.add(s6);
        grupos.get(Valor.PURPLE).anhadirCasilla(s6);
        Servicio serv1 = new Servicio("Serv1", 12, 1500000, 0, banca);
        ladoOeste.add(serv1);
        grupos.get("Servicios").anhadirCasilla(serv1);
        Solar s7 = new Solar("Solar7", 13, 1400000, 100000, 1000000, 1000000, 200000, 400000, 1500000, 7500000, banca);
        ladoOeste.add(s7);
        grupos.get(Valor.PURPLE).anhadirCasilla(s7);
        Solar s8 = new Solar("Solar8", 14, 1600000, 120000, 1000000, 1000000, 200000, 400000, 1750000, 9000000, banca);
        ladoOeste.add(s8);
        grupos.get(Valor.PURPLE).anhadirCasilla(s8);
        Transporte t2 = new Transporte("Trans2", 15, 500000, 250000, banca);
        ladoOeste.add(t2);
        grupos.get("Transporte").anhadirCasilla(t2);
        Solar s9 = new Solar("Solar9", 16, 1800000, 140000, 1000000, 1000000, 200000, 400000, 1850000, 9500000, banca);
        ladoOeste.add(s9);
        grupos.get(Valor.NARANJA).anhadirCasilla(s9);
        ladoOeste.add(new CajaComunidad("Caja", 17));
        Solar s10 = new Solar("Solar10", 18, 1800000, 140000, 1000000, 1000000, 200000, 400000, 1850000, 9500000, banca);
        ladoOeste.add(s10);
        grupos.get(Valor.NARANJA).anhadirCasilla(s10);
        Solar s11 = new Solar("Solar11", 19, 2000000, 160000, 1000000, 1000000, 200000, 400000, 2000000, 10000000, banca);
        ladoOeste.add(s11);
        grupos.get(Valor.NARANJA).anhadirCasilla(s11);

        casillas.add(ladoOeste);

        // Lado Norte
        ArrayList<Casilla> ladoNorte = new ArrayList<>();
        ladoNorte.add(new Parking("Parking", 20));
        Solar s12 = new Solar("Solar12", 21, 2200000, 180000, 1500000, 1500000, 300000, 600000, 2200000, 10500000, banca);
        ladoNorte.add(s12);
        grupos.get(Valor.RED).anhadirCasilla(s12);
        ladoNorte.add(new Suerte("Suerte", 22, juego.getJugadores()));
        Solar s13 = new Solar("Solar13", 23, 2200000, 180000, 1500000, 1500000, 300000, 600000, 2200000, 10500000, banca);
        ladoNorte.add(s13);
        grupos.get(Valor.RED).anhadirCasilla(s13);
        Solar s14 = new Solar("Solar14", 24, 2400000, 200000, 1500000, 1500000, 300000, 600000, 2325000, 11000000, banca);
        ladoNorte.add(s14);
        grupos.get(Valor.RED).anhadirCasilla(s14);
        Transporte t3 = new Transporte("Trans3", 25, 2000000, 250000, banca);
        ladoNorte.add(t3);
        grupos.get("Transporte").anhadirCasilla(t3);
        Solar s15 = new Solar("Solar15", 26, 2600000, 220000, 1500000, 1500000, 300000, 600000, 2450000, 11500000, banca);
        ladoNorte.add(s15);
        grupos.get(Valor.YELLOW).anhadirCasilla(s15);
        Solar s16 = new Solar("Solar16", 27, 2600000, 220000, 1500000, 1500000, 300000, 600000, 2450000, 11500000, banca);
        ladoNorte.add(s16);
        grupos.get(Valor.YELLOW).anhadirCasilla(s16);
        Servicio serv2 = new Servicio("Serv2", 28, 1500000, 0, banca);
        ladoNorte.add(serv2);
        grupos.get("Servicios").anhadirCasilla(serv2);
        Solar s17 = new Solar("Solar17", 29, 2800000, 240000, 1500000, 1500000, 300000, 600000, 2600000, 12000000, banca);
        ladoNorte.add(s17);
        grupos.get(Valor.YELLOW).anhadirCasilla(s17);

        casillas.add(ladoNorte);

        // Lado Este
        ArrayList<Casilla> ladoEste = new ArrayList<>();
        ladoEste.add(new Especial("IrACarcel", 30));
        Solar s18 = new Solar("Solar18", 31, 3000000, 260000, 2000000, 2000000, 400000, 800000, 2750000, 12750000, banca);
        ladoEste.add(s18);
        grupos.get(Valor.GREEN).anhadirCasilla(s18);
        Solar s19 = new Solar("Solar19", 32, 3000000, 260000, 2000000, 2000000, 400000, 800000, 2750000, 12750000, banca);
        ladoEste.add(s19);
        grupos.get(Valor.GREEN).anhadirCasilla(s19);
        ladoEste.add(new CajaComunidad("Caja", 33));
        Solar s20 = new Solar("Solar20", 34, 3200000, 280000, 2000000, 2000000, 400000, 800000, 3000000, 14000000, banca);
        ladoEste.add(s20);
        grupos.get(Valor.GREEN).anhadirCasilla(s20);
        Transporte t4 = new Transporte("Trans4", 35, 2000000, 250000, banca);
        ladoEste.add(t4);
        grupos.get("Transporte").anhadirCasilla(t4);
        ladoEste.add(new Suerte("Suerte", 36, juego.getJugadores()));
        Solar s21 = new Solar("Solar21", 37, 3500000, 350000, 2000000, 2000000, 400000, 800000, 3250000, 17000000, banca);
        ladoEste.add(s21);
        grupos.get(Valor.BLUE).anhadirCasilla(s21);
        ladoEste.add(new Impuesto("Imp2", 38));
        Solar s22 = new Solar("Solar22", 39, 4000000, 500000, 2000000, 2000000, 400000, 800000, 4250000, 20000000, banca);
        ladoEste.add(s22);
        grupos.get(Valor.BLUE).anhadirCasilla(s22);
        
        casillas.add(ladoEste);
    }
    
    public void aumentarBoteParking(float valor){
        for (ArrayList<Casilla> casillas : casillas) {
            for(Casilla c : casillas){
                if (c.getNombre().equals("Parking")) {
                    ((Parking) c).sumarBoteParking(valor);
                    return;
                }
            }
        }
    }

    public Casilla encontrar_casilla(String nombre){
        for (ArrayList<Casilla> casillas : casillas){
            for(Casilla c : casillas){
                if (c.getNombre().equals(nombre)){
                return c;
            }
            }
        }
        return null;
    }

    public void moverA(String nombreCasilla, Jugador jugador) {
        Casilla destino = this.encontrar_casilla(nombreCasilla);
        if (destino != null) {
            int posActual = jugador.getAvatar().getLugar().getPosicion();
            int posDestino = destino.getPosicion();
            int desplazamiento = posDestino - posActual;
            try {
                jugador.getAvatar().moverAvatar(this, desplazamiento);
            } catch (JuegoException e) {
                juego.getConsola().imprimir(e.getMessage());
            }
        }
    }

    public Casilla encontrar_casilla_por_posicion(int posicion) {

        for (ArrayList<Casilla> casillas : casillas) {
            for (Casilla c : casillas) {
                if (c.getPosicion() == posicion) {
                    return c;
                }
            }
        }
        return null;
    }
    public ArrayList<String> buscarCasillaMasRentable(){
        ArrayList<String> masRentables = new ArrayList<>();
        float maxRentabilidad = 0.0f;

        for (ArrayList<Casilla> casillas : casillas) {
            for(Casilla c : casillas){
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
    
    public ArrayList<String> buscarCasillaMasVisitada(){
        ArrayList<String> masVisitadas = new ArrayList<>();
        int maxVisitas = 0;

        for (ArrayList<Casilla> casillas: casillas) {
            for(Casilla c : casillas){
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
}
