package com.tfm.afac.data.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "guia")
public class GuideEntity {

    @Id
    @Column(name = "id_guia")
    private long idGuide;
    @Column(name = "destinatario")
    private String recipient;
    @Column(name = "direccion")
    private String address;
    @Column(name = "ciudad")
    private String city;
    @Column(name = "telefono")
    private long telephone;
    @NonNull
    @Column(name = "habilitado")
    private boolean activate;
    @Column(name = "estado")
    private String status;
    @Column(name = "fecha_entrada")
    private Date entryDate;
    @Column(name = "fecha_entrega")
    private Date deliveryDate;
    @Column(name = "fecha_cargue")
    private Date assignmentDate;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "id_empleado", referencedColumnName = "id")
    private EmployeeEntity employee;

}
