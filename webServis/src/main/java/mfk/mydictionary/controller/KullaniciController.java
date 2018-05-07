package mfk.mydictionary.controller;

import mfk.mydictionary.model.Kullanici;
import mfk.mydictionary.model.Mean;
import mfk.mydictionary.model.Word;
import mfk.mydictionary.repository.KullaniciRepository;
import mfk.mydictionary.repository.MeanRepository;
import mfk.mydictionary.repository.PackageRepository;
import mfk.mydictionary.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = { "http://localhost:63342" }, maxAge = 3000)
@RestController
@RequestMapping(value = "/kullanici")
public class KullaniciController {

    private KullaniciRepository repository;
    PackageRepository packageRepository;
    MeanRepository meanRepository;
    WordRepository wordRepository;


    @Autowired
    public KullaniciController(KullaniciRepository repository,
                               PackageRepository packageRepository,
                               MeanRepository meanRepository,
                               WordRepository wordRepository) {
        this.repository = repository;
        this.packageRepository=packageRepository;
        this.meanRepository=meanRepository;
        this.wordRepository=wordRepository;
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List<Kullanici> getAll(){
        return repository.findAll();
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Kullanici addUser(@RequestBody Kullanici kullanici){
        repository.save(kullanici);
        return kullanici;
    }

    @RequestMapping(value = "/addfriend/{uid}/{fid}",method = RequestMethod.GET)
    public Kullanici addNewFriend(@PathVariable int uid,@PathVariable int fid){
        repository.addFriend(uid,fid);
        return repository.findById(uid);
    }

    @RequestMapping(value = "/deletefriend/{uid}/{fid}",method = RequestMethod.GET)
    public Kullanici deleteFriend(@PathVariable int uid,@PathVariable int fid){
        repository.deletefriend(uid,fid);
        return repository.findById(uid);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public List<Kullanici> update(@RequestBody Kullanici kullanici){
        Kullanici updatedKullanici=repository.findOne(kullanici.getId());
        if (!kullanici.getName().equals("")){
            updatedKullanici.setName(kullanici.getName());
        }
        if (!kullanici.getSurname().equals("")){
            updatedKullanici.setSurname(kullanici.getSurname());
        }
        if (!kullanici.getUserName().equals("")){
            updatedKullanici.setUserName(kullanici.getUserName());
        }
        if (!kullanici.getPassword().equals("")){
            updatedKullanici.setPassword(kullanici.getPassword());
        }

        repository.save(updatedKullanici);
        return repository.findAll();
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public List<Kullanici> delete(@PathVariable int id){

        Kullanici kullanici=repository.findOne(id);
        List<Word> words=kullanici.getWords();
        for(Word word:words){
            for(Mean mean:word.getMeans()){
                meanRepository.delete(mean);
            }
            wordRepository.delete(word);
        }

        repository.deleteuserfromfriend(id);
        repository.deleteuser(id);

        return repository.findAll();
    }

    @RequestMapping(value = "/login/{username}/{password}",method = RequestMethod.GET)
    public ResponseEntity<?> login(@PathVariable String username, @PathVariable String password){

        Kullanici kullanici=null;
        kullanici=repository.findByUserNameAndPassword(username,password);

        if(kullanici!=null)
            return new ResponseEntity<>(kullanici, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/getfriend/{uid}",method = RequestMethod.GET)
    public Kullanici getfriend(@PathVariable int uid){
        return repository.findById(uid);
    }

    @RequestMapping(value = "/searchfriend/{name}",method = RequestMethod.GET)
    public List<Kullanici> searchfriend(@PathVariable String name){
        return repository.findByNameLike(name);
    }
}
