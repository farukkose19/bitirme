package mfk.mydictionary.controller;

import mfk.mydictionary.model.Game;
import mfk.mydictionary.model.Kullanici;
import mfk.mydictionary.model.Mean;
import mfk.mydictionary.repository.GameRepository;
import mfk.mydictionary.repository.KullaniciRepository;
import mfk.mydictionary.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = { "http://localhost:63342" }, maxAge = 3000)
@RestController
@RequestMapping(value = "/game")
public class GameController {

    private GameRepository repository;
    private KullaniciRepository kullaniciRepository;

    @Autowired
    public GameController(GameRepository repository, KullaniciRepository kullaniciRepository) {
        this.repository = repository;
        this.kullaniciRepository = kullaniciRepository;
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List<Game> getAll(){
        return repository.findAll();
    }

    @RequestMapping(value = "/add/{soru}/{cevap1}/{cevap2}/{cevap3}/{cevap4}/{dcevap}/{uid}/{fid}",
            method = RequestMethod.GET)
    public Game addUser(@PathVariable String soru,
                        @PathVariable String cevap1,
                        @PathVariable String cevap2,
                        @PathVariable String cevap3,
                        @PathVariable String cevap4,
                        @PathVariable int dcevap,
                        @PathVariable int uid,
                        @PathVariable int fid){

        Kullanici owner=kullaniciRepository.findById(uid);
        Kullanici friend=kullaniciRepository.findById(fid);
        List<Kullanici> friendlist=new ArrayList();
        friendlist.add(friend);

        Game game=new Game(soru,cevap1,cevap2,cevap3,cevap4,dcevap);
        game.setOwner(owner);
        game.setGameFriends(friendlist);
        repository.save(game);
        return repository.findOne(game.getId());
    }

    @RequestMapping(value = "/addnf/{soru}/{cevap1}/{cevap2}/{cevap3}/{cevap4}/{dcevap}/{uid}",
            method = RequestMethod.GET)
    public Game addnotfriend(@PathVariable String soru,
                        @PathVariable String cevap1,
                        @PathVariable String cevap2,
                        @PathVariable String cevap3,
                        @PathVariable String cevap4,
                        @PathVariable int dcevap,
                        @PathVariable int uid){

        Kullanici owner=kullaniciRepository.findById(uid);
        Game game=new Game(soru,cevap1,cevap2,cevap3,cevap4,dcevap);
        game.setOwner(owner);
        repository.save(game);
        return repository.findOne(game.getId());
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public List<Game> deteleGame(@PathVariable int id){
        Game game=repository.findOne(id);
        repository.delete(game);
        return repository.findAll();
    }

    @RequestMapping(value = "/getwithuid/{uid}",method = RequestMethod.GET)
    public List<Game> getWithUid(@PathVariable int uid){
        return repository.findByOwner_Id(uid);
    }

    @RequestMapping(value = "/getgames/{uid}/{fid}",method = RequestMethod.GET)
    public List<Game> getWithFid(@PathVariable int uid,
                                 @PathVariable int fid){
        List<Kullanici> friends=new ArrayList<>();
        friends.add(kullaniciRepository.findById(fid));
        return repository.findByGameFriendsAndOwner_Id(friends,uid);
    }

    @RequestMapping(value = "/isnull/{uid}",method = RequestMethod.GET)
    public List<Game> isNull(@PathVariable int uid){
        return repository.findByOwner_IdAndGameFriendsIsNull(uid);
    }
}
