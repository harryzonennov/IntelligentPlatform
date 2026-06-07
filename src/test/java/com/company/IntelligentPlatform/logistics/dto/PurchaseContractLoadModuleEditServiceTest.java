package com.company.IntelligentPlatform.logistics.dto;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for GET /purchaseContract/loadModuleEditService?uuid={uuid}.
 *
 * <p><b>Prerequisites (local MySQL):</b>
 * <ul>
 *   <li>Replace {@code REPLACE_WITH_KNOWN_CONTRACT_UUID} with the UUID of any
 *       PurchaseContract row in your local database for client {@code C001}.</li>
 *   <li>For TC-LO-PURCON-EDIT-003, replace {@code REPLACE_WITH_OTHER_CLIENT_CONTRACT_UUID}
 *       with a contract UUID that belongs to a different client.</li>
 *   <li>For TC-LO-PURCON-EDIT-006, replace {@code REPLACE_WITH_CONTRACT_UUID_WITH_2_ITEMS}
 *       with a contract UUID that has exactly 2 PurchaseContractMaterialItem
 *       rows, and set {@code EXPECTED_ITEM_COUNT} accordingly.</li>
 *   <li>For TC-LO-PURCON-EDIT-007, replace {@code REPLACE_WITH_SUBMITTED_APPROVED_CONTRACT_UUID}
 *       with a contract that has been through submit + approve actions.</li>
 *   <li>A user {@code no_edit_user} / {@code noedit123} with only ACID_VIEW
 *       (no ACID_EDIT) on PurchaseContract for TC-LO-PURCON-EDIT-004.</li>
 * </ul>
 */
class PurchaseContractLoadModuleEditServiceTest extends BaseIntegrationTest {

    private static final String ENDPOINT = "/purchaseContract/loadModuleEditService";

    // Replace these placeholders with real UUIDs from your local database.
    private static final String KNOWN_CONTRACT_UUID =
            "REPLACE_WITH_KNOWN_CONTRACT_UUID";
    private static final String OTHER_CLIENT_CONTRACT_UUID =
            "REPLACE_WITH_OTHER_CLIENT_CONTRACT_UUID";
    private static final String CONTRACT_UUID_WITH_ITEMS =
            "REPLACE_WITH_CONTRACT_UUID_WITH_2_ITEMS";
    private static final int    EXPECTED_ITEM_COUNT = 2;
    private static final String SUBMITTED_APPROVED_CONTRACT_UUID =
            "REPLACE_WITH_SUBMITTED_APPROVED_CONTRACT_UUID";

    // -----------------------------------------------------------------------
    // TC-LO-PURCON-EDIT-001: Load existing contract — full UI model returned
    // -----------------------------------------------------------------------

    @Test
    void TC_LO_PURCON_EDIT_001_loadExistingContract_returnsFullUIModel() {
        if (KNOWN_CONTRACT_UUID.startsWith("REPLACE")) {
            System.out.println("[TC-LO-PURCON-EDIT-001] Skipped: set KNOWN_CONTRACT_UUID.");
            return;
        }

        given(sessionSpec)
        .when()
                .get(ENDPOINT + "?uuid=" + KNOWN_CONTRACT_UUID)
        .then()
                .statusCode(200)
                // root document
                .body("purchaseContractUIModel",              notNullValue())
                .body("purchaseContractUIModel.uuid",         equalTo(KNOWN_CONTRACT_UUID))
                .body("purchaseContractUIModel.client",       notNullValue())
                // nested lists are present (may be empty)
                .body("purchaseContractMaterialItemUIModelList", notNullValue())
                .body("purchaseContractAttachmentUIModelList",   notNullValue())
                // no error
                .body("error",        nullValue())
                .body("errorMessage", nullValue());
    }

    // -----------------------------------------------------------------------
    // TC-LO-PURCON-EDIT-002: UUID does not exist — error JSON returned
    // -----------------------------------------------------------------------

    @Test
    void TC_LO_PURCON_EDIT_002_nonExistentUUID_returnsErrorJSON() {
        given(sessionSpec)
        .when()
                .get(ENDPOINT + "?uuid=non-existent-uuid-0000000000000000")
        .then()
                .statusCode(200)
                .body(anyOf(
                        hasKey("error"),
                        hasKey("errorMessage")
                ))
                .body("purchaseContractUIModel", nullValue());
    }

    // -----------------------------------------------------------------------
    // TC-LO-PURCON-EDIT-003: UUID belongs to a different client — authorization error
    // -----------------------------------------------------------------------

