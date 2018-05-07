package mfk.mydictionary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Mean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String meanName;

    @ManyToOne
    @JoinColumn(name = "word_id")
    private Word word;

    public Mean() {
    }
    public Mean(String meanName) {
        this.meanName = meanName;
    }
    public Mean(String meanName, Word word) {
        this.meanName = meanName;
        this.word = word;
    }

    public int getId() {
        return id;
    }

    public String getMeanName() {
        return meanName;
    }

    @JsonIgnore
    public Word getWord() {
        return word;
    }

    public void setMeanName(String meanName) {
        this.meanName = meanName;
    }

    public void setWord(Word word) {
        this.word = word;
    }

}
