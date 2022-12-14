package grupobala.Controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import grupobala.Controller.Authentication.AuthenticationController;
import grupobala.Controller.Authentication.IAuthenticationController.IAuthenticationController;
import grupobala.Controller.User.IUserController.IUserController;
import grupobala.Controller.User.UserController;
import grupobala.Entities.User.User;
import grupobala.SetupForTest.SetupForTest;
import org.junit.jupiter.api.Test;

public class TestEditUserInfoController {

    private IAuthenticationController authenticationController = new AuthenticationController();
    private IUserController userController = new UserController();

    @Test
    public void testEditUserInfo() throws Exception {
        SetupForTest.truncateTables();

        authenticationController.signUp("jose", "Financi@123", "jose", 500);
        authenticationController.signIn("jose", "Financi@123");

        assertDoesNotThrow(() -> {
            userController.editUserInfo("ze", "jose");
        });

        new User().close();
    }

    @Test
    public void testEditUserPassword() throws Exception {
        SetupForTest.truncateTables();

        authenticationController.signUp("jose", "Financi@123", "jose", 500);
        authenticationController.signIn("jose", "Financi@123");

        assertDoesNotThrow(() -> {
            userController.updatePassword("Financi@123", "teste");
        });

        new User().close();
    }

    @Test
    public void TestEditUserPasswordWithOldPasswordWrong() throws Exception {
        SetupForTest.truncateTables();

        authenticationController.signUp("jose", "Financi@123", "jose", 500);
        authenticationController.signIn("jose", "Financi@123");

        assertThrows(
            Exception.class,
            () -> {
                userController.updatePassword("teste", "3214");
            }
        );

        new User().close();
    }
}
