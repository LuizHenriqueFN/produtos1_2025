package com.example.produtos1_2025.service;

import com.example.produtos1_2025.dtos.RoleDTO;
import com.example.produtos1_2025.dtos.UserDTO;
import com.example.produtos1_2025.dtos.UserInsertDTO;
import com.example.produtos1_2025.entity.Role;
import com.example.produtos1_2025.entity.User;
import com.example.produtos1_2025.repository.RoleRepository;
import com.example.produtos1_2025.repository.UserRepository;
import com.example.produtos1_2025.service.exceptions.DatabaseException;
import com.example.produtos1_2025.service.exceptions.ResourceNotFound;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {
        Page<User> users = repository.findAll(pageable);
        return users.map(user -> new UserDTO(user));
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> opt = repository.findById(id);

        User user = opt.orElseThrow( () -> new ResourceNotFound("User Not Found") );
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {

        User user = new User();
        copyDtoToEntity(dto, user);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user = repository.save(user);
        return new UserDTO(user);

    }

    @Transactional
    public UserDTO update(UserDTO dto, Long id){

        try{
            User entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);

            return new UserDTO(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFound("User not found" + id);
        }

    }

    @Transactional
    public void delete(Long id){
        if (!repository.existsById(id)){
            throw new ResourceNotFound("User not found" + id);
        }
        try{
            repository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }
    }


    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();
        for(RoleDTO role: dto.getRoles()) {
            Role roleEntity = roleRepository.getReferenceById(role.getId());
            entity.getRoles().add(roleEntity);
        }
    }
}
