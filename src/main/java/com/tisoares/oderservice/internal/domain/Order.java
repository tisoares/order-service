package com.tisoares.oderservice.internal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tisoares.oderservice.internal.configuration.LazyFieldsFilter;
import com.tisoares.oderservice.internal.configuration.OrderServiceConstants;
import com.tisoares.oderservice.internal.domain.enums.OrderStatus;
import com.tisoares.oderservice.internal.exception.OrderException;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@SequenceGenerator(name = OrderServiceConstants.DEFAULT_SEQUENCE_NAME, sequenceName = "orders_seq", allocationSize = 50, initialValue = 100)
public class Order extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id")
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
    private Item item;

    @NotNull
    @Min(1)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
    private User user;

    @NotNull
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
    @JsonIgnoreProperties({"order", "stockMovement.item", "item"})
    private Set<OrderHistory> orderHistory = new HashSet<>();

    public Order() {
    }

    private Order(Builder builder) {
        setId(builder.id);
        item = builder.item;
        quantity = builder.quantity;
        user = builder.user;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Set<OrderHistory> getOrderHistory() {
        return Collections.unmodifiableSet(this.orderHistory);
    }

    public Optional<OrderHistory> addItemFromStock(StockMovement stockMovement) {
        if (this.orderStatus == OrderStatus.COMPLETED) {
            throw new OrderException("The order has already been completed");
        }

        if (stockMovement != null && stockMovement.getAvailable().compareTo(0) > 0) {
            Integer value =
                    this.getRemainingToComplete().compareTo(stockMovement.getAvailable()) >= 0 ?
                            stockMovement.getAvailable() : this.getRemainingToComplete();
            OrderHistory orderHistory = OrderHistory.builder()
                    .order(this)
                    .stockMovement(stockMovement)
                    .quantity(value)
                    .build();
            orderHistory.validateItem();
            stockMovement.removeFromStock(value);
            this.orderHistory.add(orderHistory);
            checkOrderStatusToComplete();
            return Optional.of(orderHistory);
        }
        return Optional.empty();
    }

    private void checkOrderStatusToComplete() {
        if (this.isItemCompleted()) {
            this.orderStatus = OrderStatus.COMPLETED;
        }
    }

    private boolean isItemCompleted() {
        return this.orderStatus == OrderStatus.COMPLETED || this.quantity.equals(getTotalItemsAdded());
    }

    public Integer getTotalItemsAdded() {
        return this.orderHistory.stream()
                .map(OrderHistory::getQuantity)
                .reduce(0, Integer::sum);
    }

    public Integer getRemainingToComplete() {
        return this.quantity - this.getTotalItemsAdded();
    }

    @Override
    public BaseEntity copyPropertiesUpdate(BaseEntity source) {
        if (source instanceof Order) {
            Order s = (Order) source;
            if (this.orderStatus == OrderStatus.COMPLETED) {
                throw new OrderException("Could not change Order. Order has been completed");
            }
            if (!this.orderHistory.isEmpty()) {
                throw new OrderException("Could not change Order. Order is processing!");
            }
            this.quantity = s.getQuantity();
            this.item = s.getItem();
        }
        return super.copyPropertiesUpdate(source);
    }

    public static final class Builder {
        private Long id;
        private Item item;
        private @NotNull @Min(1) Integer quantity;
        private User user;

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

        public Builder user(User val) {
            user = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
