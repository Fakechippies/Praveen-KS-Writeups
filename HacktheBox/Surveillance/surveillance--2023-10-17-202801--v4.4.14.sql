-- MariaDB dump 10.19  Distrib 10.6.12-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: craftdb
-- ------------------------------------------------------
-- Server version	10.6.12-MariaDB-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `addresses`
--

DROP TABLE IF EXISTS `addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `addresses` (
  `id` int(11) NOT NULL,
  `ownerId` int(11) DEFAULT NULL,
  `countryCode` varchar(255) NOT NULL,
  `administrativeArea` varchar(255) DEFAULT NULL,
  `locality` varchar(255) DEFAULT NULL,
  `dependentLocality` varchar(255) DEFAULT NULL,
  `postalCode` varchar(255) DEFAULT NULL,
  `sortingCode` varchar(255) DEFAULT NULL,
  `addressLine1` varchar(255) DEFAULT NULL,
  `addressLine2` varchar(255) DEFAULT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `organizationTaxId` varchar(255) DEFAULT NULL,
  `fullName` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_kxunoegwmuocvfdfpjnanpdpxnoqpuxzpbqr` (`ownerId`),
  CONSTRAINT `fk_anujebdfxoqjusjkotucnksorolwnbtvbttx` FOREIGN KEY (`id`) REFERENCES `elements` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_kxunoegwmuocvfdfpjnanpdpxnoqpuxzpbqr` FOREIGN KEY (`ownerId`) REFERENCES `elements` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `announcements`
--

DROP TABLE IF EXISTS `announcements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `announcements` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `pluginId` int(11) DEFAULT NULL,
  `heading` varchar(255) NOT NULL,
  `body` text NOT NULL,
  `unread` tinyint(1) NOT NULL DEFAULT 1,
  `dateRead` datetime DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_smofpclqadawobytnvfsiwjgadclndyjcvfd` (`userId`,`unread`,`dateRead`,`dateCreated`),
  KEY `idx_lajwfhnzcuijnkpmtjvwpsbrynnvetrixfzq` (`dateRead`),
  KEY `fk_nelgaeulnxraadcwhzmmwqwowredmthzngtw` (`pluginId`),
  CONSTRAINT `fk_jkszkvfwgmhvkpwixahdgexjlztcqaipzjxg` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_nelgaeulnxraadcwhzmmwqwowredmthzngtw` FOREIGN KEY (`pluginId`) REFERENCES `plugins` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `assetindexdata`
--

DROP TABLE IF EXISTS `assetindexdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assetindexdata` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sessionId` int(11) NOT NULL,
  `volumeId` int(11) NOT NULL,
  `uri` text DEFAULT NULL,
  `size` bigint(20) unsigned DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `isDir` tinyint(1) DEFAULT 0,
  `recordId` int(11) DEFAULT NULL,
  `isSkipped` tinyint(1) DEFAULT 0,
  `inProgress` tinyint(1) DEFAULT 0,
  `completed` tinyint(1) DEFAULT 0,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_dkyprldrvpspuazlsmaudypxubqivihxmokk` (`sessionId`,`volumeId`),
  KEY `idx_gnsqvyqkfrnldfecpyhvgiuwzlapjluyfnuj` (`volumeId`),
  CONSTRAINT `fk_cplluryrdmegdnoflhlaffhvcktjaydxutam` FOREIGN KEY (`volumeId`) REFERENCES `volumes` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_qoorsvetxgtldnsceqjvsslkaapytpiknotw` FOREIGN KEY (`sessionId`) REFERENCES `assetindexingsessions` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `assetindexingsessions`
--

DROP TABLE IF EXISTS `assetindexingsessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assetindexingsessions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `indexedVolumes` text DEFAULT NULL,
  `totalEntries` int(11) DEFAULT NULL,
  `processedEntries` int(11) NOT NULL DEFAULT 0,
  `cacheRemoteImages` tinyint(1) DEFAULT NULL,
  `listEmptyFolders` tinyint(1) DEFAULT 0,
  `isCli` tinyint(1) DEFAULT 0,
  `actionRequired` tinyint(1) DEFAULT 0,
  `processIfRootEmpty` tinyint(1) DEFAULT 0,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `assets`
--

