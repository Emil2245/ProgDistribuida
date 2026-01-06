package com.programacion.ditribuida.customers.repo;

import com.programacion.ditribuida.customers.db.PurcharseOrder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public
interface PurcharseOrderRepo extends JpaRepository<PurcharseOrder, Integer> {
}
