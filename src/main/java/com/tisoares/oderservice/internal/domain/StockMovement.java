package com.tisoares.oderservice.internal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tisoares.oderservice.internal.configuration.LazyFieldsFilter;
import com.tisoares.oderservice.internal.configuration.OrderServiceConstants;
import com.tisoares.oderservice.internal.exception.StockMovementException;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "stock_movement")
@SequenceGenerator(name = OrderServiceConstants.DEFAULT_SEQUENCE_NAME, sequenceName = "stock_movement_seq", allocationSize = 50, initialValue = 100)
public class StockMovement extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "item_id")
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
    private Item item;

    @NotNull
    @Min(1)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Min(0)
    @Column(name = "available", nullable = false)
    private Integer available;

    @Version
    @JsonIgnore
    @Column(name = "version", nullable = false)
    private Integer version;

    public StockMovement() {
    }

    private StockMovement(Builder builder) {
        setId(builder.id);
        item = builder.item;
        quantity = builder.quantity;
        available = builder.quantity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public StockMovement removeFromStock(Integer value) {
        if (value == null || value.compareTo(0) <= 0) {
            throw new StockMovementException("Invalid value to remove from stock " + value);
        }
        if (value.compareTo(this.getAvailable()) > 0) {
            throw new StockMovementException("Insufficient quantity of items in stock. Available: "
                    + this.getAvailable() + " Value to remove: " + value);
        }

        this.available -= value;

        return this;
    }

    @Override
    public BaseEntity copyPropertiesUpdate(BaseEntity source) {
        if (source instanceof StockMovement) {
            StockMovement s = (StockMovement) source;
            if (!this.available.equals(this.quantity) && s.quantity.compareTo(this.quantity) != 0) {
                throw new StockMovementException("Could not change the quantity of stock. " +
                        "Stock Movement has already been used.");
            }
            this.item = s.item;
            this.quantity = s.quantity;
        }
        return super.copyPropertiesUpdate(source);
    }

    public static final class Builder {
        private Long id;
        private Item item;
        private @NotNull @Min(1) Integer quantity;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder item(Item val) {
            item = val;
            return this;
        }

        public Builder quantity(@NotNull @Min(1) Integer val) {
            quantity = val;
            return this;
        }

        public StockMovement build() {
            return new StockMovement(this);
        }
    }
}
