package pl.com.bottega.documentmanagement.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.EmployeeRepository;

import static org.junit.Assert.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by Wojciech Winiarski on 31.07.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserManagerTest {

    @Mock
    private EmployeeRepository employeeRepository;

    private String occupiedLogin = "Occupied login";

    private String anyPassword = "Any password";
    @Mock
    private EmployeeId anyEmployeeId;

    private String freeLogin = "Free login";
    @Mock
    private PasswordHasher passwordHasher;
    @Mock
    private EmployeeFactory employeeFactory;
    @Mock
    private Employee employee;


    @Test
    public void shouldFailSignupWhenLoginIsOccupied(){
        //given
        UserManager userManager = new UserManager(employeeRepository, employeeFactory, passwordHasher);
        when(employeeRepository.findByEmployee(anyEmployeeId)).thenReturn(null);
        when(employeeRepository.isLoginOccupied(occupiedLogin)).thenReturn(true);
        //when
        SignupResultDto signupResultDto = userManager.signup(occupiedLogin, anyPassword, anyEmployeeId);
        //then
        assertFalse(signupResultDto.isSuccess());
        assertEquals("Login is occupied", signupResultDto.getFailuer());
    }

    @Test
    public void shouldSignupWhenIdAndLoginAreFree(){
        //given kiedy sie spytamy czy login jest zajety ma zwrocic ze jest nie zajety, zmienic na free login
        UserManager userManager = new UserManager(employeeRepository, employeeFactory, passwordHasher);
        when(employeeRepository.findByEmployee(anyEmployeeId)).thenReturn(null);
        when(employeeRepository.isLoginOccupied(freeLogin)).thenReturn(false);
        when(employeeFactory.create(freeLogin, anyPassword, anyEmployeeId)).thenReturn(employee);

        //when
        SignupResultDto signupResultDto = userManager.signup(freeLogin, anyPassword, anyEmployeeId);

        //then modyfikujemy ze ma byc succes lub ma byc null
        verify(employeeRepository).save(employee);
        assertTrue(signupResultDto.isSuccess());
        assertNull("Login is occupied", signupResultDto.getFailuer());
    }
    @Test
    public void shouldNotLogInWhenLoginIsIncorrect(){
        //given
        UserManager userManager = new UserManager(employeeRepository, employeeFactory, passwordHasher);
        when(employeeFactory.create(freeLogin, anyPassword, anyEmployeeId)).thenReturn(employee);


        //when
        SignupResultDto signupResultDto = userManager.login("Bad login", "Any password");

        //then
        assertFalse(signupResultDto.isSuccess());
        assertNotNull(signupResultDto.getFailuer());
        assertEquals("Login or password incorrect", signupResultDto.getFailuer());
    }
    @Test
    public void shouldNotLogInWhenPasswordIsIncorrect(){
        //given
        UserManager userManager = new UserManager(employeeRepository, employeeFactory, passwordHasher);
        when(employeeFactory.create(freeLogin, anyPassword, anyEmployeeId)).thenReturn(employee);

        //when
        SignupResultDto signupResultDto = userManager.login("Free login", "Bad password");

        //then
        assertFalse(signupResultDto.isSuccess());
        assertNotNull(signupResultDto.getFailuer());
        assertEquals("Login or password incorrect", signupResultDto.getFailuer());
    }
    @Test
    public void shouldLogInWhenLoginAndPasswordAreCorrect(){ //Nie dziala mi logowanie, Maciek prosze o podpowiedz jak zrobic.
        //given
        UserManager userManager = new UserManager(employeeRepository, employeeFactory, passwordHasher);
        when(employeeRepository.findByLoginAndPassword(freeLogin, anyPassword)).thenReturn(employee);
        when(passwordHasher.hashedPassword(anyPassword)).thenReturn(anyPassword);

        //when

        SignupResultDto signupResultDto = userManager.login(freeLogin, anyPassword);

        //then
        assertTrue(signupResultDto.isSuccess());
        assertNull(signupResultDto.getFailuer());



    }

}
