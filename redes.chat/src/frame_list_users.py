#!/usr/bin/env python
#-*- encoding: iso-8859-1 -*-

# tratar excessoes de comunicacoes e outras
# tratar desconecao do servidor
# tratar login invalido
# scholl das mensagems

from conection.communication import communication
from frame_login import frame_login
from frame_messages import frame_message
from gtk.keysyms import Return
from threading import Thread
import gobject
import gtk
import gtk.glade
import sys
import time

class frame_list_users:
	_is_thread_live 		 = None
	_window_users            = None
	_menubar_activity        = None
	_menuitem_atividade      = None
	_menuitem_atividade_menu = None
	_menuitem_login          = None
	_menuitem_logoff         = None
	_menuitem_exit           = None
	_tree_view_users_on_line = None
	_list_users				 = None
	_list_frame_messages     = None
	_frame_chat              = None
	_communication			 = None
	_user_id				 = None
	_list_messages			 = None

	def __init__(self):
		"""Inicializa a classe frame_list_users e o GTK"""
		gtk.gdk.threads_init()
		list_users = gtk.glade.XML('gtk/users.glade')

		self._frame_chat              = list_users.get_widget('windowUsers')
		self._menuitem_exit           = list_users.get_widget('menuItemExit')
		self._menuitem_logoff         = list_users.get_widget('menuItemLogoff')
		self._menuitem_login          = list_users.get_widget('menuItemLogin')
		self._tree_view_users_on_line = list_users.get_widget('treeViewUsersOnLine')

		column = gtk.TreeViewColumn()
		cell   = gtk.CellRendererText()
		column.pack_start(cell)
		column.add_attribute(cell,'text',0)
		self._tree_view_users_on_line.append_column(column)

		dic = {
			"on_windowMain_destroy"  : self.quit,
			"on_activate_exit"       : self.quit_exit,
			"on_activate_login"      : self.login,
			"on_activate_logoff"     : self.logoff,
			"on_openWindowTalk"      : self.open_window_talk,
		}

		list_users.signal_autoconnect( dic )
		self._list_frame_messages = []
		self._list_messages = []

		gtk.main()

	def quit(self, widget):
		"""Mata a aplicação"""
		self._is_thread_live = False
		sys.exit(0)

	def quit_exit(self, event):
		"""Mata a aplicação"""
		self._is_thread_live = False
		sys.exit(0)

	def login(self, event):
		"""Faz login"""
		try:
			login = frame_login();
			user_id_, pass_ = login.getText()

			if (user_id_ == "" or pass_ == ""):
				return

			# guarda o id do usuario logado
			self._user_id = user_id_
			
			# desabilita o menu
			self._menuitem_login.set_sensitive(0)

			# cria a comunicacao
			self._communication = communication()
			self._communication._user_id  = user_id_
			self._communication._password = pass_

			self.start_threads_communication()
		except Exception, detail:
			print detail
		
	def logoff(self, event):
		"""Faz logoff"""
		# desligar a thread
		self._is_thread_live = False

		self._menuitem_login.set_sensitive(1)

		# limpar lista de contatos
		self._list_users = []
		self.list_users_on_line()

		# fechar janelas
		for i in self._list_frame_messages:
			frame = i;

			if frame == None:
				continue

			frame.quit_gtk() #REVER nao funca

	def list_users_on_line(self):
		"""Lista os usuários on-line"""
		l = gtk.ListStore(gobject.TYPE_STRING)

		for i in range(len(self._list_users)):
			l.append((self._list_users[i][1],))

		self._tree_view_users_on_line.set_model(l)

	def open_window_talk(self, treeview, path, view_column):
		"""Abre uma nova janela de conversa"""
		field, item = treeview.get_selection().get_selected()
		user = field.get_value(item, 0)

		for i in range(len(self._list_users)):
			if self._list_users[i][1] == user:
				index_user = i;
				break

		user_id_1   = self._list_users[index_user][0]
		user_name_1 = self._list_users[index_user][1]
		
		# verifica se ja existe uma janela aberta
		for i in self._list_frame_messages:
			user_id_2 = i
			if (user_id_2 == user_id_1):
				return # nao deixa criar outra janela			

		frame_1 = frame_message()
		self._list_frame_messages.append((user_id_1, user_name_1, frame_1))
		frame_1._dad = self
		frame_1.set_talk_user(user_id_1, user_name_1)
		frame_1.show()

	def remove_user_from_list(self, user_id, user_name, frame):
		"""Remove a janela de conversa da lista de janelas abertas"""
		index_list = self._list_frame_messages.index((user_id, user_name, frame))
		del self._list_frame_messages [index_list]
		
	def manage_receiver_message(self, list_messages):
		"""Gerencia as mensagens recebidas"""
		#print list_messages
		
		for i in list_messages:
			user_id_1, msg = i
			
			found = False
			
			for j in self._list_frame_messages:
				user_id_2, user_name, frame = j
				
				if (user_id_1 == user_id_2):
					frame.set_message_view(user_name, msg)
					found = True
					break
		
			if (not found):
				user_name = self.returns_user_by_id(user_id_1)
				self.start_threads_manage_frame_message(user_id_1, user_name, msg)
				
	def returns_user_by_id(self, user_id):
		"""Retorna o nome do usuário pelo ID"""
		for i in self._list_users:
			if (user_id == i[0]):
				user_name = i[1]
				break
			
		return user_name
		
	def start_threads_communication(self):
		"""Inicia as threads de comunicação"""
		self._is_thread_live = True
		
		self.start_threads_get_users()
		self.start_threads_get_msgs()
				
	
	def start_threads_get_users(self):
		"""Inicia a thread get_users (keep a live)"""

		class communic_get_users(Thread):
			"""Classe/Thread get_users"""
			_dad = None
	
			def __init__(self, dad):
				self._dad = dad
				Thread.__init__(self)

			def run(self):
				#print "run thread users"
				try:
					while (self._dad._is_thread_live):
						self._dad._list_users = self._dad._communication.get_list_users()
						self._dad.list_users_on_line()

						time.sleep(5)
					#print "end thread users"
				except Exception, detail:
					print detail
					
		com_get_users = communic_get_users(self)
		com_get_users.start()
			
	
	def start_threads_get_msgs(self):
		"""Inicia thread get_messages"""
		
		class communic_get_msgs(Thread):
			"""Classe/Thread get_messages"""
			_dad = None
	
			def __init__(self, dad):
				self._dad = dad
				Thread.__init__(self)
		
			def run(self):
				#print "run thread msg"
				try:
					while (self._dad._is_thread_live):
						list_messages = self._dad._communication.get_message()
			
						if (list_messages != None) and (list_messages != ""):
							self._dad.manage_receiver_message(list_messages)

						time.sleep(3)
					
					#print "end thread msg"
				except Exception, detail:
					print detail
		
		com_get_msgs = communic_get_msgs(self)
		com_get_msgs.start()

	def start_threads_manage_frame_message(self, user_id, user_name, msg):
		"""Inicia janelas de conversa, quando mensagens enviadas pelos outros usuarios,
		foi necessário colocar dentro de outra thread, pois o show da janela, impedia a continuação
		da thread get_messages"""
		
		class manage_frame_message(Thread):
			_dad       = None
			_user_id   = None
			_user_name = None
			_msg 	   = None
		
			def __init__(self):
				Thread.__init__(self)
				
			def set_param(self, dad, user_id, user_name, msg):
				self._dad = dad
				self._user_id = user_id
				self._user_name = user_name
				self._msg = msg
		
			def run(self):
				try:
					#print "run thread manage frame"

					frame = frame_message()
					self._dad._list_frame_messages.append((self._user_id, self._user_name, frame))
					frame._dad = self._dad
					frame.set_message_view(self._user_name, msg)
					frame.set_talk_user(self._user_id, self._user_name)
					frame.show()
					
					#print "end thread manage frame"
				except Exception, detail:
					print detail
		
		man_frame_msg = manage_frame_message()
		man_frame_msg.set_param(self, user_id, user_name, msg)
		man_frame_msg.start()

"""
#Nome           Login           Senha
#Andrey         6825            5sql4v
#Caio           9116            d3p3pq
#Daniel         6896            zn17b1
#Silvio         7237            ht7mxh
#Vander         1503            3k2919        
"""        		
