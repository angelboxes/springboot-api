package com.angelboxes.springboot.springbootapi.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path="users", collectionResourceRel = "users")
public interface UserRestRepository extends PagingAndSortingRepository<User, Long> {

    public List<User> findByRole(@Param("role") String role);


}
