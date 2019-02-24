 "CREATE TABLE EVENT_NAMES ( EVENT_NAME_PID INTEGER NOT NULL, INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 17 NOT NULL, LABEL VARCHAR(2147483647) NOT NULL, EVENT_TYPE_PID INTEGER );",
 "CREATE TABLE PERSONS ( PERSON_PID INTEGER NOT NULL, INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 15 NOT NULL, BIRTH_DATE_PID INTEGER, DEATH_DATE_PID INTEGER );",
 "CREATE TABLE PERSON_NAME_MAPS ( NAME_MAP_PID INTEGER NOT NULL, NAME_STYLE_PID INTEGER NOT NULL, PART_NO INTEGER NOT NULL, LABEL_PID INTEGER NOT NULL, INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 13 NOT NULL );",
 "CREATE TABLE LANGUAGES ( LANGUAGE_PID INTEGER NOT NULL, ISOCODE CHAR(4) NOT NULL, LABEL CHAR(30) NOT NULL, INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 7 NOT NULL );",
 "CREATE TABLE PERSON_EVENTS ( PERSON_EVENT_PID INTEGER NOT NULL, EVENT_PID INTEGER NOT NULL, PERSON_PID INTEGER NOT NULL, ROLE VARCHAR(2147483647) NOT NULL, PRIMARY_PERSON BOOLEAN NOT NULL, PRIMARY_EVENT BOOLEAN NOT NULL, INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 2 NOT NULL, LANGUAGE_PID INTEGER );",
 "CREATE TABLE COMMIT_LOGS ( COMMIT_LOG_PID INTEGER NOT NULL, CHANGED_TABLE_ID INTEGER NOT NULL, CHANGED_RECORD_PID INTEGER NOT NULL, CHANGED_TIMESTAMP TIMESTAMP NOT NULL, USERID_PID INTEGER NOT NULL, COLUMN_NAME_LIST VARCHAR(2147483647) NOT NULL, COLUMN_DATA_LIST VARCHAR(2147483647) NOT NULL, INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 19 NOT NULL );",
 "CREATE TABLE USERIDS ( USERID_PID INTEGER NOT NULL, PERSON_PID INTEGER NOT NULL, INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 20 NOT NULL );",
 "CREATE TABLE PARTNERS ( PARTNER_PID INTEGER NOT NULL, PARTNER1 INTEGER NOT NULL, PARTNER2 INTEGER NOT NULL, PRIMARY_PARTNER BOOLEAN NOT NULL, ROLE VARCHAR(2147483647) NOT NULL, INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 5 NOT NULL, FROM_DATE_PID INTEGER, TO_DATE_PID INTEGER, LANGUAGE_PID INTEGER );",
 "CREATE TABLE LOCATION_NAMES ( LOCATION_NAME_PID INTEGER NOT NULL, LOCATION_PID INTEGER NOT NULL, PRIMARY_LOCATION_NAME BOOLEAN NOT NULL, LOCATION_NAME_STYLE_PID INTEGER NOT NULL, PREPOSITION VARCHAR(2147483647), INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 8 NOT NULL, FROM_DATE_PID INTEGER, TO_DATE_PID INTEGER );",
 "CREATE TABLE SEXES ( SEXES_PID INTEGER NOT NULL, PERSON_PID INTEGER NOT NULL, SEX_TYPE_PID INTEGER NOT NULL, PRIMARY_SEX BOOLEAN NOT NULL, INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 11 NOT NULL, FROM_DATE_PID INTEGER, TO_DATE_PID INTEGER );",
 "CREATE TABLE EVENTS ( EVENT_PID INTEGER NOT NULL, INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 12 NOT NULL, FROM_DATE_PID INTEGER, TO_DATE_PID INTEGER, EVENT_NAME_PID INTEGER NOT NULL );",
 "CREATE TABLE EVENT_TYPES ( EVENT_TYPE_PID INTEGER NOT NULL, ABBREVIATION CHAR(8) NOT NULL, INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 23 NOT NULL, LABEL_PID INTEGER NOT NULL );",
 "CREATE TABLE LOCATION_NAME_STYLES ( LOCATION_NAME_STYLE_PID INTEGER NOT NULL, LABEL_PID INTEGER NOT NULL, INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 16 NOT NULL, FROM_DATE_PID INTEGER, TO_DATE_PID INTEGER );",
 "CREATE TABLE LOCATION_NAME_PARTS ( LOCATION_NAME_PART_PID INTEGER NOT NULL, LOCATION_NAME_PID INTEGER NOT NULL, LABEL VARCHAR(2147483647) NOT NULL, PART_NO INTEGER NOT NULL, INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 18 NOT NULL );",
 "CREATE TABLE HDATES ( HDATE_PID INTEGER NOT NULL, INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 3 NOT NULL, ORIGINAL_TEXT VARCHAR(160) NOT NULL, DATE DATE NOT NULL, SORT_DATE DATE NOT NULL, SURETY VARCHAR(10) NOT NULL );",
 "CREATE TABLE PARENTS ( PARENT_PID INTEGER NOT NULL, CHILD INTEGER NOT NULL, PARENT INTEGER NOT NULL, PARENT_ROLE VARCHAR(2147483647) NOT NULL, PRIMARY_PARENT BOOLEAN NOT NULL, INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 22 NOT NULL, LANGUAGE_PID INTEGER );",
 "CREATE TABLE PERSON_NAMES ( NAME_PID INTEGER NOT NULL, PERSON_PID INTEGER NOT NULL, PRIMARY_NAME BOOLEAN, NAME_STYLE_PID INTEGER NOT NULL, INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 10 NOT NULL, FROM_DATE_PID INTEGER, TO_DATE_PID INTEGER );",
 "CREATE TABLE LOCATIONS ( LOCATION_PID INTEGER NOT NULL, PRIMARY_LOCATION BOOLEAN NOT NULL, X_COORDINATE DECIMAL(65535 , 32767), Y_COORDINATE DECIMAL(65535 , 32767), Z_COORDINATE DECIMAL(65535 , 32767), INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 9 NOT NULL, FROM_DATE_PID INTEGER, TO_DATE_PID INTEGER );",
 "CREATE TABLE SEX_TYPES ( SEX_TYPE_PID INTEGER NOT NULL, ABBREVIATION CHAR(1) NOT NULL, INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 1 NOT NULL, LABEL_PID INTEGER NOT NULL );",
 "CREATE TABLE LOCATION_NAME_MAPS ( LOCATION_NAME_MAP_PID INTEGER NOT NULL, PART_NO INTEGER NOT NULL, LOCATION_NAME_STYLE_PID INTEGER NOT NULL, LABEL_POSITION CHAR(4) NOT NULL, INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 21 NOT NULL, LABEL_PID INTEGER NOT NULL );",
 "CREATE TABLE PERSON_NAME_STYLES ( NAME_STYLE_PID INTEGER NOT NULL, LABEL_PID INTEGER NOT NULL, INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 14 NOT NULL );",
 "CREATE TABLE LOCATION_EVENTS ( LOCATION_EVENTS_PID INTEGER NOT NULL, EVENT_PID INTEGER NOT NULL, LOCATION_PID INTEGER NOT NULL, PRIMARY_EVENT BOOLEAN NOT NULL, PRIMARY_LOCATION BOOLEAN NOT NULL, INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 4 NOT NULL );",
 "CREATE TABLE DICTIONARY ( DICTIONARY_PID INTEGER NOT NULL, LABEL_PID INTEGER NOT NULL, ISO_CODE CHAR(4) NOT NULL, LABEL VARCHAR(2147483647) NOT NULL, INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 6 NOT NULL, LABEL_TYPE VARCHAR(2147483647) );",
 "CREATE TABLE PERSON_NAME_PARTS ( NAME_PART_PID INTEGER NOT NULL, NAME_PID INTEGER NOT NULL, LABEL VARCHAR(2147483647), PART_NO INTEGER NOT NULL, INSERT_TSTMP TIMESTAMP NOT NULL, UPDATE_TSTMP TIMESTAMP NOT NULL, TABLE_ID INTEGER DEFAULT 24 NOT NULL );",
 "CREATE UNIQUE INDEX PRIMARY_KEY_F8 ON PERSON_NAME_STYLES (NAME_STYLE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_D16 ON EVENT_NAMES (EVENT_NAME_PID ASC);",
 "CREATE INDEX SEX_TYPES_SEXES_FK_INDEX_4 ON SEXES (SEX_TYPE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_7EB9F ON HDATES (HDATE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_7584AC ON PARTNERS (PARTNER_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_1 ON PARTNERS (PARTNER_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_6D9 ON PARENTS (PARENT_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_371D3B ON LOCATION_NAME_STYLES (LOCATION_NAME_STYLE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_4B24 ON SEXES (SEXES_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_7EB9FB1 ON HDATES (HDATE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_5EBC6 ON LOCATIONS (LOCATION_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_8FE5 ON LOCATION_NAME_MAPS (LOCATION_NAME_MAP_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_C4 ON PERSON_NAME_MAPS (NAME_MAP_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_22 ON USERIDS (USERID_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_A7 ON PERSON_NAME_PARTS (NAME_PART_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_97E ON LANGUAGES (LANGUAGE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_46 ON PERSON_NAMES (NAME_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_25B5B ON PERSONS (PERSON_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_D9 ON COMMIT_LOGS (COMMIT_LOG_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_4A94 ON PERSON_EVENTS (PERSON_EVENT_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_4A9 ON PERSON_EVENTS (PERSON_EVENT_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_E7EB ON SEX_TYPES (SEX_TYPE_PID ASC);",
 "CREATE INDEX PERSONS_PARTNERS_FK_INDEX_1 ON PARTNERS (PARTNER1 ASC);",
 "CREATE INDEX CONSTRAINT_4A94_INDEX_1 ON PERSON_EVENTS (PERSON_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_7584A ON PARTNERS (PARTNER_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_7E ON HDATES (HDATE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_50 ON LOCATION_NAME_MAPS (LOCATION_NAME_MAP_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_DC ON COMMIT_LOGS (COMMIT_LOG_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_22069 ON USERIDS (USERID_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_6C ON LOCATION_NAME_PARTS (LOCATION_NAME_PART_PID ASC);",
 "CREATE INDEX CONSTRAINT_75_INDEX_1 ON PARTNERS (FROM_DATE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_DF ON LOCATIONS (LOCATION_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_D16F79 ON EVENT_NAMES (EVENT_NAME_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_75 ON PARTNERS (PARTNER_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_6CEE ON LOCATION_NAME_PARTS (LOCATION_NAME_PART_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_A ON PERSON_NAMES (NAME_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_4B2 ON SEXES (SEXES_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_5 ON LOCATIONS (LOCATION_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_378F ON DICTIONARY (DICTIONARY_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_BC ON LOCATION_NAMES (LOCATION_NAME_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_371D ON LOCATION_NAME_STYLES (LOCATION_NAME_STYLE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_D1 ON EVENT_NAMES (EVENT_NAME_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_AE07 ON PERSON_NAMES (NAME_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_2 ON PERSONS (PERSON_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_DEE ON EVENTS (EVENT_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_5E ON LOCATIONS (LOCATION_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_B ON LOCATION_NAMES (LOCATION_NAME_PID ASC);",
 "CREATE INDEX CONSTRAINT_INDEX_5 ON LOCATION_NAME_MAPS (LOCATION_NAME_STYLE_PID ASC);",
 "CREATE INDEX PERSONS_PARTNERS_FK1_INDEX_1 ON PARTNERS (PARTNER2 ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_97E7F ON LANGUAGES (LANGUAGE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_371D3 ON LOCATION_NAME_STYLES (LOCATION_NAME_STYLE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_BC9C ON LOCATION_NAMES (LOCATION_NAME_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_3 ON LOCATION_NAME_MAPS (LOCATION_NAME_MAP_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_7EB9FB ON HDATES (HDATE_PID ASC);",
 "CREATE INDEX PERSON_NAME_STYLES_PERSON_NAMES_FK_INDEX_1 ON PERSON_NAMES (NAME_STYLE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_732F ON PERSON_NAME_PARTS (NAME_PART_PID ASC);",
 "CREATE INDEX CONSTRAINT_INDEX_5E ON LOCATIONS (FROM_DATE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_DC31 ON COMMIT_LOGS (COMMIT_LOG_PID ASC);",
 "CREATE INDEX CONSTRAINT_INDEX_BC ON LOCATION_NAMES (FROM_DATE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_5EBC67 ON LOCATIONS (LOCATION_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_378F5 ON DICTIONARY (DICTIONARY_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_BC9 ON LOCATION_NAMES (LOCATION_NAME_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_3B ON PERSON_NAME_PARTS (NAME_PART_PID ASC);",
 "CREATE INDEX PERSONS_PERSON_NAMES_FK_INDEX_1 ON PERSON_NAMES (PERSON_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_320 ON LOCATION_NAME_MAPS (LOCATION_NAME_MAP_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_FB81 ON PARENTS (PARENT_PID ASC);",
 "CREATE INDEX CONSTRAINT_C8_INDEX_9 ON LOCATION_EVENTS (LOCATION_PID ASC);",
 "CREATE INDEX CONSTRAINT_7A9A_INDEX_D ON EVENTS (TO_DATE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_FB8198 ON PARENTS (PARENT_PID ASC);",
 "CREATE INDEX CONSTRAINT_4A9_INDEX_1 ON PERSON_EVENTS (EVENT_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_DC310 ON COMMIT_LOGS (COMMIT_LOG_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_8FE ON LOCATION_NAME_MAPS (LOCATION_NAME_MAP_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_D ON EVENT_TYPES (EVENT_TYPE_PID ASC);",
 "CREATE INDEX PERSONS_PARENTS_FK1_INDEX_F ON PARENTS (PARENT ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_9 ON LANGUAGES (LANGUAGE_PID ASC);",
 "CREATE UNIQUE INDEX CONSTRAINT_4A94_INDEX_E ON PERSONS (PERSON_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_C8 ON LOCATION_EVENTS (LOCATION_EVENTS_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_FB8 ON PARENTS (PARENT_PID ASC);",
 "CREATE INDEX CONSTRAINT_4B2_INDEX_4 ON SEXES (FROM_DATE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_7EB ON HDATES (HDATE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_5EBC ON LOCATIONS (LOCATION_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_3B2 ON PERSON_NAME_PARTS (NAME_PART_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_DFD ON LOCATIONS (LOCATION_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_B963 ON PERSON_NAME_MAPS (NAME_MAP_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_F8FBC ON PERSON_NAME_STYLES (NAME_STYLE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_8F ON LOCATION_NAME_MAPS (LOCATION_NAME_MAP_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_6CEEF ON LOCATION_NAME_PARTS (LOCATION_NAME_PART_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_220 ON USERIDS (USERID_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_7A9AD ON EVENTS (EVENT_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_1FE ON PERSON_EVENTS (PERSON_EVENT_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_E7E ON SEX_TYPES (SEX_TYPE_PID ASC);",
 "CREATE INDEX CONSTRAINT_4B24_INDEX_4 ON SEXES (TO_DATE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_BC9C7 ON LOCATION_NAMES (LOCATION_NAME_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_E0 ON LOCATION_NAME_STYLES (LOCATION_NAME_STYLE_PID ASC);",
 "CREATE INDEX CONSTRAINT_INDEX_5EB ON LOCATIONS (TO_DATE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_AE0 ON PERSON_NAMES (NAME_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_439 ON LOCATION_NAMES (LOCATION_NAME_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_506 ON LOCATION_NAME_MAPS (LOCATION_NAME_MAP_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_95 ON LOCATION_EVENTS (LOCATION_EVENTS_PID ASC);",
 "CREATE INDEX CONSTRAINT_INDEX_4 ON PERSON_NAMES (FROM_DATE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_BC9C76 ON LOCATION_NAMES (LOCATION_NAME_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_E7EB37 ON SEX_TYPES (SEX_TYPE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_2206 ON USERIDS (USERID_PID ASC);",
 "CREATE INDEX LOCATION_NAME_STYLES_LOCATION_NAMES_FK_INDEX_4 ON LOCATION_NAMES (LOCATION_NAME_STYLE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_7 ON EVENTS (EVENT_PID ASC);",
 "CREATE UNIQUE INDEX LANGUAGES_DICTIONARY_FK_INDEX_9_INDEX_9 ON LANGUAGES (ISOCODE ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_7A9 ON EVENTS (EVENT_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_4B24F ON SEXES (SEXES_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_1CE ON LOCATION_NAME_PARTS (LOCATION_NAME_PART_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_C ON LOCATION_EVENTS (LOCATION_EVENTS_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_795 ON USERIDS (USERID_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_6 ON PERSON_NAME_STYLES (NAME_STYLE_PID ASC);",
 "CREATE INDEX LANGUAGES_DICTIONARY_FK_INDEX_3 ON DICTIONARY (ISO_CODE ASC);",
 "CREATE INDEX CONSTRAINT_INDEX_4A ON PERSON_EVENTS (LANGUAGE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_378F5C ON DICTIONARY (DICTIONARY_PID ASC);",
 "CREATE INDEX PERSONS_SEXES_FK_INDEX_4 ON SEXES (PERSON_PID ASC);",
 "CREATE INDEX PERSON_NAME_STYLES_NAME_MAPS_FK_INDEX_F ON PERSON_NAME_MAPS (NAME_STYLE_PID ASC);",
 "CREATE INDEX CONSTRAINT_INDEX_B ON LOCATION_NAME_PARTS (LOCATION_NAME_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_6CE ON LOCATION_NAME_PARTS (LOCATION_NAME_PART_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_D16F7 ON EVENT_NAMES (EVENT_NAME_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_BCB ON LOCATION_NAME_PARTS (LOCATION_NAME_PART_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_97E7 ON LANGUAGES (LANGUAGE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_8 ON LOCATION_NAME_MAPS (LOCATION_NAME_MAP_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_FB ON PARENTS (PARENT_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_5EBC673 ON LOCATIONS (LOCATION_PID ASC);",
 "CREATE INDEX CONSTRAINT_7A9_INDEX_D7 ON EVENTS (FROM_DATE_PID ASC);",
 "CREATE INDEX CONSTRAINT_INDEX_3 ON LOCATION_NAME_STYLES (FROM_DATE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_B9 ON PERSON_NAME_MAPS (NAME_MAP_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_AE ON PERSON_NAMES (NAME_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_6CEEF6 ON LOCATION_NAME_PARTS (LOCATION_NAME_PART_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_D1CE ON EVENT_TYPES (EVENT_TYPE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_25 ON PERSONS (PERSON_PID ASC);",
 "CREATE INDEX PERSONS_USERIDS_FK_INDEX_7 ON USERIDS (PERSON_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_B96 ON PERSON_NAME_MAPS (NAME_MAP_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_FB819 ON PARENTS (PARENT_PID ASC);",
 "CREATE INDEX CONSTRAINT_INDEX_BC9 ON LOCATION_NAMES (TO_DATE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_4B ON SEXES (SEXES_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_D1C ON EVENT_TYPES (EVENT_TYPE_PID ASC);",
 "CREATE UNIQUE INDEX CONSTRAINT_7A9_INDEX_D ON LOCATIONS (LOCATION_PID ASC);",
 "CREATE INDEX CONSTRAINT_INDEX_2 ON PERSONS (BIRTH_DATE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_378 ON DICTIONARY (DICTIONARY_PID ASC);",
 "CREATE INDEX CONSTRAINT_INDEX_D ON EVENT_NAMES (EVENT_TYPE_PID ASC);",
 "CREATE INDEX PERSONS_PARENTS_FK_INDEX_F ON PARENTS (CHILD ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_7EB9 ON HDATES (HDATE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_79 ON USERIDS (USERID_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_C8BB1 ON LOCATION_EVENTS (LOCATION_EVENTS_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_8FE54 ON LOCATION_NAME_MAPS (LOCATION_NAME_MAP_PID ASC);",
 "CREATE INDEX CONSTRAINT_C_INDEX_9 ON LOCATION_EVENTS (EVENT_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_30 ON PERSON_NAME_STYLES (NAME_STYLE_PID ASC);",
 "CREATE INDEX CONSTRAINT_INDEX_37 ON LOCATION_NAME_STYLES (TO_DATE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_B9634 ON PERSON_NAME_MAPS (NAME_MAP_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_732 ON PERSON_NAME_PARTS (NAME_PART_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_37 ON LOCATION_NAME_STYLES (LOCATION_NAME_STYLE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_FB8198E ON PARENTS (PARENT_PID ASC);",
 "CREATE INDEX NAMES_NAME_PARTS_FK_INDEX_3 ON PERSON_NAME_PARTS (NAME_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_F1 ON PERSON_NAME_MAPS (NAME_MAP_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_97 ON LANGUAGES (LANGUAGE_PID ASC);",
 "CREATE INDEX CONSTRAINT_INDEX_25 ON PERSONS (DEATH_DATE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_73 ON PERSON_NAME_PARTS (NAME_PART_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_A5 ON SEX_TYPES (SEX_TYPE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_D16F ON EVENT_NAMES (EVENT_NAME_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_7A ON EVENTS (EVENT_PID ASC);",
 "CREATE INDEX CONSTRAINT_INDEX_F ON PARENTS (LANGUAGE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_25B5 ON PERSONS (PERSON_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_371 ON LOCATION_NAME_STYLES (LOCATION_NAME_STYLE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_DD ON COMMIT_LOGS (COMMIT_LOG_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_E6 ON PERSONS (PERSON_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_5EB ON LOCATIONS (LOCATION_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_76 ON DICTIONARY (DICTIONARY_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_E7 ON SEX_TYPES (SEX_TYPE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_4 ON PERSON_EVENTS (PERSON_EVENT_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_C8BB ON LOCATION_EVENTS (LOCATION_EVENTS_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_81 ON LOCATION_NAME_STYLES (LOCATION_NAME_STYLE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_E7EB3 ON SEX_TYPES (SEX_TYPE_PID ASC);",
 "CREATE INDEX USERIDS_COMMIT_LOGS_FK_INDEX_D ON COMMIT_LOGS (USERID_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_F8F ON PERSON_NAME_STYLES (NAME_STYLE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_8D ON EVENT_NAMES (EVENT_NAME_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_4A949 ON PERSON_EVENTS (PERSON_EVENT_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_4B24F7 ON SEXES (SEXES_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_F ON PERSON_NAME_MAPS (NAME_MAP_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_7A9A ON EVENTS (EVENT_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_7584 ON PARTNERS (PARTNER_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_E ON SEX_TYPES (SEX_TYPE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_732FF ON PERSON_NAME_PARTS (NAME_PART_PID ASC);",
 "CREATE INDEX CONSTRAINT_INDEX_46 ON PERSON_NAMES (TO_DATE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_1C ON PERSON_NAMES (NAME_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_469 ON PERSON_NAMES (NAME_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_DC3 ON COMMIT_LOGS (COMMIT_LOG_PID ASC);",
 "CREATE INDEX CONSTRAINT_758_INDEX_1 ON PARTNERS (TO_DATE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_25B ON PERSONS (PERSON_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_C8B ON LOCATION_EVENTS (LOCATION_EVENTS_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_4A ON PERSON_EVENTS (PERSON_EVENT_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_F8FB ON PERSON_NAME_STYLES (NAME_STYLE_PID ASC);",
 "CREATE UNIQUE INDEX PRIMARY_KEY_758 ON PARTNERS (PARTNER_PID ASC);",
 "CREATE INDEX LOCATIONS_LOCATION_NAMES_FK_INDEX_4 ON LOCATION_NAMES (LOCATION_PID ASC);",
 "CREATE INDEX CONSTRAINT_INDEX_7 ON PARTNERS (LANGUAGE_PID ASC);",
 "ALTER TABLE LOCATION_NAME_STYLES ADD CONSTRAINT PRIMARY_KEY_371D UNIQUE (LOCATION_NAME_STYLE_PID);",
 "ALTER TABLE PERSON_NAME_MAPS ADD CONSTRAINT CONSTRAINT_F PRIMARY KEY (NAME_MAP_PID);",
 "ALTER TABLE LOCATION_NAME_MAPS ADD CONSTRAINT LOCATION_NAME_MAPS_PK PRIMARY KEY (LOCATION_NAME_MAP_PID);",
 "ALTER TABLE LANGUAGES ADD CONSTRAINT LANGUAGES_DICTIONARY_FK_INDEX_9_INDEX_9 UNIQUE (ISOCODE);",
 "ALTER TABLE LOCATIONS ADD CONSTRAINT PRIMARY_KEY_5EBC6 UNIQUE (LOCATION_PID);",
 "ALTER TABLE EVENT_NAMES ADD CONSTRAINT EVENT_NAMES_PK PRIMARY KEY (EVENT_NAME_PID);",
 "ALTER TABLE LOCATION_NAME_STYLES ADD CONSTRAINT LOCATION_NAME_STYLES_PK PRIMARY KEY (LOCATION_NAME_STYLE_PID);",
 "ALTER TABLE PERSON_EVENTS ADD CONSTRAINT CONSTRAINT_4A PRIMARY KEY (PERSON_EVENT_PID);",
 "ALTER TABLE LOCATION_NAMES ADD CONSTRAINT LOCATION_NAMES_PK PRIMARY KEY (LOCATION_NAME_PID);",
 "ALTER TABLE PARTNERS ADD CONSTRAINT CONSTRAINT_7 PRIMARY KEY (PARTNER_PID);",
 "ALTER TABLE PERSON_NAME_PARTS ADD CONSTRAINT CONSTRAINT_3B PRIMARY KEY (NAME_PART_PID);",
 "ALTER TABLE DICTIONARY ADD CONSTRAINT DICTIONARY_PK PRIMARY KEY (DICTIONARY_PID);",
 "ALTER TABLE SEX_TYPES ADD CONSTRAINT SEX_TYPES_PK PRIMARY KEY (SEX_TYPE_PID);",
 "ALTER TABLE LOCATION_EVENTS ADD CONSTRAINT LOCATION_EVENTS_PK PRIMARY KEY (LOCATION_EVENTS_PID);",
 "ALTER TABLE PERSONS ADD CONSTRAINT PRIMARY_KEY_2 UNIQUE (PERSON_PID);",
 "ALTER TABLE EVENT_TYPES ADD CONSTRAINT EVENT_TYPES_PK PRIMARY KEY (EVENT_TYPE_PID);",
 "ALTER TABLE PARENTS ADD CONSTRAINT CONSTRAINT_FB PRIMARY KEY (PARENT_PID);",
 "ALTER TABLE PERSON_NAMES ADD CONSTRAINT CONSTRAINT_4 PRIMARY KEY (NAME_PID);",
 "ALTER TABLE HDATES ADD CONSTRAINT PRIMARY_KEY_7EB9F UNIQUE (HDATE_PID);",
 "ALTER TABLE EVENTS ADD CONSTRAINT PRIMARY_KEY_DEE UNIQUE (EVENT_PID);",
 "ALTER TABLE PERSON_NAME_STYLES ADD CONSTRAINT CONSTRAINT_3 PRIMARY KEY (NAME_STYLE_PID);",
 "ALTER TABLE LOCATIONS ADD CONSTRAINT LOCATIONS_PK PRIMARY KEY (LOCATION_PID);",
 "ALTER TABLE COMMIT_LOGS ADD CONSTRAINT COMMIT_LOGS_PK PRIMARY KEY (COMMIT_LOG_PID);",
 "ALTER TABLE USERIDS ADD CONSTRAINT PRIMARY_KEY_22 UNIQUE (USERID_PID);",
 "ALTER TABLE EVENTS ADD CONSTRAINT CONSTRAINT_7A PRIMARY KEY (EVENT_PID);",
 "ALTER TABLE PERSON_NAME_STYLES ADD CONSTRAINT PRIMARY_KEY_F8 UNIQUE (NAME_STYLE_PID);",
 "ALTER TABLE LANGUAGES ADD CONSTRAINT LANGUAGES_PK PRIMARY KEY (LANGUAGE_PID);",
 "ALTER TABLE LOCATION_NAMES ADD CONSTRAINT PRIMARY_KEY_BC UNIQUE (LOCATION_NAME_PID);",
 "ALTER TABLE PERSONS ADD CONSTRAINT CONSTRAINT_2 PRIMARY KEY (PERSON_PID);",
 "ALTER TABLE USERIDS ADD CONSTRAINT USERIDS_PK PRIMARY KEY (USERID_PID);",
 "ALTER TABLE SEXES ADD CONSTRAINT SEXES_PK PRIMARY KEY (SEXES_PID);",
 "ALTER TABLE HDATES ADD CONSTRAINT HDATES_PK PRIMARY KEY (HDATE_PID);",
 "ALTER TABLE LOCATION_NAME_PARTS ADD CONSTRAINT LOCATION_NAME_PARTS_PK PRIMARY KEY (LOCATION_NAME_PART_PID);",
 "ALTER TABLE SEXES ADD CONSTRAINT PERSONS_SEXES_FK FOREIGN KEY (PERSON_PID) REFERENCES PERSONS (PERSON_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE DICTIONARY ADD CONSTRAINT LANGUAGES_DICTIONARY_FK FOREIGN KEY (ISO_CODE) REFERENCES LANGUAGES (ISOCODE) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE PARENTS ADD CONSTRAINT PERSONS_PARENTS_FK FOREIGN KEY (CHILD) REFERENCES PERSONS (PERSON_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE PERSON_NAME_MAPS ADD CONSTRAINT PERSON_NAME_STYLES_NAME_MAPS_FK FOREIGN KEY (NAME_STYLE_PID) REFERENCES PERSON_NAME_STYLES (NAME_STYLE_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE EVENTS ADD CONSTRAINT CONSTRAINT_7A9A FOREIGN KEY (TO_DATE_PID) REFERENCES HDATES (HDATE_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE EVENTS ADD CONSTRAINT CONSTRAINT_7A9 FOREIGN KEY (FROM_DATE_PID) REFERENCES HDATES (HDATE_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE PERSON_NAMES ADD CONSTRAINT CONSTRAINT_469 FOREIGN KEY (TO_DATE_PID) REFERENCES HDATES (HDATE_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE LOCATION_NAME_STYLES ADD CONSTRAINT CONSTRAINT_37 FOREIGN KEY (FROM_DATE_PID) REFERENCES HDATES (HDATE_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE LOCATION_NAMES ADD CONSTRAINT LOCATION_NAME_STYLES_LOCATION_NAMES_FK FOREIGN KEY (LOCATION_NAME_STYLE_PID) REFERENCES LOCATION_NAME_STYLES (LOCATION_NAME_STYLE_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE LOCATION_EVENTS ADD CONSTRAINT CONSTRAINT_C8 FOREIGN KEY (LOCATION_PID) REFERENCES LOCATIONS (LOCATION_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE PARTNERS ADD CONSTRAINT PERSONS_PARTNERS_FK FOREIGN KEY (PARTNER1) REFERENCES PERSONS (PERSON_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE LOCATION_NAMES ADD CONSTRAINT CONSTRAINT_B FOREIGN KEY (FROM_DATE_PID) REFERENCES HDATES (HDATE_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE PARENTS ADD CONSTRAINT PERSONS_PARENTS_FK1 FOREIGN KEY (PARENT) REFERENCES PERSONS (PERSON_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE PARTNERS ADD CONSTRAINT PERSONS_PARTNERS_FK1 FOREIGN KEY (PARTNER2) REFERENCES PERSONS (PERSON_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE PERSON_NAMES ADD CONSTRAINT PERSONS_PERSON_NAMES_FK FOREIGN KEY (PERSON_PID) REFERENCES PERSONS (PERSON_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE LOCATIONS ADD CONSTRAINT CONSTRAINT_5E FOREIGN KEY (TO_DATE_PID) REFERENCES HDATES (HDATE_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE LOCATION_NAMES ADD CONSTRAINT CONSTRAINT_BC FOREIGN KEY (TO_DATE_PID) REFERENCES HDATES (HDATE_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE LOCATION_EVENTS ADD CONSTRAINT CONSTRAINT_C FOREIGN KEY (EVENT_PID) REFERENCES EVENTS (EVENT_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE COMMIT_LOGS ADD CONSTRAINT USERIDS_COMMIT_LOGS_FK FOREIGN KEY (USERID_PID) REFERENCES USERIDS (USERID_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE LOCATION_NAMES ADD CONSTRAINT LOCATIONS_LOCATION_NAMES_FK FOREIGN KEY (LOCATION_PID) REFERENCES LOCATIONS (LOCATION_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE PERSON_NAMES ADD CONSTRAINT CONSTRAINT_46 FOREIGN KEY (FROM_DATE_PID) REFERENCES HDATES (HDATE_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE PERSON_EVENTS ADD CONSTRAINT CONSTRAINT_4A9 FOREIGN KEY (EVENT_PID) REFERENCES EVENTS (EVENT_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE USERIDS ADD CONSTRAINT PERSONS_USERIDS_FK FOREIGN KEY (PERSON_PID) REFERENCES PERSONS (PERSON_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE PARTNERS ADD CONSTRAINT CONSTRAINT_758 FOREIGN KEY (TO_DATE_PID) REFERENCES HDATES (HDATE_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE PARTNERS ADD CONSTRAINT CONSTRAINT_75 FOREIGN KEY (FROM_DATE_PID) REFERENCES HDATES (HDATE_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE PERSONS ADD CONSTRAINT CONSTRAINT_25 FOREIGN KEY (BIRTH_DATE_PID) REFERENCES HDATES (HDATE_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE PERSON_EVENTS ADD CONSTRAINT CONSTRAINT_4A94 FOREIGN KEY (PERSON_PID) REFERENCES PERSONS (PERSON_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE LOCATION_NAME_MAPS ADD CONSTRAINT LOCATION_NAME_STYLES_LOCATION_NAME_MAPS_FK FOREIGN KEY (LOCATION_NAME_STYLE_PID) REFERENCES LOCATION_NAME_STYLES (LOCATION_NAME_STYLE_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE LOCATION_NAME_STYLES ADD CONSTRAINT CONSTRAINT_371 FOREIGN KEY (TO_DATE_PID) REFERENCES HDATES (HDATE_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE PERSONS ADD CONSTRAINT CONSTRAINT_25B FOREIGN KEY (DEATH_DATE_PID) REFERENCES HDATES (HDATE_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE PERSON_NAMES ADD CONSTRAINT PERSON_NAME_STYLES_PERSON_NAMES_FK FOREIGN KEY (NAME_STYLE_PID) REFERENCES PERSON_NAME_STYLES (NAME_STYLE_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE LOCATIONS ADD CONSTRAINT CONSTRAINT_5 FOREIGN KEY (FROM_DATE_PID) REFERENCES HDATES (HDATE_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;",
 "ALTER TABLE LOCATION_NAME_PARTS ADD CONSTRAINT CONSTRAINT_6 FOREIGN KEY (LOCATION_NAME_PID) REFERENCES LOCATION_NAMES (LOCATION_NAME_PID) ON DELETE RESTRICT ON UPDATE RESTRICT;"
 
