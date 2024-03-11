package com.tisoares.oderservice.internal.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tisoares.oderservice.internal.configuration.OrderServiceConstants;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_order")
@Getter
@SequenceGenerator(name = OrderServiceConstants.DEFAULT_SEQUENCE_NAME, sequenceName = "users_seq", allocationSize = 50, initialValue = 100)
public class User extends BaseEntity {

    @NotEmpty
    @Size(max = 150)
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @NotEmpty
    @Size(max = 150)
    @Email
    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @NotEmpty
    @Size(max = 150)
    @Column(name = "password", nullable = false, length = 150)
    @JsonIgnore
    private String password;

    public User() {

    }

    private User(Builder builder) {
        setId(builder.id);
        name = builder.name;
        email = builder.email;
        password = builder.password;
    }

    public static Builder builder(PasswordEncoder passwordEncoder) {
        return new Builder(passwordEncoder);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return getEmail().equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getEmail().hashCode();
        return result;
    }

    @Override
    public BaseEntity copyPropertiesUpdate(BaseEntity source) {
        if (source instanceof User) {
            User u = (User) source;
            this.name = u.name;
            this.email = u.email;
            this.password = u.password;
        }
        return super.copyPropertiesUpdate(source);
    }

    public static final class Builder {
        private final PasswordEncoder passwordEncoder;

        private Long id;
        private @NotEmpty @Size(max = 150) String name;
        private @NotEmpty @Size(max = 150) @Email String email;
        private @NotEmpty @Size(max = 150) String password;

        private Builder(PasswordEncoder passwordEncoder) {
            this.passwordEncoder = passwordEncoder;
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder name(@NotEmpty @Size(max = 150) String val) {
            name = val;
            return this;
        }

        public Builder email(@NotEmpty @Size(max = 150) @Email String val) {
            email = val;
            return this;
        }

        public Builder password(@NotEmpty @Size(min = 4, max = 20) String val) {
            password = passwordEncoder.encode(val);
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
