#!/usr/bin/env python
#-*- encoding: iso-8859-1 -*-

import socket

class conection_udp:
	_host = None
	_port = None
	_udp  = None
	_dest = None
        
	def init_udp(self):
		"""Inicia socket UDP"""
		self._udp = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
		self._dest = (self._host, self._port)
        
	def close_udp(self):
		"""Fecha a socket UDP"""
		self._udp.close()

	def send_message(self, message):
		"""Envia mensangem"""
		#message = unicode(message, 'iso-8859-1')
		self._udp.sendto (message, self._dest)
