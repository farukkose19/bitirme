package mfk.mydictionary.repository;

import mfk.mydictionary.model.Game;
import mfk.mydictionary.model.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game,Integer> {
    List<Game> findByOwner_Id(int id);
    List<Game> findByGameFriendsAndOwner_Id(List<Kullanici> friends,int id);
    List<Game> findByOwner_IdAndGameFriendsIsNull(int id);
}
