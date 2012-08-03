#!/usr/bin/env python
#-*- encoding: iso-8859-1 -*-
import sys

try:
    import pygtk
    pygtk.require("2.0")
except:
    print "Nao eh possivel executar, necessario GTK >= 2.0"
    sys.exit(1)    

from frame_list_users import frame_list_users

if __name__ == "__main__":
    try:
        frame_list_users()  
    except KeyboardInterrupt:
        print '\n\nGoodbye, world!'
