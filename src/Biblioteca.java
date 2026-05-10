import java.util.ArrayList;
import java.util.List;

/**
 * Classe Biblioteca
 * Conté la col·lecció de llibres i les operacions per gestionar-la:
 * afegir, modificar, eliminar, llistar i cercar (per títol o autor,
 * ignorant accents).
 *
 * Autor: dario (branca: dario)
 */
public class Biblioteca {
    private List<Llibre> llibres;
    private int seguentId;

    public Biblioteca() {
        this.llibres = new ArrayList<>();
        this.seguentId = 1;
    }

    // AFEGIR
    public Llibre afegirLlibre(String titol, String autor, String categoria) {
        Llibre llibre = new Llibre(seguentId++, titol, autor, categoria);
        llibres.add(llibre);
        System.out.println("Llibre afegit: " + llibre);
        return llibre;
    }

    //  MODIFICAR
    /**
     * Modifica el llibre amb l'id donat. Si algun camp està buit, no es canvia.
     */
    public boolean modificarLlibre(int id, String nouTitol, String nouAutor, String novaCategoria) {
        Llibre l = buscarPerId(id);
        if (l == null) return false;
        if (nouTitol != null && !nouTitol.isEmpty()) l.setTitol(nouTitol);
        if (nouAutor != null && !nouAutor.isEmpty()) l.setAutor(nouAutor);
        if (novaCategoria != null && !novaCategoria.isEmpty()) l.setCategoria(novaCategoria);
        return true;
    }

    //  ELIMINAR
    public boolean eliminarLlibre(int id) {
        Llibre l = buscarPerId(id);
        if (l == null) return false;
        if (l.esPrestat()) {
            System.out.println("No es pot eliminar: el llibre està prestat.");
            return false;
        }
        llibres.remove(l);
        return true;
    }

    //  LLISTAR
    public void llistarLlibres() {
        if (llibres.isEmpty()) {
            System.out.println("No hi ha cap llibre a la biblioteca.");
            return;
        }
        System.out.println("Llistat de llibres");
        for (Llibre l : llibres) {
            System.out.println(l);
        }
    }

    // CERCAR 
    public Llibre buscarPerId(int id) {
        for (Llibre l : llibres) {
            if (l.getId() == id) return l;
        }
        return null;
    }

    /** Cerca exacta per títol (ignorant majúscules i accents). */
    public Llibre buscarLlibre(String titol) {
        for (Llibre l : llibres) {
            if (NormalitzadorText.normalitzar(l.getTitol())
                    .equals(NormalitzadorText.normalitzar(titol))) {
                return l;
            }
        }
        return null;
    }

    /** Cerca per fragment de títol (ignorant accents). */
    public List<Llibre> cercarPerTitol(String text) {
        List<Llibre> resultat = new ArrayList<>();
        for (Llibre l : llibres) {
            if (NormalitzadorText.conte(l.getTitol(), text)) {
                resultat.add(l);
            }
        }
        return resultat;
    }

    /** Cerca per fragment d'autor (ignorant accents). */
    public List<Llibre> cercarPerAutor(String text) {
        List<Llibre> resultat = new ArrayList<>();
        for (Llibre l : llibres) {
            if (NormalitzadorText.conte(l.getAutor(), text)) {
                resultat.add(l);
            }
        }
        return resultat;
    }

    /** Llista llibres d'una categoria concreta. */
    public List<Llibre> cercarPerCategoria(String categoria) {
        List<Llibre> resultat = new ArrayList<>();
        for (Llibre l : llibres) {
            if (NormalitzadorText.normalitzar(l.getCategoria())
                    .equals(NormalitzadorText.normalitzar(categoria))) {
                resultat.add(l);
            }
        }
        return resultat;
    }

    //DISPONIBILITAT
    public boolean comprovarDisponibilitat(int id) {
        Llibre l = buscarPerId(id);
        return l != null && !l.esPrestat();
    }

    // Getters
    public List<Llibre> getLlibres() { return llibres; }
}
