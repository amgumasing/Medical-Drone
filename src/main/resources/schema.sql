CREATE TABLE IF NOT EXISTS drone (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    serial_number VARCHAR(100) NOT NULL UNIQUE,
    model VARCHAR(50) NOT NULL,
    weight_limit INT NOT NULL,
    battery_capacity INT NOT NULL,
    state VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS medication (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    weight INT NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    image VARCHAR(255),
    CONSTRAINT check_name_format CHECK (name ~ '^[a-zA-Z0-9-_]+$'),
    CONSTRAINT check_code_format CHECK (code ~ '^[A-Z0-9_]+$')
);

CREATE TABLE IF NOT EXISTS drone_medication (
    medication_id BIGINT,
    drone_id BIGINT,
    PRIMARY KEY (medication_id, drone_id),
    CONSTRAINT fk_medication FOREIGN KEY (medication_id) REFERENCES medication(id),
    CONSTRAINT fk_drone FOREIGN KEY (drone_id) REFERENCES drone(id)
);
