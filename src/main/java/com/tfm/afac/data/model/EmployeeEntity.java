package com.tfm.afac.data.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "empleado")
public class EmployeeEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    @NotBlank
    @Column(name = "nombre")
    private String employeeName;
    @NonNull
    @NotBlank
    @Column(name = "primer_apellido")
    private String lastName1;
    @Column(name = "segundo_apellido")
    private String lastName2;
    @NonNull
    private long cedula;
    @Column(name = "ciudad")
    private String city;
    @NonNull
    @NotBlank
    @Column(name = "direccion")
    private String address;
    @NonNull
    @Column(name = "telefono")
    private long telephone;
    @Column(name = "fecha_inicio")
    private Date iniDate;
    @Column(name = "fecha_fin")
    private Date finishDate;
    @Column(name = "habilitado")
    private boolean activate;

}
