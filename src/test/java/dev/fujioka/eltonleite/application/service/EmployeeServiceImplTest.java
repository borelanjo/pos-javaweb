package dev.fujioka.eltonleite.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.fujioka.eltonleite.domain.model.employee.Employee;
import dev.fujioka.eltonleite.domain.service.BaseService;
import dev.fujioka.eltonleite.infrastructure.persistence.hibernate.repository.EmployeeRepository;

@DisplayName("Serviço de Funcionários")
class EmployeeServiceImplTest {

    private EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);;
    private BaseService<Employee> service = new EmployeeServiceImpl(repository);

    @Test
    @DisplayName("Deve ser possível criar um funcionário")
    void testSave() {
        Employee employee = new Employee("Teste 1", LocalDate.of(1991, 6, 18));
        mockRepositorySave(employee);

        Employee employeeSaved = service.save(employee);

        assertThat(employeeSaved).isNotNull();
        assertThat(employeeSaved.getId()).isNotNull();
        assertThat(employeeSaved.getName()).isEqualTo(employee.getName());
        assertThat(employeeSaved.getDateBirth()).isEqualTo(employee.getDateBirth());
    }

    @Test
    @DisplayName("Deve ser possível atualizar um funcionário")
    void testUpdate() {
        Employee employee = new Employee("Teste A", LocalDate.of(1991, 6, 18));
        Employee employeeReturned = new Employee("Teste U", LocalDate.of(1995, 6, 18));
        mockRepositorySave(employeeReturned);
        mockRepositoryFindById(1L, employee);

        Employee employeeUpdated = service.update(1L, new Employee("Teste U", LocalDate.of(1995, 6, 18)));

        assertThat(employeeUpdated).isNotNull();
        assertThat(employeeUpdated.getId()).isEqualTo(1L);
        assertThat(employeeUpdated.getName()).isNotEqualTo(employee.getName());
        assertThat(employeeUpdated.getDateBirth()).isNotEqualTo(employee.getDateBirth());
    }

    @Test
    @DisplayName("Deve ser possível listar todos os funcionários")
    void testFindAll() {
        mockRepositoryFindAllWith(4);

        List<Employee> employees = service.findAll();

        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("Deve ser possível buscar um funcionário pelo seu id")
    void testFindById() {
        Long id = 1L;
        Employee employee = new Employee("Teste A", LocalDate.of(1991, 6, 18));
        mockRepositoryFindById(id, employee);

        Employee employeeFinded = service.findBy(id);

        assertThat(employeeFinded).isNotNull();
        assertThat(employeeFinded.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("Deve ser possível excluir um funcionário")
    @ExceptionHandler(RuntimeException.class)
    void testDelete() {
        Long id = 1L;
        service.delete(id);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.findBy(id));

        String expectedMessage = "Funcionário inexistente";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    private void mockRepositorySave(Employee employee) {
        Employee employeeReturned = new Employee(employee.getName(), employee.getDateBirth());
        employeeReturned.setId(1L);
        when(repository.save(Mockito.any(Employee.class))).thenReturn(employeeReturned);
    }

    private void mockRepositoryFindById(Long id, Employee employee) {
        Employee employeeReturned = new Employee(employee.getName(), employee.getDateBirth());
        employeeReturned.setId(id);
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(employeeReturned));
    }

    private void mockRepositoryFindAllWith(int values) {
        List<Employee> employees = new ArrayList<>();

        for (int i = 0; i < values; i++) {
            Employee employee = new Employee("Teste A", LocalDate.of(1991, 6, 18 + i));
            employee.setId(i + 1L);
            employees.add(employee);
        }

        when(repository.findAll()).thenReturn(employees);
    }

}
