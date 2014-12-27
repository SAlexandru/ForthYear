Constructing Intelligent Agents using Java, 2nd Edition 
Companion CD-ROM 


Welcome to the companion CD-ROM for Constructing Intelligent Agents using Java. 

In this document:
(You can also read this information in a browser by launching the index.html file from the root of the CD-ROM.)


I. Copyright Information
II. What's on the CD-ROM?
III. System Requirements
IV. Running the Software
V. Installing the CIAgent Software
VI. Running the CIAgent Software
VIII. Installing the Agent Building and Learning Environment Software
IX. Installing the Sun Java Runtime Environment Software
X. User Assistance and Information


I. Copyright Information

Copyright (c) 1997, 2001 Joseph P. Bigus and Jennifer Bigus. All rights reserved. Copyright (c) 2001 by John Wiley & Sons, Inc. All rights reserved. This CD-ROM and any Wiley publications and material which may be accessed from it are protected by copyright. Nothing on this CD-ROM or in the Wiley publications and material may be downloaded, reproduced, stored in a retrieval system, modified, made available on a network, used to create derivative works, or transmitted in any form or by any means, electronic, mechanical, photocopying, recording, scanning, or otherwise, except (i) in the United States, as permitted under Section 107 or 108 of the 1976 United States Copyright Act, or internationally, as permitted by other applicable national copyright laws, or (ii) as expressly authorized on this Web site, or (iii) with the prior written permission of the Publisher. Requests to the Publisher for permission should be addressed to the Permissions Department, John Wiley & Sons, Inc., 605 Third Avenue, New York, New York, 10158-0012, USA, (212) 850-6011, fax(212) 850-6008, email: permreq@wiley.com. You may download or print out one hard copy of a reasonable amount of material from this CD-ROM for your own non-commercial research or study only, and order forms may be copied for the purpose of ordering products from the Publisher. The statements and opinions in the material contained on this CD-ROM and any Wiley publications and material which may be accessed from this CD-ROM are those of the individual contributors or advertisers, as indicated. The Publisher has used reasonable care and skill in compiling the content of this CD-ROM. However, the Publisher and the Editors make no warranty as to the accuracy or completeness of any information on this CD-ROM and accept no responsibility or liability for any inaccuracy or errors and omissions, or for any damage or injury to persons or property arising out of the accessing or use of any files, software and other materials, instructions, methods or ideas contained on this CD-ROM or in the Wiley publications and material accessed from it. Any third party software or Web sites which may be accessed through this CD-ROM are the sole responsibility of the third party who is posting the software or Web site. The Publisher makes no warranty as to the accuracy of any information on third party software or Web sites and accepts no liability for any errors and omissions or for any damage or injury to personsor property arising out of the use or operation of any materials, instructions, methods or ideas contained on such software or Web sites. ALL DOWNLOADABLE SOFTWARE AND FILES ARE DISTRIBUTED ON AN "AS IS" BASIS WITHOUT WARRANTIES OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING, WITHOUT LIMITATIONS, WARRANTIES OF TITLE OR IMPLIED WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE, AND DOWNLOADING AND/OR USING THE SOFTWARE AND FILES IS AT THE USER'S SOLE RISK. 


II. What's on the CD-ROM?

This CD-ROM contains the Java source code and compiled class files for all of the example applications developed in this book. This includes the Search, Rule, and Learn applications from Part 1, and the PAManager, InfoFilter, and MarketPlace applications from Part 2. The CIAgent source and compiled class files are in the ciagent2e.zip file. The Javadocs are in the ciagent2eDocs.zip file. The CIAgent software has been unzipped on the CD-ROM and resides in the ciagent2e directory. The CD-ROM also contains the IBM Agent Building and Learning Environment (ABLE). ABLE is distributed as two zip files Able1_1b.zip and Able1_1bDocs.zip. The Agent Building and Learning Environment software has been unzipped on the CD-ROM and resides in the able1_1b directory. If you do not already have a Java 2 SDK or JRE installed, the Sun Java Runtime Environment Version 1.3 is provided as a convenience on the CD-ROM as well. The JRE is distributed as a self-extracting InstallShield program j2re1_3_0-win.exe. You can open a Web browser on the index.html file in the root directory of the CD-ROM to access the license and installation information. Equivalent information is provided in the readme.txt file. 


