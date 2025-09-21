package com.fleetguard360.alert_management.service.implementation;

import com.fleetguard360.alert_management.configuration.mapper.TipoAlertaMapper;
import com.fleetguard360.alert_management.persistence.entity.TipoAlerta;
import com.fleetguard360.alert_management.persistence.repository.TipoAlertaRepository;

import com.fleetguard360.alert_management.presentation.DTO.tipoalerta.TipoAlertaCreateRequest;
import com.fleetguard360.alert_management.presentation.DTO.tipoalerta.TipoAlertaResponse;
import com.fleetguard360.alert_management.presentation.DTO.tipoalerta.TipoAlertaUpdateRequest;
import com.fleetguard360.alert_management.service.exception.ConflictException;
import com.fleetguard360.alert_management.service.exception.NotFoundException;
import com.fleetguard360.alert_management.service.interfaces.TipoAlertaService;
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
public class TipoAlertaServiceImpl implements TipoAlertaService {

    private final TipoAlertaRepository tipoAlertaRepository;
    private final TipoAlertaMapper mapper;

    @Override
    public TipoAlertaResponse create(TipoAlertaCreateRequest request) {
        log.debug("Creando TipoAlerta nombre='{}'", request.nombre());
        String normalizedNombre = request.nombre().trim();
        if (tipoAlertaRepository.existsByNombreIgnoreCase(normalizedNombre)) {
            log.warn("Intento de crear TipoAlerta duplicado nombre='{}'", normalizedNombre);
            throw new ConflictException("Ya existe un TipoAlerta con ese nombre");
        }
        TipoAlerta entity = new TipoAlerta();
        entity.setNombre(normalizedNombre);
        entity.setDescripcion(request.descripcion());
        entity.setActivo(true);
        TipoAlerta saved = tipoAlertaRepository.save(entity);
        log.info("TipoAlerta creado id={} nombre='{}'", saved.getId(), saved.getNombre());
        return mapper.toResponse(saved);
    }

    @Override
    public TipoAlertaResponse update(Integer id, TipoAlertaUpdateRequest request) {
        log.debug("Actualizando TipoAlerta id={}", id);
        TipoAlerta entity = tipoAlertaRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forResource("TipoAlerta", "id", id));

        if (request.nombre() != null) {
            String normalizedNombre = request.nombre().trim();
            if (!normalizedNombre.equalsIgnoreCase(entity.getNombre()) &&
                    tipoAlertaRepository.existsByNombreIgnoreCase(normalizedNombre)) {
                log.warn("Conflicto al actualizar TipoAlerta id={} a nombre='{}' (duplicado)", id, normalizedNombre);
                throw new ConflictException("Ya existe un TipoAlerta con ese nombre");
            }
            entity.setNombre(normalizedNombre);
        }
        if (request.descripcion() != null) {
            entity.setDescripcion(request.descripcion());
        }
        if (request.activo() != null) {
            entity.setActivo(request.activo());
        }
        TipoAlerta saved = tipoAlertaRepository.save(entity);
        log.info("TipoAlerta actualizado id={} activo={}", saved.getId(), saved.isActivo());
        return mapper.toResponse(saved);
    }

    @Override
    public void delete(Integer id) {
        log.debug("Eliminando TipoAlerta id={}", id);
        if (!tipoAlertaRepository.existsById(id)) {
            throw NotFoundException.forResource("TipoAlerta", "id", id);
        }
        try {
            tipoAlertaRepository.deleteById(id);
            log.info("TipoAlerta eliminado id={}", id);
        } catch (DataIntegrityViolationException ex) {
            log.warn("No se puede eliminar TipoAlerta id={} por integridad referencial", id);
            throw new ConflictException("No se puede eliminar el TipoAlerta porque está referenciado por otras entidades");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TipoAlertaResponse getById(Integer id) {
        log.debug("Buscando TipoAlerta id={}", id);
        TipoAlerta entity = tipoAlertaRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forResource("TipoAlerta", "id", id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoAlertaResponse> listAll() {
        log.debug("Listando todos los TipoAlerta");
        return mapper.toResponses(tipoAlertaRepository.findAll());
    }

    @Override
    public TipoAlertaResponse activate(Integer id) {
        log.debug("Activando TipoAlerta id={}", id);
        TipoAlerta entity = tipoAlertaRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forResource("TipoAlerta", "id", id));
        if (!entity.isActivo()) {
            entity.setActivo(true);
            entity = tipoAlertaRepository.save(entity);
            log.info("TipoAlerta activado id={}", id);
        }
        return mapper.toResponse(entity);
    }

    @Override
    public TipoAlertaResponse deactivate(Integer id) {
        log.debug("Desactivando TipoAlerta id={}", id);
        TipoAlerta entity = tipoAlertaRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forResource("TipoAlerta", "id", id));
        if (entity.isActivo()) {
            entity.setActivo(false);
            entity = tipoAlertaRepository.save(entity);
            log.info("TipoAlerta desactivado id={}", id);
        }
        return mapper.toResponse(entity);
    }
}
