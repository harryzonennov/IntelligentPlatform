-- V6: Add missing columns to SystemAuthorizationObject table
-- SystemAuthorizationObject extends AuthorizationObject which defines subSystemAuthorNeed and systemAuthorCheck
-- Hibernate UNION query requires both columns present in both tables

ALTER TABLE `platform`.`SystemAuthorizationObject`
  ADD COLUMN `subSystemAuthorNeed` int DEFAULT 2,
  ADD COLUMN `systemAuthorCheck` int DEFAULT 1;
