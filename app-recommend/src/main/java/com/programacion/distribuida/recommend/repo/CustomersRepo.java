package com.programacion.ditribuida.customers.repo;


import com.programacion.ditribuida.customers.db.Customer;

@Repository
@Transacional
public
class CustomersRepo extends JpaRepository<Customer, Integer> {
}
