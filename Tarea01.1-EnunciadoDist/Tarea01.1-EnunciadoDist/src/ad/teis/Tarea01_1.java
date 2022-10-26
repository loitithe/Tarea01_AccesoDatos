/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ad.teis;

import ad.teis.model.Persona;
import ad.teis.persistencia.RandomAccessPersistencia;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rguido
 */
public class Tarea01_1 {

    public static final String PERSONAS_FILE = Paths.get("src", "docs", "personasConBorrados.dat").toString();
    private static final String PERSONAS_FILE_BK = Paths.get("src", "docs", "personasConBorrados.dat.bk").toString();
    private static final String PERSONAS_FILE_DESTINO = Paths.get("src", "docs",
            "destinoRandom.dat.").toString();

    private static void cribarBorrados() {
        //comprobacion existencia fichero personas_file
        File f = new File(PERSONAS_FILE);
        File copia = new File(PERSONAS_FILE_DESTINO);
        if (f.exists()) {

            System.out.println("Existe");
            copiaFichero(f, copia);
            compruebaAtributo(f);

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

    static void compruebaAtributo(File f) {

//        RandomAccessPersistencia r = new RandomAccessPersistencia();
//        r.leerPersona(0, PERSONAS_FILE);
//        if (r.leerPersona(0, PERSONAS_FILE).isBorrado()) {
//            System.out.println(r.leerPersona(0, PERSONAS_FILE));
//        }
         try( FileInputStream fin = new FileInputStream(f); 
                ObjectInputStream oin = new ObjectInputStream(fin)) {
            try {
                Persona p;
                
                while (true) {
                    p = (Persona) oin.readObject();
                    if (!p.isBorrado()) {
                        System.out.println(p);
                    }
                }
            } catch (EOFException e) {
                System.out.println("Fin del fichero");
            } catch (ClassNotFoundException ex) {
                 Logger.getLogger(Tarea01_1.class.getName()).log(Level.SEVERE, null, ex);
             }
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ArrayList<Persona> personasRecuperadas = new ArrayList<>();
        RandomAccessPersistencia random = new RandomAccessPersistencia();

        cribarBorrados();
        personasRecuperadas = random.leerTodo(PERSONAS_FILE_DESTINO);
        int contador = 1;
        for (Persona p : personasRecuperadas) {
            System.out.println("Persona recuperada " + contador + ": " + p);
            contador++;
        }

    }

}