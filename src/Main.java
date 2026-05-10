import java.util.List;
import java.util.Scanner;

/**
 * Classe Main
 * Punt d'entrada del programa. Mostra un menú per teclat amb totes
 * les opcions de gestió de la biblioteca.
 *
 * Integració conjunta dels dos membres del grup (branca: main).
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final Biblioteca biblioteca = new Biblioteca();
    private static final GestorBiblioteca gestor = new GestorBiblioteca(biblioteca);

    public static void main(String[] args) {
        carregarDadesExemple();

        int opcio;
        do {
            mostrarMenu();
            opcio = llegirInt("Tria una opcio: ");
            System.out.println();
            switch (opcio) {
                case 1:  afegirLlibre();           break;
                case 2:  modificarLlibre();        break;
                case 3:  eliminarLlibre();         break;
                case 4:  biblioteca.llistarLlibres(); break;
                case 5:  cercarLlibre();           break;
                case 6:  afegirUsuari();           break;
                case 7:  modificarUsuari();        break;
                case 8:  eliminarUsuari();         break;
                case 9:  gestor.llistarUsuaris();  break;
                case 10: cercarUsuari();           break;
                case 11: prestarLlibre();          break;
                case 12: retornarLlibre();         break;
                case 13: consultarDisponibilitat();break;
                case 14: historialPrestecs();      break;
                case 15: gestor.mostrarEstadistiques(); break;
                case 0:  System.out.println("Sortint... Adeu!"); break;
                default: System.out.println("Opcio no valida."); break;
            }
        } while (opcio != 0);
    }

    // =========================================================
    //                       MENU
    // =========================================================
    private static void mostrarMenu() {
        System.out.println();
        System.out.println("========= GESTIO DE BIBLIOTECA =========");
        System.out.println("  1. Afegir llibre");
        System.out.println("  2. Modificar llibre");
        System.out.println("  3. Eliminar llibre");
        System.out.println("  4. Llistar llibres");
        System.out.println("  5. Cercar llibre");
        System.out.println("  6. Afegir usuari");
        System.out.println("  7. Modificar usuari");
        System.out.println("  8. Eliminar usuari");
        System.out.println("  9. Llistar usuaris");
        System.out.println(" 10. Cercar usuari");
        System.out.println(" 11. Prestar llibre");
        System.out.println(" 12. Retornar llibre");
        System.out.println(" 13. Consultar disponibilitat d'un llibre");
        System.out.println(" 14. Historial de prestecs d'un usuari");
        System.out.println(" 15. Estadistiques");
        System.out.println("  0. Sortir");
        System.out.println("========================================");
    }

    // =========================================================
    //                  UTILITATS DE LECTURA
    // =========================================================
    private static int llegirInt(String missatge) {
        System.out.print(missatge);
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Numero no valid. " + missatge);
        }
        int n = sc.nextInt();
        sc.nextLine();
        return n;
    }

    private static String llegirText(String missatge) {
        System.out.print(missatge);
        return sc.nextLine();
    }

    // =========================================================
    //                       LLIBRES
    // =========================================================
    private static void afegirLlibre() {
        String titol = llegirText("Titol: ");
        String autor = llegirText("Autor: ");
        String cat   = llegirText("Categoria: ");
        biblioteca.afegirLlibre(titol, autor, cat);
    }

    private static void modificarLlibre() {
        int id = llegirInt("ID del llibre: ");
        System.out.println("(Deixa buit per no canviar)");
        String titol = llegirText("Nou titol: ");
        String autor = llegirText("Nou autor: ");
        String cat   = llegirText("Nova categoria: ");
        if (biblioteca.modificarLlibre(id, titol, autor, cat))
            System.out.println("Llibre modificat correctament.");
        else
            System.out.println("No s'ha trobat el llibre.");
    }

    private static void eliminarLlibre() {
        int id = llegirInt("ID del llibre: ");
        if (biblioteca.eliminarLlibre(id))
            System.out.println("Llibre eliminat.");
        else
            System.out.println("No s'ha pogut eliminar.");
    }

    private static void cercarLlibre() {
        System.out.println("1. Cercar per titol");
        System.out.println("2. Cercar per autor");
        int op = llegirInt("Opcio: ");
        String text = llegirText("Text a cercar (s'ignoren accents): ");
        List<Llibre> resultat;
        if (op == 1)      resultat = biblioteca.cercarPerTitol(text);
        else if (op == 2) resultat = biblioteca.cercarPerAutor(text);
        else { System.out.println("Opcio no valida."); return; }

        if (resultat.isEmpty()) System.out.println("Cap resultat.");
        else for (Llibre l : resultat) System.out.println(l);
    }

    // =========================================================
    //                       USUARIS
    // =========================================================
    private static void afegirUsuari() {
        String nom = llegirText("Nom: ");
        String dni = llegirText("DNI: ");
        gestor.afegirUsuari(nom, dni);
    }

    private static void modificarUsuari() {
        int id = llegirInt("ID de l'usuari: ");
        System.out.println("(Deixa buit per no canviar)");
        String nom = llegirText("Nou nom: ");
        String dni = llegirText("Nou DNI: ");
        if (gestor.modificarUsuari(id, nom, dni))
            System.out.println("Usuari modificat correctament.");
        else
            System.out.println("No s'ha trobat l'usuari.");
    }

    private static void eliminarUsuari() {
        int id = llegirInt("ID de l'usuari: ");
        if (gestor.eliminarUsuari(id))
            System.out.println("Usuari eliminat.");
        else
            System.out.println("No s'ha pogut eliminar.");
    }

    private static void cercarUsuari() {
        String text = llegirText("Nom a cercar (s'ignoren accents): ");
        List<Usuari> resultat = gestor.cercarUsuariPerNom(text);
        if (resultat.isEmpty()) System.out.println("Cap resultat.");
        else for (Usuari u : resultat) System.out.println(u);
    }

    // =========================================================
    //                       PRESTECS
    // =========================================================
    private static void prestarLlibre() {
        int idU = llegirInt("ID de l'usuari: ");
        int idL = llegirInt("ID del llibre: ");
        gestor.prestarLlibre(idU, idL);
    }

    private static void retornarLlibre() {
        int idU = llegirInt("ID de l'usuari: ");
        int idL = llegirInt("ID del llibre: ");
        gestor.retornarLlibre(idU, idL);
    }

    private static void consultarDisponibilitat() {
        int id = llegirInt("ID del llibre: ");
        Llibre l = biblioteca.buscarPerId(id);
        if (l == null) { System.out.println("Llibre no trobat."); return; }
        System.out.println(l);
        System.out.println(l.esPrestat()
                ? "Aquest llibre NO esta disponible (en prestec)."
                : "Aquest llibre esta DISPONIBLE.");
    }

    private static void historialPrestecs() {
        int id = llegirInt("ID de l'usuari: ");
        gestor.historialUsuari(id);
    }

    // =========================================================
    //                   DADES D'EXEMPLE
    // =========================================================
    private static void carregarDadesExemple() {
        biblioteca.afegirLlibre("1984", "George Orwell", "Novel.la");
        biblioteca.afegirLlibre("El petit princep", "Antoine de Saint-Exupery", "Novel.la");
        biblioteca.afegirLlibre("Sapiens", "Yuval Noah Harari", "Historia");
        biblioteca.afegirLlibre("Cosmos", "Carl Sagan", "Ciencia");

        gestor.afegirUsuari("Carla Mas", "12345678A");
        gestor.afegirUsuari("Joan Puig", "87654321B");
        System.out.println();
    }
}
