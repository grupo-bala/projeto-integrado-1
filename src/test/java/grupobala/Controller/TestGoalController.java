package grupobala.Controller;

import grupobala.Controller.Authentication.AuthenticationController;
import grupobala.Controller.Goal.GoalController;
import grupobala.Entities.User.User;
import grupobala.SetupForTest.SetupForTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestGoalController {

    @Test
    public void testGetAllGoals() throws Exception {
        SetupForTest.truncateTables();

        Assertions.assertDoesNotThrow(() -> {
            AuthenticationController authenticationController = new AuthenticationController();
            authenticationController.signUp(
                "financi",
                "Financi@123",
                "Financi",
                0
            );
            authenticationController.signIn("financi", "Financi@123");

            GoalController goalController = new GoalController();
            goalController.getGoals();
            new User().close();
        });
    }
}
