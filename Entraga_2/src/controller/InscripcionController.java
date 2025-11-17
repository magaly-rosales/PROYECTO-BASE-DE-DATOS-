package controller;

import services.DatabaseConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Date;
import java.time.LocalDate;

public class InscripcionController {

    private DatabaseConnector connection = new DatabaseConnector();
    private ResultSet rs;

    private boolean existeEntidad(int id, String tabla, String campoId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + tabla + " WHERE " + campoId + " = ?";
        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
                return false;
            }
        }
    }

    public void agregarInscripcion(Scanner scanner) {
        try {
            System.out.print("Ingresa ID de la Inscripción: ");
            int ID_INSCRIPCIONES = Integer.parseInt(scanner.nextLine());
            System.out.print("Ingresa ID del Alumno: ");
            int ID_ALUMNO = Integer.parseInt(scanner.nextLine());
            System.out.print("Ingresa ID del Curso: ");
            int ID_CURSO = Integer.parseInt(scanner.nextLine());

            if (!existeEntidad(ID_ALUMNO, "ALUMNO", "ID_ALUMNO")) {
                System.out.println("Error de Integridad: El Alumno con ID " + ID_ALUMNO + " no existe.");
                return;
            }
            if (!existeEntidad(ID_CURSO, "CURSO", "ID_CURSO")) {
                System.out.println("Error de Integridad: El Curso con ID " + ID_CURSO + " no existe.");
                return;
            }

            Date FECHA_INSCRIPCION = Date.valueOf(LocalDate.now());

            String sql = "INSERT INTO INSCRIPCIONES(ID_INSCRIPCIONES, ID_ALUMNO, ID_CURSO, FECHA_INSCRIPCION) VALUES(?, ?, ?, ?)";
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, ID_INSCRIPCIONES);
            ps.setInt(2, ID_ALUMNO);
            ps.setInt(3, ID_CURSO);
            ps.setDate(4, FECHA_INSCRIPCION);

            if (ps.executeUpdate() > 0) {
                System.out.println("Inscripción registrada exitosamente. Fecha: " + FECHA_INSCRIPCION);
            } else {
                System.out.println("Error al registrar inscripción.");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error SQL al agregar inscripción: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error de formato: Asegúrate de ingresar números enteros para los IDs.");
        }
    }

    public void imprimirTodasLasInscripciones(){
        try {
            String sql = "SELECT " +
                    "I.ID_INSCRIPCIONES, " +
                    "A.NOMBRES AS NOMBRE_ALUMNO, " +
                    "A.APELLIDO AS APELLIDO_ALUMNO, " +
                    "C.NOMBRE_CURSO, " +
                    "I.FECHA_INSCRIPCION " +
                    "FROM INSCRIPCIONES I " +
                    "INNER JOIN ALUMNO A ON I.ID_ALUMNO = A.ID_ALUMNO " +
                    "INNER JOIN CURSO C ON I.ID_CURSO = C.ID_CURSO " +
                    "ORDER BY I.ID_INSCRIPCIONES";

            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();

            System.out.println("\n--- Lista de Inscripciones ---");
            while (rs.next()){
                int ID_INSCRIPCIONES = rs.getInt("ID_INSCRIPCIONES");
                String NOMBRE_ALUMNO = rs.getString("NOMBRE_ALUMNO");
                String APELLIDO_ALUMNO = rs.getString("APELLIDO_ALUMNO");
                String NOMBRE_CURSO = rs.getString("NOMBRE_CURSO");
                Date FECHA_INSCRIPCION = rs.getDate("FECHA_INSCRIPCION");

                System.out.printf("ID Inscripción: %d | Alumno: %s %s | Curso: %s | Fecha: %s\n",
                        ID_INSCRIPCIONES, APELLIDO_ALUMNO, NOMBRE_ALUMNO, NOMBRE_CURSO, FECHA_INSCRIPCION);
            }
            System.out.println("------------------------------\n");

            ps.close();
            rs.close();
        } catch (SQLException e){
            System.out.println("Error SQL al imprimir inscripciones (JOIN): " + e.getMessage());
        }
    }

    public void actualizarInscripcion(Scanner scanner){
        try {
            System.out.print("Ingresa el ID de la inscripción a actualizar: ");
            int ID_INSCRIPCIONES = Integer.parseInt(scanner.nextLine());
            System.out.print("Ingresa la nueva Fecha de Inscripción (YYYY-MM-DD): ");
            String fechaStr = scanner.nextLine();
            Date nuevaFecha = Date.valueOf(fechaStr);

            String sql = "UPDATE INSCRIPCIONES SET FECHA_INSCRIPCION = ? WHERE ID_INSCRIPCIONES = ?";
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setDate(1, nuevaFecha);
            ps.setInt(2, ID_INSCRIPCIONES);

            if(ps.executeUpdate() > 0){
                System.out.println("Inscripción actualizada correctamente.");
            } else {
                System.out.println("No se encontró la inscripción.");
            }
            ps.close();
        } catch (SQLException e){
            System.out.println("Error SQL al actualizar inscripción: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error de formato: Ingresa ID numérico válido.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error de formato: La fecha debe estar en formato YYYY-MM-DD.");
        }
    }

    public void eliminarInscripcion(Scanner scanner){
        try {
            System.out.print("Ingresa el ID de la inscripción a eliminar: ");
            int ID_INSCRIPCIONES = Integer.parseInt(scanner.nextLine());

            String sql = "DELETE FROM INSCRIPCIONES WHERE ID_INSCRIPCIONES = ?";
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, ID_INSCRIPCIONES);

            if(ps.executeUpdate() > 0){
                System.out.println("Inscripción eliminada correctamente.");
            } else {
                System.out.println("No se encontró la inscripción.");
            }
            ps.close();
        } catch (SQLException e){
            System.out.println("Error SQL al eliminar inscripción: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ingresa un ID numérico válido.");
        }
    }

    public void menuInscripciones(Scanner scanner) {
        boolean continuar = true;
        while(continuar){
            System.out.println("\n--- Gestión de Inscripciones ---");
            System.out.println("1. Registrar Inscripción (Fecha Automática)");
            System.out.println("2. Mostrar Inscripciones");
            System.out.println("3. Actualizar Inscripción");
            System.out.println("4. Eliminar Inscripción");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Selecciona una opción: ");
            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número válido.");
                continue;
            }

            switch(opcion){
                case 1 :
                    agregarInscripcion(scanner);
                    break;
                case 2 :
                    imprimirTodasLasInscripciones();
                    break;
                case 3 :
                    actualizarInscripcion(scanner);
                    break;
                case 4 :
                    eliminarInscripcion(scanner);
                    break;
                case 5 :
                    continuar = false;
                    break;
                default :
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }
}