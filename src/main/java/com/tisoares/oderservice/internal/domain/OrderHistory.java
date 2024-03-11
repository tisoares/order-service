package com.tisoares.oderservice.internal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tisoares.oderservice.internal.configuration.LazyFieldsFilter;
import com.tisoares.oderservice.internal.configuration.OrderServiceConstants;
import com.tisoares.oderservice.internal.exception.OrderException;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "order_history")
@Getter
@SequenceGenerator(name = OrderServiceConstants.DEFAULT_SEQUENCE_NAME, sequenceName = "order_history_seq", allocationSize = 50, initialValue = 100)
public class OrderHistory extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "stock_movement_id")
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
    @JsonIgnoreProperties({"item"})
    private StockMovement stockMovement;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "order_id")
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
    @JsonIgnoreProperties(value = {"orderHistory"})
    private Order order;

    @NotNull
    @Min(1)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public OrderHistory() {
    }

    private OrderHistory(Builder builder) {
        setId(builder.id);
        stockMovement = builder.stockMovement;
        order = builder.order;
        quantity = builder.quantity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void validateItem() {
        if (this.getStockMovement() == null || this.getOrder() == null) {
            throw new OrderException("Attributes missing!");
        }
        if (!this.getStockMovement().getItem().equals(this.order.getItem())) {
            throw new OrderException("Order Item different from Stock Item!");
        }
    }

    public static final class Builder {
        private Long id;
        private StockMovement stockMovement;
        private Order order;
        private @NotNull @Min(1) Integer quantity;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder stockMovement(StockMovement val) {
            stockMovement = val;
            return this;
        }

        public Builder order(Order val) {
            order = val;
            return this;
        }

        public Builder quantity(@NotNull @Min(1) Integer val) {
            quantity = val;
            return this;
        }

        public OrderHistory build() {
            return new OrderHistory(this);
        }
    }
}
