#!/bin/bash
#VARIABLES
DIRECTORIO=/siat/cob/tmp
DIAS=15
MINUTOS=60
#COMANDO
#-mmin +$MINUTOS
#-mtime +$DIAS
find $DIRECTORIO -type f -mtime +$DIAS -exec rm {} \;
