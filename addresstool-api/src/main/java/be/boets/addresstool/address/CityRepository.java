package be.boets.addresstool.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Integer> {

    List<CityEntity> findByPostalCode(String postalCode);

    List<CityEntity> findByName(String cityName);

    @Query("select c from CityEntity c where lower(c.name) like lower(concat('%', :cityName, '%'))")
    List<CityEntity> searchByName(@Param("cityName") String cityName);
}
