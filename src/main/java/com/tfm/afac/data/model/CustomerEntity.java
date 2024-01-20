package com.tfm.afac.data.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "cliente")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    @NotBlank
    @Column(name = "nombre")
    private String customerName;
    @Column(name = "ciudad")
    private String city;
    @NonNull
    @NotBlank
    @Column(name = "direccion")
    private String address;
    @NonNull
    @Column(name = "telefono")
    private long telephone;
    @Column(name = "fecha_corte")
    private Integer closeMonthDay;
    @Column(name = "fecha_inicio")
    private Date iniDate;
    @Column(name = "fecha_fin")
    private Date finishDate;
    @NonNull
    @Column(name = "habilitado")
    private boolean activate;

}
