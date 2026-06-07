-- V5: Add missing columns to Organization table
-- Organization extends CorporateAccount extends Account; Account defines email and webPage
-- These were present in HostCompany but missing from the Organization DDL

ALTER TABLE `platform`.`Organization`
  ADD COLUMN `email` varchar(100) DEFAULT NULL,
  ADD COLUMN `webPage` varchar(100) DEFAULT NULL;
