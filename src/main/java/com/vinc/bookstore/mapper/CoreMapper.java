package com.vinc.bookstore.mapper;

import com.vinc.bookstore.dto.CoreDto;
import com.vinc.bookstore.model.CoreEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface CoreMapper<D extends CoreDto, E extends CoreEntity> {

    E toEntity(D dto);

    D toMinimalDTO(E entity);

    D toDTO(E entity);

    default List<D> toMinimalDTO(Collection<E> entities) {
        return entities
                .stream()
                .map(this::toMinimalDTO)
                .collect(Collectors.toList());
    }

    default List<E> toEntity(Collection<D> dtos) {
        return dtos
                .stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    default List<D> toDTO(Collection<E> entities) {
        return entities
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    default Page<D> toMinimalDTO(Page<E> page) {
        List<D> content = toMinimalDTO(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    default D minimalInstance(Class<D> clazz, CoreEntity entity) {
        try {
            D dto = clazz.getConstructor().newInstance();
            BeanUtils.copyProperties(CoreDto.toDto(entity), dto);
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Exception while creating dto instance", e);
        }
    }
}
