package com.E_Commerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.E_Commerce.dto.request.AddressRequest;
import com.E_Commerce.dto.response.AddressResponse;
import com.E_Commerce.entity.Address;
import com.E_Commerce.entity.User;
import com.E_Commerce.exception.UserNotFoundException;
import com.E_Commerce.repository.AddressRepository;
import com.E_Commerce.repository.UserRepository;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public AddressResponse add(AddressRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Address address = new Address();
        address.setUser(user);
        address.setLine1(request.getLine1());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setZip(request.getZip());
        return toResponse(addressRepository.save(address));
    }

    public List<AddressResponse> getByUser(Long userId) {
        return addressRepository.findByUser_Id(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private AddressResponse toResponse(Address address) {
        AddressResponse r = new AddressResponse();
        r.setId(address.getId());
        r.setLine1(address.getLine1());
        r.setCity(address.getCity());
        r.setState(address.getState());
        r.setCountry(address.getCountry());
        r.setZip(address.getZip());
        return r;
    }
}
