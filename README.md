JavaRMI_RemoteDisplayAndFileTransfer
====================================
This program is divided into 2 parts: the server side and the client side.

Client is running in the background. When we launch the server side and press connect button, it will call the method of the client and launch a window on the client side.

Text can be typed in the txtfield in server side and will show on the client side.

If the text is in the form of "[file] d:\xxx.xxx to f:\xxx.xxx", it will send the file in the server's path to the client's path with TCP socket.
