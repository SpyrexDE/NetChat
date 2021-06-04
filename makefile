netchat.deb: NetChat.jar
	cp NetChat.jar deb-build/opt/NetChat
	dpkg-deb --build deb-build
	mv deb-build.deb netchat.deb
