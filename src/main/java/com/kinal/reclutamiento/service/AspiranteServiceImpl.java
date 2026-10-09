package com.kinal.reclutamiento.service;

import com.kinal.reclutamiento.dto.AspiranteDTO;
import com.kinal.reclutamiento.exception.ValidacionException;
import com.kinal.reclutamiento.model.Aspirante;
import com.kinal.reclutamiento.repository.AspiranteRepository;
import com.kinal.reclutamiento.util.Validador;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AspiranteServiceImpl implements AspiranteService {
    private final AspiranteRepository repository;

    public AspiranteServiceImpl(AspiranteRepository repository) { this.repository = repository; }

    @Override
    public List<AspiranteDTO> listar() {
        return repository.listar().stream().map(AspiranteMapper::aDto).collect(Collectors.toList());
    }

    @Override
    public void agregar(AspiranteDTO dto) {
        validar(dto);
        if (repository.buscarPorCorreo(dto.getCorreo().trim()).isPresent()) {
            throw new ValidacionException("Ya existe un aspirante con ese correo.");
        }
        repository.guardar(AspiranteMapper.aEntidad(dto));
    }

    @Override
    public void actualizar(AspiranteDTO dto) {
        if (dto.getId() <= 0) {
            throw new ValidacionException("Seleccione un aspirante de la tabla para actualizar.");
        }
        validar(dto);
        Optional<Aspirante> existente = repository.buscarPorCorreo(dto.getCorreo().trim());
        if (existente.isPresent() && existente.get().getId() != dto.getId()) {
            throw new ValidacionException("Ese correo pertenece a otro aspirante.");
        }
        if (!repository.actualizar(AspiranteMapper.aEntidad(dto))) {
            throw new ValidacionException("El aspirante ya no existe.");
        }
    }

    @Override
    public void eliminar(int id) {
        if (id <= 0) {
            throw new ValidacionException("Seleccione un aspirante de la tabla para eliminar.");
        }
        if (!repository.eliminar(id)) {
            throw new ValidacionException("El aspirante ya no existe.");
        }
    }

    private void validar(AspiranteDTO dto) {
        Validador.requerido(dto.getNombre(), "Nombre");
        Validador.requerido(dto.getApellido(), "Apellido");
        Validador.correo(dto.getCorreo());
        Validador.telefono(dto.getTelefono());
        Validador.requerido(dto.getDireccion(), "Dirección");
        Validador.edad(dto.getEdad());
    }
}
