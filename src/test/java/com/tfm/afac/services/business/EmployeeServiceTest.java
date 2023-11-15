package com.tfm.afac.services.business;

//import com.tfm.afac.TestConfig;

import com.tfm.afac.TestConfig;
import com.tfm.afac.api.dtos.EmployeeDto;
import com.tfm.afac.data.daos.EmployeeRepository;
import com.tfm.afac.data.model.EmployeeEntity;
import com.tfm.afac.services.exceptions.ForbiddenException;
import com.tfm.afac.services.exceptions.NotFoundException;
import com.tfm.afac.services.mapper.EmployeeMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.Predicate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@TestConfig
//@RunWith(value= SpringRunner.class)
//@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    private EmployeeDto employeeDto;

    private EmployeeEntity employee;

//@BeforeEach
    @Before
    public void onInit() {

        employeeDto = EmployeeDto.builder()
                .id(1).employeeName("empleado1")
                .lastName1("apellido")
                .cedula(1111111111)
                .address("calle")
                .telephone(11)
                .iniDate(new Date())
                .activate(true)
                .build();

        employee = EmployeeMapper.INSTANCIA.employeeDTOToEmployeeEntity(employeeDto);
    }

    @Test
    void testCreateEmployeeForbidden() {
        onInit();
        EmployeeEntity employee = EmployeeMapper.INSTANCIA.employeeDTOToEmployeeEntity(employeeDto);
        when(employeeRepository.findByCedula(anyLong())).thenReturn(Optional.empty());

        when(employeeRepository.save(any (EmployeeEntity.class))).thenReturn(employee);
        EmployeeDto result = this.employeeService.create(employeeDto);

        assertNotNull( this.employeeService.create(employeeDto));
    }

    @Test
    void createWithForbiddenExceptionTest(){
        onInit();
        when(employeeRepository.findByCedula(anyLong()))
                .thenThrow(ForbiddenException.class);
        assertThrows(ForbiddenException.class, () -> employeeService.create(employeeDto));
    }

    @Test
    void updateEmployeeTest(){
        onInit();

        when(employeeRepository.findById(anyInt()))
                .thenReturn(Optional.of(employee));
        employeeDto.setEmployeeName("modificado");
        when(employeeRepository.save(any (EmployeeEntity.class))).thenReturn(employee);
        assertNotNull( this.employeeService.update(employeeDto));
    }

    @Test
    void updateWithNotFoundExceptionTest(){
        onInit();
        when(employeeRepository.findByCedula(anyLong()))
                .thenThrow(NotFoundException.class);
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> employeeService.update(employeeDto));
    }
    @Test
    void findByIdEmployeeTest(){
        onInit();
        when(employeeRepository.findById(anyInt()))
                .thenReturn(Optional.of(employee));

        assertNotNull( this.employeeService.findById(employeeDto.getId()));
    }

    @Test
    void findByIdWithNotFoundExceptionTest(){
        onInit();
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> employeeService.update(employeeDto));
    }
    @Test
    void readAllEmployeeTest(){
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));
        assertNotNull( this.employeeService.readAll());
    }
    @Test
    void readAllPageableEmployeeTest(){
       Page<EmployeeEntity> page = Mockito.mock(Page.class);
        Pageable pageable = Pageable.unpaged();
        Page<EmployeeEntity> page1 = Page.empty();
        when(employeeRepository.findAll(any(Pageable.class))).thenReturn(page);
        assertNotNull( this.employeeService.readAllPageable(pageable));
        assertFalse( this.employeeService.readAllPageable(pageable).isEmpty());
    }

    @Test
    void readAllPageableEmployeeEmptyTest(){

        Pageable pageable = Pageable.unpaged();
        Page<EmployeeEntity> page = Page.empty();
        when(employeeRepository.findAll(any(Pageable.class))).thenReturn(page);
        assertTrue( this.employeeService.readAllPageable(pageable).isEmpty());
    }

    @Test
    public void testReadAllActiveWithActivateTrue() {
        // Datos de prueba
        boolean activate = true;
        Pageable pageable = PageRequest.of(0, 10);
        EmployeeEntity employeeEntity = new EmployeeEntity(); // Ajusta esto según tu entidad

        // Mock del repositorio
        when(employeeRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(new PageImpl<>(Collections.singletonList(employeeEntity), pageable, 1));

        // Llamada al método a probar
        Page<EmployeeEntity> result = employeeService.readAllActive(pageable, activate);

        // Verificaciones
        assertNotNull(result);
        assertTrue(result.getContent().size() > 0);
        assertEquals(employeeEntity, result.getContent().get(0));

        // Verificación de llamadas al repositorio
        verify(employeeRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    public void testReadAllActiveWithActivateFalse() {
        boolean activate = false;
        Pageable pageable = PageRequest.of(0, 10);

        when(employeeRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(new PageImpl<>(Collections.emptyList(), pageable, 0));

        Page<EmployeeEntity> result = employeeService.readAllActive(pageable, activate);

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());

        verify(employeeRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    public void testReadAllActiveWithDifferentPageable() {
        boolean activate = true;
        Pageable pageable = PageRequest.of(1, 5);
        EmployeeEntity employeeEntity = new EmployeeEntity();

        when(employeeRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(new PageImpl<>(Collections.singletonList(employeeEntity), pageable, 1));

        Page<EmployeeEntity> result = employeeService.readAllActive(pageable, activate);

        assertNotNull(result);
        assertTrue(result.getContent().size() > 0);
        assertEquals(employeeEntity, result.getContent().get(0));

        verify(employeeRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    }
