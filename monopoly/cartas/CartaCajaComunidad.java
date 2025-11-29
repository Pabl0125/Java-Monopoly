package monopoly.cartas;

import partida.Jugador;
import monopoly.Tablero;

import java.util.ArrayList;

//Clase para las cartas de Caja de Comunidad //Hereda de Carta
public class CartaCajaComunidad extends Carta {
    //Constructor
    public CartaCajaComunidad(int num, String descripcion) {
        super(num, descripcion);
    }
    //Método para ejecutar la acción de la carta
    @Override
    public void accion(Tablero tablero, Jugador jugadorActual, ArrayList<Jugador> jugadores) {
        System.out.println("Has sacado la carta de Caja de Comunidad número " + this.getNum() + ":");
        switch (getNum()) {
            case 1:
                //Paga 500.000€ por un fin de semana en un balneario de 5 estrellas.
                System.out.println(getDescripcion());
                tablero.pagarImpuesto(jugadorActual, 500000f);
                break;
            case 2:
                //Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar los 2.000.000€.
                System.out.println(getDescripcion());
                jugadorActual.encarcelar(tablero);
                break;
            case 3:
                //Colócate en la casilla de Salida. Cobra 2.000.000€.
                System.out.println(getDescripcion());
                tablero.moverA("Salida", jugadorActual);
                break;
            case 4:
                //Devolución de Hacienda. Cobra 500.000€.
                System.out.println(getDescripcion());
                jugadorActual.sumarFortuna(500000f);
                jugadorActual.sumarDineroPremios(500000f);
                break;
            case 5:
                //Retrocede hasta Solar1 para comprar antigüedades exóticas.
                System.out.println(getDescripcion());
                tablero.moverA("Solar1", jugadorActual);
                break;
            case 6:
                //Ve a Solar20 para disfrutar del San Fermín.\nSi pasas por la casilla de Salida, cobra 2.000.000€.
                System.out.println(getDescripcion());
                tablero.moverA("Solar20", jugadorActual);
                break;
            default:
                System.out.println("Error: Número de carta no válido.");
                break;
        }
    }
}

    
    