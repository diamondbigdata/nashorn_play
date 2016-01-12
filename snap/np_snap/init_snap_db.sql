-- init "SNAP" project database

create sequence coll_seq ;

create table collections (
    collection varchar( 32 ) not null,
    id numeric not null,
    doc json not null,
    primary key ( collection, id )
) ;

grant all on table collections to app ;

insert into collections
        ( collection, id, doc )
    values
        ( 'test', nextval( 'coll_seq' ), '{ "foo": 3, "bar": "cat" }' ) ;
insert into collections
        ( collection, id, doc )
    values
        ( 'test', nextval( 'coll_seq' ), '{ "foo": 5, "bar": "dog" }' ) ;


-- make some denormalized display fodder

insert into collections
        ( collection, id,
            doc )
    values
        ( 'invoice', nextval( 'coll_seq' ),
            '{ "id": "INV002", "total": 123.45, "items": [] }' ) ;

insert into collections
        ( collection, id,
            doc )
    values
        ( 'invoice', nextval( 'coll_seq' ),
            '{ "id": "INV005", "total": 1000000.00, "items": [] }' ) ;

insert into collections
        ( collection, id,
            doc )
    values
        ( 'invoice', nextval( 'coll_seq' ),
            '{ "id": "INV007", "total": 4132.99, "items": [] }' ) ;

insert into collections
        ( collection, id,
            doc )
    values
        ( 'invoice', nextval( 'coll_seq' ),
            '{ "id": "INV012", "total": 19999.99, "items": [] }' ) ;


-- vi: ts=4 sw=4 expandtab
-- *** EOF ***
