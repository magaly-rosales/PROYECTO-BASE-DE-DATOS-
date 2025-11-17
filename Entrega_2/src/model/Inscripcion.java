package model;

import java.sql.Date;

public class Inscripcion {
    private int ID_INSCRIPCIONES;
    private int ID_ALUMNO;
    private int ID_CURSO;
    private Date FECHA_INSCRIPCION;

    public Inscripcion(int ID_INSCRIPCIONES, int ID_ALUMNO, int ID_CURSO, Date FECHA_INSCRIPCION) {
        this.ID_INSCRIPCIONES = ID_INSCRIPCIONES;
        this.ID_ALUMNO = ID_ALUMNO;
        this.ID_CURSO = ID_CURSO;
        this.FECHA_INSCRIPCION = FECHA_INSCRIPCION;
    }

    public int getID_INSCRIPCIONES() {
        return ID_INSCRIPCIONES;
    }

    public void setID_INSCRIPCIONES(int ID_INSCRIPCIONES) {
        this.ID_INSCRIPCIONES = ID_INSCRIPCIONES;
    }

    public int getID_ALUMNO() {
        return ID_ALUMNO;
    }

    public void setID_ALUMNO(int ID_ALUMNO) {
        this.ID_ALUMNO = ID_ALUMNO;
    }

    public int getID_CURSO() {
        return ID_CURSO;
    }

    public void setID_CURSO(int ID_CURSO) {
        this.ID_CURSO = ID_CURSO;
    }

    public Date getFECHA_INSCRIPCION() {
        return FECHA_INSCRIPCION;
    }

    public void setFECHA_INSCRIPCION(Date FECHA_INSCRIPCION) {
        this.FECHA_INSCRIPCION = FECHA_INSCRIPCION;
    }
}