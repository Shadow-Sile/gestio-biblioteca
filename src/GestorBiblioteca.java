import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe GestorBiblioteca
 * Gestiona els usuaris i els préstecs de la biblioteca. Controla l'estoc
 * (un llibre no es pot prestar dues vegades alhora) i el màxim de llibres
 * que un usuari pot tenir en préstec a la vegada.
 *
 * Genera estadístiques: llibre més prestat, usuari més actiu, préstecs
 * per categoria, etc.
 *
 * Autor: company1 (branca: company1)
 */
public class GestorBiblioteca {

    /** Màxim de llibres que un usuari pot tenir alhora. */
    public static final int MAX_LLIBRES_PER_USUARI = 3;

    private Biblioteca biblioteca;
    private List<Usuari> usuaris;
    private List<Prestec> prestecs;
    private int seguentIdUsuari;

    public GestorBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        this.usuaris = new ArrayList<>();
        this.prestecs = new ArrayList<>();
        this.seguentIdUsuari = 1;
    }

    // ============== GESTIÓ D'USUARIS ==============
    public Usuari afegirUsuari(String nom, String dni) {
        Usuari u = new Usuari(seguentIdUsuari++, nom, dni);
        usuaris.add(u);
        System.out.println("Usuari afegit: " + u);
        return u;
    }

    public boolean modificarUsuari(int id, String nouNom, String nouDni) {
        Usuari u = buscarUsuariPerId(id);
        if (u == null) return false;
        if (nouNom != null && !nouNom.isEmpty()) u.setNom(nouNom);
        if (nouDni != null && !nouDni.isEmpty()) u.setDni(nouDni);
        return true;
    }

    public boolean eliminarUsuari(int id) {
        Usuari u = buscarUsuariPerId(id);
        if (u == null) return false;
        if (!u.getLlibresPrestats().isEmpty()) {
            System.out.println("No es pot eliminar: l'usuari té llibres en préstec.");
            return false;
        }
        usuaris.remove(u);
        return true;
    }

    public Usuari buscarUsuariPerId(int id) {
        for (Usuari u : usuaris) {
            if (u.getId() == id) return u;
        }
        return null;
    }

    public List<Usuari> cercarUsuariPerNom(String text) {
        List<Usuari> resultat = new ArrayList<>();
        for (Usuari u : usuaris) {
            if (NormalitzadorText.conte(u.getNom(), text)) {
                resultat.add(u);
            }
        }
        return resultat;
    }

    public void llistarUsuaris() {
        if (usuaris.isEmpty()) {
            System.out.println("No hi ha cap usuari registrat.");
            return;
        }
        System.out.println("--- Llistat d'usuaris ---");
        for (Usuari u : usuaris) {
            System.out.println(u);
        }
    }

    // ============== PRÉSTECS ==============
    public void prestarLlibre(int idUsuari, int idLlibre) {
        Usuari u = buscarUsuariPerId(idUsuari);
        Llibre l = biblioteca.buscarPerId(idLlibre);

        if (u == null) { System.out.println("Usuari no trobat."); return; }
        if (l == null) { System.out.println("Llibre no trobat."); return; }
        if (l.esPrestat()) { System.out.println("Aquest llibre ja està prestat."); return; }

        if (u.getLlibresPrestats().size() >= MAX_LLIBRES_PER_USUARI) {
            System.out.println("L'usuari ja té el màxim de "
                + MAX_LLIBRES_PER_USUARI + " llibres en préstec.");
            return;
        }

        l.prestar();
        Prestec p = new Prestec(u, l, LocalDate.now());
        prestecs.add(p);
        u.afegirLlibre(l);
        u.afegirAlHistorial(p);
        System.out.println(u.getNom() + " ha agafat: \"" + l.getTitol() + "\""
                + " (retorn previst: " + p.getDataRetornPrevista() + ")");
    }

    public void retornarLlibre(int idUsuari, int idLlibre) {
        Usuari u = buscarUsuariPerId(idUsuari);
        Llibre l = biblioteca.buscarPerId(idLlibre);

        if (u == null || l == null) {
            System.out.println("Usuari o llibre no trobat.");
            return;
        }
        if (!l.esPrestat()) {
            System.out.println("Aquest llibre no està prestat.");
            return;
        }

        // marcar el préstec actiu corresponent com a retornat
        for (Prestec p : prestecs) {
            if (p.esActiu() && p.getLlibre() == l && p.getUsuari() == u) {
                p.marcarRetornat();
                break;
            }
        }
        l.retornar();
        u.retornarLlibre(l);
        System.out.println(u.getNom() + " ha retornat: \"" + l.getTitol() + "\"");
    }

    /** Mostra l'historial complet de préstecs d'un usuari. */
    public void historialUsuari(int idUsuari) {
        Usuari u = buscarUsuariPerId(idUsuari);
        if (u == null) {
            System.out.println("Usuari no trobat.");
            return;
        }
        if (u.getHistorial().isEmpty()) {
            System.out.println(u.getNom() + " no té cap préstec a l'historial.");
            return;
        }
        System.out.println("--- Historial de " + u.getNom() + " ---");
        for (Prestec p : u.getHistorial()) {
            System.out.println(" - " + p);
        }
    }

    // ============== ESTADÍSTIQUES ==============
    public void mostrarEstadistiques() {
        System.out.println("========= ESTADISTIQUES =========");
        System.out.println("Total llibres a la biblioteca: " + biblioteca.getLlibres().size());
        System.out.println("Total usuaris registrats:      " + usuaris.size());
        System.out.println("Total preestecs realitzats:    " + prestecs.size());

        int actius = 0;
        for (Prestec p : prestecs) if (p.esActiu()) actius++;
        System.out.println("Préstecs actius ara mateix:    " + actius);

        // Llibre més prestat
        Map<String, Integer> compteLlibres = new HashMap<>();
        for (Prestec p : prestecs) {
            String t = p.getLlibre().getTitol();
            compteLlibres.put(t, compteLlibres.getOrDefault(t, 0) + 1);
        }
        if (!compteLlibres.isEmpty()) {
            String top = null;
            int max = 0;
            for (Map.Entry<String, Integer> e : compteLlibres.entrySet()) {
                if (e.getValue() > max) { max = e.getValue(); top = e.getKey(); }
            }
            System.out.println("Llibre mes prestat: \"" + top + "\" (" + max + " preestecs)");
        }

        // Usuari més actiu
        Map<String, Integer> compteUsuaris = new HashMap<>();
        for (Prestec p : prestecs) {
            String n = p.getUsuari().getNom();
            compteUsuaris.put(n, compteUsuaris.getOrDefault(n, 0) + 1);
        }
        if (!compteUsuaris.isEmpty()) {
            String top = null;
            int max = 0;
            for (Map.Entry<String, Integer> e : compteUsuaris.entrySet()) {
                if (e.getValue() > max) { max = e.getValue(); top = e.getKey(); }
            }
            System.out.println("Usuari mes actiu: " + top + " (" + max + " preestecs)");
        }

        // Préstecs per categoria
        Map<String, Integer> compteCat = new HashMap<>();
        for (Prestec p : prestecs) {
            String c = p.getLlibre().getCategoria();
            compteCat.put(c, compteCat.getOrDefault(c, 0) + 1);
        }
        if (!compteCat.isEmpty()) {
            System.out.println("Préstecs per categoria:");
            for (Map.Entry<String, Integer> e : compteCat.entrySet()) {
                System.out.println("  - " + e.getKey() + ": " + e.getValue());
            }
        }
        System.out.println("=================================");
    }

    // --- Getters ---
    public List<Usuari> getUsuaris() { return usuaris; }
    public List<Prestec> getPrestecs() { return prestecs; }
    public Biblioteca getBiblioteca() { return biblioteca; }
}
