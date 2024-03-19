package com.phung.clothshop.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phung.clothshop.domain.dto.address.AddressAddReqDTO;
import com.phung.clothshop.domain.dto.address.AddressResDTO;
import com.phung.clothshop.domain.dto.address.AddressUpdateReqDTO;
import com.phung.clothshop.domain.entity.customer.Address;
import com.phung.clothshop.domain.entity.customer.Customer;
import com.phung.clothshop.exceptions.CustomErrorException;
import com.phung.clothshop.service.address.IAddressService;
import com.phung.clothshop.service.customer.ICustomerService;
import com.phung.clothshop.utils.AppUtils;
import com.phung.clothshop.utils.SessionDataExtractor;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/address")
public class AddressAPI {

    @Autowired
    private SessionDataExtractor sessionDataExtractor;

    @Autowired
    private IAddressService iAddressService;

    @Autowired
    private ICustomerService iCustomerService;

    @Autowired
    private AppUtils appUtils;

    List<AddressResDTO> toAddressResDTOs(List<Address> addresses) {
        List<AddressResDTO> addressResDTOs = new ArrayList<>();
        for (Address address : addresses) {
            addressResDTOs.add(address.toAddressResDTO());
        }
        return addressResDTOs;
    }

    @GetMapping("/getByCustomer")
    public ResponseEntity<?> showAddress(HttpServletRequest request) {
        try {
            Long customerID = sessionDataExtractor.extractCustomerID(request);

            List<Address> addresses = iAddressService.findByCustomerId(customerID);
            List<AddressResDTO> addressResDTOs = toAddressResDTOs(addresses);

            return new ResponseEntity<>(addressResDTOs, HttpStatus.OK);
        } catch (CustomErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> addAddress(AddressAddReqDTO addressAddReqDTO, HttpServletRequest request,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        try {
            Long customerID = sessionDataExtractor.extractCustomerID(request);

            Optional<Customer> customerOptional = iCustomerService.findById(customerID);
            Customer customer = customerOptional.get();

            Address addressAdd = addressAddReqDTO.toAddress(customer);
            iAddressService.save(addressAdd);

            List<Address> addresses = iAddressService.findByCustomerId(customerID);
            List<AddressResDTO> addressResDTOs = toAddressResDTOs(addresses);

            return new ResponseEntity<>(addressResDTOs, HttpStatus.OK);
        } catch (CustomErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }

    }

    @PostMapping("/default/{addressID}")
    public ResponseEntity<?> setDefault(@PathVariable Long addressID, HttpServletRequest request) {
        try {
            Long customerID = sessionDataExtractor.extractCustomerID(request);

            List<Address> addresses = iAddressService.findByCustomerId(customerID);

            for (Address address : addresses) {
                if (address.getId() == addressID) {
                    address.setIsDefault(true);
                } else {
                    address.setIsDefault(false);
                }
                iAddressService.save(address);
            }

            List<AddressResDTO> addressResDTOs = toAddressResDTOs(addresses);

            return new ResponseEntity<>(addressResDTOs, HttpStatus.OK);
        } catch (CustomErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }

    }

    @PatchMapping("/update/{addressID}")
    public ResponseEntity<?> update(
            @PathVariable Long addressID,
            AddressUpdateReqDTO addressUpdateReqDTO,
            HttpServletRequest request, BindingResult bindingResult) {
        try {

            if (bindingResult.hasErrors()) {
                return appUtils.mapErrorToResponse(bindingResult);
            }

            Long customerID = sessionDataExtractor.extractCustomerID(request);

            Optional<Address> addressOptional = iAddressService.findById(addressID);
            Address address = addressOptional.get();

            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(addressUpdateReqDTO, address);

            iAddressService.save(address);

            List<Address> addresses = iAddressService.findByCustomerId(customerID);
            List<AddressResDTO> addressResDTOs = toAddressResDTOs(addresses);

            return new ResponseEntity<>(addressResDTOs, HttpStatus.OK);
        } catch (CustomErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @DeleteMapping("/delete/{addressID}")
    public ResponseEntity<?> delete(
            @PathVariable Long addressID,
            HttpServletRequest request) {
        try {
            Long customerID = sessionDataExtractor.extractCustomerID(request);

            Optional<Address> addressOptional = iAddressService.findById(addressID);
            Address address = addressOptional.get();
            if (address.getIsDefault()) {
                throw new CustomErrorException(HttpStatus.NOT_FOUND, "Can't delete default address");
            }

            iAddressService.delete(address);

            List<Address> addresses = iAddressService.findByCustomerId(customerID);
            List<AddressResDTO> addressResDTOs = toAddressResDTOs(addresses);

            return new ResponseEntity<>(addressResDTOs, HttpStatus.OK);
        } catch (CustomErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

}
