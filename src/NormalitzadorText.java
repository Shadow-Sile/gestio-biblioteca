import java.text.Normalizer;

/**
 * Classe NormalitzadorText
 * Utilitat per normalitzar textos: passa a minúscules i elimina accents
 * (els diacrítics) per fer cerques que ignorin majúscules i accents.
 *
 * Exemple: "El petit príncep" -> "el petit princep"
 *
 * Autor: dario (branca: dario)
 */
public class NormalitzadorText {

    /**
     * Normalitza un text: minúscules + sense accents + sense espais sobrants.
     */
    public static String normalitzar(String text) {
        if (text == null) return "";
        // NFD descompon les lletres accentuades (à -> a + ̀)
        String normalitzat = Normalizer.normalize(text, Normalizer.Form.NFD);
        // eliminem els marcadors diacrítics
        normalitzat = normalitzat.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return normalitzat.toLowerCase().trim();
    }

    /**
     * Comprova si 'text' conté 'cerca' ignorant majúscules i accents.
     */
    public static boolean conte(String text, String cerca) {
        return normalitzar(text).contains(normalitzar(cerca));
    }
}
