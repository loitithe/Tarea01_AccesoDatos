/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ad.teis.persistencia;

import ad.teis.model.Persona;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author rguido
 */
public class ObjectPersistencia implements IPersistencia {

    @Override
    public void escribirPersona(Persona persona, String ruta) {
        if (persona != null) {
            try (
                     FileOutputStream fos = new FileOutputStream(ruta);  ObjectOutputStream oos = new ObjectOutputStream(fos);) {
                oos.writeObject(persona);
            } catch (FileNotFoundException ex) {
                System.err.println("Ha ocurrido una excepción" + ex);
            } catch (IOException ex) {
                System.err.println("Ha ocurrido una excepción" + ex);
            }
        }
    }

    @Override
    public Persona leerDatos(String ruta) {
        Persona persona = null;

        try (
                 FileInputStream fis = new FileInputStream(ruta);  ObjectInputStream ois = new ObjectInputStream(fis);) {

            Object object = ois.readObject();
            if (object instanceof Persona) {
                persona = (Persona) object;
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.err.println("Ha ocurrido una excepción: " + ex.getMessage());
        }
        return persona;
    }
}
