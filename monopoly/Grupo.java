package monopoly;

import partida.*;
import java.util.ArrayList;


class Grupo {

    //Atributos
    private ArrayList<Casilla> miembros; //Casillas miembros del grupo.
    private String colorGrupo; //Color del grupo
    private int numCasillas; //Número de casillas del grupo.
    private float precioCasa;
    private float precioHotel;
    private float precioPiscina;
    private float precioPistaDeporte;
    private float rentabilidad;
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
        cas1.setGrupo(this);
        this.miembros.add(cas2);
        cas2.setGrupo(this);
        this.colorGrupo = colorGrupo;
        this.numCasillas = 2;
        asignarPreciosEdificios(colorGrupo);
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
        asignarPreciosEdificios(colorGrupo);
    }

    private void asignarPreciosEdificios(String color) {
        switch (color) {
            case Valor.MARRON:
                this.precioCasa = 500000f;
                this.precioHotel = 500000f;
                this.precioPiscina = 100000f;
                this.precioPistaDeporte = 200000f;
                break;
            case Valor.CYAN:
                this.precioCasa = 500000f;
                this.precioHotel = 500000f;
                this.precioPiscina = 100000f;
                this.precioPistaDeporte = 200000f;
                break;
            case Valor.PURPLE:
                this.precioCasa = 1000000f;
                this.precioHotel = 1000000f;
                this.precioPiscina = 200000f;
                this.precioPistaDeporte = 400000f;
                break;
            case Valor.NARANJA:
                this.precioCasa = 1000000f;
                this.precioHotel = 1000000f;
                this.precioPiscina = 200000f;
                this.precioPistaDeporte = 400000f;
                break;
            case Valor.RED:
                this.precioCasa = 1500000f;
                this.precioHotel = 1500000f;
                this.precioPiscina = 300000f;
                this.precioPistaDeporte = 600000f;
                break;
            case Valor.YELLOW:
                this.precioCasa = 1500000f;
                this.precioHotel = 1500000f;
                this.precioPiscina = 300000f;
                this.precioPistaDeporte = 600000f;
                break;
            case Valor.GREEN:
                this.precioCasa = 2000000f;
                this.precioHotel = 2000000f;
                this.precioPiscina = 400000f;
                this.precioPistaDeporte = 800000f;
                break;
            case Valor.BLUE:
                this.precioCasa = 2000000f;
                this.precioHotel = 2000000f;
                this.precioPiscina = 400000f;
                this.precioPistaDeporte = 800000f;
                break;
            case Valor.BLACK:
            default:
                this.precioCasa = 0f;
                this.precioHotel = 0f;
                this.precioPiscina = 0f;
                this.precioPistaDeporte = 0f;
                System.out.println("codigo de color no identificado");
                break;
        }
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
        for (Casilla casilla : this.miembros) {
            if (casilla.getDuenho() != jugador) {
                return false;
            }
        }
        return true;
    }

    //Setters y getters:    
    public void setcolorGrupo(String color){
        this.colorGrupo = color;
    }
    public String getcolorGrupo(){
        return this.colorGrupo;
    }

    
    
    public float getAlquilerEdificioPorGrupo(String tipo) {
    
    // Switch principal basado en el color del grupo
    switch (this.getcolorGrupo()) {
        
        // Grupo 1: Solar1, Solar2 (Valores de Solar2)
        case Valor.MARRON:
            // Switch anidado basado en el tipo de edificación
            switch (tipo) {
                case "casa": // Alquiler de Solar2
                    return 800000.0f;
                case "hotel": // Alquiler de Solar2
                    return 4500000.0f;
                case "piscina": // Alquiler de Solar2
                    return 900000.0f;
                case "pista deportiva": // Alquiler de Solar2
                    return 900000.0f;
                default:
                    break; // Tipo de edificación no válido para este grupo
            }
            break; // Fin del case Valor.MARRON/BLACK

        // Grupo 2: Solar3, Solar4, Solar5 
        case Valor.CYAN:
            switch (tipo) {
                case "casa": // Alquiler de Solar5
                    return 1250000.0f;
                case "hotel": // Alquiler de Solar5
                    return 6000000.0f;
                case "piscina": // Alquiler de Solar5
                    return 1200000.0f;
                case "pista deportiva": // Alquiler de Solar5
                    return 1200000.0f;
                default:
                    break;
            }
            break; // Fin del case Valor.CYAN

        // Grupo 3: Solar6, Solar7, Solar8 
        case Valor.PURPLE:
            switch (tipo) {
                case "casa": // Alquiler de Solar8
                    return 1750000.0f;
                case "hotel": // Alquiler de Solar8
                    return 9000000.0f;
                case "piscina": // Alquiler de Solar8
                    return 1800000.0f;
                case "pista deportiva": // Alquiler de Solar8
                    return 1800000.0f;
                default:
                    break;
            }
            break; // Fin del case Valor.PURPLE

        // Grupo 4: Solar9, Solar10, Solar11 
        case Valor.NARANJA:
            switch (tipo) {
                case "casa": // Alquiler de Solar11
                    return 2000000.0f;
                case "hotel": // Alquiler de Solar11
                    return 10000000.0f;
                case "piscina": // Alquiler de Solar11
                    return 2000000.0f;
                case "pista deportiva": // Alquiler de Solar11
                    return 2000000.0f;
                default:
                    break;
            }
            break; // Fin del case Valor.NARANJA

        // Grupo 5: Solar12, Solar13, Solar14 
        case Valor.RED:
            switch (tipo) {
                case "casa": // Alquiler de Solar14
                    return 2325000.0f;
                case "hotel": // Alquiler de Solar14
                    return 11000000.0f;
                case "piscina": // Alquiler de Solar14
                    return 2200000.0f;
                case "pista deportiva": // Alquiler de Solar14
                    return 2200000.0f;
                default:
                    break;
            }
            break; // Fin del case Valor.RED

        // Grupo 6: Solar15, Solar16, Solar17 
        case Valor.YELLOW:
            switch (tipo) {
                case "casa": // Alquiler de Solar17
                    return 2600000.0f;
                case "hotel": // Alquiler de Solar17
                    return 12000000.0f;
                case "piscina": // Alquiler de Solar17
                    return 2400000.0f;
                case "pista deportiva": // Alquiler de Solar17
                    return 2400000.0f;
                default:
                    break;
            }
            break; // Fin del case Valor.YELLOW

        // Grupo 7: Solar18, Solar19, Solar20
        case Valor.GREEN:
            switch (tipo) {
                case "casa": // Alquiler de Solar20
                    return 3000000.0f;
                case "hotel": // Alquiler de Solar20
                    return 14000000.0f;
                case "piscina": // Alquiler de Solar20
                    return 2800000.0f;
                case "pista deportiva": // Alquiler de Solar20
                    return 2800000.0f;
                default:
                    break;
            }
            break; // Fin del case Valor.GREEN

        // Grupo 8: Solar21, Solar22 
        case Valor.BLUE:
            switch (tipo) {
                case "casa": // Alquiler de Solar22
                    return 4250000.0f;
                case "hotel": // Alquiler de Solar22
                    return 20000000.0f;
                case "piscina": // Alquiler de Solar22
                    return 4000000.0f;
                case "pista deportiva": // Alquiler de Solar22
                    return 4000000.0f;
                default:
                    break;
            }
            break; // Fin del case Valor.BLUE

        // Casos para colores no asociados a solares (Transporte, Servicios, etc.)
        case Valor.WHITE:
        case Valor.GRIS:
        case Valor.BLACK:
            // Estos grupos no tienen edificaciones, devolvemos 0 directamente.
            return 0.0f;

        // Si el color no coincide con ningún grupo de solar
        default:
            return 0.0f;
    }

    // Si el 'tipo' de edificación no coincidió en el switch anidado,
    // el flujo llegará aquí y devolverá 0.0f.
    return 0.0f;
}

    public float getPrecioEdificioPorGrupo(String tipo) {
        switch (tipo) {
            case "casa":
                return this.precioCasa;
            case "hotel":
                return this.precioHotel;
            case "piscina":
                return this.precioPiscina;
            case "pista":
            case "pista deportiva":
                return this.precioPistaDeporte;
            default:
                // Tipo de edificación no válido
                return 0.0f;
        }
    }

    public void sumarRentabilidad(float cantidad) {
        this.rentabilidad += cantidad;
    }

    public float getRentabilidad() {
        return this.rentabilidad;
    }

    public void setRentabilidad(float rentabilidad) {
        this.rentabilidad = rentabilidad;
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
