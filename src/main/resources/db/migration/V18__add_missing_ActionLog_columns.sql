-- Add missing actionCode and updateFieldsArray columns to materialActionLog
ALTER TABLE `platform`.`materialActionLog`
  ADD COLUMN IF NOT EXISTS `actionCode` int DEFAULT '0',
  ADD COLUMN IF NOT EXISTS `updateFieldsArray` mediumtext DEFAULT NULL;

-- Add missing actionCode and updateFieldsArray columns to RegisteredProductActionLog
ALTER TABLE `platform`.`RegisteredProductActionLog`
  ADD COLUMN IF NOT EXISTS `actionCode` int DEFAULT '0',
  ADD COLUMN IF NOT EXISTS `updateFieldsArray` mediumtext DEFAULT NULL;
