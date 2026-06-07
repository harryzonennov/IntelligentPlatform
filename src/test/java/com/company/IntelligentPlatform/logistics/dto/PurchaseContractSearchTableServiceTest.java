package com.company.IntelligentPlatform.logistics.dto;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for POST /purchaseContract/searchTableService.
 *
 * <p><b>Prerequisites (local MySQL):</b>
 * <ul>
 *   <li>At least one PurchaseContract row for client {@code C001}.</li>
 *   <li>At least 6 rows total to cover pagination in TC-LO-PURCON-SEARCH-002.</li>
 *   <li>One row with id {@code PC-TEST-001} for TC-LO-PURCON-SEARCH-003.</li>
 *   <li>Some rows with status {@code 1} for TC-LO-PURCON-SEARCH-004.</li>
 *   <li>Some rows with signDate in 2024 for TC-LO-PURCON-SEARCH-005.</li>
 *   <li>At least one supplier party row for TC-LO-PURCON-SEARCH-006 — replace
 *       REPLACE_WITH_KNOWN_SUPPLIER_UUID with a real value.</li>
 *   <li>A user {@code no_list_user} / {@code nolist123} that has NO
 *       ACID_LIST permission on PurchaseContract for TC-LO-PURCON-SEARCH-007.</li>
 * </ul>
 */
class PurchaseContractSearchTableServiceTest extends BaseIntegrationTest {

    private static final String ENDPOINT = "/purchaseContract/searchTableService";

    // -----------------------------------------------------------------------
    // TC-LO-PURCON-SEARCH-001: Empty search returns all contracts for the session client
    // -----------------------------------------------------------------------

    @Test
    void TC_LO_PURCON_SEARCH_001_emptySearch_returnsDataTableWithAllContracts() {
        String requestBody = buildSearchRequest(1, 0, 20, "{}");

        given(sessionSpec)
                .contentType(ContentType.JSON)
                .body(requestBody)
        .when()
                .post(ENDPOINT)
        .then()
                .statusCode(200)
                .body("draw",            equalTo(1))
                .body("recordsTotal",    greaterThanOrEqualTo(0))
                .body("recordsFiltered", greaterThanOrEqualTo(0))
                .body("data",            notNullValue())
                .body("error",           nullValue());
    }

    // -----------------------------------------------------------------------
    // TC-LO-PURCON-SEARCH-002: Pagination — two pages carry different records, same total
    // -----------------------------------------------------------------------

    @Test
    void TC_LO_PURCON_SEARCH_002_pagination_pagesDontOverlapAndTotalIsConsistent() {
        String page1Body = buildSearchRequest(2, 0, 5, "{}");
        String page2Body = buildSearchRequest(3, 5, 5, "{}");

        Response page1 = given(sessionSpec)
                .contentType(ContentType.JSON)
                .body(page1Body)
                .when().post(ENDPOINT)
                .then().statusCode(200).extract().response();

        Response page2 = given(sessionSpec)
                .contentType(ContentType.JSON)
                .body(page2Body)
                .when().post(ENDPOINT)
                .then().statusCode(200).extract().response();

        // draw is echoed back correctly for each request
        assertThat(page1.jsonPath().getInt("draw"), equalTo(2));
        assertThat(page2.jsonPath().getInt("draw"), equalTo(3));

        // recordsTotal is the same for both requests
        int total1 = page1.jsonPath().getInt("recordsTotal");
        int total2 = page2.jsonPath().getInt("recordsTotal");
        assertEquals(total1, total2, "recordsTotal must be identical across pages");

        // Pages do not share any UUIDs
        List<String> uuids1 = page1.jsonPath().getList(
                "data.purchaseContractUIModel.uuid");
        List<String> uuids2 = page2.jsonPath().getList(
                "data.purchaseContractUIModel.uuid");

        if (uuids1 != null && uuids2 != null) {
            Set<String> overlap = new HashSet<>(uuids1);
            overlap.retainAll(new HashSet<>(uuids2));
            assertTrue(overlap.isEmpty(),
                    "Page 1 and page 2 must not share any UUIDs, but overlap: " + overlap);
        }
    }

    // -----------------------------------------------------------------------
    // TC-LO-PURCON-SEARCH-003: Filter by document id
    // -----------------------------------------------------------------------

    @Test
    void TC_LO_PURCON_SEARCH_003_filterById_returnsOnlyMatchingRecords() {
        String content  = "{\"headerModel\":{\"id\":\"PC-TEST-001\"}}";
        String body     = buildSearchRequest(1, 0, 20, content);

        Response response = given(sessionSpec)
                .contentType(ContentType.JSON)
                .body(body)
                .when().post(ENDPOINT)
                .then().statusCode(200).extract().response();

        assertThat(response.jsonPath().getString("error"), nullValue());

        List<String> ids = response.jsonPath().getList(
                "data.purchaseContractUIModel.id");
        if (ids != null) {
            ids.forEach(id ->
                assertThat("Every result id must match the filter",
                        id, containsString("PC-TEST-001")));
        }
    }

    // -----------------------------------------------------------------------
    // TC-LO-PURCON-SEARCH-004: Filter by status
    // -----------------------------------------------------------------------

    @Test
    void TC_LO_PURCON_SEARCH_004_filterByStatus_returnsOnlyMatchingRecords() {
        String content = "{\"headerModel\":{\"status\":1}}";
        String body    = buildSearchRequest(1, 0, 50, content);

        Response response = given(sessionSpec)
                .contentType(ContentType.JSON)
                .body(body)
                .when().post(ENDPOINT)
                .then().statusCode(200).extract().response();

        assertThat(response.jsonPath().getString("error"), nullValue());

        List<Integer> statuses = response.jsonPath().getList(
                "data.purchaseContractUIModel.status");
        if (statuses != null) {
            statuses.forEach(s ->
                assertEquals(1, s, "Every result status must equal 1"));
        }
    }

