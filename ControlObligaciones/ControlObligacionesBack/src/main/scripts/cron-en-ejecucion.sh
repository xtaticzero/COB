#!/bin/bash
SERVICE='ControlObligaciones'
if ps ax | grep -v grep | grep $SERVICE > /dev/null
then
echo "El servicio $SERVICE esta ejecutandose"
else
echo sudo -u cob $COB_HOME/bin/ControlObligacionesWrapper start
fi
