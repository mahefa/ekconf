EKCONF
======
Tiana Rakotovao <rakotovaomahefa@gmail.com>.


Ekconf is an Eclipse plug-in for configuring the Linux kernel or Buildroot. Its
GUI is based on SWT, JFace and the Eclipse framework, but it uses the same
Kconfig parser as the Linux Kernel.

Look at the demo [here][2] and [here][3] to know how to use it.


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

You also need to download [libekconfig.so][1]. Save it into a folder
where it cannot be deleted easily. (Example: /opt/lib/)

Restart Eclipse, go to Window > Preferences > Ekconf, in the field
"libekconfig.so", enter the path to libekconfig.so or browse it.

Finally, enjoy :)


About me
--------

I am a student in final (5th) year in Computer Science and Engineering. You can
find my resume [here][4]. Feel free to send me your feedback at 
<rakotovaomahefa@gmail.com>.


[1]: https://github.com/downloads/mahefa/ekconf/libekconfig.so.1.0.0
[2]: http://cloud.github.com/downloads/mahefa/ekconf/ekconf_snapshot_1.jpg
[3]: http://cloud.github.com/downloads/mahefa/ekconf/ekconf_snapshot_2.jpg
[4]: http://mahefa.github.com/ekconf/resume/Tiana_RAKOTOVAO_Resume.pdf

