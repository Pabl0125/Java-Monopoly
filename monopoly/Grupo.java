package monopoly;

import partida.*;
import java.util.ArrayList;


class Grupo {

    //Atributos
    private ArrayList<Casilla> miembros; //Casillas miembros del grupo.
    private String colorGrupo; //Color del grupo
    private int numCasillas; //Número de casillas del grupo.

    //Constructor vacío.
    public Grupo() {
        this.miembros = new ArrayList<Casilla>();
        this.colorGrupo = null; //Convendria luego meterle un color a los genericos
    }

    /*Constructor para cuando el grupo está formado por DOS CASILLAS:
    * Requiere como parámetros las dos casillas miembro y el color del grupo.
     */
    public Grupo(Casilla cas1, Casilla cas2, String colorGrupo) {
        this.miembros = new ArrayList<Casilla>();
        this.miembros.add(cas1);
        cas1.setGrupo(this); // <--- AÑADIDO
        this.miembros.add(cas2);
        cas2.setGrupo(this); // <--- AÑADIDO
        this.colorGrupo = colorGrupo;
        this.numCasillas = 2;
    }

    /*Constructor para cuando el grupo está formado por TRES CASILLAS:
    * Requiere como parámetros las tres casillas miembro y el color del grupo.
     */
    public Grupo(Casilla cas1, Casilla cas2, Casilla cas3, String colorGrupo) {
        this.miembros = new ArrayList<Casilla>();
        miembros.add(cas1);
        cas1.setGrupo(this); // <--- AÑADIDO
        miembros.add(cas2);
        cas2.setGrupo(this); // <--- AÑADIDO
        miembros.add(cas3);
        cas3.setGrupo(this); // <--- AÑADIDO
        this.colorGrupo = colorGrupo;
        this.numCasillas = 3;
    }

    /* Método que añade una casilla al array de casillas miembro de un grupo.
    * Parámetro: casilla que se quiere añadir.
     */
    public void anhadirCasilla(Casilla miembro) {
        this.miembros.add(miembro);
        miembro.setGrupo(this);
        this.numCasillas++;
    }

    /*Método que comprueba si el jugador pasado tiene en su haber todas las casillas del grupo:
    * Parámetro: jugador que se quiere evaluar.
    * Valor devuelto: true si es dueño de todas las casillas del grupo, false en otro caso.
     */
    public boolean esDuenhoGrupo(Jugador jugador) {
        return false;
    }

    //Setters y getters:    
    public void setcolorGrupo(String color){
        this.colorGrupo = color;
    }
    public String getcolorGrupo(){
        return this.colorGrupo;
    }
    
    public String colorToNombreGrupo(){
        switch (this.colorGrupo) {
        case Valor.BLACK:
            return "Negro";
        case Valor.RED:
            return "Rojo";
        case Valor.GREEN:
            return "Verde";
        case Valor.YELLOW:
            return "Amarillo";
        case Valor.BLUE:
            return "Azul";
        case Valor.PURPLE:
            return "Morado";
        case Valor.CYAN:
            return "Cian";
        case Valor.WHITE:
            return "Blanco";
        case Valor.MARRON:
            return "Marrón";
        case Valor.NARANJA:
            return "Naranja";
        case Valor.GRIS:
            return "Gris";
        default:
            return "Color desconocido";
        }
    }

        
}
