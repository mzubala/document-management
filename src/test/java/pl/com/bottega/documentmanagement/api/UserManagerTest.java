package pl.com.bottega.documentmanagement.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.repositories.EmployeeRepository;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by paulina.pislewicz on 2016-07-31.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserManagerTest {
    @Mock
    private EmployeeRepository employeeRepository;

    private String occupiedLogin = "occupied login";
    private String freeLogin = "free login";

    private String anyPassword = "test";
    private String wrongPassword = "test";

    private String hashedPassword = "hashedTest";


    @Mock
    private EmployeeId anyEmployeeId;
    @Mock
    private PasswordHassher passwordHasher;
    @Mock
    private EmployeeFactory employeeFactory;

    @Mock
    private Employee employee;


    @Test
    public void shouldFailSignUpWhenLoginIsOccupied(){
        //given
        UserManager userManager = new UserManager(employeeRepository, employeeFactory, passwordHasher);
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(null);
        when(employeeRepository.isLoginOccupied(occupiedLogin)).thenReturn(true);
        //when
         SignupResultDto signupResultDto = userManager.signup(occupiedLogin, anyPassword, anyEmployeeId);

        //then
        assertFalse(signupResultDto.isSuccess());
        assertEquals("Login is occupied!", signupResultDto.getFailureReason());
        }

    @Test
    public void shouldSignupEmployeeWhenIdAndLoginAreFree(){
        //given
        UserManager userManager = new UserManager(employeeRepository, employeeFactory, passwordHasher);
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(null);
        when(employeeRepository.isLoginOccupied(freeLogin)).thenReturn(false);
        when(employeeFactory.create(freeLogin,anyPassword, anyEmployeeId)).thenReturn(employee);

        //when
        SignupResultDto signupResultDto = userManager.signup(freeLogin, anyPassword, anyEmployeeId);

        //then
        assertTrue(signupResultDto.isSuccess());
        assertNull(signupResultDto.getFailureReason());

    }

    @Test
    public void shouldLogin(){
        //given
        UserManager userManager = new UserManager(employeeRepository, employeeFactory, passwordHasher);
        when(passwordHasher.hashedPassword(anyPassword)).thenReturn(hashedPassword);
        when(employeeRepository.findByLoginAndPassword(occupiedLogin, hashedPassword)).thenReturn(employee);


        //when
        SignupResultDto signupResultDto = userManager.login(occupiedLogin, anyPassword);
        //then
       // verify(employeeRepository).save(employee);
        assertTrue(signupResultDto.isSuccess());
        assertNull(signupResultDto.getFailureReason());

    }

    @Test
    public void shouldFailedLoginWhenLoginOrPasswordIsWrong(){
        //given
        UserManager userManager = new UserManager(employeeRepository, employeeFactory, passwordHasher);
        when(employeeRepository.findByLoginAndPassword(freeLogin, anyPassword)).thenReturn(null);
        when(employeeRepository.findByLoginAndPassword(occupiedLogin, wrongPassword)).thenReturn(null);

        //when
        SignupResultDto signupResultDto = userManager.login(freeLogin, anyPassword);
        SignupResultDto signupResultDto_2 = userManager.login(occupiedLogin, wrongPassword);


        //then
        assertFalse(signupResultDto.isSuccess());
        assertEquals("login or password incorrect", signupResultDto.getFailureReason());
        assertFalse(signupResultDto_2.isSuccess());
        assertEquals("login or password incorrect", signupResultDto_2.getFailureReason());
    }
    }

