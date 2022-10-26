/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ad.teis.persistencia;

import ad.teis.model.Persona;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rguido
 */
public class DataIOPersistencia implements IPersistencia {

    @Override
    public void escribirPersona(Persona persona, String ruta) {

        if (persona != null) {

            try ( FileOutputStream fos = new FileOutputStream(ruta);  DataOutputStream dos = new DataOutputStream(fos);) {

                dos.writeLong(persona.getId());
                //escribo dos veces para ilustrar los dos métodos con strings
                dos.writeChars(persona.getDni()); //se escribe cada carácter a un par de bytes. No se puede leer con un editor sencillo
                dos.writeUTF(persona.getDni()); //se escribe cada carácter en uno o más bytes dependiendo de la codificación UNICODE de cada carácter. Se lee con un editor de texto sencillo

                StringBuilder sb = new StringBuilder(persona.getNombre());
                sb.setLength(LONG_NOMBRE);
                dos.writeChars(sb.toString());

                dos.writeUTF(persona.getNombre());

                dos.writeInt(persona.getEdad());
                dos.writeFloat(persona.getSalario());

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                System.out.println("Ha ocurrido una excepción: " + ex.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Ha ocurrido una excepción: " + ex.getMessage());
            }
        }
    }

    @Override
    public Persona leerDatos(String ruta) {

        long id = 0;
        char caracter;
        String dni = "";
        StringBuilder sb = new StringBuilder();
        String dniUTF = "", dniChars = "";
        String nombreUTF = "", nombreChars = "";
        int edad = 0;
        float salario = 0;
        Persona persona = null;

        try (
                 FileInputStream fis = new FileInputStream(ruta);  DataInputStream dis = new DataInputStream(fis);) {

            id = dis.readLong();
//longitud fija
            for (int i = 0; i < LONG_DNI; i++) {
                caracter = dis.readChar();
                sb.append(caracter);
            }
//lo leo 2 veces porque lo escribí 2 veces
            dniChars = sb.toString();
            dniUTF = dis.readUTF();

            //idem para nombre
            sb = new StringBuilder();
            for (int i = 0; i < LONG_NOMBRE; i++) {
                caracter = dis.readChar();
                sb.append(caracter);
            }

            nombreChars = sb.toString();

            nombreUTF = dis.readUTF();
            edad = dis.readInt();
            salario = dis.readFloat();

            //aquí podréis usar los strings leídos con char o con UTF
            
            persona = new Persona(id, dniChars, nombreChars, edad, salario);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Ha ocurrido una excepción: " + ex.getMessage());
        }
        return persona;
    }

}
