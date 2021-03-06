package dev.fujioka.eltonleite.application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.fujioka.eltonleite.domain.model.user.User;
import dev.fujioka.eltonleite.domain.service.BaseService;
import dev.fujioka.eltonleite.infrastructure.service.ResponseService;
import dev.fujioka.eltonleite.presentation.assembler.UserAssembler;
import dev.fujioka.eltonleite.presentation.dto.shared.ResponseTO;
import dev.fujioka.eltonleite.presentation.dto.user.UserRequestTO;
import dev.fujioka.eltonleite.presentation.dto.user.UserResponseTO;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final BaseService<User> service;

    private final ResponseService responseService;

    public UserController(final BaseService<User> service, final ResponseService responseService) {
        this.service = service;
        this.responseService = responseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTO<UserResponseTO>> find(@PathVariable Long id) {
        return responseService.ok(UserAssembler.from(service.findBy(id)));
    }

    @GetMapping
    public ResponseEntity<ResponseTO<List<UserResponseTO>>> findAll() {
        return responseService.ok(UserAssembler.from(service.findAll()));
    }

    @PostMapping
    public ResponseEntity<ResponseTO<UserResponseTO>> save(@RequestBody UserRequestTO requestTO) {
        User user = UserAssembler.from(requestTO);
        return responseService.ok(UserAssembler.from(service.save(user)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTO<UserResponseTO>> update(@PathVariable Long id,
            @RequestBody UserRequestTO requestTO) {
        User user = UserAssembler.from(requestTO);
        return responseService.ok(UserAssembler.from(service.update(id, user)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
