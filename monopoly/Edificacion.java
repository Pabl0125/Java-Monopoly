package monopoly;

public class Edificacion {
    private String tipo; //Tipo de edificación (casa, hotel, etc.)
    private Casilla casilla; //Casilla a la que pertenece la edificación
    private float valor; //Valor de la edificación
    private Grupo grupo; //Grupo al que pertenece la edificación
    private float alquiler; //Alquiler que genera la edificación
    private static int currenIdCount;   //Lleva la cuenta de los ids
    private int id; //Id de la edificacion acutal
    
    //Constructor
    public Edificacion(Casilla casilla, String tipo) {
        this.tipo = tipo;
        this.casilla = casilla;
        this.grupo = casilla.getGrupo();
        grupo = casilla.getGrupo();
        alquiler = (casilla.getGrupo()).getAlquilerEdificioPorGrupo(tipo);
        valor = grupo.getPrecioEdificioPorGrupo(tipo);
        //Actualizamos Id y aumentamos el currentId
        currenIdCount++;
        this.id = currenIdCount;
        casilla.getEdificios().add(this);
    }
    
    //Getters
    public String getTipo() {
        return tipo;
    }
    public float getValor(){
        return valor;
    }
    public float getAlquiler(){
        return alquiler;
    }

@Override
    public String toString() {
        return tipo+"-"+id;
    }
}