package com.fleetguard360.alert_management.persistence.repository;

import com.fleetguard360.alert_management.persistence.entity.ConfiguracionAlerta;
import com.fleetguard360.alert_management.persistence.entity.NivelPrioridad;
import com.fleetguard360.alert_management.persistence.entity.TipoAlerta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfiguracionAlertaRepository extends JpaRepository<ConfiguracionAlerta, Integer> {
    List<ConfiguracionAlerta> findByTipoAlerta(TipoAlerta tipoAlerta);
    List<ConfiguracionAlerta> findByNivelPrioridad(NivelPrioridad nivelPrioridad);
    List<ConfiguracionAlerta> findByUsuarioResponsableId(Long usuarioResponsableId);
    List<ConfiguracionAlerta> findByTipoAlertaId(Integer tipoAlertaId);
    List<ConfiguracionAlerta> findByNivelPrioridadId(Integer nivelPrioridadId);
    boolean existsByTipoAlertaIdAndNivelPrioridadIdAndUsuarioResponsableId(Integer tipoAlertaId, Integer nivelPrioridadId, Long usuarioResponsableId);
}

