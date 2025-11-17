package model;

public class Curso {
    private int ID_CURSO;
    private String NOMBRE_CURSO;
    private double CREDITO;

    public Curso(int ID_CURSO, String NOMBRE_CURSO, double CREDITO) {
        this.ID_CURSO = ID_CURSO;
        this.NOMBRE_CURSO = NOMBRE_CURSO;
        this.CREDITO = CREDITO;
    }

    public int getID_CURSO() {
        return ID_CURSO;
    }

    public void setID_CURSO(int ID_CURSO) {
        this.ID_CURSO = ID_CURSO;
    }

    public String getNOMBRE_CURSO() {
        return NOMBRE_CURSO;
    }

    public void setNOMBRE_CURSO(String NOMBRE_CURSO) {
        this.NOMBRE_CURSO = NOMBRE_CURSO;
    }

    public double getCREDITO() {
        return CREDITO;
    }

    public void setCREDITO(double CREDITO) {
        this.CREDITO = CREDITO;
    }
}