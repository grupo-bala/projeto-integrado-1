package grupobala.Database;

import static org.junit.jupiter.api.Assertions.*;

import grupobala.Database.Setup.Setup;
import grupobala.SetupForTest.SetupForTest;

import java.sql.*;
import java.util.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

@Order(1)
public class TestSetup {

    @Test
    public void testDBCreation() throws SQLException {
        SetupForTest.removeFinanciDB();

        Connection connection = Setup.setup();

        String query =
            "SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname != 'pg_catalog' AND schemaname != 'information_schema'";

        ArrayList<String> expectedTables = new ArrayList<>(
            Arrays.asList(
                "movimentacao",
                "categoria",
                "usuario",
                "meta",
                "aulaassistida",
                "aula"
            )
        );

        ArrayList<String> tables = new ArrayList<>();

        Statement statement = connection.createStatement();

        ResultSet queryResult = statement.executeQuery(query);

        connection.close();

        while (queryResult.next()) {
            tables.add(queryResult.getString("tablename"));
        }

        expectedTables.sort(Comparator.naturalOrder());
        tables.sort(Comparator.naturalOrder());

        assertEquals(expectedTables, tables);
    }
}
