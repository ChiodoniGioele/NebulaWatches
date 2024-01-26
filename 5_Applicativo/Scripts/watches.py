import sqlite3
import time
from sqlite3 import Error
import mysql.connector

class Watch:
    def __init__(self, watch_array=None, retail_price=None, brand=None, reference=None, name=None, values=None, description=None, image_data=None):
        if watch_array is not None:
            self.retail_price = watch_array[1]
            self.brand = watch_array[2]
            self.reference = watch_array[3]
            self.name = watch_array[4]
            self.values = watch_array[5].split('\n')
            self.description = watch_array[6]
            self.image_data = watch_array[7]
        else:
            self.retail_price = retail_price
            self.brand = brand
            self.reference = reference
            self.name = name
            self.values = values.split('\n')
            self.description = description
            self.image_data = image_data
    
    # family
    # movement
    # produced
    # limited
    # material
    # bezel
    # glass
    # back
    # shape
    # diameter
    # height
    # lug width
    # color
    # indexes
    # hands
    # finish
    # nickname
    # w/r
    # coating
    # materials
    # special
    def split_values_in_dict(self):
        self.values.pop(len(self.values) - 1)  # removes the last line because it's blank
        values_dict = {}
        for line_value in self.values:
            header, value = line_value.split('¦')
            values_dict[header] = value
        return values_dict
    
