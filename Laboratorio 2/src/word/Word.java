package word;
import java.util.List;

public class Word {
    private String word;
    private Integer Frecuencywd;
    
    public Word(String word, Integer Frecuencywd) {
        this.word = word;
        this.Frecuencywd = Frecuencywd;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getFrecuencywd() {
        return Frecuencywd;
    }

    public void sumFrecuency(Integer f) {
        // si esta deberia incrementar.
        this.Frecuencywd += f;
    }

    public void incFrequencywd() {
        this.Frecuencywd++;
    }
}
