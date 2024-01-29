package com.tfm.afac.services.business;

import com.tfm.afac.TestConfig;
import com.tfm.afac.api.dtos.EmployeeDto;
import com.tfm.afac.data.daos.EmployeeRepository;
import com.tfm.afac.data.model.EmployeeEntity;
import com.tfm.afac.services.exceptions.ForbiddenException;
import com.tfm.afac.services.exceptions.NotFoundException;
import com.tfm.afac.services.mapper.EmployeeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@TestConfig
class EmployeeServiceTest {


    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    private EmployeeDto employeeDto;

    private EmployeeEntity employee;

    @BeforeEach
    public void onInit() {
        MockitoAnnotations.openMocks(this);

        employeeDto = EmployeeDto.builder()
                .id(1).employeeName("empleado1")
                .lastName1("apellido")
                .cedula(00000001)
                .address("calle")
                .telephone(11)
                .iniDate(new Date())
                .activate(true)
                .build();

        employee = EmployeeMapper.INSTANCIA.employeeDTOToEmployeeEntity(employeeDto);
    }

    @Test
    void CreateEmployeeTest() {

        EmployeeEntity employee = EmployeeMapper.INSTANCIA.employeeDTOToEmployeeEntity(employeeDto);
        when(employeeRepository.save(any (EmployeeEntity.class))).thenReturn(employee);
        EmployeeDto result = this.employeeService.create(employeeDto);

        assertNotNull(result);
        assertEquals(employeeDto.getCedula(), result.getCedula());
        verify(employeeRepository, times(1)).findByCedula(anyLong());
        verify(employeeRepository, times(1)).save(any(EmployeeEntity.class));
    }

    @Test
    void createWithForbiddenExceptionTest(){

        when(employeeRepository.findByCedula(anyLong()))
                .thenThrow(ForbiddenException.class);
        assertThrows(ForbiddenException.class, () -> employeeService.create(employeeDto));
    }

    @Test
    void updateEmployeeTest(){

        when(employeeRepository.findById(anyInt()))
                .thenReturn(Optional.of(employee));
        employeeDto.setEmployeeName("modificado");
        when(employeeRepository.save(any (EmployeeEntity.class))).thenReturn(employee);
        assertNotNull( this.employeeService.update(employeeDto));
    }

    @Test
    void updateWithNotFoundExceptionTest(){
        when(employeeRepository.findByCedula(anyLong()))
                .thenThrow(NotFoundException.class);
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> employeeService.update(employeeDto));
    }
    @Test
    void findByIdEmployeeTest(){
        when(employeeRepository.findById(anyInt()))
                .thenReturn(Optional.of(employee));

        assertNotNull( this.employeeService.findById(employeeDto.getId()));
    }

    @Test
    void findByIdWithNotFoundExceptionTest(){
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> employeeService.update(employeeDto));
    }
    @Test
    void readAllEmployeeTest(){
        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));
        assertNotNull( this.employeeService.readAll());
    }
    @Test
    void readAllPageableEmployeeTest(){
       Page page = mock(Page.class);
        Pageable pageable = Pageable.unpaged();
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
    void testReadAllActiveWithActivateTrue() {

        boolean activate = true;
        Pageable pageable = PageRequest.of(0, 10);
        EmployeeEntity employeeEntity = new EmployeeEntity(); // Ajusta esto según tu entidad

        // Mock del repositorio
        when(employeeRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(new PageImpl<>(Collections.singletonList(employeeEntity), pageable, 1));

        Page<EmployeeEntity> result = employeeService.readAllActive(pageable, activate);

        assertNotNull(result);
        assertTrue(result.getContent().size() > 0);
        assertEquals(employeeEntity, result.getContent().get(0));

        verify(employeeRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void testReadAllActiveWithActivateFalse() {
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
    void testReadAllActiveWithDifferentPageable() {
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

    ///////////////

    // ... (código existente)

    @Test
    void findByActivateTrueTest() {
        boolean activate = true;
        when(employeeRepository.findByactivate(activate)).thenReturn(Collections.singletonList(employee));

        List<EmployeeDto> result = employeeService.findByActivate(activate);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(employeeDto.getId(), result.get(0).getId());

        verify(employeeRepository, times(1)).findByactivate(activate);
    }

    @Test
    void findByActivateFalseTest() {
        boolean activate = false;
        when(employeeRepository.findByactivate(activate)).thenReturn(Collections.emptyList());

        List<EmployeeDto> result = employeeService.findByActivate(activate);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(employeeRepository, times(1)).findByactivate(activate);
    }

    @Test
    void readAllActiveWithActivateTrueTest() {
        boolean activate = true;
        Pageable pageable = PageRequest.of(0, 10);

        when(employeeRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(new PageImpl<>(Collections.singletonList(employee), pageable, 1));

        Page<EmployeeEntity> result = employeeService.readAllActive(pageable, activate);

        assertNotNull(result);
        assertTrue(result.getContent().size() > 0);
        assertEquals(employee, result.getContent().get(0));

        verify(employeeRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void readAllActiveWithActivateFalseTest() {
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
    void readAllActiveWithDifferentPageableTest() {
        boolean activate = true;
        Pageable pageable = PageRequest.of(1, 5);

        when(employeeRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(new PageImpl<>(Collections.singletonList(employee), pageable, 1));

        Page<EmployeeEntity> result = employeeService.readAllActive(pageable, activate);

        assertNotNull(result);
        assertTrue(result.getContent().size() > 0);
        assertEquals(employee, result.getContent().get(0));

        verify(employeeRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

}
