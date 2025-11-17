package model;

import java.sql.Date;

public class Alumno {
    private int ID_ALUMNO;
    private String DNI;
    private String APELLIDO;
    private String NOMBRES;
    private Date FECHA_NACIMIENTO;
    private String TELEFONO;

    public Alumno(int ID_ALUMNO, String DNI, String APELLIDO, String NOMBRES, Date FECHA_NACIMIENTO, String TELEFONO) {
        this.ID_ALUMNO = ID_ALUMNO;
        this.DNI = DNI;
        this.APELLIDO = APELLIDO;
        this.NOMBRES = NOMBRES;
        this.FECHA_NACIMIENTO = FECHA_NACIMIENTO;
        this.TELEFONO = TELEFONO;
    }

    // Getters y Setters
    public int getID_ALUMNO() {
        return ID_ALUMNO;
    }

    public void setID_ALUMNO(int ID_ALUMNO) {
        this.ID_ALUMNO = ID_ALUMNO;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getAPELLIDO() {
        return APELLIDO;
    }

    public void setAPELLIDO(String APELLIDO) {
        this.APELLIDO = APELLIDO;
    }

    public String getNOMBRES() {
        return NOMBRES;
    }

    public void setNOMBRES(String NOMBRES) {
        this.NOMBRES = NOMBRES;
    }

    public Date getFECHA_NACIMIENTO() {
        return FECHA_NACIMIENTO;
    }

    public void setFECHA_NACIMIENTO(Date FECHA_NACIMIENTO) {
        this.FECHA_NACIMIENTO = FECHA_NACIMIENTO;
    }

    public String getTELEFONO() {
        return TELEFONO;
    }

    public void setTELEFONO(String TELEFONO) {
        this.TELEFONO = TELEFONO;
    }
}