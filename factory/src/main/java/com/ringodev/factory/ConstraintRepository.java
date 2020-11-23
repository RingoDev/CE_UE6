package com.ringodev.factory;

import com.ringodev.factory.data.Constraint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstraintRepository extends CrudRepository<Constraint,Long> {
}
