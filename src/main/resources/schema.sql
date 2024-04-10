DROP TABLE IF EXISTS event;
DROP TABLE IF EXISTS bank_account_document;
DROP TABLE IF EXISTS snapshot;
CREATE TABLE event
(
    id       UUID  DEFAULT uuid_generate_v4(),
    aggregate_id   VARCHAR(250) NOT NULL CHECK ( aggregate_id <> '' ),
    aggregate_type VARCHAR(250) NOT NULL CHECK ( aggregate_type <> '' ),
    event_type     VARCHAR(250) NOT NULL CHECK ( event_type <> '' ),
    timestamp      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    data           BYTEA,
    metadata       BYTEA,
    version        SERIAL       NOT NULL,
    UNIQUE (aggregate_id, version)
);

CREATE TABLE bank_account_document
(
    aggregate_id   VARCHAR(250) PRIMARY KEY,
    email          VARCHAR(250),
    user_name      VARCHAR(250),
    address        VARCHAR(250),
    balance        decimal
);

CREATE TABLE snapshot
(
    snapshot_id    UUID PRIMARY KEY         DEFAULT uuid_generate_v4(),
    aggregate_id   VARCHAR(250) UNIQUE NOT NULL CHECK ( aggregate_id <> '' ),
    aggregate_type VARCHAR(250)        NOT NULL CHECK ( aggregate_type <> '' ),
    data           BYTEA,
    metadata       BYTEA,
    version        SERIAL              NOT NULL,
    timestamp      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (aggregate_id)
);