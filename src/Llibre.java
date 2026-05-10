/**
 * Classe Llibre
 * Representa un llibre de la biblioteca amb les seves dades bàsiques
 * i el seu estat de préstec.
 *
 * Autor: dario (branca: dario)
 */
public class Llibre {
    private int id;
    private String titol;
    private String autor;
    private String categoria;
    private boolean prestat;

    public Llibre(int id, String titol, String autor, String categoria) {
        this.id = id;
        this.titol = titol;
        this.autor = autor;
        this.categoria = categoria;
        this.prestat = false;
    }

    //Getters
    public int getId() { return id; }
    public String getTitol() { return titol; }
    public String getAutor() { return autor; }
    public String getCategoria() { return categoria; }
    public boolean esPrestat() { return prestat; }

    //Setters (per modificar)
    public void setTitol(String titol) { this.titol = titol; }
    public void setAutor(String autor) { this.autor = autor; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    //Lògica de préstec
    public void prestar() { this.prestat = true; }
    public void retornar() { this.prestat = false; }

    @Override
    public String toString() {
        return "[" + id + "] \"" + titol + "\" - " + autor +
               " (" + categoria + ") " +
               (prestat ? "[En préstec]" : "[Disponible]");
    }
}
