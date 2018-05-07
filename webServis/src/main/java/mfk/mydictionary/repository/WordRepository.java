package mfk.mydictionary.repository;

import mfk.mydictionary.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word,Integer> {

    List<Word> findByKullanici_Id(int id);
    Word findByKullanici_IdAndName(int kullanici_id,String name);
    void deleteWordById(int id);
}
