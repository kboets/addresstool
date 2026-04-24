package be.boets.addresstool.address;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(
        name = "city",
        schema = "addresstool"
)
public class CityEntity {

    @Id
    @SequenceGenerator(
            name = "city_id_seq",
            sequenceName = "city_id_seq",
            schema = "addresstool",
            allocationSize =  1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "city_id_seq")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String postalCode;

    private boolean main;

    public CityEntity() {
    }

    public CityEntity(String name, String postalCode, boolean main) {
        this.name = name;
        this.postalCode = postalCode;
        this.main = main;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CityEntity that = (CityEntity) o;
        return main == that.main && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(postalCode, that.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, postalCode, main);
    }
}