class WatchDatabase:
    def __init__(self):
        self.cursor_obj = None
        self.conn = None
        self.create_db_connection()

    def create_db_connection(self):
        try:
            self.conn = mysql.connector.connect(
                host="localhost",
                user="root",
                password="Admin$00",
                database="NebulaWatches"
            )
            self.cursor_obj = self.conn.cursor()
        except Error as e:
            print(e)

    def close_db_connection(self):
        self.conn.commit()
        self.conn.close()

    def handle_sql_exceptions(func):
        def wrapper_func(self, *args, **kwargs):
            is_handling = True
            tries = 5
            while is_handling:
                try:
                    func(self, *args, **kwargs)
                    is_handling = False
                except Error as e:
                    print(f'[!] ERROR: {e}')
                    if tries > 0:
                        tries -= 1
                        time.sleep(2)
                        print('[!] Retrying ...')
                    else:
                        print('[!] Maximum retries reached. Exiting.')
                        is_handling = False

        return wrapper_func

    #@handle_sql_exceptions
    def brand_exists(self, brand_name):
        self.cursor_obj.execute('SELECT * FROM brand WHERE name LIKE %s', (brand_name,))
        rows = self.cursor_obj.fetchall()
        return rows[0][0] if len(rows) > 0 else None

    #@handle_sql_exceptions
    def add_brand(self, brand_name):
        existing_brand = self.brand_exists(brand_name)
        if existing_brand is None:
            self.cursor_obj.execute('INSERT INTO brand(name) VALUES(%s)', (brand_name,))
            self.conn.commit()

            brand_id = self.cursor_obj.lastrowid
            return brand_id
        else:
            return existing_brand

    
    #@handle_sql_exceptions
    def family_exists(self, family_name, brand_name):
        self.cursor_obj.execute('SELECT * FROM family WHERE name LIKE %s AND brand_name LIKE %s', 
                                (family_name, brand_name,))
        rows = self.cursor_obj.fetchall()
        return rows[0][0] if len(rows) > 0 else None

    #@handle_sql_exceptions
    def add_family(self, family_name, brand_name):
        existing_family = self.family_exists(family_name, brand_name)
        if existing_family is None:
            self.cursor_obj.execute('INSERT INTO family(name, brand_name) VALUES(%s, %s)', 
                              (family_name, brand_name,))
            self.conn.commit()
        
            family_id = self.cursor_obj.lastrowid
            return family_id
        else:
            return existing_family

    #@handle_sql_exceptions
    def material_exists(self, material_name):
        self.cursor_obj.execute('SELECT * FROM material WHERE name LIKE %s', (material_name,))
        rows = self.cursor_obj.fetchall()
        return rows[0][0] if len(rows) > 0 else None # rows[0][0] --> material id

    #@handle_sql_exceptions
    def add_material(self, material_name):
        existing_material = self.material_exists(material_name)
        if existing_material is None:
            self.cursor_obj.execute('INSERT INTO material(name) VALUES(%s)', (material_name,))
            self.conn.commit()
            material_id = self.cursor_obj.lastrowid
            return material_id
        else:
            return existing_material
        
    #@handle_sql_exceptions
    def watch_shape_exists(self, watch_shape_name):
        self.cursor_obj.execute('SELECT * FROM watch_shape WHERE name LIKE %s', (watch_shape_name,))
        rows = self.cursor_obj.fetchall()
        return rows[0][0] if len(rows) > 0 else None

    #@handle_sql_exceptions
    def add_watch_shape(self, watch_shape_name):
        existing_watch_shape = self.watch_shape_exists(watch_shape_name)
        if existing_watch_shape is None:
            self.cursor_obj.execute('INSERT INTO watch_shape(name) VALUES(%s)', (watch_shape_name,))
            self.conn.commit()

            watch_shape_id = self.cursor_obj.lastrowid
            return watch_shape_id
        else:
            return existing_watch_shape
    
    #@handle_sql_exceptions
    def dial_color_exists(self, dial_color_name):
        self.cursor_obj.execute('SELECT * FROM dial_color WHERE name LIKE %s', (dial_color_name,))
        rows = self.cursor_obj.fetchall()
        return rows[0][0] if len(rows) > 0 else None

    #@handle_sql_exceptions
    def add_dial_color(self, dial_color_name):
        existing_dial_color = self.dial_color_exists(dial_color_name)
        if existing_dial_color is None:
            self.cursor_obj.execute('INSERT INTO dial_color(name) VALUES(%s)', (dial_color_name,))
            self.conn.commit()

            dial_color_id = self.cursor_obj.lastrowid
            return dial_color_id
        else:
            return existing_dial_color

    #@handle_sql_exceptions
    def dial_finish_exists(self, dial_finish_name):
        self.cursor_obj.execute('SELECT * FROM dial_finish WHERE name LIKE %s', (dial_finish_name,))
        rows = self.cursor_obj.fetchall()
        # print(f'CONTROLLO SE DIAL FINISH ESISTEEE {rows[0][0] if len(rows) > 0 else None}')
        return rows[0][0] if len(rows) > 0 else None

    #@handle_sql_exceptions
    def add_dial_finish(self, dial_finish_name):
        existing_dial_finish = self.dial_finish_exists(dial_finish_name)
        if existing_dial_finish is None:
            self.cursor_obj.execute('INSERT INTO dial_finish(name) VALUES(%s)', (dial_finish_name,))
            self.conn.commit()

            dial_finish_id = self.cursor_obj.lastrowid
            return dial_finish_id
        else:
            return existing_dial_finish

    #@handle_sql_exceptions
    def indexes_exists(self, indexes_name):
        self.cursor_obj.execute('SELECT * FROM watch_indexes WHERE name LIKE %s', (indexes_name,))
        rows = self.cursor_obj.fetchall()
        return rows[0][0] if len(rows) > 0 else None

    #@handle_sql_exceptions
    def add_indexes(self, indexes_name):
        existing_index = self.indexes_exists(indexes_name)
        if existing_index is None:
            self.cursor_obj.execute('INSERT INTO watch_indexes(name) VALUES(%s)', (indexes_name,))
            self.conn.commit()

            index_id = self.cursor_obj.lastrowid
            return index_id
        else:
            return existing_index

    #@handle_sql_exceptions
    def hands_exists(self, hands_name):
        self.cursor_obj.execute('SELECT * FROM hands WHERE name LIKE %s', (hands_name,))
        rows = self.cursor_obj.fetchall()
        return rows[0][0] if len(rows) > 0 else None

    #@handle_sql_exceptions
    def add_hands(self, hands_name):
        existing_hands = self.hands_exists(hands_name)
        if existing_hands is None:
            self.cursor_obj.execute('INSERT INTO hands(name) VALUES(%s)', (hands_name,))
            self.conn.commit()

            hands_id = self.cursor_obj.lastrowid
            return hands_id
        else:
            return existing_hands
    
    #@handle_sql_exceptions
    def movement_exists(self, movement_reference):
        pass

    #@handle_sql_exceptions
    def add_movement(self, movement):
        pass

    #@handle_sql_exceptions
    def watch_material_relationship_exists(self, watch_reference, material_name):
        self.cursor_obj.execute('SELECT * FROM watchUsesMaterials WHERE watch_reference LIKE %s AND material_name LIKE %s', (watch_reference, material_name,))
        rows = self.cursor_obj.fetchall()
        return rows[0][0] if len(rows) > 0 else None

    #@handle_sql_exceptions
    def add_watch_material_relationship(self, watch_reference, material_name):
        # print(f'Watch reference {watch_reference}')
        # print(f'Watch material {material_name}')

        # print(f'AGGIUNTO {self.add_material(material_name)}')

        existing_watch_material_relationship = self.watch_material_relationship_exists(watch_reference, material_name)

        if not existing_watch_material_relationship:
            self.cursor_obj.execute('INSERT INTO watchUsesMaterials(watch_reference, material_name) VALUES(%s, %s)', (watch_reference, material_name,))
            self.conn.commit()
        else:
            return existing_watch_material_relationship
    
    #@handle_sql_exceptions
    def watch_exists(self, watch_reference):
        self.cursor_obj.execute('SELECT * FROM watch WHERE reference LIKE %s', (watch_reference,))
        rows = self.cursor_obj.fetchall()
        return True if len(rows) > 0 else False
    
    def add_optional_value_string(self, value):
        try:
            return '"' + value + '", '
        except:
            print(f'ERRORE VALORE NON DI TIPO STRING ---> {value}')
    
    def add_optional_value(self, value):
        return str(value) + ', '

    # self.retail_price = watch_array[1]
    # self.brand = watch_array[2]
    # self.reference = watch_array[3]
    # self.name = watch_array[4]
    # self.values = watch_array[5].split('\n')
    # self.description = watch_array[6]
    # self.image_data = watch_array[7]
    #@handle_sql_exceptions
    def add_watch(self, watch):
        if not self.watch_exists(watch.reference):
            optional_values_cols = ''
            optional_values = ''
            values_dict = watch.split_values_in_dict()

            all_materials_used = []

            optional_values_cols += 'retail_price, '
            optional_values += self.add_optional_value(-1)

            optional_values_cols += 'reference, '
            optional_values += self.add_optional_value_string(watch.reference)

            optional_values_cols += 'name, '
            optional_values += self.add_optional_value_string(watch.name)

            optional_values_cols += 'description, '
            optional_values += self.add_optional_value_string(watch.description)

            for header, value in values_dict.items():
                # Controllo nomi header e aggiunta in db
                if header == 'family':
                    optional_values_cols += 'family_id, '
                    ## print('family name ', value)
                    brand_id = self.add_brand(watch.brand)
                    family_id = self.add_family(value, brand_id)
                    optional_values += self.add_optional_value(family_id)
                elif header == 'movement':
                    pass
                elif header == 'produced':
                    optional_values_cols += 'production_time, '
                    
                    optional_values += self.add_optional_value_string(value)
                elif header == 'limited':
                    optional_values_cols += 'is_limited_to, '
                    optional_values += self.add_optional_value_string(value)
                elif header == 'material':
                    optional_values_cols += 'case_material_name, '
                    material_id = self.add_material(value)
                    optional_values += self.add_optional_value_string(material_id)

                    all_materials_used.append(value)
                    #self.add_watch_material_relationship(watch.reference, value)
                elif header == 'bezel':
                    optional_values_cols += 'bezel_material_name, '
                    material_id = self.add_material(value)
                    optional_values += self.add_optional_value_string(material_id)

                    #self.add_watch_material_relationship(watch.reference, value)
                    all_materials_used.append(value)
                elif header == 'glass':
                    optional_values_cols += 'glass_material_name, '
                    material_id = self.add_material(value) 
                    optional_values += self.add_optional_value_string(material_id)

                    #self.add_watch_material_relationship(watch.reference, value)
                    all_materials_used.append(value)
                elif header == 'back':
                    optional_values_cols += 'is_back_open, '
                    written_value = 0 if value == 'Closed' else 1
                    optional_values += self.add_optional_value(written_value)
                elif header == 'shape':
                    optional_values_cols += 'watch_shape_name, '
                    watch_shape_id = self.add_watch_shape(value)
                    optional_values += self.add_optional_value_string(watch_shape_id)
                elif header == 'diameter':
                    optional_values_cols += 'diameter_in_mm, '
                    optional_values += self.add_optional_value(value[:-3])
                elif header == 'height':
                    optional_values_cols += 'height_in_mm, '
                    optional_values += self.add_optional_value(value[:-3])
                elif header == 'lug width':
                    optional_values_cols += 'lug_width, '
                    optional_values += self.add_optional_value(value[:-3])
                elif header == 'color':
                    optional_values_cols += 'dial_color_name, '
                    optional_values += self.add_optional_value_string(self.add_dial_color(value))
                elif header == 'indexes':
                    optional_values_cols += 'indexes_name, '
                    optional_values += self.add_optional_value_string(self.add_indexes(value))
                elif header == 'hands':
                    optional_values_cols += 'hands_name, '
                    hands_id = self.add_hands(value)
                    ## print(f'HANDS NAME {hands_id}')
                    optional_values += self.add_optional_value_string(hands_id)
                elif header == 'finish':
                    optional_values_cols += 'dial_finish_name, '
                    optional_values += self.add_optional_value_string(self.add_dial_finish(value))
                elif header == 'nickname':
                    optional_values_cols += 'nickname, '
                    optional_values += self.add_optional_value_string(value)
                elif header == 'w/r':
                    optional_values_cols += 'water_resistance_m, '
                    optional_values += self.add_optional_value(value[:-2])
                elif header == 'coating':
                    optional_values_cols += 'coating_material_name, '
                    material_id = self.add_material(value)
                    optional_values += self.add_optional_value(material_id)

                    #self.add_watch_material_relationship(watch.reference, value)
                    all_materials_used.append(value)
                elif header == 'materials':
                    materials_used = value.split(', ')
                    for mat in materials_used:
                        self.add_material(mat)
                        all_materials_used.append(value)
                        #self.add_watch_material_relationship(watch.reference,self.add_material(mat))
                # else:
                #     optional_values_cols += header + ', '
                #     optional_values += value + ', '

            #optional_values_cols += 'image '
            #optional_values += str(watch.image_data)
            
            # optional_values += "'" + "TEST" + "'"
            
            optional_values_cols = optional_values_cols[:-2]
            optional_values = optional_values[:-2]

            # print(optional_values_cols)
            # input()
            # print(optional_values)
            # input()

            query = f'INSERT INTO watch({optional_values_cols}, image) VALUES({optional_values}, %s)'
            # print(query)
            # print('QUERY DA FARE')
            self.cursor_obj.execute(query, (watch.image_data))
            # print('QUERY FATTA')
            #self.cursor_obj(query)
            self.conn.commit()
            # print('QUERY COMMIT')

            for mat in all_materials_used:
                self.add_watch_material_relationship(watch.reference,self.add_material(mat))
            
            self.conn.commit()


