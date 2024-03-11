package com.tisoares.oderservice.internal.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tisoares.oderservice.internal.configuration.OrderServiceConstants;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler"})
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @Column(name = "id", updatable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = OrderServiceConstants.DEFAULT_SEQUENCE_NAME)
    private Long id;

    @CreationTimestamp
    @JsonFormat(pattern = OrderServiceConstants.DATE_TIME_PATTERN)
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonFormat(pattern = OrderServiceConstants.DATE_TIME_PATTERN)
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    public BaseEntity() {

    }

    public BaseEntity setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;

        BaseEntity that = (BaseEntity) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    public BaseEntity copyPropertiesUpdate(BaseEntity source) {
        return this;
    }
}
