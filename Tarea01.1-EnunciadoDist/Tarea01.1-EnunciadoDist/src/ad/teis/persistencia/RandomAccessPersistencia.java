/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ad.teis.persistencia;

import ad.teis.model.Persona;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 *
 * @author rguido
 */
public class RandomAccessPersistencia implements IPersistencia {

    private static final int LONG_BYTES_PERSONA = 35 + RandomAccessPersistencia.OFFSET_NOMBRE;
    private static final int OFFSET_BORRADO = 1;
    private static final int OFFSET_SALARIO = 4;
    private static final int OFFSET_NOMBRE = 200;

    @Override
    public void escribirPersona(Persona persona, String ruta) {

        try (
             RandomAccessFile raf = new RandomAccessFile(ruta, "rw");) {
            raf.writeLong(persona.getId());
            StringBuilder sb = new StringBuilder(persona.getDni());
            sb.setLength(LONG_DNI);
            raf.writeChars(sb.toString());

            sb = new StringBuilder(persona.getNombre());
            sb.setLength(LONG_NOMBRE);
            raf.writeChars(sb.toString());

            raf.writeInt(persona.getEdad());
            raf.writeFloat(persona.getSalario());

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());
        }
    }

    @Override
    public Persona leerDatos(String ruta) {
        long id = 0;
        int edad = 0;
        float salario = 0;
        String dni = "", nombre = "";
        Persona persona = null;
        try (
                 RandomAccessFile raf = new RandomAccessFile(ruta, "r");) {
            id = raf.readLong();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < LONG_DNI; i++) {
                sb.append(raf.readChar());
            }
            dni = sb.toString();

            sb = new StringBuilder();
            for (int i = 0; i < LONG_NOMBRE; i++) {

                sb.append(raf.readChar());
            }
            nombre = sb.toString();

            edad = raf.readInt();
            salario = raf.readFloat();
            persona = new Persona(id, dni, nombre, edad, salario);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());
        }

        return persona;
    }

    public void escribirPersonas(ArrayList<Persona> personas, String ruta) {
        long longitudBytes = 0;
        if (personas != null) {
            try (
                     RandomAccessFile raf = new RandomAccessFile(ruta, "rw");) {
//Nos posicionamos al final del fichero
                longitudBytes = raf.length();

                raf.seek(longitudBytes);
                for (Persona persona : personas) {
                    raf.writeLong(persona.getId());

                    StringBuilder sb = new StringBuilder(persona.getDni());
                    sb.setLength(9);
                    raf.writeChars(sb.toString());

                    sb = new StringBuilder(persona.getNombre());
                    sb.setLength(LONG_NOMBRE);
                    raf.writeChars(sb.toString());

                    raf.writeInt(persona.getEdad());
                    raf.writeFloat(persona.getSalario());

                    raf.writeBoolean(persona.isBorrado());
                }

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                System.out.println("Se ha producido una excepción: " + ex.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Se ha producido una excepción: " + ex.getMessage());
            }
        }

    }

    public ArrayList<Persona> leerTodo(String ruta) {
        long id;
        String dni = "", nombre = "";
        int edad;
        float salario;
        StringBuilder sb = new StringBuilder();
        Persona persona = null;
        boolean borrado = false;
        ArrayList<Persona> personas = new ArrayList<>();
        try (
                 RandomAccessFile raf = new RandomAccessFile(ruta, "r");) {

            do {
                id = raf.readLong();
                sb = new StringBuilder();
                for (int i = 0; i <= 8; i++) {
                    sb.append(raf.readChar());
                }

                dni = sb.toString();

                sb = new StringBuilder();
                for (int i = 0; i < LONG_NOMBRE; i++) {
                    sb.append(raf.readChar());
                }
                nombre = sb.toString();

                edad = raf.readInt();
                salario = raf.readFloat();

                borrado = raf.readBoolean();

                persona = new Persona(id, dni, nombre, edad, salario);
                persona.setBorrado(borrado);

                personas.add(persona);

            } while (raf.getFilePointer() < raf.length());

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());
        }
        return personas;

    }

    public Persona leerPersona(int posicion, String ruta) {
        long id = 0;
        String dni = "", nombre = "";
        int edad = 0;
        float salario = 0;
        StringBuilder sb = null;
        Persona persona = null;
        boolean borrado = false;

        try (
                 RandomAccessFile raf = new RandomAccessFile(ruta, "r");) {

            //nos posicionamos al comienzo de la persona X 
            raf.seek(converToBytePosition(posicion));
            id = raf.readLong();

            sb = new StringBuilder();
            for (int i = 0; i <= 8; i++) {
                sb.append(raf.readChar());
            }

            dni = sb.toString();

            sb = new StringBuilder();
            for (int i = 0; i < LONG_NOMBRE; i++) {
                sb.append(raf.readChar());
            }
            nombre = sb.toString();

            edad = raf.readInt();
            salario = raf.readFloat();

            borrado = raf.readBoolean();

            persona = new Persona(id, dni, nombre, edad, salario);
            persona.setBorrado(borrado);

        } catch (EOFException ex) {
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());
        }

        return persona;
    }

  

    public Persona add(int posicion, String ruta, Persona persona) {
        try (
                 RandomAccessFile raf = new RandomAccessFile(ruta, "rw");) {

            raf.seek(converToBytePosition(posicion));

            raf.writeLong(persona.getId());
            StringBuilder sb = new StringBuilder(persona.getDni());
            sb.setLength(9);
            raf.writeChars(sb.toString());

            sb = new StringBuilder(persona.getNombre());
            sb.setLength(LONG_NOMBRE);
            raf.writeChars(sb.toString());

            raf.writeInt(persona.getEdad());
            raf.writeFloat(persona.getSalario());
            raf.writeBoolean(persona.isBorrado());

        } catch (FileNotFoundException ex) {
            persona = null;
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());
        } catch (IOException ex) {
            persona = null;
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());
        }
        return persona;
    }

    public float sumarSalario(int posicion, String ruta, float incremento) {

        float salario = 0;
        try (
                 RandomAccessFile raf = new RandomAccessFile(ruta, "rw");) {

            raf.seek(posicion * LONG_BYTES_PERSONA - OFFSET_BORRADO - OFFSET_SALARIO);
//Nos posicionamos al final de la persona que ocupa la posicion que viene por parámetro y restamos los bytes que ocupan borrado y salario.
            salario = raf.readFloat(); //al leer, el puntero avanza tantos bytes como ocupa un float

            salario += incremento;
            raf.seek(raf.getFilePointer() - OFFSET_SALARIO); //retrocedemos el puntero al comienzo de salario
            raf.writeFloat(salario);//actualizamos

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());
        }
        return salario;
    }

    public boolean marcarBorrado(int posicion, String ruta, boolean borrado) {
        boolean exito = false;
        try (
                 RandomAccessFile raf = new RandomAccessFile(ruta, "rw");) {

            raf.seek(posicion * LONG_BYTES_PERSONA - OFFSET_BORRADO);
            raf.writeBoolean(borrado);

            exito = true;

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Se ha producido una excepción: " + ex.getMessage());
        }
        return exito;
    }
    
      private long converToBytePosition(int posicion) {
        if (posicion == 0) {
            return posicion;
        } else {
            return LONG_BYTES_PERSONA * (posicion - 1);
        }
    }
}