    @Test
    void TC_LO_PURCON_EDIT_003_differentClientUUID_returnsAuthorizationError() {
        if (OTHER_CLIENT_CONTRACT_UUID.startsWith("REPLACE")) {
            System.out.println("[TC-LO-PURCON-EDIT-003] Skipped: set OTHER_CLIENT_CONTRACT_UUID.");
            return;
        }

        given(sessionSpec)
        .when()
                .get(ENDPOINT + "?uuid=" + OTHER_CLIENT_CONTRACT_UUID)
        .then()
                .statusCode(200)
                .body(anyOf(hasKey("error"), hasKey("errorMessage")))
                .body("purchaseContractUIModel", nullValue());
    }

    // -----------------------------------------------------------------------
    // TC-LO-PURCON-EDIT-004: User lacks ACID_EDIT — authorization error
    // -----------------------------------------------------------------------

    @Test
    void TC_LO_PURCON_EDIT_004_noEditAuthorization_returnsAuthorizationError() {
        if (KNOWN_CONTRACT_UUID.startsWith("REPLACE")) {
            System.out.println("[TC-LO-PURCON-EDIT-004] Skipped: set KNOWN_CONTRACT_UUID.");
            return;
        }

        String loginBody = buildLoginBody("no_edit_user", "noedit123", getTestClient());
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
            System.out.println("[TC-LO-PURCON-EDIT-004] Skipped: no_edit_user does not exist in local DB.");
            return;
        }

        given()
                .filter(restrictedCookies)
        .when()
                .get(ENDPOINT + "?uuid=" + KNOWN_CONTRACT_UUID)
        .then()
                .statusCode(200)
                .body(anyOf(hasKey("error"), hasKey("errorMessage")))
                .body("purchaseContractUIModel", nullValue());
    }

    // -----------------------------------------------------------------------
    // TC-LO-PURCON-EDIT-005: Blank uuid parameter — error JSON, no stack trace
    // -----------------------------------------------------------------------

    @Test
    void TC_LO_PURCON_EDIT_005_blankUUID_returnsErrorJSON() {
        given(sessionSpec)
        .when()
                .get(ENDPOINT + "?uuid=")
        .then()
                .statusCode(200)
                .body(anyOf(hasKey("error"), hasKey("errorMessage")));
    }

    // -----------------------------------------------------------------------
    // TC-LO-PURCON-EDIT-006: Nested material items count matches database
    // -----------------------------------------------------------------------

    @Test
    void TC_LO_PURCON_EDIT_006_materialItems_nestedListCountMatchesExpected() {
        if (CONTRACT_UUID_WITH_ITEMS.startsWith("REPLACE")) {
            System.out.println("[TC-LO-PURCON-EDIT-006] Skipped: set CONTRACT_UUID_WITH_ITEMS.");
            return;
        }

        Response response = given(sessionSpec)
                .when()
                .get(ENDPOINT + "?uuid=" + CONTRACT_UUID_WITH_ITEMS)
                .then().statusCode(200).extract().response();

        assertThat(response.jsonPath().getString("error"), nullValue());

        List<?> items = response.jsonPath().getList(
                "purchaseContractMaterialItemUIModelList");
        assertNotNull(items, "purchaseContractMaterialItemUIModelList must not be null");
        assertEquals(EXPECTED_ITEM_COUNT, items.size(),
                "Item count must match what is in the database");

        // Each item must reference the same root contract
        List<String> rootUUIDs = response.jsonPath().getList(
                "purchaseContractMaterialItemUIModelList.rootNodeUUID");
        if (rootUUIDs != null) {
            rootUUIDs.forEach(rootUUID ->
                assertThat("Each item's rootNodeUUID must match the contract UUID",
                        rootUUID, equalTo(CONTRACT_UUID_WITH_ITEMS)));
        }
    }

    // -----------------------------------------------------------------------
    // TC-LO-PURCON-EDIT-007: Submitted + approved contract carries action node UUIDs
    // -----------------------------------------------------------------------

    @Test
    void TC_LO_PURCON_EDIT_007_submittedAndApprovedContract_actionNodesArePresent() {
        if (SUBMITTED_APPROVED_CONTRACT_UUID.startsWith("REPLACE")) {
            System.out.println("[TC-LO-PURCON-EDIT-007] Skipped: set SUBMITTED_APPROVED_CONTRACT_UUID.");
            return;
        }

        Response response = given(sessionSpec)
                .when()
                .get(ENDPOINT + "?uuid=" + SUBMITTED_APPROVED_CONTRACT_UUID)
                .then().statusCode(200).extract().response();

        assertThat(response.jsonPath().getString("error"), nullValue());
        assertThat("submittedBy action node must be present",
                response.jsonPath().getString("submittedBy.uuid"), notNullValue());
        assertThat("approvedBy action node must be present",
                response.jsonPath().getString("approvedBy.uuid"), notNullValue());
    }
}
