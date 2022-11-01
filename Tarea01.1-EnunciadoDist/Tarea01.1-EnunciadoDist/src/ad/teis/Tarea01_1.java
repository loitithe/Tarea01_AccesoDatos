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
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author rguido
 */
public class Tarea01_1 implements Serializable {
    
    static ArrayList<Persona> personasRecuperadas = new ArrayList<>();
    public static final String PERSONAS_FILE = Paths.get("src", "docs", "personasConBorrados.dat").toString();
    private static final String PERSONAS_FILE_BK = Paths.get("src", "docs", "personasConBorrados.dat.bk").toString();
    private static final String PERSONAS_FILE_DESTINO = Paths.get("src", "docs",
            "destinoRandom.dat.").toString();
    
    private static void cribarBorrados() {
        //comprobacion existencia fichero personas_file
        File f = new File(PERSONAS_FILE);
        File copia = new File(PERSONAS_FILE_BK);
        if (f.exists()) {
            copiaFichero(f, copia);
            personasNoBorradas();
        } else {
            System.out.println("no Existe");
        }
        
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
    
    static void personasNoBorradas() {
        RandomAccessPersistencia rm = new RandomAccessPersistencia();
        
        System.err.println("personas no borradas");
        for (int i = 0; i < rm.leerTodo(PERSONAS_FILE).size(); i++) {
            if (!rm.leerPersona(i, PERSONAS_FILE).isBorrado()) {
                System.out.println(rm.leerPersona(i, PERSONAS_FILE));
                rm.escribirPersona(rm.leerPersona(i, PERSONAS_FILE), PERSONAS_FILE_DESTINO);
            }
        }
    }
    
    static void borrarFichero(String ruta) {
        File ficheroABorrar = new File(ruta);
        if (ficheroABorrar.exists() && ficheroABorrar.isFile()) {
            ficheroABorrar.delete();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        RandomAccessPersistencia random = new RandomAccessPersistencia();
        
        personasRecuperadas = random.leerTodo(PERSONAS_FILE_DESTINO);
        cribarBorrados();
        borrarFichero(PERSONAS_FILE);
        int contador = 1;
        for (Persona p : personasRecuperadas) {
            // System.out.println("Persona recuperada " + contador + ": " + p);
            contador++;
        }
        
    }
    
}
