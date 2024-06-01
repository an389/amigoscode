package com.amigoscode.service;

import com.amigoscode.model.api.CustomerUpdateRequest;
import com.amigoscode.exception.DuplicateResourceException;
import com.amigoscode.exception.RequestValidationException;
import com.amigoscode.exception.ResourceNotFoundException;
import com.amigoscode.model.Customer;
import com.amigoscode.model.api.CustomerRegistrationRequest;
import com.amigoscode.model.dto.CustomerDTO;
import com.amigoscode.model.dto.ProductDTO;
import com.amigoscode.persistance.interfaces.repository.CustomerRepository;
import com.amigoscode.persistance.mapper.CustomerDTOMapper;
import com.amigoscode.persistance.interfaces.CustomerDao;
import com.amigoscode.persistance.s3.S3Buckets;
import com.amigoscode.persistance.s3.S3Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final CustomerDTOMapper customerDTOMapper;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;
    private final S3Buckets s3Buckets;
    private final CustomerRepository customerRepository;

    public CustomerService(@Qualifier("jpaCustomer") CustomerDao customerDao,
                           CustomerDTOMapper customerDTOMapper,
                           PasswordEncoder passwordEncoder,
                           S3Service s3Service,
                           S3Buckets s3Buckets, CustomerRepository customerRepository) {
        this.customerDao = customerDao;
        this.customerDTOMapper = customerDTOMapper;
        this.passwordEncoder = passwordEncoder;
        this.s3Service = s3Service;
        this.s3Buckets = s3Buckets;
        this.customerRepository = customerRepository;
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerDao.selectAllCustomers()
                .stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomer(String userName) {
        return customerDao.selectUserByEmail(userName)
                .map(customerDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with email [%s] not found".formatted(userName)
                ));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        // check if email exists
        String email = customerRegistrationRequest.email();
        if (customerDao.existsCustomerWithEmail(email)) {
            throw new DuplicateResourceException(
                    "email already taken"
            );
        }

        // add
        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                passwordEncoder.encode(customerRegistrationRequest.password()),
                customerRegistrationRequest.age(),
                customerRegistrationRequest.gender());

        customerDao.insertCustomer(customer);
    }

    public void deleteCustomerById(Integer customerId) {
        checkIfCustomerExistsOrThrow(customerId);
        customerDao.deleteCustomerById(customerId);
    }

    private void checkIfCustomerExistsOrThrow(Integer customerId) {
        if (!customerDao.existsCustomerById(customerId)) {
            throw new ResourceNotFoundException(
                    "customer with id [%s] not found".formatted(customerId)
            );
        }
    }

    public void updateCustomer(Integer customerId,
                               CustomerUpdateRequest updateRequest) {
        // TODO: for JPA use .getReferenceById(customerId) as it does does not bring object into memory and instead a reference
        Customer customer = customerDao.selectCustomerById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(customerId)
                ));

        boolean changes = false;

        if (updateRequest.name() != null && !updateRequest.name().equals(customer.getName())) {
            customer.setName(updateRequest.name());
            changes = true;
        }

        if (updateRequest.age() != null && !updateRequest.age().equals(customer.getAge())) {
            customer.setAge(updateRequest.age());
            changes = true;
        }

        if (updateRequest.email() != null && !updateRequest.email().equals(customer.getEmail())) {
            if (customerDao.existsCustomerWithEmail(updateRequest.email())) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }
            customer.setEmail(updateRequest.email());
            changes = true;
        }

        if (!changes) {
           throw new RequestValidationException("no data changes found");
        }

        customerDao.updateCustomer(customer);
    }

    public void uploadCustomerProfileImage(Integer customerId,
                                           MultipartFile file) {
        checkIfCustomerExistsOrThrow(customerId);
        String profileImageId = UUID.randomUUID().toString();
        try {
            s3Service.putObject(
                    s3Buckets.getCustomer(),
                    "profile-images/%s/%s".formatted(customerId, profileImageId),
                    file.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException("failed to upload profile image", e);
        }
        customerDao.updateCustomerProfileImageId(profileImageId, customerId);
    }

    public byte[] getCustomerProfileImage(Integer customerId) {
        var customer = customerDao.selectCustomerById(customerId)
                .map(customerDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(customerId)
                ));

        if (StringUtils.isBlank(customer.profileImageId())) {
            throw new ResourceNotFoundException(
                    "customer with id [%s] profile image not found".formatted(customerId));
        }

        byte[] profileImage = s3Service.getObject(
                s3Buckets.getCustomer(),
                "profile-images/%s/%s".formatted(customerId, customer.profileImageId())
        );
        return profileImage;
    }

    public List<CustomerDTO> findBySearch(String keyword) {
        return customerRepository.findBySearch(keyword)
                .stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());
    }
}

