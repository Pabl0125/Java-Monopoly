package monopoly;
import monopoly.*;
import partida.*;

import java.util.Scanner;

public class ConsolaNormal implements monopoly.interfaces.Consola {
    private Scanner scanner = new Scanner(System.in);
    @Override
    public String leer(String descripcion) {
        System.out.println(descripcion);
        return scanner.nextLine();

    }

    @Override
    public void imprimir(String mensaje) {
        System.out.println(mensaje);
    }

    
}