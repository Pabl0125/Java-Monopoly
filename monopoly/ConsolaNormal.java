package monopoly;
import monopoly.*;
import partida.*;

import java.util.Scanner;
import java.io.File;


public class ConsolaNormal implements monopoly.interfaces.Consola {
    private Scanner scanner;

    public ConsolaNormal(String fichero) {
        File file = new File(fichero);
        try{
            this.scanner = new Scanner(file);
        } catch (Exception e) {
            this.imprimir(e.getMessage());
        }
    }
    
    @Override
    public String leer(String descripcion) {
        if (this.scanner == null) return null;
        if (!this.scanner.hasNextLine()) return null;
        String linea = this.scanner.nextLine();
        imprimir(descripcion + linea);
        return linea;
    }

    @Override
    public void imprimir(String mensaje) {
        System.out.println(mensaje);
    }

    public Scanner getScanner() {
        return scanner;
    }

    
}