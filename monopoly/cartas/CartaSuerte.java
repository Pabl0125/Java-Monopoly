package monopoly.cartas;

import partida.Jugador;
import monopoly.Tablero;
import monopoly.casillas.Transporte;

import java.util.ArrayList;

public class CartaSuerte extends Carta {
    
    public CartaSuerte(int num, String descripcion) {
        super(num, descripcion);
    }

    public void accion(Tablero tablero, Jugador jugadorActual, ArrayList<Jugador> jugadores) {
        System.out.println("Has sacado la carta de Suerte número " + this.getNum() + ":");
        System.out.println(getDescripcion());
        switch (getNum()) {
            case 1:
                // Decides hacer un viaje de placer. Avanza hasta Solar19. Si pasas por la casilla de Salida, cobra 2.000.000€.
                System.out.println(getDescripcion());
                tablero.moverA("Solar19", jugadorActual);
                break;
            case 2:
                // Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar los 2.000.000€.
                System.out.println(getDescripcion());
                jugadorActual.encarcelar(tablero);
                break;
            case 3:
                // ¡Has ganado el bote de la lotería! Recibe 1.000.000€.
                System.out.println(getDescripcion());
                jugadorActual.sumarFortuna(1000000f);
                jugadorActual.sumarDineroPremios(1000000f);
                break;
            case 4:
                // Has sido elegido presidente de la junta directiva. Paga a cada jugador 250.000€.
                System.out.println(getDescripcion());
                float totalAPagar = 250000f * (jugadores.size() - 1);
                if (jugadorActual.getFortuna() < totalAPagar) {
                    System.out.println("No tienes suficiente dinero para pagar a todos los jugadores.");
                } else {
                    jugadorActual.sumarFortuna(-totalAPagar);
                    jugadorActual.sumarGastos(totalAPagar);
                    for (Jugador j : jugadores) {
                        if (!j.equals(jugadorActual)) {
                            j.sumarFortuna(250000f);
                            j.sumarDineroPremios(250000f);
                        }
                    }
                }
                break;
            case 5:
                // ¡Hora punta de tráfico! Retrocede tres casillas.
                System.out.println(getDescripcion());
                jugadorActual.getAvatar().moverAvatar(tablero, -3);
                break;
            case 6:
                // Te multan por usar el móvil mientras conduces. Paga 150.000€.
                System.out.println(getDescripcion());
                tablero.pagarImpuesto(jugadorActual, 150000f);
                break;
            case 7:
                // Avanza hasta la casilla de transporte más cercana. Si no tiene dueño, puedes comprarla. 
                //Si tiene dueño, paga al dueño el doble de la operación indicada.
                System.out.println(getDescripcion());
                int posActual = jugadorActual.getAvatar().getLugar().getPosicion();
                int posTrans1 = tablero.encontrar_casilla("Trans1").getPosicion();
                int posTrans2 = tablero.encontrar_casilla("Trans2").getPosicion();
                int posTrans3 = tablero.encontrar_casilla("Trans3").getPosicion();
                int posTrans4 = tablero.encontrar_casilla("Trans4").getPosicion();

                String casillaDestino;

                if (posActual < posTrans1) {
                    casillaDestino = "Trans1";
                } else if (posActual < posTrans2) {
                    casillaDestino = "Trans2";
                } else if (posActual < posTrans3) {
                    casillaDestino = "Trans3";
                } else if (posActual < posTrans4) {
                    casillaDestino = "Trans4";
                } else {
                    // Si el jugador ha pasado todas las casillas de transporte, la siguiente más cercana es la primera
                    casillaDestino = "Trans1";
                }

                tablero.moverA(casillaDestino, jugadorActual);
                Jugador duenho =((Transporte) tablero.encontrar_casilla(casillaDestino)).getDuenho();
                System.out.println("Si caes en una propiedad de otro jugador, el pago será el doble de lo normal.");

                if (duenho.getNombre().equals("Banca")) {
                    System.out.println("La propiedad no tiene dueño. Puedes comprarla si lo deseas.");

                } else if (!duenho.equals(jugadorActual)) {

                    //Si la casilla tiene dueño y no es el jugador actual
                    float pagoNormal = ((Transporte) tablero.encontrar_casilla(casillaDestino)).getAlquiler();
                    System.out.println("La propiedad tiene dueño. Debes pagar el doble: " + (pagoNormal * 2) + "€.");
                    float pagoDoble = pagoNormal * 2;
                    //Realizar el pago
                    tablero.pagarImpuesto(jugadorActual, pagoDoble);    //Aumenta el bote de Parking
                    duenho.sumarFortuna(pagoDoble);                     //El dueño recibe el pago
                    duenho.sumardineroCobroAlquileres(pagoDoble);       //Cuenta para estadísticas
                }
                break;
            default:
                System.out.println("Error: Número de carta no válido.");
                break;
        }
    }
}
