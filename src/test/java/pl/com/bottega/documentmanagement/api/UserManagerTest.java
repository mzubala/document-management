package pl.com.bottega.documentmanagement.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.Role;
import pl.com.bottega.documentmanagement.domain.repositories.EmployeeRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Dell on 2016-07-31.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserManagerTest {

    private static final Employee EMPLOYEE = new Employee(new EmployeeId(22L));

    private UserManager userManager;
    private String occupiedLogin = "occupied login";
    private String anyPassword = "test password";
    private String freeLogin = "free login";

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeId anyEmployeeId;

    @Mock
    private PasswordHasher passwordHasher;

    @Mock
    private EmployeeFactory employeeFactory;

    @Mock
    private Employee employee;

    @Mock
    private Set<String> roles;

    @Mock
    private Collection<Role> existingRoles;

    @Mock
    private Set<String> roleNames;

    @Before
    public void setUp() {
        userManager = new UserManager(employeeRepository, employeeFactory, passwordHasher);
    }

    @Test
    public void shouldFailSignupWhenLoginIsOccupied() {
        //given
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(null);
        when(employeeRepository.isLoginOccupied(occupiedLogin)).thenReturn(true);

        //when
        SignupResultDto signupResultDto = userManager.signup(occupiedLogin, anyPassword, anyEmployeeId);

        //then
        assertFalse(signupResultDto.isSuccess());
        assertEquals("login is occupied", signupResultDto.getFailureReason());
    }

    @Test
    public void shouldSignupEmployeeWhenIdAndLoginAreFree() {
        //given
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(null);
        when(employeeRepository.isLoginOccupied(freeLogin)).thenReturn(false);
        when(employeeFactory.create(freeLogin, anyPassword, anyEmployeeId)).thenReturn(employee);

        //when
        SignupResultDto signupResultDto = userManager.signup(freeLogin, anyPassword, anyEmployeeId);

        //then
        verify(employeeRepository).save(employee);
        assertTrue(signupResultDto.isSuccess());
        assertNull(signupResultDto.getFailureReason());
    }

    @Test
    public void shouldSignupDigitalExcludedEmployee() {
        //given
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(null);
        when(employeeFactory.create(null, null, anyEmployeeId)).thenReturn(employee);

        //when
        SignupResultDto signupResultDto = userManager.signup(anyEmployeeId);

        //then
        verify(employeeRepository).save(employee);
        assertTrue(signupResultDto.isSuccess());
        assertNull(signupResultDto.getFailureReason());
    }

    @Test
    public void shouldNotSignupDigitalExcludedEmployee() {
        //given
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(employee);

        //when
        SignupResultDto signupResultDto = userManager.signup(anyEmployeeId);

        //then
        assertFalse(signupResultDto.isSuccess());
        assertEquals("employee registered", signupResultDto.getFailureReason());
    }

    @Test
    public void shouldFailSignUpWhenEmployeIsRegistered() {
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(employee);
        when(employee.isRegistered()).thenReturn(true);

        //when
        SignupResultDto signupResultDto = userManager.signup(freeLogin, anyPassword, anyEmployeeId);

        //then
        assertFalse(signupResultDto.isSuccess());
        assertEquals("employee registered", signupResultDto.getFailureReason());
    }

    @Test
    public void shouldFailSignUpIfLoginIsNotSpecified() {
        try {
            userManager.signup(null, anyPassword, anyEmployeeId);
        }
        catch (IllegalArgumentException ex) {
            return;
        }
        fail("IllegalArgumentException excpected");
    }

    @Test
    public void shouldFailSignUpIfPasswordIsNotSpecified() {
        try {
            userManager.signup(freeLogin, null, anyEmployeeId);
        }
        catch (IllegalArgumentException ex) {
            return;
        }
        fail("IllegalArgumentException excpected");
    }

    @Test
    public void shouldFailSignupIfEmploeyeeIdIsNotSpecified() {
        try {
            userManager.signup(freeLogin, anyPassword, null);
        }
        catch (IllegalArgumentException ex) {
            return;
        }
        fail("IllegalArgumentException excpected");
    }

    @Test
    public void shouldRequireLoginForLogin() {
        try {
            userManager.login(null, anyPassword);
        }
        catch (IllegalArgumentException ex) {
            return;
        }
        fail("IllegalArgumentException excpected");
    }

    @Test
    public void shouldRequirePasswordForLogin() {
        try {
            userManager.login(occupiedLogin, null);
        }
        catch (IllegalArgumentException ex) {
            return;
        }
        fail("IllegalArgumentException excpected");
    }

    @Test
    public void shouldFailLoginIfNotFindEmploeyee() {
        when(employeeRepository.findByLoginAndPassword(occupiedLogin, anyPassword)).thenReturn(null);

        SignupResultDto result = userManager.login(occupiedLogin, anyPassword);

        assertFalse(result.isSuccess());
        assertEquals("login or password incorrect", result.getFailureReason());
    }

    @Test
    public void shouldLoginEmployee() {
        when(employeeRepository.findByLoginAndPassword(occupiedLogin, anyPassword)).thenReturn(employee);
        when(passwordHasher.hashedPassword(anyPassword)).thenReturn(anyPassword);

        SignupResultDto result = userManager.login(occupiedLogin, anyPassword);

        assertTrue(result.isSuccess());
        assertNull(result.getFailureReason());
    }

    @Test
    public void shouldUpdateRoles() {
        Set<Role> existingRoles = new HashSet<>(Arrays.asList(new Role("STAFF"), new Role("MANAGER")));
        Set<String> roleNames = new HashSet<>(Arrays.asList("STAFF", "MANAGER"));
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(EMPLOYEE);
        when(employeeRepository.getRoles(roleNames)).thenReturn(existingRoles);

        userManager.updateRoles(anyEmployeeId, roleNames);

        assertEquals(2, EMPLOYEE.getRole().size());
        assertTrue(EMPLOYEE.getRole().contains(new Role("STAFF")));
        assertTrue(EMPLOYEE.getRole().contains(new Role("MANAGER")));
    }

    @Test
    public void shouldUpdateRolesAndAddNewRoleNameToRoleTable() {
        Set<Role> existingRoles = new HashSet<>(Arrays.asList(new Role("STAFF")));
        Set<String> roleNames = new HashSet<>(Arrays.asList("STAFF", "MANAGER"));
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(EMPLOYEE);
        when(employeeRepository.getRoles(roleNames)).thenReturn(existingRoles);

        userManager.updateRoles(anyEmployeeId, roleNames);

        assertEquals(2, EMPLOYEE.getRole().size());
        assertTrue(EMPLOYEE.getRole().contains(new Role("STAFF")));
        assertTrue(EMPLOYEE.getRole().contains(new Role("MANAGER")));
    }

    @Test
    public void shouldDeleteFormerRoles() {
        Set<Role> existingRoles = new HashSet<>(Arrays.asList(new Role("STAFF"), new Role("MANAGER")));
        Set<String> roleNames = new HashSet<>(Arrays.asList("STAFF", "MANAGER"));
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(EMPLOYEE);
        when(employeeRepository.getRoles(roleNames)).thenReturn(existingRoles);
        userManager.updateRoles(anyEmployeeId, roleNames);

        Set<Role> existingRoles2 = new HashSet<>(Arrays.asList(new Role("STAFF")));
        Set<String> roleNames2 = new HashSet<>(Arrays.asList("STAFF"));
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(EMPLOYEE);
        when(employeeRepository.getRoles(roleNames)).thenReturn(existingRoles2);

        userManager.updateRoles(anyEmployeeId, roleNames2);

        assertEquals(1, EMPLOYEE.getRole().size());
        assertTrue(EMPLOYEE.getRole().contains(new Role("STAFF")));
    }

    @Test
    public void shouldRequireEmployeeIdForUpdatingRoles() {
        try {
            userManager.updateRoles(null, roles);
        }
        catch (IllegalArgumentException ex) {
            return;
        }
        fail("IllegalArgumentException excpected");
    }

    @Test
    public void shouldRequireSetOfRolesForUpdatingRoles() {
        try {
            userManager.updateRoles(anyEmployeeId, null);
        }
        catch (IllegalArgumentException ex) {
            return;
        }
        fail("IllegalArgumentException excpected");
    }

    @Test
    public void shouldFailIfEmployeeDoesNotExists() {

    }

}
