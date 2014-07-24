Boundary SNMP MIB Compiler
==========================

The Boundary SNMP MIB Compiler `bmibc` allows the compilation of custom MIBs for SNMP.


SNMP4J-SMI
----------

The Boundary SDK uses the SNMP4J-SMI library to decode OIDs. For open source use free
license is granted which restricts usage to standard MIB modules which are 
not under the enterprise OID sub-tree. Licenses can be purchased from
[www.snmp4j.org](http://www.snmp4j.org/html/buy.html) for $49 US, at the time of this writing.

Usage
-----

```bash
$ bmibc -h
usage: bmibc
 -h,--help                                 Display help information
 -l,--license <license>                    The license key string you
                                           received with the purchase a
                                           SNMP4J-SMI license or its
                                           evaluation.You may provide
                                           leave empty, but then you may
                                           not use any MIB modules of the
                                           "enterprise" OID subtree.
 -m,--mib-path <mib_path>                  Path to single MIB, ZIP file of
                                           MIB(s), or a directory of MIBs
                                           to compile.
 -q,--quiet                                Quiet mode, suppress normal
                                           output.
 -r,--repository-dir <repository_dir>      An empty directory where the
                                           bmibccan read and store
                                           compiled MIB modules
                                           persistently. Directory must
                                           not contain any plain text
                                           (i.e., uncompiled) MIB files.
 -s,--compiler-strict <lenient|standard>   Strictness defines the number
                                           of syntax checks done on the
                                           sources.

```


Configuration
-------------
The sections the follow describe the configuration of the SNMP route.

### Environment Variables

SNMP adapter parameters `mibRepository` and `license` can be set
by providing values in the `BOUNDARY_SDK_HOME/etc/boundary-event-sdk` file.

Example
-------
In the example below, the `var\mibrepository` is the location of the existing compiled standard MIBs, the `BOUNDARY-MIB.txt` is a a custom MIB.
Running the command as below will add the new custom MIB to the repository and allow for decoding of OIDs from the MIB.
NOTE: The last option relaxes strict syntax checks.

```bash
$ bmibc -r var/mibrepository -m BOUNDARY-MIB.txt -s lenient
```

