#!/bin/bash
echo ******Inicia desinstalacion de ControlObligaciones******
bin/ControlObligacionesWrapper remove
rm -rf /etc/cron.hourly/cron-en-ejecucion.sh
sed -i".back" '/trasher/d' /etc/crontab
echo ******Termina desinstalacion de ControlObligaciones******              
exit 0
