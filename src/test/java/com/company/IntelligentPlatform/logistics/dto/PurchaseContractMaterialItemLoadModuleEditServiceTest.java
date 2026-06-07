package com.company.IntelligentPlatform.logistics.dto;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for GET /purchaseContractMaterialItem/loadModuleEditService?uuid={uuid}.
 *
 * <p><b>Prerequisites (local MySQL):</b>
 * <ul>
 *   <li>Replace {@code REPLACE_WITH_KNOWN_ITEM_UUID} with the UUID of any
 *       PurchaseContractMaterialItem row for client {@code C001}.</li>
 *   <li>Replace {@code REPLACE_WITH_PARENT_CONTRACT_UUID} with the
 *       rootNodeUUID of that item (i.e. the parent PurchaseContract UUID).</li>
 *   <li>For TC-LO-PURCON-ITEM-003, replace {@code REPLACE_WITH_CONTRACT_UUID} with a root
 *       PurchaseContract UUID (to verify wrong entity type is rejected).</li>
 *   <li>For TC-LO-PURCON-ITEM-005, replace the UUIDs and set {@code ITEM_AMOUNT},
 *       {@code ITEM_UNIT_PRICE}, and {@code ITEM_EXPECTED_PRICE} to values
 *       matching a known row in the database.</li>
 *   <li>A user {@code no_edit_user} / {@code noedit123} with no ACID_EDIT
 *       on PurchaseContract for TC-LO-PURCON-ITEM-004.</li>
 * </ul>
 */
class PurchaseContractMaterialItemLoadModuleEditServiceTest extends BaseIntegrationTest {

    private static final String ENDPOINT =
            "/purchaseContractMaterialItem/loadModuleEditService";

    // Replace these placeholders with real values from your local database.
    private static final String KNOWN_ITEM_UUID =
            "REPLACE_WITH_KNOWN_ITEM_UUID";
    private static final String PARENT_CONTRACT_UUID =
            "REPLACE_WITH_PARENT_CONTRACT_UUID";
    private static final String KNOWN_CONTRACT_UUID =
            "REPLACE_WITH_CONTRACT_UUID";

    // For TC-I-05 — choose a row where you know the exact amounts.
    private static final String PRICE_ITEM_UUID   = "REPLACE_WITH_PRICE_ITEM_UUID";
    private static final double ITEM_AMOUNT        = 10.0;
    private static final double ITEM_UNIT_PRICE    = 5.0;
    private static final double ITEM_EXPECTED_PRICE = ITEM_AMOUNT * ITEM_UNIT_PRICE;

    // -----------------------------------------------------------------------
    // TC-LO-PURCON-ITEM-001: Load existing material item — full UI model returned
    // -----------------------------------------------------------------------

    @Test
    void TC_LO_PURCON_ITEM_001_loadExistingItem_returnsFullUIModel() {
        if (KNOWN_ITEM_UUID.startsWith("REPLACE")) {
            System.out.println("[TC-LO-PURCON-ITEM-001] Skipped: set KNOWN_ITEM_UUID.");
            return;
        }

        given(sessionSpec)
        .when()
                .get(ENDPOINT + "?uuid=" + KNOWN_ITEM_UUID)
        .then()
                .statusCode(200)
                .body("purchaseContractMaterialItemUIModel",           notNullValue())
                .body("purchaseContractMaterialItemUIModel.uuid",      equalTo(KNOWN_ITEM_UUID))
                .body("purchaseContractMaterialItemUIModel.rootNodeUUID", notNullValue())
                .body("purchaseContractMaterialItemAttachmentUIModelList", notNullValue())
                .body("error",        nullValue())
                .body("errorMessage", nullValue());
    }

    // -----------------------------------------------------------------------
    // TC-LO-PURCON-ITEM-002: UUID does not exist — error JSON returned
    // -----------------------------------------------------------------------

    @Test
    void TC_LO_PURCON_ITEM_002_nonExistentUUID_returnsErrorJSON() {
        given(sessionSpec)
        .when()
                .get(ENDPOINT + "?uuid=non-existent-item-uuid-000000000000")
        .then()
                .statusCode(200)
                .body(anyOf(
                        hasKey("error"),
                        hasKey("errorMessage")
                ))
                .body("purchaseContractMaterialItemUIModel", nullValue());
    }

    // -----------------------------------------------------------------------
    // TC-LO-PURCON-ITEM-003: Passing a root contract UUID to the item endpoint — wrong type
    // -----------------------------------------------------------------------

    @Test
    void TC_LO_PURCON_ITEM_003_rootContractUUID_isRejectedByItemEndpoint() {
        if (KNOWN_CONTRACT_UUID.startsWith("REPLACE")) {
            System.out.println("[TC-LO-PURCON-ITEM-003] Skipped: set KNOWN_CONTRACT_UUID.");
            return;
        }

        // A root PurchaseContract node has nodeName = NODENAME_ROOT ("root"),
        // not "PurchaseContractMaterialItem". The manager's getEntityNodeByKey
        // for nodeName=PurchaseContractMaterialItem should not find it.
        given(sessionSpec)
        .when()
                .get(ENDPOINT + "?uuid=" + KNOWN_CONTRACT_UUID)
        .then()
                .statusCode(200)
                .body(anyOf(hasKey("error"), hasKey("errorMessage")));
    }

