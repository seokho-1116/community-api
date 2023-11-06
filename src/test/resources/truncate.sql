DO ' DECLARE
    r RECORD;
BEGIN
    SET session_replication_role = ''replica'';
    FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = current_schema() AND tablename NOT IN (''databasechangelog'', ''databasechangelock'')) LOOP
        EXECUTE ''TRUNCATE TABLE '' || quote_ident(r.tablename) || '' CASCADE'';
        END LOOP;
END ';

SET session_replication_role = 'origin';
