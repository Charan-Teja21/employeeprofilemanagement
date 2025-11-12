package edu.vnrvjiet.epms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("epms")
public class EmployeeProfileController {
    @Autowired
    private  EmployeeProfileService employeeProfileService;

    @PostMapping("addOne")
    public ResponseEntity<EmployeeProfile> addOneEmployeeProfile(@RequestBody EmployeeProfile employeeProfile) {
        return saveEmployeeProfile(employeeProfile);
    }

    @GetMapping("findAll")
    public ResponseEntity<List<EmployeeProfile>> findAllEmployeeProfiles() {
        var list = employeeProfileService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("findById")
    public ResponseEntity<EmployeeProfile> findEmployeeProfileById(Integer employeeProfileId) {
        var employeeProfile = employeeProfileService.findById(employeeProfileId);
        return new ResponseEntity<>(employeeProfile.orElse(null), HttpStatus.OK);
    }

    @DeleteMapping("deleteById")
    public ResponseEntity<EmployeeProfile> deleteEmployeeProfileById(Integer employeeProfileId) {
        employeeProfileService.deleteById(employeeProfileId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<EmployeeProfile> updateEmployeeProfile(@RequestBody EmployeeProfile employeeProfile) {
        return saveEmployeeProfile(employeeProfile);
    }

    private ResponseEntity<EmployeeProfile> saveEmployeeProfile(EmployeeProfile employeeProfile) {
        employeeProfile = employeeProfileService.save(employeeProfile);
        return new ResponseEntity<>(employeeProfile, HttpStatus.OK);
    }
}
