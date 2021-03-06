package dev.fujioka.eltonleite.infrastructure.persistence.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.fujioka.eltonleite.domain.model.warehouse.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long>{
    
}
