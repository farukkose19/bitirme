package mfk.mydictionary.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String Soru;
    private String Cevap1;
    private String Cevap2;
    private String Cevap3;
    private String Cevap4;
    private int dogruCevap;

    @ManyToOne
    @JoinColumn(name = "kullanici_id")
    private Kullanici owner;

    @ManyToMany()
    @JoinTable(name = "game_kullanici",
            joinColumns = { @JoinColumn(name = "game_id") },
            inverseJoinColumns = { @JoinColumn(name = "kullanici_id") })
        private List<Kullanici> gameFriends =new ArrayList<>();


    public Game(String soru, String cevap1, String cevap2, String cevap3, String cevap4, int dogruCevap) {
        Soru = soru;
        Cevap1 = cevap1;
        Cevap2 = cevap2;
        Cevap3 = cevap3;
        Cevap4 = cevap4;
        this.dogruCevap = dogruCevap;
    }

    public Game() {
    }

    public int getId() {
        return id;
    }

    public String getSoru() {
        return Soru;
    }

    public String getCevap1() {
        return Cevap1;
    }

    public String getCevap2() {
        return Cevap2;
    }

    public String getCevap3() {
        return Cevap3;
    }

    public String getCevap4() {
        return Cevap4;
    }

    public int getDogruCevap() {
        return dogruCevap;
    }

    public Kullanici getOwner() {
        return owner;
    }

    public List<Kullanici> getGameFriends() {
        return gameFriends;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSoru(String soru) {
        Soru = soru;
    }

    public void setCevap1(String cevap1) {
        Cevap1 = cevap1;
    }

    public void setCevap2(String cevap2) {
        Cevap2 = cevap2;
    }

    public void setCevap3(String cevap3) {
        Cevap3 = cevap3;
    }

    public void setCevap4(String cevap4) {
        Cevap4 = cevap4;
    }

    public void setDogruCevap(int dogruCevap) {
        this.dogruCevap = dogruCevap;
    }

    public void setOwner(Kullanici owner) {
        this.owner = owner;
    }

    public void setGameFriends(List<Kullanici> gameFriends) {
        this.gameFriends = gameFriends;
    }

}
