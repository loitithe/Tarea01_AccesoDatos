/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ad.teis.model;

import java.io.Serializable;

/**
 *
 * @author rguido
 */
public class Persona implements Serializable {

    private long id; //8 bytes
    private String dni; //9 chars *2 = 18 bytes
    private String nombre; //100 chars * 2 = 200bytes
    private int edad; //4 bytes
    private float salario; //4 bytes
    private boolean borrado; //1 byte
    //Total = 200 + 18+16+1  = 235

    public Persona(long id, String dni, String nombre, int edad, float salario) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.edad = edad;
        this.salario = salario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

    @Override
    public String toString() {
        return "Persona{" + "id=" + id + ", dni=" + dni + " , nombre=" + ((nombre!=null)?nombre.trim():"null") + ", edad=" + edad + ", salario=" + salario + ", borrado=" + borrado +"}";
    }

}