class OldWatchDatabase:
    def __init__(self):
        self.db_file_path = '/Users/alexandruciobanu/Developer/WatchesScraper'
        self.cursor_obj = None
        self.conn = None
        self.create_db_connection()

    def create_db_connection(self):
        try:
            self.conn = sqlite3.connect(self.db_file_path)
            self.cursor_obj = self.conn.cursor()
        except Error as e:
            print(e)

    def close_db_connection(self):
        self.conn.commit()
        self.conn.close()

    def add_db_watch(self, retail_price, brand, name, reference, values, description, image_binaries):
        query = f'''INSERT INTO watch(retail_price, brand, reference, name, full_data, description, image) 
                VALUES({retail_price}, "{brand}", "{reference}", "{name}", "{values}", "{description}", ?)'''
        
        # print(query)

        try:
            self.cursor_obj.execute(query, (image_binaries,))
        except Exception as e:
            print(query + '\n' + str(e))

        # print('[+] Added ' + name + ', ref: ' + reference)

    def watch_exists_in_db(self, reference):
        while True:
            try:
                self.cursor_obj.execute(f'SELECT * FROM watch WHERE reference LIKE "{reference}"')
                rows = self.cursor_obj.fetchall()
                return True if len(rows) > 0 else False
            except sqlite3.OperationalError:
                print('[-] ERROR WITH DATABASE')

    def get_all_watches_in_db(self):
        self.cursor_obj.execute('SELECT * FROM watch')
        rows = self.cursor_obj.fetchall()
        
        return rows