    // -----------------------------------------------------------------------
    // TC-LO-PURCON-ITEM-004: User lacks ACID_EDIT on parent contract — authorization error
    // -----------------------------------------------------------------------

    @Test
    void TC_LO_PURCON_ITEM_004_noEditAuthorization_returnsAuthorizationError() {
        if (KNOWN_ITEM_UUID.startsWith("REPLACE")) {
            System.out.println("[TC-LO-PURCON-ITEM-004] Skipped: set KNOWN_ITEM_UUID.");
            return;
        }

        String loginBody = buildLoginBody("no_edit_user", "noedit123", getTestClient());
        io.restassured.filter.cookie.CookieFilter restrictedCookies =
                new io.restassured.filter.cookie.CookieFilter();

        String loginResponse = given()
                .filter(restrictedCookies)
                .contentType(io.restassured.http.ContentType.JSON)
                .header("accept-language", "en")
                .body(loginBody)
                .when().post("/common/loginService")
                .then().statusCode(200)
                .extract().body().asString();

        if (loginResponse.contains("\"error\"")) {
            System.out.println("[TC-LO-PURCON-ITEM-004] Skipped: no_edit_user does not exist in local DB.");
            return;
        }

        given()
                .filter(restrictedCookies)
        .when()
                .get(ENDPOINT + "?uuid=" + KNOWN_ITEM_UUID)
        .then()
                .statusCode(200)
                .body(anyOf(hasKey("error"), hasKey("errorMessage")))
                .body("purchaseContractMaterialItemUIModel", nullValue());
    }

    // -----------------------------------------------------------------------
    // TC-LO-PURCON-ITEM-005: itemPrice == amount × unitPrice
    // -----------------------------------------------------------------------

    @Test
    void TC_LO_PURCON_ITEM_005_itemPrice_equalsAmountMultipliedByUnitPrice() {
        if (PRICE_ITEM_UUID.startsWith("REPLACE")) {
            System.out.println("[TC-LO-PURCON-ITEM-005] Skipped: set PRICE_ITEM_UUID and price constants.");
            return;
        }

        Response response = given(sessionSpec)
                .when()
                .get(ENDPOINT + "?uuid=" + PRICE_ITEM_UUID)
                .then().statusCode(200).extract().response();

        assertThat(response.jsonPath().getString("error"), nullValue());

        double amount    = response.jsonPath().getDouble(
                "purchaseContractMaterialItemUIModel.amount");
        double unitPrice = response.jsonPath().getDouble(
                "purchaseContractMaterialItemUIModel.unitPrice");
        double itemPrice = response.jsonPath().getDouble(
                "purchaseContractMaterialItemUIModel.itemPrice");

        assertEquals(ITEM_AMOUNT,         amount,    0.001, "amount mismatch");
        assertEquals(ITEM_UNIT_PRICE,      unitPrice, 0.001, "unitPrice mismatch");
        assertEquals(ITEM_EXPECTED_PRICE,  itemPrice, 0.001,
                "itemPrice must equal amount × unitPrice");
    }

    // -----------------------------------------------------------------------
    // TC-LO-PURCON-ITEM-006: rootNodeUUID matches parent PurchaseContract
    // -----------------------------------------------------------------------

    @Test
    void TC_LO_PURCON_ITEM_006_rootNodeUUID_matchesParentContract() {
        if (KNOWN_ITEM_UUID.startsWith("REPLACE") || PARENT_CONTRACT_UUID.startsWith("REPLACE")) {
            System.out.println("[TC-LO-PURCON-ITEM-006] Skipped: set KNOWN_ITEM_UUID and PARENT_CONTRACT_UUID.");
            return;
        }

        Response itemResponse = given(sessionSpec)
                .when()
                .get(ENDPOINT + "?uuid=" + KNOWN_ITEM_UUID)
                .then().statusCode(200).extract().response();

        assertThat(itemResponse.jsonPath().getString("error"), nullValue());

        String rootNodeUUID = itemResponse.jsonPath().getString(
                "purchaseContractMaterialItemUIModel.rootNodeUUID");
        assertEquals(PARENT_CONTRACT_UUID, rootNodeUUID,
                "rootNodeUUID must match the parent PurchaseContract UUID");

        // Cross-verify: the parent contract must be loadable and have the same UUID
        Response contractResponse = given(sessionSpec)
                .when()
                .get("/purchaseContract/loadModuleEditService?uuid=" + rootNodeUUID)
                .then().statusCode(200).extract().response();

        assertThat(contractResponse.jsonPath().getString("error"), nullValue());
        assertThat(contractResponse.jsonPath().getString(
                "purchaseContractUIModel.uuid"),
                equalTo(PARENT_CONTRACT_UUID));
    }
}
