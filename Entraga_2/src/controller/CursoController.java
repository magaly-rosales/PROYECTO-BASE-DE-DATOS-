package controller;
import model.Curso;
import services.DatabaseConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class CursoController {

    private ArrayList<Curso> cursos = new ArrayList<>();
    private DatabaseConnector connection = new DatabaseConnector();
    private ResultSet rs;

    public void agregarCurso(Scanner scanner){
        System.out.print("Ingresa el código del curso: ");
        int ID_CURSO = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingresa el nombre del curso: ");
        String NOMBRE_CURSO = scanner.nextLine();
        System.out.print("Ingresa el crédito: ");
        double CREDITO = Double.parseDouble(scanner.nextLine());

        Curso curso = new Curso(ID_CURSO, NOMBRE_CURSO, CREDITO);
        cursos.add(curso);

        try {
            String sql = "INSERT INTO CURSO(ID_CURSO, NOMBRE_CURSO, CREDITO) VALUES(?, ?, ?)";
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, ID_CURSO);
            ps.setString(2, NOMBRE_CURSO);
            ps.setDouble(3, CREDITO);

            int filasAfectadas = ps.executeUpdate();
            if(filasAfectadas > 0){
                System.out.println("Se ha registrado un nuevo curso");
            } else {
                System.out.println("Error al registrar el curso");
            }
            ps.close();
        } catch (SQLException e){
            System.out.println("Error SQL al agregar curso: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error de formato: Asegúrate de ingresar números para ID y Crédito.");
        }
    }

    public void imprimirTodosLosCursos(){
        try {
            String sql = "SELECT * FROM CURSO ORDER BY ID_CURSO";
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();

            System.out.println("\n--- Lista de Cursos ---");
            while (rs.next()){
                int ID_CURSO = rs.getInt("ID_CURSO");
                String NOMBRE_CURSO = rs.getString("NOMBRE_CURSO");
                double CREDITO = rs.getDouble("CREDITO");

                System.out.println("ID: " + ID_CURSO + " - Nombre: " + NOMBRE_CURSO + " - Crédito: " + CREDITO);
            }
            System.out.println("-----------------------\n");

            ps.close();
            rs.close();
        } catch (SQLException e){
            System.out.println("Error SQL al imprimir cursos: " + e.getMessage());
        }
    }

    public void actualizarCurso(Scanner scanner){
        try {
            System.out.print("Ingresa el ID del curso a actualizar: ");
            int ID_CURSO = Integer.parseInt(scanner.nextLine());
            System.out.print("Ingresa el nuevo nombre del curso: ");
            String nuevoNombre = scanner.nextLine();
            System.out.print("Ingresa el nuevo crédito: ");
            double nuevoCredito = Double.parseDouble(scanner.nextLine());

            String sql = "UPDATE CURSO SET NOMBRE_CURSO = ?, CREDITO = ? WHERE ID_CURSO = ?";
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setString(1, nuevoNombre);
            ps.setDouble(2, nuevoCredito);
            ps.setInt(3, ID_CURSO);

            int filasAfectadas = ps.executeUpdate();
            if(filasAfectadas > 0){
                System.out.println("Curso actualizado correctamente");
            } else {
                System.out.println("No se encontró el curso");
            }

            ps.close();
        } catch (SQLException e){
            System.out.println("Error SQL al actualizar curso: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error de formato: Ingresa ID y Crédito numéricos.");
        }
    }

    public void eliminarCurso(Scanner scanner){
        try {
            System.out.print("Ingresa el ID del curso a eliminar: ");
            int ID_CURSO = Integer.parseInt(scanner.nextLine());

            String sql = "DELETE FROM CURSO WHERE ID_CURSO = ?";
            PreparedStatement ps = connection.getConnection().prepareStatement(sql);
            ps.setInt(1, ID_CURSO);

            int filasAfectadas = ps.executeUpdate();
            if(filasAfectadas > 0){
                System.out.println("Curso eliminado correctamente");
            } else {
                System.out.println("No se encontró el curso");
            }

            ps.close();
        } catch (SQLException e){
            System.out.println("Error SQL al eliminar curso: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error de formato: Ingresa un ID numérico.");
        }
    }

    public void menuCursos(Scanner scanner){
        boolean continuar = true;
        while(continuar){
            System.out.println("\n--- Gestión de Cursos ---");
            System.out.println("1. Agregar Curso");
            System.out.println("2. Mostrar Cursos");
            System.out.println("3. Actualizar Curso");
            System.out.println("4. Eliminar Curso");
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
                    agregarCurso(scanner);
                    break;
                case 2 :
                    imprimirTodosLosCursos();
                    break;
                case 3 :
                    actualizarCurso(scanner);
                    break;
                case 4 :
                    eliminarCurso(scanner);
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