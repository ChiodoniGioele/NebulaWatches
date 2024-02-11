USE NebulaWatches;

DROP TABLE IF EXISTS brand; 
CREATE TABLE brand (
    name VARCHAR(150) PRIMARY KEY
);

DROP TABLE IF EXISTS family; 
CREATE TABLE family (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL,
    brand_name VARCHAR(150),
    FOREIGN KEY (brand_name)
        REFERENCES brand (name)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);

DROP TABLE IF EXISTS material; 
CREATE TABLE material (
    name VARCHAR(150) PRIMARY KEY
);

DROP TABLE IF EXISTS watch_shape; 
CREATE TABLE watch_shape (
    name VARCHAR(150) PRIMARY KEY
);

DROP TABLE IF EXISTS dial_color; 
CREATE TABLE dial_color (
    name VARCHAR(150) PRIMARY KEY
);

DROP TABLE IF EXISTS dial_finish; 
CREATE TABLE dial_finish (
    name VARCHAR(150) PRIMARY KEY
);

DROP TABLE IF EXISTS watch_indexes; 
CREATE TABLE watch_indexes (
    name VARCHAR(150) PRIMARY KEY
);
DROP TABLE IF EXISTS hands; 
CREATE TABLE hands (
    name VARCHAR(150) PRIMARY KEY
);

DROP TABLE IF EXISTS movement; 
CREATE TABLE movement (
    id INTEGER PRIMARY KEY AUTO_INCREMENT
);

DROP TABLE IF EXISTS watch; 
CREATE TABLE watch (
    reference VARCHAR(150) PRIMARY KEY,
    retail_price REAL,
    family_id INTEGER,
    name VARCHAR(150) NOT NULL,
    movement_id INTEGER,
    movement_name VARCHAR(150),
    production_time VARCHAR(150),
    is_limited_to VARCHAR(150),
    case_material_name VARCHAR(150),
    bezel_material_name VARCHAR(150),
    glass_material_name VARCHAR(150),
    is_back_open TINYINT(1),
    watch_shape_name VARCHAR(150),
    diameter_in_mm REAL,
    height_in_mm REAL,
    water_resistance_m REAL,
    dial_color_name VARCHAR(150),
    dial_finish_name VARCHAR(150),
    indexes_name VARCHAR(150),
    hands_name VARCHAR(150),
    description VARCHAR(1500),

    nickname VARCHAR(150),
    lug_width REAL,
    coating_material_name VARCHAR(150),

    image LONGBLOB,

    FOREIGN KEY (family_id)
        REFERENCES family (id)
        ON UPDATE CASCADE
        ON DELETE SET NULL,

    FOREIGN KEY (case_material_name)
        REFERENCES material (name)
        ON UPDATE CASCADE
        ON DELETE SET NULL,

    FOREIGN KEY (bezel_material_name)
        REFERENCES material (name)
        ON UPDATE CASCADE
        ON DELETE SET NULL,

    FOREIGN KEY (glass_material_name)
        REFERENCES material (name)
        ON UPDATE CASCADE
        ON DELETE SET NULL,
    
    FOREIGN KEY (coating_material_name)
        REFERENCES material (name)
        ON UPDATE CASCADE
        ON DELETE SET NULL,

    FOREIGN KEY (watch_shape_name)
        REFERENCES watch_shape (name)
        ON UPDATE CASCADE
        ON DELETE SET NULL,

    FOREIGN KEY (dial_color_name)
        REFERENCES dial_color (name)
        ON UPDATE CASCADE
        ON DELETE SET NULL,

    FOREIGN KEY (dial_finish_name)
        REFERENCES dial_finish (name)
        ON UPDATE CASCADE
        ON DELETE SET NULL,

    FOREIGN KEY (indexes_name)
        REFERENCES watch_indexes (name)
        ON UPDATE CASCADE
        ON DELETE SET NULL,

    FOREIGN KEY (hands_name)
        REFERENCES hands (name)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);

DROP TABLE IF EXISTS watchUsesMaterials; 
CREATE TABLE watchUsesMaterials(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    watch_reference VARCHAR(150),
    material_name VARCHAR(150),

    FOREIGN KEY (watch_reference)
        REFERENCES watch (reference)
        ON UPDATE CASCADE
        ON DELETE SET NULL,
    
    FOREIGN KEY (material_name)
        REFERENCES material (name)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);





