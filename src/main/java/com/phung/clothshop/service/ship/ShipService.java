package com.phung.clothshop.service.ship;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phung.clothshop.domain.entity.order.Ship;
import com.phung.clothshop.repository.ShipRepository;

@Service
@Transactional
public class ShipService implements IShipService {

    @Autowired
    private ShipRepository shipRepository;

    @Override
    public List<Ship> findAll() {
        return shipRepository.findAll();
    }

    @Override
    public Ship getById(Long t) {
        return shipRepository.getById(t);
    }

    @Override
    public Optional<Ship> findById(Long id) {
        return shipRepository.findById(id);
    }

    @Override
    public Ship save(Ship e) {
        return shipRepository.save(e);
    }

    @Override
    public void delete(Ship e) {
        e.setDeleted(true);
        shipRepository.save(e);
    }

    @Override
    public void deleteId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteId'");
    }
}
