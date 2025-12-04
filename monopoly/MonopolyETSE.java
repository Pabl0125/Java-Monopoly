package monopoly;
import partida.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import monopoly.excepciones.MonopolyException;

public class MonopolyETSE {

    public static void main(String[] args) {
        Juego menu = new Juego();
        try {
            menu.iniciarPartida();
            menu.lecturaFichero("monopoly/comandos.txt");
        } catch (MonopolyException e) {
            System.err.println(e.getMessage());
        }
    }

}