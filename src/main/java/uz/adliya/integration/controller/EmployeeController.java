package uz.adliya.integration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.adliya.integration.service.EmployeeService;
import uz.adliya.integration.util.AppConstants;

@RestController
@RequestMapping(EmployeeController.BASE_URL)
@RequiredArgsConstructor
public class EmployeeController {

    static final String BASE_URL = AppConstants.APP_BASE_URL + "/employees";

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<String> getEmployees() {

        String employees = employeeService.getEmployees();

        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getEmployeeById(@PathVariable String id) {
        String employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }
}
