package com.fleetguard360.alert_management.service.implementation;

import com.fleetguard360.alert_management.configuration.mapper.ConfiguracionAlertaMapper;
import com.fleetguard360.alert_management.persistence.entity.ConfiguracionAlerta;
import com.fleetguard360.alert_management.persistence.entity.TipoAlerta;
import com.fleetguard360.alert_management.persistence.repository.ConfiguracionAlertaRepository;
import com.fleetguard360.alert_management.persistence.repository.TipoAlertaRepository;
import com.fleetguard360.alert_management.presentation.DTO.configuracionalerta.ConfiguracionAlertaCreateRequest;
import com.fleetguard360.alert_management.presentation.DTO.configuracionalerta.ConfiguracionAlertaResponse;
import com.fleetguard360.alert_management.presentation.DTO.configuracionalerta.ConfiguracionAlertaUpdateRequest;
import com.fleetguard360.alert_management.service.exception.ConflictException;
import com.fleetguard360.alert_management.service.exception.NotFoundException;
import com.fleetguard360.alert_management.service.interfaces.ConfiguracionAlertaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ConfiguracionAlertaServiceImpl implements ConfiguracionAlertaService {

    private final ConfiguracionAlertaRepository configuracionAlertaRepository;
    private final TipoAlertaRepository tipoAlertaRepository;
    private final ConfiguracionAlertaMapper mapper;

    @Override
    public ConfiguracionAlertaResponse create(ConfiguracionAlertaCreateRequest request) {
        log.debug("Creando ConfiguracionAlerta tipoAlertaId={} usuarioResponsableId={}",
                request.tipoAlertaId(), request.usuarioResponsableId());

        // Verificar si ya existe una configuración para el mismo tipo y usuario
        if (configuracionAlertaRepository.existsByTipoAlertaIdAndUsuarioResponsableId(
                request.tipoAlertaId(), request.usuarioResponsableId())) {
            log.warn("Intento de crear ConfiguracionAlerta duplicada (tipo={}, usuario={})",
                    request.tipoAlertaId(), request.usuarioResponsableId());
            throw new ConflictException("Ya existe una configuración con el mismo tipo y usuario responsable");
        }

        TipoAlerta tipo = tipoAlertaRepository.findById(request.tipoAlertaId())
                .orElseThrow(() -> NotFoundException.forResource("TipoAlerta", "id", request.tipoAlertaId()));

        ConfiguracionAlerta entity = new ConfiguracionAlerta();
        entity.setTipoAlerta(tipo);
        entity.setUsuarioResponsableId(request.usuarioResponsableId());

        ConfiguracionAlerta saved = configuracionAlertaRepository.save(entity);
        log.info("ConfiguracionAlerta creada id={} tipoAlerta={} usuario={}",
                saved.getId(), saved.getTipoAlerta().getNombre(), saved.getUsuarioResponsableId());
        return mapper.toResponse(saved);
    }

    @Override
    public ConfiguracionAlertaResponse update(Integer id, ConfiguracionAlertaUpdateRequest request) {
        log.debug("Actualizando ConfiguracionAlerta id={}", id);
        ConfiguracionAlerta entity = configuracionAlertaRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forResource("ConfiguracionAlerta", "id", id));

        boolean updated = false;

        if (request.tipoAlertaId() != null) {
            TipoAlerta tipo = tipoAlertaRepository.findById(request.tipoAlertaId())
                    .orElseThrow(() -> NotFoundException.forResource("TipoAlerta", "id", request.tipoAlertaId()));
            entity.setTipoAlerta(tipo);
            updated = true;
        }

        if (request.usuarioResponsableId() != null) {
            entity.setUsuarioResponsableId(request.usuarioResponsableId());
            updated = true;
        }

        if (updated) {
            // Verificar duplicados solo si se actualizaron campos relevantes
            if (configuracionAlertaRepository.existsByTipoAlertaIdAndUsuarioResponsableIdAndIdNot(
                    entity.getTipoAlerta().getId(), entity.getUsuarioResponsableId(), id)) {
                log.warn("Conflicto al actualizar ConfiguracionAlerta id={} (duplicado)", id);
                throw new ConflictException("Ya existe una configuración con el mismo tipo y usuario responsable");
            }
            entity = configuracionAlertaRepository.save(entity);
            log.info("ConfiguracionAlerta actualizada id={}", id);
        }

        return mapper.toResponse(entity);
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
            throw new ConflictException("No se puede eliminar la ConfiguracionAlerta porque está referenciada por otras entidades");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ConfiguracionAlertaResponse getById(Integer id) {
        log.debug("Buscando ConfiguracionAlerta id={}", id);
        ConfiguracionAlerta entity = configuracionAlertaRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forResource("ConfiguracionAlerta", "id", id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConfiguracionAlertaResponse> listAll() {
        log.debug("Listando todas las ConfiguracionAlerta");
        return mapper.toResponses(configuracionAlertaRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConfiguracionAlertaResponse> findByTipoAlertaId(Integer tipoAlertaId) {
        log.debug("Buscando ConfiguracionAlerta por tipoAlertaId={}", tipoAlertaId);
        List<ConfiguracionAlerta> entities = configuracionAlertaRepository.findByTipoAlertaId(tipoAlertaId);
        return mapper.toResponses(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConfiguracionAlertaResponse> findByUsuarioResponsableId(Long usuarioResponsableId) {
        log.debug("Buscando ConfiguracionAlerta por usuarioResponsableId={}", usuarioResponsableId);
        List<ConfiguracionAlerta> entities = configuracionAlertaRepository.findByUsuarioResponsableId(usuarioResponsableId);
        return mapper.toResponses(entities);
    }
}
