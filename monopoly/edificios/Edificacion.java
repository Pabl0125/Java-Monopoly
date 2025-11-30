package monopoly.edificios;

import monopoly.*;
import monopoly.casillas.*;
public abstract class Edificacion {
    private String tipo;                //Tipo de edificación (casa, hotel, etc.)
    private float valor;                //Valor de la edificación
    private float alquiler;             //Alquiler que genera la edificación
    private static int currenIdCount;   //Lleva la cuenta de los ids
    private int id;                     //Id de la edificacion actual
    
    public Edificacion() {
        currenIdCount++;
        this.tipo = this.getClass().getSimpleName();    //El String tipo de edificacion equivale al nombre de la clase
        this.id = currenIdCount;
        this.valor = 0;
        this.alquiler = 0;
    }
    
    //////////////////GETTERS Y SETTERES//////////////////
    public String getTipo(){
        return tipo;
    }
    public float getValor(){
        return valor;
    }
    public float getAlquiler(){
        return alquiler;
    }
    public void setValor(float valor){
        this.valor = valor;
    }
    public void setAlquiler(float alquiler){
        this.alquiler = alquiler;
    }
    /////////////////METODOS GENERICOS//////////////////


    //////////////////METODOS SOBREESCRITOS/////////////
    @Override
    public String toString() {
        return tipo+"-"+id;
    }
}