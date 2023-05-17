package api.services;

import api.contracts.ICrudService;
import api.exceptions.BadRequestException;
import api.models.User;
import api.repositories.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements ICrudService<User, Long, UserRepository> {
    public static final String EMAIL_NOT_UNIQUE_ERROR_MESSAGE = "O email informado já está sendo utilizado por outro usuário.";
    @Getter
    private final UserRepository repository;

    @Override
    public String getEntityMessageNotFound() {
        return "Não foi possível encontrar este usuário na nossa base de dados.";
    }

    public void validateEmailUniqueness(String email) {
        if (Boolean.TRUE.equals(this.existsByEmail(email))) {
           throw new BadRequestException("email", EMAIL_NOT_UNIQUE_ERROR_MESSAGE);
        }
    }

    private Boolean existsByEmail(String email) {
        return this.repository.existsByEmail(email);
    }

    @Override
    public Boolean existsById(Long id) {
        return this.repository.existsById(id);
    }
}
