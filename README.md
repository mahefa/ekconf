EKCONF
======
Tiana Rakotovao <rakotovaomahefa@gmail.com>


Ekconf is an Eclipse plug-in for configuring the Linux kernel or Buildroot.


Requirements
------------

Operating System : Linux 32 bits.
A version a Linux 64 bits is not yet available.

Eclipse version : Ekconf was tested with an Eclipse 3.6


Installation
------------

Use the Update Manager of Eclipse to install Ekconf :
- Start Eclipse, select Help > Install New Software...
- Click "Add" to add a new repository. The location is :
      http://mahefa.github.com/ekconf/p2/
  Enter a Name.
- Click "OK", then select "Ekconf" in the available software list.
- Click "Next" and continue the installation until the end.

You also need to download [libekconfig.so][1]. Save it into a folder
where it cannot be deleted easily. (Example: /opt/lib/)

Restart Eclipse, go to Window > Preferences > Ekconf, in the field
"libekconfig.so", enter the path to libekconfig.so or browse it.


About me
--------

I am a final (5th) year student in Computer Science and Engineering.
Feel free to send me your feedback.
Tiana Rakotovao <rakotovaomahefa@gmail.com>

[1]: https://github.com/downloads/mahefa/ekconf/libekconfig.so.1.0.0

