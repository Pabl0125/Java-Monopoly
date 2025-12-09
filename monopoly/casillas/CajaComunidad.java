package monopoly.casillas;

import java.util.ArrayList;
import java.util.Collections;
import monopoly.Juego;
import monopoly.Tablero;
import monopoly.cartas.CartaCajaComunidad;
import partida.Jugador;

public final class CajaComunidad extends Casilla{

    private static final ArrayList<CartaCajaComunidad> mazoCajaComunidad = new ArrayList<>();
    private static int indiceCajaComunidad = 0;
    /////////////////// OJO///////////////
    private ArrayList<Jugador> jugadoresPartida = new ArrayList<>();
    //Se incluye este atributo porque algunas cartas requieren de los jugadores de la partida
    
    static {
        mazoCajaComunidad.add(new CartaCajaComunidad(1,"Paga 500.000€ por un fin de semana en un balneario de 5 estrellas."));
        mazoCajaComunidad.add(new CartaCajaComunidad(2, "Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar"));
        mazoCajaComunidad.add(new CartaCajaComunidad(3, "Colócate en la casilla de Salida. Cobra 2.000.000€."));
        mazoCajaComunidad.add(new CartaCajaComunidad(4, "Devolución de Hacienda. Cobra 500.000€."));
        mazoCajaComunidad.add(new CartaCajaComunidad(5, "Retrocede hasta Solar1 para comprar antigüedades exóticas."));
        mazoCajaComunidad.add(new CartaCajaComunidad(6, "Ve a Solar20 para disfrutar del San Fermín. Si pasas por la casilla de Salida, cobra 2.000.000€."));
    }

    public CajaComunidad(String nombre, int posicion){
        super(nombre,posicion);                    
    }

    /////////////////////METODOS GENERICOS DEL MAZO /////////////////////

    public CartaCajaComunidad sacarCartaCajaComunidad(){
        CartaCajaComunidad carta = mazoCajaComunidad.get(indiceCajaComunidad);
        incrementarIndiceCajaComunidad(); 
        return carta;
    }
    
    public void incrementarIndiceCajaComunidad(){
        indiceCajaComunidad++;
        
        if(indiceCajaComunidad >= mazoCajaComunidad.size()) indiceCajaComunidad = 0; 
    }
    

    ///////////////////// GETTERS Y SETTERS /////////////////////
    public ArrayList<CartaCajaComunidad> getMazoCajaComunidad() {
        return mazoCajaComunidad;
    }

    ///////////////////// MÉTODOS SOBREESCRITOS /////////////////////
    @Override
    public String infoCasilla(){
        return "Nombre: " + this.getNombre() + "\nTipo: Caja de Comunidad";
    }

    @Override
    public boolean evaluarCasilla(Tablero tablero, Jugador jugadorActual, int tirada){
        Juego.consola.imprimir(jugadorActual.getNombre() + " ha caído en " + jugadorActual.getAvatar().getLugar().getNombre() + " y debe tomar una carta de Caja de Comunidad.");

        CartaCajaComunidad carta = sacarCartaCajaComunidad();
        Juego.consola.imprimir("Carta de Caja de Comunidad: " + carta.getDescripcion());
        //Se ejecuta la accion correspondiente a la carta
        carta.accion(tablero, jugadorActual, jugadoresPartida);


        return jugadorActual.esSolvente();
    }
}