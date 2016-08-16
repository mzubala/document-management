package pl.com.bottega.documentmanagement.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.repositories.EmployeeRepository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by maciuch on 31.07.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserManagerTest {

    private final String nullLogin = null;
    private final String nullPassword = null;
    private final EmployeeId nullEmployeeId = null;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeId anyEmployeeId;
    @Mock
    private PasswordHasher passwordHasher;
    @Mock
    private EmployeeFactory employeeFactory;
    @Mock
    private Employee anyEmployee;

    private String occupiedLogin = "occupied login";
    private String freeLogin = "free login";
    private String wrongLogin = "wrong login";
    private String wrongPassword = "wrong password";
    private String anyPassword = "test";

    private String correctLogin = "correct login";
    private String correctPassword = "correct password";
    private UserManager userManager;

    @Before
    public void setUp(){
        userManager = new UserManager(employeeRepository, employeeFactory, passwordHasher);
    }

    @Test
    public void shouldFailSignupWhenLoginIsOccupied() {
        // given
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(null);
        when(employeeRepository.isLoginOccupied(occupiedLogin)).thenReturn(true);

        // when
        SignupResultDto signupResultDto = userManager.signup(occupiedLogin, anyPassword, anyEmployeeId);

        // then
        assertFalse(signupResultDto.isSuccess());
        assertEquals("login is occupied", signupResultDto.getFailureReason());
    }

    @Test
    public void shouldSignupEmployeeWhenIdAndLoginAreFree() {
        // given
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(null);
        when(employeeRepository.isLoginOccupied(freeLogin)).thenReturn(false);
        when(employeeFactory.create(freeLogin, anyPassword, anyEmployeeId)).thenReturn(anyEmployee);

        // when
        SignupResultDto signupResultDto = userManager.signup(freeLogin, anyPassword, anyEmployeeId);

        // then
        verify(employeeRepository).save(anyEmployee);
        assertTrue(signupResultDto.isSuccess());
        assertNull(signupResultDto.getFailureReason());
    }

    @Test
    public void shouldFailedSignupWhenLoginIsNull() {
        // when
        SignupResultDto signupResultDto = userManager.signup(nullLogin, correctPassword, anyEmployeeId);

        //then
        assertFalse(signupResultDto.isSuccess());
        assertEquals("data is incorrect", signupResultDto.getFailureReason());
    }

    @Test
    public void shouldFailedSignupWhenPasswordIsNull() {
        // when
        SignupResultDto signupResultDto = userManager.signup(correctLogin, nullPassword, anyEmployeeId);

        //then
        assertFalse(signupResultDto.isSuccess());
        assertEquals("data is incorrect", signupResultDto.getFailureReason());
    }

    @Test
    public void shouldFailedSignupWhenEmploueeIdIsNull() {
        // when
        SignupResultDto signupResultDto = userManager.signup(correctLogin, correctPassword, nullEmployeeId);

        //then
        assertFalse(signupResultDto.isSuccess());
        assertEquals("data is incorrect", signupResultDto.getFailureReason());
    }

    @Test
    public void shouldFailLoginWhenDataIncorrect() {
        // given
        when(employeeRepository.findByLoginAndPassword(wrongLogin, passwordHasher.hashedPassword(wrongPassword))).thenReturn(null);

        // when
        SignupResultDto signupResultDto = userManager.login(wrongLogin, wrongPassword);

        // then
        assertFalse(signupResultDto.isSuccess());
        assertEquals("login or password incorrect", signupResultDto.getFailureReason());
    }

    @Test
    public void shouldFailLoginWhenLoginOrPasswordIsNull() {
        // given
        when(employeeRepository.findByLoginAndPassword(nullLogin, passwordHasher.hashedPassword(nullPassword)))
                .thenThrow(new NullPointerException());

        // when
        SignupResultDto signupResultDto = userManager.login(nullLogin, nullPassword);

        // then
        assertFalse(signupResultDto.isSuccess());
        assertEquals("login or password incorrect", signupResultDto.getFailureReason());
    }

    @Test
    public void shouldLoginWhenDataAreCorrect() {
        //given
        when(employeeRepository.findByLoginAndPassword(correctLogin, passwordHasher.hashedPassword(correctPassword))).thenReturn(anyEmployee);

        //when
        SignupResultDto signupResultDto = userManager.login(correctLogin, correctPassword);

        //then
        assertTrue(signupResultDto.isSuccess());
    }
}
