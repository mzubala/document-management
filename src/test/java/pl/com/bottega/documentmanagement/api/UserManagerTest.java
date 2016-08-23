package pl.com.bottega.documentmanagement.api;

import com.google.common.collect.Sets;
import org.junit.Assert;
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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by bartosz.paszkowski on 31.07.2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class UserManagerTest {

    private UserManager userManager;

    @Mock
    private EmployeeRepository employeeRepository;
    private String occupiedLogin = "occupied login";
    private String anyPassword = "test";
    private String freeLogin = "free login";
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
    public void shouldFailSignupWhenLoginIsOccupied(){
        //given
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(null);
        when(employeeRepository.isLoginOccupied(occupiedLogin)).thenReturn(true);

        //when
       signupResultDto = userManager.signup(occupiedLogin, anyPassword, anyEmployeeId);

        //then
        assertFailedSignup("login is occupied");
        assertFalse(signupResultDto.isSuccess());
        assertEquals("login is occupied", signupResultDto.getFailureReason());
    }
    private void assertFailedSignup(String failureReason) {
        assertFalse(signupResultDto.isSuccess());
        Assert.assertEquals(failureReason, signupResultDto.getFailureReason());
    }

    @Test
    public void shouldSignupEmployeeWhenIdAndLoginIsFree(){
        //given
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(null);
        when(employeeRepository.isLoginOccupied(occupiedLogin)).thenReturn(false);
        when(employeeFactory.create(freeLogin,anyPassword,anyEmployeeId)).thenReturn(employee);

        //when
        SignupResultDto signupResultDto = userManager.signup(freeLogin, anyPassword, anyEmployeeId);

        //then
        verify(employeeRepository).save(employee);
        assertTrue(signupResultDto.isSuccess());
        assertNull(signupResultDto.getFailureReason());
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
    private void assertSignupSuccess() {
        Assert.assertTrue(signupResultDto.isSuccess());
        assertNull(signupResultDto.getFailureReason());
    }

    @Test
    public void shouldNotLogInWhenLoginIsIncorrect(){
        //given
        UserManager userManager = new UserManager(employeeRepository, employeeFactory,passwordHasher);
        when(employeeRepository.findByLoginAndPassword(freeLogin,anyPassword)).thenReturn(employee);

        //when
        SignupResultDto signupResultDto = userManager.login("bad login","test");

        //then
        assertFalse(signupResultDto.isSuccess());
        assertNotNull(signupResultDto.getFailureReason());
        assertEquals("Login or pass incorrect", signupResultDto.getFailureReason());
    }

    @Test
    public void shouldNotLogInWhenPasswordIsIncorrect(){
        //given
        UserManager userManager = new UserManager(employeeRepository, employeeFactory,passwordHasher);
        when(employeeRepository.findByLoginAndPassword(freeLogin,anyPassword)).thenReturn(employee);

        //when
        SignupResultDto signupResultDto = userManager.login("free login"," bad password");

        //then
        assertFalse(signupResultDto.isSuccess());
        assertNotNull(signupResultDto.getFailureReason());
        assertEquals("Login or pass incorrect", signupResultDto.getFailureReason());
    }


    @Test
    public void shouldLogInWhenLoginAndPasswordAreCorrect(){
        //given
        UserManager userManager = new UserManager(employeeRepository, employeeFactory,passwordHasher);
        when(employeeRepository.findByLoginAndPassword(freeLogin,anyPassword)).thenReturn(employee);
        when(passwordHasher.hashedPassword(anyPassword)).thenReturn(anyPassword);

        //when
        SignupResultDto signupResultDto = userManager.login(freeLogin,anyPassword);

        //then
        assertTrue(signupResultDto.isSuccess());

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
    //given

    //when

    //then
}
