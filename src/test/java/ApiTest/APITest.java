package ApiTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class APITest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void test () {
        RestAssured.given()
                    .log().all()
                .when()
                    .get("/todo")
                .then()
                    .statusCode(200)
                    .log().all();
    }

    @Test
    public void deveAdicionarTarefaComSucesso () {
        RestAssured.given()
                .body("{\n" +
                        "        \"task\": \"Teste via API\",\n" +
                        "        \"dueDate\": \"2024-02-04\"\n" +
                        "    }")
                .contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .statusCode(201)
                .log().all();
    }

    @Test
    public void naoDeveAdicionarTarefaComSucesso () {
        RestAssured.given()
                .body("{\n" +
                        "        \"task\": \"Teste via API\",\n" +
                        "        \"dueDate\": \"2010-02-04\"\n" +
                        "    }")
                .contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .body("message", CoreMatchers.is("Due date must not be in past"))
                .statusCode(400);
    }
}


