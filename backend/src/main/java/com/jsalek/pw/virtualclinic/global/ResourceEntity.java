package com.jsalek.pw.virtualclinic.global;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * Domain object representing any resource that can be
 * created, modified, deleted.
 */

@Data
@MappedSuperclass
public abstract class ResourceEntity {

//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate() {
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
    }

    @PreUpdate
    protected void onUpdate() {
        setUpdatedAt(LocalDateTime.now());
    }

}
