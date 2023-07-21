package Phase3Project;

import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ReusableMethods {

    private String baseURL = "http://localhost:8088/employees/";
    
    public ReusableMethods() {
        // Setup base URL for RestAssured
        RestAssured.baseURI = baseURL;
    }

    // Reusable method to get all employees
    public Response getEmployees() {
        return RestAssured.get(baseURL);
    }

    // Reusable method to get single employee by id
    public Response getEmployee(String id) {
        return RestAssured.get(baseURL + id);
    }

    // Reusable method to create a new employee
    public Response createNewEmployee(String name, String lastName, String salary, String email) {
		JSONObject json = new JSONObject();
		json.put("firstName", name);
		json.put("lastName", lastName);
		json.put("salary", salary);
		json.put("email", email);
		RestAssured.baseURI = baseURL;
		RequestSpecification request = RestAssured.given();
		Response response = request.contentType(ContentType.JSON).accept(ContentType.JSON).body(json.toString())
				.post();		
		return response;
		}

    // Reusable method to update an existing employee
		public Response updateEmployee(String id,String name) {
			JSONObject json = new JSONObject();
			json.put("firstName", name);
			json.put("lastName", "Dae");
			json.put("salary", "100000");
			json.put("email", "tommy@gmail.com");
			RestAssured.baseURI = baseURL;
			RequestSpecification request = RestAssured.given();
			Response response = request.contentType(ContentType.JSON).accept(ContentType.JSON).body(json.toString())
					.put(baseURL+id);
			return response;
		}

    // Reusable method to delete an employee by id
    public Response deleteEmployee(String id) {
        return RestAssured.delete(baseURL + id);
    }
}
