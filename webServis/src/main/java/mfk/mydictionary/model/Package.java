package mfk.mydictionary.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String packageName;

    @ManyToMany()
    @JoinTable(name = "package_words",
            joinColumns = { @JoinColumn(name = "package_id") },
            inverseJoinColumns = { @JoinColumn(name = "word_id") })
    private List<Word> words=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "kullanici_id")
    private Kullanici kullaniciUser;

    //package friends tablosu

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "package_kullanici",
            joinColumns = { @JoinColumn(name = "package_id") },
            inverseJoinColumns = { @JoinColumn(name = "kullanici_id") })
    private List<Kullanici> friends =new ArrayList<>();



    public Package() {
    }

    public Package(String packageName) {
        this.packageName = packageName;
    }

    public int getId() {
        return id;
    }

    public String getPackageName() {
        return packageName;
    }


    public void setKullaniciUser(Kullanici kullaniciUser) {
        this.kullaniciUser = kullaniciUser;
    }

    public Kullanici getKullaniciUser() {

        return kullaniciUser;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public List<Kullanici> getFriends() {
        return friends;
    }

    public void setFriends(List<Kullanici> friends) {
        this.friends = friends;
    }
}
