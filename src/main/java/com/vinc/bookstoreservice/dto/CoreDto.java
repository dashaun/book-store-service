package com.vinc.bookstoreservice.dto;

import com.vinc.bookstoreservice.model.CoreEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoreDto {

    private String id;
    private LocalDateTime created;
    private LocalDateTime modified;

    public static CoreDto toDto(CoreEntity entity) {
        return CoreDto.builder()
                .id(entity.getId().toString())
                .created(entity.getCreated())
                .modified(entity.getModified())
                .build();
    }
}
