package api.services;

import api.contracts.ICrudService;
import api.models.User;
import api.repositories.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements ICrudService<User, Long, UserRepository> {
    @Getter
    private final UserRepository repository;

    @Override
    public String getEntityMessageNotFound() {
        return "Não foi possível encontrar este usuário na nossa base de dados.";
    }

    @Override
    public Boolean existsById(Long id) {
        return this.repository.existsById(id);
    }
}
