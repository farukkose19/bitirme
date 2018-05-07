package mfk.mydictionary.controller;

import mfk.mydictionary.model.Mean;
import mfk.mydictionary.model.Package;
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
import java.util.HashSet;
import java.util.List;

@CrossOrigin(origins = { "http://localhost:63342" }, maxAge = 3000)
@RestController
@RequestMapping(value = "/word")
public class WordController {

    private WordRepository repository;
    private KullaniciRepository kullaniciRepository;
    private MeanRepository meanRepository;
    private PackageRepository packageRepository;

    @Autowired
    public WordController(WordRepository repository,
                          KullaniciRepository kullaniciRepository,
                          MeanRepository meanRepository,
                          PackageRepository packageRepository) {
        this.repository = repository;
        this.kullaniciRepository = kullaniciRepository;
        this.meanRepository = meanRepository;
        this.packageRepository = packageRepository;
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List<Word> getAll(){
        return repository.findAll();
    }

    @RequestMapping(value = "/add/{name}/{id}",method = RequestMethod.GET)
    public Word addNewWord(@PathVariable String name, @PathVariable int id){

        Word word=new Word(name);
        word.setKullanici(kullaniciRepository.findById(id));
        repository.save(word);
        return word;
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public List<Word> deteleword(@PathVariable int id){
        Word word=repository.findOne(id);
        if(word.getMeans().size()>0){
            for(Mean mean:word.getMeans()){
                meanRepository.delete(mean);
            }
        }
        List<Word> words=new ArrayList<>();
        words.add(word);
        List<Package> packages=packageRepository.findPackagesByWords(words);
        for(int i=0;i<packages.size();i++){
            packages.get(i).getWords().remove(word);
        }
        repository.delete(word);
        return repository.findAll();
    }

    @RequestMapping(value = "/update/{id}/{name}",method = RequestMethod.GET)
    public Word update(@PathVariable int id,@PathVariable String name){
        Word word=repository.findOne(id);
        word.setName(name);
        repository.save(word);
        return repository.findOne(id);
    }

    @RequestMapping(value = "/wgetuser/{id}",method = RequestMethod.GET)
    public List<Word> wgetuser(@PathVariable int id){
        List<Word> words=repository.findByKullanici_Id(id);
        return words;
    }

    @RequestMapping(value = "/getid/{id}",method = RequestMethod.GET)
    public Word wgetid(@PathVariable int id){
        Word word=repository.findOne(id);
        return word;
    }

    @RequestMapping(value = "/search/{id}/{name}",method = RequestMethod.GET)
    public ResponseEntity<?> search(@PathVariable int id,@PathVariable String name){
        Word word=null;
        word=repository.findByKullanici_IdAndName(id, name);

        if(word!=null)
            return new ResponseEntity<>(word, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