DROP TABLE IF EXISTS `assets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assets` (
  `id` int(11) NOT NULL,
  `volumeId` int(11) DEFAULT NULL,
  `folderId` int(11) NOT NULL,
  `uploaderId` int(11) DEFAULT NULL,
  `filename` varchar(255) NOT NULL,
  `kind` varchar(50) NOT NULL DEFAULT 'unknown',
  `alt` text DEFAULT NULL,
  `width` int(11) unsigned DEFAULT NULL,
  `height` int(11) unsigned DEFAULT NULL,
  `size` bigint(20) unsigned DEFAULT NULL,
  `focalPoint` varchar(13) DEFAULT NULL,
  `deletedWithVolume` tinyint(1) DEFAULT NULL,
  `keptFile` tinyint(1) DEFAULT NULL,
  `dateModified` datetime DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_dthmtosiitxnwrdcsmqdhyljmeqwfclmlfzb` (`filename`,`folderId`),
  KEY `idx_wlsckulkzsykngdtpdeehroxrqvrzeuzahcy` (`folderId`),
  KEY `idx_pkrcoynowrmhjggaddyvxdsnjuwpfoubqmgk` (`volumeId`),
  KEY `fk_uwarmhxniazztlxylibzfkfxlasccspjsdcv` (`uploaderId`),
  CONSTRAINT `fk_irxmrnclhoxjveztieeuyehuaumxorolyjma` FOREIGN KEY (`volumeId`) REFERENCES `volumes` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_mrnulklnqkwmhhsnjzjmnmldsoxvkasjvibn` FOREIGN KEY (`folderId`) REFERENCES `volumefolders` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_uwarmhxniazztlxylibzfkfxlasccspjsdcv` FOREIGN KEY (`uploaderId`) REFERENCES `users` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_yaagwtbqeyfahcwafaoxjysknnykypnyqygy` FOREIGN KEY (`id`) REFERENCES `elements` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `groupId` int(11) NOT NULL,
  `parentId` int(11) DEFAULT NULL,
  `deletedWithGroup` tinyint(1) DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_qyeghfsubnioafkupimphrydhswosttyscpr` (`groupId`),
  KEY `fk_bkegwectpwnfaairlsmlmcurwgbqedbpljzi` (`parentId`),
  CONSTRAINT `fk_bkegwectpwnfaairlsmlmcurwgbqedbpljzi` FOREIGN KEY (`parentId`) REFERENCES `categories` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_bszlflxarqfqufkavirhhgnvglefxojpnpjq` FOREIGN KEY (`id`) REFERENCES `elements` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_hlqulodqvdzxqvjnzotglsqzgdcpsmqnineo` FOREIGN KEY (`groupId`) REFERENCES `categorygroups` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `categorygroups`
--

DROP TABLE IF EXISTS `categorygroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categorygroups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `structureId` int(11) NOT NULL,
  `fieldLayoutId` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `handle` varchar(255) NOT NULL,
  `defaultPlacement` enum('beginning','end') NOT NULL DEFAULT 'end',
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `dateDeleted` datetime DEFAULT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_vsjvxruarnomorgwlwxeykbohslpksdadanv` (`name`),
  KEY `idx_dmwrrqoxxdhxegqfohosqaxojnqysnmnaxyh` (`handle`),
  KEY `idx_iesdmmdnopzkczcforbtpjiliqajzdqpyxxo` (`structureId`),
  KEY `idx_zpgrugfdvnolyfiqcrqrofbuestyhluvdabu` (`fieldLayoutId`),
  KEY `idx_givlfjmbplgbhioabanjmfibeyidozbymarq` (`dateDeleted`),
  CONSTRAINT `fk_fmthretkwrcsbkzaolbldpgrotyjuxeibvwp` FOREIGN KEY (`fieldLayoutId`) REFERENCES `fieldlayouts` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_lsqjniffhslsygxicjuzrfsbnxsydxdiujaz` FOREIGN KEY (`structureId`) REFERENCES `structures` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `categorygroups_sites`
--

DROP TABLE IF EXISTS `categorygroups_sites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categorygroups_sites` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `groupId` int(11) NOT NULL,
  `siteId` int(11) NOT NULL,
  `hasUrls` tinyint(1) NOT NULL DEFAULT 1,
  `uriFormat` text DEFAULT NULL,
  `template` varchar(500) DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_cycupllwxjripqtqwknhthfduazlownrxucc` (`groupId`,`siteId`),
  KEY `idx_algyvlxpxwsgijzqxradjnmnngjpiyrjezlg` (`siteId`),
  CONSTRAINT `fk_enxhrdwqggzerggswxefslhxbblcqnaasbyk` FOREIGN KEY (`groupId`) REFERENCES `categorygroups` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_mszemzybwmpnnbygnklkjmvjojefznvbzupt` FOREIGN KEY (`siteId`) REFERENCES `sites` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `changedattributes`
--

DROP TABLE IF EXISTS `changedattributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `changedattributes` (
  `elementId` int(11) NOT NULL,
  `siteId` int(11) NOT NULL,
  `attribute` varchar(255) NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `propagated` tinyint(1) NOT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`elementId`,`siteId`,`attribute`),
  KEY `idx_wjgndocdydqylkhpfebxgzxyegidebcpjhlz` (`elementId`,`siteId`,`dateUpdated`),
  KEY `fk_asadxweqzccmewqgukqrytngcjjokmitpkqr` (`siteId`),
  KEY `fk_dmltrrnmnyzyjlwrsfzbxnbgckdthmwcsxlo` (`userId`),
  CONSTRAINT `fk_aelfpkevtyjlvnmyimbadmcydaldtrcaylhx` FOREIGN KEY (`elementId`) REFERENCES `elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_asadxweqzccmewqgukqrytngcjjokmitpkqr` FOREIGN KEY (`siteId`) REFERENCES `sites` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_dmltrrnmnyzyjlwrsfzbxnbgckdthmwcsxlo` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `changedfields`
--

DROP TABLE IF EXISTS `changedfields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `changedfields` (
  `elementId` int(11) NOT NULL,
  `siteId` int(11) NOT NULL,
  `fieldId` int(11) NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `propagated` tinyint(1) NOT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`elementId`,`siteId`,`fieldId`),
  KEY `idx_aohafrurtzarsarjuzssjqdqwnoymbyxkfxr` (`elementId`,`siteId`,`dateUpdated`),
  KEY `fk_exqihszcgjcnvdfsimxndwuzpywkaywlbrzz` (`siteId`),
  KEY `fk_uhsbxorhkbjjcxfnsmzvzotkotujemumgcyy` (`fieldId`),
  KEY `fk_tyjuwwiyaqbgwwxvlyikavmzyjfyyategdxb` (`userId`),
  CONSTRAINT `fk_exqihszcgjcnvdfsimxndwuzpywkaywlbrzz` FOREIGN KEY (`siteId`) REFERENCES `sites` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tcwlcpbeqriqyiuuzsosgscfdyfqtjxbofku` FOREIGN KEY (`elementId`) REFERENCES `elements` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tyjuwwiyaqbgwwxvlyikavmzyjfyyategdxb` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_uhsbxorhkbjjcxfnsmzvzotkotujemumgcyy` FOREIGN KEY (`fieldId`) REFERENCES `fields` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `content`
--

DROP TABLE IF EXISTS `content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `elementId` int(11) NOT NULL,
  `siteId` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_iluirnazosszxxcxsduowxooepomdqnltibd` (`elementId`,`siteId`),
  KEY `idx_wvnkmhjhaeddbdsuyrednqgcovffhuafxfvo` (`siteId`),
  KEY `idx_rhpgzdqvpaxozrhzopgacdzbtiewicsroflm` (`title`),
  CONSTRAINT `fk_fjsndyrbfmfuqgqrfbaexhykmevsxyozijod` FOREIGN KEY (`siteId`) REFERENCES `sites` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_lggikdtnrlfxdiqdmfwseojzwcfhewjasvkz` FOREIGN KEY (`elementId`) REFERENCES `elements` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `craftidtokens`
--

DROP TABLE IF EXISTS `craftidtokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `craftidtokens` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `accessToken` text NOT NULL,
  `expiryDate` datetime DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_xdsdihyjrkcitgkqxxdsvagwtncfzznlqjbw` (`userId`),
  CONSTRAINT `fk_xdsdihyjrkcitgkqxxdsvagwtncfzznlqjbw` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `deprecationerrors`
--

DROP TABLE IF EXISTS `deprecationerrors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deprecationerrors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(255) NOT NULL,
  `fingerprint` varchar(255) NOT NULL,
  `lastOccurrence` datetime NOT NULL,
  `file` varchar(255) NOT NULL,
  `line` smallint(6) unsigned DEFAULT NULL,
  `message` text DEFAULT NULL,
  `traces` text DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_gsctzekdkbjggrmmjtsbxrjzmxbpkdquuicv` (`key`,`fingerprint`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `drafts`
--

DROP TABLE IF EXISTS `drafts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `drafts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `canonicalId` int(11) DEFAULT NULL,
  `creatorId` int(11) DEFAULT NULL,
  `provisional` tinyint(1) NOT NULL DEFAULT 0,
  `name` varchar(255) NOT NULL,
  `notes` text DEFAULT NULL,
  `trackChanges` tinyint(1) NOT NULL DEFAULT 0,
  `dateLastMerged` datetime DEFAULT NULL,
  `saved` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  KEY `idx_zpuezahbcqabttvkbujrgoljlropswxxcuwp` (`creatorId`,`provisional`),
  KEY `idx_ehowlnsrzrodmtwgfwspntrmvpivptzlqhhc` (`saved`),
  KEY `fk_xhidqcvhpiswrwrfmmsodpjmjhusmgvfrxvu` (`canonicalId`),
  CONSTRAINT `fk_xhidqcvhpiswrwrfmmsodpjmjhusmgvfrxvu` FOREIGN KEY (`canonicalId`) REFERENCES `elements` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_xrxkuvlgbzylfppdocuxgwrfpgmvbphbpltw` FOREIGN KEY (`creatorId`) REFERENCES `users` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `elements`
--

DROP TABLE IF EXISTS `elements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `elements` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `canonicalId` int(11) DEFAULT NULL,
  `draftId` int(11) DEFAULT NULL,
  `revisionId` int(11) DEFAULT NULL,
  `fieldLayoutId` int(11) DEFAULT NULL,
  `type` varchar(255) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT 1,
  `archived` tinyint(1) NOT NULL DEFAULT 0,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `dateLastMerged` datetime DEFAULT NULL,
  `dateDeleted` datetime DEFAULT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_nkvdfltminonyumnevshuctonydswjjmcbmf` (`dateDeleted`),
  KEY `idx_tlkovqbdresmrzrbllymedxzespwdzafmypb` (`fieldLayoutId`),
  KEY `idx_vusaizqsohvuxiexxlkyrwyynohxcotnnggc` (`type`),
  KEY `idx_ubpjwfncsqnqgwwomzgdlcpstexfpsekzvqt` (`enabled`),
  KEY `idx_ypqhyqcdxqagcajdwzntrpjihhxahxbaecyp` (`canonicalId`),
  KEY `idx_nriuipxbqwbnlgjdxzflyatalftvrjycptrv` (`archived`,`dateCreated`),
  KEY `idx_cmirvxrctdjyvcqnbsabgfmzhjxbnjfzuqrp` (`archived`,`dateDeleted`,`draftId`,`revisionId`,`canonicalId`),
  KEY `idx_bqevrrfgrwntckfmoppflxensyzhuyhckivt` (`archived`,`dateDeleted`,`draftId`,`revisionId`,`canonicalId`,`enabled`),
  KEY `fk_nquokwflnetjdhagvysxmjjgtylxhyqkexmu` (`draftId`),
  KEY `fk_igxsssegmrrzfqverhpnwtekwhnnwvhgwspq` (`revisionId`),
  CONSTRAINT `fk_igxsssegmrrzfqverhpnwtekwhnnwvhgwspq` FOREIGN KEY (`revisionId`) REFERENCES `revisions` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_nquokwflnetjdhagvysxmjjgtylxhyqkexmu` FOREIGN KEY (`draftId`) REFERENCES `drafts` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_ppdmhnowbbqbxsjzvragsudfabsjnwkiprfs` FOREIGN KEY (`canonicalId`) REFERENCES `elements` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_ptllxwkdpbczsosigldlufulejnfjkzngmbb` FOREIGN KEY (`fieldLayoutId`) REFERENCES `fieldlayouts` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `elements_sites`
--

DROP TABLE IF EXISTS `elements_sites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `elements_sites` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `elementId` int(11) NOT NULL,
  `siteId` int(11) NOT NULL,
  `slug` varchar(255) DEFAULT NULL,
  `uri` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT 1,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_bfkevwqrgihnesfckholmafivgbdaynmscgc` (`elementId`,`siteId`),
  KEY `idx_zdlqpgtizwfmowzmvzyvorlwcmhqdveiyuph` (`siteId`),
  KEY `idx_xppnepxildjjzgssrktoyehynpywbonowtdv` (`slug`,`siteId`),
  KEY `idx_gnvlemsmanxdayzrrjuzeokbuyczqymkeuil` (`enabled`),
  KEY `idx_jdqvpofyltlrpddtunblhcbetubamlcsrmgt` (`uri`,`siteId`),
  CONSTRAINT `fk_hlbynorqrhywtblclmmxzroymkaiclnmvxop` FOREIGN KEY (`elementId`) REFERENCES `elements` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_lywkbtgyandsmvwdzjahzixowtdajartajfp` FOREIGN KEY (`siteId`) REFERENCES `sites` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `entries`
--

DROP TABLE IF EXISTS `entries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entries` (
  `id` int(11) NOT NULL,
  `sectionId` int(11) NOT NULL,
  `parentId` int(11) DEFAULT NULL,
  `typeId` int(11) NOT NULL,
  `authorId` int(11) DEFAULT NULL,
  `postDate` datetime DEFAULT NULL,
  `expiryDate` datetime DEFAULT NULL,
  `deletedWithEntryType` tinyint(1) DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_aqbqumlikxsayxfccxebzhlpvtjpoljgzfog` (`postDate`),
  KEY `idx_zktaqvtoitpjdevcvlcidsmvajkvacxuzwvi` (`expiryDate`),
  KEY `idx_ggzmesfakedvsuceomczyhwwbwovxlhrzwom` (`authorId`),
  KEY `idx_opckgeodpwsdxuxpycovagkbowiojusajefh` (`sectionId`),
  KEY `idx_tcpcifevqnxuaclvlyrnijdhnetncplgejmu` (`typeId`),
  KEY `fk_uoxnkrvehvqkvshcnrzngorxlndvabuuvnop` (`parentId`),
  CONSTRAINT `fk_gcbfsjvdxegznubwszbpztgkgcihqcgibokg` FOREIGN KEY (`id`) REFERENCES `elements` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_lkjgmnfyhuwhlmvyojseldoqeignzxxbtgst` FOREIGN KEY (`sectionId`) REFERENCES `sections` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_lrkhawbdwkxalepumjybyhhfktnjuajojnnc` FOREIGN KEY (`authorId`) REFERENCES `users` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_omftnqwnvmqcuwtizhzehenhxmbkvwuvyukm` FOREIGN KEY (`typeId`) REFERENCES `entrytypes` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_uoxnkrvehvqkvshcnrzngorxlndvabuuvnop` FOREIGN KEY (`parentId`) REFERENCES `entries` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `entrytypes`
--

DROP TABLE IF EXISTS `entrytypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entrytypes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sectionId` int(11) NOT NULL,
  `fieldLayoutId` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `handle` varchar(255) NOT NULL,
  `hasTitleField` tinyint(1) NOT NULL DEFAULT 1,
  `titleTranslationMethod` varchar(255) NOT NULL DEFAULT 'site',
  `titleTranslationKeyFormat` text DEFAULT NULL,
  `titleFormat` varchar(255) DEFAULT NULL,
  `sortOrder` smallint(6) unsigned DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `dateDeleted` datetime DEFAULT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_dadqeltipcnxfnsiggmaceyhhwyzxkmmcflh` (`name`,`sectionId`),
  KEY `idx_cvozlhdzpskurmcyiirwfealvihzoqwtdouo` (`handle`,`sectionId`),
  KEY `idx_skvvpmysgiglnlyibcsbqwbeeersrywqmjjp` (`sectionId`),
  KEY `idx_rfrgpnocwnbmmdiblpquemiorsrmirwlhfcw` (`fieldLayoutId`),
  KEY `idx_rfbtnimwzlkurddekxsuscrjltkizbpjnupc` (`dateDeleted`),
  CONSTRAINT `fk_jetaxljwgbzycfaxbnsmwnstdnsqinwbsyri` FOREIGN KEY (`fieldLayoutId`) REFERENCES `fieldlayouts` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_lrokmpdujuwblufszcsyklqcpnkxqokkxiag` FOREIGN KEY (`sectionId`) REFERENCES `sections` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fieldgroups`
--

DROP TABLE IF EXISTS `fieldgroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fieldgroups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `dateDeleted` datetime DEFAULT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_zgwubxqpzikkixwfgiqclxweoafnyjnptkha` (`name`),
  KEY `idx_blsubqmdjyofuvqcgljlbfqewuvkrwikfmrl` (`dateDeleted`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fieldlayoutfields`
--

DROP TABLE IF EXISTS `fieldlayoutfields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fieldlayoutfields` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `layoutId` int(11) NOT NULL,
  `tabId` int(11) NOT NULL,
  `fieldId` int(11) NOT NULL,
  `required` tinyint(1) NOT NULL DEFAULT 0,
  `sortOrder` smallint(6) unsigned DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_xgxotfjevqvxyzwdhihdulrzoonhallpxtrk` (`layoutId`,`fieldId`),
  KEY `idx_akeaqzbtrxnnuatzxftphvvsgilkcgjuzesb` (`sortOrder`),
  KEY `idx_zordlwqsqusjhedmcrrtozsmbhfnkotqrcwc` (`tabId`),
  KEY `idx_aetwfjaykoqubccoimtnsxunfsasjaxienca` (`fieldId`),
  CONSTRAINT `fk_cxvsjufpostigfjbtprkecmlnqmrdlctepgs` FOREIGN KEY (`layoutId`) REFERENCES `fieldlayouts` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_cxyemcmpnsygidlyqesuxpkxxzqmeqexkmnt` FOREIGN KEY (`tabId`) REFERENCES `fieldlayouttabs` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_iuoenrrnbumzxpsopqrjzslljafissrlhple` FOREIGN KEY (`fieldId`) REFERENCES `fields` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fieldlayouts`
--

DROP TABLE IF EXISTS `fieldlayouts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fieldlayouts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) NOT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `dateDeleted` datetime DEFAULT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_oealiojbwbntyyqqalyapmtmdefpsypsfpcb` (`dateDeleted`),
  KEY `idx_gppsmiqatcdgpelllkfwdzyndeuvisjipfpe` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fieldlayouttabs`
--

DROP TABLE IF EXISTS `fieldlayouttabs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fieldlayouttabs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `layoutId` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `settings` text DEFAULT NULL,
  `elements` text DEFAULT NULL,
  `sortOrder` smallint(6) unsigned DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_pxzrehqipkvrghvkravmptffchsnnwkylycb` (`sortOrder`),
  KEY `idx_vpvxinnhlqofcyreiczuwugljtynndqblwru` (`layoutId`),
  CONSTRAINT `fk_twhmdbtqxytlrrbocxdjkoyqksogflmmbdwr` FOREIGN KEY (`layoutId`) REFERENCES `fieldlayouts` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fields`
--

DROP TABLE IF EXISTS `fields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fields` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `groupId` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `handle` varchar(64) NOT NULL,
  `context` varchar(255) NOT NULL DEFAULT 'global',
  `columnSuffix` char(8) DEFAULT NULL,
  `instructions` text DEFAULT NULL,
  `searchable` tinyint(1) NOT NULL DEFAULT 1,
  `translationMethod` varchar(255) NOT NULL DEFAULT 'none',
  `translationKeyFormat` text DEFAULT NULL,
  `type` varchar(255) NOT NULL,
  `settings` text DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_noqogzyzflcnkgrphdqasohqhmdzypembqpd` (`handle`,`context`),
  KEY `idx_lmklnphnuzbtgxmjqrlvfosbycnmsczemxyf` (`groupId`),
  KEY `idx_pfnncxnldvuofyojchttgnidsyzyipbuxujn` (`context`),
  CONSTRAINT `fk_dyqxhbanqcquctkvsrihmvwueccerygjtrho` FOREIGN KEY (`groupId`) REFERENCES `fieldgroups` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `globalsets`
--

DROP TABLE IF EXISTS `globalsets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `globalsets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `handle` varchar(255) NOT NULL,
  `fieldLayoutId` int(11) DEFAULT NULL,
  `sortOrder` smallint(6) unsigned DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_zvitlvqgnimqaleefvfmctdrevzxyepzegcw` (`name`),
  KEY `idx_vhrqwxzssbjwvfvikljzjxqgnrfrhaihmmnl` (`handle`),
  KEY `idx_emfnkiimwjdgjenxoynbrvmiujcsoawxxlsy` (`fieldLayoutId`),
  KEY `idx_fvcvyklqqorqvdbwhvggislbznvitchahjms` (`sortOrder`),
  CONSTRAINT `fk_wuydycappkjiqdeydvcoprblnosbdgnfunln` FOREIGN KEY (`id`) REFERENCES `elements` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_xjvwkybjcrcdioqjqdwobvnoxmyekyyukcam` FOREIGN KEY (`fieldLayoutId`) REFERENCES `fieldlayouts` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gqlschemas`
--

DROP TABLE IF EXISTS `gqlschemas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gqlschemas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `scope` text DEFAULT NULL,
  `isPublic` tinyint(1) NOT NULL DEFAULT 0,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gqltokens`
--

DROP TABLE IF EXISTS `gqltokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gqltokens` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `accessToken` varchar(255) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT 1,
  `expiryDate` datetime DEFAULT NULL,
  `lastUsed` datetime DEFAULT NULL,
  `schemaId` int(11) DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_plhvpeevdwmyxittcwllhdbpjahbfuutqrzy` (`accessToken`),
  UNIQUE KEY `idx_bgoepwpdvfqmagvfeblmitacvjtdancfruim` (`name`),
  KEY `fk_rqdlfkjkindwtvyybjxhxwdzybnutwtjozso` (`schemaId`),
  CONSTRAINT `fk_rqdlfkjkindwtvyybjxhxwdzybnutwtjozso` FOREIGN KEY (`schemaId`) REFERENCES `gqlschemas` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `imagetransformindex`
--

DROP TABLE IF EXISTS `imagetransformindex`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `imagetransformindex` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `assetId` int(11) NOT NULL,
  `transformer` varchar(255) DEFAULT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `format` varchar(255) DEFAULT NULL,
  `transformString` varchar(255) NOT NULL,
  `fileExists` tinyint(1) NOT NULL DEFAULT 0,
  `inProgress` tinyint(1) NOT NULL DEFAULT 0,
  `error` tinyint(1) NOT NULL DEFAULT 0,
  `dateIndexed` datetime DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_fduammxsosovbxgpnngamwqtpbdfivegnzhc` (`assetId`,`transformString`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `imagetransforms`
--

DROP TABLE IF EXISTS `imagetransforms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `imagetransforms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `handle` varchar(255) NOT NULL,
  `mode` enum('stretch','fit','crop','letterbox') NOT NULL DEFAULT 'crop',
  `position` enum('top-left','top-center','top-right','center-left','center-center','center-right','bottom-left','bottom-center','bottom-right') NOT NULL DEFAULT 'center-center',
  `width` int(11) unsigned DEFAULT NULL,
  `height` int(11) unsigned DEFAULT NULL,
  `format` varchar(255) DEFAULT NULL,
  `quality` int(11) DEFAULT NULL,
  `interlace` enum('none','line','plane','partition') NOT NULL DEFAULT 'none',
  `fill` varchar(11) DEFAULT NULL,
  `upscale` tinyint(1) NOT NULL DEFAULT 1,
  `parameterChangeTime` datetime DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_dzipjlorfhpplfrfsiizkuadflexofvvzsru` (`name`),
  KEY `idx_evpgdjqpvdsjfnpguwpuxxejgyyymrwipnvo` (`handle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `info`
--

DROP TABLE IF EXISTS `info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` varchar(50) NOT NULL,
  `schemaVersion` varchar(15) NOT NULL,
  `maintenance` tinyint(1) NOT NULL DEFAULT 0,
  `configVersion` char(12) NOT NULL DEFAULT '000000000000',
  `fieldVersion` char(12) NOT NULL DEFAULT '000000000000',
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `matrixblocks`
--

DROP TABLE IF EXISTS `matrixblocks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `matrixblocks` (
  `id` int(11) NOT NULL,
  `primaryOwnerId` int(11) NOT NULL,
  `fieldId` int(11) NOT NULL,
  `typeId` int(11) NOT NULL,
  `deletedWithOwner` tinyint(1) DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_yceojygnbjdlotqcimkomoyeaznmeuyzycyu` (`primaryOwnerId`),
  KEY `idx_sqtgmlmlioyzuojgaozgdmeknfwuptcqdzlx` (`fieldId`),
  KEY `idx_fokqirsdrmjeaxahefvayktoxcynuwpwajsy` (`typeId`),
  CONSTRAINT `fk_baaazmekdfycejsvgojdrmrazvxnrshtonvy` FOREIGN KEY (`fieldId`) REFERENCES `fields` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_nxrxfxyalklahmebyoqovbnoucxbavhzrhut` FOREIGN KEY (`id`) REFERENCES `elements` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_pbmysbikbupyejsdzwxhwchendgqogucwhee` FOREIGN KEY (`typeId`) REFERENCES `matrixblocktypes` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_qgqmelxqyxwnhkwgnadrwohujjqiaiujktsu` FOREIGN KEY (`primaryOwnerId`) REFERENCES `elements` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `matrixblocks_owners`
--

DROP TABLE IF EXISTS `matrixblocks_owners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `matrixblocks_owners` (
  `blockId` int(11) NOT NULL,
  `ownerId` int(11) NOT NULL,
  `sortOrder` smallint(6) unsigned NOT NULL,
  PRIMARY KEY (`blockId`,`ownerId`),
  KEY `fk_vchhfbyqfdfjyetegbtorpjylmduxaqpktnr` (`ownerId`),
  CONSTRAINT `fk_gxwnhvqkppsjojzdpkwbrohaowmlhomypvos` FOREIGN KEY (`blockId`) REFERENCES `matrixblocks` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_vchhfbyqfdfjyetegbtorpjylmduxaqpktnr` FOREIGN KEY (`ownerId`) REFERENCES `elements` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `matrixblocktypes`
--

DROP TABLE IF EXISTS `matrixblocktypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `matrixblocktypes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fieldId` int(11) NOT NULL,
  `fieldLayoutId` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `handle` varchar(255) NOT NULL,
  `sortOrder` smallint(6) unsigned DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_pbsphdpkukulwwciibsetxkqrpjomtoqcsvs` (`name`,`fieldId`),
  KEY `idx_dcfyopqdmnspnwykobbvgtlphfvcrlbtioga` (`handle`,`fieldId`),
  KEY `idx_pxxsxudauxtgwafxirzwappuygpzqiplglgq` (`fieldId`),
  KEY `idx_bwlxrugpmrxkmudvbknhirymsduyxpfkxslu` (`fieldLayoutId`),
  CONSTRAINT `fk_rjurtwfvxbjxrkfubuegvrffwpsdalvbaobh` FOREIGN KEY (`fieldLayoutId`) REFERENCES `fieldlayouts` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_sxdkahdfnqctixwqyrhqlhjyszwojxgswrty` FOREIGN KEY (`fieldId`) REFERENCES `fields` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `migrations`
--

DROP TABLE IF EXISTS `migrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `migrations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `track` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `applyTime` datetime NOT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_kffhrnfjlffmevybuexzcssyoascwzhktthv` (`track`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `plugins`
--

DROP TABLE IF EXISTS `plugins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plugins` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `handle` varchar(255) NOT NULL,
  `version` varchar(255) NOT NULL,
  `schemaVersion` varchar(255) NOT NULL,
  `installDate` datetime NOT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_ysxfzjsnswqddynzkyyrdjhkjthiqewgmsmb` (`handle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `projectconfig`
--

DROP TABLE IF EXISTS `projectconfig`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projectconfig` (
  `path` varchar(255) NOT NULL,
  `value` text NOT NULL,
  PRIMARY KEY (`path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `queue`
--

DROP TABLE IF EXISTS `queue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `queue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `channel` varchar(255) NOT NULL DEFAULT 'queue',
  `job` longblob NOT NULL,
  `description` text DEFAULT NULL,
  `timePushed` int(11) NOT NULL,
  `ttr` int(11) NOT NULL,
  `delay` int(11) NOT NULL DEFAULT 0,
  `priority` int(11) unsigned NOT NULL DEFAULT 1024,
  `dateReserved` datetime DEFAULT NULL,
  `timeUpdated` int(11) DEFAULT NULL,
  `progress` smallint(6) NOT NULL DEFAULT 0,
  `progressLabel` varchar(255) DEFAULT NULL,
  `attempt` int(11) DEFAULT NULL,
  `fail` tinyint(1) DEFAULT 0,
  `dateFailed` datetime DEFAULT NULL,
  `error` text DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_bivkywhuuauqqolcbyotyonufgoljfsmmzzw` (`channel`,`fail`,`timeUpdated`,`timePushed`),
  KEY `idx_gbsniqcjqbsanfnswextldbrdffbltiomqmp` (`channel`,`fail`,`timeUpdated`,`delay`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `relations`
--

DROP TABLE IF EXISTS `relations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `relations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fieldId` int(11) NOT NULL,
  `sourceId` int(11) NOT NULL,
  `sourceSiteId` int(11) DEFAULT NULL,
  `targetId` int(11) NOT NULL,
  `sortOrder` smallint(6) unsigned DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_emtjtgjnkicqhonaepbquktxopzgmnsazvxz` (`fieldId`,`sourceId`,`sourceSiteId`,`targetId`),
  KEY `idx_olwwygazpscepdxlklqtinaibgzjujwldqvv` (`sourceId`),
  KEY `idx_mawluipiuaniyjlmojtrjupkkydkzuxkfnmo` (`targetId`),
  KEY `idx_iyzjzfinduojxoiwkiibkccuhxllztlwwoiz` (`sourceSiteId`),
  CONSTRAINT `fk_musqzfhvyubmserqckgzkviizsnrglwpdyby` FOREIGN KEY (`sourceSiteId`) REFERENCES `sites` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_yosuhuhevxfjcsjgjmvrgwosghuibwebnksy` FOREIGN KEY (`fieldId`) REFERENCES `fields` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_zulrgijhusffrvxbxidywesoybjdddmsbivm` FOREIGN KEY (`sourceId`) REFERENCES `elements` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `resourcepaths`
--

DROP TABLE IF EXISTS `resourcepaths`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resourcepaths` (
  `hash` varchar(255) NOT NULL,
  `path` varchar(255) NOT NULL,
  PRIMARY KEY (`hash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `revisions`
--

DROP TABLE IF EXISTS `revisions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `revisions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `canonicalId` int(11) NOT NULL,
  `creatorId` int(11) DEFAULT NULL,
  `num` int(11) NOT NULL,
  `notes` text DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_wxzxomdcbvmxfmlibavwuhnlfcmuawmdgmfr` (`canonicalId`,`num`),
  KEY `fk_cpyrehqxncziyzxlwqzqijixbvarmntjkpql` (`creatorId`),
  CONSTRAINT `fk_cpyrehqxncziyzxlwqzqijixbvarmntjkpql` FOREIGN KEY (`creatorId`) REFERENCES `users` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_ycjovvunttaxqtfukpnnsyxnpgtvvhhnychf` FOREIGN KEY (`canonicalId`) REFERENCES `elements` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `searchindex`
--

DROP TABLE IF EXISTS `searchindex`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `searchindex` (
  `elementId` int(11) NOT NULL,
  `attribute` varchar(25) NOT NULL,
  `fieldId` int(11) NOT NULL,
  `siteId` int(11) NOT NULL,
  `keywords` text NOT NULL,
  PRIMARY KEY (`elementId`,`attribute`,`fieldId`,`siteId`),
  FULLTEXT KEY `idx_otiwzrtoifbqvtoxmhhtfvjglqazrvjujobp` (`keywords`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sections`
--

DROP TABLE IF EXISTS `sections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sections` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `structureId` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `handle` varchar(255) NOT NULL,
  `type` enum('single','channel','structure') NOT NULL DEFAULT 'channel',
  `enableVersioning` tinyint(1) NOT NULL DEFAULT 0,
  `propagationMethod` varchar(255) NOT NULL DEFAULT 'all',
  `defaultPlacement` enum('beginning','end') NOT NULL DEFAULT 'end',
  `previewTargets` text DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `dateDeleted` datetime DEFAULT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_spuipznhifhsyovecujweuyspydvfevsnvnh` (`handle`),
  KEY `idx_kgrfyjisoywyehcfepjjrxgrrfrdbvfduygt` (`name`),
  KEY `idx_nzpbycqotszyumzojpmrmvvimnpynushfhgk` (`structureId`),
  KEY `idx_lmfysnelphmlkjbxzbkeesrifumsvhbmrqew` (`dateDeleted`),
  CONSTRAINT `fk_wnmutvgymambxuarcbnjsxatpnbnsfmoqnff` FOREIGN KEY (`structureId`) REFERENCES `structures` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sections_sites`
--

DROP TABLE IF EXISTS `sections_sites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sections_sites` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sectionId` int(11) NOT NULL,
  `siteId` int(11) NOT NULL,
  `hasUrls` tinyint(1) NOT NULL DEFAULT 1,
  `uriFormat` text DEFAULT NULL,
  `template` varchar(500) DEFAULT NULL,
  `enabledByDefault` tinyint(1) NOT NULL DEFAULT 1,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_luybgbtrptovxukcxwuwgehgtgowgidrzjqv` (`sectionId`,`siteId`),
  KEY `idx_pkhesykgppsjxsjjoxxnlegnzstfadcspedm` (`siteId`),
  CONSTRAINT `fk_gohrbvrflyqqsosfskcxadfnqpvmfvioxhzd` FOREIGN KEY (`sectionId`) REFERENCES `sections` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_srhfmwthbpbygayzutlyavrvwyyiqapimwyv` FOREIGN KEY (`siteId`) REFERENCES `sites` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sequences`
--

DROP TABLE IF EXISTS `sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sequences` (
  `name` varchar(255) NOT NULL,
  `next` int(11) unsigned NOT NULL DEFAULT 1,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sessions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `token` char(100) NOT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_xyuvyaxihdzbfaeajbcjboaqswrbifdgnkux` (`uid`),
  KEY `idx_zsiiffhkwxvoavypakvikspfndfqekuovcxw` (`token`),
  KEY `idx_tzdkvrvskjfjdgvzfgbjwmaopemvvvglmkru` (`dateUpdated`),
  KEY `idx_qrehqewqshboxhmnyicwiegvzdpmwumfjasm` (`userId`),
  CONSTRAINT `fk_afxedpeenaamfghbeqkruaiwunvrxbpndsvt` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shunnedmessages`
--

DROP TABLE IF EXISTS `shunnedmessages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shunnedmessages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `message` varchar(255) NOT NULL,
  `expiryDate` datetime DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_eaxmvyxbwphwcgiuziiqmxjrutatzrcfcbij` (`userId`,`message`),
  CONSTRAINT `fk_ppifagpktydsjokdsembxtfylalrzkndvvkb` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sitegroups`
--

DROP TABLE IF EXISTS `sitegroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sitegroups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `dateDeleted` datetime DEFAULT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_bbkhjyijwdlsxxzjdnoalrecsxyhxqpmsggp` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sites`
--

DROP TABLE IF EXISTS `sites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sites` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `groupId` int(11) NOT NULL,
  `primary` tinyint(1) NOT NULL,
  `enabled` varchar(255) NOT NULL DEFAULT 'true',
  `name` varchar(255) NOT NULL,
  `handle` varchar(255) NOT NULL,
  `language` varchar(12) NOT NULL,
  `hasUrls` tinyint(1) NOT NULL DEFAULT 0,
  `baseUrl` varchar(255) DEFAULT NULL,
  `sortOrder` smallint(6) unsigned DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `dateDeleted` datetime DEFAULT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_tbfzntrlucgdpauncdwrhygzxmjkctscxgaw` (`dateDeleted`),
  KEY `idx_txrdwyowpcisjvztxtriknejkgysmmqmvsay` (`handle`),
  KEY `idx_wddtaknifisflwnvhbeqjlhxabjfwgwbhccu` (`sortOrder`),
  KEY `fk_rblmoifmqlmoyknkqhjhefozwbjksxohhevk` (`groupId`),
  CONSTRAINT `fk_rblmoifmqlmoyknkqhjhefozwbjksxohhevk` FOREIGN KEY (`groupId`) REFERENCES `sitegroups` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `structureelements`
--

DROP TABLE IF EXISTS `structureelements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `structureelements` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `structureId` int(11) NOT NULL,
  `elementId` int(11) DEFAULT NULL,
  `root` int(11) unsigned DEFAULT NULL,
  `lft` int(11) unsigned NOT NULL,
  `rgt` int(11) unsigned NOT NULL,
  `level` smallint(6) unsigned NOT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_qavwlodktfnclklkygugchzoquhqtzghhmmb` (`structureId`,`elementId`),
  KEY `idx_kfrsesvjtwsvjfpozbovuckkklczcedrlehc` (`root`),
  KEY `idx_xfgbtiktvrritcrgexfdygtvxopgzuaktsey` (`lft`),
  KEY `idx_ofzdlnhatdyjytwxktvfmyjkzrrbmkbnezjn` (`rgt`),
  KEY `idx_dtbjjelyhmpjzzrbxhqymnwnnmbsszgvinud` (`level`),
  KEY `idx_qmtcjulcelstmotqgijmnmfyarslqidankgp` (`elementId`),
  CONSTRAINT `fk_awgkdiyjnqslqrjfhohbyajfrojddzjdooar` FOREIGN KEY (`structureId`) REFERENCES `structures` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `structures`
--

DROP TABLE IF EXISTS `structures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `structures` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `maxLevels` smallint(6) unsigned DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `dateDeleted` datetime DEFAULT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_yqvcdwbsklufxxatsxtbpkwjpxjeozszfphs` (`dateDeleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `systemmessages`
--

DROP TABLE IF EXISTS `systemmessages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `systemmessages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `language` varchar(255) NOT NULL,
  `key` varchar(255) NOT NULL,
  `subject` text NOT NULL,
  `body` text NOT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_lxjehyfzkccycmwxyktrnmatfqxcgtswhulc` (`key`,`language`),
  KEY `idx_ebxfqqqdftrnsfahnhoicmfrcsoydmskkdpk` (`language`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `taggroups`
--

DROP TABLE IF EXISTS `taggroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taggroups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `handle` varchar(255) NOT NULL,
  `fieldLayoutId` int(11) DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `dateDeleted` datetime DEFAULT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_hofrvdtaxlzcmfwfhvtbjcxjyvkjuyoykqdb` (`name`),
  KEY `idx_baoifejvfmbsngviqhosfxlbalgvmaujisib` (`handle`),
  KEY `idx_jdazoqfuajcslplodsmdpnuyelcxaurpcqbh` (`dateDeleted`),
  KEY `fk_emeavrarecvmjwpwmmxxsojmjjbsvxvezels` (`fieldLayoutId`),
  CONSTRAINT `fk_emeavrarecvmjwpwmmxxsojmjjbsvxvezels` FOREIGN KEY (`fieldLayoutId`) REFERENCES `fieldlayouts` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tags` (
  `id` int(11) NOT NULL,
  `groupId` int(11) NOT NULL,
  `deletedWithGroup` tinyint(1) DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_kkfafjqivdnvlycyaghkpjxgsdcvdpwqxidh` (`groupId`),
  CONSTRAINT `fk_bbqhnubusnszulfoumgrydktocgicvmebvwk` FOREIGN KEY (`groupId`) REFERENCES `taggroups` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_pqhbscylldddeonbjjhqhswwvnqagrewbmui` FOREIGN KEY (`id`) REFERENCES `elements` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tokens`
--

DROP TABLE IF EXISTS `tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tokens` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` char(32) NOT NULL,
  `route` text DEFAULT NULL,
  `usageLimit` tinyint(3) unsigned DEFAULT NULL,
  `usageCount` tinyint(3) unsigned DEFAULT NULL,
  `expiryDate` datetime NOT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_kqpaqsebgnpkikelzdvtzwoaoirerwnrixay` (`token`),
  KEY `idx_nmivupqbyarxntmwxrycdfybolrinzyijffd` (`expiryDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usergroups`
--

DROP TABLE IF EXISTS `usergroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usergroups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `handle` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_fsnbxwjzgpjsorcmpbgbktrrtlbchxtifkho` (`handle`),
  KEY `idx_wuxgleaqbezyynlvjvwziufhrtjbhjrwjwfm` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usergroups_users`
--

DROP TABLE IF EXISTS `usergroups_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usergroups_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `groupId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_ifeqycbwvaftfxirqiolopcobahtrktakruk` (`groupId`,`userId`),
  KEY `idx_tjsyrvftacslltldsyygqmjjvidsqwxvggnh` (`userId`),
  CONSTRAINT `fk_iqhbynclmdioqmuhpighxwsjgdgcwoiiqfrw` FOREIGN KEY (`groupId`) REFERENCES `usergroups` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_krwjxjgnlyqphnxwzhxnryepidegbvxydbyb` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `userpermissions`
--

DROP TABLE IF EXISTS `userpermissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userpermissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_hanrcfrrmgdynrimsshorfrmtajijrnpryru` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `userpermissions_usergroups`
--

DROP TABLE IF EXISTS `userpermissions_usergroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userpermissions_usergroups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permissionId` int(11) NOT NULL,
  `groupId` int(11) NOT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_nleiwknfaqfgspqaktshyutjztwfivgeitwz` (`permissionId`,`groupId`),
  KEY `idx_hnolgyzaevrxuydjxgjcghyccsyltgwgtmfn` (`groupId`),
  CONSTRAINT `fk_bhywfnfkzrazjnkbiegzklmxrmymrghkxnot` FOREIGN KEY (`groupId`) REFERENCES `usergroups` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_xavkrrrcahkzeuxspvxsisdwrtcxukxioonk` FOREIGN KEY (`permissionId`) REFERENCES `userpermissions` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `userpermissions_users`
--

DROP TABLE IF EXISTS `userpermissions_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userpermissions_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permissionId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_ibjfqzymqmhamobjopuevtfsbvbjxzcjpwya` (`permissionId`,`userId`),
  KEY `idx_bqizpjriycskybhkrrpmruqssgbrzoiocidi` (`userId`),
  CONSTRAINT `fk_ikmwvkdommmkomxnxzqznqsfsuzmdgelxxet` FOREIGN KEY (`permissionId`) REFERENCES `userpermissions` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_qwvwtnafftugyiexdrzchgutvcowukmkwttj` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `userpreferences`
--

DROP TABLE IF EXISTS `userpreferences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userpreferences` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `preferences` text DEFAULT NULL,
  PRIMARY KEY (`userId`),
  CONSTRAINT `fk_tdmyytiswqqtfbnbnkdtudqlvblkokxppyhr` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `photoId` int(11) DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT 0,
  `pending` tinyint(1) NOT NULL DEFAULT 0,
  `locked` tinyint(1) NOT NULL DEFAULT 0,
  `suspended` tinyint(1) NOT NULL DEFAULT 0,
  `admin` tinyint(1) NOT NULL DEFAULT 0,
  `username` varchar(255) DEFAULT NULL,
  `fullName` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `lastLoginDate` datetime DEFAULT NULL,
  `lastLoginAttemptIp` varchar(45) DEFAULT NULL,
  `invalidLoginWindowStart` datetime DEFAULT NULL,
  `invalidLoginCount` tinyint(3) unsigned DEFAULT NULL,
  `lastInvalidLoginDate` datetime DEFAULT NULL,
  `lockoutDate` datetime DEFAULT NULL,
  `hasDashboard` tinyint(1) NOT NULL DEFAULT 0,
  `verificationCode` varchar(255) DEFAULT NULL,
  `verificationCodeIssuedDate` datetime DEFAULT NULL,
  `unverifiedEmail` varchar(255) DEFAULT NULL,
  `passwordResetRequired` tinyint(1) NOT NULL DEFAULT 0,
  `lastPasswordChangeDate` datetime DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_rwdrdgpfnxgjkcyodousvbrruakknyingtil` (`active`),
  KEY `idx_ddlptdkxvazjabtftbyqulqzhvyuvwrvjegh` (`locked`),
  KEY `idx_bqhsxyicrjqknufrviljptdgdagyybqenzee` (`pending`),
  KEY `idx_dqvidjgrstwmfiwvhhgcbbuacpjksuesaqkx` (`suspended`),
  KEY `idx_qqxjptnffcfgvnlotisnjmnwzhtceafhssez` (`verificationCode`),
  KEY `idx_kqwyhqmknuyiahocnkgrnjbaqdvumsuxfnkr` (`email`),
  KEY `idx_rpazcbmyerqfrnwzgiwbtgvfxurgowzhjzhm` (`username`),
  KEY `fk_tjkerccyilsgjjzkjhdeeytwlymdmgykfwqj` (`photoId`),
  CONSTRAINT `fk_tjkerccyilsgjjzkjhdeeytwlymdmgykfwqj` FOREIGN KEY (`photoId`) REFERENCES `assets` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_twcxdjbrarpaiqqslizioqymboyacziavjzp` FOREIGN KEY (`id`) REFERENCES `elements` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `volumefolders`
--

DROP TABLE IF EXISTS `volumefolders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `volumefolders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(11) DEFAULT NULL,
  `volumeId` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `path` varchar(255) DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_fenvjrkpsmpyqtcqogpijmswridkxtatewkw` (`name`,`parentId`,`volumeId`),
  KEY `idx_hatyazpsdtadnyskbxhfbojkmieqbtnpeuoy` (`parentId`),
  KEY `idx_ftlsidjxcmjcdevokthrvmdwtqmtadytugkv` (`volumeId`),
  CONSTRAINT `fk_lqnklcwzopvuvpxtrrxqjnvjnlbkirznpytx` FOREIGN KEY (`parentId`) REFERENCES `volumefolders` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_nfsmpybncvxryxpskdlvtwriahqwinjgaeaa` FOREIGN KEY (`volumeId`) REFERENCES `volumes` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `volumes`
--

DROP TABLE IF EXISTS `volumes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `volumes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fieldLayoutId` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `handle` varchar(255) NOT NULL,
  `fs` varchar(255) NOT NULL,
  `transformFs` varchar(255) DEFAULT NULL,
  `transformSubpath` varchar(255) DEFAULT NULL,
  `titleTranslationMethod` varchar(255) NOT NULL DEFAULT 'site',
  `titleTranslationKeyFormat` text DEFAULT NULL,
  `sortOrder` smallint(6) unsigned DEFAULT NULL,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `dateDeleted` datetime DEFAULT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_rpmnqvhfswxllfteusaentpfflqlzagwttxe` (`name`),
  KEY `idx_enhbgvalvnfkylvuwywcvrsrqjceyacqmurh` (`handle`),
  KEY `idx_xwfwzxikowpsycdvwzjkjvwhtvayxetebkff` (`fieldLayoutId`),
  KEY `idx_rcgzbhjwmapzathzkltlozuzcuclgwjfjkcx` (`dateDeleted`),
  CONSTRAINT `fk_fjiwkearzmxqzrwwmdpogyilcdqgcpzhlvvp` FOREIGN KEY (`fieldLayoutId`) REFERENCES `fieldlayouts` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `widgets`
--

DROP TABLE IF EXISTS `widgets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `widgets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `type` varchar(255) NOT NULL,
  `sortOrder` smallint(6) unsigned DEFAULT NULL,
  `colspan` tinyint(3) DEFAULT NULL,
  `settings` text DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT 1,
  `dateCreated` datetime NOT NULL,
  `dateUpdated` datetime NOT NULL,
  `uid` char(36) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_scsahotymfvyqrmmbqcwcavvkgjrdoovkabi` (`userId`),
  CONSTRAINT `fk_hwlrvopsygserlgnwubuzwlrzfetwvxnjtoi` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'craftdb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-17 20:28:02
-- MariaDB dump 10.19  Distrib 10.6.12-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: craftdb
-- ------------------------------------------------------
-- Server version	10.6.12-MariaDB-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `addresses`
--

LOCK TABLES `addresses` WRITE;
/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `announcements`
--

LOCK TABLES `announcements` WRITE;
/*!40000 ALTER TABLE `announcements` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `announcements` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `assetindexingsessions`
--

LOCK TABLES `assetindexingsessions` WRITE;
/*!40000 ALTER TABLE `assetindexingsessions` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `assetindexingsessions` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `assets`
--

LOCK TABLES `assets` WRITE;
/*!40000 ALTER TABLE `assets` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `assets` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `categorygroups`
--

LOCK TABLES `categorygroups` WRITE;
/*!40000 ALTER TABLE `categorygroups` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `categorygroups` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `categorygroups_sites`
--

LOCK TABLES `categorygroups_sites` WRITE;
/*!40000 ALTER TABLE `categorygroups_sites` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `categorygroups_sites` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `changedattributes`
--

LOCK TABLES `changedattributes` WRITE;
/*!40000 ALTER TABLE `changedattributes` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `changedattributes` VALUES (1,1,'email','2023-10-17 20:27:46',0,1),(1,1,'firstName','2023-10-11 20:18:21',0,1),(1,1,'fullName','2023-10-11 20:18:21',0,1),(1,1,'invalidLoginCount','2023-10-11 18:16:50',0,1),(1,1,'invalidLoginWindowStart','2023-10-11 18:16:50',0,1),(1,1,'lastName','2023-10-11 20:18:21',0,1),(1,1,'lastPasswordChangeDate','2023-10-17 20:27:46',0,1),(1,1,'password','2023-10-17 20:27:46',0,1),(2,1,'enabled','2023-10-17 15:27:11',0,1),(7,1,'authorId','2023-10-11 19:03:32',0,1),(7,1,'uri','2023-10-11 19:03:32',0,1),(8,1,'authorId','2023-10-11 19:03:32',0,1),(8,1,'uri','2023-10-11 19:03:32',0,1),(9,1,'authorId','2023-10-11 19:03:32',0,1),(9,1,'uri','2023-10-11 19:03:32',0,1),(10,1,'authorId','2023-10-11 19:03:32',0,1),(10,1,'uri','2023-10-11 19:03:32',0,1),(11,1,'authorId','2023-10-11 19:03:32',0,1),(11,1,'uri','2023-10-11 19:03:32',0,1);
/*!40000 ALTER TABLE `changedattributes` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `changedfields`
--

LOCK TABLES `changedfields` WRITE;
/*!40000 ALTER TABLE `changedfields` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `changedfields` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `content`
--

LOCK TABLES `content` WRITE;
/*!40000 ALTER TABLE `content` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `content` VALUES (1,1,1,NULL,'2023-10-11 17:57:16','2023-10-17 20:27:45','b931f23f-9988-4659-9f9c-67cb5194507a'),(2,2,1,'Home','2023-10-11 18:20:54','2023-10-17 15:27:11','943bdc13-82d2-402e-acbf-9d07f0955b9a'),(3,3,1,'Home','2023-10-11 18:20:55','2023-10-11 18:20:55','f42dff2c-1715-4805-81f4-3b559e93ff35'),(4,4,1,'Home','2023-10-11 18:20:55','2023-10-11 18:20:55','e65f7a43-934d-42d7-a498-5398bb77a1e9'),(5,5,1,'Home','2023-10-11 18:21:17','2023-10-11 18:21:17','37306abb-167c-4e1e-b1ef-b0ac1019cbd1'),(6,6,1,'Home','2023-10-11 18:59:41','2023-10-11 18:59:41','8d507fcb-644c-4621-8fc0-ea3968173c28'),(7,7,1,'Coming Soon','2023-10-11 19:02:04','2023-10-11 19:03:32','1a551f47-c012-405c-b882-e66879050902'),(8,8,1,'Coming Soon','2023-10-11 19:02:04','2023-10-11 19:03:32','3501d3fa-d853-4e0c-8e50-005102ad9865'),(9,9,1,'Coming Soon','2023-10-11 19:02:04','2023-10-11 19:03:32','3c158b37-662b-4fb7-addc-7861fa5a60e3'),(10,10,1,'Coming Soon','2023-10-11 19:02:05','2023-10-11 19:03:32','3775ef58-c54d-4449-9527-8d1287b25d8b'),(11,11,1,'Coming Soon','2023-10-11 19:03:06','2023-10-11 19:03:32','5b9aaabc-0500-4087-aec7-c4d5d9de39bd'),(13,13,1,'Home','2023-10-17 15:26:03','2023-10-17 15:26:03','30ae6d41-9897-4bad-91ce-1c7c4b1ae219'),(15,15,1,'Home','2023-10-17 15:27:11','2023-10-17 15:27:11','ed80bb2e-069e-4cd8-a217-256909bfdc25');
/*!40000 ALTER TABLE `content` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `craftidtokens`
--

LOCK TABLES `craftidtokens` WRITE;
/*!40000 ALTER TABLE `craftidtokens` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `craftidtokens` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `deprecationerrors`
--

LOCK TABLES `deprecationerrors` WRITE;
/*!40000 ALTER TABLE `deprecationerrors` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `deprecationerrors` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `drafts`
--

LOCK TABLES `drafts` WRITE;
/*!40000 ALTER TABLE `drafts` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `drafts` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `elements`
--

LOCK TABLES `elements` WRITE;
/*!40000 ALTER TABLE `elements` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `elements` VALUES (1,NULL,NULL,NULL,NULL,'craft\\elements\\User',1,0,'2023-10-11 17:57:16','2023-10-17 20:27:45',NULL,NULL,'9b665688-a499-4e32-b75e-1641a9bef7bf'),(2,NULL,NULL,NULL,1,'craft\\elements\\Entry',1,0,'2023-10-11 18:20:54','2023-10-17 15:27:11',NULL,NULL,'f9b0d838-d99b-4981-810f-fc4c43176b8d'),(3,2,NULL,1,1,'craft\\elements\\Entry',1,0,'2023-10-11 18:20:54','2023-10-11 18:20:54',NULL,NULL,'c8c81f2f-1002-44c1-8231-0a05a902c2a9'),(4,2,NULL,2,1,'craft\\elements\\Entry',1,0,'2023-10-11 18:20:55','2023-10-11 18:20:55',NULL,NULL,'7fcfacad-cb4b-436b-9976-dbec825bf70d'),(5,2,NULL,3,1,'craft\\elements\\Entry',1,0,'2023-10-11 18:21:17','2023-10-11 18:21:17',NULL,NULL,'fcc8ae23-f97d-44da-b14a-1c570969aa85'),(6,2,NULL,4,1,'craft\\elements\\Entry',1,0,'2023-10-11 18:59:41','2023-10-11 18:59:41',NULL,NULL,'b19927a4-2cec-4c5d-ab5f-12055488664a'),(7,NULL,NULL,NULL,2,'craft\\elements\\Entry',1,0,'2023-10-11 19:02:04','2023-10-11 19:03:06',NULL,'2023-10-17 15:25:37','80c18adb-68a2-4363-81ae-1f9b502aabd6'),(8,7,NULL,5,2,'craft\\elements\\Entry',1,0,'2023-10-11 19:02:04','2023-10-11 19:02:04',NULL,'2023-10-17 15:25:37','8dab44f3-1430-4432-9f81-bd8e1e00d778'),(9,7,NULL,6,2,'craft\\elements\\Entry',1,0,'2023-10-11 19:02:04','2023-10-11 19:02:04',NULL,'2023-10-17 15:25:37','c21c5069-427e-4735-b68f-a0d378beca3f'),(10,7,NULL,7,2,'craft\\elements\\Entry',1,0,'2023-10-11 19:02:04','2023-10-11 19:02:05',NULL,'2023-10-17 15:25:37','55bfd5b5-cdf8-4aa4-a1f2-d970b70f54d0'),(11,7,NULL,8,2,'craft\\elements\\Entry',1,0,'2023-10-11 19:03:06','2023-10-11 19:03:06',NULL,'2023-10-17 15:25:37','f77d68b4-21cf-410d-9178-09a896836faa'),(13,2,NULL,9,1,'craft\\elements\\Entry',0,0,'2023-10-17 15:26:03','2023-10-17 15:26:03',NULL,NULL,'90beca62-3309-44d2-a8fe-966c83dfb0a5'),(15,2,NULL,10,1,'craft\\elements\\Entry',1,0,'2023-10-17 15:27:11','2023-10-17 15:27:11',NULL,NULL,'c4588198-fe45-4f9f-9927-5f55354e3623');
/*!40000 ALTER TABLE `elements` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `elements_sites`
--

LOCK TABLES `elements_sites` WRITE;
/*!40000 ALTER TABLE `elements_sites` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `elements_sites` VALUES (1,1,1,NULL,NULL,1,'2023-10-11 17:57:16','2023-10-11 17:57:16','8c788018-2cbc-4b8e-87d5-4a75b99fa088'),(2,2,1,'home','__home__',1,'2023-10-11 18:20:54','2023-10-11 18:20:54','52312e52-af35-4230-b4dd-59fb8dfbfc2a'),(3,3,1,'home','__home__',1,'2023-10-11 18:20:55','2023-10-11 18:20:55','e052ac77-6804-4145-a507-097455ea3fd8'),(4,4,1,'home','__home__',1,'2023-10-11 18:20:55','2023-10-11 18:20:55','45374cc9-bd04-4d4c-a1e4-8b07bc168681'),(5,5,1,'home','__home__',1,'2023-10-11 18:21:17','2023-10-11 18:21:17','a67937dd-9997-4e3e-9546-72d6c1d12dc8'),(6,6,1,'home','__home__',1,'2023-10-11 18:59:41','2023-10-11 18:59:41','482dc5b1-40cf-4d10-9c52-20f32c5d5abc'),(7,7,1,'coming-soon','coming-soon',1,'2023-10-11 19:02:04','2023-10-11 19:03:32','374f73cb-7d81-42b2-b3d5-4f51f3f4cc77'),(8,8,1,'coming-soon','coming-soon',1,'2023-10-11 19:02:04','2023-10-11 19:03:32','a171fb5e-0944-406a-a69a-b1d2d5784b58'),(9,9,1,'coming-soon','coming-soon',1,'2023-10-11 19:02:04','2023-10-11 19:03:32','8118f971-d5e0-4aee-aa18-996ce78c2b1f'),(10,10,1,'coming-soon','coming-soon',1,'2023-10-11 19:02:05','2023-10-11 19:03:32','b1624dff-6a55-4e51-8211-b7fb2ac20725'),(11,11,1,'coming-soon','coming-soon',1,'2023-10-11 19:03:06','2023-10-11 19:03:32','36b19b29-03bf-4338-9c63-4cebe64ea384'),(13,13,1,'home','__home__',1,'2023-10-17 15:26:03','2023-10-17 15:26:03','59854675-02ac-44f0-b391-540659e62255'),(15,15,1,'home','__home__',1,'2023-10-17 15:27:11','2023-10-17 15:27:11','11f74f21-3d40-4e3c-8bd0-85154b947d5c');
/*!40000 ALTER TABLE `elements_sites` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `entries`
--

LOCK TABLES `entries` WRITE;
/*!40000 ALTER TABLE `entries` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `entries` VALUES (2,1,NULL,1,NULL,'2023-10-11 18:20:00',NULL,NULL,'2023-10-11 18:20:54','2023-10-11 18:20:54'),(3,1,NULL,1,NULL,'2023-10-11 18:20:00',NULL,NULL,'2023-10-11 18:20:55','2023-10-11 18:20:55'),(4,1,NULL,1,NULL,'2023-10-11 18:20:00',NULL,NULL,'2023-10-11 18:20:55','2023-10-11 18:20:55'),(5,1,NULL,1,NULL,'2023-10-11 18:20:00',NULL,NULL,'2023-10-11 18:21:17','2023-10-11 18:21:17'),(6,1,NULL,1,NULL,'2023-10-11 18:20:00',NULL,NULL,'2023-10-11 18:59:41','2023-10-11 18:59:41'),(7,2,NULL,2,1,'2023-10-11 19:02:00',NULL,0,'2023-10-11 19:02:04','2023-10-11 19:03:32'),(8,2,NULL,2,1,'2023-10-11 19:02:00',NULL,NULL,'2023-10-11 19:02:04','2023-10-11 19:03:32'),(9,2,NULL,2,1,'2023-10-11 19:02:00',NULL,NULL,'2023-10-11 19:02:04','2023-10-11 19:03:32'),(10,2,NULL,2,1,'2023-10-11 19:02:00',NULL,NULL,'2023-10-11 19:02:05','2023-10-11 19:03:32'),(11,2,NULL,2,1,'2023-10-11 19:02:00',NULL,NULL,'2023-10-11 19:03:06','2023-10-11 19:03:32'),(13,1,NULL,1,NULL,'2023-10-11 18:20:00',NULL,NULL,'2023-10-17 15:26:03','2023-10-17 15:26:03'),(15,1,NULL,1,NULL,'2023-10-11 18:20:00',NULL,NULL,'2023-10-17 15:27:11','2023-10-17 15:27:11');
/*!40000 ALTER TABLE `entries` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `entrytypes`
--

LOCK TABLES `entrytypes` WRITE;
/*!40000 ALTER TABLE `entrytypes` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `entrytypes` VALUES (1,1,1,'Home','home',0,'site',NULL,'{section.name|raw}',1,'2023-10-11 18:20:54','2023-10-11 18:20:54',NULL,'1544c59c-a6de-4760-98f3-d19fd9a5dcda'),(2,2,2,'Coming Soon','soon',1,'site',NULL,NULL,1,'2023-10-11 19:01:43','2023-10-11 19:02:04',NULL,'88d9790a-81cf-47db-98ae-a3e6550121a0');
/*!40000 ALTER TABLE `entrytypes` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `fieldgroups`
--

LOCK TABLES `fieldgroups` WRITE;
/*!40000 ALTER TABLE `fieldgroups` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `fieldgroups` VALUES (1,'Common','2023-10-11 17:57:16','2023-10-11 17:57:16',NULL,'d221a27f-3b29-4ea3-8661-437b1d7e4e95');
/*!40000 ALTER TABLE `fieldgroups` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `fieldlayoutfields`
--

LOCK TABLES `fieldlayoutfields` WRITE;
/*!40000 ALTER TABLE `fieldlayoutfields` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `fieldlayoutfields` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `fieldlayouts`
--

LOCK TABLES `fieldlayouts` WRITE;
/*!40000 ALTER TABLE `fieldlayouts` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `fieldlayouts` VALUES (1,'craft\\elements\\Entry','2023-10-11 18:20:54','2023-10-11 18:20:54',NULL,'099c96b8-7ec7-4a53-be80-0d4067ccc6a2'),(2,'craft\\elements\\Entry','2023-10-11 19:01:43','2023-10-11 19:01:43',NULL,'d594b2c7-d73f-4035-98b6-9ebe2b5a772d');
/*!40000 ALTER TABLE `fieldlayouts` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `fieldlayouttabs`
--

LOCK TABLES `fieldlayouttabs` WRITE;
/*!40000 ALTER TABLE `fieldlayouttabs` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `fieldlayouttabs` VALUES (2,1,'Content','{\"userCondition\":null,\"elementCondition\":null}','[{\"type\":\"craft\\\\fieldlayoutelements\\\\entries\\\\EntryTitleField\",\"autocomplete\":false,\"class\":null,\"size\":null,\"name\":null,\"autocorrect\":true,\"autocapitalize\":true,\"disabled\":false,\"readonly\":false,\"title\":null,\"placeholder\":null,\"step\":null,\"min\":null,\"max\":null,\"requirable\":false,\"id\":null,\"containerAttributes\":[],\"inputContainerAttributes\":[],\"labelAttributes\":[],\"orientation\":null,\"label\":\"Welcome to Crafty\",\"instructions\":null,\"tip\":null,\"warning\":null,\"width\":75,\"uid\":\"d59377cb-120a-4c5e-9a6d-b669afe0678c\",\"userCondition\":null,\"elementCondition\":null}]',1,'2023-10-11 18:21:17','2023-10-11 18:21:17','b2d40bd8-7051-4738-83ab-6c85a85f1be4'),(4,2,'Content','{\"userCondition\":null,\"elementCondition\":null}','[{\"type\":\"craft\\\\fieldlayoutelements\\\\entries\\\\EntryTitleField\",\"autocomplete\":false,\"class\":null,\"size\":null,\"name\":null,\"autocorrect\":true,\"autocapitalize\":true,\"disabled\":false,\"readonly\":false,\"title\":null,\"placeholder\":null,\"step\":null,\"min\":null,\"max\":null,\"requirable\":false,\"id\":null,\"containerAttributes\":[],\"inputContainerAttributes\":[],\"labelAttributes\":[],\"orientation\":null,\"label\":null,\"instructions\":null,\"tip\":null,\"warning\":null,\"width\":100,\"uid\":\"8c28331c-511a-4086-b2a9-49c604176ee4\",\"userCondition\":null,\"elementCondition\":null}]',1,'2023-10-11 19:02:04','2023-10-11 19:02:04','361ebf44-2779-4364-ae9e-4cb2d34a530e');
/*!40000 ALTER TABLE `fieldlayouttabs` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `fields`
--

LOCK TABLES `fields` WRITE;
/*!40000 ALTER TABLE `fields` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `fields` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `globalsets`
--

LOCK TABLES `globalsets` WRITE;
/*!40000 ALTER TABLE `globalsets` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `globalsets` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `gqlschemas`
--

LOCK TABLES `gqlschemas` WRITE;
/*!40000 ALTER TABLE `gqlschemas` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `gqlschemas` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `gqltokens`
--

LOCK TABLES `gqltokens` WRITE;
/*!40000 ALTER TABLE `gqltokens` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `gqltokens` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `imagetransforms`
--

LOCK TABLES `imagetransforms` WRITE;
/*!40000 ALTER TABLE `imagetransforms` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `imagetransforms` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `info`
--

LOCK TABLES `info` WRITE;
/*!40000 ALTER TABLE `info` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `info` VALUES (1,'4.4.14','4.4.0.4',0,'uyuprittrjvt','3@mbyrbizfsg','2023-10-11 17:57:16','2023-10-17 20:26:05','355506bc-f2df-49cd-9bbb-d71d0dee2acd');
/*!40000 ALTER TABLE `info` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `matrixblocks`
--

LOCK TABLES `matrixblocks` WRITE;
/*!40000 ALTER TABLE `matrixblocks` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `matrixblocks` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `matrixblocks_owners`
--

LOCK TABLES `matrixblocks_owners` WRITE;
/*!40000 ALTER TABLE `matrixblocks_owners` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `matrixblocks_owners` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `matrixblocktypes`
--

LOCK TABLES `matrixblocktypes` WRITE;
/*!40000 ALTER TABLE `matrixblocktypes` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `matrixblocktypes` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `migrations`
--

LOCK TABLES `migrations` WRITE;
/*!40000 ALTER TABLE `migrations` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `migrations` VALUES (1,'craft','Install','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','09f8b24a-0b36-4e68-b599-328a30dc3fbd'),(2,'craft','m210121_145800_asset_indexing_changes','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','2f06fde8-1520-4851-a65f-15db6594e940'),(3,'craft','m210624_222934_drop_deprecated_tables','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','e9c09145-8fa4-4687-9979-cbd8799903a1'),(4,'craft','m210724_180756_rename_source_cols','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','729b7720-e387-4225-935f-3ea10c550a32'),(5,'craft','m210809_124211_remove_superfluous_uids','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','2304882c-1237-4cad-9a0f-1f424e10fe7e'),(6,'craft','m210817_014201_universal_users','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','92de335d-674b-4c62-a11d-84f48bdb2fc6'),(7,'craft','m210904_132612_store_element_source_settings_in_project_config','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','6c966130-6d80-4601-988e-3bdd66369f49'),(8,'craft','m211115_135500_image_transformers','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','c5934695-0366-4055-add5-745c23077fdc'),(9,'craft','m211201_131000_filesystems','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','66a0f0d1-a728-4572-bcbc-8e5f8c95f85d'),(10,'craft','m220103_043103_tab_conditions','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','8074f559-2a01-4ccd-81d5-15da5ba710d3'),(11,'craft','m220104_003433_asset_alt_text','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','2fd80a6d-533d-49b7-9f3f-58407edf34f5'),(12,'craft','m220123_213619_update_permissions','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','45c68ec8-e95c-4a68-9bc9-84444b587617'),(13,'craft','m220126_003432_addresses','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','b5b6fda1-f712-4124-a150-cbf6ebc7af71'),(14,'craft','m220209_095604_add_indexes','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','9486547b-4e5e-484b-9bc9-49f288d2d29b'),(15,'craft','m220213_015220_matrixblocks_owners_table','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','caa176af-6c2f-4f35-a8ce-9d2a1995aedf'),(16,'craft','m220214_000000_truncate_sessions','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','5a056b95-433f-4945-84e7-3ead3d7e4e8c'),(17,'craft','m220222_122159_full_names','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','c164f382-c611-4492-a784-abe9867b8d0e'),(18,'craft','m220223_180559_nullable_address_owner','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','24a307c2-6483-47cb-a099-44f8e806dc83'),(19,'craft','m220225_165000_transform_filesystems','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','cba0efe7-e53c-4c69-b6be-6142d7265a80'),(20,'craft','m220309_152006_rename_field_layout_elements','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','a45670ce-f8ae-43b1-8fa5-686b8bbcb986'),(21,'craft','m220314_211928_field_layout_element_uids','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','32130950-a965-4240-b9c5-c87c529c9a84'),(22,'craft','m220316_123800_transform_fs_subpath','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','382a10a0-8980-4c5a-90cd-075c626837e8'),(23,'craft','m220317_174250_release_all_jobs','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','a21e435e-55f2-4ada-8dd3-15090132b5e2'),(24,'craft','m220330_150000_add_site_gql_schema_components','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','22b0880a-851c-4630-9ce5-7fec38eb791d'),(25,'craft','m220413_024536_site_enabled_string','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','0019254e-57b5-4141-8e2f-7a5254d1b510'),(26,'craft','m221027_160703_add_image_transform_fill','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','8b70e6cb-bc1d-4c17-9f5d-bbb09551bb9e'),(27,'craft','m221028_130548_add_canonical_id_index','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','abd930ad-f261-46d8-9dd3-e4415097039c'),(28,'craft','m221118_003031_drop_element_fks','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','9eb4baf2-e15c-4418-ae26-39309d4c806f'),(29,'craft','m230131_120713_asset_indexing_session_new_options','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','cd9daaf8-11cd-4940-8621-d22855542395'),(30,'craft','m230226_013114_drop_plugin_license_columns','2023-10-11 17:57:16','2023-10-11 17:57:16','2023-10-11 17:57:16','43c88082-c91c-4991-a81e-6451fe6c498f');
/*!40000 ALTER TABLE `migrations` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `plugins`
--

LOCK TABLES `plugins` WRITE;
/*!40000 ALTER TABLE `plugins` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `plugins` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `projectconfig`
--

LOCK TABLES `projectconfig` WRITE;
/*!40000 ALTER TABLE `projectconfig` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `projectconfig` VALUES ('dateModified','1697574365'),('email.fromEmail','\"admin@surveillance.htb\"'),('email.fromName','\"$SERVER_NAME\"'),('email.replyToEmail','null'),('email.template','null'),('email.transportSettings.command','\"/usr/sbin/sendmail -bs\"'),('email.transportType','\"craft\\\\mail\\\\transportadapters\\\\Sendmail\"'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elementCondition','null'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.autocapitalize','true'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.autocomplete','false'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.autocorrect','true'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.class','null'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.disabled','false'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.elementCondition','null'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.id','null'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.instructions','null'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.label','\"Welcome to Crafty\"'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.max','null'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.min','null'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.name','null'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.orientation','null'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.placeholder','null'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.readonly','false'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.requirable','false'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.size','null'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.step','null'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.tip','null'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.title','null'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.type','\"craft\\\\fieldlayoutelements\\\\entries\\\\EntryTitleField\"'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.uid','\"d59377cb-120a-4c5e-9a6d-b669afe0678c\"'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.userCondition','null'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.warning','null'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.elements.0.width','75'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.name','\"Content\"'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.uid','\"b2d40bd8-7051-4738-83ab-6c85a85f1be4\"'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.fieldLayouts.099c96b8-7ec7-4a53-be80-0d4067ccc6a2.tabs.0.userCondition','null'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.handle','\"home\"'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.hasTitleField','false'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.name','\"Home\"'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.section','\"2fef98dc-6c79-43b0-89c2-67dbad131f0f\"'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.sortOrder','1'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.titleFormat','\"{section.name|raw}\"'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.titleTranslationKeyFormat','null'),('entryTypes.1544c59c-a6de-4760-98f3-d19fd9a5dcda.titleTranslationMethod','\"site\"'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elementCondition','null'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.autocapitalize','true'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.autocomplete','false'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.autocorrect','true'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.class','null'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.disabled','false'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.elementCondition','null'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.id','null'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.instructions','null'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.label','null'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.max','null'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.min','null'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.name','null'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.orientation','null'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.placeholder','null'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.readonly','false'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.requirable','false'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.size','null'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.step','null'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.tip','null'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.title','null'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.type','\"craft\\\\fieldlayoutelements\\\\entries\\\\EntryTitleField\"'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.uid','\"8c28331c-511a-4086-b2a9-49c604176ee4\"'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.userCondition','null'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.warning','null'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.elements.0.width','100'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.name','\"Content\"'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.uid','\"361ebf44-2779-4364-ae9e-4cb2d34a530e\"'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.fieldLayouts.d594b2c7-d73f-4035-98b6-9ebe2b5a772d.tabs.0.userCondition','null'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.handle','\"soon\"'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.hasTitleField','true'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.name','\"Coming Soon\"'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.section','\"2ee5c3db-b015-4f81-8295-b56aee9388b4\"'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.sortOrder','1'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.titleFormat','null'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.titleTranslationKeyFormat','null'),('entryTypes.88d9790a-81cf-47db-98ae-a3e6550121a0.titleTranslationMethod','\"site\"'),('fieldGroups.d221a27f-3b29-4ea3-8661-437b1d7e4e95.name','\"Common\"'),('graphql.publicToken.enabled','false'),('graphql.publicToken.expiryDate','null'),('meta.__names__.1544c59c-a6de-4760-98f3-d19fd9a5dcda','\"Home\"'),('meta.__names__.2ee5c3db-b015-4f81-8295-b56aee9388b4','\"Coming Soon\"'),('meta.__names__.2fef98dc-6c79-43b0-89c2-67dbad131f0f','\"Home\"'),('meta.__names__.88d9790a-81cf-47db-98ae-a3e6550121a0','\"Coming Soon\"'),('meta.__names__.99bbec32-db93-4f58-a075-2562f99e8090','\"Surveillance\"'),('meta.__names__.9f9fdbdf-6b19-4af3-bb60-c48479e600fe','\"surveillance\"'),('meta.__names__.bd1f59d6-d10a-4d80-a009-621d7e091db2','\"Public Schema\"'),('meta.__names__.d221a27f-3b29-4ea3-8661-437b1d7e4e95','\"Common\"'),('sections.2ee5c3db-b015-4f81-8295-b56aee9388b4.defaultPlacement','\"end\"'),('sections.2ee5c3db-b015-4f81-8295-b56aee9388b4.enableVersioning','true'),('sections.2ee5c3db-b015-4f81-8295-b56aee9388b4.handle','\"soon\"'),('sections.2ee5c3db-b015-4f81-8295-b56aee9388b4.name','\"Coming Soon\"'),('sections.2ee5c3db-b015-4f81-8295-b56aee9388b4.propagationMethod','\"all\"'),('sections.2ee5c3db-b015-4f81-8295-b56aee9388b4.siteSettings.99bbec32-db93-4f58-a075-2562f99e8090.enabledByDefault','true'),('sections.2ee5c3db-b015-4f81-8295-b56aee9388b4.siteSettings.99bbec32-db93-4f58-a075-2562f99e8090.hasUrls','true'),('sections.2ee5c3db-b015-4f81-8295-b56aee9388b4.siteSettings.99bbec32-db93-4f58-a075-2562f99e8090.template','\"coming-soon/_entry.twig\"'),('sections.2ee5c3db-b015-4f81-8295-b56aee9388b4.siteSettings.99bbec32-db93-4f58-a075-2562f99e8090.uriFormat','\"coming-soon\"'),('sections.2ee5c3db-b015-4f81-8295-b56aee9388b4.type','\"channel\"'),('sections.2fef98dc-6c79-43b0-89c2-67dbad131f0f.defaultPlacement','\"end\"'),('sections.2fef98dc-6c79-43b0-89c2-67dbad131f0f.enableVersioning','true'),('sections.2fef98dc-6c79-43b0-89c2-67dbad131f0f.handle','\"home\"'),('sections.2fef98dc-6c79-43b0-89c2-67dbad131f0f.name','\"Home\"'),('sections.2fef98dc-6c79-43b0-89c2-67dbad131f0f.propagationMethod','\"all\"'),('sections.2fef98dc-6c79-43b0-89c2-67dbad131f0f.siteSettings.99bbec32-db93-4f58-a075-2562f99e8090.enabledByDefault','true'),('sections.2fef98dc-6c79-43b0-89c2-67dbad131f0f.siteSettings.99bbec32-db93-4f58-a075-2562f99e8090.hasUrls','true'),('sections.2fef98dc-6c79-43b0-89c2-67dbad131f0f.siteSettings.99bbec32-db93-4f58-a075-2562f99e8090.template','\"home/_entry\"'),('sections.2fef98dc-6c79-43b0-89c2-67dbad131f0f.siteSettings.99bbec32-db93-4f58-a075-2562f99e8090.uriFormat','\"__home__\"'),('sections.2fef98dc-6c79-43b0-89c2-67dbad131f0f.type','\"single\"'),('siteGroups.9f9fdbdf-6b19-4af3-bb60-c48479e600fe.name','\"surveillance\"'),('sites.99bbec32-db93-4f58-a075-2562f99e8090.baseUrl','\"@web\"'),('sites.99bbec32-db93-4f58-a075-2562f99e8090.enabled','true'),('sites.99bbec32-db93-4f58-a075-2562f99e8090.handle','\"default\"'),('sites.99bbec32-db93-4f58-a075-2562f99e8090.hasUrls','true'),('sites.99bbec32-db93-4f58-a075-2562f99e8090.language','\"en-US\"'),('sites.99bbec32-db93-4f58-a075-2562f99e8090.name','\"Surveillance\"'),('sites.99bbec32-db93-4f58-a075-2562f99e8090.primary','true'),('sites.99bbec32-db93-4f58-a075-2562f99e8090.siteGroup','\"9f9fdbdf-6b19-4af3-bb60-c48479e600fe\"'),('sites.99bbec32-db93-4f58-a075-2562f99e8090.sortOrder','1'),('system.edition','\"solo\"'),('system.live','true'),('system.name','\"Surveillance\"'),('system.retryDuration','null'),('system.schemaVersion','\"4.4.0.4\"'),('system.timeZone','\"America/Los_Angeles\"'),('users.allowPublicRegistration','false'),('users.defaultGroup','null'),('users.photoSubpath','null'),('users.photoVolumeUid','null'),('users.requireEmailVerification','true');
/*!40000 ALTER TABLE `projectconfig` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `queue`
--

LOCK TABLES `queue` WRITE;
/*!40000 ALTER TABLE `queue` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `queue` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `relations`
--

LOCK TABLES `relations` WRITE;
/*!40000 ALTER TABLE `relations` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `relations` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `revisions`
--

LOCK TABLES `revisions` WRITE;
/*!40000 ALTER TABLE `revisions` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `revisions` VALUES (1,2,1,1,NULL),(2,2,1,2,NULL),(3,2,1,3,NULL),(4,2,1,4,NULL),(5,7,1,1,NULL),(6,7,1,2,NULL),(7,7,1,3,NULL),(8,7,1,4,NULL),(9,2,1,5,'Applied “Draft 1”'),(10,2,1,6,'Applied “Draft 1”');
/*!40000 ALTER TABLE `revisions` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `searchindex`
--

LOCK TABLES `searchindex` WRITE;
/*!40000 ALTER TABLE `searchindex` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `searchindex` VALUES (1,'email',0,1,' admin surveillance htb '),(1,'firstname',0,1,' matthew '),(1,'fullname',0,1,' matthew b '),(1,'lastname',0,1,' b '),(1,'slug',0,1,''),(1,'username',0,1,' admin '),(2,'slug',0,1,' home '),(2,'title',0,1,' home '),(7,'slug',0,1,' coming soon '),(7,'title',0,1,' coming soon ');
/*!40000 ALTER TABLE `searchindex` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `sections`
--

LOCK TABLES `sections` WRITE;
/*!40000 ALTER TABLE `sections` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `sections` VALUES (1,NULL,'Home','home','single',1,'all','end',NULL,'2023-10-11 18:20:54','2023-10-11 18:20:54',NULL,'2fef98dc-6c79-43b0-89c2-67dbad131f0f'),(2,NULL,'Coming Soon','soon','channel',1,'all','end',NULL,'2023-10-11 19:01:43','2023-10-11 19:03:31',NULL,'2ee5c3db-b015-4f81-8295-b56aee9388b4');
/*!40000 ALTER TABLE `sections` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `sections_sites`
--

LOCK TABLES `sections_sites` WRITE;
/*!40000 ALTER TABLE `sections_sites` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `sections_sites` VALUES (1,1,1,1,'__home__','home/_entry',1,'2023-10-11 18:20:54','2023-10-11 18:20:54','d9d0da07-7b9c-41c3-bf82-78d04d38f6f3'),(2,2,1,1,'coming-soon','coming-soon/_entry.twig',1,'2023-10-11 19:01:43','2023-10-11 19:03:31','d62f4930-4b84-4eed-95d2-c8bcd77835b3');
/*!40000 ALTER TABLE `sections_sites` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `sequences`
--

LOCK TABLES `sequences` WRITE;
/*!40000 ALTER TABLE `sequences` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `sequences` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `shunnedmessages`
--

LOCK TABLES `shunnedmessages` WRITE;
/*!40000 ALTER TABLE `shunnedmessages` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `shunnedmessages` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `sitegroups`
--

LOCK TABLES `sitegroups` WRITE;
/*!40000 ALTER TABLE `sitegroups` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `sitegroups` VALUES (1,'surveillance','2023-10-11 17:57:16','2023-10-17 20:25:05',NULL,'9f9fdbdf-6b19-4af3-bb60-c48479e600fe');
/*!40000 ALTER TABLE `sitegroups` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `sites`
--

LOCK TABLES `sites` WRITE;
/*!40000 ALTER TABLE `sites` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `sites` VALUES (1,1,1,'1','Surveillance','default','en-US',1,'@web',1,'2023-10-11 17:57:16','2023-10-17 20:25:24',NULL,'99bbec32-db93-4f58-a075-2562f99e8090');
/*!40000 ALTER TABLE `sites` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `structureelements`
--

LOCK TABLES `structureelements` WRITE;
/*!40000 ALTER TABLE `structureelements` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `structureelements` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `structures`
--

LOCK TABLES `structures` WRITE;
/*!40000 ALTER TABLE `structures` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `structures` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `systemmessages`
--

LOCK TABLES `systemmessages` WRITE;
/*!40000 ALTER TABLE `systemmessages` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `systemmessages` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `taggroups`
--

LOCK TABLES `taggroups` WRITE;
/*!40000 ALTER TABLE `taggroups` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `taggroups` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `tokens`
--

LOCK TABLES `tokens` WRITE;
/*!40000 ALTER TABLE `tokens` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `tokens` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `usergroups`
--

LOCK TABLES `usergroups` WRITE;
/*!40000 ALTER TABLE `usergroups` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `usergroups` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `usergroups_users`
--

LOCK TABLES `usergroups_users` WRITE;
/*!40000 ALTER TABLE `usergroups_users` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `usergroups_users` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `userpermissions`
--

LOCK TABLES `userpermissions` WRITE;
/*!40000 ALTER TABLE `userpermissions` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `userpermissions` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `userpermissions_usergroups`
--

LOCK TABLES `userpermissions_usergroups` WRITE;
/*!40000 ALTER TABLE `userpermissions_usergroups` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `userpermissions_usergroups` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `userpermissions_users`
--

LOCK TABLES `userpermissions_users` WRITE;
/*!40000 ALTER TABLE `userpermissions_users` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `userpermissions_users` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `userpreferences`
--

LOCK TABLES `userpreferences` WRITE;
/*!40000 ALTER TABLE `userpreferences` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `userpreferences` VALUES (1,'{\"language\":\"en-US\",\"locale\":null,\"weekStartDay\":\"1\",\"alwaysShowFocusRings\":false,\"useShapes\":false,\"underlineLinks\":false,\"notificationDuration\":\"5000\",\"showFieldHandles\":false,\"enableDebugToolbarForSite\":false,\"enableDebugToolbarForCp\":false,\"showExceptionView\":false,\"profileTemplates\":false}');
/*!40000 ALTER TABLE `userpreferences` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `users` VALUES (1,NULL,1,0,0,0,1,'admin','Matthew B','Matthew','B','admin@surveillance.htb','39ed84b22ddc63ab3725a1820aaa7f73a8f3f10d0848123562c9f35c675770ec','2023-10-17 20:22:34',NULL,NULL,NULL,'2023-10-11 18:58:57',NULL,1,NULL,NULL,NULL,0,'2023-10-17 20:27:46','2023-10-11 17:57:16','2023-10-17 20:27:46');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `volumefolders`
--

LOCK TABLES `volumefolders` WRITE;
/*!40000 ALTER TABLE `volumefolders` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `volumefolders` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `volumes`
--

LOCK TABLES `volumes` WRITE;
/*!40000 ALTER TABLE `volumes` DISABLE KEYS */;
set autocommit=0;
/*!40000 ALTER TABLE `volumes` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping data for table `widgets`
--

LOCK TABLES `widgets` WRITE;
/*!40000 ALTER TABLE `widgets` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `widgets` VALUES (1,1,'craft\\widgets\\RecentEntries',1,NULL,'{\"siteId\":1,\"section\":\"*\",\"limit\":10}',1,'2023-10-11 17:59:40','2023-10-11 17:59:40','90a4c24e-14e7-433e-935f-9cdc0ab5b25a'),(2,1,'craft\\widgets\\CraftSupport',2,NULL,'[]',1,'2023-10-11 17:59:40','2023-10-11 17:59:40','019d0015-4f49-428a-833c-cdc5fb65d47a'),(3,1,'craft\\widgets\\Updates',3,NULL,'[]',1,'2023-10-11 17:59:40','2023-10-11 17:59:40','4a79d7be-0700-4288-85f8-2da6eb1de79e'),(4,1,'craft\\widgets\\Feed',4,NULL,'{\"url\":\"https:\\/\\/craftcms.com\\/news.rss\",\"title\":\"Craft News\",\"limit\":5}',1,'2023-10-11 17:59:40','2023-10-11 17:59:40','d96e4029-11c1-4759-9de9-24c1b35739fd');
/*!40000 ALTER TABLE `widgets` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Dumping routines for database 'craftdb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-17 20:28:02
