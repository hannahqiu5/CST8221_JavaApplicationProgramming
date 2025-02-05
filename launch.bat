@echo off
:: ---------------------------------------------------------------------
:: JAP COURSE - SCRIPT
:: SCRIPT CST8221 - Fall 2024
:: ---------------------------------------------------------------------
:: Begin of Script (Labs - F24)
:: ---------------------------------------------------------------------
CLS
:: LOCAL VARIABLES ....................................................
SET SRCDIR=src
SET BINDIR=bin
SET BINERR=javac.err
SET JARNAME=launch.jar
SET JAROUT=jar.out
SET JARERR=jar.err
SET DOCDIR=doc
SET DOCERR=javadoc.err
SET MAINCLASSSRC=src/Main.java
SET MAINCLASSBIN=Main

ECHO "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
ECHO "@                                                                   @"
ECHO "@                   #       @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  @"
ECHO "@                  ##       @  A L G O N Q U I N  C O L L E G E  @  @"
ECHO "@                ##  #      @    JAVA APPLICATION PROGRAMMING    @  @"
ECHO "@             ###    ##     @         F A L L  -  2 0 2 3        @  @"
ECHO "@          ###    ##        @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  @"
ECHO "@        ###    ##                                                  @"
ECHO "@        ##    ###                 ###                              @"
ECHO "@         ##    ###                ###                              @"
ECHO "@           ##    ##               ###   #####  ##     ##  #####    @"
ECHO "@         (     (      ((((()      ###       ## ###   ###      ##   @"
ECHO "@     ((((     ((((((((     ()     ###   ######  ###  ##   ######   @"
ECHO "@        ((                ()      ###  ##   ##   ## ##   ##   ##   @"
ECHO "@         ((((((((((( ((()         ###   ######    ###     ######   @"
ECHO "@         ((         ((           ###                               @"
ECHO "@          (((((((((((                                              @"
ECHO "@   (((                      ((                                     @"
ECHO "@    ((((((((((((((((((((() ))                                      @"
ECHO "@         ((((((((((((((((()                                        @"
ECHO "@                                                                   @"
ECHO "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"


ECHO "1. Compiling ......................"
javac -d %BINDIR% %SRCDIR%\*.java 2>%BINERR%

(
  ECHO Manifest-Version: 1.0
  ECHO Main-Class: %MAINCLASSBIN%
  ECHO Created-By: %USERNAME%
) > MANIFEST.MF

ECHO "2. Creating Jar ..................."
jar cvfm %BINDIR%\%JARNAME% MANIFEST.MF -C %BINDIR% . >%JAROUT% 2>%JARERR%


ECHO "3. Creating Javadoc ..............."
javadoc -d %DOCDIR% %MAINCLASSSRC% 2>%DOCERR%



ECHO "4. Running Jar ...................."
java -jar %BINDIR%\%JARNAME%
ECHO "[END OF SCRIPT -------------------]"
ECHO "                                   "
@echo on
:: ---------------------------------------------------------------------
:: End of Script (Labs - F24)
:: ------------------------------------------------------------------