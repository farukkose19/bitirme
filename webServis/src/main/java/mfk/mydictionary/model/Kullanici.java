package mfk.mydictionary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "Kullanici.addfriend", procedureName = "addfriend", parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "uid", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "fid", type = Integer.class)}),
        @NamedStoredProcedureQuery(name = "Kullanici.deleteuserfromfriend", procedureName = "deleteuserfromfriend", parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "uid", type = Integer.class)}),
        @NamedStoredProcedureQuery(name = "Kullanici.deleteuser", procedureName = "deleteuser", parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "uid", type = Integer.class)}),
        @NamedStoredProcedureQuery(name = "Kullanici.deletefriend", procedureName = "deletefriend", parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "uid", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "fid", type = Integer.class)})
})
public class Kullanici {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    private String name;
    private String surname;
    private String userName;
    private String password;

    @OneToMany(mappedBy = "kullanici")
    private List<Word> words;

    @OneToMany(mappedBy = "kullaniciUser", cascade = CascadeType.ALL)
    private List<Package> packagesUser=new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Game> gameOwner=new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "friend")
    private Set<Kullanici> users;

    @ManyToMany(mappedBy = "users",cascade = CascadeType.ALL)
    private List<Kullanici> friends=new ArrayList<>();

    public Kullanici() {
    }

    public Kullanici(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Kullanici(String name, String surname, String userName, String password) {
        this.name = name;
        this.surname = surname;
        this.userName = userName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public void setPackagesUser(List<Package> packagesUser) {
        this.packagesUser = packagesUser;
    }


    public void setUsers(Set<Kullanici> users) {
        this.users = users;
    }

    public void setFriends(List<Kullanici> friends) {
        this.friends = friends;
    }

    public List<Kullanici> getFriends() {
        return friends;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public List<Integer> getPackagesUser() {

        List<Integer> list=new ArrayList<>();
        for(int i=0;i<packagesUser.size();i++){
            list.add(packagesUser.get(i).getId());
        }
        return list;
    }


}
