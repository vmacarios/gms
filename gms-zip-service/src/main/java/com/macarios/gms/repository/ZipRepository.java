package com.macarios.gms.repository;

import com.macarios.gms.model.Zip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZipRepository extends JpaRepository<Zip, Integer> {
}
