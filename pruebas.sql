



CREATE TABLE civilizacion (
    id NUMBER(10) PRIMARY KEY NOT NULL,
    nombre VARCHAR2(50) NOT NULL,
    epoca NUMBER(4) NOT NULL
);

-- Crear o reemplazar la tabla inventario
CREATE TABLE inventario (
    id_civilizacion  NUMBER(10)   PRIMARY KEY NOT NULL,
    madera NUMBER(10),
    hierro NUMBER(10),
    mana NUMBER(10),
    comida NUMBER(10),
     CONSTRAINT fk_inventario FOREIGN KEY (id_civilizacion)
    REFERENCES civilizacion(id)
    ON DELETE CASCADE
);

-- Crear o reemplazar la tabla edificios
CREATE TABLE edificios (
    id_civilizacion NUMBER(10)  PRIMARY KEY NOT NULL,
  
    herreria NUMBER(10),
    granja NUMBER(10),
    carpinteria NUMBER(10),
    torre_magica NUMBER(10),
    iglesia NUMBER(10),
     CONSTRAINT fk_edificios FOREIGN KEY (id_civilizacion)
    REFERENCES civilizacion(id)
    ON DELETE CASCADE
);

-- Crear o reemplazar la tabla UnidadesDeAtaque
CREATE TABLE UnidadesDeAtaque (
    id_civilizacion NUMBER(10)  NOT NULL,
    unit_id NUMBER(10) NOT NULL,
    Type VARCHAR2(20) CHECK (Type IN ('Swordman', 'Spearman', 'Crossbow', 'Cannon')),
    armor NUMBER(10),
    base_damage NUMBER(10),
    experience NUMBER(10),
    sanctified NUMBER(1) CHECK (sanctified IN (0, 1)),
    CONSTRAINT pk3 PRIMARY KEY (id_civilizacion, unit_id),
     CONSTRAINT fk_ataque FOREIGN KEY (id_civilizacion)
    REFERENCES civilizacion(id)
    ON DELETE CASCADE
);

-- Crear o reemplazar la tabla UnidadesDeDefensa
CREATE TABLE UnidadesDeDefensa (
    id_civilizacion NUMBER(10) NOT NULL,
    unit_id NUMBER(10) NOT NULL,
    Type VARCHAR2(20) CHECK (Type IN ('ArrowTower', 'Catapult', 'RocketLauncherTower')),
    armor NUMBER(10),
    base_damage NUMBER(10),
    experience NUMBER(10),
    sanctified NUMBER(1) CHECK (sanctified IN (0, 1)),
    CONSTRAINT pk2 PRIMARY KEY (id_civilizacion, unit_id),
     CONSTRAINT fk_defensa FOREIGN KEY (id_civilizacion)
    REFERENCES civilizacion(id)
    ON DELETE CASCADE
);

-- Crear o reemplazar la tabla UnidadesEspeciales
CREATE TABLE UnidadesEspeciales (
    id_civilizacion NUMBER(10) NOT NULL,
    unit_id NUMBER(10) NOT NULL,
    Type VARCHAR2(20) CHECK (Type IN ('Magician', 'Priest')),
    armor NUMBER(10),
    base_damage NUMBER(10),
    experience NUMBER(10),
    sanctified NUMBER(1) CHECK (sanctified IN (0, 1)),
    CONSTRAINT pk1 PRIMARY KEY (id_civilizacion, unit_id),
    CONSTRAINT fk_especiales FOREIGN KEY (id_civilizacion)
    REFERENCES civilizacion(id)
    ON DELETE CASCADE
   
);
