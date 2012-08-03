#!/usr/bin/env python
#-*- encoding: iso-8859-1 -*-

import gtk

class frame_login:
	def responseToDialog(self, entry_user_id, dialog, response):
		dialog.response(response)

	def getText(self):
		dialog = gtk.MessageDialog(
			None,
			gtk.DIALOG_DESTROY_WITH_PARENT,
			gtk.MESSAGE_QUESTION,
			gtk.BUTTONS_OK,
			None)

		dialog.set_markup('Entre com o <b>ID</b> e <b>senha</b>')

		entry_user_id = gtk.Entry()
		entry_pass    = gtk.Entry()
		entry_user_id.set_text('7237')
		entry_pass.set_text('ht7mxh')

		entry_user_id.connect("activate", self.responseToDialog, dialog, gtk.RESPONSE_OK)
		entry_pass.connect("activate", self.responseToDialog, dialog, gtk.RESPONSE_OK)

		hbox1 = gtk.HBox()
		hbox1.pack_start(gtk.Label("User Id:      "), False, 5, 5)
		hbox1.pack_end(entry_user_id)

		hbox2 = gtk.HBox()
		hbox2.pack_start(gtk.Label("Password:"), False, 5, 5)
		hbox2.pack_end(entry_pass)

		dialog.vbox.pack_end(hbox2, True, True, 0)
		dialog.vbox.pack_end(hbox1, True, True, 0)
		dialog.show_all()

		dialog.run()
		user_ = entry_user_id.get_text()
		pass_ = entry_pass.get_text()
		dialog.destroy()

		return user_, pass_
