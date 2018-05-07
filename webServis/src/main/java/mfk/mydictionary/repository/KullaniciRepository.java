package mfk.mydictionary.repository;

import mfk.mydictionary.model.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
@Repository
public interface KullaniciRepository extends JpaRepository<Kullanici,Integer> {
    Kullanici findById(int id);

    @Procedure(name = "Kullanici.addfriend")
    void addFriend(@Param("uid") Integer uid,@Param("fid") Integer fid);

    @Procedure(name = "Kullanici.deleteuserfromfriend")
    void deleteuserfromfriend(@Param("uid") Integer uid);

    @Procedure(name = "Kullanici.deleteuser")
    void deleteuser(@Param("uid") Integer uid);

    @Procedure(name = "Kullanici.deletefriend")
    void deletefriend(@Param("uid") Integer uid,@Param("fid") Integer fid);

    Kullanici findByUserNameAndPassword(String username,String password);

    List<Kullanici> findByNameLike(String name);
}