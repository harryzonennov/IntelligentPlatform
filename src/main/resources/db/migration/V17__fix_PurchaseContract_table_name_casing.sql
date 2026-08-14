-- Fix table name casing for PurchaseContract family.
-- DDL in V2 used lowercase names; @Table annotations use camelCase (Java class name).
-- MySQL on case-sensitive filesystems treats these as different tables, causing persist failures.

RENAME TABLE
    `logistics`.`purchaseContract`                    TO `logistics`.`PurchaseContract`,
    `logistics`.`purchasecontractactionnode`          TO `logistics`.`PurchaseContractActionNode`,
    `logistics`.`purchasecontractattachment`          TO `logistics`.`PurchaseContractAttachment`,
    `logistics`.`purchasecontractmaterialitemattachment` TO `logistics`.`PurchaseContractMaterialItemAttachment`,
    `logistics`.`purchasecontractparty`               TO `logistics`.`PurchaseContractParty`;
