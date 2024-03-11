package com.tisoares.oderservice.internal.domain;


import com.tisoares.oderservice.internal.configuration.OrderServiceConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "item")
@SequenceGenerator(name = OrderServiceConstants.DEFAULT_SEQUENCE_NAME, sequenceName = "item_seq", allocationSize = 50, initialValue = 100)
public class Item extends BaseEntity {

    @NotEmpty
    @Size(max = 150)
    @Column(name = "name", unique = true, nullable = false, length = 150)
    private String name;

    public Item() {
    }

    private Item(Builder builder) {
        setId(builder.id);
        name = builder.name;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Item item = (Item) o;

        return getName().equals(item.getName());
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }

    @Override
    public BaseEntity copyPropertiesUpdate(BaseEntity source) {
        if (source instanceof Item) {
            Item s = (Item) source;
            this.name = s.name;
        }
        return super.copyPropertiesUpdate(source);
    }

    public static final class Builder {
        private Long id;
        private @NotEmpty @Size(max = 150) String name;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder name(@NotEmpty @Size(max = 150) String val) {
            name = val;
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }
}
