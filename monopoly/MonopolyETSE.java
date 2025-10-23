package monopoly;
import partida.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MonopolyETSE {

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.iniciarPartida();
        menu.lecturaFichero("monopoly/comandos_4.txt");
    }


}