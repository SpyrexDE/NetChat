VERSION="0.1.1"

netchat.deb: NetChat.jar deb
	cp NetChat.jar deb/opt/NetChat
	echo "Version: $(VERSION)\n`grep -v 'Version: ' deb/DEBIAN/control)`" > deb/DEBIAN/control 
	dpkg-deb --build deb netchat.deb
