package com.tfm.afac.data.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
    @Column(name = "id_cliente")
    private int customerId;
    @Column(name = "id_empleado")
    private int employeeId;

}
