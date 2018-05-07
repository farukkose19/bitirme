package mfk.mydictionary.repository;

import mfk.mydictionary.model.Kullanici;
import mfk.mydictionary.model.Package;
import mfk.mydictionary.model.Word;
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
public interface PackageRepository extends JpaRepository<Package,Integer> {

    List<Package> findByKullaniciUser_Id(int id);
    List<Package> findPackagesByFriendsAndKullaniciUser_Id(List<Kullanici> friend,int id);
    List<Package> findPackagesByWords(List<Word> word);
}
