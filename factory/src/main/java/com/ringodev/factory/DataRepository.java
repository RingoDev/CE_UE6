package com.ringodev.factory;

import com.ringodev.factory.data.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends CrudRepository<Order,Long> {
}
