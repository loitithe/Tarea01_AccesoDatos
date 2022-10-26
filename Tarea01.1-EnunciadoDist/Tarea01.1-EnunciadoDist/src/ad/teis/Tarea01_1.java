/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ad.teis;

import ad.teis.model.Persona;
import ad.teis.persistencia.RandomAccessPersistencia;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author rguido
 */
public class Tarea01_1 implements Serializable{
    static ArrayList<Persona> personasRecuperadas = new ArrayList<>();
    public static final String PERSONAS_FILE = Paths.get("src", "docs", "personasConBorrados.dat").toString();
    private static final String PERSONAS_FILE_BK = Paths.get("src", "docs", "personasConBorrados.dat.bk").toString();
    private static final String PERSONAS_FILE_DESTINO = Paths.get("src", "docs",
            "destinoRandom.dat.").toString();

    private static void cribarBorrados() {
        //comprobacion existencia fichero personas_file
        File f = new File(PERSONAS_FILE);
        File copia = new File(PERSONAS_FILE_DESTINO);
        if (f.exists()) {
            copiaFichero(f, copia);
        } else {
            System.out.println("no Existe");
        }
          personasNoBorradas(personasRecuperadas);
          
    }

    /**
     *
     * @param origen
     * @param destino
     */
    static void copiaFichero(File origen, File destino) {
        try ( InputStream in = new FileInputStream(origen);  OutputStream out = new FileOutputStream(destino)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.getMessage();
        }

    }

    static void personasNoBorradas( ArrayList<Persona> personasRecuperadas) {
        Persona p;
//        RandomAccessPersistencia r = new RandomAccessPersistencia();
//
//        for (int i = 0; i < personasRecuperadas.size(); i++) {
//            if (!personasRecuperadas.get(i).isBorrado()) {
//                System.out.println(personasRecuperadas.get(i));
//            }
//        }
        try (
                 FileInputStream inObj = new FileInputStream(PERSONAS_FILE);  ObjectInputStream oin = new ObjectInputStream(inObj);) {

            while (true) {
                p = (Persona) oin.readObject();
                if (!p.isBorrado()) {
                    System.out.println(p);
                }
            }
        } catch (Exception e) {
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        RandomAccessPersistencia random = new RandomAccessPersistencia();
     
        personasRecuperadas = random.leerTodo(PERSONAS_FILE_DESTINO);
        cribarBorrados();
        int contador = 1;
        for (Persona p : personasRecuperadas) {
                  System.out.println("Persona recuperada " + contador + ": " + p);
            contador++;
        }

    }

}
