package be.boets.addresstool.person;

import be.boets.addresstool.address.AddressRecord;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(
        name = "person",
        schema = "addresstool"
)
public class Person implements Serializable {
    @Id
    @SequenceGenerator(
            name = "person_id_seq",
            sequenceName = "person_id_seq",
            schema = "addresstool",
            allocationSize =  1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "person_id_seq")
    private Integer id;
    @Column(nullable = false)
    String firstName;
    @Column(nullable = false)
    String lastName;
    @Column(nullable = false)
    private LocalDate birthDate;
    //@Embedded
    //private Address address;
}
