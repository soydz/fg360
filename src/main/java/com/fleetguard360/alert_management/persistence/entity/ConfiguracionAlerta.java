package com.fleetguard360.alert_management.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad central que define una regla de negocio para una alerta,
 * vinculando un tipo y un responsable específico.
 */
@Entity
@Table(name = "configuracion_alerta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracionAlerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Relación Muchos a Uno: Muchas configuraciones pueden usar el mismo Tipo de Alerta.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo_alerta_id", nullable = false)
    private TipoAlerta tipoAlerta;

    @Column(name = "usuario_responsable_id", nullable = false)
    private Long usuarioResponsableId;
}
