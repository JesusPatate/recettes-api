CREATE TABLE recipe (
    id UUID PRIMARY KEY,
    title TEXT NOT NULL,
    hot BOOLEAN NOT NULL,
    dessert BOOLEAN NOT NULL,
    preparation_time SMALLINT NOT NULL,
    cooking_time SMALLINT,
    servings SMALLINT NOT NULL,
    source TEXT
);

CREATE TABLE unit (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);


CREATE TABLE ingredient (
    recipe_id UUID NOT NULL REFERENCES recipe (id),
    name TEXT NOT NULL,
    unit_id INTEGER REFERENCES unit (id),
    amount SMALLINT
);

CREATE TABLE comment (
    recipe_id UUID REFERENCES recipe (id),
    contents TEXT NOT NULL
);
