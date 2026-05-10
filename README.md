# Gestió de Biblioteca

Projecte de l'**AEA3-1** del mòdul **M-ED (Entorns de desenvolupament)**.
Sistema de gestió d'una biblioteca implementat en **Java** amb POO,
desenvolupat en grup amb **Git i GitHub** (branques per persona).

---

## Membres del grup

| Membre    | Branca       | Classes                                              |
|-----------|--------------|------------------------------------------------------|
| **dario**    | `dario`      | `Llibre`, `NormalitzadorText`, `Biblioteca`         |
| **diego** | `diego`   | `Usuari`, `Prestec`, `GestorBiblioteca`             |

> La classe `Main` (menú per teclat) és la integració conjunta a la branca `main`.

---

## Funcionalitats

El programa mostra un menú amb **16 opcions**:

```
  1. Afegir llibre              9. Llistar usuaris
  2. Modificar llibre          10. Cercar usuari
  3. Eliminar llibre           11. Prestar llibre
  4. Llistar llibres           12. Retornar llibre
  5. Cercar llibre             13. Consultar disponibilitat
  6. Afegir usuari             14. Historial de préstecs d'un usuari
  7. Modificar usuari          15. Estadístiques
  8. Eliminar usuari            0. Sortir
```

### Millores implementades (de l'enunciat)

- Historial complet de préstecs per usuari.
- Comprovació de disponibilitat d'un llibre.
- Categories de llibres ("Novel·la", "Història", "Ciència"...).
- Estadístiques: llibre més prestat, usuari més actiu, préstecs per categoria.
- Cerca **ignorant accents i majúscules** (classe `NormalitzadorText`).
- Màxim de llibres per usuari (`MAX_LLIBRES_PER_USUARI = 3`).
- Data de retorn prevista automàtica (2 setmanes).

---

## Estructura del projecte

```
gestio-biblioteca/
├── README.md
├── .gitignore
└── src/
    ├── Llibre.java              (dario)
    ├── NormalitzadorText.java   (dario)
    ├── Biblioteca.java          (dario)
    ├── Usuari.java              (diego)
    ├── Prestec.java             (diego)
    ├── GestorBiblioteca.java    (diego)
    └── Main.java                (conjunt)
```

---

## Com executar el projecte

### Des de la línia de comandes

```bash
cd src
javac *.java
java Main
```

### Des de Eclipse

Obre la carpeta com a projecte Java i executa la classe `Main`.

---

## Flux de treball amb Git

Les branques que cal mantenir són:

```bash
git checkout -b dario      # branca de dario
git checkout -b diego   # branca del diego
```

Cada membre treballa a la seva branca. Quan una funcionalitat està
acabada, es fa **merge** a `main`:

```bash
git checkout main
git merge --no-ff dario
git merge --no-ff diego
```

### Convenció de commits

S'utilitza **Conventional Commits**:

- `feat(modul): ...` — nova funcionalitat
- `fix(modul): ...` — correcció de bug
- `chore: ...` — tasques de manteniment
- `docs: ...` — canvis a la documentació
- `merge: ...` — merge de branques

---

## Historial de versions

- **v1.0** — Versió inicial amb menú complet, totes les classes
  i les millores opcionals (categories, historial, estadístiques).

