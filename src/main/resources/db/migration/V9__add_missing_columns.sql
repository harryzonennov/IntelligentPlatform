-- Add missing doc-flow columns to repairprodorder (present in productionOrder but absent here)
ALTER TABLE `production`.`repairprodorder`
    ADD COLUMN `prevDocType` int DEFAULT '0',
    ADD COLUMN `prevDocUUID` varchar(100) DEFAULT NULL,
    ADD COLUMN `prevProfDocType` int DEFAULT '0',
    ADD COLUMN `prevProfDocUUID` varchar(100) DEFAULT NULL,
    ADD COLUMN `nextDocType` int DEFAULT '0',
    ADD COLUMN `nextDocUUID` varchar(100) DEFAULT NULL,
    ADD COLUMN `nextProfDocType` int DEFAULT '0',
    ADD COLUMN `nextProfDocUUID` varchar(100) DEFAULT NULL;