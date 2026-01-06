package com.programacion.ditribuida.customers.repo;


import com.programacion.ditribuida.customers.db.Customer;
import com.programacion.ditribuida.customers.db.PurcharseOrder;

@Repository
@Transacional
public
class PurcharseOrderRepo extends JpaRepository<PurcharseOrder, Integer> {
}
