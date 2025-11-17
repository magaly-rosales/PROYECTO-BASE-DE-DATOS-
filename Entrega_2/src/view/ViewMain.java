package view;

import controller.AlumnoController;
import controller.CursoController;
import controller.InscripcionController;
import java.util.Scanner;

public class ViewMain {

    CursoController cursosController = new CursoController();
    AlumnoController alumnosController = new AlumnoController();
    InscripcionController inscripcionController = new InscripcionController();
    Scanner scanner = new Scanner(System.in);

    public void ejecutando() {
        boolean execute = true;
        while (execute) {
            System.out.println("\n--- INSTITUCIÓN EDUCATIVA ---");
            System.out.println("1. Alumnos");
            System.out.println("2. Curso");
            System.out.println("3. Inscripciones");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");

            int option;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número válido.");
                continue;
            }

            switch (option) {
                case 1:
                    alumnosController.menuAlumnos(scanner);
                    break;
                case 2:
                    cursosController.menuCursos(scanner);
                    break;

                case 3:
                    inscripcionController.menuInscripciones(scanner);
                    break;

                case 4:
                    System.out.println("Fin del programa");
                    execute = false;
                    break;
                default:
                    System.out.println("Ingresa una opción válida");
                    break;
            }
        }
        scanner.close();
    }
}
