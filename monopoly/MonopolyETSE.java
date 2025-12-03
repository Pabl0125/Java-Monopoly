package monopoly;
import partida.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MonopolyETSE {

    public static void main(String[] args) {
        Juego menu = new Juego();
        menu.iniciarPartida();
        menu.lecturaFichero("monopoly/comandos.txt");
    }


}