package api.contracts;

import api.exceptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICrudService<E extends IEntity<T>, T, R extends JpaRepository<E, T>> {

    R getRepository();

    String getEntityMessageNotFound();

    Boolean existsById(T id);

    default E create(E entity) {
        entity.setId(null);
        return this.getRepository()
                .save(entity);
    }

    default E read(T id) {
        return this.getRepository()
                .findById(id)
                .orElseThrow(() -> new NotFoundException(this.getEntityMessageNotFound()));
    }

    default E update(E entity) {
        if (Boolean.FALSE.equals(this.existsById(entity.getId()))) {
            throw new NotFoundException(this.getEntityMessageNotFound());
        }
        return this.getRepository()
                .save(entity);
    }

    default void delete(T id) {
        if (Boolean.FALSE.equals(this.existsById(id))) {
            throw new NotFoundException(this.getEntityMessageNotFound());
        }
        this.getRepository().deleteById(id);
    }
}
