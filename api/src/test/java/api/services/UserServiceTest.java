package api.services;

import api.exceptions.NotFoundException;
import api.models.User;
import api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
class UserServiceTest {
    private static final String ENTITY_NOT_FOUND_MESSAGE = "Não foi possível encontrar este usuário na nossa base de dados.";

    @Mock
    private final UserRepository userRepository;
    private UserService userService;

    @Autowired
    public UserServiceTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
        this.userService = new UserService(this.userRepository);
    }

    private User buildUserPayload(Long id, String name, String email, String password) {
        return User
                .builder()
                .id(id)
                .name(name)
                .email(email)
                .password(password)
                .build();
    }

    @Test
    void create_should_call_save_method_of_user_repository() {
        User payload = this.buildUserPayload(null, "payload", "payload@payload.com", "payload");
        this.userService.create(payload);
        verify(this.userRepository, times(1)).save(payload);
    }

    @Test
    void create_should_set_entity_id_to_null_before_call_save_method_of_user_repository() {
        User payload = this.buildUserPayload(7L, "payload", "payload@payload.com", "payload");
        this.userService.create(payload);
        verify(this.userRepository, times(1))
                .save(this.buildUserPayload(null, "payload", "payload@payload.com", "payload"));
    }

    @Test
    void read_should_call_find_by_id_method_of_user_repository() {
        User payload = this.buildUserPayload(7L, "payload", "payload@payload.com", "payload");
        when(this.userRepository.findById(payload.getId())).thenReturn(Optional.of(payload));
        this.userService.read(payload.getId());
        verify(this.userRepository, times(1)).findById(payload.getId());
    }

    @Test
    void read_should_throw_an_not_found_exception_when_the_requested_user_does_not_exist() {
        Long payload = 5L;
        when(this.userRepository.findById(payload)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> this.userService.read(payload))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(ENTITY_NOT_FOUND_MESSAGE);
    }

    @Test
    void update_should_call_exists_by_id_method_of_user_repository() {
        User payload = this.buildUserPayload(13L, "payload", "payload@payload.com", "payload");
        when(this.userRepository.existsById(payload.getId())).thenReturn(Boolean.TRUE);
        this.userService.update(payload);
        verify(this.userRepository, times(1)).existsById(payload.getId());
    }

    @Test
    void update_should_throw_an_not_found_exception_when_the_requested_user_does_not_exist() {
        User payload = this.buildUserPayload(12L, "payload", "payload@payload.com", "payload");
        when(this.userRepository.existsById(payload.getId())).thenReturn(Boolean.FALSE);
        assertThatThrownBy(() -> this.userService.update(payload))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(ENTITY_NOT_FOUND_MESSAGE);

    }

    @Test
    void update_should_call_save_method_of_user_repository_when_the_requested_user_exist() {
        User payload = this.buildUserPayload(13L, "payload", "payload@payload.com", "payload");
        when(this.userRepository.existsById(payload.getId())).thenReturn(Boolean.TRUE);
        this.userService.update(payload);
        verify(this.userRepository, times(1)).save(payload);
    }

    @Test
    void delete_should_call_exists_by_id_method_of_user_repository() {
        Long payload = 8L;
        when(this.userRepository.existsById(payload)).thenReturn(Boolean.TRUE);
        this.userService.delete(payload);
        verify(this.userRepository, times(1)).existsById(payload);
    }

    @Test
    void delete_should_throw_an_not_found_exception_when_the_requested_user_does_not_exist() {
        Long payload = 45L;
        when(this.userRepository.existsById(payload)).thenReturn(Boolean.FALSE);
        assertThatThrownBy(() -> this.userService.delete(payload))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(ENTITY_NOT_FOUND_MESSAGE);
    }

    @Test
    void delete_should_call_delete_by_id_method_of_user_repository_when_the_requested_user_exist() {
        Long payload = 63L;
        when(this.userRepository.existsById(payload)).thenReturn(Boolean.TRUE);
        this.userService.delete(payload);
        verify(this.userRepository, times(1)).deleteById(payload);
    }

    @Test
    void exists_by_id_should_call_exists_by_id_method_of_user_repository() {
        Long payload = 9L;
        this.userService.existsById(payload);
        verify(this.userRepository, times(1)).existsById(payload);
    }
}