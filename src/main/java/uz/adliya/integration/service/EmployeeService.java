package uz.adliya.integration.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static uz.adliya.integration.util.AppConstants.EMPLOYEES_URL;
import static uz.adliya.integration.util.AppConstants.EMPLOYEES_URL_BY_CATEGORY;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public String getEmployees() {
        return restTemplate.getForObject(EMPLOYEES_URL_BY_CATEGORY, String.class);
    }

    public String getEmployeeById(String id) {

        String url = EMPLOYEES_URL + "/" + id;

        return restTemplate.getForObject(url, String.class);
    }
}
