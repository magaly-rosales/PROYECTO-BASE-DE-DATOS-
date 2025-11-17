package dao;

import interfaz.IAlumnoDao;
import model.Alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AlumnoDao implements IAlumnoDao {

    public String INSERT = "INSERT INTO ALUMNO(ID_ALUMNO, DNI, APELLIDO, NOMBRES, FECHA_NACIMIENTO, TELEFONO) VALUES(?, ?, ?, ?, ?, ?)";
    public String UPDATE = "UPDATE ALUMNO SET TELEFONO = ?, DNI = ?, NOMBRES = ?, APELLIDO = ?, WHERE ID_ALUMNO = ?";
    public String DELETE = "DELETE FROM ALUMNO WHERE ID_ALUMNO = ?";
    public String GETALL = "SELECT * FROM ALUMNO ORDER BY ID_ALUMNO";
    public String GETONE = "SELECT ID_ALUMNO, DNI, APELLIDO, NOMBRES, FECHA_NACIMIENTO, TELEFONO FROM ALUMNO WHERE DNI = ?";
    private Connection conn;


    public AlumnoDao(Connection conn) {
        this.conn = conn;
    }
    @Override
    public void guardar(Alumno a) throws DAOException {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(INSERT);

            ps.setInt(1, a.getID_ALUMNO());
            ps.setString(2, a.getDNI());
            ps.setString(3, a.getAPELLIDO());
            ps.setString(4, a.getNOMBRES());
            ps.setDate(5, a.getFECHA_NACIMIENTO());
            ps.setString(6, a.getTELEFONO());

            if (ps.executeUpdate() > 0) {
                System.out.println("Alumno registrado exitosamente.");
            } else {
                System.out.println("Error al registrar alumno.");
            }

        } catch (SQLException ex) {
            throw new DAOException("error en sql ", ex);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException("error en sql ", e);
                }
            }
        }
    }


    @Override
    public void actualizar(Alumno a) {

    }

    @Override
    public void eliminar(Alumno id) {

    }

    @Override
    public List<Alumno> listar() {
        return List.of();
    }

    @Override
    public Alumno obtener(Long id) {
        return null;
    }
}
