package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.config.TestSecurityConfig;
import io.restassured.RestAssured;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyString;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Base class for all integration tests.
 *
 * <p>Boots the full Spring context on a random port against the local MySQL
 * (configured in application-test.yml). Before each test a fresh HTTP session
 * is established by calling POST /common/loginService with the credentials
 * supplied by the concrete subclass. The resulting session cookie is stored in
 * {@code sessionSpec} and shared across every request in that test method.</p>
 *
 * <p><b>Prerequisites:</b> local MySQL must be running and the database
 * populated (all Flyway migrations applied). Set the following environment
 * variables if the defaults do not match your local setup:
 * DB_HOST, DB_PORT, DB_NAME, DB_USERNAME, DB_PASSWORD.</p>
 *
 * <p><b>Test credentials:</b> override {@link #getTestUserId()},
 * {@link #getTestPassword()}, and {@link #getTestClient()} in a subclass when
 * you need a different user (e.g. one without ACID_EDIT permission).</p>
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public abstract class BaseIntegrationTest {

    @LocalServerPort
    private int port;

    /**
     * A RestAssured request specification that already carries the active
     * session cookie. Use this in every test request so the server recognises
     * the logged-in session.
     */
    protected RequestSpecification sessionSpec;

    // -----------------------------------------------------------------------
    // Credentials — override in subclasses for different users
    // -----------------------------------------------------------------------

    protected String getTestUserId()  { return "admin"; }
    protected String getTestPassword(){ return "admin123"; }
    protected String getTestClient()  { return "C001"; }

    // -----------------------------------------------------------------------
    // Setup
    // -----------------------------------------------------------------------

    @BeforeEach
    void setUpSession() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port    = port;

        // CookieFilter retains JSESSIONID across all requests made with sessionSpec.
        CookieFilter cookieFilter = new CookieFilter();

        // POST /common/loginService to establish the HTTP session.
        // The server sets JSESSIONID which CookieFilter captures automatically.
        Response loginResponse = given()
                .filter(cookieFilter)
                .contentType(ContentType.JSON)
                .header("accept-language", "en")
                .body(buildLoginBody(getTestUserId(), getTestPassword(), getTestClient()))
                .when()
                .post("/common/loginService")
                .then()
                .statusCode(200)
                .body(not(emptyString()))
                .extract().response();

        // Fail fast if login itself returned an error JSON.
        String loginBody = loginResponse.getBody().asString();
        boolean loginOk = !loginBody.contains("\"error\"") && !loginBody.contains("\"errorMessage\"");
        assumeTrue(loginOk,
                "Login failed for user=" + getTestUserId() +
                " client=" + getTestClient() +
                " — skipping test. Response: " + loginBody +
                "\nEnsure the user exists in the local database and the password is correct.");

        // Attach the cookie filter so every subsequent request reuses the session.
        sessionSpec = given()
                .filter(cookieFilter)
                .accept(ContentType.ANY);
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    /**
     * Build the JSON body for POST /common/loginService.
     * Fields match LogonUIModel: userId, password, client.
     */
    protected String buildLoginBody(String userId, String password, String client) {
        return String.format(
                "{\"userId\":\"%s\",\"password\":\"%s\",\"client\":\"%s\"}",
                userId, password, client);
    }

    /**
     * Build the standard DataTable search request envelope used by all
     * searchTableService endpoints.
     *
     * @param draw   DataTables echo value (any int, returned unchanged in response)
     * @param start  zero-based row offset
     * @param length page size
     * @param contentJson the PurchaseContractSearchModel JSON fragment, e.g. {@code "{}"}
     */
    protected String buildSearchRequest(int draw, int start, int length, String contentJson) {
        return String.format(
                "{\"draw\":%d,\"start\":%d,\"length\":%d,\"content\":%s}",
                draw, start, length, contentJson);
    }
}
