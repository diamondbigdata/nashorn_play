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


-- vi: ts=4 sw=4 expandtab
-- *** EOF ***
