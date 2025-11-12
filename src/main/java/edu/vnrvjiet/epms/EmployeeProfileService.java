package edu.vnrvjiet.epms;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeProfileService {
    @Autowired
    EmployeeProfileRepository employeeProfileRepository;

    public EmployeeProfile save(EmployeeProfile employeeProfile) {
           return employeeProfileRepository.save(employeeProfile);

    }

    public Optional<EmployeeProfile> findById(Integer employeeProfileId) {
        return employeeProfileRepository.findById(employeeProfileId);
    }

    public Optional<EmployeeProfile> findByName(String name) {
        return employeeProfileRepository.findByName(name);
    }

    public List<EmployeeProfile> findAll() {
        return employeeProfileRepository.findAll();
    }

    public void deleteById(Integer id) {
        employeeProfileRepository.deleteById(id);
    }
}
