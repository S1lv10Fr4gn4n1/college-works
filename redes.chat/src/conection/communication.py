#!/usr/bin/env python
#-*- encoding: iso-8859-1 -*-

import sys
from conection_tcp import conection_tcp
from conection_udp import conection_udp

class communication:
	_user_id    = None
	_password   = None
	_thread_com = None
	_tcp_user   = None
	_tcp_msg    = None
	_udp        = None
	_host       = None
	_port_udp   = None
	_port_tcp   = None

	def __init__(self):
		"""Inicializa configurações da comunicação"""
		self._host     = "larc.inf.furb.br"
		self._port_tcp = 1012
		self._port_udp = 1011

		self.configuration()

	def configuration(self):
		"""Aplica as configurações nos sockets TCP/UDP"""
		self._tcp_user 		 = conection_tcp()
		self._tcp_user._host = self._host
		self._tcp_user._port = self._port_tcp
		self._tcp_user.init_conection()

		self._tcp_msg 		= conection_tcp()
		self._tcp_msg._host = self._host
		self._tcp_msg._port = self._port_tcp
		self._tcp_msg.init_conection()

		self._udp 		= conection_udp()
		self._udp._host = self._host
		self._udp._port = self._port_udp
		self._udp.init_udp()

	def send_message(self, user_id, message):
		"""Envia mensagem"""
		try:
			self._udp.send_message("SEND MSG " +self._user_id + ":" + self._password + ":" + user_id + ":" + message)
		except Exception, detail:
			raise Exception(detail)

	def get_message(self):
		"""Recebe mensagem"""
		try:
			list_result = []

			message_send = "GET MSG " + self._user_id + ":" + self._password

			self._tcp_msg.send_message(message_send)
			msg_recv = self._tcp_msg.receiver_message()

			if (msg_recv.find("Erro: Id inv") >= 0):
				raise Exception("Id invalido")

			message = msg_recv
			
			while msg_recv != "0:":
				msg_recv = ""
				self._tcp_msg.send_message(message_send)
				msg_recv += self._tcp_msg.receiver_message()
				message += msg_recv

			if (message == "" or message == "0:"):
				return list_result			
			
			index = message.find(":")

			msg_aux = message
	
			# tira o ":" da frente da message
			if (index == 0):
				msg_aux = msg_aux[1:len(msg_aux)]

			# encontra aonde esta o primeiro ":"
			index = msg_aux.find(":")

			id_ = msg_aux[0:index]

			msg_aux = msg_aux[index+1:len(msg_aux)]

			index = msg_aux.find(":")

			# verifica se esta no final da string
			try:
				msg_aux[index+1]
			except:
				index += 3

			# ate aonde esta o : e menos 4, que eh o tamanho do ID
			msg_ = msg_aux[0:index-4]

			msg_aux = msg_aux[index-4:len(msg_aux)]

			list_result.append((id_, msg_),)

			return list_result
		except Exception, detail:
			raise

	def get_list_users(self):
		"""Recebe a lista de usuários on-line"""
		try:
			self._tcp_user.send_message("GET USRS " + self._user_id + ":" + self._password)

			msg_recv = "";

			while msg_recv.find(":0::") < 0:
				msg_recv += self._tcp_user.receiver_message()				
				 
				if (msg_recv.find("Erro: Id inv") >= 0):
					raise Exception("Id invalido")

			list_aux   = msg_recv.split(":")
			list_users = []

			for i in list_aux:
				if (i == "0") or (i == ''):
					del list_aux [list_aux.index(i)]

			# passa a segunda vez para tirar mais um # REVER
			for i in list_aux:
				if (i == "0") or (i == ''):
					del list_aux [list_aux.index(i)]

			for i in range(0, len(list_aux), 2):
				list_users.append((list_aux[i], list_aux[i+1]),)

			return list_users
		except Exception, detail:
			raise
