package mfk.mydictionary.controller;

import mfk.mydictionary.model.Kullanici;
import mfk.mydictionary.model.Mean;
import mfk.mydictionary.repository.MeanRepository;
import mfk.mydictionary.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:63342" }, maxAge = 3000)
@RestController
@RequestMapping(value = "/mean")
public class MeanController {

    private MeanRepository repository;
    private WordRepository wordRepository;

    @Autowired
    public MeanController(MeanRepository repository, WordRepository wordRepository) {
        this.repository = repository;
        this.wordRepository=wordRepository;
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List<Mean> getAll(){
        return repository.findAll();
    }

    @RequestMapping(value = "/add/{meanName}/{wordId}",method = RequestMethod.GET)
    public Mean addUser(@PathVariable String meanName,@PathVariable int wordId){

        Mean mean=new Mean(meanName);
        mean.setWord(wordRepository.findOne(wordId));
        repository.save(mean);
        return repository.findOne(mean.getId());
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public List<Mean> delete(@PathVariable int id){
        repository.delete(id);
        return repository.findAll();
    }

    @RequestMapping(value = "/update/{id}/{name}",method = RequestMethod.GET)
    public List<Mean> update(@PathVariable int id,@PathVariable String name){
        Mean mean=repository.findOne(id);
        mean.setMeanName(name);
        repository.save(mean);
        return repository.findAll();
    }

}
