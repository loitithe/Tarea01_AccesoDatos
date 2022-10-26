/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ad.teis.persistencia;

import ad.teis.model.Persona;
import java.util.ArrayList;

/**
 *
 * @author rguido
 */
public interface IPersistencia {
    
     public static final int LONG_DNI=9;
    public static final int LONG_NOMBRE=100;
    
    void escribirPersona(Persona persona, String ruta);
    Persona leerDatos(String ruta);

    
}
