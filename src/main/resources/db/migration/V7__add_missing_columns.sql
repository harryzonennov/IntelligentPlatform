-- Add missing display price columns to RegisteredProduct
ALTER TABLE `platform`.`RegisteredProduct`
    ADD COLUMN `purchasePriceDisplay` double NOT NULL DEFAULT 0,
    ADD COLUMN `retailPriceDisplay` double NOT NULL DEFAULT 0,
    ADD COLUMN `unitCostDisplay` double NOT NULL DEFAULT 0;
