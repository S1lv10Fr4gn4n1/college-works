#!/usr/bin/env python
#-*- encoding: iso-8859-1 -*-

import socket

class conection_tcp:
	# padroes do python

	# - Colocado "_" para indicar variavel privada
	# porem nao existe nivel privado no escopo do python
	# - Nao é feito set's
	# - Separador de palavras
	_host = None
	_port = None
	_tcp  = None
    
	def __init__(self):
		self._host = ""
		self._port = 0
        
	def init_conection(self):
		"""Inicia socket TCP""" # esse comentario, irá aparecer no help
		try:
			self._tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
			dest = (self._host, self._port)
			self._tcp.connect(dest)
		except Exception, detail:
			(code, message) = detail
			raise Exception("Problemas ao conectar!\nError: " + message)
    
	def close_conection(self):
		"""Fecha socket TCP"""
		self._tcp.close()
        
	def send_message(self, message):
		"""Envia mensagem"""
		try:
			message = unicode(message, 'iso-8859-1')
			self._tcp.send(message)
		except Exception, detail:
			(code, message) = detail
			raise Exception("Problemas ao enviar mensagem (" + message + ").\nError: " + message)
    	
	def receiver_message(self):
		"""Recebe mensagem"""
		try:
			message = self._tcp.recv(4096) 
			message = unicode(message, 'iso-8859-1')
			return message
		except Exception, detail:
			(code, message) = detail
			raise Exception("Problemas ao receber mensagem!\nError: " + message)
