netchat.deb: NetChat.jar
	cp NetChat.jar deb/opt/NetChat
	dpkg-deb --build deb netchat.deb
