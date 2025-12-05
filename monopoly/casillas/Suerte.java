package monopoly.casillas;

import java.util.ArrayList;
import java.util.Collections;
import monopoly.Juego;
import monopoly.Tablero;
import monopoly.cartas.CartaSuerte; // Asumiendo que esta clase existe
import partida.Jugador;

public final class Suerte extends Casilla{

    private static final ArrayList<CartaSuerte> mazoSuerte = new ArrayList<>();
    private static int indiceSuerte = 0;
    /////////////////// OJO///////////////
    private ArrayList<Jugador> jugadoresPartida = new ArrayList<>();
    //Se incluye este atributo porque algunas cartas requieren de los jugadores de la partida
    
    static {
        mazoSuerte.add(new CartaSuerte(1, "Decides hacer un viaje de placer. Avanza hasta Solar19. Si pasas por la casilla de Salida, cobra 2.000.000€."));
        mazoSuerte.add(new CartaSuerte(2, "Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar los 2.000.000€."));
        mazoSuerte.add(new CartaSuerte(3, "¡Has ganado el bote de la lotería! Recibe 1.000.000€."));
        mazoSuerte.add(new CartaSuerte(4, "Has sido elegido presidente de la junta directiva. Paga a cada jugador 250.000€."));
        mazoSuerte.add(new CartaSuerte(5, "¡Hora punta de tráfico! Retrocede tres casillas."));
        mazoSuerte.add(new CartaSuerte(6, "Te multan por usar el móvil mientras conduces. Paga 150.000€."));
        mazoSuerte.add(new CartaSuerte(7, "Avanza hasta la casilla de transporte más cercana. Si no tiene dueño, puedes comprarla. Si tiene dueño, paga al dueño el doble de la operación indicada."));
    }

    public Suerte(String nombre, int posicion, ArrayList<Jugador> jugadoresPartida){
        super(nombre,posicion);
        this.jugadoresPartida.addAll(jugadoresPartida);              
    }

    /////////////////////METODOS GENERICOS DEL MAZO /////////////////////

    public CartaSuerte sacarCartaSuerte(){
        CartaSuerte carta = mazoSuerte.get(indiceSuerte);
        incrementarIndiceSuerte(); 
        return carta;
    }

    public void incrementarIndiceSuerte(){
        indiceSuerte++;
        
        if(indiceSuerte >= mazoSuerte.size()) {
            indiceSuerte = 0; 
        }
    }
    

    ///////////////////// GETTERS Y SETTERS /////////////////////
    public ArrayList<CartaSuerte> getMazoSuerte() {
        return mazoSuerte;
    }

    ///////////////////// MÉTODOS SOBREESCRITOS /////////////////////
    @Override
    public String infoCasilla(){
        return "Nombre: " + this.getNombre() + "\nTipo: Suerte";
    }

    @Override
    public boolean evaluarCasilla(Tablero tablero, Jugador jugadorActual, int tirada){
        Juego.consola.imprimir(jugadorActual.getNombre() + " ha caído en " + jugadorActual.getAvatar().getLugar().getNombre() + " y debe tomar una carta de Suerte.");


        CartaSuerte carta = sacarCartaSuerte();
        Juego.consola.imprimir("Carta de Suerte: " + carta.getDescripcion());
        
        carta.accion(tablero, jugadorActual,jugadoresPartida);

        return jugadorActual.esSolvente();
    }
}