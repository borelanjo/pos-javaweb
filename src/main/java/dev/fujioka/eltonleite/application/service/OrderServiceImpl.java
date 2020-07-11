package dev.fujioka.eltonleite.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.fujioka.eltonleite.domain.model.order.Order;
import dev.fujioka.eltonleite.domain.service.BaseService;
import dev.fujioka.eltonleite.infrastructure.persistence.hibernate.repository.OrderRepository;

@Service
public class OrderServiceImpl implements BaseService<Order> {

    private final OrderRepository repository;

    public OrderServiceImpl(final OrderRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public Order save(Order entity) {

        return repository.save(entity);
    }

    @Override
    public Order update(Long id, Order entity) {
        Order savedOrder = findBy(id);
        savedOrder.setIdUser(entity.getIdUser());
        savedOrder.setDateOrder(entity.getDateOrder());
        return repository.save(savedOrder);
    }

    @Override
    public Order findBy(Long id) {
        Optional<Order> optOrder = repository.findById(id);
        if (!optOrder.isPresent()) {
            throw new RuntimeException("Compra inexistente");
        }
        return optOrder.get();
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);

    }

}
