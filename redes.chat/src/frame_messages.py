#!/usr/bin/env python
#-*- encoding: iso-8859-1 -*-

import pygtk
import pango
import gtk, gtk.glade
from gtk.keysyms import Return 
from threading 		import Thread

class frame_message:
	_text_view_send_message = None
	_text_view_messages     = None
	_user_id                = None
	_user_name              = None
	_dad                    = None
	_frame_chat             = None
	_scrolled_window_view	= None
	_h_tag					= None
	_i_tag					= None
    
	def __init__(self):
		"""Inicializa a classe frame_message, carregando o GTK"""
		chat_glade = gtk.glade.XML('gtk/chat.glade')

		self._frame_chat             = chat_glade.get_widget('windowMessage')
		self._text_view_send_message = chat_glade.get_widget('textViewSendMessage')
		self._text_view_messages     = chat_glade.get_widget('textViewMessages')
		self._scrolled_window_view   = chat_glade.get_widget('scrolledwindow1')

		# criado as tags para rich text        
		text_buffer = self._text_view_messages.get_buffer()
		self._h_tag = text_buffer.create_tag( "h", size_points=10, weight=pango.WEIGHT_BOLD) 
		self._i_tag = text_buffer.create_tag( "i", style=pango.STYLE_ITALIC)

		dic = { 
			"on_windowMain_destroy"  : self.quit,
			"on_key_release_event"	 : self.on_key_release,
		}	    
		
		chat_glade.signal_autoconnect( dic )
	
	def show(self):
		"""Mostra a tela"""
		gtk.gdk.threads_init()
		gtk.main()
	    	
	def quit(self, widget):
		"""Mata a tela"""
		self.quit_gtk()

	def quit_gtk(self):
		"""Mata a tela"""
		self._dad.remove_user_from_list(self._user_id, self._user_name, self)
	
	def on_key_release(self, widget, event):
		"""Verifica o key release"""
		if event.keyval == Return:
			try:
				text_buffer = self._text_view_send_message.get_buffer()

				message = text_buffer.get_text(text_buffer.get_start_iter(), text_buffer.get_end_iter())

				# envia texto para area de visualisacao de mensagem
				self.set_message_view('me', message)	

				# envia mensagem
				self._dad._communication.send_message(self._user_id, message)

				# limpa area de escrita de mensagem
				self._text_view_send_message.get_buffer().set_text("")
			except Exception, detail:
				print detail
    
	def set_talk_user(self, user_id, user_name):
		"""Seta o usuario com quem esta conversando"""
		self._user_id   = user_id
		self._user_name = user_name
		self._frame_chat.set_title(self._user_name)
        
	def set_message_view(self, user, message):
		"""Seta as messagens no view"""
		text_buffer = self._text_view_messages.get_buffer()
		end_iter = text_buffer.get_end_iter()

		text_buffer.insert_with_tags(end_iter, user + " say: ", self._i_tag, self._h_tag) 

		end_iter = text_buffer.get_end_iter()
		text_buffer.insert(end_iter, message + "\n")
