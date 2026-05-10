import java.time.LocalDate;

/**
 * Classe Prestec
 * Representa un préstec d'un llibre a un usuari. Guarda la data de préstec,
 * la data de retorn prevista (2 setmanes després) i la data real de retorn
 * un cop el llibre torna.
 *
 * Autor: company1 (branca: company1)
 */
public class Prestec {
    private Usuari usuari;
    private Llibre llibre;
    private LocalDate dataPrestec;
    private LocalDate dataRetornPrevista;
    private LocalDate dataRetornReal;
    private boolean actiu;

    public Prestec(Usuari usuari, Llibre llibre, LocalDate dataPrestec) {
        this.usuari = usuari;
        this.llibre = llibre;
        this.dataPrestec = dataPrestec;
        this.dataRetornPrevista = dataPrestec.plusWeeks(2);
        this.dataRetornReal = null;
        this.actiu = true;
    }

    // --- Getters ---
    public Usuari getUsuari() { return usuari; }
    public Llibre getLlibre() { return llibre; }
    public LocalDate getDataPrestec() { return dataPrestec; }
    public LocalDate getDataRetornPrevista() { return dataRetornPrevista; }
    public LocalDate getDataRetornReal() { return dataRetornReal; }
    public boolean esActiu() { return actiu; }

    /** Marca el préstec com a retornat avui. */
    public void marcarRetornat() {
        this.actiu = false;
        this.dataRetornReal = LocalDate.now();
    }

    @Override
    public String toString() {
        String estat = actiu
                ? "ACTIU (retorn previst: " + dataRetornPrevista + ")"
                : "RETORNAT el " + dataRetornReal;
        return "\"" + llibre.getTitol() + "\" - prestat el " + dataPrestec + " - " + estat;
    }
}
