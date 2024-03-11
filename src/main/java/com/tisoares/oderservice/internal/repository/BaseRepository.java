package com.tisoares.oderservice.internal.repository;

import com.tisoares.oderservice.internal.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends PagingAndSortingRepository<T, Long>,
        CrudRepository<T, Long>, JpaSpecificationExecutor<T> {

}
