package com.fleetguard360.alert_management.service.implementation;

import com.fleetguard360.alert_management.persistence.entity.NivelPrioridad;
import com.fleetguard360.alert_management.persistence.repository.NivelPrioridadRepository;
import com.fleetguard360.alert_management.presentation.DTO.NivelPrioridadCreateRequest;
import com.fleetguard360.alert_management.presentation.DTO.NivelPrioridadUpdateRequest;
import com.fleetguard360.alert_management.service.exception.ConflictException;
import com.fleetguard360.alert_management.service.exception.NotFoundException;
import com.fleetguard360.alert_management.service.interfaces.NivelPrioridadService;
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
public class NivelPrioridadServiceImpl implements NivelPrioridadService {

    private final NivelPrioridadRepository nivelPrioridadRepository;

    @Override
    public NivelPrioridad create(NivelPrioridadCreateRequest request) {
        log.debug("Creando NivelPrioridad nombre='{}' color='{}'", request.nombre(), request.colorHex());
        String normalizedNombre = request.nombre().trim();
        if (nivelPrioridadRepository.existsByNombreIgnoreCase(normalizedNombre)) {
            log.warn("Intento de crear NivelPrioridad duplicado nombre='{}'", normalizedNombre);
            throw new ConflictException("Ya existe un NivelPrioridad con ese nombre");
        }

        NivelPrioridad entity = new NivelPrioridad();
        entity.setNombre(normalizedNombre);
        entity.setColorHex(request.colorHex());
        NivelPrioridad saved = nivelPrioridadRepository.save(entity);
        log.info("NivelPrioridad creado id={} nombre='{}'", saved.getId(), saved.getNombre());
        return saved;
    }

    @Override
    public NivelPrioridad update(Integer id, NivelPrioridadUpdateRequest request) {
        log.debug("Actualizando NivelPrioridad id={}", id);
        NivelPrioridad entity = nivelPrioridadRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forResource("NivelPrioridad", "id", id));

        if (request.nombre() != null) {
            String normalizedNombre = request.nombre().trim();
            if (!normalizedNombre.equalsIgnoreCase(entity.getNombre()) &&
                    nivelPrioridadRepository.existsByNombreIgnoreCase(normalizedNombre)) {
                log.warn("Conflicto al actualizar NivelPrioridad id={} a nombre='{}' (duplicado)", id, normalizedNombre);
                throw new ConflictException("Ya existe un NivelPrioridad con ese nombre");
            }
            entity.setNombre(normalizedNombre);
        }
        if (request.colorHex() != null) {
            entity.setColorHex(request.colorHex());
        }
        NivelPrioridad saved = nivelPrioridadRepository.save(entity);
        log.info("NivelPrioridad actualizado id={} nombre='{}'", saved.getId(), saved.getNombre());
        return saved;
    }

    @Override
    public void delete(Integer id) {
        log.debug("Eliminando NivelPrioridad id={}", id);
        if (!nivelPrioridadRepository.existsById(id)) {
            throw NotFoundException.forResource("NivelPrioridad", "id", id);
        }
        try {
            nivelPrioridadRepository.deleteById(id);
            log.info("NivelPrioridad eliminado id={}", id);
        } catch (DataIntegrityViolationException ex) {
            log.warn("No se puede eliminar NivelPrioridad id={} por integridad referencial", id);
            throw new ConflictException("No se puede eliminar el NivelPrioridad porque está referenciado por otras entidades");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public NivelPrioridad getById(Integer id) {
        log.debug("Buscando NivelPrioridad id={}", id);
        return nivelPrioridadRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forResource("NivelPrioridad", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<NivelPrioridad> listAll() {
        log.debug("Listando todos los NivelPrioridad");
        return nivelPrioridadRepository.findAll();
    }
}
