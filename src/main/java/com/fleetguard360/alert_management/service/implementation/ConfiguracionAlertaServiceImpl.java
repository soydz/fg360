package com.fleetguard360.alert_management.service.implementation;

import com.fleetguard360.alert_management.persistence.entity.ConfiguracionAlerta;
import com.fleetguard360.alert_management.persistence.entity.NivelPrioridad;
import com.fleetguard360.alert_management.persistence.entity.TipoAlerta;
import com.fleetguard360.alert_management.persistence.repository.ConfiguracionAlertaRepository;
import com.fleetguard360.alert_management.persistence.repository.NivelPrioridadRepository;
import com.fleetguard360.alert_management.persistence.repository.TipoAlertaRepository;
import com.fleetguard360.alert_management.presentation.DTO.ConfiguracionAlertaCreateRequest;
import com.fleetguard360.alert_management.presentation.DTO.ConfiguracionAlertaUpdateRequest;
import com.fleetguard360.alert_management.service.exception.ConflictException;
import com.fleetguard360.alert_management.service.exception.NotFoundException;
import com.fleetguard360.alert_management.service.interfaces.ConfiguracionAlertaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ConfiguracionAlertaServiceImpl implements ConfiguracionAlertaService {

    private final ConfiguracionAlertaRepository configuracionAlertaRepository;
    private final TipoAlertaRepository tipoAlertaRepository;
    private final NivelPrioridadRepository nivelPrioridadRepository;

    @Override
    public ConfiguracionAlerta create(ConfiguracionAlertaCreateRequest request) {
        log.debug("Creando ConfiguracionAlerta tipoAlertaId={} nivelPrioridadId={} usuarioResponsableId={}", request.tipoAlertaId(), request.nivelPrioridadId(), request.usuarioResponsableId());

        if (configuracionAlertaRepository.existsByTipoAlertaIdAndNivelPrioridadIdAndUsuarioResponsableId(
                request.tipoAlertaId(), request.nivelPrioridadId(), request.usuarioResponsableId())) {
            log.warn("Intento de crear ConfiguracionAlerta duplicada (tipo={}, nivel={}, usuario={})", request.tipoAlertaId(), request.nivelPrioridadId(), request.usuarioResponsableId());
            throw new ConflictException("Ya existe una configuración con el mismo tipo, nivel y usuario responsable");
        }

        TipoAlerta tipo = tipoAlertaRepository.findById(request.tipoAlertaId())
                .orElseThrow(() -> NotFoundException.forResource("TipoAlerta", "id", request.tipoAlertaId()));
        NivelPrioridad nivel = nivelPrioridadRepository.findById(request.nivelPrioridadId())
                .orElseThrow(() -> NotFoundException.forResource("NivelPrioridad", "id", request.nivelPrioridadId()));

        ConfiguracionAlerta entity = new ConfiguracionAlerta();
        entity.setTipoAlerta(tipo);
        entity.setNivelPrioridad(nivel);
        entity.setUsuarioResponsableId(request.usuarioResponsableId());
        entity.setParametroDisparador(request.parametroDisparador());

        ConfiguracionAlerta saved = configuracionAlertaRepository.save(entity);
        log.info("ConfiguracionAlerta creada id={} (tipo={}, nivel={}, usuario={})", saved.getId(), request.tipoAlertaId(), request.nivelPrioridadId(), request.usuarioResponsableId());
        return saved;
    }

    @Override
    public ConfiguracionAlerta update(Integer id, ConfiguracionAlertaUpdateRequest request) {
        log.debug("Actualizando ConfiguracionAlerta id={}", id);

        ConfiguracionAlerta entity = configuracionAlertaRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forResource("ConfiguracionAlerta", "id", id));

        // Guardar combinación original
        Integer origTipoId = entity.getTipoAlerta().getId();
        Integer origNivelId = entity.getNivelPrioridad().getId();
        Long origUsuarioId = entity.getUsuarioResponsableId();

        // Aplicar cambios
        if (request.tipoAlertaId() != null && !request.tipoAlertaId().equals(origTipoId)) {
            TipoAlerta tipo = tipoAlertaRepository.findById(request.tipoAlertaId())
                    .orElseThrow(() -> NotFoundException.forResource("TipoAlerta", "id", request.tipoAlertaId()));
            entity.setTipoAlerta(tipo);
        }
        if (request.nivelPrioridadId() != null && !request.nivelPrioridadId().equals(origNivelId)) {
            NivelPrioridad nivel = nivelPrioridadRepository.findById(request.nivelPrioridadId())
                    .orElseThrow(() -> NotFoundException.forResource("NivelPrioridad", "id", request.nivelPrioridadId()));
            entity.setNivelPrioridad(nivel);
        }
        if (request.usuarioResponsableId() != null) {
            entity.setUsuarioResponsableId(request.usuarioResponsableId());
        }
        if (request.parametroDisparador() != null) {
            entity.setParametroDisparador(request.parametroDisparador());
        }

        // Combinación final tras los cambios
        Integer finalTipoId = entity.getTipoAlerta().getId();
        Integer finalNivelId = entity.getNivelPrioridad().getId();
        Long finalUsuarioId = entity.getUsuarioResponsableId();

        // Si la combinación cambió, verificar duplicados
        if (!finalTipoId.equals(origTipoId) || !finalNivelId.equals(origNivelId) || !finalUsuarioId.equals(origUsuarioId)) {
            if (configuracionAlertaRepository.existsByTipoAlertaIdAndNivelPrioridadIdAndUsuarioResponsableId(finalTipoId, finalNivelId, finalUsuarioId)) {
                log.warn("Conflicto al actualizar ConfiguracionAlerta id={} a (tipo={}, nivel={}, usuario={}) duplicado", id, finalTipoId, finalNivelId, finalUsuarioId);
                throw new ConflictException("Ya existe una configuración con el mismo tipo, nivel y usuario responsable");
            }
        }

        ConfiguracionAlerta saved = configuracionAlertaRepository.save(entity);
        log.info("ConfiguracionAlerta actualizada id={} (tipo={}, nivel={}, usuario={})", saved.getId(), saved.getTipoAlerta().getId(), saved.getNivelPrioridad().getId(), saved.getUsuarioResponsableId());
        return saved;
    }

    @Override
    public void delete(Integer id) {
        log.debug("Eliminando ConfiguracionAlerta id={}", id);
        if (!configuracionAlertaRepository.existsById(id)) {
            throw NotFoundException.forResource("ConfiguracionAlerta", "id", id);
        }
        try {
            configuracionAlertaRepository.deleteById(id);
            log.info("ConfiguracionAlerta eliminada id={}", id);
        } catch (DataIntegrityViolationException ex) {
            log.warn("No se puede eliminar ConfiguracionAlerta id={} por integridad referencial", id);
            throw new ConflictException("No se puede eliminar la configuración porque está referenciada por otras entidades");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ConfiguracionAlerta getById(Integer id) {
        log.debug("Buscando ConfiguracionAlerta id={}", id);
        return configuracionAlertaRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forResource("ConfiguracionAlerta", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConfiguracionAlerta> listAll() {
        log.debug("Listando todas las ConfiguracionAlerta");
        return configuracionAlertaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConfiguracionAlerta> findByTipoAlertaId(Integer tipoAlertaId) {
        log.debug("Buscando ConfiguracionAlerta por tipoAlertaId={}", tipoAlertaId);
        return configuracionAlertaRepository.findByTipoAlertaId(tipoAlertaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConfiguracionAlerta> findByNivelPrioridadId(Integer nivelPrioridadId) {
        log.debug("Buscando ConfiguracionAlerta por nivelPrioridadId={}", nivelPrioridadId);
        return configuracionAlertaRepository.findByNivelPrioridadId(nivelPrioridadId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConfiguracionAlerta> findByUsuarioResponsableId(Long usuarioResponsableId) {
        log.debug("Buscando ConfiguracionAlerta por usuarioResponsableId={}", usuarioResponsableId);
        return configuracionAlertaRepository.findByUsuarioResponsableId(usuarioResponsableId);
    }
}
