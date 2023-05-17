package api.controllers.v1;

import api.dtos.requests.RegisterUserRequestDTO;
import api.dtos.responses.ResponseDTO;
import api.models.User;
import api.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
@RestController
public class AuthController {
    private final UserService userService;

    @PostMapping(path = "/register")
    public ResponseEntity<ResponseDTO<User>> register(@Valid @RequestBody RegisterUserRequestDTO request) {
        this.userService.validateEmailUniqueness(request.getEmail());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO<>(this.userService.create(request.convert()), null));
    }
}
