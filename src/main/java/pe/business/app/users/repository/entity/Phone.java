package pe.business.app.users.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "tbl_phones")
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @NotNull(message = "number must not be null")
    @Positive(message = "number must be mayor than zero")
    private  int number;

    @NotNull(message = "citycode not be null")
    @Column(name = "citycode", nullable=false)
    @Positive(message = "citycode must be mayor than zero")
    private int citycode;

    @NotNull(message = "countrycode not be null")
    @Column(name = "countrycode", nullable=false)
    @Positive(message = "countrycode must be mayor than zero")
    private int countrycode;

    @NotNull(message = "La categoria no puede ser vacia")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private User user;

}
