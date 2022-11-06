package grupobala.Database.Goal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import grupobala.Database.Connection.IDBConnection.IDBConnection;
import grupobala.Database.Goal.IDBGoal.IDBGoal;
import grupobala.Entities.Goal.Goal;
import grupobala.Entities.Goal.IGoal.IGoal;

public class DBGoal implements IDBGoal {

    private IDBConnection databaseConnection;

    public DBGoal(IDBConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public IGoal addGoal(int userID, String title, double objective, Calendar expectedDate, double idealValuePerMonth) throws SQLException {
        DateFormat formateDate = new SimpleDateFormat("yyyy-MM-dd");

        String query = String.format(
            Locale.US,
            "INSERT INTO meta(titulo, idusuario, valormeta, valoratual, datalimite, valoridealpormes) VALUES ('%s', %d, %f, %f, '%s', %f)",
            title,
            userID,
            objective,
            0.0,
            formateDate.format(expectedDate.getTime()),
            idealValuePerMonth
        );

        int howManyUpdates = this.databaseConnection.executeUpdate(query);

        if (howManyUpdates == 0) {
            throw new SQLException("Essa meta já existe");
        }

        query = String.format(
            Locale.US,
            "SELECT id FROM meta WHERE idusuario = %d AND titulo = '%s'",
            userID,
            title
        );

        ResultSet result = this.databaseConnection.executeQuery(query);

        result.next();

        int goalID = result.getInt("id");

        result.close();

        IGoal goal = new Goal(goalID, title, objective, expectedDate.getTime(), idealValuePerMonth);

        return goal;
    }

    @Override
    public int removeGoal(int userID, int goalId) throws SQLException {
        String query = String.format(
            Locale.US,
            "DELETE FROM meta WHERE id = %d AND idusuario = %d",
            goalId,
            userID
        );

        int howManyUpdates = this.databaseConnection.executeUpdate(query);

        if (howManyUpdates == 0) {
            throw new SQLException("Meta não existe");
        }

        return howManyUpdates;
    }

    @Override
    public int updateGoal(int userID, IGoal goal) throws SQLException {
        DateFormat formateDate = new SimpleDateFormat("yyyy-MM-dd");

        String query = String.format(
            Locale.US,
            "UPDATE meta SET titulo = '%s', valormeta = %f, valoratual = %f, datalimite = '%s', valoridealpormes = %f WHERE idusuario = %d AND id = %d",
            goal.getTitle(),
            goal.getObjective(),
            goal.getAmountDeposited(),
            formateDate.format(goal.getExpectedDate()),
            goal.getIdealValuePerMonth(),
            userID,
            goal.getID()
        );

        int howManyUpdates = this.databaseConnection.executeUpdate(query);

        if (howManyUpdates == 0) {
            throw new SQLException("Meta não existe");
        }

        return howManyUpdates;
    }
    
}
