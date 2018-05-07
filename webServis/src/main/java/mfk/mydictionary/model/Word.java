package mfk.mydictionary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @ManyToOne()
    @JoinColumn(name = "kullanici_id")
    private Kullanici kullanici;

    @OneToMany(mappedBy = "word")
    private List<Mean> means=new ArrayList<>();


   /* @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "packageWord")
    private Set<Package> packages;*/

    public Word() {
    }

    public Word(String name) {
        this.name = name;
    }

    public Word(String name, Kullanici kullanici) {
        this.name = name;
        this.kullanici = kullanici;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public int getKullanici() {
        return kullanici.getId();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public List<Mean> getMeans() {
        return means;
    }

    public void setMeans(List<Mean> means) {
        this.means = means;
    }
}
