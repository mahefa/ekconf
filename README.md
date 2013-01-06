EKCONF
======
Tiana Rakotovao Andriamahefa


Ekconf is an Eclipse plug-in for configuring the Linux kernel or Buildroot. Its
GUI is based on SWT, JFace and the Eclipse framework, but it uses the same
Kconfig parser as the Linux Kernel.

Screenshots
-----------
[Menu][2] and [main window][3] 


Requirements
------------

Operating System : Linux 32 bits. A version for a Linux 64 bits is not yet available.

Eclipse version : Ekconf was tested with an Eclipse 3.6 and Eclipse 3.7.


Installation
------------

Use the Update Manager of Eclipse to install Ekconf :
- Start Eclipse, select Help > Install New Software...
- Click "Add" to add a new repository. The location is : 
  http://mahefa.github.com/ekconf/p2/ .Enter a repository name.
- Click "OK", then select "Ekconf" in the available software list.
- Click "Next" and continue the installation until the end.

Then, download [libekconfig.so][1]. Save it into a folder
(rember the path)

Restart Eclipse, go to Window > Preferences > Ekconf, in the field
"libekconfig.so", browse to libekconfig.so

Finally, enjoy :)


[1]: https://github.com/downloads/mahefa/ekconf/libekconfig.so.1.0.0
[2]: http://cloud.github.com/downloads/mahefa/ekconf/ekconf_snapshot_1.jpg
[3]: http://cloud.github.com/downloads/mahefa/ekconf/ekconf_snapshot_2.jpg
