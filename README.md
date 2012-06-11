EKCONF
======
Tiana Rakotovao <rakotovaomahefa@gmail.com>


Ekconf is an Eclipse plug-in for configuring the Linux kernel or Buildroot.

Have a look at the demo [here][2] and [here][3] to see how it works.


Requirements
------------

Operating System : Linux 32 bits.
A version a Linux 64 bits is not yet available.

Eclipse version : Ekconf was tested with an Eclipse 3.6.


Installation
------------

Use the Update Manager of Eclipse to install Ekconf :
- Start Eclipse, select Help > Install New Software...
- Click "Add" to add a new repository. The location is :
      http://mahefa.github.com/ekconf/p2/
  .Enter a repository name.
- Click "OK", then select "Ekconf" in the available software list.
- Click "Next" and continue the installation until the end.

You also need to download [libekconfig.so][1]. Save it into a folder
where it cannot be deleted easily. (Example: /opt/lib/)

Restart Eclipse, go to Window > Preferences > Ekconf, in the field
"libekconfig.so", enter the path to libekconfig.so or browse it.

Finaly, enjoy :)


About me
--------

I am a final (5th) year student in Computer Science and Engineering.
Feel free to mail me your feedback at <rakotovaomahefa@gmail.com>.

[1]: https://github.com/downloads/mahefa/ekconf/libekconfig.so.1.0.0
[2]: http://cloud.github.com/downloads/mahefa/ekconf/ekconf_snapshot_1.jpg
[3]: http://cloud.github.com/downloads/mahefa/ekconf/ekconf_snapshot_2.jpg

