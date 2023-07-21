package Phase3Project;


import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EmployeeEndToEndTest {

    @Test
    public void testEndToEnd() {
        ReusableMethods reusableMethods = new ReusableMethods();

        // Get all employees and validate response
        Response response = reusableMethods.getEmployees();
        Assert.assertEquals(response.getStatusCode(), 200);
        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.getList("employees").size(), 3);

        // Create a new employee with name as John and validate response
        response = reusableMethods.createNewEmployee("John","Dae","100000","johnDae@gmail.com");
        Assert.assertEquals(response.getStatusCode(), 201);
        jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.getString("firstName"), "John");

        // Get the employee id from the response
        String employeeId = jsonPath.getString("id");
        System.out.println(employeeId);
        
        // Get all employees and validate the employee count is 4 now
        response = reusableMethods.getEmployees();
        Assert.assertEquals(response.getStatusCode(), 200);
        jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.getList("employees").size(), 4);

        // Update the new employee's name to Tom and validate response
        response = reusableMethods.updateEmployee(employeeId, "Tom");
        Assert.assertEquals(response.getStatusCode(), 200);
        jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.getString("firstName"), "Tom");

        // Get all employees and validate that John is no longer present
        response = reusableMethods.getEmployees();
        Assert.assertEquals(response.getStatusCode(), 200);
        jsonPath = response.jsonPath();
        Assert.assertFalse(jsonPath.getString("employees").contains("John"));

        // Get single employee with the id and validate response
        response = reusableMethods.getEmployee(employeeId);
        Assert.assertEquals(response.getStatusCode(), 200);
        jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.getString("firstName"), "Tom");

        // Delete the employee with the id and validate response
        response = reusableMethods.deleteEmployee(employeeId);
        Assert.assertEquals(response.getStatusCode(), 200);

        // Get single employee with the id and validate response code is 400
        response = reusableMethods.getEmployee(employeeId);
        Assert.assertEquals(response.getStatusCode(), 400);

        // Get all employees and validate the response code is 200 and number of employees are 3
        response = reusableMethods.getEmployees();
        Assert.assertEquals(response.getStatusCode(), 200);
        jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.getList("employees").size(), 3);
    }
}



