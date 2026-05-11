import java.util.ArrayList;
import java.util.List;
public class Usuari {
    private int id;
    private String nom;
    private String dni;
    private List<Llibre> llibresPrestats;
    private List<Prestec> historial;

    public Usuari(int id, String nom, String dni) {
        this.id = id;
        this.nom = nom;
        this.dni = dni;
        this.llibresPrestats = new ArrayList<>();
        this.historial = new ArrayList<>();
    }

    // --- Getters ---
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getDni() { return dni; }
    public List<Llibre> getLlibresPrestats() { return llibresPrestats; }
    public List<Prestec> getHistorial() { return historial; }

    // --- Setters ---
    public void setNom(String nom) { this.nom = nom; }
    public void setDni(String dni) { this.dni = dni; }

    // --- Operacions ---
    public void afegirLlibre(Llibre llibre) {
        llibresPrestats.add(llibre);
    }

    public void retornarLlibre(Llibre llibre) {
        llibresPrestats.remove(llibre);
    }

    public void afegirAlHistorial(Prestec p) {
        historial.add(p);
    }

    //el tostring que no falteeh
    @Override
    public String toString() {
        return "[" + id + "] " + nom + " (DNI: " + dni + ")"
                + " - llibres en prestec: " + llibresPrestats.size();
    }
}