III. System Requirements

To run the Java examples, you must have a Java 2 SDK or Java Runtime Environment(JRE) installed on your machine. The Java 2 platform requires at least a 166 MHz Pentium processor. The Sun Java 2 Platform requires 64MB of RAM running Windows 98/NT. The CIAgent code requires 5MB of disk space. The Sun Java Runtime Environment Version 1.3.0 requires 16MB of disk space. The IBM Agent Building and Learning Environment requires 25MB of disk space. A CD-ROM drive and a web browser are required for navigation through the CD and viewing the CIAgent Javadocs. Internet access is required to run several of the CIAgent example applications. 


IV. Running the Software

The software must be copied from the CD-ROM to your computer to run. Installation procedures are provided below. For your convenience the CIAgent and IBM ABLE software is provided unzipped on this CD-ROM. 
V. Installing the CIAgent Software

You can use the ciagent2e.exe, self-extracting zip file to copy the CIAgent files (and directory structure) to your computer, or unzip the ciagent code and Javadocs from the two zip files provided. To use the ciagent2e.exe file, follow these steps: 

1. Create a directory to install the software using the mkdir command. We recommend ciagent2e as the base directory name. 

2. Open a MS-DOS Prompt (command line) window and cd to the installation directory on your hard disk.

3. On the command line, enter: 

copy g:\ciagent2e.exe 

where g is the correct letter of your CD-ROM. This command will copy the CIAgent zipfile onto your hard disk.

4. Run the executable, enter: ciagent2e.exe
This will create the directory structure and copy the files to the corresponding directories. You can directly unzip the code and docs to your system. The source code and compile class files are in the ciagent2e.zip and the JavaDocs are in ciagent2eDocs.zip. 


VI. Running the CIAgent Software

To run any of the six applications, you must first change to the root ciagent2e directory. This is one directory level above the CIAgent source code directories. From there you can run the applications by specifying their package name followed by the application class name. The pattern used for all six applications is <packagename>.<packagename>App. For example, to run the Search application you would enter: c:\ciagent2e> java search.SearchApp


VII. Compiling the CIAgent Software

To compile any of the seven Java packages you must first change to the root ciagent2e directory.  This is one directory level above the source code directories. From there you can compile the entire package (all Java files in the package directory) by entering:c:\ciagent2e> javac search\*.java 


VIII. Installing the Agent Building and Learning Environment Software

Before installing the IBM Agent Building and Learning Environment software, you must first read and agree to the terms and conditions described in the IBM ABLE License Agreement or able_license.txt file in the root directory). If you do not agree to the terms of the license, do not install this software. Installation instructions can be found in the ABLE documentation on this CD-ROM or on the IBM alphaWorks site http://www.alphaWorks.ibm.com/tech/able.


IX. Installing the Sun Java Runtime Environment Software

Before installing the Sun Microsystems, Inc. Java Runtime Environment software, you must first read and agree to the terms and conditions described in the Sun JRE License Agreement or j2re_license.txt file in the root directory. If you do not agree to the terms of the license, do not install this software. Installation instructions can be found on the Sun JRE site http://www.java.sun.com/j2se/1.3/jre.


X. User Assistance and Information

The software accompanying this book is being provided as is without warranty or support of any kind. Should you require basic installation assistance, or if your media is defective, please call our product support number at (212)850-6194 weekdays between 9 am and 4 pm Eastern Standard Time. Or, we can be reached via e-mail at: techhelp@wiley.com or visit http://www.wiley.com/techsupport. To place additional orders or to request information about other Wiley products, please call (800) 879-4539 or visit http://www.wiley.com. The authors can be contacted via e-mail at support@bigusbooks.com or visit http://www.bigusbooks.com for additional information.