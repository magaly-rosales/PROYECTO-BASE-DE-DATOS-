package controller;

import dao.AlumnoDao;
import dao.DAOException;
import model.Alumno;
import services.DatabaseConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Date;

public class AlumnoController {


    private DatabaseConnector connection = new DatabaseConnector();
    private ResultSet rs;

    private boolean existeDni(String dni) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ALUMNO WHERE DNI = ?";
        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }



    public void agregarAlumno(Scanner scanner) {
        try {
            System.out.print("Ingresa ID del alumno: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Ingresa DNI: ");
            String dni = scanner.nextLine();

            // Verificación de unicidad de DNI
            if (existeDni(dni)) {
                System.out.println("ADVERTENCIA: El DNI " + dni + " ya está registrado en la base de datos.");
                return; // Detiene la ejecución aquí y evita el error ORA-00001
            }

            System.out.print("Ingresa Apellido: ");
            String apellido = scanner.nextLine();
            System.out.print("Ingresa Nombres: ");
            String nombres = scanner.nextLine();
            System.out.print("Ingresa Fecha de Nacimiento (YYYY-MM-DD): ");
            Date fechaNacimiento = Date.valueOf(scanner.nextLine());
            System.out.print("Ingresa Teléfono: ");
            String telefono = scanner.nextLine();

            Alumno alumno = new Alumno(id, dni, apellido, nombres, fechaNacimiento, telefono);
            new AlumnoDao(connection.getConnection()).guardar(alumno);

            //String sql = "INSERT INTO ALUMNO(ID_ALUMNO, DNI, APELLIDO, NOMBRES, FECHA_NACIMIENTO, TELEFONO) VALUES(?, ?, ?, ?, ?, ?)";
            //PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            //ps.setInt(1, id);
            //ps.setString(2, dni);
            //ps.setString(3, apellido);
            //ps.setString(4, nombres);
            //ps.setDate(5, fechaNacimiento);
            //ps.setString(6, telefono);
//
            //if (ps.executeUpdate() > 0) {
            //    System.out.println("Alumno registrado exitosamente.");
            //} else {
            //    System.out.println("Error al registrar alumno.");
            //}
            //ps.close();
        } catch (SQLException e) {
            System.out.println("Error SQL al agregar alumno: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error de formato: Asegúrate de ingresar un ID numérico válido.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error de formato: La fecha de nacimiento no está en formato YYYY-MM-DD.");
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }



    public void imprimirTodosLosAlumnos(){
        try {
            String sql = "SELECT * FROM ALUMNO ORDER BY ID_ALUMNO";
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();

            System.out.println("\n--- Lista de Alumnos ---");
            while (rs.next()){
                int ID_ALUMNO = rs.getInt("ID_ALUMNO");
                String DNI = rs.getString("DNI");
                String APELLIDO = rs.getString("APELLIDO");
                String NOMBRES = rs.getString("NOMBRES");

                System.out.println("ID: " + ID_ALUMNO + " - DNI: " + DNI + " - Nombre: " + APELLIDO + " " + NOMBRES);
            }
            System.out.println("------------------------\n");

            ps.close();
            rs.close();
        } catch (SQLException e){
            System.out.println("Error SQL al imprimir alumnos: " + e.getMessage());
        }
    }

    public void actualizarAlumno(Scanner scanner){
        try {
            System.out.print("Ingresa el ID del alumno a actualizar: ");
            int ID_ALUMNO = Integer.parseInt(scanner.nextLine());
            System.out.print("Ingresa el nuevo Teléfono: ");
            String nuevoTelefono = scanner.nextLine();
            System.out.print("Ingresa el nuevo DNI: ");
            String nuevoDNI = scanner.nextLine();

            String sql = "UPDATE ALUMNO SET TELEFONO = ?, DNI = ? WHERE ID_ALUMNO = ?";
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setString(1, nuevoTelefono);
            ps.setString(2, nuevoDNI);
            ps.setInt(3, ID_ALUMNO);

            if(ps.executeUpdate() > 0){
                System.out.println("Alumno actualizado correctamente.");
            } else {
                System.out.println("No se encontró el alumno.");
            }
            ps.close();
        } catch (SQLException e){
            System.out.println("Error SQL al actualizar alumno: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ingresa un ID numérico válido.");
        }
    }

    public void eliminarAlumno(Scanner scanner){
        try {
            System.out.print("Ingresa el ID del alumno a eliminar: ");
            int ID_ALUMNO = Integer.parseInt(scanner.nextLine());

            String sql = "DELETE FROM ALUMNO WHERE ID_ALUMNO = ?";
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, ID_ALUMNO);

            if(ps.executeUpdate() > 0){
                System.out.println("Alumno eliminado correctamente.");
            } else {
                System.out.println("No se encontró el alumno.");
            }
            ps.close();
        } catch (SQLException e){
            System.out.println("Error SQL al eliminar alumno: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ingresa un ID numérico válido.");
        }
    }

    public void menuAlumnos(Scanner scanner) {
        boolean continuar = true;
        while(continuar){
            System.out.println("\n--- Gestión de Alumnos ---");
            System.out.println("1. Agregar Alumno");
            System.out.println("2. Mostrar Alumnos");
            System.out.println("3. Actualizar Alumno");
            System.out.println("4. Eliminar Alumno");
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
                    agregarAlumno(scanner);
                    break;
                case 2 :
                    imprimirTodosLosAlumnos();
                    break;
                case 3 :
                    actualizarAlumno(scanner);
                    break;
                case 4 :
                    eliminarAlumno(scanner);
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