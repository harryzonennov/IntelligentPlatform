-- Add missing status column to RegisteredProduct
ALTER TABLE `platform`.`RegisteredProduct`
    ADD COLUMN `status` int NOT NULL DEFAULT 0;
