 create table watch (
        reference varchar(255) not null,
        description varchar(255),
        diameter_in_mm float(23) not null,
        height_in_mm float(23) not null,
        image mediumblob,
        is_back_open bit not null,
        is_limited_to varchar(255),
        lug_width float(23) not null,
        movement_id integer not null,
        movement_name varchar(255),
        name varchar(255),
        nickname varchar(255),
        production_time varchar(255),
        retail_price float(23) not null,
        water_resistance_m float(23),
        bezel_material_name varchar(255),
        case_material_name varchar(255),
        coating_material_name varchar(255),
        dial_color_name varchar(255),
        dial_finish_name varchar(255),
        family_id bigint,
        glass_material_name varchar(255),
        hands_name varchar(255),
        watch_indexes_name varchar(255),
        watch_shape_name varchar(255),
        primary key (reference)
    ) engine=InnoDB