package com.nequi.franquicias.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * JPA Entity for Branch table
 * R2DBC compatible entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("branches")
public class BranchEntity {
    
    @Id
    private Long id;
    
    @Column("name")
    private String name;
    
    @Column("franchise_id")
    private Long franchiseId;
    
    @Column("created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column("updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
}
