package com.pwp.repository.springdata;

import com.pwp.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: Mark Spritzler
 * Date: 8/21/12
 * Time: 11:50 AM
 */
public interface SpringDataAddressRepository extends JpaRepository<Address, Long> {
}
