package monopoly.cartas;

import partida.Jugador;
import monopoly.Tablero;
import monopoly.ConsolaNormal;
import monopoly.Juego;

import java.util.ArrayList;

//Clase para las cartas de Caja de Comunidad //Hereda de Carta
public class CartaCajaComunidad extends Carta {
    //Constructor
    public CartaCajaComunidad(int num, Juego juego, String descripcion) {
        super(num, juego, descripcion);
    }
    //Método para ejecutar la acción de la carta
    @Override
    public void accion(Tablero tablero, Jugador jugadorActual, ArrayList<Jugador> jugadores) {
        this.getConsola().imprimir("Has sacado la carta de Caja de Comunidad número " + this.getNum() + ":");
        switch (getNum()) {
            case 1:
                //Paga 500.000€ por un fin de semana en un balneario de 5 estrellas.
                this.getConsola().imprimir(getDescripcion());
                tablero.pagarImpuesto(jugadorActual, 500000f);
                break;
            case 2:
                //Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar los 2.000.000€.
                this.getConsola().imprimir(getDescripcion());
                jugadorActual.encarcelar(tablero);
                break;
            case 3:
                //Colócate en la casilla de Salida. Cobra 2.000.000€.
                this.getConsola().imprimir(getDescripcion());
                tablero.moverA("Salida", jugadorActual);
                break;
            case 4:
                //Devolución de Hacienda. Cobra 500.000€.
                this.getConsola().imprimir(getDescripcion());
                jugadorActual.sumarFortuna(500000f);
                jugadorActual.sumarDineroPremios(500000f);
                break;
            case 5:
                //Retrocede hasta Solar1 para comprar antigüedades exóticas.
                this.getConsola().imprimir(getDescripcion());
                tablero.moverA("Solar1", jugadorActual);
                break;
            case 6:
                //Ve a Solar20 para disfrutar del San Fermín.\nSi pasas por la casilla de Salida, cobra 2.000.000€.
                this.getConsola().imprimir(getDescripcion());
                tablero.moverA("Solar20", jugadorActual);
                break;
            default:
                this.getConsola().imprimir("Error: Número de carta no válido.");
                break;
        }
    }
}

    
    