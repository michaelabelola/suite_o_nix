CREATE TABLE IF NOT EXISTS countries
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    iso3            CHAR(3),
    numeric_code    CHAR(3),
    iso2            CHAR(2),
    phonecode       VARCHAR(255),
    capital         VARCHAR(255),
    currencyCode        VARCHAR(255),
    currency_name   VARCHAR(255),
    currency_symbol VARCHAR(255),
    tld             VARCHAR(255),
    native          VARCHAR(255),
    region          VARCHAR(255),
    subregion       VARCHAR(255),
    nationality     VARCHAR(255),
    timezones       TEXT,
    translations    TEXT,
    latitude        DECIMAL(10, 8),
    longitude       DECIMAL(11, 8),
    emoji           VARCHAR(191),
    emojiU          VARCHAR(191),
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    flag            BOOLEAN      NOT NULL DEFAULT TRUE,
    wikidata_id     VARCHAR(255) -- Rapid API GeoDB Cities
);

CREATE TABLE IF NOT EXISTS states
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    country_id   INTEGER      NOT NULL,
    country_code CHAR(2)      NOT NULL,
    fips_code    VARCHAR(255),
    iso2         VARCHAR(255),
    type         VARCHAR(191),
    latitude     DECIMAL(10, 8),
    longitude    DECIMAL(11, 8),
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    flag         BOOLEAN      NOT NULL DEFAULT TRUE,
    wikidata_id  VARCHAR(255) -- Rapid API GeoDB Cities
);

CREATE TABLE IF NOT EXISTS cities
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    state_id     INTEGER      NOT NULL,
    state_code   VARCHAR(255) NOT NULL,
    country_id   INTEGER      NOT NULL,
    country_code char(2)      NOT NULL,
    latitude     decimal(10, 8),
    longitude    decimal(11, 8),
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    flag         BOOLEAN      NOT NULL DEFAULT TRUE,
    wikidata_id  VARCHAR(255)
);

CREATE INDEX IF NOT EXISTS idx_country_continent ON countries (region);
CREATE INDEX IF NOT EXISTS idx_country_subregion ON countries (subregion);

CREATE INDEX IF NOT EXISTS idx_country_region ON states (country_id);
-- ALTER TABLE states
--     ADD CONSTRAINT fk_country_region FOREIGN KEY (country_id) REFERENCES countries (id);
