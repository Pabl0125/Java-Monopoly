package monopoly;
import partida.Jugador;
import monopoly.casillas.Propiedad;

public class Trato {
    private Jugador jugador1;               // El jugador que propone el trato
    private Jugador jugador2;               // El jugador al que se le propone el trato
    private Propiedad propiedadOfrecida;    // La propiedad que el jugador1 ofrece
    private Propiedad propiedadSolicitada;  // La propiedad que el jugador1 solicita
    private float dineroOfrecido;             // La cantidad de dinero que el jugador1 ofrece
    private float dineroSolicitado;           // La cantidad de dinero que el jugador1 solicita
    private int id;                         // Identificador del trato
    private static int idCount = 1;         // Contador estatico para los id's

    // Constructor, inicializa todos los atributos del trato (si alguno de ellos no es ofrecido / solicitado, se pasa como null o 0.0f)
    public Trato(Jugador jugador1, Jugador jugador2, Propiedad propiedadOfrecida, Propiedad propiedadSolicitada, float dineroOfrecido, float dineroSolicitado) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.propiedadOfrecida = propiedadOfrecida;
        this.propiedadSolicitada = propiedadSolicitada;
        this.dineroOfrecido = dineroOfrecido;
        this.dineroSolicitado = dineroSolicitado;
        id = idCount++;
    }


    /////////////////Getters de los atributos del trato//////////////////
    
    public Jugador getJugador1() {
        return jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public Propiedad getPropiedadOfrecida() {
        return propiedadOfrecida;
    }

    public Propiedad getPropiedadSolicitada() {
        return propiedadSolicitada;
    }

    public float getDineroOfrecido() {
        return dineroOfrecido;
    }

    public float getDineroSolicitado() {
        return dineroSolicitado;
    }

    public int getId(){
        return id;
    }
    ////////////////////METODOS SOBREESCRITOS/////////////////////////////
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Trato otroTrato = (Trato) obj;

        return this.id == otroTrato.id;
    }
    
}