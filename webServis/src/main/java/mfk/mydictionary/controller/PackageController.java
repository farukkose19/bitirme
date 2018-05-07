package mfk.mydictionary.controller;

import mfk.mydictionary.model.Kullanici;
import mfk.mydictionary.model.Package;
import mfk.mydictionary.model.Word;
import mfk.mydictionary.repository.KullaniciRepository;
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
@RequestMapping(value = "/package")
public class PackageController {

    private PackageRepository repository;
    private KullaniciRepository kullaniciRepository;
    private WordRepository wordRepository;

    @Autowired
    public PackageController(PackageRepository repository, KullaniciRepository kullaniciRepository, WordRepository wordRepository) {
        this.repository = repository;
        this.kullaniciRepository = kullaniciRepository;
        this.wordRepository = wordRepository;
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List<Package> getAll(){
        return repository.findAll();
    }

    @RequestMapping(value = "/add/{name}/{user}/{friend}/{words}",method = RequestMethod.GET)
    public Package addPackage(@PathVariable String name,
                                 @PathVariable int user,
                                 @PathVariable int friend,
                              @PathVariable List<Integer> words){

        Package aPackage=new Package(name);

        aPackage.setKullaniciUser(kullaniciRepository.findOne(user));

        for(int i=0;i<words.size();i++) {
            Word word1 = wordRepository.findOne(words.get(i));
            aPackage.getWords().add(word1);
        }

        Kullanici friend1=kullaniciRepository.findOne(friend);
        aPackage.getFriends().add(friend1);

        repository.save(aPackage);
        return aPackage;
    }

    @RequestMapping(value = "/addjustpackage/{name}/{user}/{words}",method = RequestMethod.GET)
    public Package addJustPackage(@PathVariable String name,
                           @PathVariable int user,
                           @PathVariable List<Integer> words){

        Package aPackage=new Package(name);

        aPackage.setKullaniciUser(kullaniciRepository.findOne(user));

        for(int i=0;i<words.size();i++) {
            Word word1 = wordRepository.findOne(words.get(i));
            aPackage.getWords().add(word1);
        }
        repository.save(aPackage);
        return aPackage;
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> delete(@PathVariable int id){
        Package pa=repository.findOne(id);
        pa.getFriends().clear();
        pa.getWords().clear();
        repository.delete(pa);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{id}/{name}",method = RequestMethod.GET)
    public List<Package> update(@PathVariable int id,@PathVariable String name){
        Package pa=repository.findOne(id);
        pa.setPackageName(name);
        repository.save(pa);
        return repository.findAll();
    }

    @RequestMapping(value = "/addword/{id}/{wordid}",method = RequestMethod.GET)
    public List<Package> addword(@PathVariable int id,@PathVariable int wordid){
        Package pa=repository.findOne(id);
        Word word=wordRepository.findOne(wordid);
        pa.getWords().add(word);
        repository.save(pa);
        return repository.findAll();
    }

    @RequestMapping(value = "/userpackage/{id}",method = RequestMethod.GET)
    public List<Package> kullanicininPaketi(@PathVariable int id){
        List<Package> list=repository.findByKullaniciUser_Id(id);
        return list;
    }

    @RequestMapping(value = "/getpackage/{uid}/{fid}",method = RequestMethod.GET)
    public List<Package> getPackage(@PathVariable int uid,@PathVariable int fid){
        List<Kullanici> kullanicis=new ArrayList<>();
        kullanicis.add(kullaniciRepository.findById(fid));
        List<Package> list=repository.findPackagesByFriendsAndKullaniciUser_Id(kullanicis,uid);
        return list;
    }

    @RequestMapping(value = "/getpackageid/{id}",method = RequestMethod.GET)
    public Package getPackageid(@PathVariable int id){
        return repository.findOne(id);
    }
}
