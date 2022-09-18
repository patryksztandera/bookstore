package com.bookstore.model.services;

import com.bookstore.model.entities.ClientEntity;
import com.bookstore.model.repositories.ClientRepository;
import com.bookstore.model.responses.ClientRestModel;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class ClientService {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Deprecated
    public List<ClientRestModel> getAll() {
        return clientRepository.findAll()
                .stream()
                .map(ClientRestModel::new)
                .collect(Collectors.toList());
    }

    public PageImpl<ClientRestModel> getAll(Pageable pageable) {
        return new PageImpl<>(clientRepository.findAll(pageable)
                .stream()
                .map(ClientRestModel::new)
                .collect(Collectors.toList()));
    }

    public ClientRestModel getById(final Long id) {
        return new ClientRestModel(clientRepository.getReferenceById(id));
    }

    public ClientRestModel add(ClientRestModel clientRestModel) {
        return new ClientRestModel(clientRepository.save(mapRestModel(clientRestModel)));
    }

    public List<ClientRestModel> addAll(List<ClientRestModel> clientRestModels) {
        Set<ClientEntity> entities = clientRestModels
                .stream()
                .map(this::mapRestModel).collect(Collectors.toSet());
        List<ClientEntity> savedEntities = clientRepository
                .saveAll(entities);
        return savedEntities
                .stream()
                .map(ClientRestModel::new)
                .collect(Collectors.toList());
    }

    private ClientEntity mapRestModel(final ClientRestModel model) {
        return new ClientEntity(model.getName(), model.getSurname(), model.getAddress(), model.getEmail(),
                passwordEncoder.encode(model.getPassword()), model.getPhone());
    }
}