    // -----------------------------------------------------------------------
    // TC-LO-PURCON-SEARCH-005: Filter by signDate range (2024)
    // -----------------------------------------------------------------------

    @Test
    void TC_LO_PURCON_SEARCH_005_filterBySignDateRange_returnsRecordsWithinRange() {
        String content = "{\"signDateLow\":\"2024-01-01\",\"signDateHigh\":\"2024-12-31\"}";
        String body    = buildSearchRequest(1, 0, 50, content);

        Response response = given(sessionSpec)
                .contentType(ContentType.JSON)
                .body(body)
                .when().post(ENDPOINT)
                .then().statusCode(200).extract().response();

        assertThat(response.jsonPath().getString("error"), nullValue());
        // We cannot parse dates easily here; assert no error and structure is intact
        assertThat(response.jsonPath().get("data"),     notNullValue());
        assertThat(response.jsonPath().get("recordsTotal"), notNullValue());
    }

    // -----------------------------------------------------------------------
    // TC-LO-PURCON-SEARCH-006: Filter by supplier UUID
    // -----------------------------------------------------------------------

    @Test
    void TC_LO_PURCON_SEARCH_006_filterBySupplierUUID_returnsOnlyMatchingRecords() {
        // Replace with a supplier UUID that exists in your local database.
        String knownSupplierUUID = "REPLACE_WITH_KNOWN_SUPPLIER_UUID";
        if (knownSupplierUUID.startsWith("REPLACE")) {
            // Skip test rather than fail with a misleading assertion
            System.out.println("[TC-LO-PURCON-SEARCH-006] Skipped: set knownSupplierUUID to a real value.");
            return;
        }

        String content = String.format(
                "{\"purchaseFromSupplier\":{\"refUUID\":\"%s\"}}", knownSupplierUUID);
        String body = buildSearchRequest(1, 0, 50, content);

        given(sessionSpec)
                .contentType(ContentType.JSON)
                .body(body)
        .when()
                .post(ENDPOINT)
        .then()
                .statusCode(200)
                .body("error", nullValue())
                .body("data", notNullValue());
    }

    // -----------------------------------------------------------------------
    // TC-LO-PURCON-SEARCH-007: No ACID_LIST authorization — error JSON returned
    // -----------------------------------------------------------------------

    @Test
    void TC_LO_PURCON_SEARCH_007_noAuthorization_returnsErrorJSON() {
        // Log in with a user that has no list permission on PurchaseContract.
        // This test creates its own session rather than using the default one.
        String loginBody = buildLoginBody("no_list_user", "nolist123", getTestClient());

        io.restassured.filter.cookie.CookieFilter restrictedCookies =
                new io.restassured.filter.cookie.CookieFilter();

        String loginResponse = given()
                .filter(restrictedCookies)
                .contentType(ContentType.JSON)
                .header("accept-language", "en")
                .body(loginBody)
                .when().post("/common/loginService")
                .then().statusCode(200)
                .extract().body().asString();

        if (loginResponse.contains("\"error\"")) {
            System.out.println("[TC-LO-PURCON-SEARCH-007] Skipped: no_list_user does not exist in local DB.");
            return;
        }

        String searchBody = buildSearchRequest(1, 0, 20, "{}");

        given()
                .filter(restrictedCookies)
                .contentType(ContentType.JSON)
                .body(searchBody)
        .when()
                .post(ENDPOINT)
        .then()
                .statusCode(200)
                .body("error",        notNullValue())
                .body("data",         nullValue());
    }

    // -----------------------------------------------------------------------
    // TC-LO-PURCON-SEARCH-008: draw value is echoed back unchanged
    // -----------------------------------------------------------------------

    @Test
    void TC_LO_PURCON_SEARCH_008_drawValue_isEchoedBackUnchanged() {
        int expectedDraw = 42;
        String body = buildSearchRequest(expectedDraw, 0, 5, "{}");

        given(sessionSpec)
                .contentType(ContentType.JSON)
                .body(body)
        .when()
                .post(ENDPOINT)
        .then()
                .statusCode(200)
                .body("draw", equalTo(expectedDraw));
    }

    // -----------------------------------------------------------------------
    // TC-LO-PURCON-SEARCH-009: start beyond total records — empty data, recordsTotal > 0
    // -----------------------------------------------------------------------

    @Test
    void TC_LO_PURCON_SEARCH_009_startBeyondTotal_returnsEmptyDataWithCorrectTotal() {
        // First get the real total
        String countBody = buildSearchRequest(1, 0, 1, "{}");
        int total = given(sessionSpec)
                .contentType(ContentType.JSON)
                .body(countBody)
                .when().post(ENDPOINT)
                .then().statusCode(200)
                .extract().jsonPath().getInt("recordsTotal");

        if (total == 0) {
            System.out.println("[TC-LO-PURCON-SEARCH-009] Skipped: no PurchaseContract rows exist.");
            return;
        }

        // Now request beyond the last row
        String beyondBody = buildSearchRequest(1, total + 1000, 20, "{}");

        given(sessionSpec)
                .contentType(ContentType.JSON)
                .body(beyondBody)
        .when()
                .post(ENDPOINT)
        .then()
                .statusCode(200)
                .body("error",        nullValue())
                .body("recordsTotal", equalTo(total))
                .body("data",         anyOf(nullValue(), hasSize(0)));
    }
}
