package mfk.mydictionary.repository;

import mfk.mydictionary.model.Mean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeanRepository extends JpaRepository<Mean,Integer> {
}
