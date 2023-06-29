package fontys.s3.andreipieleanu.datalayer;

import fontys.s3.andreipieleanu.datalayer.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;


public interface IAddressDal extends JpaRepository<AddressEntity, Integer> {
    @Transactional
    @Modifying
    @Query("update AddressEntity ad set ad.streetName=:#{#updatableAddress.streetName}, " +
            "ad.streetNumber=:#{#updatableAddress.streetNumber}, " +
            "ad.zipCode=:#{#updatableAddress.zipCode}, " +
            "ad.city=:#{#updatableAddress.city}, " +
            "ad.country=:#{#updatableAddress.country}, " +
            "ad.phone=:#{#updatableAddress.phone} " +
            "where ad.id=:#{#updatableAddress.id}")
    Integer updateAddress(AddressEntity updatableAddress);
}
