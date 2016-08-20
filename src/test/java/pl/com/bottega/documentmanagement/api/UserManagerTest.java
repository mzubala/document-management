package pl.com.bottega.documentmanagement.api;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.Role;
import pl.com.bottega.documentmanagement.domain.repositories.EmployeeRepository;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by maciuch on 31.07.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserManagerTest {

    private UserManager userManager;

    @Mock
    private EmployeeRepository employeeRepository;

    private String occupiedLogin = "occupied login";
    private String freeLogin = "free login";
    private String anyPassword = "test";

    @Mock
    private EmployeeId anyEmployeeId;

    @Mock
    private PasswordHasher passwordHasher;

    @Mock
    private EmployeeFactory employeeFactory;

    @Mock
    private Employee employee;

    private SignupResultDto signupResultDto;

    private String hashedPassword = "hashed";
    private String[] roles = {"r", "a", "b"};

    @Before
    public void setUp() throws Exception {
        userManager = new UserManager(employeeRepository, employeeFactory, passwordHasher);
    }

    @Test
    public void shouldFailSignupWhenLoginIsOccupied() {
        // given
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(null);
        when(employeeRepository.isLoginOccupied(occupiedLogin)).thenReturn(true);

        // when
        signupResultDto = userManager.signup(occupiedLogin, anyPassword, anyEmployeeId);

        // then
        assertFailedSignup("login is occupied");
    }

    @Test
    public void shouldSignupEmployeeWhenIdAndLoginAreFree() {
        // given
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(null);
        when(employeeRepository.isLoginOccupied(freeLogin)).thenReturn(false);
        when(employeeFactory.create(freeLogin, anyPassword, anyEmployeeId)).thenReturn(employee);

        // when
        signupResultDto = userManager.signup(freeLogin, anyPassword, anyEmployeeId);

        // then
        verify(employeeRepository).save(employee);
        assertSignupSuccess();
    }

    @Test
    public void shouldFailSignupWhenEmployeeHasAlreadySignedUp() {
        //given
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(employee);
        when(employee.isRegistered()).thenReturn(true);

        // when
        signupResultDto = userManager.signup(freeLogin, anyPassword, anyEmployeeId);

        //then
        assertFailedSignup("employee registered");
    }

    @Test
    public void shouldSetupAccountForExistingEmployeeWithoutLogin() {
        //given
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(employee);
        when(employee.isRegistered()).thenReturn(false);

        // when
        signupResultDto = userManager.signup(freeLogin, anyPassword, anyEmployeeId);

        //then
        verify(employee).setupAccount(freeLogin, anyPassword);
        verify(employeeRepository).save(employee);
        assertSignupSuccess();
    }

    @Test
    public void shouldNotSetupAccountForExisitingEmployeeIfLoginIsOccupied() {
        //given
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(employee);
        when(employee.isRegistered()).thenReturn(false);
        when(employeeRepository.isLoginOccupied(occupiedLogin)).thenReturn(true);

        // when
        signupResultDto = userManager.signup(occupiedLogin, anyPassword, anyEmployeeId);

        //then
        assertFailedSignup("login occupied");
    }

    @Test
    public void shouldLoginUser() {
        //given
        when(passwordHasher.hashedPassword(anyPassword)).thenReturn(hashedPassword);
        when(employeeRepository.findByLoginAndPassword(freeLogin, hashedPassword)).thenReturn(employee);
        when(employee.hasRoles(roles)).thenReturn(true);

        //when
        signupResultDto = userManager.login(freeLogin, anyPassword);

        //then
        assertSignupSuccess();
        assertEquals(employee, userManager.currentEmployee());
        assertTrue(userManager.isAuthenticated(roles));
    }

    @Test
    public void shouldFailLoginWhenIncorrectCredentials() {
        //given
        when(passwordHasher.hashedPassword(anyPassword)).thenReturn(hashedPassword);
        when(employeeRepository.findByLoginAndPassword(freeLogin, hashedPassword)).thenReturn(null);

        //when
        signupResultDto = userManager.login(freeLogin, anyPassword);

        //then
        assertNull(userManager.currentEmployee());
        assertFailedSignup("login or password incorrect");
        assertFalse(userManager.isAuthenticated(roles));
    }

    @Test
    public void shouldUpdateRoles() {
        //given
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(employee);
        when(employeeRepository.getRoles(Sets.newHashSet(roles[0]))).thenReturn(Sets.newHashSet(new Role(roles[0])));

        //when
        userManager.updateRoles(anyEmployeeId, Sets.newHashSet(roles));

        //then
        verify(employee).updateRoles(Arrays.stream(roles).map(Role::new).collect(Collectors.toSet()));
    }

    private void assertFailedSignup(String failureReason) {
        assertFalse(signupResultDto.isSuccess());
        assertEquals(failureReason, signupResultDto.getFailureReason());
    }

    private void assertSignupSuccess() {
        assertTrue(signupResultDto.isSuccess());
        assertNull(signupResultDto.getFailureReason());
    }

}
