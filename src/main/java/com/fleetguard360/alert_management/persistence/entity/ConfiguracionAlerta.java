package com.fleetguard360.alert_management.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad central que define una regla de negocio para una alerta,
 * vinculando un tipo, una prioridad y un responsable.
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

    // Relación Muchos a Uno: Muchas configuraciones pueden tener la misma Prioridad.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nivel_prioridad_id", nullable = false)
    private NivelPrioridad nivelPrioridad;

    @Column(name = "usuario_responsable_id", nullable = false)
    private Long usuarioResponsableId;

    @Column(name = "parametro_disparador", length = 255)
    private String parametroDisparador;
}

