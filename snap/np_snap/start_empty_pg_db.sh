#!/bin/sh -x

# some constants

DB_NAME=snap_demo_db
DB_USER=app
DB_PWD=cryptic

# create and run an empty database
# note: fails miserably if there are spaces in directory names

PGDATA=`pwd`/pgdata
export PGDATA

# make it if not there
mkdir -p $PGDATA

# clean it out if anything there
/bin/rm -rf $PGDATA/*

# set up the database directory layout
initdb

# start the server process
nohup postgres 2>&1 > postgres.log &
sleep 2

# create an empty DB/schema
createdb $DB_NAME

# create a user and password ("demo") to use for connections
psql -d $DB_NAME -c "create user $DB_USER password '$DB_PWD'"

psql -d $DB_NAME -f init_snap_db.sql

ps auxww | grep '[p]ostgres'
echo run tail -f postgres.log to monitor database

# vi: nu ai ts=4 sw=4
# *** EOF ***
