package api.models;

import api.contracts.IEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(schema = "time_log", name = "tb_roles")
@Entity
public class Role implements IEntity<Long>, GrantedAuthority {
    @Id
    @SequenceGenerator(name = "sq_roles", schema = "time_log", sequenceName = "sq_roles")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_roles")
    @Column(name = "id")
    private Long id;
    @Column(name = "authority")
    private String authority;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) && Objects.equals(authority, role.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authority);
    }
}
