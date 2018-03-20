#!/bin/bash
echo ******Inicia instalacion de ControlObligaciones******
chmod 754 cron-en-ejecucion.sh
chmod 754 trasher.sh
cp cron-en-ejecucion.sh /etc/cron.hourly
echo "01 23 * * * root /home/cob/ControlObligacionesBack/trasher.sh" >> /etc/crontab
bin/ControlObligacionesWrapper install
echo ******Termina instalacion de ControlObligaciones******              
exit 0
